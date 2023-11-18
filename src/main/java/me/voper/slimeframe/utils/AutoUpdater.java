package me.voper.slimeframe.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.managers.SettingsManager;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public final class AutoUpdater implements Runnable {

    private static final String LATEST_RELEASE = "https://api.github.com/repos/VoperAD/SlimeFrame/releases/latest";
    private static final String RELEASES = "https://github.com/VoperAD/SlimeFrame/releases";

    private final SlimeFrame plugin;
    private final File updateFolder;
    private final File pluginFile;
    private final String userAgent;

    public AutoUpdater(@Nonnull SlimeFrame plugin, File pluginFile) {
        this.plugin = plugin;
        this.updateFolder = plugin.getServer().getUpdateFolderFile();
        this.pluginFile = pluginFile;
        this.userAgent = " (" + plugin.getName() + "/" + plugin.getDescription().getVersion() + ", " +
                "ServerVersion/" + plugin.getServer().getVersion() + ", " +
                "BukkitVersion/" + plugin.getServer().getBukkitVersion() +
                ")";
    }

    @Override
    public void run() {
        Release latestRelease = getLatestRelease();
        if (latestRelease == null || !latestRelease.isValid()) {
            plugin.getLogger().severe("Failed to check for updates");
            return;
        }

        if (isLatestVersionNewer(latestRelease.getCleanVersion())) {
            if (isAutoUpdateEnabled()) {
                info("There is a new version of SlimeFrame available: " + latestRelease.getTagName());
                info("Downloading...");
                downloadUpdate(latestRelease.getDownloadUrl());
            } else {
                info("There is a new version of SlimeFrame available: " + latestRelease.getTagName());
                info("Download it here: " + RELEASES);
            }
        } else {
            info("You are running the latest version");
        }
    }

    private boolean isAutoUpdateEnabled() {
        return SlimeFrame.getSettingsManager().getBoolean(SettingsManager.ConfigField.AUTO_UPDATE);
    }

    private @Nullable Release getLatestRelease() {
        try {
            URL url = new URL(LATEST_RELEASE);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.addRequestProperty("User-Agent", userAgent);
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            JsonElement jsonElement = JsonParser.parseReader(inputStreamReader);
            return new Release(jsonElement);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to check for updates: " + e.getMessage());
        }
        return null;
    }

    private boolean isLatestVersionNewer(String latest) {
        ComparableVersion latestVersion = new ComparableVersion(latest);
        ComparableVersion currentVersion = new ComparableVersion(plugin.getDescription().getVersion());
        return latestVersion.compareTo(currentVersion) > 0;
    }

    private void downloadUpdate(String downloadUrl) {
        BufferedInputStream in = null;
        FileOutputStream fout = null;

        try {
            URL url = new URL(downloadUrl);
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(new File(updateFolder, pluginFile.getName()));

            final byte[] data = new byte[4096];
            int count;
            while ((count = in.read(data, 0, 4096)) != -1) {
                fout.write(data, 0, count);
            }

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to download update: " + e.getMessage());
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to close file output stream: " + e.getMessage());
            }
        }
    }

    private void info(String message) {
        plugin.getLogger().info(message);
    }

    private static class Release {

        @Getter
        private final String tagName;
        private final String jarName;

        public Release(@Nonnull JsonElement element) {
            this.tagName = element.getAsJsonObject().get("tag_name").getAsString();
            this.jarName = element.getAsJsonObject().get("assets").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        }

        @Nonnull
        @Contract(pure = true)
        public String getDownloadUrl() {
            return "https://github.com/VoperAD/SlimeFrame/releases/download/" + tagName + "/" + jarName;
        }

        @Nonnull
        @Contract(pure = true)
        public String getCleanVersion() {
            return tagName.replace("v", "");
        }

        public boolean isValid() {
            return tagName != null && jarName != null;
        }

    }

}

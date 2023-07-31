package me.voper.slimeframe.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;


public class Updater {

    private final String userAgent;
    private final Plugin plugin;
    private final File updateFolder;
    private final File file;
    private final UpdateType updateType;
    private final boolean logger;
    private final Thread thread;
    private String downloadLink;
    private String version;
    private Result result = Result.SUCCESS;
    private int id;
    private int page = 1;
    private boolean emptyPage;

    private static final String DOWNLOAD = "/download";
    private static final String VERSIONS = "/versions";
    private static final String PAGE = "?page=";
    private static final String API_RESOURCE = "https://api.spiget.org/v2/resources/";

    public Updater(@Nonnull Plugin plugin, int id, File file, UpdateType updateType, boolean logger) {
        this.plugin = plugin;
        this.updateFolder = plugin.getServer().getUpdateFolderFile();
        this.id = id;
        this.file = file;
        this.updateType = updateType;
        this.logger = logger;

        this.userAgent = " (" + plugin.getName() + "/" + plugin.getDescription().getVersion() + ", " +
                "ServerVersion/" + Bukkit.getVersion() + ", " +
                "BukkitVersion/" + Bukkit.getBukkitVersion() +
                ")";

        downloadLink = API_RESOURCE + id;

        thread = new Thread(new UpdaterRunnable());
        thread.start();
    }

    public enum UpdateType {
        // Checks only the version
        VERSION_CHECK,
        // Downloads without checking the version
        DOWNLOAD,
        // If updater finds new version automatically it downloads it.
        CHECK_DOWNLOAD

    }

    public enum Result {

        UPDATE_FOUND,

        NO_UPDATE,

        SUCCESS,

        FAILED,

        BAD_ID
    }

    /**
     * Get the result of the update.
     *
     * @return result of the update.
     * @see Result
     */
    public Result getResult() {
        waitThread();
        return result;
    }

    /**
     * Get the latest version from spigot.
     *
     * @return latest version.
     */
    public String getVersion() {
        waitThread();
        return version;
    }

    /**
     * Check if id of resource is valid
     *
     * @param link link of the resource
     * @return true if id of resource is valid
     */
    private boolean checkResource(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", userAgent);

            int code = connection.getResponseCode();

            if (code != 200) {
                connection.disconnect();
                result = Result.BAD_ID;
                return false;
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Checks if there is any update available.
     */
    private void checkUpdate() {
        try {
            String page = Integer.toString(this.page);

            URL url = new URL(API_RESOURCE + id + VERSIONS + PAGE + page);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", userAgent);

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            JsonElement element = JsonParser.parseReader(reader);
            JsonArray jsonArray = element.getAsJsonArray();

            if (jsonArray.size() == 10 && !emptyPage) {
                connection.disconnect();
                this.page++;
                checkUpdate();
            } else if (jsonArray.size() == 0) {
                emptyPage = true;
                this.page--;
                checkUpdate();
            } else if (jsonArray.size() < 10) {
                element = jsonArray.get(jsonArray.size() - 1);

                JsonObject object = element.getAsJsonObject();
                element = object.get("name");
                version = element.toString().replaceAll("\"", "").replace("v", "");
                if (logger) {
                    info("Checking for update...");
                }
                if (shouldUpdate(version, plugin.getDescription().getVersion()) && updateType == UpdateType.VERSION_CHECK) {
                    result = Result.UPDATE_FOUND;
                    if (logger) {
                        info("There is a new version available for SlimeFrame!",
                                "Your version: " + plugin.getDescription().getVersion(),
                                "Latest version: " + version);
                    }
                } else if (updateType == UpdateType.DOWNLOAD) {
                    if (logger) {
                        plugin.getLogger().info("Downloading update... version not checked");
                    }
                    download();
                } else if (updateType == UpdateType.CHECK_DOWNLOAD) {
                    if (shouldUpdate(version, plugin.getDescription().getVersion())) {
                        if (logger) {
                            info("Update found, downloading now...", "After the download, restart the server");
                        }
                        download();
                    } else {
                        if (logger) {
                            info("Update not found");
                        }
                        result = Result.NO_UPDATE;
                    }
                } else {
                    if (logger) {
                        info("Update not found");
                    }
                    result = Result.NO_UPDATE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if plugin should be updated
     *
     * @param newVersion remote version
     * @param oldVersion current version
     */
    private boolean shouldUpdate(@Nonnull String newVersion, String oldVersion) {
        return !newVersion.equalsIgnoreCase(oldVersion) && isOtherVersionNewer(oldVersion, newVersion);
    }

    /**
     * Downloads the file
     */
    private void download() {
        BufferedInputStream in = null;
        FileOutputStream fout = null;

        try {
            URL url = new URL(downloadLink);
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(new File(updateFolder, file.getName()));

            final byte[] data = new byte[4096];
            int count;
            while ((count = in.read(data, 0, 4096)) != -1) {
                fout.write(data, 0, count);
            }
        } catch (Exception e) {
            if (logger) {
                plugin.getLogger().log(Level.SEVERE, "Updater tried to download the update, but was unsuccessful.");
            }
            result = Result.FAILED;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                this.plugin.getLogger().log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
                this.plugin.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * Updater depends on thread's completion, so it is necessary to wait for thread to finish.
     */
    private void waitThread() {
        if (thread != null && thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                this.plugin.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }

    private void info(@Nonnull String... text) {
        for (String s : text) {
            plugin.getLogger().info(s);
        }
    }

    public static boolean isOtherVersionNewer(String myVersion, String otherVersion) {
        ComparableVersion currentVersion = new ComparableVersion(myVersion);
        ComparableVersion remoteVersion = new ComparableVersion(otherVersion);
        return remoteVersion.compareTo(currentVersion) > 0;
    }

    public class UpdaterRunnable implements Runnable {

        public void run() {
            if (checkResource(downloadLink)) {
                downloadLink = downloadLink + DOWNLOAD;
                checkUpdate();
            }
        }
    }

}

package me.voper.slimeframe.utils;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;

import me.voper.slimeframe.SlimeFrame;

import org.jetbrains.annotations.Contract;

public final class Keys {

    // Categories
    public static final NamespacedKey CAT_MAIN = createKey("wf_main");
    public static final NamespacedKey CAT_RELICS = createKey("wf_relics");
    public static final NamespacedKey CAT_LITH = createKey("wf_lith");
    public static final NamespacedKey CAT_MESO = createKey("wf_meso");
    public static final NamespacedKey CAT_NEO = createKey("wf_neo");
    public static final NamespacedKey CAT_AXI = createKey("wf_axi");
    public static final NamespacedKey CAT_RESOURCES = createKey("wf_resources");
    public static final NamespacedKey CAT_MULTIBLOCKS = createKey("wf_multiblocks");
    public static final NamespacedKey CAT_MACHINES = createKey("wf_machines");
    public static final NamespacedKey CAT_GENERATORS = createKey("wf_generators");
    public static final NamespacedKey CAT_GEAR = createKey("wf_gear");
    public static final NamespacedKey CAT_UTILS_AND_TOOLS = createKey("wf_utils");
    public static final NamespacedKey CAT_PRIME_COMPONENTS = createKey("wf_parts");

    // Researches
    public static final NamespacedKey ORES_RESEARCH = createKey("wfres_ores");
    public static final NamespacedKey NOSAM_RESEARCH = createKey("wfres_nosam");
    public static final NamespacedKey FOCUSED_NOSAM_RESEARCH = createKey("wfres_foc_nosam");
    public static final NamespacedKey PRIME_NOSAM_RESEARCH = createKey("wfres_prim_nosam");
    public static final NamespacedKey ALLOYS_RESEARCH = createKey("wfres_alloys");
    public static final NamespacedKey ALLOY_PLATES_RESEARCH = createKey("wfres_plates");
    public static final NamespacedKey CRYO_SUIT_RESEARCH = createKey("wfres_cryo_suit");
    public static final NamespacedKey ENERGY_GEN_RESEARCH = createKey("wfres_energy_gen");
    public static final NamespacedKey ASTRAL_GEN_RESEARCH = createKey("wfres_astral_gen");
    public static final NamespacedKey MACHINES_RESEARCH = createKey("wfres_machines");
    public static final NamespacedKey ADV_MACHINES_RESEARCH = createKey("wfres_adv_machines");
    public static final NamespacedKey PRIME_MACHINES_RESEARCH = createKey("wfres_prime_machines");
    public static final NamespacedKey MULTIBLOCKS_RESEARCH = createKey("wfres_multiblocks");
    public static final NamespacedKey CONDENSED_PLATE_RESEARCH = createKey("wfres_condensed_plate");
    public static final NamespacedKey GENERAL_RESOURCES_RESEARCH = createKey("wfres_general_research");
    public static final NamespacedKey OROKIN_WAND_RESEARCH = createKey("wfres_orokin_wand");
    public static final NamespacedKey PRIME_ORO_WAND_RESEARCH = createKey("wfres_prime_oro_wand");
    public static final NamespacedKey CONTRACT_RESEARCH = createKey("wfres_contract");

    // Persistent data
    public static final NamespacedKey PLACED_BLOCK = createKey("wf_placed_block");
    public static final NamespacedKey KILL_COUNTER = createKey("wf_kills");
    public static final NamespacedKey FISHERIES_COUNTER = createKey("wf_fisheries");
    public static final NamespacedKey BLOCKS_BROKEN_COUNTER = createKey("wf_blocks_broken");
    public static final NamespacedKey BLOCKS_PLACED_COUNTER = createKey("wf_blocks_placed");
    public static final NamespacedKey VOID_TRACES_OWNED = createKey("wf_void_traces_owned");
    public static final NamespacedKey RELICS_INVENTORY = createKey("wf_relics_inv");
    public static final NamespacedKey RAKNOID = createKey("wf_raknoid");
    public static final NamespacedKey MERCHANT_RECIPE = createKey("wf_merchant_recipe");
    public static final NamespacedKey REACTANTS_COUNTER = createKey("wf_reactants_counter");
    public static final NamespacedKey RELIC_REFINEMENT = createKey("wf_relic_refinement");

    @Nonnull
    @Contract("_ -> new")
    public static NamespacedKey createKey(@Nonnull String key) {
        return new NamespacedKey(SlimeFrame.getInstance(), key);
    }

}

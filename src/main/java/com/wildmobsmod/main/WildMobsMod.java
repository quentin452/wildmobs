package com.wildmobsmod.main;

import com.wildmobsmod.entity.EntityConfig;
import com.wildmobsmod.entity.monster.dreath.EntityConfigDreathMired;
import com.wildmobsmod.entity.passive.hyena.EntityConfigHyena;
import com.wildmobsmod.entity.passive.jellyfish.EntityConfigJellyfish;
import com.wildmobsmod.entity.passive.mouse.EntityConfigMouse;
import com.wildmobsmod.entity.passive.ocelot.EntityConfigOcelot;
import com.wildmobsmod.entity.passive.wolf.EntityConfigWolf;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.misc.AquaHealingDrinkWaterHandler;
import com.wildmobsmod.misc.CreeperAvoidWMOcelotHandler;
import com.wildmobsmod.misc.MobDropsHandler;
import com.wildmobsmod.misc.OcelotHuntMiceHandler;
import com.wildmobsmod.misc.OcelotOverrideHandler;
import com.wildmobsmod.misc.PotionAquaHealing;
import com.wildmobsmod.misc.SeaScorpionSpecialSpawnHandler;
import com.wildmobsmod.misc.SkeletonWolfHandler;
import com.wildmobsmod.misc.WolfOverrideHandler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = WildMobsMod.MODID, name = WildMobsMod.MODNAME, version = WildMobsMod.VERSION, guiFactory = "com.wildmobsmod.main.GUIFactory",dependencies = "required-after:optimizationsandtweaks;")
public class WildMobsMod
{
	public static final String MODID = "wildmobsmod", MODNAME = "Wild Mobs", VERSION = "1.5.3";

	@SidedProxy(clientSide = "com.wildmobsmod.main.ClientProxy", serverSide = "com.wildmobsmod.main.ServerProxy")
	public static IProxy proxy;

	public static Configuration config;

	public static final String CONFIG_CATEGORY_GENERAL = "_general";
	public static final String CONFIG_CATEGORY_ENTITIES = "entities";

	public static int aquaHealingID = 40;
	public static boolean enableCalamari = true;
	public static int skeletonWolfChance = 3;
	public static final boolean enableFoxUnnaturalVariants = true; // flag is still (technically) in use, but no longer configurable (whether a functionality like this should be available in a proper SkinnedEntity-System remains to be decided)

	private static final String[] MIRED_LOOT = {"minecraft:bone, 1, 8", "minecraft:bone, 2, 4", "minecraft:wheat_seeds, 1, 7", "minecraft:wheat_seeds, 2, 2", "minecraft:arrow, 1, 5", "minecraft:arrow, 2, 2", "minecraft:rotten_flesh, 1, 6", "minecraft:stick, 1, 5", "minecraft:torch, 1, 4", "minecraft:bread, 1, 4", "minecraft:poisonous_potato, 1, 3", "minecraft:potato, 1, 2", "minecraft:coal:1, 1, 2", "minecraft:book, 1, 1", "minecraft:book, 2, 1", "minecraft:stone_sword, 1, 1", "minecraft:stone_pickaxe, 1, 1", "minecraft:stone_axe, 1, 1", "minecraft:stone_shovel, 1, 1", "minecraft:leather_helmet, 1, 1", "minecraft:leather_chestplate, 1, 1", "minecraft:leather_leggings, 1, 1", "minecraft:leather_boots, 1, 1", "minecraft:gold_nugget, 1, 1"};

	// Monster
	public static final EntityConfigDreathMired DREATH_MIRED_CONFIG = EntityConfigDreathMired.construct("Dreath", 5, 1, 1, true, true, true, 1.0D, true, 3, MIRED_LOOT, false);
	public static final EntityConfig FADED_CONFIG = EntityConfig.construct("Faded", 4, 1, 1, true);
	public static final EntityConfig MAGMAPLANT_CONFIG = EntityConfig.construct("Magma Plant", 8, 2, 4, true);
	public static final EntityConfig SEASCORPION_CONFIG = EntityConfig.construct("Sea Scorpion", 5, 1, 1, true);
	public static final EntityConfig TARANTULA_CONFIG = EntityConfig.construct("Tarantula", 15, 4, 4, true);
	public static final EntityConfig ZOMGUS_CONFIG = EntityConfig.construct("Zomgus", 8, 4, 4, true);

	// Passive
	public static final EntityConfig ARMADILLO_CONFIG = EntityConfig.construct("Armadillo", 5, 1, 1, true);
	public static final EntityConfig BISON_CONFIG = EntityConfig.construct("Bison", 4, 6, 10, true);
	public static final EntityConfig BUTTERFLY_CONFIG = EntityConfig.construct("Butterfly", 5, 1, 1, true);
	public static final EntityConfig COUGAR_CONFIG = EntityConfig.construct("Cougar", 4, 1, 1, true);
	public static final EntityConfig CHEETAH_CONFIG = EntityConfig.construct("Cheetah", 4, 1, 5, true);
	public static final EntityConfig DEER_CONFIG = EntityConfig.construct("Deer", 7, 4, 4, true);
	public static final EntityConfig DIREWOLF_CONFIG = EntityConfig.construct("Direwolf", 2, 4, 4, true);
	public static final EntityConfig DRAGONFLY_CONFIG = EntityConfig.construct("Dragonfly", 7, 1, 4, true);
	public static final EntityConfig FOX_CONFIG = EntityConfig.construct("Fox", 6, 1, 1, true);
	public static final EntityConfig GOAT_CONFIG = EntityConfig.construct("Goat", 10, 4, 4, true);
	public static final EntityConfig GOOSE_CONFIG = EntityConfig.construct("Goose", 8, 4, 4, true);
	public static final EntityConfigHyena HYENA_CONFIG = EntityConfigHyena.construct("Hyena", 4, 1, 5, true, true);
	public static final EntityConfigJellyfish JELLYFISH_CONFIG = EntityConfigJellyfish.construct("Jellyfish", 9, 3, 4, true, 2);
	public static final EntityConfigMouse MOUSE_CONFIG = EntityConfigMouse.construct("Mouse", 5, 1, 2, true, true);
	public static final EntityConfigOcelot OCELOT_CONFIG = EntityConfigOcelot.construct("Ocelot", 2, 1, 1, true, 50);
	public static final EntityConfigWolf WOLF_CONFIG = EntityConfigWolf.construct("Wolf", 4, 4, 4, true, 50);

//	public static final EntityConfig WIZARD_CONFIG = EntityConfig.construct("Wizard", 0, false); // Disabled for now;


	@Metadata
	public static ModMetadata meta;

	@Instance(MODID)
	public static WildMobsMod modInstance;

	public static final CreativeTabs TAB_WILDMOBS = new CreativeTabs(MODID)
	{
		@Override
		public Item getTabIconItem()
		{
			return WildMobsModItems.fur;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
		FMLCommonHandler.instance().bus().register(modInstance);
		proxy.registerModObjects();
		proxy.registerRenderThings();
	}

	public static Potion potionAquaHealing;

	public static Achievement achievementTameDireWolf;
	public static Achievement achievementKillWizard;

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		CommonProxy.expandPotionArray();
		//
		// Whether or not Wild Mob achievements should be on it's or achievement
		// page or not.
		//
//		if(WildMobsMod.enableAchievementPage) // Achievements disabled for now - this should be redone and expanded for the new Achievement system
//		{
//			achievementTameDireWolf = new Achievement("achievement.tameDireWolf", "tameDireWolf", 0, 0, WildMobsModItems.thickBone, AchievementList.buildSword).initIndependentStat().registerStat();
//			achievementKillWizard = new Achievement("achievement.killWizard", "killWizard", 2, 0, WildMobsModItems.magicBook, AchievementList.blazeRod).initIndependentStat().registerStat().setSpecial();
//			AchievementPage.registerAchievementPage(new AchievementPage("Wild Mobs", new Achievement[] { WildMobsMod.achievementTameDireWolf, WildMobsMod.achievementKillWizard }));
//		}
//		else
//		{
//			achievementTameDireWolf = new Achievement("achievement.tameDireWolf", "tameDireWolf", 5, -4, WildMobsModItems.thickBone, AchievementList.buildSword).initIndependentStat().registerStat();
//			achievementKillWizard = new Achievement("achievement.killWizard", "killWizard", 0, 12, WildMobsModItems.magicBook, AchievementList.blazeRod).initIndependentStat().registerStat().setSpecial();
//		}

		if(WOLF_CONFIG.isEnabled() && WOLF_CONFIG.getOverrideChance() > 0)
		{
			MinecraftForge.EVENT_BUS.register(new WolfOverrideHandler());
		}
		if(skeletonWolfChance > -1) // Enabled
		{
			MinecraftForge.EVENT_BUS.register(new SkeletonWolfHandler());
		}
		if(MOUSE_CONFIG.isEnabled())
		{
			MinecraftForge.EVENT_BUS.register(new OcelotHuntMiceHandler());
		}
		if(OCELOT_CONFIG.isEnabled())
		{
			MinecraftForge.EVENT_BUS.register(new CreeperAvoidWMOcelotHandler());
			if(OCELOT_CONFIG.getOverrideChance() > 0)
			{
				MinecraftForge.EVENT_BUS.register(new OcelotOverrideHandler());
			}
		}
		if(SEASCORPION_CONFIG.isEnabled())
		{
			MinecraftForge.EVENT_BUS.register(new SeaScorpionSpecialSpawnHandler());
			MinecraftForge.EVENT_BUS.register(new AquaHealingDrinkWaterHandler());
		}
		if(enableCalamari)
		{
			MinecraftForge.EVENT_BUS.register(new MobDropsHandler());
		}
		potionAquaHealing = (new PotionAquaHealing(WildMobsMod.aquaHealingID)).setIconIndex(0, 0).setPotionName("potion.wildmobs.aqua_healing");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.performCompatRelevantActions();
	}

	public static void syncConfig()
	{
		skeletonWolfChance = config.getInt("skeletonWolfChance", CONFIG_CATEGORY_ENTITIES, 3, -1, 100, "Chance (in Percent) for a Skeleton Wolf to spawn alongside a Skeleton (0 to disable; -1 to prevent the Skeleton Wolf Entity from being registered)");
		aquaHealingID = config.getInt("aquaHealingId", CONFIG_CATEGORY_GENERAL, 40, 0, 1024, "The Potion ID for Aqua Healing, please do your research before changing this!");
		enableCalamari = config.getBoolean("enableCalamari", CONFIG_CATEGORY_GENERAL, true, "Make Squids drop calamari");

		EntityConfig.syncAll(config);

		if(config.hasChanged())
		{
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.modID.equals(MODID)) syncConfig();
	}


	public static final String ENTITY_LOADING_FLAG_NAME = "wm_NotNewChecked";

	/**
	 *  <b>Required to prevent chanced replacement handlers from rolling every time the entity is loaded from a save!</b><br>
	 *  Creates a Boolean NBTTag ({@link #ENTITY_LOADING_FLAG_NAME}) to track if an entity has been reviewed by this function at any point.
	 *  Can be used to perform actions only when an entity is originally created and added to the world without that action being repeated whenever that entity is loaded from the world save.<br>
	 *  Explicitly setting the Tag to false or deleting it will make the game "forget" the entity is being loaded and is highly discouraged due to potential world-breakage.<br>
	 *  If possible this functionality should be replaced with Vanilla/Forge methods or at least a unified framework (if multiple mods need to do this, they will otherwise all add their own Tags).<br>
	 */
	public static boolean checkIsEntityNew(Entity entity) {
		NBTTagCompound nbt = entity.getEntityData();
		if(nbt.getBoolean(ENTITY_LOADING_FLAG_NAME)) {
			return false;
		} else {
			nbt.setBoolean(ENTITY_LOADING_FLAG_NAME, true);
			return true;
		}
	}
}

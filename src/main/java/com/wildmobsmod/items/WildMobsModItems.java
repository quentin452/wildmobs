package com.wildmobsmod.items;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class WildMobsModItems
{

	public static ArmorMaterial enumArmorMaterialFur = EnumHelper.addArmorMaterial("WILDMOBS_FUR", 5, new int[] { 1, 3, 2, 1 }, 15);
	public static ArmorMaterial enumArmorMaterialBisonLeather = EnumHelper.addArmorMaterial("WILDMOBS_BISON_LEATHER", 5, new int[] { 1, 3, 2, 1 }, 15);

	public static void registerAll()
	{
		initItems();
		registerItems();
	}

	// Armor
	public static Item helmetBison;
	public static Item chestplateBison;
	public static Item legsBison;
	public static Item bootsBison;
	public static Item helmetFur;
	public static Item chestplateFur;
	public static Item legsFur;
	public static Item bootsFur;
	
	// Food
	public static Item rawBisonMeat;
	public static Item cookedBisonMeat;
	public static Item rawCalamari;
	public static Item cookedCalamari;
	public static Item rawChevon;
	public static Item cookedChevon;
	public static Item rawGoose;
	public static Item cookedGoose;
	public static Item rawMouse;
	public static Item cookedMouse;
	public static Item rawVenison;
	public static Item cookedVenison;

	public static Item goldenSeaEgg;
	public static Item infectedFlesh;
	public static Item roastedMagmaPlantSeed;
	
	// Misc
	public static Item fur;
	public static Item bisonLeather;
	public static Item cougarHide;
	public static Item thickBone;
	public static Item butterfly;
	public static Item bugNet;
	public static Item dragonfly;
	public static Item armadilloShell;
	public static Item slimeDrop;
	public static Item magmaCreamDrop;
	public static Item magmaPlantSeed;
	public static Item jellyfish;
	public static Item netherJellyfish;
	public static Item miredBottle;
	public static Item seaScorpionBucket;
	public static Item seaScorpionEgg;
	
	//placeholder
	public static Item magicWand;

	// Spawn eggs
	public static final Item deerSpawnEgg = new ItemWildMobsMonsterPlacer("Deer", 0x775943, 0xCFC6C0, 0, false);
	public static final Item reindeerSpawnEgg = new ItemWildMobsMonsterPlacer("Deer", 0xD1BCAE, 0x7D624F, 1, false, "Reindeer");
	public static final Item elkSpawnEgg = new ItemWildMobsMonsterPlacer("Deer", 0xBD9071, 0x755143, 2, false, "Elk");
	public static final Item foxSpawnEgg = new ItemWildMobsMonsterPlacer("Fox", 0xDB7823, 0x2E2E2E, 0, false);
	public static final Item arcticFoxSpawnEgg = new ItemWildMobsMonsterPlacer("Fox", 0xEEEEE4, 0xB0B0B0, 1, false, "ArcticFox");
	public static final Item desertFoxSpawnEgg = new ItemWildMobsMonsterPlacer("Fox", 0xD9A871, 0xB08D66, 2, false, "DesertFox");
	public static final Item cougarSpawnEgg = new ItemWildMobsMonsterPlacer("Cougar", 0xB3896D, 0xFFFFFF, 0, false);
	public static final Item zomgusSpawnEgg = new ItemWildMobsMonsterPlacer("Zomgus", 0x3AA68E, 0x9C8B87, 0, false);
	public static final Item bisonSpawnEgg = new ItemWildMobsMonsterPlacer("Bison", 0x382E2A, 0x70513A, 0, false);
//	public static final Item wizardSpawnEgg = new ItemWildMobsMonsterPlacer("Wizard", 0x4F5678, 0xB5A59B, 0, false); //disabled for now
	public static final Item mouseSpawnEgg = new ItemWildMobsMonsterPlacer("Mouse", 0x7A6858, 0xEDA8A8, 0, false);
	public static final Item butterflySpawnEgg = new ItemWildMobsMonsterPlacer("Butterfly", 0xCC5621, 0xEFD71F, 0, false);
	public static final Item tarantulaSpawnEgg = new ItemWildMobsMonsterPlacer("Tarantula", 0x1A1812, 0xA30F0F, 0, false);
	public static final Item dreathSpawnEgg = new ItemWildMobsMonsterPlacer("Dreath", 0x86A868, 0x474747, 0, false);
	public static final Item miredSpawnEgg = new ItemWildMobsMonsterPlacer("Mired", 0x000000, 0x2B2B2B, 0, false);
	public static final Item goatSpawnEgg = new ItemWildMobsMonsterPlacer("Goat", 0xB5B5B5, 0x965A3E, 0, false);
	public static final Item direWolfSpawnEgg = new ItemWildMobsMonsterPlacer("DireWolf", 0xA59070, 0x5A4823, 0, false);
	public static final Item magmaPlantSpawnEgg = new ItemWildMobsMonsterPlacer("MagmaPlant", 0x9F452B, 0xF5835D, 0, false);
	public static final Item dragonflySpawnEgg = new ItemWildMobsMonsterPlacer("Dragonfly", 0x616BBA, 0xF0D737, 0, false);
	public static final Item armadilloSpawnEgg = new ItemWildMobsMonsterPlacer("Armadillo", 0xB5AEAC, 0xBCA593, 0, false);
	public static final Item jellyfishSpawnEgg = new ItemWildMobsMonsterPlacer("Jellyfish", 0xF5A51B, 0xBD4707, 0, false);
	public static final Item netherJellyfishSpawnEgg = new ItemWildMobsMonsterPlacer("Jellyfish", 0xA63719, 0xF5A51B, 6, true, "NetherJellyfish");
	public static final Item skeletonWolfSpawnEgg = new ItemWildMobsMonsterPlacer("SkeletonWolf", 0xC4A68F, 0x737373, 0, false);
	public static final Item gooseSpawnEgg = new ItemWildMobsMonsterPlacer("Goose", 0x9E7A59, 0x333333, 0, false);
	public static final Item wildCatSpawnEgg = new ItemWildMobsMonsterPlacer("WMOcelot", 0x807B78, 0xFFFFFF, 1, false, "WildCat");
	public static final Item wolfSpawnEgg = new ItemWildMobsMonsterPlacer("WMWolf", 0xB5B5B5, 0x965A3E, 1, false);
	public static final Item seaScorpionSpawnEgg = new ItemWildMobsMonsterPlacer("SeaScorpion", 0xA2AD57, 0x615A1F, 0, false);
	public static final Item fadedSpawnEgg = new ItemWildMobsMonsterPlacer("Faded", 0x997650, 0x7F7D75, 0, false);
	public static final Item cheetahSpawnEgg = new ItemWildMobsMonsterPlacer("Cheetah", 0xC69B69, 0x1A1A1A, 0, false);
	public static final Item hyenaSpawnEgg = new ItemWildMobsMonsterPlacer("Hyena", 0x5E4730, 0x211C15, 0, false);
	

	public static void initItems()
	{
		//Armor
		helmetBison = new ItemWMArmor(enumArmorMaterialBisonLeather, 5, 0, "wildmobsmod:bison_helmet", "wildmobsmod:textures/models/armor/bison_layer_1.png").setUnlocalizedName("bison_leather_helmet");
		chestplateBison = new ItemWMArmor(enumArmorMaterialBisonLeather, 5, 1, "wildmobsmod:bison_chestplate", "wildmobsmod:textures/models/armor/bison_layer_1.png").setUnlocalizedName("bison_leather_chestplate");
		legsBison = new ItemWMArmor(enumArmorMaterialBisonLeather, 5, 2, "wildmobsmod:bison_leggings", "wildmobsmod:textures/models/armor/bison_layer_2.png").setUnlocalizedName("bison_leather_leggings");
		bootsBison = new ItemWMArmor(enumArmorMaterialBisonLeather, 5, 3, "wildmobsmod:bison_boots", "wildmobsmod:textures/models/armor/bison_layer_1.png").setUnlocalizedName("bison_leather_boots");
		helmetFur = new ItemWMArmor(enumArmorMaterialFur, 5, 0, "wildmobsmod:fur_helmet", "wildmobsmod:textures/models/armor/fur_layer_1.png").setUnlocalizedName("fur_helmet");
		chestplateFur = new ItemWMArmor(enumArmorMaterialFur, 5, 1, "wildmobsmod:fur_chestplate", "wildmobsmod:textures/models/armor/fur_layer_1.png").setUnlocalizedName("fur_chestplate");
		legsFur = new ItemWMArmor(enumArmorMaterialFur, 5, 2, "wildmobsmod:fur_leggings", "wildmobsmod:textures/models/armor/fur_layer_2.png").setUnlocalizedName("fur_leggings");
		bootsFur = new ItemWMArmor(enumArmorMaterialFur, 5, 3, "wildmobsmod:fur_boots", "wildmobsmod:textures/models/armor/fur_layer_1.png").setUnlocalizedName("fur_boots");
		
		//Food
		rawBisonMeat = new ItemWMFood(4, 0.6F, true).setInternalName("bison_meat");
		cookedBisonMeat = new ItemWMFood(8, 0.6F, false).setInternalName("bison_meat_cooked");
		rawCalamari = new ItemWMFood(2, 0.6F, false).setInternalName("calamari");
		cookedCalamari = new ItemWMFood(5, 0.6F, false).setInternalName("calamari_cooked");
		rawChevon = new ItemWMFood(3, 0.6F, false).setInternalName("chevon");
		cookedChevon = new ItemWMFood(6, 0.6F, false).setInternalName("chevon_cooked");
		rawGoose = new ItemWMFood(2, 0.6F, false).setInternalName("goose");
		cookedGoose = new ItemWMFood(6, 0.6F, false).setInternalName("goose_cooked");
		rawMouse = new ItemWMFood(1, 0.6F, false).setInternalName("mouse");
		cookedMouse = new ItemWMFood(4, 0.6F, false).setInternalName("mouse_cooked");
		rawVenison = new ItemWMFood(4, 0.6F, true).setInternalName("venison");
		cookedVenison = new ItemWMFood(8, 0.6F, false).setInternalName("venison_cooked");

		goldenSeaEgg = new ItemFoodGoldenSeaEgg(6, 1F, false).setInternalName("golden_sea_egg");
		infectedFlesh = new ItemFoodInfectedFlesh(4, 0.2F, false).setInternalName("infected_flesh");
		roastedMagmaPlantSeed = ((ItemFood) new ItemWMFood(2, 0.5F, false).setInternalName("roasted_magma_plant_seed")).setPotionEffect(Potion.fireResistance.id, 300, 0, 1F).setAlwaysEdible();
		
		//Misc
		armadilloShell = new ItemWM().setInternalName("armadillo_shell");
		cougarHide = new ItemWM().setInternalName("cougar_hide");
		bisonLeather = new ItemWM().setInternalName("bison_leather");
		fur = new ItemWM().setInternalName("fur");
		magmaCreamDrop = new ItemWM().setInternalName("magma_cream_drop");
		slimeDrop = new ItemWM().setInternalName("slime_drop");
		thickBone = new ItemWM().setInternalName("thick_bone");

		bugNet = new ItemBugNet().setInternalName("bug_net");
		butterfly = new ItemButterfly().setInternalName("butterfly");
		dragonfly = new ItemDragonfly().setInternalName("dragonfly");
		jellyfish = new ItemJellyfishBucket(false).setInternalName("jellyfish_bucket");
		netherJellyfish = new ItemJellyfishBucket(true).setInternalName("jellyfish_bucket");
		magmaPlantSeed = new ItemMagmaPlantSeed().setInternalName("magma_plant_seed");
		miredBottle = new ItemMiredBottle().setInternalName("mired_bottle");
		seaScorpionBucket = new ItemSeaScorpionBucket().setInternalName("sea_scorpion_bucket");
		seaScorpionEgg = new ItemSeaScorpionEgg().setInternalName("sea_scorpion_egg");
		
		//placeholder
		magicWand = new ItemWM().setInternalName("magic_wand");
	}

	public static void registerItems()
	{
		//Armor
		GameRegistry.registerItem(helmetBison, "bison_leather_helmet");
		GameRegistry.registerItem(chestplateBison, "bison_leather_chestplate");
		GameRegistry.registerItem(legsBison, "bison_leather_leggings");
		GameRegistry.registerItem(bootsBison, "bison_leather_boots");
		GameRegistry.registerItem(helmetFur, "fur_helmet");
		GameRegistry.registerItem(chestplateFur, "fur_chestplate");
		GameRegistry.registerItem(legsFur, "fur_leggings");
		GameRegistry.registerItem(bootsFur, "fur_boots");
		
		//Food
		GameRegistry.registerItem(rawBisonMeat, "bison_meat");
		GameRegistry.registerItem(cookedBisonMeat, "cooked_bison_meat");
		GameRegistry.registerItem(rawCalamari, "calamari");
		GameRegistry.registerItem(cookedCalamari, "cooked_calamari");
		GameRegistry.registerItem(rawChevon, "chevon");
		GameRegistry.registerItem(cookedChevon, "cooked_chevon");
		GameRegistry.registerItem(rawGoose, "goose");
		GameRegistry.registerItem(cookedGoose, "cooked_goose");
		GameRegistry.registerItem(rawMouse, "mouse");
		GameRegistry.registerItem(cookedMouse, "cooked_mouse");
		GameRegistry.registerItem(rawVenison, "venison");
		GameRegistry.registerItem(cookedVenison, "cooked_venison");

		GameRegistry.registerItem(goldenSeaEgg, "golden_sea_egg");
		GameRegistry.registerItem(infectedFlesh, "infected_flesh");
		GameRegistry.registerItem(roastedMagmaPlantSeed, "roasted_magma_plant_seed");

		//Misc
		GameRegistry.registerItem(seaScorpionEgg, "sea_scorpion_egg");
		GameRegistry.registerItem(magmaPlantSeed, "magma_plant_seed");
		GameRegistry.registerItem(miredBottle, "mired_bottle");
		GameRegistry.registerItem(cougarHide, "cougar_hide");	// this doesn't have a use right now, generic fur is dropped (with the exception of bisons)
		GameRegistry.registerItem(thickBone, "thick_bone");
		GameRegistry.registerItem(butterfly, "butterfly");
		GameRegistry.registerItem(bugNet, "bug_net");
		GameRegistry.registerItem(bisonLeather, "bison_leather");
		GameRegistry.registerItem(fur, "fur");
		GameRegistry.registerItem(dragonfly, "dragonfly");
		GameRegistry.registerItem(armadilloShell, "armadillo_shell");
		GameRegistry.registerItem(slimeDrop, "slime_drop");
		GameRegistry.registerItem(magmaCreamDrop, "magma_cream_drop");
		GameRegistry.registerItem(jellyfish, "jellyfish_bucket");
		GameRegistry.registerItem(netherJellyfish, "nether_jellyfish_bucket");
		GameRegistry.registerItem(seaScorpionBucket, "sea_scorpion_bucket");

		//placeholder
		GameRegistry.registerItem(magicWand, "magic_wand");

		//
		// Spawn eggs
		//
		if(WildMobsMod.DEER_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(deerSpawnEgg, "spawn_egg_deer");
			GameRegistry.registerItem(reindeerSpawnEgg, "spawn_egg_reindeer");
			GameRegistry.registerItem(elkSpawnEgg, "spawn_egg_elk");
		}
		if(WildMobsMod.FOX_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(foxSpawnEgg, "spawn_egg_fox");
			GameRegistry.registerItem(arcticFoxSpawnEgg, "spawn_egg_arctic");
			GameRegistry.registerItem(desertFoxSpawnEgg, "spawn_egg_desert");
		}
		if(WildMobsMod.COUGAR_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(cougarSpawnEgg, "spawn_egg_cougar");
		}
		if(WildMobsMod.ZOMGUS_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(zomgusSpawnEgg, "spawn_egg_zomgus");
		}
		if(WildMobsMod.BISON_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(bisonSpawnEgg, "spawn_egg_bison");
		}
//		if(WildMobsMod.WIZARD_CONFIG.isEnabled()) // disabled for now
//		{
//			GameRegistry.registerItem(wizardSpawnEgg, "spawn_egg_wizard");
//		}
		if(WildMobsMod.MOUSE_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(mouseSpawnEgg, "spawn_egg_mouse");
		}
		if(WildMobsMod.BUTTERFLY_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(butterflySpawnEgg, "spawn_egg_butterfly");
		}
		if(WildMobsMod.TARANTULA_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(tarantulaSpawnEgg, "spawn_egg_tarantula");
		}
		if(WildMobsMod.DREATH_MIRED_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(dreathSpawnEgg, "spawn_egg_dreath");
			GameRegistry.registerItem(miredSpawnEgg, "spawn_egg_mired");
		}
		if(WildMobsMod.GOAT_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(goatSpawnEgg, "spawn_egg_goat");
		}
		if(WildMobsMod.DIREWOLF_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(direWolfSpawnEgg, "spawn_egg_dire_wolf");
		}
		if(WildMobsMod.MAGMAPLANT_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(magmaPlantSpawnEgg, "spawn_egg_magma_plant");
		}
		if(WildMobsMod.DRAGONFLY_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(dragonflySpawnEgg, "spawn_egg_dragonfly");
		}
		if(WildMobsMod.ARMADILLO_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(armadilloSpawnEgg, "spawn_egg_armadillo");
		}
		if(WildMobsMod.JELLYFISH_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(jellyfishSpawnEgg, "spawn_egg_jellyfish");
			GameRegistry.registerItem(netherJellyfishSpawnEgg, "spawn_egg_nether_jellyfish");
		}
		if(WildMobsMod.skeletonWolfChance > -1)
		{
			GameRegistry.registerItem(skeletonWolfSpawnEgg, "spawn_egg_skeleton_wolf");
		}
		if(WildMobsMod.GOOSE_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(gooseSpawnEgg, "spawn_egg_goose");
		}
		if(WildMobsMod.OCELOT_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(wildCatSpawnEgg, "spawn_egg_wild_cat");
		}
		if(WildMobsMod.WOLF_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(wolfSpawnEgg, "spawn_egg_wmwolf");
		}
		if(WildMobsMod.SEASCORPION_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(seaScorpionSpawnEgg, "spawn_egg_sea_scorpion");
		}
		if(WildMobsMod.FADED_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(fadedSpawnEgg, "spawn_egg_faded");
		}
		if(WildMobsMod.CHEETAH_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(cheetahSpawnEgg, "spawn_egg_cheetah");
		}
		if(WildMobsMod.HYENA_CONFIG.isEnabled())
		{
			GameRegistry.registerItem(hyenaSpawnEgg, "spawn_egg_hyena");
		}
	}
}

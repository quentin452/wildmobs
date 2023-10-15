//package com.wildmobsmod.entity.monster.magmaplant;
//
//import org.apache.commons.lang3.ArrayUtils;
//
//import com.wildmobsmod.main.WildMobsMod;
//
//import cpw.mods.fml.common.registry.EntityRegistry;
//import net.minecraft.entity.EnumCreatureType;
//import net.minecraft.world.biome.BiomeGenBase;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;
//
//public class RegisterMagmaPlant
//{
//
//	public static void mainRegistry()
//	{
//		registerModEntity();
//	}
//
//	public static void registerModEntity()
//	{
//
//		createEntity(EntityMagmaPlant.class, "MagmaPlant", 0xA59070, 0x5A4823);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.magmaPlantSpawnRate;
//		boolean flag = WildMobsMod.enableMagmaPlant;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 15, WildMobsMod.modInstance, 100, 1, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.NETHER));
//			EntityRegistry.addSpawn(entityClass, i, 2, 4, EnumCreatureType.monster, biomes);
//		}
//	}
//}

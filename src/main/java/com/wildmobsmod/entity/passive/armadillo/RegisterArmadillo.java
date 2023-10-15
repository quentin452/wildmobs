package com.wildmobsmod.entity.passive.armadillo;
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
//public class RegisterArmadillo
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
//		createEntity(EntityArmadillo.class, "Armadillo", 0x775943, 0xCFC6C0);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.armadilloSpawnRate;
//		boolean flag = WildMobsMod.enableArmadillo;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 19, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.FOREST));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.JUNGLE));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.PLAINS));
//			EntityRegistry.addSpawn(entityClass, i, 1, 1, EnumCreatureType.creature, biomes);
//		}
//	}
//}

//package com.wildmobsmod.entity.passive.dragonfly;
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
//public class RegisterDragonfly
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
//		createEntity(EntityDragonfly.class, "Dragonfly", 0xCC5621, 0xEFD71F);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.dragonflySpawnRate;
//		boolean flag = WildMobsMod.enableDragonfly;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 17, WildMobsMod.modInstance, 82, 1, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.BEACH));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.SWAMP));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.JUNGLE));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.RIVER));
//			EntityRegistry.addSpawn(entityClass, i, 1, 4, EnumCreatureType.ambient, biomes);
//		}
//	}
//}

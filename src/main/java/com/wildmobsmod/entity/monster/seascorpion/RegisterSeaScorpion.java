//package com.wildmobsmod.entity.monster.seascorpion;
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
//public class RegisterSeaScorpion
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
//		createEntity(EntitySeaScorpion.class, "SeaScorpion", 0xFFFFFF, 0xFFFFFF);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.seaScorpionSpawnRate;
//		boolean flag = WildMobsMod.enableSeaScorpion;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 26, WildMobsMod.modInstance, 82, 2, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.OCEAN));
//			EntityRegistry.addSpawn(entityClass, i, 1, 1, EnumCreatureType.waterCreature, biomes);
//		}
//	}
//}

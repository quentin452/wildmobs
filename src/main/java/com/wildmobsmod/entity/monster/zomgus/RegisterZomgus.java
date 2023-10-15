//package com.wildmobsmod.entity.monster.zomgus;
//
//import com.wildmobsmod.main.WildMobsMod;
//
//import cpw.mods.fml.common.registry.EntityRegistry;
//import net.minecraft.entity.EnumCreatureType;
//import net.minecraft.world.biome.BiomeGenBase;
//
//public class RegisterZomgus
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
//		createEntity(EntityZomgus.class, "Zomgus", 0x3AA68E, 0x9C8B87);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.zomgusSpawnRateMushroomIsland;
//		int j = WildMobsMod.zomgusSpawnRateRoofedForest;
//		boolean flag = WildMobsMod.enableZomgus;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 4, WildMobsMod.modInstance, 82, 3, true);
//			EntityRegistry.addSpawn(entityClass, j, 4, 4, EnumCreatureType.monster, BiomeGenBase.getBiome(29), BiomeGenBase.getBiome(157), BiomeGenBase.getBiome(157));
//			EntityRegistry.addSpawn(entityClass, i, 4, 4, EnumCreatureType.monster, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore);
//		}
//	}
//}

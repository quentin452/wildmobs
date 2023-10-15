//package com.wildmobsmod.entity.monster.dreath;
//
//import java.util.ArrayList;
//
//import com.wildmobsmod.main.WildMobsMod;
//
//import cpw.mods.fml.common.registry.EntityRegistry;
//import net.minecraft.entity.EnumCreatureType;
//import net.minecraft.world.biome.BiomeGenBase;
//
//public class RegisterDreath
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
//		createEntity(EntityDreath.class, "Dreath", 0x86A868, 0x474747);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.dreathSpawnRate;
//		boolean flag = WildMobsMod.enableDreath;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 11, WildMobsMod.modInstance, 82, 3, true);
//
//			ArrayList<BiomeGenBase> all_biomesList = new ArrayList<BiomeGenBase>();
//			for(BiomeGenBase biome : BiomeGenBase.getBiomeGenArray())
//			{
//				if(biome != null)
//				{
//					if(biome != biome.mushroomIsland && biome != biome.mushroomIslandShore)
//					{
//						all_biomesList.add(biome);
//					}
//				}
//			}
//			BiomeGenBase[] all_biomes_array = new BiomeGenBase[all_biomesList.size()];
//			all_biomes_array = all_biomesList.toArray(all_biomes_array);
//			EntityRegistry.addSpawn(entityClass, i, 1, 1, EnumCreatureType.monster, all_biomes_array);
//		}
//	}
//}

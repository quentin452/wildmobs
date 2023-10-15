//package com.wildmobsmod.entity.passive.direwolf;
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
//public class RegisterDireWolf
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
//		createEntity(EntityDireWolf.class, "DireWolf", 0xA59070, 0x5A4823);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.direWolfSpawnRate;
//		boolean flag = WildMobsMod.enableDireWolf;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 14, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.FOREST));
//			EntityRegistry.addSpawn(entityClass, i, 4, 4, EnumCreatureType.creature, biomes);
//		}
//	}
//}

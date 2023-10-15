//package com.wildmobsmod.entity.passive.cougar;
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
//public class RegisterCougar
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
//		createEntity(EntityCougar.class, "Cougar", 0xB3896D, 0xFFFFFF);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.cougarSpawnRate;
//		boolean flag = WildMobsMod.enableCougar;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 2, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.MOUNTAIN));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.MESA));
//			EntityRegistry.addSpawn(entityClass, i, 1, 1, EnumCreatureType.creature, biomes);
//		}
//	}
//}

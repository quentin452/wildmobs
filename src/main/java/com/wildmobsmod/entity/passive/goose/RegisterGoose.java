//package com.wildmobsmod.entity.passive.goose;
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
//public class RegisterGoose
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
//		createEntity(EntityGoose.class, "Goose", 0x775943, 0xCFC6C0);
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.gooseSpawnRateWater;
//		int j = WildMobsMod.gooseSpawnRateSwamp;
//		boolean flag = WildMobsMod.enableGoose;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 23, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes1 = new BiomeGenBase[0];
//			biomes1 = ArrayUtils.addAll(biomes1, BiomeDictionary.getBiomesForType(Type.RIVER));
//			biomes1 = ArrayUtils.addAll(biomes1, BiomeDictionary.getBiomesForType(Type.BEACH));
//
//			BiomeGenBase[] biomes2 = new BiomeGenBase[0];
//			biomes2 = ArrayUtils.addAll(biomes2, BiomeDictionary.getBiomesForType(Type.SWAMP));
//
//			EntityRegistry.addSpawn(entityClass, i, 4, 4, EnumCreatureType.creature, biomes1);
//			EntityRegistry.addSpawn(entityClass, j, 4, 4, EnumCreatureType.creature, biomes2);
//		}
//	}
//}

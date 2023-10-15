//package com.wildmobsmod.entity.passive.deer;
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
//public class RegisterDeer
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
//		createEntity(EntityDeer.class, "Deer", 0x775943, 0xCFC6C0);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.deerSpawnRate;
//		boolean flag = WildMobsMod.enableDeer;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 0, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.FOREST));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.COLD));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.SWAMP));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.MOUNTAIN));
//			EntityRegistry.addSpawn(entityClass, i, 4, 4, EnumCreatureType.creature, biomes);
//		}
//	}
//}

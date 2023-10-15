//package com.wildmobsmod.entity.passive.bison;
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
//public class RegisterBison
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
//		createEntity(EntityBison.class, "Bison", 0x382E2A, 0x70513A);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.bisonSpawnRate;
//		boolean flag = WildMobsMod.enableBison;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 5, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.PLAINS));
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.SAVANNA));
//			EntityRegistry.addSpawn(entityClass, i, 6, 10, EnumCreatureType.creature, biomes);
//		}
//	}
//}

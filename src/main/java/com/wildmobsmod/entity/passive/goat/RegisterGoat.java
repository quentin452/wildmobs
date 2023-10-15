//package com.wildmobsmod.entity.passive.goat;
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
//public class RegisterGoat
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
//		createEntity(EntityGoat.class, "Goat", 0xB5B5B5, 0x965A3E);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.goatSpawnRate;
//		boolean flag = WildMobsMod.enableGoat;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 13, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.MOUNTAIN));
//			EntityRegistry.addSpawn(entityClass, i, 4, 4, EnumCreatureType.creature, biomes);
//		}
//	}
//}

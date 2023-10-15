package com.wildmobsmod.entity.passive.jellyfish;

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
//public class RegisterJellyfish
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
//		createEntity(EntityJellyfish.class, "Jellyfish", 0x775943, 0xCFC6C0);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		int i = WildMobsMod.jellyfishSpawnRate;
//		boolean flag = WildMobsMod.enableJellyfish;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 20, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			BiomeGenBase[] biomes1 = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.OCEAN));
//			biomes1 = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.NETHER));
//			EntityRegistry.addSpawn(entityClass, i, 4, 4, EnumCreatureType.waterCreature, biomes);
//			EntityRegistry.addSpawn(entityClass, i, 1, 2, EnumCreatureType.creature, biomes1);
//		}
//	}
//}

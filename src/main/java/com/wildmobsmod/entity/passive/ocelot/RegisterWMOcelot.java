//package com.wildmobsmod.entity.passive.ocelot;
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
//public class RegisterWMOcelot
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
//		createEntity(EntityWMOcelot.class, "Cat", 0xB5B5B5, 0x965A3E);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		boolean flag = WildMobsMod.enableImprovedCats;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 25, WildMobsMod.modInstance, 82, 3, true);
//
//			BiomeGenBase[] biomes = new BiomeGenBase[0];
//			biomes = ArrayUtils.addAll(biomes, BiomeDictionary.getBiomesForType(Type.FOREST));
//			EntityRegistry.addSpawn(entityClass, 2, 1, 1, EnumCreatureType.creature, biomes);
//		}
//	}
//}

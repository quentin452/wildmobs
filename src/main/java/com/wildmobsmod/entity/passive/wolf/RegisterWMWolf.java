//package com.wildmobsmod.entity.passive.wolf;
//
//import com.wildmobsmod.main.WildMobsMod;
//
//import cpw.mods.fml.common.registry.EntityRegistry;
//
//public class RegisterWMWolf
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
//		createEntity(EntityWMWolf.class, "Wolf", 0xB5B5B5, 0x965A3E);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		boolean flag = WildMobsMod.enableImprovedWolves;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 18, WildMobsMod.modInstance, 82, 3, true);
//		}
//	}
//}

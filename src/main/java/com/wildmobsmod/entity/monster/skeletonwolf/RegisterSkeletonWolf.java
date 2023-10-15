//package com.wildmobsmod.entity.monster.skeletonwolf;
//
//import com.wildmobsmod.main.WildMobsMod;
//
//import cpw.mods.fml.common.registry.EntityRegistry;
//
//public class RegisterSkeletonWolf
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
//		createEntity(EntitySkeletonWolf.class, "SkeletonWolf", 0x775943, 0xCFC6C0);
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		boolean flag = WildMobsMod.enableSkeletonWolf;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 21, WildMobsMod.modInstance, 82, 3, true);
//		}
//	}
//}

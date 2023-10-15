//package com.wildmobsmod.entity.projectile.lavaspit;
//
//import com.wildmobsmod.main.WildMobsMod;
//
//import cpw.mods.fml.common.registry.EntityRegistry;
//
//public class RegisterLavaSpit
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
//		boolean flag = WildMobsMod.enableMagmaPlant;
//		if(flag)
//		{
//			createEntity(EntityLavaSpit.class, "LavaSpit", 0xFFFFFF, 0xFFFFFF);
//		}
//
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		EntityRegistry.registerModEntity(entityClass, entityName, 16, WildMobsMod.modInstance, 82, 1, true);
//	}
//}

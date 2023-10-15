//package com.wildmobsmod.entity.monster.wizard;
//
//import com.wildmobsmod.main.WildMobsMod;
//
//import cpw.mods.fml.common.registry.EntityRegistry;
//
//public class RegisterWizard
//{
//
//	public static void mainRegistry()
//	{
//		registerModEntity();
//	}
//
//	public static void registerModEntity()
//	{
//		createEntity(EntityWizard.class, "Wizard", 0x4F5678, 0xB5A59B);
//	}
//
//	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
//	{
//		boolean flag = WildMobsMod.enableWizard;
//		if(flag)
//		{
//			EntityRegistry.registerModEntity(entityClass, entityName, 6, WildMobsMod.modInstance, 82, 3, true);
//		}
//	}
//}

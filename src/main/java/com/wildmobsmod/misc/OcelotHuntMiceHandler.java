package com.wildmobsmod.misc;

import com.wildmobsmod.entity.passive.mouse.EntityMouse;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class OcelotHuntMiceHandler
{
	@SubscribeEvent
	public void onOcelotSpawn(EntityJoinWorldEvent event)
	{
		Entity entity = event.entity;
		if(entity != null && !entity.worldObj.isRemote && entity.getClass() == EntityOcelot.class)
		{
			((EntityLiving) entity).targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature) entity, EntityMouse.class, 200, true));
		}
	}
}

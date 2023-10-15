package com.wildmobsmod.misc;

import com.wildmobsmod.entity.passive.ocelot.EntityWMOcelot;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class CreeperAvoidWMOcelotHandler
{
	@SubscribeEvent
	public void onCreeperSpawn(EntityJoinWorldEvent event)
	{
		Entity entity = event.entity;
		if(entity != null && !entity.worldObj.isRemote && entity.getClass() == EntityCreeper.class)
		{
			((EntityLiving) entity).tasks.addTask(3, new EntityAIAvoidEntity((EntityCreature) entity, EntityWMOcelot.class, 6.0F, 1.0D, 1.2D));
		}
	}
}

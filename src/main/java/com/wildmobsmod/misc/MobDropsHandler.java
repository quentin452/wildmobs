package com.wildmobsmod.misc;

import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class MobDropsHandler
{
	@SubscribeEvent
	public void onSquidDrops(LivingDropsEvent event)
	{
		if(event.entity instanceof EntitySquid)
		{
			World world = event.entity.worldObj;
			int j = world.rand.nextInt(2) + 1 + world.rand.nextInt(1);
			event.drops.add(new EntityItem(world, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(event.entity.isBurning() ? WildMobsModItems.rawCalamari : WildMobsModItems.cookedCalamari, 1, j)));
		}
	}
}

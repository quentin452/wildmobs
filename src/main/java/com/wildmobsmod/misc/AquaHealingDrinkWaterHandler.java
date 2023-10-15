package com.wildmobsmod.misc;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class AquaHealingDrinkWaterHandler
{
	@SubscribeEvent
	public void OnFinnishDrinking(PlayerUseItemEvent.Finish event)
	{
		EntityLivingBase living = event.entityLiving;
		ItemStack stack = event.item;
		Item item;
		int meta;
		if(living != null && !living.worldObj.isRemote && stack != null && (item = stack.getItem()) == Items.potionitem && ((meta = item.getDamage(stack)) == 0 || meta == 64 || meta == 16) && living.isPotionActive(WildMobsMod.aquaHealingID) && !living.isWet())
		{
			if(living.getHealth() < living.getMaxHealth() - 1)
			{
				living.heal(2.0F);
			}
			else
			{
				if(living.getHealth() < living.getMaxHealth())
				{
					living.heal(1.0F);
				}
			}
		}
	}
}

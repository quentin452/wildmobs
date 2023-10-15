package com.wildmobsmod.items;

import com.wildmobsmod.entity.projectile.seascorpionegg.EntitySeaScorpionEgg;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSeaScorpionEgg extends ItemWM
{
	public ItemSeaScorpionEgg()
	{
		this.setMaxStackSize(16);
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
		}

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if(!world.isRemote && world.rand.nextBoolean())
		{
			world.spawnEntityInWorld(new EntitySeaScorpionEgg(world, player));
		}

		return stack;
	}
}

package com.wildmobsmod.items;

import com.wildmobsmod.entity.monster.dreath.mired.EntityMiredSummoner;
import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemMiredBottle extends ItemWM
{
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!WildMobsMod.DREATH_MIRED_CONFIG.getEnableMiredBottle()) return false;
		if(!world.isRemote)
		{
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];
			double yOffset = (side == 1 && world.getBlock(x, y, z).getRenderType() == 11) ? 0.5D : 0.0D;
			EntityMiredSummoner miredSummoner = new EntityMiredSummoner(world);
			miredSummoner.setLocationAndAngles((double) x + 0.5D, (double) y + yOffset, (double) z + 0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
			world.spawnEntityInWorld(miredSummoner);
			miredSummoner.setLifeTime(EntityMiredSummoner.EXPECTED_LIFETIME);
			if(!player.capabilities.isCreativeMode)
			{
				if(stack.stackSize-- == 1)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.glass_bottle));
				}
				else if(!player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle)))
				{
					player.dropPlayerItemWithRandomChoice(new ItemStack(Items.glass_bottle, 1, 0), false);
				}
			}
		}
		return true;
	}
}

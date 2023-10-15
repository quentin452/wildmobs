package com.wildmobsmod.items;

import com.wildmobsmod.entity.monster.magmaplant.EntityMagmaPlant;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemMagmaPlantSeed extends ItemWM
{
	public ItemMagmaPlantSeed()
	{
		this.setMaxStackSize(16);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote) return true;
		x += Facing.offsetsXForSide[side];
		y += Facing.offsetsYForSide[side];
		z += Facing.offsetsZForSide[side];
		double yOffset = (side == 1 && world.getBlock(x, y, z).getRenderType() == 11) ? 0.5D : 0.0D;
		Block block = world.getBlock(x, y - 1, z);
		if(block == Blocks.netherrack || block == Blocks.quartz_ore)
		{
			EntityMagmaPlant magmaPlant = new EntityMagmaPlant(world);
			magmaPlant.setLocationAndAngles((double) x + 0.5D, (double) y + yOffset, (double) z + 0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
			if(magmaPlant.worldObj.checkNoEntityCollision(magmaPlant.boundingBox))
			{
				magmaPlant.rotationYawHead = magmaPlant.rotationYaw;
				magmaPlant.renderYawOffset = magmaPlant.rotationYaw;
				magmaPlant.onSpawnWithEgg((IEntityLivingData) null);
				magmaPlant.setStage(0);
				magmaPlant.setGrowingAge(world.rand.nextInt(10000) + 10000);
				magmaPlant.setIsWild(false);
				world.spawnEntityInWorld(magmaPlant);
				stack.stackSize--;
				return true;
			}
		}
		return false;
	}
}

package com.wildmobsmod.items;

import com.wildmobsmod.blocks.WildMobsModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemArmadilloShellBlockSlab extends ItemSlab
{
	public ItemArmadilloShellBlockSlab(Block block)
	{
		super(block, WildMobsModBlocks.armadilloShellBlockSlabSingle, WildMobsModBlocks.armadilloShellBlockSlabDouble, false);
	}
}

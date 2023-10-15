package com.wildmobsmod.blocks;

import java.util.List;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCarpet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockLeatherCarpet extends BlockCarpet
{
	public BlockLeatherCarpet()
	{
		super();
		this.setCreativeTab(WildMobsMod.TAB_WILDMOBS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return WildMobsModBlocks.leatherBlock.getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
		list.add(new ItemStack(item, 1, 0));
    }
}

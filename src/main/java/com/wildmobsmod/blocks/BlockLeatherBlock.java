package com.wildmobsmod.blocks;

import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockLeatherBlock extends Block
{
	public BlockLeatherBlock()
	{
		super(Material.cloth);
		this.setCreativeTab(WildMobsMod.TAB_WILDMOBS);
		this.setHardness(0.8F);
		this.setResistance(4F);
		this.setStepSound(soundTypeCloth);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon("wildmobsmod:leather_block");
	}
}

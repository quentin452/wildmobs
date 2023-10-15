package com.wildmobsmod.blocks;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockArmadilloShellBlock extends Block
{

	public BlockArmadilloShellBlock()
	{
		super(Material.rock);
		this.setCreativeTab(WildMobsMod.TAB_WILDMOBS);
		this.setHardness(1.5F);
		this.setResistance(45F);
		this.setStepSound(soundTypeStone);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon(this.getTextureName());
	}
}

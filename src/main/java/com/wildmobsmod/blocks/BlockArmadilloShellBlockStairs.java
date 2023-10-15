package com.wildmobsmod.blocks;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockArmadilloShellBlockStairs extends BlockStairs
{
	/**
	 * @param base ignored
	 */
	protected BlockArmadilloShellBlockStairs(Block base, int meta)
	{
		super(WildMobsModBlocks.armadilloShellBlock, meta);
		this.setCreativeTab(WildMobsMod.TAB_WILDMOBS);
		this.setHardness(1.5F);
		this.setResistance(45F);
		this.setStepSound(soundTypeStone);
		this.setLightOpacity(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(this.getTextureName());
	}
}

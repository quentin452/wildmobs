package com.wildmobsmod.blocks;

import java.util.Random;

import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockBladderBlockStage1 extends Block
{
	public BlockBladderBlockStage1()
	{
		super(Material.gourd);
		this.setTickRandomly(true);
		this.setCreativeTab(WildMobsMod.TAB_WILDMOBS);
		this.setHardness(1F);
		this.setResistance(5F);
		this.setStepSound(soundTypeCloth);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.875D, z + 1);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e)
	{
		e.motionX *= 0.9D;
		e.motionZ *= 0.9D;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon("wildmobsmod:bladder_block_damaged_1");
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rng)
	{
		super.updateTick(world, x, y, z, rng);
		world.setBlock(x, y, z, WildMobsModBlocks.bladderBlockStage2, 0, 2);
	}
}

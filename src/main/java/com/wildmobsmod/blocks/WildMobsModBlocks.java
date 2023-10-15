package com.wildmobsmod.blocks;

import com.wildmobsmod.items.ItemArmadilloShellBlockSlab;
import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.util.RegistryNamespaced;

public class WildMobsModBlocks
{
	public static final RegistryNamespaced blockRegistry = GameData.getBlockRegistry();
	public static final Block.SoundType soundTypeCloth = new Block.SoundType("cloth", 1.0F, 1.0F);

	public static final Block bisonBlock = new BlockBisonBlock().setBlockName(WildMobsMod.MODID + ":bison_block");
	public static final Block leatherBlock = new BlockLeatherBlock().setBlockName(WildMobsMod.MODID + ":leather_block");
	public static final Block furBlock = new BlockFurBlock().setBlockName(WildMobsMod.MODID + ":fur_block");
	public static final Block furCarpet = new BlockFurCarpet().setHardness(0.1F).setStepSound(soundTypeCloth).setBlockName(WildMobsMod.MODID + ":fur_carpet").setLightOpacity(0);
	public static final Block leatherCarpet = new BlockLeatherCarpet().setHardness(0.1F).setStepSound(soundTypeCloth).setBlockName(WildMobsMod.MODID + ":leather_carpet").setLightOpacity(0);
	public static final Block bisonCarpet = new BlockBisonCarpet().setHardness(0.1F).setStepSound(soundTypeCloth).setBlockName(WildMobsMod.MODID + ":bison_carpet").setLightOpacity(0);
	public static final Block bladderBlockStage0 = new BlockBladderBlockStage0().setBlockName(WildMobsMod.MODID + ":bladder_block_damaged_0");
	public static final Block bladderBlockStage1 = new BlockBladderBlockStage1().setBlockName(WildMobsMod.MODID + ":bladder_block_damaged_1");
	public static final Block bladderBlockStage2 = new BlockBladderBlockStage2().setBlockName(WildMobsMod.MODID + ":bladder_block_damaged_2");
	public static final Block armadilloShellBlock = new BlockArmadilloShellBlock().setBlockName(WildMobsMod.MODID + ":armadillo_shell_block").setBlockTextureName(WildMobsMod.MODID + ":armadillo_shell_block");
	public static final Block armadilloShellBlockStairs = new BlockArmadilloShellBlockStairs(armadilloShellBlock, 0).setBlockName(WildMobsMod.MODID + ":armadillo_shell_block_stairs").setBlockTextureName(WildMobsMod.MODID + ":armadillo_shell_block");
	public static final BlockSlab armadilloShellBlockSlabSingle = (BlockSlab) new BlockArmadilloShellBlockSlab(false).setBlockName(WildMobsMod.MODID + ":armadillo_shell_block_slab").setBlockTextureName(WildMobsMod.MODID + ":armadillo_shell_block");
	public static final BlockSlab armadilloShellBlockSlabDouble = (BlockSlab) new BlockArmadilloShellBlockSlab(true).setBlockName(WildMobsMod.MODID + ":armadillo_shell_block_slab").setBlockTextureName(WildMobsMod.MODID + ":armadillo_shell_block");
	public static final Block armadilloShellBlockCracked = new BlockArmadilloShellBlock().setBlockName(WildMobsMod.MODID + ":armadillo_shell_block_cracked").setBlockTextureName(WildMobsMod.MODID + ":armadillo_shell_block_cracked");
	public static final Block armadilloShellBlockPatterned = new BlockArmadilloShellBlock().setBlockName(WildMobsMod.MODID + ":armadillo_shell_block_patterned").setBlockTextureName(WildMobsMod.MODID + ":armadillo_shell_block_patterned");

	public static void registerBlocks()
	{
		GameRegistry.registerBlock(bisonBlock, "bisonBlock");
		GameRegistry.registerBlock(bisonCarpet, "bisonCarpet");
		GameRegistry.registerBlock(leatherBlock, "leatherBlock");
		GameRegistry.registerBlock(leatherCarpet, "leatherCarpet");
		GameRegistry.registerBlock(furBlock, "furBlock");
		GameRegistry.registerBlock(furCarpet, "furCarpet");
		GameRegistry.registerBlock(bladderBlockStage0, "bladderBlockStage0");
		GameRegistry.registerBlock(bladderBlockStage1, "bladderBlockStage1");
		GameRegistry.registerBlock(bladderBlockStage2, "bladderBlockStage2");
		GameRegistry.registerBlock(armadilloShellBlock, "armadilloShellBlock");
		GameRegistry.registerBlock(armadilloShellBlockStairs, "armadilloShellBlockStairs");
		GameRegistry.registerBlock(armadilloShellBlockSlabSingle, ItemArmadilloShellBlockSlab.class, "armadilloShellBlockSlabSingle");
		GameRegistry.registerBlock(armadilloShellBlockSlabDouble, ItemArmadilloShellBlockSlab.class, "armadilloShellBlockSlabDouble");
		GameRegistry.registerBlock(armadilloShellBlockCracked, "armadilloShellBlockCracked");
		GameRegistry.registerBlock(armadilloShellBlockPatterned, "armadilloShellBlockPatterned");
	}
}

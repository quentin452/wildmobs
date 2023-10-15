package com.wildmobsmod.items;

import java.util.List;

import com.wildmobsmod.entity.passive.jellyfish.EntityJellyfish;
import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ItemJellyfishBucket extends ItemWM
{
	public static final String[] variants = new String[] { "orange", "blue", "pink", "red", "yellow", "white", "nether" };
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	private boolean nether;

	public ItemJellyfishBucket(boolean nether)
	{
		this.nether = nether;
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg)
	{
		int j = MathHelper.clamp_int(dmg, 0, variants.length - 1);
		return this.icons[j];
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, variants.length - 1);
		return super.getUnlocalizedName() + "." + variants[i];
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < 6; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.icons = new IIcon[variants.length];

		for(int i = 0; i < variants.length; ++i)
		{
			this.icons[i] = reg.registerIcon(this.getIconString() + "_" + variants[i]);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);
        if(mop.typeOfHit == MovingObjectType.BLOCK)
        {
    		Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
    		if(block.getMaterial() == (this.nether ? Material.lava : Material.water) && block instanceof BlockLiquid)
    		{
    			if(!world.isRemote)
    			{
    	    		int type = stack.getItemDamage();
    	    		EntityJellyfish jellyfish = new EntityJellyfish(world);
    	    		jellyfish.setLocationAndAngles(mop.blockX + 0.5D, mop.blockY, mop.blockZ + 0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
    	    		jellyfish.rotationYawHead = jellyfish.rotationYaw;
    	    		jellyfish.renderYawOffset = jellyfish.rotationYaw;
    	    		jellyfish.onSpawnWithEgg((IEntityLivingData) null);
    	    		jellyfish.setSkin(type);
    	    		jellyfish.setNether(this.nether);
    				if(stack.hasDisplayName())
    				{
    					jellyfish.setCustomNameTag(stack.getDisplayName());
    				}
    				world.spawnEntityInWorld(jellyfish);
    			}
    			return new ItemStack(this.nether ? Items.lava_bucket : Items.water_bucket);
    		}
        }
        return stack;
	}
}

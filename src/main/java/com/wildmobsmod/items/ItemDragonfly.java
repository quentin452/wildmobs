package com.wildmobsmod.items;

import java.util.List;

import com.wildmobsmod.entity.passive.dragonfly.EntityDragonfly;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemDragonfly extends ItemWM
{
	public static final String[] variants = new String[] { "blue", "cyan", "green", "orange", "red", "yellow" };
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemDragonfly()
	{
		this.maxStackSize = 1;
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

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote) return true;
		
		int type = stack.getItemDamage();
		Block block = world.getBlock(x, y, z);
		x += Facing.offsetsXForSide[side];
		y += Facing.offsetsYForSide[side];
		z += Facing.offsetsZForSide[side];
		double yOffset = side == 1 && block.getRenderType() == 11 ? 0.5D : 0.0D;
		EntityDragonfly entityliving = new EntityDragonfly(world);
		entityliving.setLocationAndAngles((double) x + 0.5D, (double) y + yOffset, (double) z + 0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
		entityliving.rotationYawHead = entityliving.rotationYaw;
		entityliving.renderYawOffset = entityliving.rotationYaw;
		entityliving.onSpawnWithEgg((IEntityLivingData) null);
		entityliving.setSkin(type);
		if(stack.hasDisplayName())
		{
			entityliving.setCustomNameTag(stack.getDisplayName());
		}
		world.spawnEntityInWorld(entityliving);
		stack.stackSize--;
		return true;
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
}

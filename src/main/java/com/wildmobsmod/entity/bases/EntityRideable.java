package com.wildmobsmod.entity.bases;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityRideable extends EntityAnimal
{
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(21, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(22, Byte.valueOf((byte) 0));
	}

	public EntityRideable(World world)
	{
		super(world);
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Saddle", this.getSaddled());
		nbt.setBoolean("IsTame", this.getSaddled());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSaddled(nbt.getBoolean("Saddle"));
		this.setSaddled(nbt.getBoolean("IsTame"));
	}

	public boolean getSaddled()
	{
		return (this.dataWatcher.getWatchableObjectByte(21) & 1) != 0;
	}

	public void setSaddled(boolean flag)
	{
		this.dataWatcher.updateObject(21, Byte.valueOf((byte) (flag ? 1 : 0)));
	}

	public boolean getTamed()
	{
		return (this.dataWatcher.getWatchableObjectByte(22) & 1) != 0;
	}

	public void setTamed(boolean flag)
	{
		this.dataWatcher.updateObject(22, Byte.valueOf((byte) (flag ? 1 : 0)));
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte mod)
	{
		switch(mod)
		{
			case 7 : this.playTameEffect(true); break;
			case 6 : this.playTameEffect(false); break;
			default : super.handleHealthUpdate(mod); break;
		}
	}

	protected void playTameEffect(boolean flag)
	{
		String particleName = flag ? "heart" : "smoke";
		for(int i = 0; i < 7; ++i)
		{
			double x = this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
			double y = this.posY + 0.5D + (double) (this.rand.nextFloat() * this.height);
			double z = this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
			this.worldObj.spawnParticle(particleName, x, y, z, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D);
		}
	}
}

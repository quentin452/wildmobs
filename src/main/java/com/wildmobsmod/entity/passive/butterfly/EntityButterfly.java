package com.wildmobsmod.entity.passive.butterfly;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityButterfly extends EntityAmbientCreature implements ISkinnedEntity
{
	private ChunkCoordinates cachedPosition;

	public EntityButterfly(World world)
	{
		super(world);
		this.setSize(0.5F, 0.3F);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.BUTTERFLY_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		super.onSpawnWithEgg(data);
		this.setSkin(this.worldObj.rand.nextInt(14));
		return data;
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", this.getSkin());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("Variant"));
	}

	public int getSkin()
	{
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	public void setSkin(int skinId)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) skinId));
	}

	protected String getHurtSound()
	{
		return null;
	}

	protected String getDeathSound()
	{
		return null;
	}

	public boolean canBePushed()
	{
		return false;
	}

	protected void collideWithEntity(Entity entity) {}

	protected void collideWithNearbyEntities() {}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	public void onUpdate()
	{
		super.onUpdate();

		this.motionY *= 0.6D; // fly
	}

	protected void updateAITasks()
	{
		super.updateAITasks();

		if(this.cachedPosition != null && (!this.worldObj.isAirBlock(this.cachedPosition.posX, this.cachedPosition.posY, this.cachedPosition.posZ) || this.cachedPosition.posY < 1))
		{
			this.cachedPosition = null;
		}

		if(this.cachedPosition == null || this.rand.nextInt(30) == 0 || this.cachedPosition.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0F)
		{
			this.cachedPosition = new ChunkCoordinates((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = (double) this.cachedPosition.posX + 0.5D - this.posX;
		double d1 = (double) this.cachedPosition.posY + 0.1D - this.posY;
		double d2 = (double) this.cachedPosition.posZ + 0.5D - this.posZ;
		this.motionX += (Math.signum(d0) * 0.3D - this.motionX) * 0.15D;
		this.motionY += (Math.signum(d1) * 0.7D - this.motionY) * 0.15D;
		this.motionZ += (Math.signum(d2) * 0.3D - this.motionZ) * 0.15D;
		float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
		float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
		this.moveForward = 0.5F;
		this.rotationYaw += f1;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected void fall(float height) {}

	protected void updateFallState(double distanceFallenThisTick, boolean onGround) {}

	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		int i = this.getSkin();

		if(super.interact(player))
		{
			return true;
		}
		else if(itemstack != null && itemstack.getItem() == WildMobsModItems.bugNet)
		{
			ItemStack itemstack1 = new ItemStack(WildMobsModItems.butterfly, 1, i);
			if(!player.inventory.addItemStackToInventory(itemstack1))
			{
				player.dropPlayerItemWithRandomChoice(itemstack1, false);
				this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
				itemstack.damageItem(1, player);
				this.isDead = true;
				return true;
			}
			else
			{
				this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
				itemstack.damageItem(1, player);
				this.isDead = true;
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}

    public boolean getCanSpawnHere()
    {
        if (this.worldObj.rand.nextInt(2) == 0)
        {
            return false;
        }
        else
        {
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.boundingBox.minY);
            int k = MathHelper.floor_double(this.posZ);

            Block block = this.worldObj.getBlock(i, j - 1, k);
            int lightValue = this.worldObj.getBlockLightValue(i, j, k);

            boolean isDaytime = this.worldObj.isDaytime();
            boolean isOnGrass = block == Blocks.grass;

            return isDaytime && isOnGrass && lightValue > 8 && super.getCanSpawnHere();
        }
    }
}

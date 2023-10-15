package com.wildmobsmod.entity.monster.skeletonwolf;

import java.util.List;

import com.wildmobsmod.entity.ai.EntityAIFollowSkeleton;
import com.wildmobsmod.entity.ai.EntityAISkeletonHurtByTarget;
import com.wildmobsmod.entity.ai.EntityAISkeletonHurtTarget;
import com.wildmobsmod.entity.ai.EntityAIWatchSkeleton;
import com.wildmobsmod.entity.monster.dreath.EntityDreath;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class EntitySkeletonWolf extends EntityMob
{
	// This determines the mob the skeleton wolf should follow. The mob
	// automatically chooses the closest skeleton/dreath to it, and will start
	// to follow it.
	public EntityLiving entityToFollow;

	public final IEntitySelector followSkeleton = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity entity)
		{
			return entity.isEntityAlive() && EntitySkeletonWolf.this.getEntitySenses().canSee(entity) && (entity instanceof EntitySkeleton || entity instanceof EntityDreath);
		}
	};

	public EntitySkeletonWolf(World world)
	{
		super(world);
		this.setSize(0.6F, 0.8F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.4D, true));
		this.tasks.addTask(4, new EntityAIRestrictSun(this));
		this.tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(6, new EntityAIFollowSkeleton(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchSkeleton(this, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAISkeletonHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAISkeletonHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.experienceValue = 5;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, new Byte((byte) 0));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}

	protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}

	protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}

	protected void func_145780_a(int x, int y, int z, Block block)
	{
		this.playSound("mob.skeleton.step", 0.15F, 1.0F);
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	public float getEyeHeight()
	{
		return this.height * 0.8F;
	}

	public boolean attackEntityAsMob(Entity entity)
	{
		if(this.getSkeletonType() == 1 && entity instanceof EntityLivingBase)
		{
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 100));
		}
		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
	}

	public void onLivingUpdate()
	{
		if(this.worldObj.isDaytime() && !this.worldObj.isRemote)
		{
			float f = this.getBrightness(1.0F);

			if(f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				this.setFire(8);
			}
		}

		if(this.worldObj.isRemote && this.getSkeletonType() == 1)
		{
			this.setSize(0.72F, 0.96F);
		}
		else
		{
			this.setSize(0.6F, 0.8F);
		}

		if(this.entityToFollow != null && !this.worldObj.isRemote)
		{
			if(!this.entityToFollow.isEntityAlive())
			{
				this.entityToFollow = null;
			}
		}

		if(this.entityToFollow == null && !this.worldObj.isRemote)
		{
			this.findMobToFollow();
		}

		super.onLivingUpdate();
	}

	protected void findMobToFollow()
	{
		List list = this.worldObj.selectEntitiesWithinAABB(EntityMob.class, this.boundingBox.expand(16.0D, 16.0D, 16.0D), this.followSkeleton);

		if(list.isEmpty())
		{
		}
		else if(this.entityToFollow == null)
		{
			this.entityToFollow = (EntityLiving) list.get(0);
		}
	}

	protected Item getDropItem()
	{
		return Items.bone;
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		data = super.onSpawnWithEgg(data);

		if(this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
		{
			this.setSkeletonType(1);
		}
		else
		{
			this.setSkeletonType(0);
		}

		return data;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		int j = this.rand.nextInt(3 + looting);

		for(int k = 0; k < j; ++k)
		{
			this.dropItem(Items.bone, 1);
		}

		if(this.getSkeletonType() == 1)
		{
			j = this.rand.nextInt(3 + looting) - 1;

			for(int k = 0; k < j; ++k)
			{
				this.dropItem(Items.coal, 1);
			}
		}
	}

	public int getSkeletonType()
	{
		return this.dataWatcher.getWatchableObjectByte(13);
	}

	public void setSkeletonType(int type)
	{
		this.dataWatcher.updateObject(13, Byte.valueOf((byte) type));
		this.isImmuneToFire = type == 1;

		if(type == 1)
		{
			this.setSize(0.72F, 0.96F);
		}
		else
		{
			this.setSize(0.6F, 0.8F);
		}
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		if(nbt.hasKey("SkeletonType", 99))
		{
			byte b0 = nbt.getByte("SkeletonType");
			this.setSkeletonType(b0);
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setByte("SkeletonType", (byte) this.getSkeletonType());
	}
}

package com.wildmobsmod.entity.passive.direwolf;

import com.wildmobsmod.entity.ai.EntityAIDireWolfBeg;
import com.wildmobsmod.entity.passive.deer.EntityDeer;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityDireWolf extends EntityTameable
{
	private float dedication;
	private float interest;
	private boolean isShaking;
	private boolean shouldStartShaking;
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;

	public EntityDireWolf(World p_i1696_1_)
	{
		super(p_i1696_1_);
		this.setSize(0.8F, 1.1F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.5F));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.4D, true));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIDireWolfBeg(this, 8.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityPlayer.class, 180, false));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityHorse.class, 200, false));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityDeer.class, 200, false));
		this.setTamed(false);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.DIREWOLF_CONFIG.getMaxPackSize();
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
		setHealthAttribute();
	}
	
	protected void setHealthAttribute()
	{
		if(this.isTamed())
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		}
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public void setAttackTarget(EntityLivingBase target)
	{
		super.setAttackTarget(target);

		if(target == null)
		{
			this.setAngry(false);
		}
		else if(!this.isTamed())
		{
			this.setAngry(true);
		}
	}

	protected void updateAITick()
	{
		this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(18, new Float(this.getHealth()));
		this.dataWatcher.addObject(19, new Byte((byte) 0));
		this.dataWatcher.addObject(20, new Byte((byte) BlockColored.func_150032_b(1)));
	}

	// step sound
	protected void func_145780_a(int x, int y, int z, Block stepBlock)
	{
		this.playSound("mob.wolf.step", 0.15F, 1.0F);
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Angry", this.isAngry());
		nbt.setByte("CollarColor", (byte) this.getCollarColor());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setAngry(nbt.getBoolean("Angry"));

		if(nbt.hasKey("CollarColor", 99))
		{
			this.setCollarColor(nbt.getByte("CollarColor"));
		}
	}

	protected String getLivingSound()
	{
		return this.isAngry() ? "mob.wolf.growl" : (this.rand.nextInt(3) == 0 ? (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
	}

	protected String getHurtSound()
	{
		return "mob.wolf.hurt";
	}

	protected String getDeathSound()
	{
		return "mob.wolf.death";
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	protected Item getDropItem()
	{
        return Item.getItemById(-1);
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(!this.worldObj.isRemote && this.isShaking && !this.shouldStartShaking && !this.hasPath() && this.onGround)
		{
			this.shouldStartShaking = true;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
			this.worldObj.setEntityState(this, (byte) 8);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();
		this.interest = this.dedication;

		if(this.isBegging())
		{
			this.dedication += (1.0F - this.dedication) * 0.4F;
		}
		else
		{
			this.dedication += (0.0F - this.dedication) * 0.4F;
		}

		if(this.isBegging())
		{
			this.numTicksToChaseTarget = 10;
		}

		if(this.isWet())
		{
			this.isShaking = true;
			this.shouldStartShaking = false;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		}
		else if(this.shouldStartShaking)
		{
			if(this.timeWolfIsShaking == 0.0F)
			{
				this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
			this.timeWolfIsShaking += 0.05F;

			if(this.prevTimeWolfIsShaking >= 2.0F)
			{
				this.isShaking = false;
				this.shouldStartShaking = false;
				this.prevTimeWolfIsShaking = 0.0F;
				this.timeWolfIsShaking = 0.0F;
			}

			if(this.timeWolfIsShaking > 0.4F)
			{
				float f = (float) this.boundingBox.minY;
				int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

				for(int j = 0; j < i; ++j)
				{
					float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					this.worldObj.spawnParticle("splash", this.posX + (double) f1, (double) (f + 0.8F), this.posZ + (double) f2, this.motionX, this.motionY, this.motionZ);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean getWolfShaking()
	{
		return this.isShaking;
	}

	@SideOnly(Side.CLIENT)
	public float getShadingWhileShaking(float partialTickTime)
	{
		return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * partialTickTime) / 2.0F * 0.25F;
	}

	@SideOnly(Side.CLIENT)
	public float getShakeAngle(float f1, float f2)
	{
		float f3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * f1 + f2) / 1.8F;

		if(f3 < 0.0F)
		{
			f3 = 0.0F;
		}
		else if(f3 > 1.0F)
		{
			f3 = 1.0F;
		}

		return MathHelper.sin(f3 * (float) Math.PI) * MathHelper.sin(f3 * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
	}

	public float getEyeHeight()
	{
		return this.height * 0.8F;
	}

	@SideOnly(Side.CLIENT)
	public float getInterestedAngle(float f)
	{
		return (this.interest + (this.dedication - this.interest) * f) * 0.08F * (float) Math.PI;
	}

	public int getVerticalFaceSpeed()
	{
		return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			Entity entity = source.getEntity();
			this.aiSit.setSitting(false);

			if(entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
			{
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean attackEntityAsMob(Entity target)
	{
		int dmg = this.isTamed() ? 10 : worldObj.difficultySetting == EnumDifficulty.HARD ? 7 : worldObj.difficultySetting == EnumDifficulty.NORMAL ? 5 : 4;
		return target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) dmg);
	}

	public void setTamed(boolean flag)
	{
		super.setTamed(flag);
		setHealthAttribute();
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if(this.isTamed())
		{
			if(itemstack != null)
			{
				if(itemstack.getItem() instanceof ItemFood)
				{
					ItemFood itemfood = (ItemFood) itemstack.getItem();

					if(itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < getMaxHealth())
					{
						if(!player.capabilities.isCreativeMode)
						{
							--itemstack.stackSize;
						}

						this.heal((float) itemfood.func_150905_g(itemstack));

						if(itemstack.stackSize <= 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
						}

						return true;
					}
				}
				else if(itemstack.getItem() == Items.dye)
				{
					int i = BlockColored.func_150032_b(itemstack.getItemDamage());

					if(i != this.getCollarColor())
					{
						this.setCollarColor(i);

						if(!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
						}

						return true;
					}
				}
			}

			if(this.func_152114_e(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack))
			{
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.setPathToEntity((PathEntity) null);
				this.setTarget((Entity) null);
				this.setAttackTarget((EntityLivingBase) null);
			}
		}
		else if(itemstack != null && itemstack.getItem() == WildMobsModItems.thickBone && !this.isAngry())
		{
			if(!player.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;
			}

			if(itemstack.stackSize <= 0)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
			}

			if(!this.worldObj.isRemote)
			{
				if(this.rand.nextInt(4) == 0)
				{
					this.setTamed(true);
					this.setPathToEntity((PathEntity) null);
					this.setAttackTarget((EntityLivingBase) null);
					this.aiSit.setSitting(true);
					this.setHealth(getMaxHealth());
					this.func_152115_b(player.getUniqueID().toString());
					this.playTameEffect(true);
					this.worldObj.setEntityState(this, (byte) 7);
					player.addStat(WildMobsMod.achievementTameDireWolf, 1);
				}
				else
				{
					this.playTameEffect(false);
					this.worldObj.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}
		if(itemstack != null && itemstack.getItem() == WildMobsModItems.direWolfSpawnEgg && !this.worldObj.isRemote)
		{
			EntityDireWolf entityageable = this.createChild(this);
			entityageable.setGrowingAge(-24000);
			entityageable.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
			worldObj.spawnEntityInWorld(entityageable);

			if(itemstack.hasDisplayName())
			{
				entityageable.setCustomNameTag(itemstack.getDisplayName());
			}

			if(!player.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;

				if(itemstack.stackSize <= 0)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
				}
			}
			return true;
		}

		return super.interact(player);
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte b)
	{
		if(b == 8)
		{
			this.shouldStartShaking = true;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		}
		else
		{
			super.handleHealthUpdate(b);
		}
	}

	@SideOnly(Side.CLIENT)
	public float getTailRotation()
	{
		return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (getMaxHealth() - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * (float) Math.PI : ((float) Math.PI / 5F));
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof ItemFood && ((ItemFood) stack.getItem()).isWolfsFavoriteMeat();
	}

	public boolean isAngry()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	public void setAngry(boolean flag)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);

		if(flag)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
		}
		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
		}
	}

	public int getCollarColor()
	{
		return this.dataWatcher.getWatchableObjectByte(20) & 15;
	}

	public void setCollarColor(int color)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) (color & 15)));
	}

	public EntityDireWolf createChild(EntityAgeable entity)
	{
		EntityDireWolf entitydirewolf = new EntityDireWolf(this.worldObj);
		String s = this.func_152113_b();

		if(s != null && s.trim().length() > 0)
		{
			entitydirewolf.func_152115_b(s);
			entitydirewolf.setTamed(true);
		}

		return entitydirewolf;
	}

	public void setBegging(boolean flag)
	{
		if(flag)
		{
			this.dataWatcher.updateObject(19, Byte.valueOf((byte) 1));
		}
		else
		{
			this.dataWatcher.updateObject(19, Byte.valueOf((byte) 0));
		}
	}

	public boolean isBegging()
	{
		return this.dataWatcher.getWatchableObjectByte(19) == 1;
	}

	public boolean canMateWith(EntityAnimal entity)
	{
		if(entity == this || !this.isTamed() || !(entity instanceof EntityDireWolf))
		{
			return false;
		}
		else
		{
			EntityDireWolf direwolf = (EntityDireWolf) entity;
			return direwolf.isTamed() && !direwolf.isSitting() && this.isInLove() && direwolf.isInLove();
		}
	}

	protected boolean canDespawn()
	{
		return !this.isTamed() && this.ticksExisted > 2400;
	}

	/**
	 * Should attack target to protect owner?
	 */
	public boolean func_142018_a(EntityLivingBase target, EntityLivingBase owner)
	{
		if(!(target instanceof EntityCreeper) && !(target instanceof EntityGhast))
		{
			if(target instanceof EntityDireWolf)
			{
				EntityDireWolf direwolf = (EntityDireWolf) target;
				if(direwolf.isTamed() && direwolf.getOwner() == owner) return false;
			}
			return target instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target) ? false : !(target instanceof EntityHorse) || !((EntityHorse) target).isTame();
		}
		return false;
	}
}

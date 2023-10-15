package com.wildmobsmod.entity.monster.dreath;

import java.util.Calendar;
import java.util.UUID;

import com.wildmobsmod.entity.monster.dreath.mired.EntityMired;
import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;

public class EntityDreath extends EntityMob implements IRangedAttackMob
{
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 0, 20.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);

	private int summonTimer;
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.4D, 1);

	public EntityDreath(World world)
	{
		super(world);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIRestrictSun(this));
		this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.setSize(0.66F, 1.98F);
		if(world != null && !world.isRemote)
		{
			this.setCombatTask();
		}
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.DREATH_MIRED_CONFIG.getMaxPackSize();
	}
	
	protected int getExperiencePoints(EntityPlayer player)
	{
		if(this.isChild())
		{
			this.experienceValue = (int) ((float) this.experienceValue * 2.5F);
		}

		return super.getExperiencePoints(player);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(12, new Byte((byte) 0));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected String getLivingSound()
	{
		return "wildmobsmod:mob.dreath.say";
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:mob.dreath.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:mob.dreath.death";
	}

	protected void func_145780_a(int x, int y, int z, Block block)
	{
		this.playSound("wildmobsmod:mob.dreath.step", 0.15F, 1.0F);
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		if(nbt.getBoolean("IsBaby"))
		{
			this.setChild(true);
		}

		this.setCombatTask();
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		if(this.isChild())
		{
			nbt.setBoolean("IsBaby", true);
		}
	}

	public boolean isChild()
	{
		return this.getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	public void setChild(boolean flag)
	{
		this.getDataWatcher().updateObject(12, Byte.valueOf((byte) (flag ? 1 : 0)));

		if(this.worldObj != null && !this.worldObj.isRemote)
		{
			IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			iattributeinstance.removeModifier(babySpeedBoostModifier);

			if(flag)
			{
				iattributeinstance.applyModifier(babySpeedBoostModifier);
			}
		}
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	public void onLivingUpdate()
	{
		if(this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild())
		{
			float f = this.getBrightness(1.0F);

			if(f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				boolean flag = true;
				ItemStack itemstack = this.getEquipmentInSlot(4);

				if(itemstack != null)
				{
					if(itemstack.isItemStackDamageable())
					{
						itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + this.rand.nextInt(2));

						if(itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage())
						{
							this.renderBrokenItemStack(itemstack);
							this.setCurrentItemOrArmor(4, (ItemStack) null);
						}
					}

					flag = false;
				}

				if(flag)
				{
					this.setFire(8);
				}
			}
		}

		ItemStack itemstack = this.getHeldItem();

		if(this.getAttackTarget() != null && !this.worldObj.isRemote)
		{
			if(this.summonTimer > 0)
			{
				this.summonTimer--;
			}
			else
			{
				if(this.isEntityAlive() == true && itemstack != null && (itemstack.getItem() == Items.stick || itemstack.getItem() == Items.bone || itemstack.getItem() == WildMobsModItems.thickBone))
				{
					this.summonTimer = 80;
					int i;
					int j;
					int k;
					int l;
					i = MathHelper.floor_double(this.posX);
					j = MathHelper.floor_double(this.posY + 2.5D);
					l = MathHelper.floor_double(this.posY + 2.0D);
					k = MathHelper.floor_double(this.posZ);
					int l1 = this.rand.nextInt(6);
					Block block = this.worldObj.getBlock(i + Facing.offsetsXForSide[l1], j + Facing.offsetsYForSide[l1], k + Facing.offsetsZForSide[l1]);
					Block block2 = this.worldObj.getBlock(i + Facing.offsetsXForSide[l1], l + Facing.offsetsYForSide[l1], k + Facing.offsetsZForSide[l1]);
					if(block == Blocks.air)
					{
						EntityMired entitymired = new EntityMired(this.worldObj);
						entitymired.setLocationAndAngles(this.posX, this.posY + 2.5D, this.posZ, this.rotationYaw, this.rotationPitch);
						worldObj.spawnEntityInWorld(entitymired);
						if(this.rand.nextInt(3) == 0)
						{
							EntityMired entitymired1 = new EntityMired(this.worldObj);
							entitymired1.setLocationAndAngles(this.posX, this.posY + 2.5D, this.posZ, this.rotationYaw, this.rotationPitch);
							worldObj.spawnEntityInWorld(entitymired1);
						}
					}
					else if(block2 == Blocks.air)
					{
						EntityMired entitymired = new EntityMired(this.worldObj);
						entitymired.setLocationAndAngles(this.posX, this.posY + 2.5D, this.posZ, this.rotationYaw, this.rotationPitch);
						worldObj.spawnEntityInWorld(entitymired);
						if(this.rand.nextInt(3) == 0)
						{
							EntityMired entitymired1 = new EntityMired(this.worldObj);
							entitymired1.setLocationAndAngles(this.posX, this.posY + 2.5D, this.posZ, this.rotationYaw, this.rotationPitch);
							worldObj.spawnEntityInWorld(entitymired1);
						}
					}
				}
			}
		}

		else if(this.worldObj.isRemote && this.isChild() == false)
		{
			this.setSize(0.6F, 1.8F);
		}
		else if(this.worldObj.isRemote && this.isChild() == true)
		{
			this.setSize(0.3F, 0.9F);
		}

		super.onLivingUpdate();
	}

	protected Item getDropItem()
	{
		return Items.bone;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		int j;
		int k;

		j = this.rand.nextInt(3 + looting);

		for(k = 0; k < j; ++k)
		{
			this.dropItem(Items.bone, 1);
		}

		if((playerkill || looting > 2) && this.rand.nextDouble() < WildMobsMod.DREATH_MIRED_CONFIG.getBottleDropChance())
		{
			this.dropItem(WildMobsModItems.miredBottle, 1);
		}
	}

	public boolean getCanSpawnHere()
	{
		if(this.worldObj.provider instanceof WorldProviderSurface)
		{
			return super.getCanSpawnHere();
		}
		else
		{
			return false;
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		data = super.onSpawnWithEgg(data);

		if(this.getRNG().nextInt(20) == 0 && WildMobsMod.DREATH_MIRED_CONFIG.getEnableBabyDreath())
		{
			this.setChild(true);
		}

		if(this.getRNG().nextInt(100) == 0 && this.getEquipmentInSlot(4) == null && !this.isChild())
		{
			this.setCurrentItemOrArmor(4, new ItemStack(Items.skull, 1, this.rand.nextInt(3) == 0 ? 4 : 2));
		}

		Calendar calendar = this.worldObj.getCurrentDate();

		if(calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
		{
			if(this.getEquipmentInSlot(4) == null)
			{
				this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
			}
			this.setCurrentItemOrArmor(0, new ItemStack(Items.bone));
			this.equipmentDropChances[4] = 0.0F;
		}
		else
		{
			this.setCurrentItemOrArmor(0, new ItemStack(Items.stick));
		}

		if(Math.random() * 100 < WildMobsMod.skeletonWolfChance)
		{
			EntitySkeletonWolf entityskeletonwolf = new EntitySkeletonWolf(this.worldObj);
			entityskeletonwolf.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			entityskeletonwolf.onSpawnWithEgg((IEntityLivingData) null);
			entityskeletonwolf.setSkeletonType(0);
			this.worldObj.spawnEntityInWorld(entityskeletonwolf);
			entityskeletonwolf.entityToFollow = this;
			if(this.isChild() == true)
			{
				this.mountEntity(entityskeletonwolf);
			}
		}

		return data;
	}

	public void setCombatTask()
	{
		this.tasks.removeTask(this.aiAttackOnCollide);
		this.tasks.removeTask(this.aiArrowAttack);
		ItemStack itemstack = this.getHeldItem();

		if(itemstack != null && (itemstack.getItem() == Items.stick || itemstack.getItem() == Items.bone || itemstack.getItem() == WildMobsModItems.thickBone))
		{
			this.tasks.addTask(4, this.aiArrowAttack);
		}
		else
		{
			this.tasks.addTask(4, this.aiAttackOnCollide);
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float damage) {}

	public double getYOffset()
	{
		if(this.isChild() == false)
		{
			return super.getYOffset() - 0.5D;
		}
		else
		{
			if(this.ridingEntity instanceof EntitySkeletonWolf)
			{
				return super.getYOffset() - 0.05D;
			}
			else
			{
				return super.getYOffset() - 0.2D;
			}
		}
	}

	public float getEyeHeight()
	{
		if(this.isChild() == false)
		{
			return this.height * 0.85F;
		}
		else
		{
			return this.height * 0.425F;
		}
	}

	public void updateRidden()
	{
		super.updateRidden();

		if(this.ridingEntity instanceof EntityCreature)
		{
			EntityCreature entitycreature = (EntityCreature) this.ridingEntity;
			this.renderYawOffset = entitycreature.renderYawOffset;
		}
	}

	public void setCurrentItemOrArmor(int slot, ItemStack armorStack)
	{
		super.setCurrentItemOrArmor(slot, armorStack);

		if(!this.worldObj.isRemote && slot == 0)
		{
			this.setCombatTask();
		}
	}
}

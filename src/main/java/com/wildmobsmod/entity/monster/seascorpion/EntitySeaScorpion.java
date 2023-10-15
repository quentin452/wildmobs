package com.wildmobsmod.entity.monster.seascorpion;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wildmobsmod.entity.bases.EntityMobTameable;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySeaScorpion extends EntityMobTameable
{
	//
	// This mob is completely finished and shouldn't be changed at all. There's
	// a lot of information about it on my Discord server.
	//
	
	// Changed to more resource-friendly spawn system

	private ChunkCoordinates cachedPosition;
	private Entity closestLivingEntity;
	private EntitySeaScorpion.Sorter theNearestAttackableTargetSorter;
	private int eatingTimer;
	public float seaScorpionPitch;
	public float prevSeaScorpionPitch;
	private float nextSeaScorpionPitch;
	private double prevPosY;
	private int timeToCalculatePosY;
	private double movedDist;
	private int timeToJump;
	
	private boolean canSpawnPack = true; // prevent pack members from spawning their own packs - only used for on-spawn handling

	private ItemStack food;

	public final IEntitySelector targetEntitySelectorMob = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity entity)
		{
			return entity.isEntityAlive() && EntitySeaScorpion.this.getEntitySenses().canSee(entity)
					&& EntitySeaScorpion.this.worldObj.getBlock(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ)).getMaterial() == Material.water && entity != EntitySeaScorpion.this
					&& !(entity instanceof EntitySeaScorpion) && !(entity instanceof EntityCreeper && !(entity instanceof EntityGhast) && ((EntityMob) entity).getActivePotionEffect(WildMobsMod.potionAquaHealing) == null);
		}
	};

	public final IEntitySelector targetEntitySelectorHuman = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity entity)
		{
			return entity.isEntityAlive() && EntitySeaScorpion.this.getEntitySenses().canSee(entity)
					&& EntitySeaScorpion.this.worldObj.getBlock(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ)).getMaterial() == Material.water && entity != EntitySeaScorpion.this
					&& entity instanceof EntityPlayer && (!((EntityPlayer) entity).capabilities.disableDamage && ((EntityPlayer) entity).getActivePotionEffect(WildMobsMod.potionAquaHealing) == null);
		}
	};

	public final IEntitySelector targetEntitySelectorLiving = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity entity)
		{
			return entity.isEntityAlive() && EntitySeaScorpion.this.getEntitySenses().canSee(entity)
					&& EntitySeaScorpion.this.worldObj.getBlock(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ)).getMaterial() == Material.water && entity != EntitySeaScorpion.this
					&& !(entity instanceof EntitySeaScorpion) && !(entity instanceof EntityCreeper) && !(entity instanceof EntityGhast);
		}
	};

	public EntitySeaScorpion(World world)
	{
		super(world);
		this.setSize(0.8F, 0.2F);
		this.setIsWild(true);
		this.setIsSeaMonster(false);
		this.theNearestAttackableTargetSorter = new EntitySeaScorpion.Sorter(this);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.SEASCORPION_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(21, new Integer(0));
		this.dataWatcher.addObject(22, new Integer(0));
		this.dataWatcher.addObject(23, new Integer(0));
		this.dataWatcher.addObject(24, Byte.valueOf((byte) 0));
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("IsWild", this.getIsWild());
		nbt.setInteger("Age", this.getGrowingAge());
		nbt.setInteger("Regeneration", this.getRegeneration());
		nbt.setInteger("Size", this.getSize());
		nbt.setBoolean("IsSeaMonster", this.getIsSeaMonster());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setIsWild(nbt.getBoolean("IsWild"));
		this.setGrowingAge(nbt.getInteger("Age"));
		this.setRegeneration(nbt.getInteger("Regeneration"));
		this.setSize(nbt.getInteger("Size"));
		this.setIsSeaMonster(nbt.getBoolean("IsSeaMonster"));
	}

	public boolean getIsWild()
	{
		return (this.dataWatcher.getWatchableObjectByte(20) & 1) != 0;
	}

	public void setIsWild(boolean flag)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) (flag ? 1 : 0)));
	}

	public int getGrowingAge()
	{
		return this.dataWatcher.getWatchableObjectInt(21);
	}

	public void setGrowingAge(int age)
	{
		this.dataWatcher.updateObject(21, Integer.valueOf(age));
	}

	public int getRegeneration()
	{
		return this.dataWatcher.getWatchableObjectInt(22);
	}

	public void setRegeneration(int regen)
	{
		this.dataWatcher.updateObject(22, Integer.valueOf(regen));
	}

	public int getSize()
	{
		return this.dataWatcher.getWatchableObjectInt(23);
	}

	public void setSize(int size)
	{
		this.dataWatcher.updateObject(23, Integer.valueOf(size));
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double) (8 + 4 * size));
		this.experienceValue = 3 + size;
		this.setHealth(this.getMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(this.getSize());
	}

	public boolean getIsSeaMonster()
	{
		return (this.dataWatcher.getWatchableObjectByte(24) & 1) != 0;
	}

	public void setIsSeaMonster(boolean flag)
	{
		this.dataWatcher.updateObject(24, Byte.valueOf((byte) (flag ? 1 : 0)));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
	}

	protected void fall(float distance)
	{
		if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
		{
			super.fall(distance);
		}
	}

	protected void updateFallState(double distanceFallenThisTick, boolean onGround)
	{
		if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
		{
			super.updateFallState(distanceFallenThisTick, onGround);
		}
	}

	public boolean handleWaterMovement()
	{
		return false;
	}

	public boolean canBreatheUnderwater()
	{
		return true;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected boolean canDespawn()
	{
		return this.getIsWild() && !(this.getIsSeaMonster());
	}

	public float getEyeHeight()
	{
		return this.height * 0.5F;
	}

	protected String getLivingSound()
	{
		return this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water ? "wildmobsmod:mob.seascorpion.saywater" : null;
	}

	protected String getHurtSound()
	{
		return this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water ? "wildmobsmod:mob.seascorpion.hurtwater" : "wildmobsmod:mob.seascorpion.hurtland";
	}

	protected String getDeathSound()
	{
		return this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water ? "wildmobsmod:mob.seascorpion.hurtwater" : "wildmobsmod:mob.seascorpion.hurtland";
	}

	public int getTalkInterval()
	{
		return 80;
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		checkBlockCollision();
		final int flooredX = MathHelper.floor_double(posX), flooredY = MathHelper.floor_double(posY), flooredZ = MathHelper.floor_double(posZ);
		final Block blockAtPos = worldObj.getBlock(flooredX, flooredY, flooredZ);
		
		// Movement
		if(blockAtPos.getMaterial() == Material.water) {
			if(timeToJump > 0) {
				motionY = 0.2D;
				timeToJump--;
			} else {
				if(cachedPosition != null) {
					double d1 = (double) cachedPosition.posY + 0.1D - posY;
					motionY += (Math.signum(d1) * 0.699999988079071 - motionY) * 0.15000000149011612D;
					motionY *= entityToAttack == null ? cachedPosition.posY - 0.5D > posY || cachedPosition.posY + 0.5D < posY ? 0.6D : 0.4D : 0.8D;
				} else {
					motionY = 0.0D;
				}
			}
			if(entityToAttack != null) {
				Block block = worldObj.getBlock(MathHelper.floor_double(entityToAttack.posX), MathHelper.floor_double(entityToAttack.posY + entityToAttack.getEyeHeight()), MathHelper.floor_double(entityToAttack.posZ));
				if(!block.isNormalCube() && block.getMaterial() != Material.water && rand.nextInt(30) == 0 && getDistanceSqToEntity(entityToAttack) < 16.0D) {
					timeToJump = 20;
				}
			}
		}

		if(timeToJump > 0 && !blockAtPos.isNormalCube() && blockAtPos.getMaterial() != Material.water && worldObj.getBlock(flooredX, flooredY - 1, flooredZ).getMaterial() == Material.water) {
			timeToJump = 0;
			waterJump();
		}

		if((cachedPosition == null || rand.nextInt(100) == 0 || cachedPosition.getDistanceSquared(flooredX, flooredY, flooredZ) < 2.0F || cachedPosition.getDistanceSquared(flooredX, (int) cachedPosition.posY, flooredZ) < 1.0F) && entityToAttack == null) {
			cachedPosition = new ChunkCoordinates(flooredX + rand.nextInt(12) - rand.nextInt(12), flooredY + rand.nextInt(8) - 2, flooredZ + rand.nextInt(12) - rand.nextInt(12));
		} else if(entityToAttack != null) {
			cachedPosition = null;
			final int targetX = (int) entityToAttack.posX, targetY = (int) entityToAttack.posY, targetZ = (int) entityToAttack.posZ;
			if(targetY < (int) (targetY + entityToAttack.getEyeHeight()) && worldObj.getBlock(targetX, (int) (targetY + entityToAttack.getEyeHeight()), targetZ).getMaterial() == Material.water) {
				cachedPosition = new ChunkCoordinates(targetX - 1, targetY + 1, targetZ);
			} else {
				cachedPosition = new ChunkCoordinates(targetX - 1, targetY, targetZ);
			}
		}

		final int cachedX = cachedPosition.posX, cachedY = cachedPosition.posY, cachedZ = cachedPosition.posZ;
		if(cachedPosition != null && worldObj.getBlock(cachedX, cachedY, cachedZ).getMaterial() != Material.water || cachedY < 1) {
			cachedPosition = null;
		}

		if(cachedPosition != null && blockAtPos.getMaterial() == Material.water) {
			final double xDist = cachedX + 0.5D - posX, zDist =  cachedZ + 0.5D - posZ;
			final double speed = entityToAttack == null ? 0.07D : 0.12D;
			motionX += (Math.signum(xDist) * speed - motionX) * 0.5D;
			motionZ += (Math.signum(zDist) * speed - motionZ) * 0.5D;
			moveForward = 0.05F;
		}

		// Find attack target
		if(getGrowingAge() >= 0) {
			if(getIsSeaMonster() || (hasCustomNameTag() && "Leviathan".equals(getCustomNameTag()))) {
				findLivingToAttack();
			} else if(!getIsWild()) {
				findMobToAttack();
			} else if(!canSeaScorpionSeeSky() && !worldObj.isDaytime() && worldObj.isThundering() && worldObj.isRaining() && getBrightness(1.0F) < 0.5F) {
				findHumanToAttack();
			}
		}
		
		if(entityToAttack != null) {
			if(getGrowingAge() < 0 || !canAttackTargetBeSeen(entityToAttack)) {
				entityToAttack = null;
			} else {
				final int targetX = MathHelper.floor_double(entityToAttack.posX), targetY = MathHelper.floor_double(entityToAttack.posY), targetZ = MathHelper.floor_double(entityToAttack.posZ);
				if(worldObj.getBlock(targetX, targetY, targetZ).getMaterial() != Material.water && worldObj.getBlock(targetX, targetY - 1, targetZ).getMaterial() != Material.water) {
					entityToAttack = null;
				}
			}
		}

		// Update stats
		int age = getGrowingAge();
		if(age < 0) {
			age++;
		} else if(age > 0) {
			age--;
		}
		setGrowingAge(age);

		int regen = getRegeneration();
		if(regen < 0) {
			regen++;
		} else if(regen > 0) {
			regen--;
		} else if(!getIsWild() && getHealth() < getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue() && blockAtPos.getMaterial() == Material.water) {
			regen = entityToAttack == null ? 80 : 50;
			heal(1);
		}
		setRegeneration(regen);

		if(worldObj.isRemote && eatingTimer > 0) {
			String particleName = "iconcrack_" + Item.getIdFromItem(food.getItem());
			if(food.getHasSubtypes()) {
				particleName = particleName + "_" + food.getItemDamage();
			}
			for(int i = 0; i < 3; i++) {
				double d0 = rand.nextGaussian() * 0.01D;
				double d1 = rand.nextGaussian() * 0.01D;
				double d2 = rand.nextGaussian() * 0.01D;
				worldObj.spawnParticle(particleName, posX + (double) (rand.nextFloat() * width * 1.5F) - (double) width, posY + 0.5D + (double) (rand.nextFloat() * height) - 0.5D, posZ + (double) (rand.nextFloat() * width * 1.5F) - (double) width, d0, d1, d2);
			}
		}

		if(eatingTimer < 0) {
			eatingTimer++;
		} else if(eatingTimer > 0) {
			eatingTimer--;
		}

		// Adjust pitch
		prevSeaScorpionPitch = seaScorpionPitch;
		double d0 = Math.sqrt(Math.abs(motionX * motionX + motionZ * motionZ));
		if(timeToCalculatePosY > 0) {
			timeToCalculatePosY--;
		} else {
			timeToCalculatePosY = 2;
			movedDist = -(prevPosY - posY);
			prevPosY = posY;
		}
		nextSeaScorpionPitch = (float) ((d0 * 100) * (movedDist * 10));
		if(seaScorpionPitch < nextSeaScorpionPitch - 4.0F) {
			seaScorpionPitch = seaScorpionPitch + 2.0F;
		} else if(seaScorpionPitch > nextSeaScorpionPitch + 4.0F) {
			seaScorpionPitch = seaScorpionPitch - 2.0F;
		}
	}

	protected void waterJump() {
		motionY = 0.4D;
		if(isPotionActive(Potion.jump)) {
			motionY += (double) ((float) (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
		}
		motionX *= 1.5D;
		motionZ *= 1.5D;
		isAirBorne = true;
	}

//	private float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_)
//	{
//		float f3 = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);
//
//		if(f3 > p_75652_3_)
//		{
//			f3 = p_75652_3_;
//		}
//
//		if(f3 < -p_75652_3_)
//		{
//			f3 = -p_75652_3_;
//		}
//
//		return p_75652_1_ + f3;
//	}

	public void updateEntityActionState() {
		super.updateEntityActionState();
		renderYawOffset = rotationYaw = -((float) Math.atan2(motionX, motionZ)) * 180.0F / (float) Math.PI;
	}

	protected void checkBlockCollision() {
		if(cachedPosition == null || cachedPosition.posY != (int) posY) return;
		int spawnX = cachedPosition.posX, spawnY = cachedPosition.posY, spawnZ = cachedPosition.posZ;
		int x = MathHelper.floor_double(posX), y = MathHelper.floor_double(posY), z = MathHelper.floor_double(posZ);
		
		if(spawnZ > posZ && worldObj.getBlock(x, y, z + 1).isNormalCube()) {
			cachedPosition = entityToAttack == null ? new ChunkCoordinates(x + rand.nextInt(12) - rand.nextInt(12), y + rand.nextInt(8) - 2, z - rand.nextInt(12)) : null;
		} else if(spawnX < posX && worldObj.getBlock(x - 1, y, z).isNormalCube()) {
			cachedPosition = entityToAttack == null ? new ChunkCoordinates(x + rand.nextInt(12), y + rand.nextInt(8) - 2, z + rand.nextInt(12) - rand.nextInt(12)) : null;
		} else if(spawnZ < posZ && worldObj.getBlock(x, y, z - 1).isNormalCube()) {
			cachedPosition = entityToAttack == null ? new ChunkCoordinates(x + rand.nextInt(12) - rand.nextInt(12), y + rand.nextInt(8) - 2, z + rand.nextInt(12)) : null;
		} else if(spawnX > posX && worldObj.getBlock(x + 1, y, z).isNormalCube()) {
			cachedPosition = entityToAttack == null ? new ChunkCoordinates(x - rand.nextInt(12), y + rand.nextInt(8) - 2, z + rand.nextInt(12) - rand.nextInt(12)) : null;
		}
	}

	public void onUpdate() {
		super.onUpdate();
		if(!worldObj.isRemote) {
			if( worldObj.difficultySetting == EnumDifficulty.PEACEFUL && getIsWild()) setDead();
		} else {
			setScaleForAge(isChild(), getSize());
		}
	}

	public void onEntityUpdate() {
		int air = getAir();
		super.onEntityUpdate();
		if(isEntityAlive() && worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)).getMaterial() != Material.water) {
			setAir(--air);
			if(air <= -20) {
				setAir(0);
				attackEntityFrom(DamageSource.drown, 2.0F);
			}
		} else {
			setAir(300);
		}
	}

	public boolean canAttackTargetBeSeen(Entity entity) {
		return this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(posX, posY + (double) getEyeHeight(), posZ), Vec3.createVectorHelper(entity.posX, entity.posY + 0.1, entity.posZ)) == null;
	}

	public boolean attackEntityAsMob(Entity entity) {
		if(getGrowingAge() >= 0 && worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)).getMaterial() == Material.water) {
			float damage = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			int knockback = 0;
			if(entity instanceof EntityLivingBase) {
				damage += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) entity);
				knockback += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) entity);
			}
			if(!getIsWild() && !(entity instanceof EntityPlayer)) {
				damage += 5;
			}
			if(entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage + 5)) {
				if(knockback > 0) {
					entity.addVelocity((double) (-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F), 0.1D, (double) (MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F));
					motionX *= 0.6D;
					motionZ *= 0.6D;
				}
				int fireAspect = EnchantmentHelper.getFireAspectModifier(this);
				if(fireAspect > 0) {
					entity.setFire(fireAspect * 4);
				}
				if(entity instanceof EntityLivingBase) {
					EnchantmentHelper.func_151384_a((EntityLivingBase) entity, this);
				}
				EnchantmentHelper.func_151385_b(this, entity);
				return true;
			}
		}
		return false;
	}

	protected void findMobToAttack() {
		findAttackTarget(targetEntitySelectorMob);
	}

	protected void findHumanToAttack() {
		findAttackTarget(targetEntitySelectorHuman);
	}

	protected void findLivingToAttack() {
		findAttackTarget(targetEntitySelectorLiving);
	}
	
	protected void findAttackTarget(IEntitySelector selector) {
		List list = worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(16.0D, 16.0D, 16.0D), selector);
		Collections.sort(list, theNearestAttackableTargetSorter);
		if(!list.isEmpty() && entityToAttack == null) {
			entityToAttack = (EntityLivingBase) list.get(0);
		}
	}

	protected Entity findPlayerToAttack()
	{
		return null;
	}

	public void setScaleForAge(boolean isChild, int size)
	{
		int i = size + 1;
		this.setSize((isChild ? (0.2F + (i * 0.2F)) / 2 : 0.2F + (i * 0.2F)), (isChild ? (0.025F + (i * 0.025F)) / 2 : 0.05F + (i * 0.05F)));
	}

	public boolean isChild()
	{
		return this.getGrowingAge() < 0;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(!isEntityInvulnerable() && super.attackEntityFrom(source, amount)) {
			Entity entity = source.getEntity();
			if(riddenByEntity != entity && ridingEntity != entity && entity != this && getAge() >= 0) {
				this.entityToAttack = entity;
			}
			return true;
		}
		return false;
	}

	public boolean interact(EntityPlayer player) {
		final ItemStack stack = player.inventory.getCurrentItem();
		if(!worldObj.isRemote && stack != null) {
			final Item item = stack.getItem();
			if(item == Items.water_bucket && !getIsWild() && !getIsSeaMonster())
			{
				int i = 4;
				if(getGrowingAge() >= 0 && getSize() == 0) {
					i = 1;
				}
				else if(getGrowingAge() < 0) {
					switch(getSize()) {
						case 0 : i = 2; break;
						case 1 : i = 3; break;
						case 2 : i = 0; break;
					}
				}
				if(i < 4) {
					ItemStack bucket = new ItemStack(WildMobsModItems.seaScorpionBucket, 1, i);
					if(--stack.stackSize < 1) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, bucket);
					} else if(!player.inventory.addItemStackToInventory(bucket)) {
						player.dropPlayerItemWithRandomChoice(bucket, false);
					}
					this.setDead();
					return true;
				} else {
					return super.interact(player);
				}
			} else if(item == WildMobsModItems.seaScorpionSpawnEgg) {
				EntitySeaScorpion entityseascorpion = new EntitySeaScorpion(worldObj);
				entityseascorpion.setGrowingAge(-24000);
				entityseascorpion.setSize(getSize());
				entityseascorpion.setIsWild(getIsWild());
				entityseascorpion.setIsSeaMonster(getIsSeaMonster());
				entityseascorpion.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
				worldObj.spawnEntityInWorld(entityseascorpion);
				if(stack.hasDisplayName()) {
					entityseascorpion.setCustomNameTag(stack.getDisplayName());
				}
				if(!player.capabilities.isCreativeMode) {
					if(--stack.stackSize < 1) {
						player.destroyCurrentEquippedItem();
					}
				}
				return true;
			} else if(item instanceof ItemFood) {
				ItemFood itemfood = (ItemFood) item;
				if(isHealingItem(stack) == true && getHealth() < getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue() && !getIsWild()) {
					if(!player.capabilities.isCreativeMode) {
						stack.stackSize--;
					}
					heal((float) itemfood.func_150905_g(stack));
					food = stack;
					eatingTimer = 10;
					if(stack.stackSize < 1) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
					}
					return true;
				} else {
					return super.interact(player);
				}
			}
		}
		return super.interact(player);
	}

	public boolean isHealingItem(ItemStack stack)
	{
		if(stack == null) return false;
		Item item = stack.getItem();
		return item == Items.fish || item == WildMobsModItems.rawCalamari;
	}

	public static class Sorter implements Comparator
	{
		private final Entity theEntity;

		public Sorter(Entity entity)
		{
			this.theEntity = entity;
		}

		public int compare(Entity entity1, Entity entity2)
		{
			double distance1 = this.theEntity.getDistanceSqToEntity(entity1);
			double distance2 = this.theEntity.getDistanceSqToEntity(entity2);
			return distance1 < distance2 ? -1 : (distance1 > distance2 ? 1 : 0);
		}

		public int compare(Object obj1, Object obj2)
		{
			return this.compare((Entity) obj1, (Entity) obj2);
		}
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		if(this.getGrowingAge() >= 0) {
			int amount = rand.nextInt(1 + getSize()) + rand.nextInt(1 + looting);
			int type = rand.nextInt(100);

			for(int k = 0; k < amount; ++k) {
				if(isBurning()) {
					if(type < 60) {
						entityDropItem(new ItemStack(Items.cooked_fished, 1, 0), 0.0F);
					} else if(type < 85) {
						entityDropItem(new ItemStack(Items.cooked_fished, 1, 1), 0.0F);
					} else if(type < 87) {
						entityDropItem(new ItemStack(Items.fish, 1, 2), 0.0F);
					} else {
						entityDropItem(new ItemStack(Items.fish, 1, 3), 0.0F);
					}
				} else {
					if(type < 60) {
						entityDropItem(new ItemStack(Items.fish, 1, 0), 0.0F);
					} else if(type < 85) {
						entityDropItem(new ItemStack(Items.fish, 1, 1), 0.0F);
					} else if(type < 87) {
						entityDropItem(new ItemStack(Items.fish, 1, 2), 0.0F);
					} else {
						entityDropItem(new ItemStack(Items.fish, 1, 3), 0.0F);
					}
				}
			}

			if(this.rand.nextInt(100) <= 4 + getSize() + looting) {
				dropItem(WildMobsModItems.seaScorpionEgg, 1);
			}
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		data = super.onSpawnWithEgg(data);
		int size = 4;
		int i = rand.nextInt(8);
		if(i < 4) {
			size = 1;
		} else if(i < 7) {
			size = 2;
		}
		setSize(size);
		setIsWild(true);
		return data;
	}

	public void trySpawnPack() {
		if(!canSpawnPack) return;
		for(int k = 0; k < 3; ++k) {
			double x = posX + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5);
			double y = posY + worldObj.rand.nextInt(2) - worldObj.rand.nextInt(2);
			double z = posZ + worldObj.rand.nextInt(5) - worldObj.rand.nextInt(5);

			EntitySeaScorpion entityseascorpion = new EntitySeaScorpion(worldObj);
			entityseascorpion.setPosition(x, y, z);
			entityseascorpion.onSpawnWithEgg((IEntityLivingData) null);
			entityseascorpion.setSize(getSize());
			entityseascorpion.canSpawnPack = false;
			if(entityseascorpion.getCanSpawnHere()) {
				worldObj.spawnEntityInWorld(entityseascorpion);
			}
		}
	}

	public boolean canSeaScorpionSeeSky()
	{
		boolean flag = false;

		int i = 0;

		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);

		for(int k = y; k < 255; ++k)
		{
			Block block = this.worldObj.getBlock(x, k, z);

			if(block.isNormalCube())
			{
				k = 255;
				flag = false;
			}
			else
			{
				if(k >= 255)
				{
					flag = true;
				}
			}

			i = k;
		}

		return flag;
	}

	public boolean getCanSpawnHere()
	{
		int x = MathHelper.floor_double(posX);
		int y = MathHelper.floor_double(boundingBox.minY);
		int z = MathHelper.floor_double(posZ);
		Block block = worldObj.getBlock(x, y, z);
		boolean flag = worldObj.difficultySetting != EnumDifficulty.PEACEFUL && isValidLightLevel() && worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && block.getMaterial() == Material.water;
		if(flag && worldObj.isDaytime() && !worldObj.getWorldInfo().isThundering())
		{
			return this.canSeaScorpionSeeSky();
		}
		return flag;
	}
}

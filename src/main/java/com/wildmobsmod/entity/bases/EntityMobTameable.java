package com.wildmobsmod.entity.bases;

import com.wildmobsmod.entity.monster.seascorpion.EntitySeaScorpion;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * Only used for {@link EntitySeaScorpion}
 */
public abstract class EntityMobTameable extends EntityCreature implements IMob
{
	public EntityMobTameable(World world)
	{
		super(world);
		this.experienceValue = 5;
	}

	public void onLivingUpdate()
	{
		this.updateArmSwingProgress();
		float f = this.getBrightness(1.0F);
		if(f > 0.5F)
		{
			this.entityAge += 2;
		}
		super.onLivingUpdate();
	}

	public void onUpdate()
	{
		super.onUpdate();
	}

	protected String getSwimSound()
	{
		return "game.hostile.swim";
	}

	protected String getSplashSound()
	{
		return "game.hostile.swim.splash";
	}

	protected Entity findPlayerToAttack()
	{
		EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
		return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable()) return false;
		if(super.attackEntityFrom(source, amount))
		{
			Entity entity = source.getEntity();
			if(this.riddenByEntity != entity && this.ridingEntity != entity && entity != this) this.entityToAttack = entity;
			return true;
		}
		return false;
	}

	protected String getHurtSound()
	{
		return "game.hostile.hurt";
	}

	protected String getDeathSound()
	{
		return "game.hostile.die";
	}

	/**
	 * Get falling impact sound
	 */
	protected String func_146067_o(int height)
	{
		return height > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
	}

	public boolean attackEntityAsMob(Entity target)
	{
		float damage = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		int knockback = 0;
		if(target instanceof EntityLivingBase)
		{
			damage += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) target);
			knockback += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) target);
		}
		boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
		if(flag)
		{
			if(knockback > 0)
			{
				target.addVelocity((double) (-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F), 0.1D, (double) (MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * (float) knockback * 0.5F));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}
			int fireAspect = EnchantmentHelper.getFireAspectModifier(this);
			if(fireAspect > 0) target.setFire(fireAspect * 4);
			if(target instanceof EntityLivingBase) EnchantmentHelper.func_151384_a((EntityLivingBase) target, this);
			EnchantmentHelper.func_151385_b(this, target);
		}
		return flag;
	}

	protected void attackEntity(Entity target, float distance)
	{
		if(this.attackTime <= 0 && distance < 2.0F && target.boundingBox.maxY > this.boundingBox.minY && target.boundingBox.minY < this.boundingBox.maxY)
		{
			this.attackEntityAsMob(target);
			this.attackTime = 20;
		}
	}

	public float getBlockPathWeight(int x, int y, int z)
	{
		return 0.5F - this.worldObj.getLightBrightness(x, y, z);
	}
	
	protected boolean isValidLightLevel()
	{
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);
		if(this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > this.rand.nextInt(32)) return false;
		int l = this.worldObj.getBlockLightValue(i, j, k);
		if(this.worldObj.isThundering())
		{
			int i1 = this.worldObj.skylightSubtracted;
			this.worldObj.skylightSubtracted = 10;
			l = this.worldObj.getBlockLightValue(i, j, k);
			this.worldObj.skylightSubtracted = i1;
		}
		return l <= this.rand.nextInt(8);
	}

	public boolean getCanSpawnHere()
	{
		return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
	}

	protected boolean func_146066_aG()
	{
		return true;
	}
}

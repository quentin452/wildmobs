package com.wildmobsmod.entity.passive.cougar;

import com.wildmobsmod.entity.bases.EntityLargePredator;
import com.wildmobsmod.entity.passive.bison.EntityBison;
import com.wildmobsmod.entity.passive.deer.EntityDeer;
import com.wildmobsmod.entity.passive.goat.EntityGoat;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;

public class EntityCougar extends EntityLargePredator
{
	public EntityCougar(World world)
	{
		super(world, EntityBison.class, EntityCow.class, EntitySheep.class, EntityDeer.class, EntityGoat.class, EntityChicken.class);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.COUGAR_CONFIG.getMaxPackSize();
	}
	
	protected String getLivingSound()
	{
		return this.getAttackTarget() != null ? "wildmobsmod:entity.cougar.angry" : null;
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:entity.cougar.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:entity.cougar.hurt";
	}
	
	@Override
	public EntityCougar createChild(EntityAgeable entity)
	{
		return null;
	}
}

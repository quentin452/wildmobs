package com.wildmobsmod.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAISkeletonWolfNearestAttackableTarget extends EntityAITarget
{
	private final Class targetClass;
	private final int targetChance;
	/** Instance of EntityAINearestAttackableTargetSorter. */
	private final EntityAISkeletonWolfNearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
	/**
	 * This filter is applied to the Entity search. Only matching entities will
	 * be targetted. (null -> no restrictions)
	 */
	private final IEntitySelector targetEntitySelector;
	private EntityLivingBase targetEntity;

	public EntityAISkeletonWolfNearestAttackableTarget(EntitySkeletonWolf wolf, Class targetClass, int chance, boolean checkSight)
	{
		this(wolf, targetClass, chance, checkSight, false);
	}

	public EntityAISkeletonWolfNearestAttackableTarget(EntitySkeletonWolf wolf, Class targetClass, int chance, boolean checkSight, boolean nearbyOnly)
	{
		this(wolf, targetClass, chance, checkSight, nearbyOnly, (IEntitySelector) null);
	}

	public EntityAISkeletonWolfNearestAttackableTarget(EntitySkeletonWolf wolf, Class targetClass, int chance, boolean checkSight, boolean nearbyOnly, final IEntitySelector selector)
	{
		super(wolf, checkSight, nearbyOnly);
		this.targetClass = targetClass;
		this.targetChance = chance;
		this.theNearestAttackableTargetSorter = new EntityAISkeletonWolfNearestAttackableTarget.Sorter(wolf);
		this.setMutexBits(1);
		this.targetEntitySelector = new IEntitySelector()
		{
			/**
			 * Return whether the specified entity is applicable to this filter.
			 */
			public boolean isEntityApplicable(Entity e)
			{
				return !(e instanceof EntityLivingBase) ? false : (selector != null && !selector.isEntityApplicable(e) ? false : EntityAISkeletonWolfNearestAttackableTarget.this.isSuitableTarget((EntityLivingBase) e, false));
			}
		};
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0 || ((EntitySkeletonWolf) this.taskOwner).entityToFollow == null) return false;
		double d0 = this.getTargetDistance();
		List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
		Collections.sort(list, this.theNearestAttackableTargetSorter);
		if(!list.isEmpty())
		{
			this.targetEntity = (EntityLivingBase) list.get(0);
			return true;
		}
		return false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.targetEntity);
		super.startExecuting();
	}

	public static class Sorter implements Comparator
	{
		private final Entity theEntity;

		public Sorter(Entity e)
		{
			this.theEntity = e;
		}

		public int compare(Entity compare1, Entity compare2)
		{
			double d0 = this.theEntity.getDistanceSqToEntity(compare1);
			double d1 = this.theEntity.getDistanceSqToEntity(compare2);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}

		public int compare(Object compare1, Object compare2)
		{
			return this.compare((Entity) compare1, (Entity) compare2);
		}
	}
}

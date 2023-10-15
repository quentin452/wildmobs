package com.wildmobsmod.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIAttackIfApproached extends EntityAITarget
{
	private final Class targetClass;
	private final int targetChance;
	/** Instance of EntityAINearestAttackableTargetSorter. */
	private final EntityAIAttackIfApproached.Sorter theNearestAttackableTargetSorter;
	/**
	 * This filter is applied to the Entity search. Only matching entities will
	 * be targetted. (null -> no restrictions)
	 */
	private final IEntitySelector targetEntitySelector;
	private EntityLivingBase targetEntity;

	public EntityAIAttackIfApproached(EntityCreature creature, Class targetClass, int targetChance, boolean checkLOS)
	{
		this(creature, targetClass, targetChance, checkLOS, false);
	}

	public EntityAIAttackIfApproached(EntityCreature creature, Class targetClass, int targetChance, boolean checkLOS, boolean nearbyOnly)
	{
		this(creature, targetClass, targetChance, checkLOS, nearbyOnly, (IEntitySelector) null);
	}

	public EntityAIAttackIfApproached(EntityCreature creature, Class targetClass, int targetChance, boolean checkLOS, boolean nearbyOnly, final IEntitySelector selector)
	{
		super(creature, checkLOS, nearbyOnly);
		this.targetClass = targetClass;
		this.targetChance = targetChance;
		this.theNearestAttackableTargetSorter = new EntityAIAttackIfApproached.Sorter(creature);
		this.setMutexBits(1);
		this.targetEntitySelector = new IEntitySelector()
		{
			/**
			 * Return whether the specified entity is applicable to this filter.
			 */
			public boolean isEntityApplicable(Entity e)
			{
				return !(e instanceof EntityLivingBase) ? false : (selector != null && !selector.isEntityApplicable(e) ? false : EntityAIAttackIfApproached.this.isSuitableTarget((EntityLivingBase) e, false));
			}
		};
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) return false;
		double d0 = this.getTargetDistance();
		List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(2.0D, 4.0D, 2.0D), this.targetEntitySelector);
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

		public Sorter(Entity theEntity)
		{
			this.theEntity = theEntity;
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

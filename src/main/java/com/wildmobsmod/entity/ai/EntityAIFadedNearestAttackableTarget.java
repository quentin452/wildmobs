package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.monster.faded.EntityFaded;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAIFadedNearestAttackableTarget extends EntityAINearestAttackableTarget
{
	public EntityAIFadedNearestAttackableTarget(EntityCreature creature, Class targetClass, int chance, boolean checkSight)
	{
		super(creature, targetClass, chance, checkSight);
	}

	public boolean shouldExecute()
	{
		return (((EntityFaded) this.taskOwner).getEquipmentInSlot(0) == null || ((EntityFaded) this.taskOwner).getHandEquipment() == null) && super.shouldExecute();
	}
}

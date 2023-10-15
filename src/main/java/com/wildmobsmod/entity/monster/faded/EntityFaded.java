package com.wildmobsmod.entity.monster.faded;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.wildmobsmod.entity.ai.EntityAIFollowLeaderFaded;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;

public class EntityFaded extends EntityZombie
{
	//
	// This mob is quite interesting. It's essentially a zombie that has two
	// held item slots, one with a weapon, and one with a random item it can use
	// to heal or apply a potion effect. They also travel in groups,
	// following an automatically chosen leader.
	// The mob only spawns during rain. It should have a custom drop, but I
	// haven't decided what it should be (Something that fits their pack
	// behavior)
	//

	private int eating;
	private int eatingSound;

	// Backpack is just a funny name for the fadeds that run slower than other
	// fadeds when attacking. If a pack has more than four fadeds, some of them
	// are made to move slower to avoid them clustering together.
	public boolean isInBackpack;

	private static final UUID backPackSlownessUUID = UUID.fromString("3d17be0f-fc6d-4947-8d90-0e0556f1b737");
	private static final AttributeModifier backPackSlonwessModifier = new AttributeModifier(backPackSlownessUUID, "Back pack slowness", -0.25D, 1);

	public EntityFaded(World world)
	{
		super(world);
		this.tasks.addTask(5, new EntityAIFollowLeaderFaded(this, 1.0D));
		this.setIsLeader(false);
		this.isInBackpack = false;
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.FADED_CONFIG.getMaxPackSize();
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(15, new ItemStack(Items.stone_axe));
		this.dataWatcher.addObject(16, new Float(0));
		this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);

		NBTTagCompound nbttagcompound2;
		nbttagcompound2 = new NBTTagCompound();

		if(this.getHandEquipment() != null)
		{
			this.getHandEquipment().writeToNBT(nbttagcompound2);
		}

		nbt.setTag("SecondaryHandItem", nbttagcompound2);
		nbt.setFloat("SecondaryHandItemDropChance", this.getHandEquipmentDropChance());

		nbt.setBoolean("IsLeader", this.getIsLeader());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		this.setHandEquipment(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("SecondaryHandItem")));
		this.setHandEquipmentDropChance(nbt.getFloat("SecondaryHandItemDropChance"));

		this.setIsLeader(nbt.getBoolean("IsLeader"));
	}

	public ItemStack getHandEquipment()
	{
		return this.dataWatcher.getWatchableObjectItemStack(15);
	}

	public void setHandEquipment(ItemStack stack)
	{
		this.dataWatcher.updateObject(15, stack);
	}

	public float getHandEquipmentDropChance()
	{
		return this.dataWatcher.getWatchableObjectFloat(16);
	}

	public void setHandEquipmentDropChance(float stack)
	{
		this.dataWatcher.updateObject(16, stack);
	}

	public boolean getIsLeader()
	{
		return (this.dataWatcher.getWatchableObjectByte(17) & 1) != 0;
	}

	public void setIsLeader(boolean flag)
	{
		if(flag)
		{
			this.dataWatcher.updateObject(17, Byte.valueOf((byte) 1));
		}
		else
		{
			this.dataWatcher.updateObject(17, Byte.valueOf((byte) 0));
		}
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(field_110186_bp).setBaseValue(0.0D);
	}

	public boolean interact(EntityPlayer player)
	{
		return false;
	}

	protected void addRandomArmor()
	{
		super.addRandomArmor();

		if(this.rand.nextInt(3 + this.worldObj.difficultySetting.getDifficultyId()) > 0)
		{
			if(this.rand.nextInt(4) == 0)
			{
				if(this.rand.nextInt(2) == 0)
				{
					this.setHandEquipment(new ItemStack(Items.potionitem, 1, 8194));
				}
				else
				{
					this.setHandEquipment(new ItemStack(Items.potionitem, 1, 8201));
				}
			}
			else
			{
				int j = this.rand.nextInt(3);
				if(j == 0)
				{
					this.setHandEquipment(new ItemStack(Items.beef));
				}
				else if(j == 1)
				{
					this.setHandEquipment(new ItemStack(Items.baked_potato));
				}
				else if(j == 2)
				{
					this.setHandEquipment(new ItemStack(Items.cooked_porkchop));
				}
			}
		}

		this.setHandEquipmentDropChance(0.085F);

		if(this.rand.nextInt(3) == 0)
		{
			this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
		}
		else
		{
			this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_axe));
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		super.onSpawnWithEgg(data);
		this.setVillager(false);
		if(this.getIsLeader() == true)
		{
			this.spawnPack();
		}
		return data;
	}

	protected void dropEquipment(boolean playerkill, int looting)
	{
		super.dropEquipment(playerkill, looting);

		ItemStack itemstack = this.getHandEquipment();
		boolean flag1 = this.getHandEquipmentDropChance() > 1.0F;

		if(itemstack != null && (playerkill || flag1) && this.rand.nextFloat() - (float) looting * 0.01F < this.getHandEquipmentDropChance())
		{
			if(!flag1 && itemstack.isItemStackDamageable())
			{
				int k = Math.max(itemstack.getMaxDamage() - 25, 1);
				int l = itemstack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(k) + 1);

				if(l > k)
				{
					l = k;
				}

				if(l < 1)
				{
					l = 1;
				}

				itemstack.setItemDamage(l);
			}
			this.entityDropItem(itemstack, 0.0F);
		}
	}

	public void onLivingUpdate()
	{
		this.worldObj.theProfiler.startSection("looting");

		if(!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
		{
			List list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.0D, 0.0D, 1.0D));
			Iterator iterator = list.iterator();

			while(iterator.hasNext())
			{
				EntityItem entityitem = (EntityItem) iterator.next();

				if(!entityitem.isDead && entityitem.getEntityItem() != null)
				{
					ItemStack itemstack = entityitem.getEntityItem();
					int i = getArmorPosition(itemstack);

					if(i > -1)
					{
						boolean flag = true;
						ItemStack itemstack1 = this.getEquipmentInSlot(i);

						if(itemstack1 != null)
						{
							if(i == 0)
							{
								if(itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword))
								{
									flag = true;
								}
								else if(itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword)
								{
									ItemSword itemsword = (ItemSword) itemstack.getItem();
									ItemSword itemsword1 = (ItemSword) itemstack1.getItem();

									if(itemsword.func_150931_i() == itemsword1.func_150931_i())
									{
										flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
									}
									else
									{
										flag = itemsword.func_150931_i() > itemsword1.func_150931_i();
									}
								}
								else
								{
									flag = false;
								}
							}
							else if(itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor))
							{
								flag = true;
							}
							else if(itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor)
							{
								ItemArmor itemarmor = (ItemArmor) itemstack.getItem();
								ItemArmor itemarmor1 = (ItemArmor) itemstack1.getItem();

								if(itemarmor.damageReduceAmount == itemarmor1.damageReduceAmount)
								{
									flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
								}
								else
								{
									flag = itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount;
								}
							}
							else
							{
								flag = false;
							}
						}

						if(!flag)
						{
							boolean flag1 = true;

							ItemStack itemstack2 = this.getHandEquipment();

							if(itemstack2 != null)
							{
								if((this.isItemUseful(itemstack) && !(this.isItemUseful(itemstack2))) || (this.isItemEdible(itemstack) && !(this.isItemEdible(itemstack2))))
								{
									flag1 = true;
								}
								else
								{
									flag1 = false;
								}
							}
							else
							{
								flag1 = true;
							}

							if(flag1 == true)
							{
								if(itemstack.getItem() == Items.diamond && entityitem.func_145800_j() != null)
								{
									EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(entityitem.func_145800_j());

									if(entityplayer != null)
									{
										entityplayer.triggerAchievement(AchievementList.field_150966_x);
									}
								}

								this.setHandEquipment(itemstack);
								this.setHandEquipmentDropChance(2.0F);
								this.func_110163_bv();
								this.onItemPickup(entityitem, 1);
								entityitem.setDead();
							}
						}
					}
				}
			}
		}

		this.worldObj.theProfiler.endSection();

		super.onLivingUpdate();

		if(!this.worldObj.isRemote)
		{
			ItemStack itemstack = this.getHeldItem();
			ItemStack itemstack2 = this.getHandEquipment();

			if(this.getAttackTarget() != null && itemstack != null && (itemstack.getItemUseAction() == EnumAction.drink || itemstack.getItemUseAction() == EnumAction.eat) && this.eating <= 0 && this.rand.nextFloat() < 0.2F)
			{
				if(itemstack.getItem() instanceof ItemFood)
				{
					if(this.getHealth() < this.getMaxHealth() - ((float) ((ItemFood) itemstack.getItem()).func_150905_g(itemstack) + 2.0F))
					{
						this.eating = 26;
					}
				}
				else
				{
					this.eating = 26;
				}
			}
			else if(this.getAttackTarget() != null && itemstack != null && itemstack.getItemUseAction() != EnumAction.drink && itemstack.getItemUseAction() != EnumAction.eat && itemstack2 != null && this.rand.nextFloat() < 0.1F)
			{
				if((this.isItemUseful(itemstack2) && !(this.isItemUseful(itemstack))) || (this.isItemEdible(itemstack2) && !(this.isItemEdible(itemstack))))
				{
					if(itemstack2.getItem() instanceof ItemFood)
					{
						if(this.getHealth() < this.getMaxHealth() - ((float) ((ItemFood) itemstack2.getItem()).func_150905_g(itemstack) + 2.0F))
						{
							ItemStack newhandequipment = itemstack;
							float newhandequipmentdropchance = this.equipmentDropChances[0];
							this.setCurrentItemOrArmor(0, this.getHandEquipment());
							this.equipmentDropChances[0] = this.getHandEquipmentDropChance();
							this.setHandEquipment(newhandequipment);
							this.setHandEquipmentDropChance(newhandequipmentdropchance);
						}
					}
					else
					{
						ItemStack newhandequipment = itemstack;
						float newhandequipmentdropchance = this.equipmentDropChances[0];
						this.setCurrentItemOrArmor(0, this.getHandEquipment());
						this.equipmentDropChances[0] = this.getHandEquipmentDropChance();
						this.setHandEquipment(newhandequipment);
						this.setHandEquipmentDropChance(newhandequipmentdropchance);
					}
				}
			}
			else if(itemstack == null && itemstack2 != null && this.rand.nextFloat() < 0.2F)
			{
				this.setCurrentItemOrArmor(0, this.getHandEquipment());
				this.equipmentDropChances[0] = this.getHandEquipmentDropChance();
				this.setHandEquipment(null);
				this.setHandEquipmentDropChance(0.0F);
			}

			if(this.eating > 0)
			{
				if(this.eating == 1)
				{
					if(itemstack != null && itemstack.getItem() == Items.potionitem)
					{
						List list = Items.potionitem.getEffects(itemstack);

						if(list != null)
						{
							Iterator iterator = list.iterator();

							while(iterator.hasNext())
							{
								PotionEffect potioneffect = (PotionEffect) iterator.next();
								this.addPotionEffect(new PotionEffect(potioneffect));
							}
						}
					}
					else if(itemstack != null && itemstack.getItem() == Items.milk_bucket)
					{
						this.curePotionEffects(itemstack);
					}
					else if(itemstack != null && itemstack.getItem() == Items.golden_apple && itemstack.getItemDamage() == 0)
					{
						this.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 20 * 120, 0));
						this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20 * 5, 1));
					}
					else if(itemstack != null && itemstack.getItem() == Items.golden_apple && itemstack.getItemDamage() == 1)
					{
						this.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 20 * 120, 3));
						this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20 * 20, 1));
						this.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20 * 300, 0));
						this.addPotionEffect(new PotionEffect(Potion.resistance.id, 20 * 300, 0));
					}
					else if(itemstack != null && itemstack.getItem() == WildMobsModItems.goldenSeaEgg)
					{
						this.addPotionEffect(new PotionEffect(WildMobsMod.aquaHealingID, 20 * 90, 0));
					}
					else if(itemstack != null && itemstack.getItem() instanceof ItemFood)
					{
						ItemFood itemfood = (ItemFood) itemstack.getItem();
						this.heal((float) itemfood.func_150905_g(itemstack));
					}

					if(itemstack != null && itemstack.getItem().getItemUseAction(itemstack) == EnumAction.eat)
					{
						this.playSound("random.burp", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
					}

					if(itemstack != null && itemstack.getItem() == Items.potionitem)
					{
						this.setCurrentItemOrArmor(0, new ItemStack(Items.glass_bottle));
					}
					else if(itemstack != null && itemstack.getItem() == Items.milk_bucket)
					{
						this.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
					}
					else if(itemstack != null && itemstack.getItem() == Items.mushroom_stew)
					{
						this.setCurrentItemOrArmor(0, new ItemStack(Items.bowl));
					}
					else
					{
						this.setCurrentItemOrArmor(0, null);
					}
				}
				this.eating--;
				if(this.eatingSound == 0)
				{
					if(itemstack != null && itemstack.getItem().getItemUseAction(itemstack) == EnumAction.drink)
					{
						this.playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
					}
					else if(itemstack != null && itemstack.getItem().getItemUseAction(itemstack) == EnumAction.eat)
					{
						this.playSound("random.eat", 0.5F + 0.5F * (float) this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					}
					this.eatingSound = 3;
				}
				else
				{
					this.eatingSound--;
				}
			}
		}

		//
		// Determine the Leader
		//
		if(!this.worldObj.isRemote && this.rand.nextInt(20) == 0)
		{
			List list = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(8.0D, 4.0D, 8.0D));
			List list2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(8.0D, 4.0D, 8.0D));
			list2.clear();
			int i = 0;

			while(list.size() > 0 && i < list.size())
			{
				EntityFaded entityfaded = (EntityFaded) list.get(i);
				if(entityfaded.getIsLeader() == true)
				{
					list2.add(entityfaded);
				}
				i++;
			}

			if(list2.size() <= 0 && list.size() > 1)
			{
				this.setIsLeader(true);
			}
			else if(list2.size() > 1)
			{
				this.setIsLeader(false);
			}
			else if(list.size() < 2)
			{
				this.setIsLeader(false);
			}
		}

		//
		// Back Pack
		//
		if(!this.worldObj.isRemote && this.rand.nextInt(20) == 0)
		{
			List list = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(8.0D, 4.0D, 8.0D));
			List list2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(8.0D, 4.0D, 8.0D));
			list2.clear();
			int i = 0;

			while(list.size() > 0 && i < list.size())
			{
				EntityFaded entityfaded = (EntityFaded) list.get(i);
				if(entityfaded.isInBackpack == false)
				{
					list2.add(entityfaded);
				}
				i++;
			}

			if(this.getAttackTarget() != null && list.size() > 4)
			{
				if(list2.size() <= 3)
				{
					this.isInBackpack = false;
				}
				else if(list2.size() > 4)
				{
					this.isInBackpack = true;
				}
			}
			else
			{
				this.isInBackpack = false;
			}
		}

		if(!this.worldObj.isRemote && this.isInBackpack == true)
		{
			IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			iattributeinstance.removeModifier(backPackSlonwessModifier);
			iattributeinstance.applyModifier(backPackSlonwessModifier);
		}
		else if(!this.worldObj.isRemote && this.isInBackpack == false)
		{
			IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			iattributeinstance.removeModifier(backPackSlonwessModifier);
		}
	}

	public boolean isItemUseful(ItemStack itemstack)
	{
		Item item = itemstack.getItem();
		if(item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemHoe || item instanceof ItemSpade || item instanceof ItemPickaxe || itemstack.getItemUseAction() == EnumAction.eat || itemstack.getItemUseAction() == EnumAction.drink)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isItemEdible(ItemStack itemstack)
	{
		if(itemstack.getItemUseAction() == EnumAction.eat || itemstack.getItemUseAction() == EnumAction.drink)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void spawnPack()
	{
		ArrayList allblocks = new ArrayList();

		int currentblock = 0;

		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				for(int k = 0; k < 5; k++)
				{
					allblocks.add(new ChunkCoordinates((int) this.posX + (i - 2), (int) this.posY + (j - 2), (int) this.posZ + (k - 2)));
				}
			}
		}

		allblocks.remove(new ChunkCoordinates((int) this.posX, (int) this.posY, (int) this.posZ));

		ArrayList suitableblocks = new ArrayList();

		for(int j = 0; j < allblocks.size(); j++)
		{
			ChunkCoordinates currentblock1 = (ChunkCoordinates) allblocks.get(j);

			Block block1 = this.worldObj.getBlock(currentblock1.posX, currentblock1.posY - 1, currentblock1.posZ);
			Block block2 = this.worldObj.getBlock(currentblock1.posX, currentblock1.posY, currentblock1.posZ);
			Block block3 = this.worldObj.getBlock(currentblock1.posX, currentblock1.posY + 1, currentblock1.posZ);

			if(block1.isNormalCube() && !block2.isNormalCube() && !block3.isNormalCube() && !block2.getMaterial().isLiquid() && !block3.getMaterial().isLiquid())
			{
				suitableblocks.add(currentblock1);
			}
		}

		for(int k = 0; k < 3 + this.rand.nextInt(2); k++)
		{
			if(suitableblocks.size() > 0)
			{
				ChunkCoordinates spawnblock = (ChunkCoordinates) suitableblocks.get(this.rand.nextInt(suitableblocks.size()));

				EntityFaded entityfaded = new EntityFaded(this.worldObj);
				entityfaded.setPosition(spawnblock.posX - 0.5D, spawnblock.posY, spawnblock.posZ + 0.5D);
				entityfaded.onSpawnWithEgg((IEntityLivingData) null);
				this.worldObj.spawnEntityInWorld(entityfaded);
				suitableblocks.remove(spawnblock);
			}
		}
	}

	public boolean getCanSpawnHere()
	{
		if((this.worldObj.getWorldInfo().isRaining() || this.worldObj.getWorldInfo().isThundering()) && this.worldObj.provider instanceof WorldProviderSurface
				&& this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
		{
			List list = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(8.0D, 4.0D, 8.0D));

			if(list.size() > 0)
			{
				for(int i = 0; i < list.size(); i++)
				{
					EntityFaded currententity = (EntityFaded) list.get(i);

					if(currententity.getIsLeader() == false)
					{
						list.remove(i);
					}
				}
			}

			if(list.size() <= 0)
			{
				this.setIsLeader(true);
			}

			return super.getCanSpawnHere();
		}
		else
		{
			return false;
		}
	}
}

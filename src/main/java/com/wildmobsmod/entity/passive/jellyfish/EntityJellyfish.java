package com.wildmobsmod.entity.passive.jellyfish;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class EntityJellyfish extends EntityWaterMob implements ISkinnedEntity
{
	//
	// You may add a custom drop if you have good ideas, but for now the normal
	// jellyfish drop slime and the nether medusae drop magma cream.
	//

	public float swimTimer;
	public float width;
	public float height;
	
	private int lavaHeight;
	private byte floatingCD; // = (byte) (Math.random() * 250D - 125); // prevent floating pattern from being synchronized (disabled, because I think it looks nice)
	private float floating;

	public EntityJellyfish(World world)
	{
		super(world);
		this.setSize(0.6F, 0.4F);
		this.width = 2.0F;
		this.height = 2.0F;
		this.isImmuneToFire = true;
	}

	public int getMaxSpawnedInChunk()
	{
		return worldObj.provider instanceof WorldProviderHell ? WildMobsMod.JELLYFISH_CONFIG.getNetherMaxPackSize() : WildMobsMod.JELLYFISH_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(19, Byte.valueOf((byte) 0));
	}
	
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", this.getSkin());
		nbt.setBoolean("IsNether", this.getNether());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("Variant"));
		this.setNether(nbt.getBoolean("IsNether"));
	}

	public int getSkin()
	{
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	public void setSkin(int skinId)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) skinId));
	}

	public boolean getNether()
	{
		return (this.dataWatcher.getWatchableObjectByte(19) & 1) != 0;
	}

	public void setNether(boolean flag)
	{
		this.dataWatcher.updateObject(19, Byte.valueOf((byte) (flag ? 1 : 0)));
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData entity)
	{
		IEntityLivingData data = super.onSpawnWithEgg(entity);
		if(this.worldObj.provider instanceof WorldProviderHell)
		{
			this.setSkin(6);
			this.setNether(true);
		}
		else
		{
			int i = 0;

			if(data instanceof EntityJellyfish.GroupData)
			{
				i = ((EntityJellyfish.GroupData) data).type;
			}
			else
			{
				i = this.worldObj.rand.nextInt(6);

				data = new EntityJellyfish.GroupData(i);
			}

			this.setSkin(i);
			this.setNether(false);
		}
		return (IEntityLivingData) data;
	}

	public static class GroupData implements IEntityLivingData
	{
		public int type;

		public GroupData(int type)
		{
			this.type = type;
		}
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
	}

	protected void fall(float distance) {}

	protected void updateFallState(double distanceFallenThisTick, boolean onGround)
	{
		if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
		{
			super.updateFallState(distanceFallenThisTick, onGround);
		}
	}

	protected String getLivingSound()
	{
		return null;
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:entity.jellyfish.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:entity.jellyfish.hurt";
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public boolean handleWaterMovement()
	{
		return false;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(this.getNether())
		{
			this.isImmuneToFire = true;
			this.setSize(0.6F, 0.6F);
		}
		else
		{
			this.isImmuneToFire = false;
			this.setSize(0.4F, 0.4F);
		}

		// Swimming AI
		if(!this.getNether())
		{
			if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water)
			{
				this.swimTimer++;

				if(!this.worldObj.isRemote)
				{
					if(this.swimTimer > 12)
					{
						this.motionX = 0.0D;
						this.motionY -= 0.05D;
						this.motionY *= 0.3D;
						this.motionZ = 0.0D;
					}
					else
					{
						this.motionX = 0.0D;
						this.motionY += 0.065D;
						this.motionY *= 0.7D;
						this.motionZ = 0.0D;
					}
				}
			}
			else
			{
				if(!this.worldObj.isRemote)
				{
					this.motionX = 0.0D;
					this.motionY -= 0.08D;
					this.motionY *= 0.98D;
					this.motionZ = 0.0D;
				}
			}

			if(this.swimTimer >= 60)
			{
				if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
				{
					this.swimTimer = this.worldObj.rand.nextInt(4);
				}
				else if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
				{
					this.swimTimer = this.worldObj.rand.nextInt(6) + 3;
				}
				else
				{
					this.swimTimer = this.worldObj.rand.nextInt(7);
				}
			}

			// Animations
			if(this.swimTimer > 39 && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water)
			{
				this.width = this.width + 0.025F;
			}
			else if(this.swimTimer > 0 && this.swimTimer < 20 && this.width > 2.0F && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water)
			{
				this.width = this.width - 0.15F;
			}
			if(this.width < 2.0F)
			{
				this.width = 2.0F;
			}

			if(this.swimTimer > 39 && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water)
			{
				this.height = this.height - 0.0125F;
			}
			else if(this.swimTimer > 0 && this.swimTimer < 20 && this.height < 2.0F && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water)
			{
				this.height = this.height + 0.075F;
			}
			if(this.height > 2.0F)
			{
				this.height = 2.0F;
			}

			if(this.swimTimer == 20)
			{
				this.width = 2.0F;
				this.height = 2.0F;
			}
		}
		else
		{	// Nether
			this.swimTimer++;

			if(!this.worldObj.isRemote)
			{
				if(this.swimTimer > 12)
				{
					this.motionY -= 0.05D;
					this.motionY *= 0.3D;
					this.motionZ = 0.0D;
					this.motionX = 0.0D;
				}
				else
				{
					this.motionY += 0.065D;
					this.motionY *= 0.7D;
					this.floatTowardsLava();
				}
				floatingCD++; // this is meant to overflow, resulting in an update period of 256 ticks
				if(floatingCD == 0) {
					floating = worldObj.rand.nextFloat() * 11F - 1;
				}
				if(motionY * ((lavaHeight + floating) - posY) < 0) { // adjust floating direction
					motionY *= -1D;
				}
			}

			if(this.swimTimer >= 60)
			{
				if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)).isNormalCube())
				{
					this.swimTimer = this.worldObj.rand.nextInt(4);
				}
				else if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)).isNormalCube())
				{
					this.swimTimer = this.worldObj.rand.nextInt(6) + 3;
				}
				else
				{
					this.swimTimer = this.worldObj.rand.nextInt(7);
				}
			}

			// Animations
			if(this.swimTimer > 39)
			{
				this.width = this.width + 0.025F;
			}
			else if(this.swimTimer > 0 && this.swimTimer < 20 && this.width > 2.0F)
			{
				this.width = this.width - 0.15F;
			}
			if(this.width < 2.0F)
			{
				this.width = 2.0F;
			}

			if(this.swimTimer > 39)
			{
				this.height = this.height - 0.0125F;
			}
			else if(this.swimTimer > 0 && this.swimTimer < 20 && this.height < 2.0F)
			{
				this.height = this.height + 0.075F;
			}
			if(this.height > 2.0F)
			{
				this.height = 2.0F;
			}

			if(this.swimTimer == 20)
			{
				this.width = 2.0F;
				this.height = 2.0F;
			}
		}
	}
	
	private void floatTowardsLava() {
		double t = 2 * Math.PI * Math.random();
		double r = 2.0D;
		int x = (int) (r * Math.cos(t));
		int z = (int) (r * Math.sin(t));
		int y = findLavaY((int) posX + x, (int) posY, (int) posZ + z);
		if(y > 0) {
			lavaHeight = y;
			motionX = Math.random() * 0.2D * x;
			motionZ = Math.random() * 0.2D * z;
		}
	}

	public void onEntityUpdate()
	{
		int i = this.getAir();
		super.onEntityUpdate();

		if(this.isEntityAlive() && !this.getNether() && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water) // Nether Jellyfish don't "drown"
		{
			--i;
			this.setAir(i);

			if(this.getAir() <= -20)
			{
				this.setAir(0);
				this.attackEntityFrom(DamageSource.drown, 2.0F);
			}
		}
		else
		{
			this.setAir(300);
		}
	}

	protected Item getDropItem()
	{
		return WildMobsModItems.slimeDrop;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		int j = this.rand.nextInt(3 + looting) - 1;

		if(this.getNether() == false)
		{
			for(int k = 0; k < j; ++k)
			{
				this.dropItem(WildMobsModItems.slimeDrop, 1);
			}
		}
		else
		{
			for(int k = 0; k < j; ++k)
			{
				this.dropItem(WildMobsModItems.magmaCreamDrop, 1);
			}
		}
	}

	public void moveEntityWithHeading(float strafe, float forward)
	{
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}

	public boolean getCanSpawnHere()
	{
		if(worldObj.provider instanceof WorldProviderHell)
		{
			if(super.getCanSpawnHere()) {
				int x = (int) posX, y = (int) posY, z = (int) posZ;
//				Block b1 = worldObj.getBlock(x, y, z);
//				if(b1 instanceof BlockLiquid && b1.getMaterial() == Material.lava) {
//					return true;
//				}
//				Block b2 = worldObj.getBlock(x, y, z);
//				return b2 instanceof BlockLiquid && b2.getMaterial() == Material.lava;
				
				// Workaround to avoid custom spawnHandler (natural spawning in liquids other than water is unsupported by the Vanilla/FML frameworks); Checks if the Jellyfish will fall into a lava lake
				int lava = findLavaY(x, y, z);
				if(lava > 0) {
					return worldObj.checkNoEntityCollision(this.boundingBox.getOffsetBoundingBox(0.0D, lava - posY, 0.0D));
				}
				
			}
			return false;
		}
		else
		{
			return this.posY > 0.0D && this.posY < 55.0D && super.getCanSpawnHere();
		}
	}
	
	private int findLavaY(int x, int y, int z) {
		Block b0 = null;
		while(y > 0 && ((b0 = worldObj.getBlock(x, y, z)) == null || b0.isAir(worldObj, x, y, z))) y--;
		return (b0 instanceof BlockLiquid && b0.getMaterial() == Material.lava) ? y : -1;
	}

	public String getCommandSenderName()
	{
		if(this.hasCustomNameTag())
		{
			return this.getCustomNameTag();
		}
		else
		{
			if(this.getNether() == false)
			{
				return StatCollector.translateToLocal("entity.wildmobsmod.Jellyfish.name");
			}
			else
			{
				return StatCollector.translateToLocal("entity.wildmobsmod.NetherJellyfish.name");
			}
		}
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		ItemStack bucket = null;
		if(itemstack != null && itemstack.getItem() == Items.water_bucket && !this.getNether())
		{
			bucket = new ItemStack(WildMobsModItems.jellyfish, 1, this.getSkin());
		}
		else if(itemstack != null && itemstack.getItem() == Items.lava_bucket && this.getNether())
		{
			bucket = new ItemStack(WildMobsModItems.netherJellyfish, 1, this.getSkin());
		}
		if(bucket != null)
		{
			if(itemstack.stackSize-- == 1)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, bucket);
			}
			else if(!player.inventory.addItemStackToInventory(bucket))
			{
				player.dropPlayerItemWithRandomChoice(bucket, false);
			}
			this.setDead();
			return true;
		}
		else
		{
			return super.interact(player);
		}
	}

	protected boolean isValidLightLevel()
	{
		return true;
	}
}

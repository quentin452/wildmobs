package com.wildmobsmod.entity.passive.goose;

import com.wildmobsmod.entity.ai.EntityAILookIdleGoose;
import com.wildmobsmod.entity.ai.EntityAIWanderGoose;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import fr.iamacat.optimizationsandtweaks.utils.apache.commons.math3.util.FastMath;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class EntityGoose extends EntityCreature implements IAnimals
{
    //
    // Try adding some new use(s) to geese as I want them to be more than just
    // food.
    //

    private ChunkCoordinates spawnPosition;
    private int fallTimer;
    public int animation;
    public int feedingAnimation;

    public EntityGoose(World world)
    {
        super(world);
        this.setSize(0.5F, 0.7F);
        this.tasks.addTask(0, new EntityAIWanderGoose(this, 1.0D));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(2, new EntityAILookIdleGoose(this));
        this.setIsIdle(false);
        this.feedingAnimation = 0;
    }

    public int getMaxSpawnedInChunk()
    {
        return WildMobsMod.GOOSE_CONFIG.getMaxPackSize();
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, (byte) 0);
        this.dataWatcher.addObject(22, (byte) 0);
        this.dataWatcher.addObject(23, (byte) 0);
        this.dataWatcher.addObject(24, (byte) 0);
        this.dataWatcher.addObject(25, (byte) 0);
    }

    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("IsIdle", this.getIsIdle());
        nbt.setInteger("FlyingState", this.getFlyingState());
        nbt.setInteger("FlyingDirectionX", this.getFlyingDirectionX());
        nbt.setInteger("FlyingDirectionZ", this.getFlyingDirectionZ());
        nbt.setInteger("FlyingTime", this.getFlyingTime());
    }

    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        this.setIsIdle(nbt.getBoolean("IsIdle"));
        this.setFlyingState(nbt.getInteger("FlyingState"));
        this.setFlyingDirectionX(nbt.getInteger("FlyingDirectionX"));
        this.setFlyingDirectionZ(nbt.getInteger("FlyingDirectionZ"));
        this.setFlyingTime(nbt.getInteger("FlyingTime"));
    }

    public boolean getIsIdle()
    {
        return (this.dataWatcher.getWatchableObjectByte(20) & 1) != 0;
    }

    public void setIsIdle(boolean flag)
    {
        if(flag)
        {
            this.dataWatcher.updateObject(20, (byte) 1);
        }
        else
        {
            this.dataWatcher.updateObject(20, (byte) 0);
        }
    }

    public int getFlyingState()
    {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public void setFlyingState(int state)
    {
        this.dataWatcher.updateObject(21, (byte) state);
    }

    public int getFlyingDirectionX()
    {
        return this.dataWatcher.getWatchableObjectByte(22);
    }

    public void setFlyingDirectionX(int x)
    {
        this.dataWatcher.updateObject(22, (byte) x);
    }

    public int getFlyingDirectionZ()
    {
        return this.dataWatcher.getWatchableObjectByte(23);
    }

    public void setFlyingDirectionZ(int z)
    {
        this.dataWatcher.updateObject(23, (byte) z);
    }

    public int getFlyingTime()
    {
        return this.dataWatcher.getWatchableObjectByte(24);
    }

    public void setFlyingTime(int time)
    {
        this.dataWatcher.updateObject(24, (byte) time);
    }

    public boolean isAIEnabled()
    {
        return true;
    }

    public boolean isInWater()
    {
        return false;
    }

    public boolean allowLeashing()
    {
        return false;
    }

    protected void fall(float distance) {}

    protected String getLivingSound()
    {
        return "wildmobsmod:entity.goose.say";
    }

    protected String getHurtSound()
    {
        return "wildmobsmod:entity.goose.hurt";
    }

    protected String getDeathSound()
    {
        return "wildmobsmod:entity.goose.hurt";
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected int getExperiencePoints(EntityPlayer player)
    {
        return 1 + this.worldObj.rand.nextInt(3);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
    }

    protected void updateAITasks() {
        if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition.posX, this.spawnPosition.posY, this.spawnPosition.posZ) || this.spawnPosition.posY < 1)) {
                this.spawnPosition = null;

        }

        double d0;
        double d2;

        if (this.getFlyingState() == 1 || this.getFlyingState() == 2 || this.getFlyingState() == 3) {
            d0 = (int) this.posX + (double) this.getFlyingDirectionX() + 0.5D - this.posX;
            d2 = (int) this.posZ + (double) this.getFlyingDirectionZ() + 0.5D - this.posZ;
            this.updateMotion(d0, d2, 0.4D);
        } else if (this.getFlyingState() != 1 && this.getFlyingState() != 2 && this.getFlyingState() != 3 && !this.onGround && (this.spawnPosition != null)) {
                d0 = (double) this.spawnPosition.posX + 0.5D - this.posX;
                d2 = (double) this.spawnPosition.posZ + 0.5D - this.posZ;

                double speed = this.getIsIdle() ? 0.07D : 0.04D;
                this.updateMotion(d0, d2, speed);

        }

        super.updateAITasks();
    }

    private void updateMotion(double d0, double d2, double speed) {
        this.motionX += (Math.signum(d0) * speed - this.motionX) * 0.1500000014901161;
        this.motionZ += (Math.signum(d2) * speed - this.motionZ) * 0.1500000014901161;

        float newRotation = (float) (FastMath.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
        float rotationDiff = MathHelper.wrapAngleTo180_float(newRotation - this.rotationYaw);

        float maxRotationSpeed = 2.0F;
        if (rotationDiff > maxRotationSpeed) {
            rotationDiff = maxRotationSpeed;
        } else if (rotationDiff < -maxRotationSpeed) {
            rotationDiff = -maxRotationSpeed;
        }

        this.rotationYaw += rotationDiff;

        this.moveForward = 0.05F;
    }

    protected void checkBlockCollision() {
        int blockX = MathHelper.floor_double(this.posX);
        int blockY = MathHelper.floor_double(this.posY);
        int blockZ = MathHelper.floor_double(this.posZ);

        int blockOffsetX = 0;
        int blockOffsetZ = 0;

        if (this.rotationYaw < 45 || this.rotationYaw >= 315) {
            blockOffsetZ = 1;
        } else if (this.rotationYaw < 135) {
            blockOffsetX = -1;
        } else if (this.rotationYaw < 225) {
            blockOffsetZ = -1;
        } else if (this.rotationYaw < 315) {
            blockOffsetX = 1;
        }

        if (this.worldObj.getBlock(blockX + blockOffsetX, blockY, blockZ + blockOffsetZ).isNormalCube()) {
            this.setFlyingState(0);
        }
    }
    private int isJumpingOutOfWaterTimer = 0;
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.onGround) {
            if (this.rand.nextFloat() < 0.02F && this.getIsIdle()) {
                this.setIsIdle(false);
            } else if (this.rand.nextFloat() < 0.01F && !this.getIsIdle()) {
                this.setIsIdle(true);
            }
        } else {
            if (this.rand.nextFloat() < 0.01F && this.getIsIdle()) {
                this.setIsIdle(false);
            } else if (this.rand.nextFloat() < 0.05F && !this.getIsIdle()) {
                this.setIsIdle(true);
            }
            this.fallTimer = 0;
        }

        int flyingState = this.getFlyingState();
        int flyingTime = this.getFlyingTime();

        if (flyingState == 1) {
            if (!this.worldObj.isRemote) {
                if (flyingTime >= 120) {
                    this.setFlyingState(2);
                } else {
                    flyingTime++;
                    this.setFlyingTime(flyingTime);
                }
            }

            this.motionY = 1.0D;
            this.motionY *= 0.2D;

            if (this.rand.nextFloat() < 0.025F && flyingTime >= 10 && !this.worldObj.isRemote) {
                this.setFlyingState(2);
            }

            this.fallTimer = 10;
            this.checkBlockCollision();
        } else if (flyingState == 2) {
            this.setFlyingTime(0);
            this.motionY = 0.0D;
            this.motionY *= 0.2D;

            if (this.rand.nextFloat() < 0.025F && !this.worldObj.isRemote) {
                this.setFlyingState(3);
            }

            this.fallTimer = 10;
            this.checkBlockCollision();
        } else if (flyingState == 3) {
            this.setFlyingTime(0);
            this.motionY = -1.0D;
            this.motionY *= 0.2D;

            int blockX = MathHelper.floor_double(this.posX);
            int blockY = MathHelper.floor_double(this.posY) - 1;
            int blockZ = MathHelper.floor_double(this.posZ);
            Block blockBelow = this.worldObj.getBlock(blockX, blockY, blockZ);

            if (blockBelow.getMaterial() == Material.water && !this.worldObj.isRemote) {
                this.setFlyingState(0);
            } else if (!this.getIsIdle()) {
                this.setFlyingState(0);
            }

            if (this.onGround && !this.worldObj.isRemote) {
                this.setFlyingState(0);
            }

            this.fallTimer = 10;
            this.checkBlockCollision();
        } else {
            this.setFlyingTime(0);
            int blockX = MathHelper.floor_double(this.posX);
            int blockY = MathHelper.floor_double(this.posY);
            int blockZ = MathHelper.floor_double(this.posZ);
            Block blockAtCurrentPos = this.worldObj.getBlock(blockX, blockY, blockZ);

            if (blockAtCurrentPos.getMaterial() == Material.water) {
                int blockYAbove = blockY + 1;
                Block blockAbove = this.worldObj.getBlock(blockX, blockYAbove, blockZ);

                if (blockAbove.getMaterial() != Material.water) {
                    this.motionY = 0.015D;
                    this.motionY *= 0.7D;

                    if (this.rand.nextFloat() < 0.00015F && !this.worldObj.isRemote) {
                        this.setFlyingState(1);
                        this.setFlyingDirectionX(this.rand.nextInt(2) == 0
                            ? 1000 + this.rand.nextInt(1000)
                            : 1000 - this.rand.nextInt(1000));

                        this.setFlyingDirectionZ(this.rand.nextInt(2) == 0
                            ? 1000 + this.rand.nextInt(1000)
                            : 1000 - this.rand.nextInt(1000));
                        this.playSound("wildmobsmod:entity.goose.flying", this.getSoundVolume(), this.getSoundPitch());
                    }

                    this.fallTimer = 0;
                } else {
                    this.motionY = 0.1D;
                    this.motionY *= 0.7D;
                    this.fallTimer = 0;
                }
            } else {
                if (this.fallTimer < 5 && !this.worldObj.getBlock(blockX, blockY - 1, blockZ).isNormalCube()) {
                    this.fallTimer++;
                }
                this.motionY *= 1.2D;
            }
        }
        if (this.isInWater()) {
            this.animation = 0;
            return;
        }

        if (this.isJumpingOutOfWaterTimer > 0) {
            this.isJumpingOutOfWaterTimer--;
        } else {
            // Animations
            if (flyingState == 1) {
                this.animation = 3;
            } else if (flyingState == 2) {
                this.animation = 4;
                this.isJumpingOutOfWaterTimer = 10;
            } else if (flyingState == 3) {
                this.animation = 5;
            } else if (this.fallTimer >= 5) {
                this.animation = 1;
            } else {
                int blockX = MathHelper.floor_double(this.posX);
                int blockY = MathHelper.floor_double(this.posY);
                int blockZ = MathHelper.floor_double(this.posZ);
                Block blockAtCurrentPos = this.worldObj.getBlock(blockX, blockY, blockZ);

                Block blockAbove = this.worldObj.getBlock(blockX, blockY, blockZ);

                if (blockAtCurrentPos.getMaterial() == Material.water) {
                    if (blockAbove.getMaterial() != Material.water) {
                        this.animation = 2;
                    } else {
                        this.animation = 0;
                    }
                } else {
                    this.animation = 0;
                }
            }
        }
    }

    public void onUpdate()
    {
        super.onUpdate();

        if(this.rand.nextFloat() < 0.004F && this.feedingAnimation <= 0)
        {
            this.feedingAnimation = 1;
        }
        else if(this.feedingAnimation > 0 && this.feedingAnimation < 30)
        {
            this.feedingAnimation++;
        }
        else if(this.feedingAnimation >= 30)
        {
            this.feedingAnimation = 0;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if(!this.worldObj.isRemote)
            {
                this.setFlyingState(1);
                if(this.rand.nextInt(2) == 0)
                {
                    this.setFlyingDirectionX(10 + this.rand.nextInt(10));
                }
                else
                {
                    this.setFlyingDirectionX(-10 - this.rand.nextInt(10));
                }

                if(this.rand.nextInt(2) == 0)
                {
                    this.setFlyingDirectionZ(10 + this.rand.nextInt(10));
                }
                else
                {
                    this.setFlyingDirectionZ(-10 - this.rand.nextInt(10));
                }
                this.playSound("wildmobsmod:entity.goose.flying", this.getSoundVolume(), this.getSoundPitch());
            }
            return super.attackEntityFrom(source, amount);
        }
    }

    protected Item getDropItem()
    {
        return Items.feather;
    }

    protected void dropFewItems(boolean playerkill, int looting)
    {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);

        for(int k = 0; k < j; ++k)
        {
            this.dropItem(Items.feather, 1);
        }

        if(this.isBurning())
        {
            this.dropItem(WildMobsModItems.cookedGoose, 1);
        }
        else
        {
            this.dropItem(WildMobsModItems.rawGoose, 1);
        }
    }
    @Override
    public boolean getCanSpawnHere() {
        if (!super.getCanSpawnHere()) {
            return false;
        }

        BiomeGenBase biome = worldObj.getBiomeGenForCoords((int)this.posX, (int)this.posZ);
        if (biome == BiomeGenBase.beach) {
            return true;
        }

        return false;
    }
}

package com.wildmobsmod.items;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.entity.passive.deer.EntityDeer;
import com.wildmobsmod.entity.passive.fox.EntityFox;
import com.wildmobsmod.entity.passive.jellyfish.EntityJellyfish;
import com.wildmobsmod.entity.passive.ocelot.EntityWMOcelot;
import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemWildMobsMonsterPlacer extends ItemMonsterPlacer
{
	@SideOnly(Side.CLIENT)
	private IIcon theIcon;
	protected final int colorBase;
	protected final int colorSpots;
	protected final int entityToSpawnVariant;
	protected final boolean booleanType;
	protected final String entityToSpawnName;
	protected final String entityToSpawnNameFull;
	protected final String spawnEggName;

	public ItemWildMobsMonsterPlacer(String parEntityToSpawnName, int parPrimaryColor, int parSecondaryColor, int parEntityToSpawnVariant, boolean parBooleanType)
	{
		this(parEntityToSpawnName, parPrimaryColor, parSecondaryColor, parEntityToSpawnVariant, parBooleanType, "");
//		System.out.println("Spawn egg constructor for " + entityToSpawnName);
	}

	public ItemWildMobsMonsterPlacer(String parEntityToSpawnName, int parPrimaryColor, int parSecondaryColor, int parEntityToSpawnVariant, boolean parBooleanType, String parSpawnEggName)
	{
		setHasSubtypes(false);
		setMaxStackSize(64);
		setCreativeTab(WildMobsMod.TAB_WILDMOBS);
		entityToSpawnName = parEntityToSpawnName;
		entityToSpawnNameFull = WildMobsMod.MODID + "." + entityToSpawnName;
		colorBase = parPrimaryColor;
		colorSpots = parSecondaryColor;
		entityToSpawnVariant = parEntityToSpawnVariant;
		booleanType = parBooleanType;
		spawnEggName = parSpawnEggName;
		setTextureName("spawn_egg");
		setUnlocalizedName("monsterPlacer");
//		System.out.println("Spawn egg constructor for " + entityToSpawnName);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		if(world.isRemote) return true;
		Block block = world.getBlock(x, y, z);
		x += Facing.offsetsXForSide[side];
		y += Facing.offsetsYForSide[side];
		z += Facing.offsetsZForSide[side];
		double yOffset = (side == 1 && world.getBlock(x, y, z).getRenderType() == 11) ? 0.5D : 0.0D;
		EntityLiving entity = spawnEntity(world, x + 0.5D, y + yOffset, z + 0.5D);
		if(entity != null)
		{
			if(stack.hasDisplayName())
			{
				entity.setCustomNameTag(stack.getDisplayName());
			}
			if(!player.capabilities.isCreativeMode)
			{
				--stack.stackSize;
			}
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed creating entity from spawnegg!"));
		}
		return true;
	}

	/**
	 * only call this on the server side!
	 */
	private EntityLiving spawnEntity(World world, double parX, double parY, double parZ)
	{
		if(!EntityList.stringToClassMapping.containsKey(entityToSpawnNameFull)) {
			FMLLog.log(WildMobsMod.MODID, Level.ERROR, "Spawnegg couldn't identify entity: " + entityToSpawnName);
			return null;
		}
		EntityLiving entityToSpawn = (EntityLiving) EntityList.createEntityByName(entityToSpawnNameFull, world);
		if(entityToSpawn == null) {
			FMLLog.log(WildMobsMod.MODID, Level.ERROR, "Failed to create Entity from Spawnegg: " + entityToSpawnName);
			return null;
		}
		entityToSpawn.setLocationAndAngles(parX, parY, parZ, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
		world.spawnEntityInWorld(entityToSpawn);
		entityToSpawn.onSpawnWithEgg((IEntityLivingData) null);
		if(entityToSpawn instanceof EntityJellyfish)
		{
			((EntityJellyfish) entityToSpawn).setNether(booleanType);
			if(booleanType == false)
			{
				((EntityJellyfish) entityToSpawn).setSkin(world.rand.nextInt(6));
			}
			else
			{
				((EntityJellyfish) entityToSpawn).setSkin(entityToSpawnVariant);
			}
		}
		else if(entityToSpawn instanceof ISkinnedEntity)
		{
			((ISkinnedEntity) entityToSpawn).setSkin(entityToSpawnVariant);
		}
		entityToSpawn.playLivingSound();
		return entityToSpawn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item parItem, CreativeTabs parTab, List parList)
	{
		parList.add(new ItemStack(parItem, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int parColorType)
	{
		return (parColorType == 0) ? colorBase : colorSpots;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon(getIconString() + "_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int parDamageVal, int parRenderPass)
	{
		return parRenderPass > 0 ? theIcon : super.getIconFromDamageForRenderPass(parDamageVal, parRenderPass);
	}

	public String getItemStackDisplayName(ItemStack stack)
	{
		String baseName = StatCollector.translateToLocal(this.getUnlocalizedName() + ".name").trim();
		if(!this.spawnEggName.isEmpty())
		{
			return baseName + " " + StatCollector.translateToLocal("entity." + WildMobsMod.MODID + "." + spawnEggName + ".name");
		}
		if(!this.entityToSpawnName.isEmpty())
		{
			return baseName + " " + StatCollector.translateToLocal("entity." + WildMobsMod.MODID + "." + entityToSpawnName + ".name");
		}
		return baseName;
	}
}

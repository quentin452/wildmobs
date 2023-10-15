package com.wildmobsmod.entity.monster.wizard;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderWizard extends RenderLiving
{
	private static final ResourceLocation wizardTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wizard/wizard.png");
	private static final ResourceLocation voldemortTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wizard/voldemort.png");
	public ModelWizard wizardModel;

	public RenderWizard()
	{
		super(new ModelWizard(0.0F), 0.5F);
		this.wizardModel = (ModelWizard) this.mainModel;
	}

	public void doRender(EntityWizard wizard, double x, double y, double z, float f1, float f2)
	{
		ItemStack itemstack = wizard.getHeldItem();
		this.wizardModel.holdsItem = itemstack != null;
		super.doRender((EntityLiving) wizard, x, y, z, f1, f2);
	}

	protected void renderEquippedItems(EntityLiving living, float partialTickTime)
	{
		EntityWizard entitywizard = (EntityWizard) living;
		if(entitywizard.getHasArms() == false)
		{
		}
		else
		{
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			super.renderEquippedItems(living, partialTickTime);
			ItemStack itemstack = living.getHeldItem();
			ItemStack itemstack1 = living.func_130225_q(3);
			Item item;
			float f1;

			if(itemstack != null && itemstack.getItem() != null)
			{
				item = itemstack.getItem();
				GL11.glPushMatrix();

				if(this.mainModel.isChild)
				{
					f1 = 0.5F;
					GL11.glTranslatef(0.0F, 0.625F, 0.0F);
					GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
					GL11.glScalef(f1, f1, f1);
				}

				this.wizardModel.bipedRightArm.postRender(0.0625F);
				GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

				net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
				boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

				if(item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType())))
				{
					f1 = 0.5F;
					GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
					f1 *= 0.75F;
					GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(-f1, -f1, f1);
				}
				else if(item == Items.bow)
				{
					f1 = 0.625F;
					GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
					GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(f1, -f1, f1);
					GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				}
				else if(item.isFull3D())
				{
					f1 = 0.625F;

					if(item.shouldRotateAroundWhenRendering())
					{
						GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(0.0F, -0.125F, 0.0F);
					}

					GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
					GL11.glScalef(f1, -f1, f1);
					GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				}
				else
				{
					f1 = 0.375F;
					GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
					GL11.glScalef(f1, f1, f1);
					GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
				}

				float f2;
				int i;
				float f5;

				if(itemstack.getItem().requiresMultipleRenderPasses())
				{
					for(i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i)
					{
						int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
						f5 = (float) (j >> 16 & 255) / 255.0F;
						f2 = (float) (j >> 8 & 255) / 255.0F;
						float f3 = (float) (j & 255) / 255.0F;
						GL11.glColor4f(f5, f2, f3, 1.0F);
						this.renderManager.itemRenderer.renderItem(living, itemstack, i);
					}
				}
				else
				{
					i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
					float f4 = (float) (i >> 16 & 255) / 255.0F;
					f5 = (float) (i >> 8 & 255) / 255.0F;
					f2 = (float) (i & 255) / 255.0F;
					GL11.glColor4f(f4, f5, f2, 1.0F);
					this.renderManager.itemRenderer.renderItem(living, itemstack, 0);
				}

				GL11.glPopMatrix();
			}
		}
	}

	protected ResourceLocation getEntityTexture(EntityWizard wizard)
	{
		if(wizard.hasCustomNameTag() && "Voldemort".equals(wizard.getCustomNameTag()))
		{
			return voldemortTextures;
		}
		else
		{
			return wizardTextures;
		}
	}

	protected void preRenderCallback(EntityWizard wizard, float partialTickTime)
	{
		float f1 = 0.9375F;
		GL11.glScalef(f1, f1, f1);
	}

	@Override
	public void doRender(EntityLiving living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityWizard) living, x, y, z, f1, f2);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityWizard) living, partialTickTime);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase living, float partialTickTime)
	{
		this.renderEquippedItems((EntityWizard) living, partialTickTime);
	}

	@Override
	public void doRender(EntityLivingBase living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityWizard) living, x, y, z, f1, f2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityWizard) entity);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityWizard) entity, x, y, z, f1, f2);
	}
}

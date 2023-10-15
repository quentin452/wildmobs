package com.wildmobsmod.entity.monster.zomgus;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderZomgus extends RenderBiped
{
	private static final ResourceLocation zomgusTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/zombie/zomgus.png");
	private ModelBiped modelBiped;
	private ModelZomgus zomgusVillagerModel;
	protected ModelBiped modelBiped1;
	protected ModelBiped modelBiped2;
	protected ModelBiped modelZombieVillager1;
	protected ModelBiped modelZombieVillager2;
	private int field_82431_q = 1;

	public RenderZomgus()
	{
		super(new ModelZomgus(), 0.5F, 1.0F);
		this.modelBiped = this.modelBipedMain;
		this.zomgusVillagerModel = new ModelZomgus();
	}

	@Override
	protected void func_82421_b()
	{
		this.field_82423_g = new ModelZomgus(1.0F, 0.0F, true);
		this.field_82425_h = new ModelZomgus(0.5F, 0.0F, true);
		this.modelBiped1 = this.field_82423_g;
		this.modelBiped2 = this.field_82425_h;
	}

	protected int shouldRenderPass(EntityZomgus zomgus, int pass, float partialTickTime)
	{
		this.setModel(zomgus);
		return super.shouldRenderPass((EntityLiving) zomgus, pass, partialTickTime);
	}

	public void doRender(EntityZomgus zomgus, double x, double y, double z, float f1, float f2)
	{
		this.setModel(zomgus);
		super.doRender((EntityLiving) zomgus, x, y, z, f1, f2);
	}

	protected ResourceLocation getEntityTexture(EntityZomgus zomgus)
	{
		return zomgusTextures;
	}

	protected void renderEquippedItems(EntityZomgus zomgus, float scale)
	{
		this.setModel(zomgus);
		super.renderEquippedItems(zomgus, scale);
	}

	private void setModel(EntityZomgus zomgus)
	{
		if(zomgus.isVillager())
		{
			if(this.field_82431_q != this.zomgusVillagerModel.func_82897_a())
			{
				this.zomgusVillagerModel = new ModelZomgus();
				this.field_82431_q = this.zomgusVillagerModel.func_82897_a();
				this.modelZombieVillager1 = new ModelZombieVillager(1.0F, 0.0F, true);
				this.modelZombieVillager2 = new ModelZombieVillager(0.5F, 0.0F, true);
			}

			this.mainModel = this.zomgusVillagerModel;
			this.field_82423_g = this.modelZombieVillager1;
			this.field_82425_h = this.modelZombieVillager2;
		}
		else
		{
			this.mainModel = this.modelBiped;
			this.field_82423_g = this.modelBiped1;
			this.field_82425_h = this.modelBiped2;
		}

		this.modelBipedMain = (ModelBiped) this.mainModel;
	}

	protected void rotateCorpse(EntityZomgus zomgus, float f1, float f2, float f3)
	{
		if(zomgus.isConverting())
		{
			f2 += (float) (Math.cos((double) zomgus.ticksExisted * 3.25D) * Math.PI * 0.25D);
		}

		super.rotateCorpse(zomgus, f1, f2, f3);
	}

	@Override
	protected void renderEquippedItems(EntityLiving living, float partialTickTime)
	{
		this.renderEquippedItems((EntityZomgus) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving living)
	{
		return this.getEntityTexture((EntityZomgus) living);
	}
	
	@Override
	public void doRender(EntityLiving living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityZomgus) living, x, y, z, f1, f2);
	}

	@Override
	protected int shouldRenderPass(EntityLiving living, int pass, float f)
	{
		return this.shouldRenderPass((EntityZomgus) living, pass, f);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase living, int pass, float f)
	{
		return this.shouldRenderPass((EntityZomgus) living, pass, f);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase living, float f)
	{
		this.renderEquippedItems((EntityZomgus) living, f);
	}

	@Override
	protected void rotateCorpse(EntityLivingBase living, float f1, float f2, float f3)
	{
		this.rotateCorpse((EntityZomgus) living, f1, f2, f3);
	}

	@Override
	public void doRender(EntityLivingBase living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityZomgus) living, x, y, z, f1, f2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityZomgus) entity);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityZomgus) entity, x, y, z, f1, f2);
	}
}

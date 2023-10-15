package com.wildmobsmod.entity.monster.wizard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelWizard extends ModelVillager
{
	public boolean holdsItem;
	private ModelRenderer modelRenderer = (new ModelRenderer(this)).setTextureSize(64, 128);
	private ModelRenderer witchHat;
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftArm;
	public int heldItemLeft;
	public int heldItemRight;

	public ModelWizard(float scale)
	{
		super(scale, 0.0F, 64, 128);
		this.witchHat = (new ModelRenderer(this)).setTextureSize(64, 128);
		this.witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
		this.witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
		this.villagerHead.addChild(this.witchHat);
		this.bipedRightArm = (new ModelRenderer(this)).setTextureSize(64, 128);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.setTextureOffset(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
		this.bipedLeftArm = (new ModelRenderer(this)).setTextureSize(64, 128);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.setTextureOffset(40, 46).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
		ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(64, 128);
		modelrenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
		modelrenderer.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
		modelrenderer.rotateAngleX = -0.05235988F;
		modelrenderer.rotateAngleZ = 0.02617994F;
		this.witchHat.addChild(modelrenderer);
		ModelRenderer modelrenderer1 = (new ModelRenderer(this)).setTextureSize(64, 128);
		modelrenderer1.setRotationPoint(1.75F, -4.0F, 2.0F);
		modelrenderer1.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
		modelrenderer1.rotateAngleX = -0.10471976F;
		modelrenderer1.rotateAngleZ = 0.05235988F;
		modelrenderer.addChild(modelrenderer1);
		ModelRenderer modelrenderer2 = (new ModelRenderer(this)).setTextureSize(64, 128);
		modelrenderer2.setRotationPoint(1.75F, -2.0F, 2.0F);
		modelrenderer2.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
		modelrenderer2.rotateAngleX = -0.20943952F;
		modelrenderer2.rotateAngleZ = 0.10471976F;
		modelrenderer1.addChild(modelrenderer2);
	}

	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float scale)
	{
		EntityWizard entitywizard = (EntityWizard) entity;
		this.setRotationAngles(f1, f2, f3, f4, f5, scale, entity);
		this.villagerHead.render(scale);
		this.villagerBody.render(scale);
		this.rightVillagerLeg.render(scale);
		this.leftVillagerLeg.render(scale);
		if(entitywizard.getHasArms() == false)
		{
			this.villagerArms.render(scale);
		}
		else
		{
			this.bipedRightArm.render(scale);
			this.bipedLeftArm.render(scale);
		}
	}

	public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float scale, Entity entity)
	{
		super.setRotationAngles(f1, f2, f3, f4, f5, scale, entity);
		this.bipedRightArm.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 2.0F * f2 * 0.5F;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 2.0F * f2 * 0.5F;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;

		this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);

		this.bipedRightArm.rotateAngleZ += MathHelper.cos(f3 * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(f3 * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(f3 * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(f3 * 0.067F) * 0.05F;
	}
}

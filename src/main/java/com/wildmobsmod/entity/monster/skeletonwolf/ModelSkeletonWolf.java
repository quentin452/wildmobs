package com.wildmobsmod.entity.monster.skeletonwolf;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelSkeletonWolf extends ModelBase
{
	public ModelRenderer wolfHeadMain;
	public ModelRenderer wolfBody;
	public ModelRenderer wolfLeg1;
	public ModelRenderer wolfLeg2;
	public ModelRenderer wolfLeg3;
	public ModelRenderer wolfLeg4;
	public ModelRenderer wolfTail;
	public ModelRenderer wolfMane;

	public ModelSkeletonWolf()
	{
		float f = 0.0F;
		float f1 = 13.5F;
		this.wolfHeadMain = new ModelRenderer(this, 0, 0);
		this.wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, f);
		this.wolfHeadMain.setRotationPoint(-1.0F, f1, -7.0F);
		this.wolfBody = new ModelRenderer(this, 18, 14);
		this.wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, f);
		this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
		this.wolfMane = new ModelRenderer(this, 21, 0);
		this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, f);
		this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
		this.wolfLeg1 = new ModelRenderer(this, 0, 18);
		this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
		this.wolfLeg2 = new ModelRenderer(this, 0, 18);
		this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
		this.wolfLeg3 = new ModelRenderer(this, 0, 18);
		this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
		this.wolfLeg4 = new ModelRenderer(this, 0, 18);
		this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
		this.wolfTail = new ModelRenderer(this, 9, 18);
		this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
		this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, f);
		this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, f);
		this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, f);
	}

	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float scale)
	{
		super.render(entity, f1, f2, f3, f4, f5, scale);
		this.setRotationAngles(f1, f2, f3, f4, f5, scale, entity);

		this.wolfHeadMain.renderWithRotation(scale);
		this.wolfBody.render(scale);
		this.wolfLeg1.render(scale);
		this.wolfLeg2.render(scale);
		this.wolfLeg3.render(scale);
		this.wolfLeg4.render(scale);
		this.wolfTail.render(scale);
		this.wolfMane.render(scale);
	}

	public void setLivingAnimations(EntityLivingBase living, float f1, float f2, float f3)
	{
		this.wolfTail.rotateAngleZ = MathHelper.cos(f1 * 0.6662F) * 0.8F * f2;
		this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
		this.wolfBody.rotateAngleX = ((float) Math.PI / 2F);
		this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
		this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
		this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
		this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
		this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
		this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
		this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
		this.wolfLeg1.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
		this.wolfLeg2.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
		this.wolfLeg3.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
		this.wolfLeg4.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
		this.wolfTail.rotateAngleX = 0.5393804F;
	}

	public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		super.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		this.wolfHeadMain.rotateAngleX = f5 / (180F / (float) Math.PI);
		this.wolfHeadMain.rotateAngleY = f4 / (180F / (float) Math.PI);
	}
}

package com.wildmobsmod.entity.passive.ocelot;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelWMOcelot extends ModelBase
{
	ModelRenderer ocelotBackLeftLeg;
	ModelRenderer ocelotBackRightLeg;
	ModelRenderer ocelotFrontLeftLeg;
	ModelRenderer ocelotFrontRightLeg;
	ModelRenderer ocelotTail;
	ModelRenderer ocelotTail2;
	ModelRenderer ocelotHead;
	ModelRenderer ocelotBody;
	int modelState = 1;

	public ModelWMOcelot()
	{
		this.setTextureOffset("head.main", 0, 0);
		this.setTextureOffset("head.nose", 0, 24);
		this.setTextureOffset("head.ear1", 0, 10);
		this.setTextureOffset("head.ear2", 6, 10);
		this.ocelotHead = new ModelRenderer(this, "head");
		this.ocelotHead.addBox("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
		this.ocelotHead.addBox("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
		this.ocelotHead.addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
		this.ocelotHead.addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
		this.ocelotHead.setRotationPoint(0.0F, 15.0F, -9.0F);
		this.ocelotBody = new ModelRenderer(this, 20, 0);
		this.ocelotBody.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
		this.ocelotBody.setRotationPoint(0.0F, 12.0F, -10.0F);
		this.ocelotTail = new ModelRenderer(this, 0, 15);
		this.ocelotTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
		this.ocelotTail.rotateAngleX = 0.9F;
		this.ocelotTail.setRotationPoint(0.0F, 15.0F, 8.0F);
		this.ocelotTail2 = new ModelRenderer(this, 4, 15);
		this.ocelotTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
		this.ocelotTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
		this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
		this.ocelotBackLeftLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
		this.ocelotBackLeftLeg.setRotationPoint(1.1F, 18.0F, 5.0F);
		this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
		this.ocelotBackRightLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
		this.ocelotBackRightLeg.setRotationPoint(-1.1F, 18.0F, 5.0F);
		this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
		this.ocelotFrontLeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
		this.ocelotFrontLeftLeg.setRotationPoint(1.2F, 13.8F, -5.0F);
		this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
		this.ocelotFrontRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
		this.ocelotFrontRightLeg.setRotationPoint(-1.2F, 13.8F, -5.0F);
	}

	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		this.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);

		if(this.isChild)
		{
			float f7 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.5F / f7, 1.5F / f7, 1.5F / f7);
			GL11.glTranslatef(0.0F, 10.0F * f6, 4.0F * f6);
			this.ocelotHead.render(f6);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f7, 1.0F / f7, 1.0F / f7);
			GL11.glTranslatef(0.0F, 24.0F * f6, 0.0F);
			this.ocelotBody.render(f6);
			this.ocelotBackLeftLeg.render(f6);
			this.ocelotBackRightLeg.render(f6);
			this.ocelotFrontLeftLeg.render(f6);
			this.ocelotFrontRightLeg.render(f6);
			this.ocelotTail.render(f6);
			this.ocelotTail2.render(f6);
			GL11.glPopMatrix();
		}
		else
		{
			this.ocelotHead.render(f6);
			this.ocelotBody.render(f6);
			this.ocelotTail.render(f6);
			this.ocelotTail2.render(f6);
			this.ocelotBackLeftLeg.render(f6);
			this.ocelotBackRightLeg.render(f6);
			this.ocelotFrontLeftLeg.render(f6);
			this.ocelotFrontRightLeg.render(f6);
		}
	}
	
	public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		this.ocelotHead.rotateAngleX = f5 / (180F / (float) Math.PI);
		this.ocelotHead.rotateAngleY = f4 / (180F / (float) Math.PI);

		if(this.modelState != 3)
		{
			this.ocelotBody.rotateAngleX = ((float) Math.PI / 2F);

			if(this.modelState == 2)
			{
				this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.0F * f2;
				this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F + 0.3F) * 1.0F * f2;
				this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI + 0.3F) * 1.0F * f2;
				this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.0F * f2;
				this.ocelotTail2.rotateAngleX = 1.7278761F + ((float) Math.PI / 10F) * MathHelper.cos(f1) * f2;
			}
			else
			{
				this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.0F * f2;
				this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.0F * f2;
				this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.0F * f2;
				this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.0F * f2;

				if(this.modelState == 1)
				{
					this.ocelotTail2.rotateAngleX = 1.7278761F + ((float) Math.PI / 4F) * MathHelper.cos(f1) * f2;
				}
				else
				{
					this.ocelotTail2.rotateAngleX = 1.7278761F + 0.47123894F * MathHelper.cos(f1) * f2;
				}
			}
		}
	}

	public void setLivingAnimations(EntityLivingBase living, float f1, float f2, float f3)
	{
		EntityWMOcelot ocelot = (EntityWMOcelot) living;
		this.ocelotBody.rotationPointY = 12.0F;
		this.ocelotBody.rotationPointZ = -10.0F;
		this.ocelotHead.rotationPointY = 15.0F;
		this.ocelotHead.rotationPointZ = -9.0F;
		this.ocelotTail.rotationPointY = 15.0F;
		this.ocelotTail.rotationPointZ = 8.0F;
		this.ocelotTail2.rotationPointY = 20.0F;
		this.ocelotTail2.rotationPointZ = 14.0F;
		this.ocelotFrontLeftLeg.rotationPointY = this.ocelotFrontRightLeg.rotationPointY = 13.8F;
		this.ocelotFrontLeftLeg.rotationPointZ = this.ocelotFrontRightLeg.rotationPointZ = -5.0F;
		this.ocelotBackLeftLeg.rotationPointY = this.ocelotBackRightLeg.rotationPointY = 18.0F;
		this.ocelotBackLeftLeg.rotationPointZ = this.ocelotBackRightLeg.rotationPointZ = 5.0F;
		this.ocelotTail.rotateAngleX = 0.9F;

		if(ocelot.isSneaking())
		{
			++this.ocelotBody.rotationPointY;
			this.ocelotHead.rotationPointY += 2.0F;
			++this.ocelotTail.rotationPointY;
			this.ocelotTail2.rotationPointY += -4.0F;
			this.ocelotTail2.rotationPointZ += 2.0F;
			this.ocelotTail.rotateAngleX = ((float) Math.PI / 2F);
			this.ocelotTail2.rotateAngleX = ((float) Math.PI / 2F);
			this.modelState = 0;
		}
		else if(ocelot.isSprinting())
		{
			this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
			this.ocelotTail2.rotationPointZ += 2.0F;
			this.ocelotTail.rotateAngleX = ((float) Math.PI / 2F);
			this.ocelotTail2.rotateAngleX = ((float) Math.PI / 2F);
			this.modelState = 2;
		}
		else if(ocelot.isSitting())
		{
			this.ocelotBody.rotateAngleX = ((float) Math.PI / 4F);
			this.ocelotBody.rotationPointY += -4.0F;
			this.ocelotBody.rotationPointZ += 5.0F;
			this.ocelotHead.rotationPointY += -3.3F;
			++this.ocelotHead.rotationPointZ;
			this.ocelotTail.rotationPointY += 8.0F;
			this.ocelotTail.rotationPointZ += -2.0F;
			this.ocelotTail2.rotationPointY += 2.0F;
			this.ocelotTail2.rotationPointZ += -0.8F;
			this.ocelotTail.rotateAngleX = 1.7278761F;
			this.ocelotTail2.rotateAngleX = 2.670354F;
			this.ocelotFrontLeftLeg.rotateAngleX = this.ocelotFrontRightLeg.rotateAngleX = -0.15707964F;
			this.ocelotFrontLeftLeg.rotationPointY = this.ocelotFrontRightLeg.rotationPointY = 15.8F;
			this.ocelotFrontLeftLeg.rotationPointZ = this.ocelotFrontRightLeg.rotationPointZ = -7.0F;
			this.ocelotBackLeftLeg.rotateAngleX = this.ocelotBackRightLeg.rotateAngleX = -((float) Math.PI / 2F);
			this.ocelotBackLeftLeg.rotationPointY = this.ocelotBackRightLeg.rotationPointY = 21.0F;
			this.ocelotBackLeftLeg.rotationPointZ = this.ocelotBackRightLeg.rotationPointZ = 1.0F;
			this.modelState = 3;
		}
		else
		{
			this.modelState = 1;
		}
	}
}

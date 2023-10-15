package com.wildmobsmod.entity.passive.direwolf;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelDireWolf extends ModelBase
{
	// fields
	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer mane;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer tail;
	ModelRenderer ear1;
	ModelRenderer ear2;
	ModelRenderer nose;

	protected static void convertToChild(ModelRenderer parent, ModelRenderer child)
	{
		// move child rotation point to be relative to parent
		child.rotationPointX -= parent.rotationPointX;
		child.rotationPointY -= parent.rotationPointY;
		child.rotationPointZ -= parent.rotationPointZ;
		// make rotations relative to parent
		child.rotateAngleX -= parent.rotateAngleX;
		child.rotateAngleY -= parent.rotateAngleY;
		child.rotateAngleZ -= parent.rotateAngleZ;
		// create relationship
		parent.addChild(child);
	}

	public ModelDireWolf()
	{
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.5F, -3.5F, -6F, 7, 7, 6);
		head.setRotationPoint(0F, 11F, -6F);
		head.setTextureSize(64, 64);
		setRotation(head, 0F, 0F, 0F);
		body = new ModelRenderer(this, 26, 18);
		body.addBox(-3.5F, 0F, -3F, 7, 10, 7);
		body.setRotationPoint(0F, 12F, -0.5F);
		body.setTextureSize(64, 64);
		setRotation(body, 1.422082F, 0F, 0F);
		mane = new ModelRenderer(this, 26, 0);
		mane.addBox(-5F, 0F, -5F, 10, 8, 9);
		mane.setRotationPoint(0F, 11F, -7F);
		mane.setTextureSize(64, 64);
		setRotation(mane, 1.570796F, 0F, 0F);
		leg1 = new ModelRenderer(this, 0, 24);
		leg1.mirror = true;
		leg1.addBox(-1.5F, 0F, -1.5F, 3, 8, 3);
		leg1.setRotationPoint(-2F, 16F, 7F);
		leg1.setTextureSize(64, 64);
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 24);
		leg2.addBox(-1.5F, 0F, -1.5F, 3, 8, 3);
		leg2.setRotationPoint(2F, 16F, 7F);
		leg2.setTextureSize(64, 64);
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 0, 24);
		leg3.mirror = true;
		leg3.addBox(-1.5F, 0F, -1.5F, 3, 9, 3);
		leg3.setRotationPoint(-2.5F, 16F, -4.5F);
		leg3.setTextureSize(64, 64);
		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 0, 24);
		leg4.addBox(-1.5F, 0F, -1.5F, 3, 9, 3);
		leg4.setRotationPoint(2.5F, 16F, -4.5F);
		leg4.setTextureSize(64, 64);
		setRotation(leg4, 0F, 0F, 0F);
		tail = new ModelRenderer(this, 13, 24);
		tail.addBox(-1.5F, 0F, -1.5F, 3, 9, 3);
		tail.setRotationPoint(0F, 11F, 8.7F);
		tail.setTextureSize(64, 64);
		setRotation(tail, 0.9069976F, 0F, 0F);
		ear1 = new ModelRenderer(this, 19, 14);
		ear1.addBox(0F, 0F, 0F, 2, 3, 1);
		ear1.setRotationPoint(-3F, 5.5F, -9F);
		ear1.setTextureSize(64, 64);
		setRotation(ear1, 0F, 0F, 0F);
		convertToChild(head, ear1);
		ear2 = new ModelRenderer(this, 19, 14);
		ear2.addBox(0F, 0F, 0F, 2, 3, 1);
		ear2.setRotationPoint(1F, 5.5F, -9F);
		ear2.setTextureSize(64, 64);
		setRotation(ear2, 0F, 0F, 0F);
		convertToChild(head, ear2);
		nose = new ModelRenderer(this, 0, 14);
		nose.addBox(0F, 0F, 0F, 4, 4, 5);
		nose.setRotationPoint(-2F, 10.5F, -15.5F);
		nose.setTextureSize(64, 64);
		setRotation(nose, 0F, 0F, 0F);
		convertToChild(head, nose);
	}

	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		super.render(entity, f1, f2, f3, f4, f5, f6);
		this.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		if(this.isChild)
		{
			float f7 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 6.7F * f6, 2.5F * f6);
			head.render(f6);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f7, 1.0F / f7, 1.0F / f7);
			GL11.glTranslatef(0.0F, 24.0F * f6, 0.0F);
			body.render(f6);
			mane.render(f6);
			leg1.render(f6);
			leg2.render(f6);
			leg3.render(f6);
			leg4.render(f6);
			tail.render(f6);
			GL11.glPopMatrix();
		}
		else
		{
			head.render(f6);
			body.render(f6);
			mane.render(f6);
			leg1.render(f6);
			leg2.render(f6);
			leg3.render(f6);
			leg4.render(f6);
			tail.render(f6);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setLivingAnimations(EntityLivingBase living, float f1, float f2, float f3)
	{
		EntityDireWolf entitydirewolf = (EntityDireWolf) living;

		if(entitydirewolf.isAngry())
		{
			this.tail.rotateAngleY = 0.0F;
		}
		else
		{
			this.tail.rotateAngleY = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
		}

		if(entitydirewolf.isSitting())
		{
			this.mane.setRotationPoint(0F, 12F, -7F);
			this.mane.rotateAngleX = ((float) Math.PI * 2F / 5F);
			this.mane.rotateAngleY = 0.0F;
			this.body.setRotationPoint(0F, 14.6F, -1.5F);
			this.body.rotateAngleX = ((float) Math.PI / 4F);
			this.tail.setRotationPoint(0.0F, 20F, 5.5F);
			this.leg1.setRotationPoint(-2F, 22.0F, 3.0F);
			this.leg1.rotateAngleX = ((float) Math.PI * 3F / 2F);
			this.leg2.setRotationPoint(2F, 22.0F, 3.0F);
			this.leg2.rotateAngleX = ((float) Math.PI * 3F / 2F);
			this.leg3.rotateAngleX = 5.811947F;
			this.leg3.setRotationPoint(-2F, 16.0F, -4.0F);
			this.leg4.rotateAngleX = 5.811947F;
			this.leg4.setRotationPoint(2F, 16.0F, -4.0F);
		}
		else
		{
			this.body.setRotationPoint(0F, 12F, -0.5F);
			this.body.rotateAngleX = 1.422082F;
			this.mane.setRotationPoint(0F, 11F, -7F);
			this.mane.rotateAngleX = (1.570796F);
			this.tail.setRotationPoint(0F, 11F, 8.7F);
			this.leg1.setRotationPoint(-2F, 16F, 7F);
			this.leg2.setRotationPoint(2F, 16F, 7F);
			this.leg3.setRotationPoint(-2.5F, 16F, -4.5F);
			this.leg4.setRotationPoint(2.5F, 16F, -4.5F);
			this.leg1.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
			this.leg2.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
			this.leg3.rotateAngleX = MathHelper.cos(f1 * 0.6662F + (float) Math.PI) * 1.4F * f2;
			this.leg4.rotateAngleX = MathHelper.cos(f1 * 0.6662F) * 1.4F * f2;
		}

		this.head.rotateAngleZ = entitydirewolf.getInterestedAngle(f3) + entitydirewolf.getShakeAngle(f3, 0.0F);
		this.mane.rotateAngleZ = entitydirewolf.getShakeAngle(f3, -0.08F);
		this.body.rotateAngleZ = entitydirewolf.getShakeAngle(f3, -0.16F);
		this.tail.rotateAngleZ = entitydirewolf.getShakeAngle(f3, -0.2F);
	}

	public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity)
	{
		super.setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
		this.head.rotateAngleX = f5 / (180F / (float) Math.PI);
		this.head.rotateAngleY = f4 / (180F / (float) Math.PI);
		this.tail.rotateAngleX = f3 * 0.6F + 0.3F;
	}

}

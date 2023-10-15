package com.wildmobsmod.entity.passive.goose;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelGoose extends ModelBase
{
	// fields
	ModelRenderer head;
	ModelRenderer beak;
	ModelRenderer neck;
	ModelRenderer body;
	ModelRenderer wing1;
	ModelRenderer wing2;
	ModelRenderer tail;
	ModelRenderer flyingwing1;
	ModelRenderer flyingwing2;
	ModelRenderer leg1;
	ModelRenderer leg2;

	ModelRenderer headflying;
	ModelRenderer beakflying;
	ModelRenderer neckflying;
	ModelRenderer bodyflying;
	ModelRenderer tailflying;

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

	public ModelGoose()
	{
		textureWidth = 64;
		textureHeight = 32;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-1.5F, -3F, -1.5F, 3, 3, 3);
		head.setRotationPoint(0F, 14F, -5F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		beak = new ModelRenderer(this, 12, 0);
		beak.addBox(-1F, -1F, -2.5F, 2, 2, 3);
		beak.setRotationPoint(0F, 13F, -6.5F);
		beak.setTextureSize(64, 32);
		beak.mirror = true;
		setRotation(beak, 0F, 0F, 0F);
		convertToChild(head, beak);
		neck = new ModelRenderer(this, 0, 6);
		neck.addBox(-1F, -8F, -1F, 2, 9, 2);
		neck.setRotationPoint(0F, 20F, -4.5F);
		neck.setTextureSize(64, 32);
		neck.mirror = true;
		setRotation(neck, -0.0349066F, 0F, 0F);
		convertToChild(neck, head);
		body = new ModelRenderer(this, 0, 16);
		body.addBox(-2.5F, -3F, 0F, 5, 6, 10);
		body.setRotationPoint(0F, 21F, -5F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		bodyflying = new ModelRenderer(this, 0, 16);
		bodyflying.addBox(-2.5F, -3F, 0F, 5, 6, 10);
		bodyflying.setRotationPoint(0F, 21F, -5F);
		bodyflying.setTextureSize(64, 32);
		bodyflying.mirror = true;
		setRotation(bodyflying, 0F, 0F, 0F);
		wing1 = new ModelRenderer(this, 30, 16);
		wing1.addBox(-1F, 0F, -5F, 1, 5, 10);
		wing1.setRotationPoint(-2.5F, 18F, 1F);
		wing1.setTextureSize(64, 32);
		wing1.mirror = true;
		setRotation(wing1, 0F, 0F, 0F);
		convertToChild(body, wing1);
		wing2 = new ModelRenderer(this, 30, 16);
		wing2.addBox(0F, 0F, -5F, 1, 5, 10);
		wing2.setRotationPoint(2.5F, 18F, 1F);
		wing2.setTextureSize(64, 32);
		wing2.mirror = true;
		setRotation(wing2, 0F, 0F, 0F);
		convertToChild(body, wing2);
		tail = new ModelRenderer(this, 46, 16);
		tail.addBox(-2F, -1F, 0F, 4, 2, 5);
		tail.setRotationPoint(0F, 19F, 3F);
		tail.setTextureSize(64, 32);
		tail.mirror = true;
		setRotation(tail, 0.122173F, 0F, 0F);
		convertToChild(body, tail);
		flyingwing1 = new ModelRenderer(this, 22, 0);
		flyingwing1.addBox(-0.5F, -14F, -3.5F, 1, 14, 7);
		flyingwing1.setRotationPoint(-2.5F, 18.5F, -1F);
		flyingwing1.setTextureSize(64, 32);
		flyingwing1.mirror = true;
		setRotation(flyingwing1, 0F, 0F, -1.570796F);
		convertToChild(bodyflying, flyingwing1);
		flyingwing2 = new ModelRenderer(this, 22, 0);
		flyingwing2.addBox(-0.5F, -14F, -3.5F, 1, 14, 7);
		flyingwing2.setRotationPoint(2.5F, 18.5F, -1F);
		flyingwing2.setTextureSize(64, 32);
		flyingwing2.mirror = true;
		setRotation(flyingwing2, 0F, 0F, 1.570796F);
		convertToChild(bodyflying, flyingwing2);
		leg1 = new ModelRenderer(this, 52, 23);
		leg1.addBox(-1.5F, 0F, -1.5F, 3, 3, 3);
		leg1.setRotationPoint(-1.5F, 20.8F, -1F);
		leg1.setTextureSize(64, 32);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 52, 23);
		leg2.addBox(-1.5F, 0F, -1.5F, 3, 3, 3);
		leg2.setRotationPoint(1.5F, 20.8F, -1F);
		leg2.setTextureSize(64, 32);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);

		headflying = new ModelRenderer(this, 0, 0);
		headflying.addBox(-1.5F, -3F, -1.5F, 3, 3, 3);
		headflying.setRotationPoint(0F, 21F, -13F);
		headflying.setTextureSize(64, 32);
		headflying.mirror = true;
		setRotation(headflying, 0F, 0F, 0F);
		convertToChild(bodyflying, headflying);
		beakflying = new ModelRenderer(this, 12, 0);
		beakflying.addBox(-1F, -1F, -2.5F, 2, 2, 3);
		beakflying.setRotationPoint(0F, 20F, -14.5F);
		beakflying.setTextureSize(64, 32);
		beakflying.mirror = true;
		setRotation(beakflying, 0F, 0F, 0F);
		convertToChild(bodyflying, beakflying);
		neckflying = new ModelRenderer(this, 0, 6);
		neckflying.addBox(-1F, -8F, -1F, 2, 9, 2);
		neckflying.setRotationPoint(0F, 20F, -3.5F);
		neckflying.setTextureSize(64, 32);
		neckflying.mirror = true;
		setRotation(neckflying, 1.570796F, 0F, 0F);
		convertToChild(bodyflying, neckflying);
		tailflying = new ModelRenderer(this, 46, 16);
		tailflying.addBox(-2F, -1F, 0F, 4, 2, 5);
		tailflying.setRotationPoint(0F, 19F, 3F);
		tailflying.setTextureSize(64, 32);
		tailflying.mirror = true;
		setRotation(tailflying, 0.122173F, 0F, 0F);
		convertToChild(bodyflying, tailflying);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		EntityGoose entitygoose = (EntityGoose) entity;

		if(entitygoose.animation == 0)
		{
			neck.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
		}
		else if(entitygoose.animation == 1)
		{
			bodyflying.render(f5);
			this.flyingwing1.rotateAngleZ = MathHelper.cos(f2 * 0.5F) * (float) Math.PI * 0.18F - 1.3F;
			this.flyingwing2.rotateAngleZ = -this.flyingwing1.rotateAngleZ;
		}
		else if(entitygoose.animation == 2)
		{
			neck.render(f5);
			body.render(f5);
		}
		else if(entitygoose.animation == 3)
		{
			bodyflying.render(f5);
			this.flyingwing1.rotateAngleZ = MathHelper.cos(f2 * 1.2F) * (float) Math.PI * 0.22F - 1.3F;
			this.flyingwing2.rotateAngleZ = -this.flyingwing1.rotateAngleZ;
		}
		else if(entitygoose.animation == 4)
		{
			bodyflying.render(f5);
			this.flyingwing1.rotateAngleZ = MathHelper.cos(f2 * 1.2F) * (float) Math.PI * 0.22F - 1.3F;
			this.flyingwing2.rotateAngleZ = -this.flyingwing1.rotateAngleZ;
		}
		else if(entitygoose.animation == 5)
		{
			bodyflying.render(f5);
			this.flyingwing1.rotateAngleZ = MathHelper.cos(f2 * 0.5F) * (float) Math.PI * 0.18F - 1.3F;
			this.flyingwing2.rotateAngleZ = -this.flyingwing1.rotateAngleZ;
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
		EntityGoose entitygoose = (EntityGoose) living;

		if(entitygoose.animation != 0)
		{
			this.neck.setRotationPoint(0F, 22F, -4.5F);
			this.body.setRotationPoint(0F, 23F, -5F);
		}
		else
		{
			this.neck.setRotationPoint(0F, 17F, -4.5F);
			this.body.setRotationPoint(0F, 18F, -5F);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		EntityGoose entitygoose = (EntityGoose) entity;
		if(entitygoose.animation == 0)
		{
			this.body.rotateAngleX = -0.1F;
		}
		else
		{
			this.body.rotateAngleX = 0.0F;
		}
		this.neck.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.neck.rotateAngleY = f3 / (180F / (float) Math.PI);

		//
		// Feeding animation
		//
		if(entitygoose.feedingAnimation == 1)
		{
			this.neck.rotateAngleX += 0.5F;
		}
		else if(entitygoose.feedingAnimation == 2)
		{
			this.neck.rotateAngleX += 0.9F;
		}
		else if(entitygoose.feedingAnimation == 3)
		{
			this.neck.rotateAngleX += 1.3F;
		}
		else if(entitygoose.feedingAnimation == 4)
		{
			this.neck.rotateAngleX += 1.7F;
		}
		else if(entitygoose.feedingAnimation >= 5 && entitygoose.feedingAnimation <= 25)
		{
			this.neck.rotateAngleX += 2.1F;
		}
		else if(entitygoose.feedingAnimation == 26)
		{
			this.neck.rotateAngleX += 1.7F;
		}
		else if(entitygoose.feedingAnimation == 27)
		{
			this.neck.rotateAngleX += 1.3F;
		}
		else if(entitygoose.feedingAnimation == 28)
		{
			this.neck.rotateAngleX += 0.9F;
		}
		else if(entitygoose.feedingAnimation == 29)
		{
			this.neck.rotateAngleX += 0.5F;
		}
		else
		{
			this.neck.rotateAngleX += 0.1F;
		}

		this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.8F * f1;
		this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.8F * f1;
		if(entitygoose.animation == 1)
		{
			this.bodyflying.rotateAngleX = 0.0F;
		}
		else if(entitygoose.animation == 3)
		{
			this.bodyflying.rotateAngleX = -0.15F;
		}
		else if(entitygoose.animation == 4)
		{
			this.bodyflying.rotateAngleX = 0.0F;
		}
		else if(entitygoose.animation == 5)
		{
			this.bodyflying.rotateAngleX = 0.15F;
		}
	}
}

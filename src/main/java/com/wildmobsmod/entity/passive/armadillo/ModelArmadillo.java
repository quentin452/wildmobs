package com.wildmobsmod.entity.passive.armadillo;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelArmadillo extends ModelBase
{
	// fields
	ModelRenderer head;
	ModelRenderer nose;
	ModelRenderer body;
	ModelRenderer tail;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer ear1;
	ModelRenderer ear2;
	ModelRenderer ball;

	// This is really useful for converting the source from a Techne model
	// export
	// which will have absolute rotation points that need to be converted before
	// creating the addChild() relationship
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

	public ModelArmadillo()
	{
		textureWidth = 64;
		textureHeight = 32;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-1.5F, -1.5F, -4F, 3, 3, 4);
		head.setRotationPoint(0F, 19F, -4F);
		head.setTextureSize(64, 32);
		setRotation(head, 0F, 0F, 0F);
		nose = new ModelRenderer(this, 0, 7);
		nose.addBox(-1F, -1F, -2F, 2, 2, 2);
		nose.setRotationPoint(0F, 19.5F, -8F);
		nose.setTextureSize(64, 32);
		setRotation(nose, 0F, 0F, 0F);
		convertToChild(head, nose);
		body = new ModelRenderer(this, 18, 0);
		body.addBox(-3F, 0F, -3F, 6, 10, 6);
		body.setRotationPoint(0F, 19F, -5F);
		body.setTextureSize(64, 32);
		setRotation(body, 1.570796F, 0F, 0F);
		tail = new ModelRenderer(this, 9, 12);
		tail.addBox(-1F, 0F, -1F, 2, 8, 2);
		tail.setRotationPoint(0F, 20F, 4.5F);
		tail.setTextureSize(64, 32);
		setRotation(tail, 1.186824F, 0F, 0F);
		leg1 = new ModelRenderer(this, 0, 12);
		leg1.addBox(-1F, 0F, -1F, 2, 3, 2);
		leg1.setRotationPoint(-1.5F, 21F, 3F);
		leg1.setTextureSize(64, 32);
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 12);
		leg2.addBox(-1F, 0F, -1F, 2, 3, 2);
		leg2.setRotationPoint(1.5F, 21F, 3F);
		leg2.setTextureSize(64, 32);
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 0, 12);
		leg3.addBox(-1F, 0F, -1F, 2, 3, 2);
		leg3.setRotationPoint(-1.5F, 21F, -3F);
		leg3.setTextureSize(64, 32);
		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 0, 12);
		leg4.addBox(-1F, 0F, -1F, 2, 3, 2);
		leg4.setRotationPoint(1.5F, 21F, -3F);
		leg4.setTextureSize(64, 32);
		setRotation(leg4, 0F, 0F, 0F);
		ear1 = new ModelRenderer(this, 9, 8);
		ear1.addBox(-0.5F, -2F, -0.5F, 1, 2, 1);
		ear1.setRotationPoint(-1F, 18F, -5.5F);
		ear1.setTextureSize(64, 32);
		setRotation(ear1, -0.2617994F, 0F, -0.3490659F);
		convertToChild(head, ear1);
		ear2 = new ModelRenderer(this, 9, 8);
		ear2.addBox(-0.5F, -2F, -0.5F, 1, 2, 1);
		ear2.setRotationPoint(1F, 18F, -5.5F);
		ear2.setTextureSize(64, 32);
		setRotation(ear2, -0.2617994F, 0F, 0.3490659F);
		convertToChild(head, ear2);
		ball = new ModelRenderer(this, 18, 16);
		ball.addBox(-3.5F, -8F, -4F, 7, 8, 8);
		ball.setRotationPoint(0F, 24F, 0F);
		ball.setTextureSize(64, 32);
		setRotation(ball, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if(this.isChild)
		{
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
			body.render(f5);
			tail.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			ball.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.6F / f6, 1.6F / f6, 1.6F / f6);
			GL11.glTranslatef(0.0F, 8.0F * f5, 1.7F * f5);
			head.render(f5);
			GL11.glPopMatrix();
		}
		else
		{
			body.render(f5);
			tail.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			ball.render(f5);
			head.render(f5);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.head.rotateAngleY = f3 / (360F / (float) Math.PI);
		this.head.rotateAngleX += 0.3F;
		this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1;
		this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1;
		this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1;
		this.leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1;
		this.tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
	}

}

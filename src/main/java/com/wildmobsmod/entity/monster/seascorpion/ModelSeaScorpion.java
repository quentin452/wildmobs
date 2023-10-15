package com.wildmobsmod.entity.monster.seascorpion;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSeaScorpion extends ModelBase
{
	// fields
	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer tail1;
	ModelRenderer tail2;
	ModelRenderer tail3;
	ModelRenderer tailfin;
	ModelRenderer paddle1;
	ModelRenderer paddle2;
	ModelRenderer legs;

	protected void convertToChild(ModelRenderer parParent, ModelRenderer parChild)
	{
		// move child rotation point to be relative to parent
		parChild.rotationPointX -= parParent.rotationPointX;
		parChild.rotationPointY -= parParent.rotationPointY;
		parChild.rotationPointZ -= parParent.rotationPointZ;
		// make rotations relative to parent
		parChild.rotateAngleX -= parParent.rotateAngleX;
		parChild.rotateAngleY -= parParent.rotateAngleY;
		parChild.rotateAngleZ -= parParent.rotateAngleZ;
		// create relationship
		parParent.addChild(parChild);
	}

	public ModelSeaScorpion()
	{
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -1.5F, -5F, 8, 3, 5);
		head.setRotationPoint(0F, 22.5F, -5F);
		head.setTextureSize(64, 64);
		setRotation(head, 0F, 0F, 0F);
		body = new ModelRenderer(this, 0, 8);
		body.addBox(-5F, -1F, 0F, 10, 3, 10);
		body.setRotationPoint(0F, 22F, -5F);
		body.setTextureSize(64, 64);
		setRotation(body, 0F, 0F, 0F);
		tail1 = new ModelRenderer(this, 0, 21);
		tail1.addBox(-3F, -1F, 0F, 6, 2, 5);
		tail1.setRotationPoint(0F, 22.5F, 5F);
		tail1.setTextureSize(64, 64);
		setRotation(tail1, 0F, 0F, 0F);
		tail2 = new ModelRenderer(this, 0, 28);
		tail2.addBox(-3F, -1F, 0F, 6, 2, 5);
		tail2.setRotationPoint(0F, 22.5F, 10F);
		tail2.setTextureSize(64, 64);
		setRotation(tail2, 0F, 0F, 0F);
		convertToChild(tail1, tail2);
		tail3 = new ModelRenderer(this, 0, 35);
		tail3.addBox(-2F, -1F, 0F, 4, 2, 5);
		tail3.setRotationPoint(0F, 0F, 5F);
		tail3.setTextureSize(64, 64);
		setRotation(tail3, 0F, 0F, 0F);
		tail2.addChild(tail3);
		tailfin = new ModelRenderer(this, 0, 42);
		tailfin.addBox(-4F, 0F, 0F, 8, 1, 4);
		tailfin.setRotationPoint(0F, -0.5F, 4F);
		tailfin.setTextureSize(64, 64);
		setRotation(tailfin, 0F, 0F, 0F);
		tail3.addChild(tailfin);
		paddle1 = new ModelRenderer(this, 0, 47);
		paddle1.addBox(-1.5F, 0F, 0F, 3, 10, 1);
		paddle1.setRotationPoint(4F, 23.5F, -3F);
		paddle1.setTextureSize(64, 64);
		setRotation(paddle1, 1.570796F, 0.8726646F, 0F);
		paddle2 = new ModelRenderer(this, 0, 47);
		paddle2.addBox(-1.5F, 0F, 0F, 3, 10, 1);
		paddle2.setRotationPoint(-4F, 23.5F, -3F);
		paddle2.setTextureSize(64, 64);
		setRotation(paddle2, 1.570796F, -0.8726646F, 0F);
		legs = new ModelRenderer(this, 0, 47);
		legs.addBox(-9F, 0F, -6F, 18, 0, 8);
		legs.setRotationPoint(0F, 23.5F, -4F);
		legs.setTextureSize(64, 64);
		setRotation(legs, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		EntitySeaScorpion entityseascorpion = (EntitySeaScorpion) entity;
		head.render(f5);
		body.render(f5);
		tail1.render(f5);
		paddle1.render(f5);
		paddle2.render(f5);
		legs.render(f5);
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
		EntitySeaScorpion entityseascorpion = (EntitySeaScorpion) entity;
		this.tail1.rotateAngleX = MathHelper.cos(f * 0.650F) * 0.6F * f1;
		this.tail2.rotateAngleX = MathHelper.cos(f * 0.650F) * 0.6F * f1;
		this.tail3.rotateAngleX = MathHelper.cos(f * 0.650F) * 0.6F * f1;
		this.paddle1.rotateAngleY = (MathHelper.cos(f * 0.650F) * 0.8F * f1) + 0.7F;
		this.paddle2.rotateAngleY = (MathHelper.cos(f * 0.650F + (float) Math.PI) * 0.8F * f1) - 0.7F;
	}
}

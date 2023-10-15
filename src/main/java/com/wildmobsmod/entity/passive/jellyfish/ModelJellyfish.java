package com.wildmobsmod.entity.passive.jellyfish;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelJellyfish extends ModelBase
{
	// fields
	ModelRenderer body;
	ModelRenderer tentacles1;
	ModelRenderer tentacles2;
	ModelRenderer oralarm1;
	ModelRenderer oralarm2;

	public ModelJellyfish()
	{
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this, 0, 0);
		body.addBox(-4F, 0F, -4F, 8, 6, 8);
		body.setRotationPoint(0F, 18F, 0F);
		body.setTextureSize(64, 64);
		setRotation(body, 0F, 0F, 0F);
		tentacles1 = new ModelRenderer(this, 0, 36);
		tentacles1.addBox(-4F, 0F, -4F, 8, 16, 8);
		tentacles1.setRotationPoint(0F, 24F, 0F);
		tentacles1.setTextureSize(64, 64);
		setRotation(tentacles1, 0F, 0F, 0F);
		tentacles2 = new ModelRenderer(this, 32, 36);
		tentacles2.addBox(-3.5F, 0F, -3.5F, 7, 16, 7);
		tentacles2.setRotationPoint(0F, 24F, 0F);
		tentacles2.setTextureSize(64, 64);
		setRotation(tentacles2, 0F, 0F, 0F);
		oralarm1 = new ModelRenderer(this, 0, 14);
		oralarm1.addBox(-3.5F, 0F, 0F, 7, 22, 0);
		oralarm1.setRotationPoint(0F, 18F, 0F);
		oralarm1.setTextureSize(64, 64);
		setRotation(oralarm1, 0F, 0.7853982F, 0F);
		oralarm2 = new ModelRenderer(this, 0, 14);
		oralarm2.addBox(-3.5F, 0F, 0F, 7, 22, 0);
		oralarm2.setRotationPoint(0F, 18F, 0F);
		oralarm2.setTextureSize(64, 64);
		setRotation(oralarm2, 0F, -0.7853982F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		tentacles1.render(f5);
		tentacles2.render(f5);
		oralarm1.render(f5);
		oralarm2.render(f5);
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
	}
}

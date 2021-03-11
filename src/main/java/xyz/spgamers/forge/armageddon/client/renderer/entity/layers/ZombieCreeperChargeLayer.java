package xyz.spgamers.forge.armageddon.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import xyz.spgamers.forge.armageddon.client.renderer.entity.model.ZombieCreeperModel;
import xyz.spgamers.forge.armageddon.entity.ZombieCreeperEntity;
import xyz.spgamers.forge.armageddon.util.Constants;

public class ZombieCreeperChargeLayer extends EnergyLayer<ZombieCreeperEntity, ZombieCreeperModel>
{
	private final ZombieCreeperModel model = new ZombieCreeperModel(2F);

	public ZombieCreeperChargeLayer(IEntityRenderer<ZombieCreeperEntity, ZombieCreeperModel> entityRenderer)
	{
		super(entityRenderer);
	}

	@Override
	protected float func_225634_a_(float p_225634_1_)
	{
		return p_225634_1_ * .01F;
	}

	@Override
	protected ResourceLocation func_225633_a_()
	{
		return Constants.Textures.LIGHTNING_TEXTURE;
	}

	@Override
	protected EntityModel<ZombieCreeperEntity> func_225635_b_()
	{
		return model;
	}
}
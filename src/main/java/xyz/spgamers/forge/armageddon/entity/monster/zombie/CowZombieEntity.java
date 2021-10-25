package xyz.spgamers.forge.armageddon.entity.monster.zombie;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import xyz.spgamers.forge.armageddon.Armageddon;
import xyz.spgamers.forge.armageddon.init.ModEntities;
import xyz.spgamers.forge.armageddon.init.ModItems;
import xyz.spgamers.forge.armageddon.util.ZombieHelper;

import java.util.Random;

public final class CowZombieEntity extends AbstractZombieEntity
{
	public CowZombieEntity(World world)
	{
		super(ModEntities.COW_ZOMBIE.get(), world, Armageddon.SERVER_CONFIG.animals::isCowZombieEnabled);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_COW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource)
	{
		return SoundEvents.ENTITY_COW_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_COW_DEATH;
	}

	@Override
	protected SoundEvent getStepSound()
	{
		return SoundEvents.ENTITY_COW_STEP;
	}

	@Override
	protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand)
	{
		ItemStack bucket = player.getHeldItem(hand);

		if(!bucket.isEmpty() && bucket.getItem() == Items.BUCKET)
		{
			player.playSound(SoundEvents.ENTITY_COW_MILK, 1F, 1F);
			ItemStack filled = DrinkHelper.fill(bucket, player, ModItems.SPOILED_MILK_BUCKET.get().getDefaultInstance());
			player.setHeldItem(hand, filled);
			return ActionResultType.func_233537_a_(world.isRemote());
		}

		return super.func_230254_b_(player, hand);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
	{
		return isChild() ? sizeIn.height * .95F : 1.3F;
	}

	public static AttributeModifierMap.MutableAttribute registerCowZombieAttributes()
	{
		return ZombieHelper.registerZombieAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 10D).createMutableAttribute(Attributes.MOVEMENT_SPEED, .2F);
	}

	public static boolean canCowZombieSpawn(EntityType<? extends MonsterEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random random)
	{
		if(!Armageddon.SERVER_CONFIG.animals.isCowZombieEnabled())
			return false;
		if(!ZombieHelper.canZombieSpawn(entityType, world, reason, pos, random))
			return false;
		// MonsterEntity does not extend AnimalEntity
		// return AnimalEntity.canAnimalSpawn(entityType, world, reason, pos, random);
		// code from AnimalEntity#canAnimalSpawn()
		return world.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK) && world.getLightSubtracted(pos, 0) > 8;
	}
}
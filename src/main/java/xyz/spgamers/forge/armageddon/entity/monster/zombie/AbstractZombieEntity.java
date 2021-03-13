package xyz.spgamers.forge.armageddon.entity.monster.zombie;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import xyz.spgamers.forge.armageddon.Armageddon;

import java.util.Random;
import java.util.function.BooleanSupplier;

// Add any common shared logic between the different zombie types here
// rather than duplicate code across them all
public class AbstractZombieEntity extends ZombieEntity
{
	private final BooleanSupplier entityEnabledSupplier;

	protected AbstractZombieEntity(EntityType<? extends AbstractZombieEntity> type, World world, BooleanSupplier entityEnabledSupplier)
	{
		super(type, world);

		this.entityEnabledSupplier = entityEnabledSupplier;
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		addCustomZombieGoals(this);
	}

	public boolean isEntityEnabled()
	{
		return entityEnabledSupplier.getAsBoolean();
	}

	@Override
	public void tick()
	{
		super.tick();

		if(!isEntityEnabled() && isAlive())
			onKillCommand();
	}

	@Override
	protected boolean canDropLoot()
	{
		return isEntityEnabled() && super.canDropLoot();
	}

	protected static AttributeModifierMap.MutableAttribute registerZombieAttributes()
	{
		return ZombieEntity.func_234342_eQ_();
	}

	protected static boolean canZombieSpawn(EntityType<? extends MonsterEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random random)
	{
		return MonsterEntity.canMonsterSpawnInLight(entityType, world, reason, pos, random);
	}

	public static void addCustomZombieGoals(ZombieEntity zombie)
	{
		if(Armageddon.SERVER_CONFIG.entities.isZombiePigEnabled())
			zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, PigEntity.class, true));
		if(Armageddon.SERVER_CONFIG.entities.isZombieCowEnabled())
			zombie.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(zombie, CowEntity.class, true));
	}
}

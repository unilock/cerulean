package fmt.cerulean.flow.recipe;

import java.util.List;
import java.util.function.Predicate;

import fmt.cerulean.flow.FlowState;
import fmt.cerulean.mixin.ItemEntityAccessor;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PigmentInventory extends SimpleInventory {
	public final FlowState flow, opposing;
	public final World world;
	public final BlockPos pos;
	public final Direction direction;
	public final List<ItemEntity> entities;
	public int recipeProgress = 0;

	public PigmentInventory(FlowState flow, FlowState opposing, World world, BlockPos pos, Direction direction) {
		this(flow, opposing, world, pos, direction, getItemEntities(world, pos));
	}

	public PigmentInventory(FlowState flow, FlowState opposing, World world, BlockPos pos, Direction direction, List<ItemEntity> entities) {
		super(entities.stream().map(e -> e.getStack()).toArray(i -> new ItemStack[i]));
		this.flow = flow;
		this.opposing = opposing;
		this.world = world;
		this.pos = pos;
		this.direction = direction;
		this.entities = entities;
	}

	private static List<ItemEntity> getItemEntities(World world, BlockPos pos) {
		Box box = Box.enclosing(pos, pos);
		return world.getEntitiesByClass(ItemEntity.class, box, e -> true);
	}

	public ItemStack find(Predicate<ItemStack> predicate) {
		for (int i = 0; i < size(); i++) {
			ItemStack stack = getStack(i);
			if (predicate.test(stack)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

	public void killItems(Predicate<ItemStack> predicate, int amount) {
		int slain = 0;
		for (int i = 0; i < size(); i++) {
			if (slain >= amount) {
				return;
			}
			ItemStack stack = getStack(i);
			if (predicate.test(stack)) {
				int coveted = Math.min(amount, stack.getCount());
				stack.decrement(coveted);
				slain += coveted;
			}
		}
	}

	public void keepAlive(ItemStack stack) {
		for (ItemEntity e : entities) {
			if (e.getStack() == stack) {
				((ItemEntityAccessor) e).setItemAge(0);
				return;
			}
		}
	}

	public void spawnResult(ItemStack stack) {
		ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, stack);
		world.spawnEntity(entity);
	}
}

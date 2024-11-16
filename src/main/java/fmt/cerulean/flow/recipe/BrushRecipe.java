package fmt.cerulean.flow.recipe;

import fmt.cerulean.flow.FlowState;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public interface BrushRecipe extends Recipe<PigmentInventory> {

	int getCraftTime();

	int getRequiredFlowInputs();

	boolean canCraft(PigmentInventory inventory);

	void craft(PigmentInventory inventory);

	default FlowState getProcessedFlow(FlowState flow, int process) {
		return FlowState.NONE;
	}

	@Override
	default boolean matches(PigmentInventory inventory, World world) {
		if (inventory.opposing.empty() != (getRequiredFlowInputs() == 1)) {
			return false;
		}
		return canCraft(inventory);
	}

	@Override
	default ItemStack craft(PigmentInventory input, RegistryWrapper.WrapperLookup lookup) {
		craft(input);
		return ItemStack.EMPTY;
	}

	@Override
	default boolean fits(int width, int height) {
		return true;
	}

	@Override
	default ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return ItemStack.EMPTY;
	}

	@Override
	default RecipeSerializer<?> getSerializer() {
		throw new UnsupportedOperationException();
	}

	@Override
	default RecipeType<?> getType() {
		throw new UnsupportedOperationException();
	}
}

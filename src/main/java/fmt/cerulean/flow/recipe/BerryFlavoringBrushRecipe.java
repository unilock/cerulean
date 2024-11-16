package fmt.cerulean.flow.recipe;

import fmt.cerulean.item.BerryItem;
import fmt.cerulean.registry.CeruleanItems;
import net.minecraft.component.DataComponentTypes;

public class BerryFlavoringBrushRecipe implements BrushRecipe {

	@Override
	public int getRequiredFlowInputs() {
		return 1;
	}

	@Override
	public int getCraftTime() {
		return 12;
	}

	@Override
	public boolean canCraft(PigmentInventory inventory) {
		if (inventory.containsAny(s -> s.isOf(CeruleanItems.BERRIES) && !s.get(DataComponentTypes.CUSTOM_DATA).getNbt().contains("Flow"))) {
			return true;
		}
		return false;
	}

	@Override
	public void craft(PigmentInventory inventory) {
		inventory.removeItem(CeruleanItems.BERRIES, 1);
		inventory.spawnResult(BerryItem.fromFlow(inventory.flow));
	}
}

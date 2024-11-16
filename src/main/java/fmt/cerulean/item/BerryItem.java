package fmt.cerulean.item;

import fmt.cerulean.flow.FlowState;
import fmt.cerulean.registry.CeruleanItems;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class BerryItem extends AliasedBlockItem {
	
	public BerryItem(Block block, Settings settings) {
		super(block, settings);
	}

	public static ItemStack fromFlow(FlowState state) {
		ItemStack stack = new ItemStack(CeruleanItems.BERRIES);
		// TODO: the easy way out
		NbtComponent.set(DataComponentTypes.CUSTOM_DATA, stack, nbt -> {
			nbt.put("Flow", state.toNbt());
		});
		return stack;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		NbtComponent nbt = stack.get(DataComponentTypes.CUSTOM_DATA);
		if (nbt != null && nbt.contains("Flow")) {
			FlowState flow = FlowState.fromNbt(nbt.getNbt().getCompound("Flow"));
			if (!flow.empty()) {
				if (user instanceof PlayerEntity player) {
					player.sendMessage(Text.translatable("message.cerulean.flow_flavor", flow.resource().getBrightness().text(), flow.resource().getColor().text()), true);
				}
			}
		}
		return super.finishUsing(stack, world, user);
	}
}

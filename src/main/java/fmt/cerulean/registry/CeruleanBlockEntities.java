package fmt.cerulean.registry;

import fmt.cerulean.Cerulean;
import fmt.cerulean.block.entity.MimicBlockEntity;
import fmt.cerulean.block.entity.PipeBlockEntity;
import fmt.cerulean.block.entity.WellBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CeruleanBlockEntities {
	public static final BlockEntityType<WellBlockEntity> WELL = register("well", WellBlockEntity::new, CeruleanBlocks.STAR_WELL);
	public static final BlockEntityType<PipeBlockEntity> PIPE = register("pipe", PipeBlockEntity::new,
			CeruleanBlocks.PIPE, CeruleanBlocks.EXPOSED_PIPE, CeruleanBlocks.WEATHERED_PIPE, CeruleanBlocks.OXIDIZED_PIPE,
			CeruleanBlocks.WAXED_PIPE, CeruleanBlocks.WAXED_EXPOSED_PIPE, CeruleanBlocks.WAXED_WEATHERED_PIPE, CeruleanBlocks.WAXED_OXIDIZED_PIPE,
			CeruleanBlocks.FUCHSIA_PIPE);
	public static final BlockEntityType<MimicBlockEntity> MIMIC = register("mimic", MimicBlockEntity::new, CeruleanBlocks.MIMIC);

	public static void init() {
	}
	
	private static <T extends BlockEntity> BlockEntityType<T> register(String path, BlockEntityType.BlockEntityFactory<T> function, Block... blocks) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Cerulean.id(path), BlockEntityType.Builder.create(function, blocks).build());
	}
}

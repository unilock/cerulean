package fmt.cerulean;

import fmt.cerulean.flow.recipe.BrushRecipes;
import fmt.cerulean.net.CeruleanServerNetworking;
import fmt.cerulean.registry.CeruleanBlockEntities;
import fmt.cerulean.registry.CeruleanBlocks;
import fmt.cerulean.registry.CeruleanItemGroups;
import fmt.cerulean.registry.CeruleanItems;
import fmt.cerulean.registry.CeruleanParticleTypes;
import fmt.cerulean.world.CeruleanDimensions;
import fmt.cerulean.world.gen.DreamscapeBiomeSource;
import fmt.cerulean.world.gen.DreamscapeChunkGenerator;
import fmt.cerulean.world.gen.SkiesBiomeSource;
import fmt.cerulean.world.gen.SkiesChunkGenerator;
import fmt.cerulean.world.gen.feature.BiomeDecorator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;

import java.util.List;

public class Cerulean implements ModInitializer {
	public static final String ID = "cerulean";

	@Override
	public void onInitialize() {
		CeruleanBlocks.init();
		CeruleanBlockEntities.init();
		CeruleanParticleTypes.init();

		CeruleanItems.init();

		CeruleanItemGroups.init();

		BiomeDecorator.init();

		CeruleanServerNetworking.init();

		BrushRecipes.init();
		

		Registry.register(Registries.BIOME_SOURCE, CeruleanDimensions.DREAMSCAPE, DreamscapeBiomeSource.CODEC);
		Registry.register(Registries.BIOME_SOURCE, CeruleanDimensions.SKIES, SkiesBiomeSource.CODEC);

		Registry.register(Registries.CHUNK_GENERATOR, CeruleanDimensions.DREAMSCAPE, DreamscapeChunkGenerator.CODEC);
		Registry.register(Registries.CHUNK_GENERATOR, CeruleanDimensions.SKIES, SkiesChunkGenerator.CODEC);

		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (world.getDimensionEntry().matchesId(CeruleanDimensions.DREAMSCAPE)) {
				return TypedActionResult.fail(ItemStack.EMPTY);
			}

			return TypedActionResult.pass(ItemStack.EMPTY);
		});

		UseBlockCallback.EVENT.register((player, world, hand, res) -> {
			if (player.getAbilities().allowModifyWorld && !player.getStackInHand(hand).isEmpty() && world.getDimensionEntry().matchesId(CeruleanDimensions.DREAMSCAPE)) {
				return ActionResult.FAIL;
			}

			return ActionResult.PASS;
		});

		EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> {
			List<PaintingEntity> paintings = player.getWorld().getEntitiesByClass(PaintingEntity.class, Box.enclosing(sleepingPos, sleepingPos).expand(10), p -> true);
			boolean found = false;

			for (PaintingEntity painting : paintings) {
				if (painting.getVariant().matchesKey(RegistryKey.of(RegistryKeys.PAINTING_VARIANT, Cerulean.id("dreams")))) {
					if (found) {
						// Found two portal paintings?
						found = false;
						break;
					}
					found = true;
				}
			}

			if (found) {
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}

	public static Identifier id(String path) {
		return Identifier.of(ID, path);
	}
}

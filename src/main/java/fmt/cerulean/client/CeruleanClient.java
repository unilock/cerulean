package fmt.cerulean.client;

import fmt.cerulean.block.entity.WellBlockEntity;
import fmt.cerulean.client.effects.DreamscapeEffects;
import fmt.cerulean.client.effects.SkiesEffects;
import fmt.cerulean.client.render.DreamscapeRenderer;
import fmt.cerulean.client.render.SkiesRenderer;
import fmt.cerulean.item.StarItem;
import fmt.cerulean.net.CeruleanClientNetworking;
import fmt.cerulean.registry.CeruleanItems;
import fmt.cerulean.registry.client.CeruleanBlockEntityRenderers;
import fmt.cerulean.registry.client.CeruleanParticles;
import fmt.cerulean.registry.client.CeruleanRenderLayers;
import fmt.cerulean.world.CeruleanDimensions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.ColorHelper;

public class CeruleanClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CeruleanRenderLayers.init();
		CeruleanParticles.init();
		CeruleanBlockEntityRenderers.init();

		DimensionRenderingRegistry.registerDimensionEffects(CeruleanDimensions.DREAMSCAPE, new DreamscapeEffects());
		DimensionRenderingRegistry.registerDimensionEffects(CeruleanDimensions.SKIES, new SkiesEffects());

		DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKeys.WORLD, CeruleanDimensions.DREAMSCAPE), new DreamscapeRenderer());
		DimensionRenderingRegistry.registerSkyRenderer(RegistryKey.of(RegistryKeys.WORLD, CeruleanDimensions.SKIES), new SkiesRenderer());

		CeruleanClientNetworking.init();

		ColorProviderRegistry.ITEM.register((stack, index) -> {
			return ColorHelper.Argb.fullAlpha(WellBlockEntity.getRgb(((StarItem) stack.getItem()).resource));
		}, CeruleanItems.STARS.values().toArray(ItemConvertible[]::new));
	}
}

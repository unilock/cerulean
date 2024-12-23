package fmt.cerulean.mixin.client;

import fmt.cerulean.world.CeruleanDimensions;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
	@Shadow private @Nullable ClientWorld world;

	@Redirect(method = "processWorldEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V"))
	private void cerulean$noPortalSounds(SoundManager instance, SoundInstance sound) {
		RegistryEntry<DimensionType> entry = this.world.getDimensionEntry();
		if (entry.matchesId(CeruleanDimensions.DREAMSCAPE)) {
			return;
		}

		if (entry.matchesId(CeruleanDimensions.SKIES)) {
			return;
		}

		instance.play(sound);
	}
}

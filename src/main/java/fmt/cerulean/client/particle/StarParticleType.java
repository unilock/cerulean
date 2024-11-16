package fmt.cerulean.client.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fmt.cerulean.registry.CeruleanParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public record StarParticleType(float red, float green, float blue, boolean collision, boolean shortLife) implements ParticleEffect {
	public static final MapCodec<StarParticleType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.FLOAT.fieldOf("red").forGetter(StarParticleType::red),
			Codec.FLOAT.fieldOf("green").forGetter(StarParticleType::green),
			Codec.FLOAT.fieldOf("blue").forGetter(StarParticleType::blue),
			Codec.BOOL.fieldOf("collision").forGetter(StarParticleType::collision),
			Codec.BOOL.fieldOf("short_life").forGetter(StarParticleType::shortLife)
	).apply(instance, StarParticleType::new));
	public static final PacketCodec<RegistryByteBuf, StarParticleType> PACKET_CODEC = PacketCodec.ofStatic(
			(buf, value) -> {
				buf.writeFloat(value.red);
				buf.writeFloat(value.blue);
				buf.writeFloat(value.green);
				buf.writeBoolean(value.collision);
				buf.writeBoolean(value.shortLife);
			},
			buf -> new StarParticleType(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readBoolean())
	);

	@Override
	public ParticleType<?> getType() {
		return CeruleanParticleTypes.STAR;
	}
}

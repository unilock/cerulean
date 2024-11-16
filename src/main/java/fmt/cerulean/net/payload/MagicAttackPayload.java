package fmt.cerulean.net.payload;

import fmt.cerulean.Cerulean;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record MagicAttackPayload(int eId) implements CustomPayload {
	public static final Id<MagicAttackPayload> ID = new Id<>(Cerulean.id("magic_attack"));

	public static final PacketCodec<RegistryByteBuf, MagicAttackPayload> CODEC = PacketCodecs.VAR_INT.xmap(MagicAttackPayload::new, MagicAttackPayload::eId).cast();

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

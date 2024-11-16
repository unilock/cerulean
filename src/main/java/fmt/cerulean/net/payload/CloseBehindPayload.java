package fmt.cerulean.net.payload;

import fmt.cerulean.Cerulean;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record CloseBehindPayload() implements CustomPayload {
	public static final Id<CloseBehindPayload> ID = new Id<>(Cerulean.id("close_behind"));

	public static final CloseBehindPayload INSTANCE = new CloseBehindPayload();
	public static final PacketCodec<RegistryByteBuf, CloseBehindPayload> CODEC = PacketCodec.unit(INSTANCE);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

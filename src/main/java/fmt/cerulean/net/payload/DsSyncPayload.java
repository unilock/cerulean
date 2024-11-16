package fmt.cerulean.net.payload;

import fmt.cerulean.Cerulean;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record DsSyncPayload(NbtCompound nbt) implements CustomPayload {
	public static final Id<DsSyncPayload> ID = new Id<>(Cerulean.id("ds_sync"));

	public static final PacketCodec<RegistryByteBuf, DsSyncPayload> CODEC = PacketCodecs.NBT_COMPOUND.xmap(DsSyncPayload::new, DsSyncPayload::nbt).cast();

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

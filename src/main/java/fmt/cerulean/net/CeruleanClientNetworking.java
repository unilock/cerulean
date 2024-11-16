package fmt.cerulean.net;

import fmt.cerulean.net.payload.DsSyncPayload;
import fmt.cerulean.util.Counterful;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CeruleanClientNetworking {
	public static void init() {
		ClientPlayNetworking.registerGlobalReceiver(DsSyncPayload.ID, (payload, context) -> {
			if (context.player() != null) {
				Counterful.get(context.player()).read(payload.nbt());
			}
		});
	}
}

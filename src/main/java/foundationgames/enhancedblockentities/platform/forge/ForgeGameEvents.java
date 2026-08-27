package foundationgames.enhancedblockentities.platform.forge;

//? if forge {

/*import foundationgames.enhancedblockentities.Main;
import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeGameEvents {

	private ForgeGameEvents() {
	}

	//? if <= 1.20 {
	/^@SubscribeEvent
	public static void onLevelTickPost(TickEvent.LevelTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;

		var level = event.level;
	^///?} else {
	@SubscribeEvent
	public static void onLevelTickPost(TickEvent.LevelTickEvent.Post event) {
		var level = event.level;
	//?}

		if (level instanceof ClientLevel clientLevel) {
			WorldUtil.EVENT_LISTENER.onEndTick(clientLevel);
		}
	}
}
*///?}

package foundationgames.enhancedblockentities.platform.forge;

//? if forge {

/*import foundationgames.enhancedblockentities.Main;
import foundationgames.enhancedblockentities.client.model.DynamicModelProvidingPlugin;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import net.minecraftforge.api.distmarker.Dist;
//? if <= 1.18 {
/^import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
^///?} else {
import net.minecraftforge.client.event.ModelEvent;
//?}
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ForgeLegacyModelEvents {

	private ForgeLegacyModelEvents() {
	}

	//? if <= 1.18 {
	/^@SubscribeEvent
	public static void registerGeometryLoaders(ModelRegistryEvent event) {
		DynamicModelProvidingPlugin.registerGeometryLoader(event);
	}
	^///?} else {
	@SubscribeEvent
	public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
		DynamicModelProvidingPlugin.registerGeometryLoader(event);
	}
	//?}

	//? if <= 1.18 {
	/^@SubscribeEvent
	public static void modifyBakingResult(ModelBakeEvent event) {
		ModelIdentifiers.captureExtraModels(event);
	}
	^///?} else if <= 1.19.2 {
	/^@SubscribeEvent
	public static void modifyBakingResult(ModelEvent.BakingCompleted event) {
		ModelIdentifiers.captureExtraModels(event);
	}
	^///?} else {
	@SubscribeEvent
	public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
		ModelIdentifiers.captureExtraModels(event);
	}
	//?}
}
*///?}

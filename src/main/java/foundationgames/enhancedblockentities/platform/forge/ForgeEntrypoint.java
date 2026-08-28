package foundationgames.enhancedblockentities.platform.forge;

//? if forge {

/*import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.Main;
import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
//? if <= 1.16 {
/^import net.minecraftforge.fml.ExtensionPoint;
^///?} else if <= 1.17 {
/^import net.minecraftforge.fmlclient.ConfigGuiHandler;
^///?} else if <= 1.18 {
/^import net.minecraftforge.client.ConfigGuiHandler;
^///?} else {
import net.minecraftforge.client.ConfigScreenHandler;
//?}
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Main.MOD_ID)
public class ForgeEntrypoint {

	public ForgeEntrypoint() {
		Main.onInitialize();

		if (FMLEnvironment.dist.isClient()) {
			//? if <= 1.16 {
			/^ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
					() -> (mc, parent) -> new EBEConfigScreen(parent));
			^///?} else if <= 1.18 {
			/^ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
					() -> new ConfigGuiHandler.ConfigGuiFactory((mc, parent) -> new EBEConfigScreen(parent)));
			^///?} else {
			ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
					() -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> new EBEConfigScreen(parent)));
			//?}

			EnhancedBlockEntities.initClient();
		}
	}
}
*///?}

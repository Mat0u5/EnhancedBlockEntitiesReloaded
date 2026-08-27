package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.render.SignRenderManager;
import foundationgames.enhancedblockentities.client.resource.template.TemplateLoader;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.WorldUtil;
//? if fabric {
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//?}
//? if fabric {
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
//?}
import foundationgames.enhancedblockentities.platform.Platform;
import net.minecraft.client.Minecraft;
import foundationgames.enhancedblockentities.util.DateUtil;
import net.minecraft.client.renderer.item.ItemProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

//? if fabric {
public final class EnhancedBlockEntities implements ClientModInitializer {
//?} else {
/*public final class EnhancedBlockEntities {
*///?}
    public static final String ID = "enhancedblockentities";
    public static final String NAMESPACE = "ebe";
    public static final Logger LOG = LogManager.getLogger("Enhanced Block Entities");
    public static final EBEConfig CONFIG = new EBEConfig();

    public static final TemplateLoader TEMPLATE_LOADER = new TemplateLoader();

    public static final String API_V1 = "ebe_v1";

    //? if fabric {
    @Override
    public void onInitializeClient() {
        initClient();
        registerLoaderEvents();
    }
    //?}

    @SuppressWarnings("unchecked")
    public static void initClient() {
        Platform.forEachApiEntrypoint(API_V1, Consumer.class,
                (modId, init) -> init.accept((Runnable) EnhancedBlockEntities::load));

        ItemProperties.registerGeneric(EBEUtil.id("is_christmas"), (stack, level, entity, seed) -> DateUtil.isChristmas() ? 1 : 0);

        ModelIdentifiers.init();
        EBESetup.setupResourceProviders();

        load();

        //foundationgames.enhancedblockentities.util.AutoTest.start();
    }

    private static void registerLoaderEvents() {
        //? if fabric {
        WorldRenderEvents.END.register(ctx -> SignRenderManager.endFrame());
        //?}
        //? if fabric {
        ClientTickEvents.END_WORLD_TICK.register(WorldUtil.EVENT_LISTENER::onEndTick);
        //?}
    }

    public static void reload(ReloadType type) {
        load();
        if (type == ReloadType.WORLD) {
            Minecraft.getInstance().levelRenderer.allChanged();
        } else if (type == ReloadType.RESOURCES) {
            Minecraft.getInstance().reloadResourcePacks();
        }
    }

    public static void load() {
        CONFIG.load();

        EnhancedBlockEntityRegistry.clear();
        ResourceUtil.resetBasePack();
        ResourceUtil.resetTopLevelPack();

        if (CONFIG.renderEnhancedChests) {
            EBESetup.setupChests();
            EBESetup.setupRRPChests();
        }

        if (CONFIG.renderEnhancedSigns) {
            EBESetup.setupSigns();
            EBESetup.setupRRPSigns();
        }

        if (CONFIG.renderEnhancedBells) {
            EBESetup.setupBells();
            EBESetup.setupRRPBells();
        }

        if (CONFIG.renderEnhancedBeds) {
            EBESetup.setupBeds();
            EBESetup.setupRRPBeds();
        }

        if (CONFIG.renderEnhancedShulkerBoxes) {
            EBESetup.setupShulkerBoxes();
            EBESetup.setupRRPShulkerBoxes();
        }

        //? if >= 1.19.4 {
        if (CONFIG.renderEnhancedDecoratedPots) {
            EBESetup.setupDecoratedPots();
            EBESetup.setupRRPDecoratedPots();
        }
        //?}
    }
}

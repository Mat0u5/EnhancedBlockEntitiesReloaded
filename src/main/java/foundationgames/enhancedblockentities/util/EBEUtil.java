package foundationgames.enhancedblockentities.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
//? if <= 1.21.11 {
/*import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
*///?} else {
import net.fabricmc.fabric.api.client.renderer.v1.model.ModelHelper;
//?}
import net.fabricmc.loader.api.FabricLoader;
//? if <= 1.21.11 {
/*import net.minecraft.client.renderer.ItemBlockRenderTypes;
*///?} else {
import com.mojang.blaze3d.vertex.QuadInstance;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelRenderState;
//?}
//? if <= 26.1 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}
//? if >= 1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}
//? if <= 1.21.11 {
/*import net.minecraft.client.renderer.block.model.BakedQuad;
*///?} else {
import net.minecraft.client.resources.model.geometry.BakedQuad;
//?}
//? if <= 1.21.4 {
/*import net.minecraft.client.resources.model.BakedModel;
*///?} else {
//? if <= 1.21.11 {
/*import net.minecraft.client.renderer.block.model.BlockStateModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
*///?} else {
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
//?}
//?}
import net.minecraft.core.Direction;
//? if >= 26.2 {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
//?}
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;

import java.io.IOException;
import java.nio.file.Files;
//? if >= 26.2 {
import java.util.HashMap;
import java.util.Map;
//?}

public enum EBEUtil {;
    private static final RandomSource dummy = RandomSource.create();

    // Contains all dye colors, and null
    public static final DyeColor[] DEFAULTED_DYE_COLORS;
    // All directions except up and down
    public static final Direction[] HORIZONTAL_DIRECTIONS;

    static {
        var dColors = DyeColor.values();
        DEFAULTED_DYE_COLORS = new DyeColor[dColors.length + 1];
        System.arraycopy(dColors, 0, DEFAULTED_DYE_COLORS, 0, dColors.length);

        HORIZONTAL_DIRECTIONS = new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    }

    //? if >= 26.2 {
    private static final Map<ResourceKey<Item>, ResourceKey<DecoratedPotPattern>> POT_PATTERNS_BY_ITEM = new HashMap<>();

    static {
        DecoratedPotPatterns.itemToPatternMappings(POT_PATTERNS_BY_ITEM::put);
    }

    public static ResourceKey<DecoratedPotPattern> potPatternFromItem(Item item) {
        var itemKey = BuiltInRegistries.ITEM.getResourceKey(item).orElse(null);
        var pattern = itemKey != null ? POT_PATTERNS_BY_ITEM.get(itemKey) : null;

        return pattern != null ? pattern : DecoratedPotPatterns.BLANK;
    }
    //?}

    public static int angle(Direction dir) {
        int h = dir.get2DDataValue();
        return h >= 0 ? h * 90 : 0;
    }

    //? if >= 1.21.9 {
    //? if <= 1.21.11 {
    /*public static void renderBakedModel(SubmitNodeCollector output, BlockState state, PoseStack matrices, BlockStateModel model, int light, int overlay) {
        if (model == null) return;
        output.submitBlockModel(matrices, ItemBlockRenderTypes.getRenderType(state), model, 1, 1, 1, light, overlay, 0);
    }
    *///?} else {
    public static void renderBakedModel(SubmitNodeCollector output, BlockState state, PoseStack matrices, BlockStateModel model, int light, int overlay) {
        if (model == null) return;

        var parts = new ObjectArrayList<BlockStateModelPart>();
        model.collectParts(dummy, parts);
        if (parts.isEmpty()) return;

        //? if <= 26.1 {
        /*output.submitBlockModel(matrices, Sheets.cutoutBlockSheet(), parts,
                BlockModelRenderState.EMPTY_TINTS, light, overlay, 0);
        *///?} else {
        output.submitBlockModel(matrices, Sheets.cutoutBlockItemSheet(), parts,
                BlockModelRenderState.EMPTY_TINTS, light, overlay, 0);
        //?}
    }
    //?}
    //?}

    //? if <= 1.21.4 {
    /*public static void renderBakedModel(MultiBufferSource vertexConsumers, BlockState state, PoseStack matrices, BakedModel model, int light, int overlay) {
        if (model == null) return;
        VertexConsumer vertices = vertexConsumers.getBuffer(ItemBlockRenderTypes.getRenderType(state));
        for (int i = 0; i <= 6; i++) {
            for (BakedQuad q : model.getQuads(null, ModelHelper.faceFromIndex(i), dummy)) {
                vertices.putBulkData(matrices.last(), q, 1, 1, 1, 1, light, overlay);
            }
        }
    }
    *///?} else {
    //? if <= 1.21.11 {
    /*public static void renderBakedModel(MultiBufferSource vertexConsumers, BlockState state, PoseStack matrices, BlockStateModel model, int light, int overlay) {
        if (model == null) return;
        VertexConsumer vertices = vertexConsumers.getBuffer(ItemBlockRenderTypes.getRenderType(state));
        for (BlockStateModelPart part : model.collectParts(dummy)) {
            for (int i = 0; i <= 6; i++) {
                for (BakedQuad q : part.getQuads(ModelHelper.faceFromIndex(i))) {
                    vertices.putBulkData(matrices.last(), q, 1, 1, 1, 1, light, overlay);
                }
            }
        }
    }
    *///?} else {
    //? if <= 26.1 {
    /*public static void renderBakedModel(MultiBufferSource vertexConsumers, BlockState state, PoseStack matrices, BlockStateModel model, int light, int overlay) {
        if (model == null) return;

        var parts = new ObjectArrayList<BlockStateModelPart>();
        model.collectParts(dummy, parts);

        var quadInstance = new QuadInstance();
        quadInstance.setLightCoords(light);
        quadInstance.setOverlayCoords(overlay);

        VertexConsumer vertices = vertexConsumers.getBuffer(Sheets.cutoutBlockSheet());
        for (BlockStateModelPart part : parts) {
            for (int i = 0; i <= 6; i++) {
                for (BakedQuad q : part.getQuads(ModelHelper.faceFromIndex(i))) {
                    vertices.putBakedQuad(matrices.last(), q, quadInstance);
                }
            }
        }
    }
    *///?}
    //?}
    //?}

    public static boolean isVanillaResourcePack(PackResources pack) {
        return (pack instanceof VanillaPackResources) ||
                // Terrible quilt compat hack
                ("org.quiltmc.qsl.resource.loader.api.GroupResourcePack$Wrapped".equals(pack.getClass().getName()));
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(EnhancedBlockEntities.NAMESPACE, path);
    }

    public static final String DUMP_FOLDER_NAME = "enhanced_bes_dump";

    public static void dumpResources() throws IOException {
        var path = FabricLoader.getInstance().getGameDir().resolve(DUMP_FOLDER_NAME);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        ResourceUtil.dumpAllPacks(path);
    }
}

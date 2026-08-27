package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.EBEUtil;
//? if fabric && <= 1.20 {
/*import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
*///?} else if fabric {
/*import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
*///?}
import net.minecraft.client.resources.model.BakedModel;
//? if forge {
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.BlockModelRotation;
//?}
//? if neoforge {
/*import net.neoforged.neoforge.client.event.ModelEvent;
*///?}
//? if neoforge {
/*import net.minecraft.client.resources.model.ModelResourceLocation;
*///?}
//? if forge {
import net.minecraft.client.resources.model.ModelResourceLocation;
//?}
//? if forge {
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.client.event.ModelEvent;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
//? if >= 1.21 {
/*import net.minecraft.world.level.block.entity.DecoratedPotPattern;
*///?}
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

//? if fabric && >= 1.21 {
/*public final class ModelIdentifiers implements ModelLoadingPlugin {
*///?} else {
public final class ModelIdentifiers {
//?}
    private static final Map<Predicate<EBEConfig>, Set<ResourceLocation>> modelLoaders = new HashMap<>();
    //? if neoforge {
    /*private static final Map<ResourceLocation, BakedModel> extraModels = new HashMap<>();
    *///?}
    //? if forge {
    private static final Map<ResourceLocation, BakedModel> extraModels = new HashMap<>();
    //?}

    public static final Predicate<EBEConfig> CHEST_PREDICATE = c -> c.renderEnhancedChests;
    public static final Predicate<EBEConfig> BELL_PREDICATE = c -> c.renderEnhancedBells;
    public static final Predicate<EBEConfig> SHULKER_BOX_PREDICATE = c -> c.renderEnhancedShulkerBoxes;
    public static final Predicate<EBEConfig> DECORATED_POT_PREDICATE = c -> c.renderEnhancedDecoratedPots;

    public static final ResourceLocation CHEST_CENTER = of("block/chest_center", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_CENTER_TRUNK = of("block/chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_CENTER_LID = of("block/chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT = of("block/chest_left", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT_TRUNK = of("block/chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_LEFT_LID = of("block/chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT = of("block/chest_right", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT_TRUNK = of("block/chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHEST_RIGHT_LID = of("block/chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation TRAPPED_CHEST_CENTER = of("block/trapped_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_CENTER_TRUNK = of("block/trapped_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_CENTER_LID = of("block/trapped_chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT = of("block/trapped_chest_left", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT_TRUNK = of("block/trapped_chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_LEFT_LID = of("block/trapped_chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT = of("block/trapped_chest_right", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT_TRUNK = of("block/trapped_chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation TRAPPED_CHEST_RIGHT_LID = of("block/trapped_chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation CHRISTMAS_CHEST_CENTER = of("block/christmas_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_CENTER_TRUNK = of("block/christmas_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_CENTER_LID = of("block/christmas_chest_center_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT = of("block/christmas_chest_left", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT_TRUNK = of("block/christmas_chest_left_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_LEFT_LID = of("block/christmas_chest_left_lid", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT = of("block/christmas_chest_right", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT_TRUNK = of("block/christmas_chest_right_trunk", CHEST_PREDICATE);
    public static final ResourceLocation CHRISTMAS_CHEST_RIGHT_LID = of("block/christmas_chest_right_lid", CHEST_PREDICATE);

    public static final ResourceLocation ENDER_CHEST_CENTER = of("block/ender_chest_center", CHEST_PREDICATE);
    public static final ResourceLocation ENDER_CHEST_CENTER_TRUNK = of("block/ender_chest_center_trunk", CHEST_PREDICATE);
    public static final ResourceLocation ENDER_CHEST_CENTER_LID = of("block/ender_chest_center_lid", CHEST_PREDICATE);

    public static final ResourceLocation BELL_BETWEEN_WALLS = of("block/bell_between_walls", BELL_PREDICATE);
    public static final ResourceLocation BELL_CEILING = of("block/bell_ceiling", BELL_PREDICATE);
    public static final ResourceLocation BELL_FLOOR = of("block/bell_floor", BELL_PREDICATE);
    public static final ResourceLocation BELL_WALL = of("block/bell_wall", BELL_PREDICATE);
    public static final ResourceLocation BELL_BETWEEN_WALLS_WITH_BELL = of("block/bell_between_walls_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_CEILING_WITH_BELL = of("block/bell_ceiling_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_FLOOR_WITH_BELL = of("block/bell_floor_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_WALL_WITH_BELL = of("block/bell_wall_with_bell", BELL_PREDICATE);
    public static final ResourceLocation BELL_BODY = of("block/bell_body", BELL_PREDICATE);

    public static final ResourceLocation DECORATED_POT_BASE = of("block/decorated_pot_base", DECORATED_POT_PREDICATE);
    public static final ResourceLocation DECORATED_POT_SHAKING = of("block/decorated_pot_shaking", DECORATED_POT_PREDICATE);

    public static final Map<DyeColor, ResourceLocation> SHULKER_BOXES = new HashMap<>();
    public static final Map<DyeColor, ResourceLocation> SHULKER_BOX_BOTTOMS = new HashMap<>();
    public static final Map<DyeColor, ResourceLocation> SHULKER_BOX_LIDS = new HashMap<>();

    //? if <= 1.20 {
    public static final Map<ResourceKey<String>, ResourceLocation[]> POTTERY_PATTERNS = new HashMap<>();
    //?} else {
    /*public static final Map<ResourceKey<DecoratedPotPattern>, ResourceLocation[]> POTTERY_PATTERNS = new HashMap<>();
    *///?}

    static {
        for (DyeColor color : EBEUtil.DEFAULTED_DYE_COLORS) {
            var id = color != null ? "block/"+color.getName()+"_shulker_box" : "block/shulker_box";
            SHULKER_BOXES.put(color, of(id, SHULKER_BOX_PREDICATE));
            SHULKER_BOX_BOTTOMS.put(color, of(id+"_bottom", SHULKER_BOX_PREDICATE));
            SHULKER_BOX_LIDS.put(color, of(id+"_lid", SHULKER_BOX_PREDICATE));
        }

        refreshPotteryPatterns();
    }

    public static void init() {
        //? if fabric && >= 1.21 {
        /*ModelLoadingPlugin.register(new ModelIdentifiers());
        *///?}
    }

    public static void refreshPotteryPatterns() {
        POTTERY_PATTERNS.clear();

        // The order decorated pots store patterns per face
        Direction[] orderedHorizontalDirs = new Direction[] {Direction.NORTH, Direction.WEST, Direction.EAST, Direction.SOUTH};

        //? if <= 1.20 {
        for (var patternKey : BuiltInRegistries.DECORATED_POT_PATTERNS.registryKeySet()) {
        //?} else {
        /*for (var patternKey : BuiltInRegistries.DECORATED_POT_PATTERN.registryKeySet()) {
        *///?}
            var pattern = patternKey.location().getPath();
            var ids = new ResourceLocation[orderedHorizontalDirs.length];;

            for (int i = 0; i < 4; i++) {
                ids[i] = of("block/" + pattern + "_" + orderedHorizontalDirs[i].getName(),
                        DECORATED_POT_PREDICATE);
            }

            POTTERY_PATTERNS.put(patternKey, ids);
        }
    }

    private static ResourceLocation of(String id, Predicate<EBEConfig> condition) {
        ResourceLocation idf = EBEUtil.rl(id);
        modelLoaders.computeIfAbsent(condition, k -> new HashSet<>()).add(idf);
        //? if fabric && <= 1.20 {
        /*ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, consumer) -> {
            if (condition.test(EnhancedBlockEntities.CONFIG)) consumer.accept(idf);
        });
        *///?}
        return idf;
    }

    public static Set<ResourceLocation> enabledModelIds() {
        var config = EnhancedBlockEntities.CONFIG;
        var ids = new HashSet<ResourceLocation>();

        for (var entry : modelLoaders.entrySet()) {
            if (entry.getKey().test(config)) ids.addAll(entry.getValue());
        }

        return ids;
    }

    //? if fabric && <= 1.20 {
    /*public static @Nullable BakedModel getBakedModel(ResourceLocation id) {
        return ((BakedModelManagerAccess) Minecraft.getInstance().getModelManager()).enhanced_bes$getModel(id);
    }
    *///?} else if fabric {
    /*public static @Nullable BakedModel getBakedModel(ResourceLocation id) {
        return Minecraft.getInstance().getModelManager().getModel(id);
    }
    *///?}
    //? if neoforge {
    /*public static @Nullable BakedModel getBakedModel(ResourceLocation id) {
        return extraModels.get(id);
    }

    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        var config = EnhancedBlockEntities.CONFIG;

        for (var entry : modelLoaders.entrySet()) {
            if (entry.getKey().test(config)) {
                for (var id : entry.getValue()) {
                    event.register(new ModelResourceLocation(id, "standalone"));
                }
            }
        }
    }

    public static void captureBakedModels(ModelEvent.BakingCompleted event) {
        extraModels.clear();
        var config = EnhancedBlockEntities.CONFIG;
        var baked = event.getModels();

        for (var entry : modelLoaders.entrySet()) {
            if (entry.getKey().test(config)) {
                for (var id : entry.getValue()) {
                    var model = baked.get(new ModelResourceLocation(id, "standalone"));
                    if (model != null) extraModels.put(id, model);
                }
            }
        }
    }
    *///?}
    //? if forge {
    public static @Nullable BakedModel getBakedModel(ResourceLocation id) {
        return extraModels.get(id);
    }

    private static ResourceLocation extraModelStateId(ResourceLocation id) {
        return EBEUtil.id("extra/" + id.getNamespace() + "/" + id.getPath());
    }

    public static void emitExtraModelBlockStates(EBEPack pack) {
        var config = EnhancedBlockEntities.CONFIG;

        for (var entry : modelLoaders.entrySet()) {
            if (!entry.getKey().test(config)) continue;

            for (var id : entry.getValue()) {
                var stateId = extraModelStateId(id);

                pack.addPlainTextResource(
                        EBEUtil.rl(stateId.getNamespace(), "blockstates/" + stateId.getPath() + ".json"),
                        "{\"variants\":{\"\":{\"model\":\"" + id + "\"}}}");
            }
        }
    }

    private static boolean extraModelsDirty = true;

    public static void bakeExtraModels(ModelBaker baker) {
        if (!extraModelsDirty) return;

        extraModelsDirty = false;
        extraModels.clear();

        var config = EnhancedBlockEntities.CONFIG;

        for (var entry : modelLoaders.entrySet()) {
            if (!entry.getKey().test(config)) continue;

            for (var id : entry.getValue()) {
                var model = baker.bake(id, BlockModelRotation.X0_Y0);
                if (model != null) extraModels.put(id, model);
            }
        }
    }

    public static void captureExtraModels(ModelEvent.ModifyBakingResult event) {
        extraModelsDirty = true;
    }
    //?}

    //? if fabric && >= 1.21 && <= 1.21 {
    /*@Override
    public void onInitializeModelLoader(Context ctx) {
        var config = EnhancedBlockEntities.CONFIG;

        for (var entry : modelLoaders.entrySet()) {
            if (entry.getKey().test(config)) {
                ctx.addModels(entry.getValue());
            }
        }
    }
    *///?} else if fabric && >= 1.21.2 {
    /*@Override
    public void initialize(Context ctx) {
        var config = EnhancedBlockEntities.CONFIG;

        for (var entry : modelLoaders.entrySet()) {
            if (entry.getKey().test(config)) {
                ctx.addModels(entry.getValue());
            }
        }
    }
    *///?}
}

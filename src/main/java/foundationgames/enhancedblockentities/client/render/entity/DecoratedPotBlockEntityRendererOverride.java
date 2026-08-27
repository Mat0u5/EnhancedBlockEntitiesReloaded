package foundationgames.enhancedblockentities.client.render.entity;

//? if <= 1.19.2 {
/*public final class DecoratedPotBlockEntityRendererOverride {
    private DecoratedPotBlockEntityRendererOverride() {
    }
}
*///?} else {
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
//? if >= 1.21 {
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
//?}
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;

import java.util.Map;
import java.util.Optional;

public class DecoratedPotBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public static final float WOBBLE_STRENGTH = 1f / 64;

    private BakedModel baseModel = null;
    //? if <= 1.20 {
    /*private Map<ResourceKey<String>, BakedModel[]> potPatternModels = null;
    *///?} else {
    private Map<ResourceKey<DecoratedPotPattern>, BakedModel[]> potPatternModels = null;
    //?}

    //? if <= 1.19.4 {
    /*private static Item sherdAt(DecoratedPotBlockEntity pot, int i) {
        return pot.getShards().get(i);
    }
    *///?} else if <= 1.20 {
    /*private static Item sherdAt(DecoratedPotBlockEntity pot, int i) {
        var decorations = pot.getDecorations();

        return switch (i) {
            case 0 -> decorations.back();
            case 1 -> decorations.left();
            case 2 -> decorations.right();
            default -> decorations.front();
        };
    }
    *///?} else {
    private static Optional<Item> sherdAt(DecoratedPotBlockEntity pot, int i) {
        var decorations = pot.getDecorations();

        return switch (i) {
            case 0 -> decorations.back();
            case 1 -> decorations.left();
            case 2 -> decorations.right();
            default -> decorations.front();
        };
    }
    //?}

    //? if <= 1.20 {
    /*private static ResourceKey<String> patternOf(Item sherd) {
        return DecoratedPotPatterns.getResourceKey(sherd);
    }
    *///?} else {
    private static ResourceKey<DecoratedPotPattern> patternOf(Optional<Item> sherd) {
        return sherd.map(DecoratedPotPatterns::getPatternFromItem).orElse(DecoratedPotPatterns.BLANK);
    }
    //?}

    private void tryGetModels() {
        if (this.baseModel == null) {
            this.baseModel = ModelIdentifiers.getBakedModel(ModelIdentifiers.DECORATED_POT_BASE);
        }

        if (this.potPatternModels == null) {
            //? if <= 1.20 {
            /*var builder = ImmutableMap.<ResourceKey<String>, BakedModel[]>builder();
            *///?} else {
            var builder = ImmutableMap.<ResourceKey<DecoratedPotPattern>, BakedModel[]>builder();
            //?}

            //? if <= 1.20 {
            /*BuiltInRegistries.DECORATED_POT_PATTERNS.registryKeySet().forEach(k -> {
            *///?} else {
            BuiltInRegistries.DECORATED_POT_PATTERN.registryKeySet().forEach(k -> {
            //?}
                var patternModelIDs = ModelIdentifiers.POTTERY_PATTERNS.get(k);
                BakedModel[] patternPerFaceModels = new BakedModel[patternModelIDs.length];

                for (int i = 0; i < patternModelIDs.length; i++) {
                    patternPerFaceModels[i] = ModelIdentifiers.getBakedModel(patternModelIDs[i]);
                }

                builder.put(k, patternPerFaceModels);
            });

            this.potPatternModels = builder.build();
        }
    }

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource output, int light, int overlay) {
        tryGetModels();

        if (blockEntity instanceof DecoratedPotBlockEntity pot) {
            matrices.pushPose();

            var dir = pot.getDirection();

            matrices.translate(0.5f, 0, 0.5f);
            matrices.mulPose(EBEUtil.rotYDeg(180 - EBEUtil.angle(dir)));
            matrices.translate(-0.5f, 0, -0.5f);

            //? if >= 1.21 {
            var wobbleType = pot.lastWobbleStyle;
            if (wobbleType != null && pot.getLevel() != null) {
                float tilt = ((float)(pot.getLevel().getGameTime() - pot.wobbleStartedAtTick) + tickDelta) / (float)wobbleType.duration;
                if (tilt >= 0.0F && tilt <= 1.0F) {
                    if (wobbleType == DecoratedPotBlockEntity.WobbleStyle.POSITIVE) {
                        float animPeriod = tilt * Mth.TWO_PI;

                        float tiltX = -1.5f * (Mth.cos(animPeriod) + 0.5f) * Mth.sin(animPeriod * 0.5f);
                        matrices.rotateAround(EBEUtil.rotXRad(tiltX * WOBBLE_STRENGTH), 0.5f, 0f, 0.5f);

                        float tiltZ = Mth.sin(animPeriod);
                        matrices.rotateAround(EBEUtil.rotZRad(tiltZ * WOBBLE_STRENGTH), 0.5f, 0f, 0.5f);
                    } else {
                        float yaw = (1f - tilt) * Mth.sin(-tilt * 3 * Mth.PI) * 0.125f;
                        matrices.rotateAround(EBEUtil.rotYRad(yaw), 0.5f, 0f, 0.5f);
                    }
                }
            }
            //?}

            EBEUtil.renderBakedModel(output, blockEntity.getBlockState(), matrices, this.baseModel, light, overlay);

            EBEUtil.renderBakedModel(output, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(patternOf(sherdAt(pot, 0)))[0], light, overlay);
            EBEUtil.renderBakedModel(output, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(patternOf(sherdAt(pot, 1)))[1], light, overlay);
            EBEUtil.renderBakedModel(output, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(patternOf(sherdAt(pot, 2)))[2], light, overlay);
            EBEUtil.renderBakedModel(output, blockEntity.getBlockState(), matrices,
                    this.potPatternModels.get(patternOf(sherdAt(pot, 3)))[3], light, overlay);

            matrices.popPose();
        }
    }

    @Override
    public void onModelsReload() {
        this.baseModel = null;
        this.potPatternModels = null;
    }
}
//?}

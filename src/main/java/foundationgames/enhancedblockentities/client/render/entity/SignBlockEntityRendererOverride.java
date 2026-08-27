package foundationgames.enhancedblockentities.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.AbstractSignBlockEntityRenderAccessor;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
//? if <= 1.19.2 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
*///?} else if <= 1.19.4 {
/*import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
*///?} else {
import net.minecraft.world.level.block.SignBlock;
//?}

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public SignBlockEntityRendererOverride() {}

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource output, int light, int overlay) {
        //? if <= 1.19.2 {
        /*if (blockEntity instanceof SignBlockEntity entity) {
            var state = entity.getBlockState();
            var client = Minecraft.getInstance();
            var font = client.font;

            matrices.pushPose();
            matrices.translate(0.5, 0.5, 0.5);

            if (state.getBlock() instanceof StandingSignBlock) {
                matrices.mulPose(EBEUtil.rotYNDeg((state.getValue(StandingSignBlock.ROTATION) * 360f) / 16));
            } else {
                matrices.mulPose(EBEUtil.rotYNDeg(state.getValue(WallSignBlock.FACING).toYRot()));
                matrices.translate(0.0, -0.3125, -0.4375);
            }

            matrices.translate(0.0, 0.3333333333333333, 0.046666666666666666);
            matrices.scale(0.010416667f, -0.010416667f, 0.010416667f);

            var lines = entity.getRenderMessages(client.isTextFilteringEnabled(), text -> {
                var wrapped = font.split(text, 90);
                return wrapped.isEmpty() ? FormattedCharSequence.EMPTY : wrapped.get(0);
            });

            int darkColor = AbstractSignBlockEntityRenderAccessor.enhanced_bes$getDarkColor(entity);
            int textColor;
            boolean outlined;
            int textLight;

            if (entity.hasGlowingText()) {
                textColor = entity.getColor().getTextColor();
                outlined = true;
                textLight = 15728880;
            } else {
                textColor = darkColor;
                outlined = false;
                textLight = light;
            }

            for (int i = 0; i < 4; i++) {
                var line = lines[i];
                float x = -font.width(line) / 2f;
                float y = (i * 10) - 20;

                if (outlined) {
                    font.drawInBatch8xOutline(line, x, y, textColor, darkColor, matrices.last().pose(), output, textLight);
                } else {
                    font.drawInBatch(line, x, y, textColor, false, matrices.last().pose(), output, false, 0, textLight);
                }
            }

            matrices.popPose();
        }
        *///?} else if <= 1.19.4 {
        /*if (blockEntity instanceof SignBlockEntity entity) {
            var state = entity.getBlockState();
            var sign = (AbstractSignBlockEntityRenderAccessor) renderer;

            matrices.pushPose();

            if (entity instanceof HangingSignBlockEntity) {
                matrices.translate(0.5, 15f / 128, 0.5);
                matrices.mulPose(EBEUtil.rotYNDeg(hangingSignRotation(state)));
                matrices.translate(0.0, -0.3125, 0.0);
                sign.enhanced_bes$renderText(entity, matrices, output, light, 1);
            } else {
                matrices.translate(0.5, 0.5, 0.5);

                if (state.getBlock() instanceof StandingSignBlock) {
                    matrices.mulPose(EBEUtil.rotYNDeg((state.getValue(StandingSignBlock.ROTATION) * 360f) / 16));
                } else {
                    matrices.mulPose(EBEUtil.rotYNDeg(state.getValue(WallSignBlock.FACING).toYRot()));
                    matrices.translate(0.0, -0.3125, -0.4375);
                }

                sign.enhanced_bes$renderText(entity, matrices, output, light, 2f / 3);
            }
        }
        *///?} else {
        if (blockEntity instanceof SignBlockEntity entity) {
            var state = entity.getBlockState();
            SignBlock block = (SignBlock) state.getBlock();
            var sign = (AbstractSignBlockEntityRenderAccessor) renderer;
            sign.enhanced_bes$applyTransforms(matrices, -block.getYRotationDegrees(state), state);
            sign.enhanced_bes$renderText(entity.getBlockPos(), entity.getFrontText(), matrices, output, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), true);
            sign.enhanced_bes$renderText(entity.getBlockPos(), entity.getBackText(), matrices, output, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), false);
        }
        //?}
    }

    //? if >= 1.19.4 && <= 1.19.4 {
    /*private static float hangingSignRotation(BlockState state) {
        if (state.getBlock() instanceof CeilingHangingSignBlock) {
            return (state.getValue(CeilingHangingSignBlock.ROTATION) * 360f) / 16;
        }

        return state.getValue(WallSignBlock.FACING).toYRot();
    }
    *///?}
}

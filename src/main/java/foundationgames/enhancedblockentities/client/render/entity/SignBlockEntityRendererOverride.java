package foundationgames.enhancedblockentities.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.AbstractSignBlockEntityRenderAccessor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
//? if <= 1.19.4 {
/*import com.mojang.math.Axis;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
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
        //? if <= 1.19.4 {
        /*if (blockEntity instanceof SignBlockEntity entity) {
            var state = entity.getBlockState();
            var sign = (AbstractSignBlockEntityRenderAccessor) renderer;

            matrices.pushPose();

            if (entity instanceof HangingSignBlockEntity) {
                matrices.translate(0.5, 15f / 128, 0.5);
                matrices.mulPose(Axis.YN.rotationDegrees(hangingSignRotation(state)));
                matrices.translate(0.0, -0.3125, 0.0);
                sign.enhanced_bes$renderText(entity, matrices, output, light, 1);
            } else {
                matrices.translate(0.5, 0.5, 0.5);

                if (state.getBlock() instanceof StandingSignBlock) {
                    matrices.mulPose(Axis.YN.rotationDegrees((state.getValue(StandingSignBlock.ROTATION) * 360f) / 16));
                } else {
                    matrices.mulPose(Axis.YN.rotationDegrees(state.getValue(WallSignBlock.FACING).toYRot()));
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

    //? if <= 1.19.4 {
    /*private static float hangingSignRotation(BlockState state) {
        if (state.getBlock() instanceof CeilingHangingSignBlock) {
            return (state.getValue(CeilingHangingSignBlock.ROTATION) * 360f) / 16;
        }

        return state.getValue(WallSignBlock.FACING).toYRot();
    }
    *///?}
}

package foundationgames.enhancedblockentities.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.AbstractSignBlockEntityRenderAccessor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public SignBlockEntityRendererOverride() {}

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (blockEntity instanceof SignBlockEntity entity) {
            var state = entity.getBlockState();
            SignBlock block = (SignBlock) state.getBlock();
            var sign = (AbstractSignBlockEntityRenderAccessor) renderer;
            sign.enhanced_bes$applyTransforms(matrices, -block.getYRotationDegrees(state), state);
            sign.enhanced_bes$renderText(entity.getBlockPos(), entity.getFrontText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), true);
            sign.enhanced_bes$renderText(entity.getBlockPos(), entity.getBackText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), false);
        }
    }
}

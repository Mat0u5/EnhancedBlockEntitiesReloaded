package foundationgames.enhancedblockentities.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.mixin.AbstractSignBlockEntityRenderAccessor;
//? if <= 1.21.6 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?} else {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
//?}
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class SignBlockEntityRendererOverride extends BlockEntityRendererOverride {
    public SignBlockEntityRendererOverride() {}

    @Override
    //? if <= 1.21.6 {
    /*public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource output, int light, int overlay) {
    *///?} else {
    public void render(BlockEntityRenderer<BlockEntity, ?> renderer, BlockEntityRenderState renderState, BlockEntity blockEntity, float tickDelta, PoseStack matrices, SubmitNodeCollector output, int light, int overlay) {
    //?}
        if (blockEntity instanceof SignBlockEntity entity) {
            var state = entity.getBlockState();
            SignBlock block = (SignBlock) state.getBlock();
            var sign = (AbstractSignBlockEntityRenderAccessor) renderer;
            sign.enhanced_bes$applyTransforms(matrices, -block.getYRotationDegrees(state), state);
            //? if <= 1.21.6 {
            /*sign.enhanced_bes$renderText(entity.getBlockPos(), entity.getFrontText(), matrices, output, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), true);
            sign.enhanced_bes$renderText(entity.getBlockPos(), entity.getBackText(), matrices, output, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), false);
            *///?} else {
            sign.enhanced_bes$submitSignText((SignRenderState) renderState, matrices, output, true);
            sign.enhanced_bes$submitSignText((SignRenderState) renderState, matrices, output, false);
            //?}
        }
    }
}

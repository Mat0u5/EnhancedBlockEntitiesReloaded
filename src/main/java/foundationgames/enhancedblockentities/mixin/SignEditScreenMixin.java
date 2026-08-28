package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import com.mojang.blaze3d.platform.Lighting;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
//? if <= 1.19.4 {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
//? if <= 1.19.2 {
/*import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
*///?}
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {
    private static final float SIGN_SCALE = 93.75f;

    //? if <= 1.19.2 {
    /*@Shadow private SignRenderer.SignModel signModel;
    @Shadow @Final private SignBlockEntity sign;

    @Inject(method = "render", at = @At("HEAD"))
    private void enhanced_bes$renderBakedModelSign(PoseStack context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        BlockState state = this.sign.getBlockState();

        boolean enhanceSigns = EnhancedBlockEntities.CONFIG.renderEnhancedSigns;

        if (!EnhancedBlockEntityRegistry.BLOCKS.contains(state.getBlock())) return;

        //? if <= 1.16 {
        /^this.signModel.sign.visible = !enhanceSigns;
        this.signModel.stick.visible = !enhanceSigns;
        ^///?} else {
        this.signModel.root.visible = !enhanceSigns;
        //?}

        if (enhanceSigns) {
            var self = (SignEditScreen) (Object) this;
            var models = Minecraft.getInstance().getModelManager().getBlockModelShaper();
            var buffers = Minecraft.getInstance().renderBuffers().bufferSource();

            if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
            } else if (state.hasProperty(BlockStateProperties.ROTATION_16)) {
                state = state.setValue(BlockStateProperties.ROTATION_16, 0);
            }

            var signModel = models.getBlockModel(state);

            context.pushPose();
            context.translate(self.width / 2d, 0.0, 50.0);
            context.scale(SIGN_SCALE, -SIGN_SCALE, SIGN_SCALE);
            context.translate(-0.5, -1.8125, -1);

            EBEUtil.renderBakedModel(buffers, state, context, signModel, 15728880, OverlayTexture.NO_OVERLAY);

            context.popPose();
        }
    }
    *///?} else {
    @Inject(method = "renderSignBackground", at = @At("HEAD"), cancellable = true)
    //? if <= 1.19.4 {
    /*private void enhanced_bes$renderBakedModelSign(PoseStack context, MultiBufferSource.BufferSource signBuffers, BlockState signState, CallbackInfo ci) {
    *///?} else if <= 1.21 {
    /*private void enhanced_bes$renderBakedModelSign(GuiGraphics context, BlockState signState, CallbackInfo ci) {
    *///?} else {
    private void enhanced_bes$renderBakedModelSign(GuiGraphics context, CallbackInfo ci) {
    //?}
        BlockState state = ((SignEditScreen) (Object) this).sign.getBlockState();

        boolean enhanceSigns = EnhancedBlockEntities.CONFIG.renderEnhancedSigns;

        if (!EnhancedBlockEntityRegistry.BLOCKS.contains(state.getBlock())) return;

        if (enhanceSigns) {
            var models = Minecraft.getInstance().getModelManager().getBlockModelShaper();
            float up = 0;
            if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
                up += 5f/16;
            } else if (state.hasProperty(BlockStateProperties.ROTATION_16)) {
                state = state.setValue(BlockStateProperties.ROTATION_16, 0);
            }

            var signModel = models.getBlockModel(state);

            //? if <= 1.19.4 {
            /*var buffers = Minecraft.getInstance().renderBuffers().bufferSource();
            var matrices = context;
            *///?} else {
            var buffers = Minecraft.getInstance().renderBuffers().bufferSource();
            var matrices = context.pose();
            //?}

            matrices.pushPose();
            matrices.translate(0, 31, -50);
            matrices.scale(SIGN_SCALE, -SIGN_SCALE, SIGN_SCALE);
            matrices.translate(-0.5, up - 0.5, 0);

            Lighting.setupFor3DItems();
            EBEUtil.renderBakedModel(buffers, state, matrices, signModel, 15728880, OverlayTexture.NO_OVERLAY);
            buffers.endBatch();
            Lighting.setupForFlatItems();

            matrices.popPose();

            ci.cancel();
        }
    }
    //?}
}

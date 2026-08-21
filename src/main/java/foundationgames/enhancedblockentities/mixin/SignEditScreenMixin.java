package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
//? if <= 1.21.5 {
/*import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.renderer.texture.OverlayTexture;
*///?} else {
import foundationgames.enhancedblockentities.client.render.gui.SignGuiElementRenderer;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
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

    @Inject(method = "renderSignBackground", at = @At("HEAD"), cancellable = true)
    private void enhanced_bes$renderBakedModelSign(GuiGraphics context, CallbackInfo ci) {
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

            //? if <= 1.21.5 {
            /*var buffers = Minecraft.getInstance().renderBuffers().bufferSource();
            var matrices = context.pose();

            matrices.pushPose();
            matrices.translate(0, 31, -50);
            matrices.scale(SIGN_SCALE, -SIGN_SCALE, SIGN_SCALE);
            matrices.translate(-0.5, up - 0.5, 0);

            EBEUtil.renderBakedModel(buffers, state, matrices, signModel, 15728880, OverlayTexture.NO_OVERLAY);

            matrices.popPose();
            *///?} else {
            int centerX = ((SignEditScreen) (Object) this).width / 2;

            context.guiRenderState.submitPicturesInPictureState(new SignGuiElementRenderer.State(
                    signModel, state, up, centerX - 48, 66, centerX + 48, 168, SIGN_SCALE, null));
            //?}

            ci.cancel();
        }
    }
}

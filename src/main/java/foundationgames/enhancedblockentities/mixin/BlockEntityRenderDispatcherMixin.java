package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.EnhancedBlockEntityRegistry;
import foundationgames.enhancedblockentities.client.render.BlockEntityRenderCondition;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.entity.BlockEntity;
//? if <= 1.21.6 {
/*import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
*///?} else {
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
//? if <= 1.21.11 {
/*import net.minecraft.client.renderer.state.CameraRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
//?}
//? if >= 1.21.5 <= 1.21.6 {
/*import net.minecraft.world.phys.Vec3;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >= 1.21.9 {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//?}

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    //? if <= 1.21.4 {
    /*@Inject(
            method = "setupAndRender(Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void enhanced_bes$renderOverrides(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource output, CallbackInfo ci) {
        if (EnhancedBlockEntityRegistry.ENTITIES.containsKey(blockEntity.getType()) && EnhancedBlockEntityRegistry.BLOCKS.contains(blockEntity.getBlockState().getBlock())) {
            EnhancedBlockEntityRegistry.Entry entry = EnhancedBlockEntityRegistry.ENTITIES.get(blockEntity.getType());
            if (entry.condition().shouldRender(blockEntity)) {
                entry.renderer().render(renderer, blockEntity, tickDelta, matrices, output, LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY);
            }
            ci.cancel();
        }
    }
    *///?}
    //? if >= 1.21.5 <= 1.21.6 {
    /*@Inject(
            method = "setupAndRender(Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/phys/Vec3;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void enhanced_bes$renderOverrides(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource output, Vec3 cameraPos, CallbackInfo ci) {
        if (EnhancedBlockEntityRegistry.ENTITIES.containsKey(blockEntity.getType()) && EnhancedBlockEntityRegistry.BLOCKS.contains(blockEntity.getBlockState().getBlock())) {
            EnhancedBlockEntityRegistry.Entry entry = EnhancedBlockEntityRegistry.ENTITIES.get(blockEntity.getType());
            if (entry.condition().shouldRender(blockEntity)) {
                entry.renderer().render(renderer, blockEntity, tickDelta, matrices, output, LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY);
            }
            ci.cancel();
        }
    }
    *///?}
    //? if >= 1.21.9 {
    @Inject(method = "tryExtractRenderState", at = @At("HEAD"), cancellable = true)
    //? if <= 26.1 {
    /*private void enhanced_bes$skipOverriddenExtraction(BlockEntity blockEntity, float partialTick,
            ModelFeatureRenderer.CrumblingOverlay crumbling, CallbackInfoReturnable<BlockEntityRenderState> cir) {
    *///?} else {
    private void enhanced_bes$skipOverriddenExtraction(BlockEntity blockEntity, float partialTick,
            ModelFeatureRenderer.CrumblingOverlay crumbling, boolean globalRender, CallbackInfoReturnable<BlockEntityRenderState> cir) {
    //?}
        EnhancedBlockEntityRegistry.Entry entry = EnhancedBlockEntityRegistry.ENTITIES.get(blockEntity.getType());

        if (entry == null || !EnhancedBlockEntityRegistry.BLOCKS.contains(blockEntity.getBlockState().getBlock())) return;

        if (!entry.condition().shouldRender(blockEntity)) {
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "submit", at = @At("HEAD"), cancellable = true)
    private void enhanced_bes$renderOverrides(BlockEntityRenderState renderState, PoseStack matrices, SubmitNodeCollector output, CameraRenderState cameraState, CallbackInfo ci) {
        EnhancedBlockEntityRegistry.Entry entry = EnhancedBlockEntityRegistry.ENTITIES.get(renderState.blockEntityType);

        if (entry == null || !EnhancedBlockEntityRegistry.BLOCKS.contains(renderState.blockState.getBlock())) return;

        ci.cancel();

        var level = Minecraft.getInstance().level;
        BlockEntity blockEntity = level != null ? level.getBlockEntity(renderState.blockPos) : null;
        if (blockEntity == null) return;

        BlockEntityRenderer<BlockEntity, ?> renderer = ((BlockEntityRenderDispatcher) (Object) this).getRenderer(renderState);
        float tickDelta = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);

        entry.renderer().render(renderer, renderState, blockEntity, tickDelta, matrices, output, renderState.lightCoords, OverlayTexture.NO_OVERLAY);
    }
    //?}
}

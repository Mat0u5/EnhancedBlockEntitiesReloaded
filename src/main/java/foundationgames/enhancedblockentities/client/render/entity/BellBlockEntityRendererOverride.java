package foundationgames.enhancedblockentities.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.client.render.BlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BellBlockEntityRendererOverride extends BlockEntityRendererOverride {
    private BakedModel bellModel = null;

    @Override
    public void render(BlockEntityRenderer<BlockEntity> renderer, BlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource output, int light, int overlay) {
        if (bellModel == null) bellModel = getBellModel();
        if (bellModel != null && blockEntity instanceof BellBlockEntity self) {
            float ringTicks = (float)self.ticks + tickDelta;
            float bellPitch = 0.0F;
            float bellRoll = 0.0F;
            if (self.shaking) {
                float swingAngle = Mth.sin(ringTicks / (float)Math.PI) / (4.0F + ringTicks / 3.0F);
                if (self.clickDirection == Direction.NORTH) {
                    bellPitch = -swingAngle;
                } else if (self.clickDirection == Direction.SOUTH) {
                    bellPitch = swingAngle;
                } else if (self.clickDirection == Direction.EAST) {
                    bellRoll = -swingAngle;
                } else if (self.clickDirection == Direction.WEST) {
                    bellRoll = swingAngle;
                }
            }
            matrices.pushPose();
            matrices.translate(8f/16, 12f/16, 8f/16);
            matrices.mulPose(EBEUtil.rotXRad(bellPitch));
            matrices.mulPose(EBEUtil.rotZRad(bellRoll));
            matrices.translate(-8f/16, -12f/16, -8f/16);
            EBEUtil.renderBakedModel(output, blockEntity.getBlockState(), matrices, bellModel, light, overlay);

            matrices.popPose();
        }
    }

    private BakedModel getBellModel() {
        return ModelIdentifiers.getBakedModel(ModelIdentifiers.BELL_BODY);
    }

    @Override
    public void onModelsReload() {
        bellModel = null;
    }
}

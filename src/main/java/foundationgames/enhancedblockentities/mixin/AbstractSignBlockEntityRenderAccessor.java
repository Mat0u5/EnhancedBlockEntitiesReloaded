package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;

@Mixin(SignRenderer.class)
public interface AbstractSignBlockEntityRenderAccessor {
    @Invoker("translateSign")
    void enhanced_bes$applyTransforms(PoseStack matrices, float rotationDegrees, BlockState state);

    @Invoker("renderSignText")
    void enhanced_bes$renderText(BlockPos pos, SignText signText, PoseStack matrices, MultiBufferSource output, int light, int lineHeight, int lineWidth, boolean front);

    @Accessor("OUTLINE_RENDER_DISTANCE")
    static int enhanced_bes$getRenderDistance() {
        throw new AssertionError();
    }
}

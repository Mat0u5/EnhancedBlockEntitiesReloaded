package foundationgames.enhancedblockentities.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
//? if <= 1.19.4 {
/*import net.minecraft.world.level.block.entity.SignBlockEntity;
*///?} else {
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
//?}

@Mixin(SignRenderer.class)
public interface AbstractSignBlockEntityRenderAccessor {
    //? if <= 1.19.2 {
    /*@Invoker("getDarkColor")
    static int enhanced_bes$getDarkColor(SignBlockEntity sign) {
        throw new AssertionError();
    }
    *///?} else if <= 1.19.4 {
    /*@Invoker("renderSignText")
    void enhanced_bes$renderText(SignBlockEntity entity, PoseStack matrices, MultiBufferSource output, int light, float scale);
    *///?} else {
    @Invoker("translateSign")
    void enhanced_bes$applyTransforms(PoseStack matrices, float rotationDegrees, BlockState state);

    @Invoker("renderSignText")
    void enhanced_bes$renderText(BlockPos pos, SignText signText, PoseStack matrices, MultiBufferSource output, int light, int lineHeight, int lineWidth, boolean front);
    //?}

    @Accessor("OUTLINE_RENDER_DISTANCE")
    static int enhanced_bes$getRenderDistance() {
        throw new AssertionError();
    }
}

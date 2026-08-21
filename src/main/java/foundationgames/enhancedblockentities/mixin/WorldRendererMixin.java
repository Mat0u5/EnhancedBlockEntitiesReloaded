package foundationgames.enhancedblockentities.mixin;

//? if >= 1.21.9 {
import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.render.SignRenderManager;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.LevelRenderState;
//?}
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {
    //? if >= 1.21.9 {
    @Inject(method = "submitBlockEntities", at = @At("HEAD"))
    private void enhanced_bes$endSignFrame(PoseStack matrices, LevelRenderState levelState, SubmitNodeStorage storage, CallbackInfo ci) {
        SignRenderManager.endFrame();
    }
    //?}

    @Inject(method = "addRecentlyCompiledSection", at = @At("HEAD"))
    private void enhanced_bes$runPostRebuildTask(SectionRenderDispatcher.RenderSection chunk, CallbackInfo ci) {
        ((ChunkRebuildTaskAccess) chunk).enhanced_bes$runAfterRebuildTask();
    }
}

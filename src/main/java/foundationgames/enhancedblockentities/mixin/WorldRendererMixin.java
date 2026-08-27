package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "addRecentlyCompiledSection", at = @At("HEAD"))
    private void enhanced_bes$runPostRebuildTask(SectionRenderDispatcher.RenderSection chunk, CallbackInfo ci) {
        ((ChunkRebuildTaskAccess) chunk).enhanced_bes$runAfterRebuildTask();
    }
}

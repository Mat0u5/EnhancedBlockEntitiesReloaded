package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.SectionPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SectionRenderDispatcher.RenderSection.class)
public class BuiltChunkMixin implements ChunkRebuildTaskAccess {
    private @Unique
    @Nullable Runnable enhanced_bes$taskAfterRebuild = null;

    @Inject(method = "createCompileTask", at = @At("HEAD"))
    private void enhanced_bes$addPostRebuildTask(RenderRegionCache cache, CallbackInfoReturnable<SectionRenderDispatcher.RenderSection.CompileTask> cir) {
        if (WorldUtil.CHUNK_UPDATE_TASKS.isEmpty()) return;

        var self = (SectionRenderDispatcher.RenderSection) (Object) this;
        //? if <= 1.21.4 {
        /*var pos = SectionPos.of(self.getOrigin());
        *///?} else {
        var pos = SectionPos.of(self.getRenderOrigin());
        //?}

        if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
            this.enhanced_bes$setTaskAfterRebuild(WorldUtil.CHUNK_UPDATE_TASKS.remove(pos));
        }
    }

    @Override
    public Runnable enhanced_bes$getTaskAfterRebuild() {
        return enhanced_bes$taskAfterRebuild;
    }

    @Override
    public void enhanced_bes$setTaskAfterRebuild(Runnable task) {
        enhanced_bes$taskAfterRebuild = task;
    }
}

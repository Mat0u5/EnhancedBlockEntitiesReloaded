package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.WorldUtil;
//? if >= 1.21.9 {
import com.mojang.blaze3d.vertex.PoseStack;
import foundationgames.enhancedblockentities.client.render.SignRenderManager;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.LevelRenderState;
//?}
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.SectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {
    //? if >= 1.21.9 {
    @Inject(method = "submitBlockEntities", at = @At("HEAD"))
    private void enhanced_bes$endSignFrame(PoseStack matrices, LevelRenderState levelState, SubmitNodeStorage storage, CallbackInfo ci) {
        SignRenderManager.endFrame();
    }
    //?}

    @ModifyVariable(method = "compileSections",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0, target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"),
            index = 7)
    private SectionRenderDispatcher.RenderSection enhanced_bes$addPostRebuildTask(SectionRenderDispatcher.RenderSection chunk) {
        if (WorldUtil.CHUNK_UPDATE_TASKS.size() > 0) {
            //? if <= 1.21.4 {
            /*var pos = SectionPos.of(chunk.getOrigin());
            *///?} else {
            var pos = SectionPos.of(chunk.getRenderOrigin());
            //?}

            if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
                var task = WorldUtil.CHUNK_UPDATE_TASKS.remove(pos);
                ((ChunkRebuildTaskAccess) chunk).enhanced_bes$setTaskAfterRebuild(task);
            }
        }

        return chunk;
    }

    @Inject(method = "addRecentlyCompiledSection", at = @At("HEAD"))
    private void enhanced_bes$runPostRebuildTask(SectionRenderDispatcher.RenderSection chunk, CallbackInfo ci) {
        ((ChunkRebuildTaskAccess) chunk).enhanced_bes$runAfterRebuildTask();
    }
}

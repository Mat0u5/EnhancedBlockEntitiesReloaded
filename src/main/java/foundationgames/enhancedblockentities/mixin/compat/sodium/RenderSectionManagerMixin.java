package foundationgames.enhancedblockentities.mixin.compat.sodium;

//? if (fabric || neoforge) && >= 1.21 {
import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import net.minecraft.core.SectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = RenderSectionManager.class, remap = false)
public class RenderSectionManagerMixin {
    @Inject(method = "createRebuildTask", at = @At("HEAD"), require = 0)
    private void enhanced_bes$compat_sodium$cacheUpdatingChunk(RenderSection section, int frame,
            CallbackInfoReturnable<?> cir) {
        if (WorldUtil.CHUNK_UPDATE_TASKS.isEmpty()) return;

        var pos = SectionPos.of(section.getChunkX(), section.getChunkY(), section.getChunkZ());

        if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
            var task = WorldUtil.CHUNK_UPDATE_TASKS.remove(pos);
            ((ChunkRebuildTaskAccess) section).enhanced_bes$setTaskAfterRebuild(task);
        }
    }
}
//?} else if fabric {
/*import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager", remap = false)
public class RenderSectionManagerMixin {
}
*///?}

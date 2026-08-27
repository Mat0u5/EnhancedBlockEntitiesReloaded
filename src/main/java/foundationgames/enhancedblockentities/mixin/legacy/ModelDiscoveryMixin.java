package foundationgames.enhancedblockentities.mixin.legacy;

//? if forge && >= 1.21.2 {
import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import net.minecraft.client.resources.model.ModelDiscovery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelDiscovery.class)
public class ModelDiscoveryMixin {
    @Shadow @Final private Map<ResourceLocation, UnbakedModel> inputModels;
    @Shadow @Final private Map<ModelResourceLocation, UnbakedModel> topModels;
    @Shadow @Final private Map<ResourceLocation, UnbakedModel> referencedModels;

    @Inject(method = "discoverDependencies", at = @At("HEAD"))
    private void enhanced_bes$discoverEnhancedModels(CallbackInfo ci) {
        for (ResourceLocation id : ModelIdentifiers.enabledModelIds()) {
            UnbakedModel model = this.inputModels.get(id);
            if (model == null) continue;

            this.topModels.putIfAbsent(new ModelResourceLocation(id, "standalone"), model);
            this.referencedModels.putIfAbsent(id, model);
        }
    }
}
//?}

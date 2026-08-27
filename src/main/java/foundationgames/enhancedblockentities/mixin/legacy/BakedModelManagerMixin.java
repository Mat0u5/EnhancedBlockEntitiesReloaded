package foundationgames.enhancedblockentities.mixin.legacy;

import net.minecraft.client.resources.model.ModelManager;
import org.spongepowered.asm.mixin.Mixin;
//? if <= 1.20 {
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
//?}

// The class must exist on every target: Mixin treats a missing mixin class as
// fatal even when the config is not required.
//? if <= 1.20 {
@Mixin(ModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerAccess {
    @Shadow private Map<ResourceLocation, BakedModel> bakedRegistry;

    @Override
    public BakedModel enhanced_bes$getModel(ResourceLocation id) {
        return this.bakedRegistry.get(id);
    }
}
//?} else {
/*@Mixin(ModelManager.class)
public class BakedModelManagerMixin {
}
*///?}

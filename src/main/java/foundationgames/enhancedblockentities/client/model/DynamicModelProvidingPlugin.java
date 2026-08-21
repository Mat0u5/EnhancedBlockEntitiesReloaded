package foundationgames.enhancedblockentities.client.model;

//? if <= 1.21.4 {
/*import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamicModelProvidingPlugin implements ModelLoadingPlugin, ModelModifier.OnLoad {
    private final Supplier<DynamicUnbakedModel> model;
    private final Identifier id;

    public DynamicModelProvidingPlugin(Identifier id, Supplier<DynamicUnbakedModel> model) {
        this.model = model;
        this.id = id;
    }

    public static void register(Identifier id, Supplier<DynamicUnbakedModel> model) {
        ModelLoadingPlugin.register(new DynamicModelProvidingPlugin(id, model));
    }

    @Override
    public void initialize(ModelLoadingPlugin.Context ctx) {
        ctx.modifyModelOnLoad().register(this);
    }

    @Override
    public @Nullable UnbakedModel modifyModelOnLoad(@Nullable UnbakedModel model, ModelModifier.OnLoad.Context context) {
        if (context.id().equals(this.id)) return this.model.get();
        return model;
    }
}
*///?} else {
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DynamicModelProvidingPlugin {
    private static final Map<Identifier, Supplier<DynamicUnbakedModel>> PROVIDERS = new HashMap<>();

    static {
        CustomUnbakedBlockStateModel.register(DynamicUnbakedModel.Unbaked.TYPE_ID, DynamicUnbakedModel.Unbaked.CODEC);
    }

    private DynamicModelProvidingPlugin() {
    }

    public static void register(Identifier id, Supplier<DynamicUnbakedModel> model) {
        PROVIDERS.put(id, model);
    }

    public static @Nullable DynamicUnbakedModel get(Identifier id) {
        var provider = PROVIDERS.get(id);
        return provider != null ? provider.get() : null;
    }
}
//?}

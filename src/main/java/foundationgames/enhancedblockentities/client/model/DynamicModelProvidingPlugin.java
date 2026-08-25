package foundationgames.enhancedblockentities.client.model;

//? if fabric && <= 1.21.4 {
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
*///?}
//? if neoforge && <= 1.21.4 {
/*import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.UnbakedModelLoader;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DynamicModelProvidingPlugin {
    private static final Map<Identifier, Supplier<DynamicUnbakedModel>> PROVIDERS = new HashMap<>();

    private DynamicModelProvidingPlugin() {
    }

    public static void register(Identifier id, Supplier<DynamicUnbakedModel> model) {
        PROVIDERS.put(id, model);
    }

    public static @Nullable DynamicUnbakedModel get(Identifier id) {
        var provider = PROVIDERS.get(id);
        return provider != null ? provider.get() : null;
    }

    public static void registerModelLoader(ModelEvent.RegisterLoaders event) {
        UnbakedModelLoader<DynamicUnbakedModel> loader = DynamicModelProvidingPlugin::read;
        event.register(EBEUtil.id("dynamic"), loader);
    }

    public static void emitModels(EBEPack pack) {
        for (var id : PROVIDERS.keySet()) {
            pack.addPlainTextResource(
                    Identifier.fromNamespaceAndPath(id.getNamespace(), "models/" + id.getPath() + ".json"),
                    "{\"loader\":\"" + EBEUtil.id("dynamic") + "\",\"key\":\"" + id + "\"}");
        }
    }

    private static DynamicUnbakedModel read(JsonObject json, JsonDeserializationContext context) {
        var key = Identifier.parse(json.get("key").getAsString());
        var model = get(key);

        if (model == null) {
            throw new JsonParseException("No Enhanced Block Entities dynamic model registered for " + key);
        }

        return model;
    }
}
*///?}
//? if >= 1.21.5 {
//? if fabric {
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
//?} else if neoforge {
/*import net.neoforged.neoforge.client.event.RegisterBlockStateModels;
*///?}
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DynamicModelProvidingPlugin {
    private static final Map<Identifier, Supplier<DynamicUnbakedModel>> PROVIDERS = new HashMap<>();

    //? if fabric {
    static {
        CustomUnbakedBlockStateModel.register(DynamicUnbakedModel.Unbaked.TYPE_ID, DynamicUnbakedModel.Unbaked.CODEC);
    }
    //?} else if neoforge {
    /*public static void registerModelType(RegisterBlockStateModels event) {
        event.registerModel(DynamicUnbakedModel.Unbaked.TYPE_ID, DynamicUnbakedModel.Unbaked.CODEC);
    }
    *///?}

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

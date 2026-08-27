package foundationgames.enhancedblockentities.client.model;

//? if fabric && <= 1.20 {
/*import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamicModelProvidingPlugin implements ModelResourceProvider {
    private final Supplier<DynamicUnbakedModel> model;
    private final ResourceLocation id;

    public DynamicModelProvidingPlugin(ResourceLocation id, Supplier<DynamicUnbakedModel> model) {
        this.model = model;
        this.id = id;
    }

    public static void register(ResourceLocation id, Supplier<DynamicUnbakedModel> model) {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> new DynamicModelProvidingPlugin(id, model));
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        if (resourceId.equals(this.id)) return this.model.get();
        return null;
    }
}
*///?} else if fabric {
/*import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelResolver;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamicModelProvidingPlugin implements ModelLoadingPlugin, ModelResolver {
    private final Supplier<DynamicUnbakedModel> model;
    private final ResourceLocation id;

    public DynamicModelProvidingPlugin(ResourceLocation id, Supplier<DynamicUnbakedModel> model) {
        this.model = model;
        this.id = id;
    }

    public static void register(ResourceLocation id, Supplier<DynamicUnbakedModel> model) {
        ModelLoadingPlugin.register(new DynamicModelProvidingPlugin(id, model));
    }

    //? if <= 1.21 {
    @Override
    public void onInitializeModelLoader(ModelLoadingPlugin.Context ctx) {
        ctx.resolveModel().register(this);
    }
    //?} else {
    /^@Override
    public void initialize(ModelLoadingPlugin.Context ctx) {
        ctx.resolveModel().register(this);
    }
    ^///?}

    @Override
    public @Nullable UnbakedModel resolveModel(ModelResolver.Context ctx) {
        if (ctx.id().equals(this.id)) return this.model.get();
        return null;
    }
}
*///?}
//? if neoforge {
/*import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DynamicModelProvidingPlugin {
    private static final Map<ResourceLocation, Supplier<DynamicUnbakedModel>> PROVIDERS = new HashMap<>();

    private DynamicModelProvidingPlugin() {
    }

    public static void register(ResourceLocation id, Supplier<DynamicUnbakedModel> model) {
        PROVIDERS.put(id, model);
    }

    public static @Nullable DynamicUnbakedModel get(ResourceLocation id) {
        var provider = PROVIDERS.get(id);
        return provider != null ? provider.get() : null;
    }

    public static void registerModelLoader(ModelEvent.RegisterGeometryLoaders event) {
        IGeometryLoader<DynamicUnbakedModel> loader = DynamicModelProvidingPlugin::read;
        event.register(EBEUtil.id("dynamic"), loader);
    }

    public static void emitModels(EBEPack pack) {
        for (var id : PROVIDERS.keySet()) {
            pack.addPlainTextResource(
                    EBEUtil.rl(id.getNamespace(), "models/" + id.getPath() + ".json"),
                    "{\"elements\":[],\"loader\":\"" + EBEUtil.id("dynamic") + "\",\"key\":\"" + id + "\"}");
        }
    }

    private static DynamicUnbakedModel read(JsonObject json, JsonDeserializationContext context) {
        var key = EBEUtil.rl(json.get("key").getAsString());
        var model = get(key);

        if (model == null) {
            throw new JsonParseException("No Enhanced Block Entities dynamic model registered for " + key);
        }

        return model;
    }
}
*///?}
//? if forge {
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import foundationgames.enhancedblockentities.Main;
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DynamicModelProvidingPlugin {
    private static final Map<ResourceLocation, Supplier<DynamicUnbakedModel>> PROVIDERS = new HashMap<>();

    private DynamicModelProvidingPlugin() {
    }

    public static void register(ResourceLocation id, Supplier<DynamicUnbakedModel> model) {
        PROVIDERS.put(id, model);
    }

    public static @Nullable DynamicUnbakedModel get(ResourceLocation id) {
        var provider = PROVIDERS.get(id);
        return provider != null ? provider.get() : null;
    }

    public static void registerGeometryLoader(ModelEvent.RegisterGeometryLoaders event) {
        IGeometryLoader<DynamicUnbakedModel> loader = DynamicModelProvidingPlugin::readGeometry;
        event.register("dynamic", loader);
    }

    private static String loaderId() {
        return Main.MOD_ID + ":dynamic";
    }

    public static void emitModels(EBEPack pack) {
        for (var id : PROVIDERS.keySet()) {
            pack.addPlainTextResource(
                    EBEUtil.rl(id.getNamespace(), "models/" + id.getPath() + ".json"),
                    "{\"elements\":[],\"loader\":\"" + loaderId() + "\",\"key\":\"" + id + "\"}");
        }
    }

    private static DynamicUnbakedModel readGeometry(JsonObject json, JsonDeserializationContext context) {
        var key = EBEUtil.rl(json.get("key").getAsString());
        var model = get(key);

        if (model == null) {
            throw new JsonParseException("No Enhanced Block Entities dynamic model registered for " + key);
        }

        return model;
    }
}
//?}

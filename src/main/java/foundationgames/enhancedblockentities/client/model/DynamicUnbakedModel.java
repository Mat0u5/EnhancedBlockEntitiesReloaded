package foundationgames.enhancedblockentities.client.model;

//? if <= 1.21.4 {
/*import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DynamicUnbakedModel implements UnbakedModel {
    private final ResourceLocation[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicUnbakedModel(ResourceLocation[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    @Override
    public void resolveDependencies(Resolver resolver) {
        for (ResourceLocation modelId : models) {
            if(modelId == null) continue;
            resolver.resolve(modelId);
        }
    }

    @Override
    public @Nullable BakedModel bake(TextureSlots textures, ModelBaker baker, ModelState settings, boolean ambientOcclusion, boolean isSideLit, ItemTransforms transformation) {
        BakedModel[] baked = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            baked[i] = baker.bake(models[i], settings);
        }
        return new DynamicBakedModel(baked, selector, effects);
    }
}
*///?} else {
import com.mojang.serialization.MapCodec;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.MissingBlockModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.resources.ResourceLocation;

public class DynamicUnbakedModel {
    private final ResourceLocation[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicUnbakedModel(ResourceLocation[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    public void resolveDependencies(ResolvableModel.Resolver resolver) {
        for (ResourceLocation modelId : models) {
            if (modelId == null) continue;
            resolver.markDependency(modelId);
        }
    }

    public BlockStateModel bake(ModelBaker baker, ModelState settings) {
        BlockModelPart[] baked = new BlockModelPart[models.length];
        for (int i = 0; i < models.length; i++) {
            baked[i] = models[i] != null ? SimpleModelWrapper.bake(baker, models[i], settings) : null;
        }
        return new DynamicBakedModel(baked, selector, effects);
    }

    public record Unbaked(Variant variant) implements CustomUnbakedBlockStateModel {
        public static final ResourceLocation TYPE_ID = EBEUtil.id("dynamic");
        public static final MapCodec<Unbaked> CODEC = Variant.MAP_CODEC.xmap(Unbaked::new, Unbaked::variant);

        @Override
        public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
            return CODEC;
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            var model = DynamicModelProvidingPlugin.get(variant.modelLocation());

            if (model != null) {
                model.resolveDependencies(resolver);
            } else {
                resolver.markDependency(MissingBlockModel.LOCATION);
            }
        }

        @Override
        public BlockStateModel bake(ModelBaker baker) {
            var model = DynamicModelProvidingPlugin.get(variant.modelLocation());
            var settings = variant.modelState().asModelState();

            if (model == null) {
                return new SingleVariant(SimpleModelWrapper.bake(baker, MissingBlockModel.LOCATION, settings));
            }

            return model.bake(baker, settings);
        }
    }
}
//?}

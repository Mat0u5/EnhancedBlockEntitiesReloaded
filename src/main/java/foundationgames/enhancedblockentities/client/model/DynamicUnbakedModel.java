package foundationgames.enhancedblockentities.client.model;

//? if forge {
/*import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
//? if <= 1.21 {
/^import net.minecraft.client.renderer.block.model.ItemOverrides;
^///?}
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class DynamicUnbakedModel implements IUnbakedGeometry<DynamicUnbakedModel> {
    private final ResourceLocation[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicUnbakedModel(ResourceLocation[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    //? if <= 1.21 {
    /^@Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver, IGeometryBakingContext context) {
        for (ResourceLocation modelId : models) {
            if (modelId == null) continue;
            resolver.apply(modelId);
        }
    }

    @Override
    //? if <= 1.20 {
    /^¹public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState settings, ItemOverrides overrides, ResourceLocation location) {
    ¹^///?} else {
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState settings, ItemOverrides overrides) {
    //?}
    ^///?} else {
    @Override
    public void resolveDependencies(UnbakedModel.Resolver resolver, IGeometryBakingContext context) {
        for (ResourceLocation modelId : models) {
            if (modelId == null) continue;
            resolver.resolve(modelId);
        }
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState settings) {
    //?}
        ModelIdentifiers.bakeExtraModels(baker);

        BakedModel[] baked = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            baked[i] = baker.bake(models[i], settings);
        }
        return new DynamicBakedModel(baked, selector, effects);
    }
}
*///?} else {
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
//? if neoforge {
/*//? if <= 1.21 {
import net.minecraft.client.renderer.block.model.ItemOverrides;
//?} else {
/^import net.minecraft.client.renderer.block.model.ItemOverride;

import java.util.List;
^///?}
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
*///?}
import org.jetbrains.annotations.Nullable;

//? if <= 1.21 {
/*import java.util.Collection;
import java.util.Collections;
*///?}
import java.util.function.Function;

//? if neoforge {
/*public class DynamicUnbakedModel implements IUnbakedGeometry<DynamicUnbakedModel> {
*///?} else {
public class DynamicUnbakedModel implements UnbakedModel {
//?}
    private final ResourceLocation[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicUnbakedModel(ResourceLocation[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    //? if neoforge {
    /*//? if <= 1.21 {
    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver, IGeometryBakingContext context) {
        for (ResourceLocation modelId : models) {
            if (modelId == null) continue;
            resolver.apply(modelId);
        }
    }
    //?} else {
    /^@Override
    public void resolveDependencies(UnbakedModel.Resolver resolver, IGeometryBakingContext context) {
        for (ResourceLocation modelId : models) {
            if (modelId == null) continue;
            resolver.resolve(modelId);
        }
    }
    ^///?}
    *///?} else if <= 1.21 {
    /*@Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
    }
    *///?} else {
    @Override
    public void resolveDependencies(Resolver resolver) {
        for (ResourceLocation modelId : models) {
            if (modelId == null) continue;
            resolver.resolve(modelId);
        }
    }
    //?}

    //? if neoforge {
    /*//? if <= 1.21 {
    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState settings, ItemOverrides overrides) {
    //?} else {
    /^@Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState settings, List<ItemOverride> overrides) {
    ^///?}
    *///?} else {
    //? if <= 1.20 {
    /*@Override
    public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState settings, ResourceLocation location) {
    *///?} else {
    @Override
    public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState settings) {
    //?}
    //?}
        BakedModel[] baked = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            baked[i] = baker.bake(models[i], settings);
        }
        return new DynamicBakedModel(baked, selector, effects);
    }
}
//?}

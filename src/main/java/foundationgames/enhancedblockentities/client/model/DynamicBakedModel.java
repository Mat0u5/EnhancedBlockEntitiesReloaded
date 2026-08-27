package foundationgames.enhancedblockentities.client.model;

//? if fabric {
/*import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BakedQuad;
//? if <= 1.21 {
/^import net.minecraft.client.renderer.block.model.ItemOverrides;
^///?}
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class DynamicBakedModel implements BakedModel, FabricBakedModel {
    private final BakedModel[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    private final ThreadLocal<int[]> activeModelIndices;
    private final ThreadLocal<BakedModel[]> displayedModels;

    public DynamicBakedModel(BakedModel[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;

        this.activeModelIndices = ThreadLocal.withInitial(() -> new int[selector.displayedModelCount]);
        this.displayedModels = ThreadLocal.withInitial(() -> new BakedModel[selector.displayedModelCount]);
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter view, BlockState state, BlockPos pos, Supplier<RandomSource> rng, RenderContext context) {
        QuadEmitter emitter = context.getEmitter();
        RenderMaterial mat = null;

        var indices = this.activeModelIndices.get();
        var models = this.displayedModels.get();

        getSelector().writeModelIndices(view, state, pos, rng, indices);
        for (int i = 0; i < indices.length; i++) {
            int modelIndex = indices[i];

            if (modelIndex >= 0) {
                models[i] = this.models[modelIndex];
            } else {
                models[i] = null;
            }
        }

        var renderer = RendererAccess.INSTANCE.getRenderer();
        if (renderer != null) {
            mat = renderer.materialById(RenderMaterial.MATERIAL_STANDARD);
        }

        for (int i = 0; i <= 6; i++) {
            Direction dir = ModelHelper.faceFromIndex(i);
            for (BakedModel model : models) if (model != null) {
                for (BakedQuad quad : model.getQuads(state, dir, rng.get())) {
                    emitter.fromVanilla(quad, mat, dir);
                    emitter.emit();
                }
            }
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> rng, RenderContext context) {
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, RandomSource random) {
        return models[0].getQuads(state, face, random);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return getEffects().ambientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    //? if <= 1.21 {
    /^@Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
    ^///?}

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return models[getSelector().getParticleModelIndex()].getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return null;
    }

    public BakedModel[] getModels() {
        return models;
    }

    public ModelSelector getSelector() {
        return selector;
    }

    public DynamicModelEffects getEffects() {
        return effects;
    }
}
*///?}
//? if neoforge {
/*import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
//? if <= 1.21 {
import net.minecraft.client.renderer.block.model.ItemOverrides;
//?}
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DynamicBakedModel implements IDynamicBakedModel {
    public static final ModelProperty<int[]> MODEL_INDICES = new ModelProperty<>();

    private static final ThreadLocal<RandomSource> RANDOM = ThreadLocal.withInitial(RandomSource::create);

    private final BakedModel[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicBakedModel(BakedModel[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    @Override
    public ModelData getModelData(BlockAndTintGetter view, BlockPos pos, BlockState state, ModelData modelData) {
        var indices = new int[this.selector.displayedModelCount];
        this.selector.writeModelIndices(view, state, pos, RANDOM::get, indices);

        return modelData.derive().with(MODEL_INDICES, indices).build();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, RandomSource random, ModelData data, @Nullable RenderType renderType) {
        var indices = data.get(MODEL_INDICES);
        if (indices == null) return this.models[0].getQuads(state, face, random);

        var quads = new ArrayList<BakedQuad>();
        for (int modelIndex : indices) {
            if (modelIndex < 0 || modelIndex >= this.models.length) continue;

            var model = this.models[modelIndex];
            if (model != null) quads.addAll(model.getQuads(state, face, random, data, renderType));
        }

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return getEffects().ambientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    //? if <= 1.21 {
    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
    //?}

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return models[getSelector().getParticleModelIndex()].getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return null;
    }

    public BakedModel[] getModels() {
        return models;
    }

    public ModelSelector getSelector() {
        return selector;
    }

    public DynamicModelEffects getEffects() {
        return effects;
    }
}
*///?}
//? if forge {
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
//? if <= 1.21 {
/*import net.minecraft.client.renderer.block.model.ItemOverrides;
*///?}
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DynamicBakedModel implements IDynamicBakedModel {
    public static final ModelProperty<int[]> MODEL_INDICES = new ModelProperty<>();

    private static final ThreadLocal<RandomSource> RANDOM = ThreadLocal.withInitial(RandomSource::create);

    private final BakedModel[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicBakedModel(BakedModel[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    @Override
    public ModelData getModelData(BlockAndTintGetter view, BlockPos pos, BlockState state, ModelData modelData) {
        var indices = new int[this.selector.displayedModelCount];
        this.selector.writeModelIndices(view, state, pos, RANDOM::get, indices);

        return modelData.derive().with(MODEL_INDICES, indices).build();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, RandomSource random, ModelData data, @Nullable RenderType renderType) {
        var indices = data.get(MODEL_INDICES);
        if (indices == null) return this.models[0].getQuads(state, face, random);

        var quads = new ArrayList<BakedQuad>();
        for (int modelIndex : indices) {
            if (modelIndex < 0 || modelIndex >= this.models.length) continue;

            var model = this.models[modelIndex];
            if (model != null) quads.addAll(model.getQuads(state, face, random, data, renderType));
        }

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return getEffects().ambientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    //? if <= 1.21 {
    /*@Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
    *///?}

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return models[getSelector().getParticleModelIndex()].getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return null;
    }

    public BakedModel[] getModels() {
        return models;
    }

    public ModelSelector getSelector() {
        return selector;
    }

    public DynamicModelEffects getEffects() {
        return effects;
    }
}
//?}

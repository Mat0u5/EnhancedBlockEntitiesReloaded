package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.FallbackResourceManager;
//? if <= 1.16 {
/*import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
*///?} else if <= 1.17 {
/*import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
*///?} else {
import net.minecraft.server.packs.resources.MultiPackResourceManager;
//?}
import net.minecraft.server.packs.resources.ResourceManager;
//? if <= 1.17 {
/*import net.minecraft.util.Unit;
*///?}
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
//? if <= 1.17 {
/*import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?} else {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//? if <= 1.17 {
/*import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
*///?}

//? if <= 1.17 {
/*@Mixin(SimpleReloadableResourceManager.class)
*///?} else {
@Mixin(MultiPackResourceManager.class)
//?}
public abstract class LifecycledResourceManagerImplMixin {
    //? if <= 1.17 {
    /*@Shadow @Final          private Map<String, FallbackResourceManager> namespacedPacks;
    @Shadow @Final          private PackType type;
    *///?} else {
    @Shadow @Final          private Map<String, FallbackResourceManager> namespacedManagers;
    //?}

    //? if <= 1.16 {
    /*@ModifyVariable(method = "createFullReload", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    *///?} else if <= 1.17 {
    /*@ModifyVariable(method = "createReload", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    *///?} else {
    @ModifyVariable(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;copyOf(Ljava/util/Collection;)Ljava/util/List;", shift = At.Shift.BEFORE), ordinal = 0)
    //?}
    private List<PackResources> enhanced_bes$injectBasePack(List<PackResources> old) {
        var packs = new ArrayList<>(old);

        int idx = 0;
        if (packs.size() > 0) do {
            idx++;
        } while (idx < packs.size() && !EBEUtil.isVanillaResourcePack(packs.get(idx - 1)));
        packs.add(idx, ResourceUtil.getBasePack());

        return packs;
    }

    //? if <= 1.16 {
    /*@Inject(method = "createReload", at = @At("HEAD"))
    private void enhanced_bes$injectTopLevelPack(Executor backgroundExecutor, Executor mainExecutor, List<PreparableReloadListener> listeners, CompletableFuture<Unit> waiting, CallbackInfoReturnable<ReloadInstance> cir) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();

        addPack(this.type, ResourceUtil.getTopLevelPack());
    }
    *///?} else if <= 1.17 {
    /*@Inject(method = "createReload", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;isDebugEnabled()Z"))
    private void enhanced_bes$injectTopLevelPack(Executor backgroundExecutor, Executor mainExecutor, CompletableFuture<Unit> waiting, List<PackResources> packs, CallbackInfoReturnable<ReloadInstance> cir) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();

        addPack(this.type, ResourceUtil.getTopLevelPack());
    }
    *///?} else {
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void enhanced_bes$injectTopLevelPack(PackType type, List<PackResources> packs, CallbackInfo ci) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();

        addPack(type, ResourceUtil.getTopLevelPack());
    }
    //?}

    private void addPack(PackType type, PackResources pack) {
        for (var namespace : pack.getNamespaces(type)) {
            //? if <= 1.17 {
            /*this.namespacedPacks.computeIfAbsent(namespace, n -> new FallbackResourceManager(type, n)).add(pack);
            *///?} else if <= 1.18 {
            /*this.namespacedManagers.computeIfAbsent(namespace, n -> new FallbackResourceManager(type, n)).add(pack);
            *///?} else {
            this.namespacedManagers.computeIfAbsent(namespace, n -> new FallbackResourceManager(type, n)).push(pack);
            //?}
        }
    }
}

package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.duck.AppearanceStateHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BellBlockEntity.class)
public class BellBlockEntityMixin extends BlockEntity implements AppearanceStateHolder {
    @Unique private int enhanced_bes$modelState = 0;
    @Unique private int enhanced_bes$renderState = 0;

    //? if <= 1.16 {
    /*public BellBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }
    *///?} else {
    public BellBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    //?}

    //? if <= 1.16 {
    /*@Inject(method = "tick", at = @At("TAIL"))
    private void enhanced_bes$listenForStopRinging(CallbackInfo ci) {
        var blockEntity = (BellBlockEntity) (Object) this;
        int mState = blockEntity.ticks > 0 ? 1 : 0;
        if (EnhancedBlockEntities.CONFIG.renderEnhancedBells && this.getModelState() != mState) {
            this.updateAppearanceState(mState, blockEntity.getLevel(), blockEntity.getBlockPos());
        }
    }
    *///?} else {
    // Can't inject because one of the args' type is private (BellBlockEntity$Effect) and I don't feel like AW
    // So now you get to suffer through this
    @ModifyVariable(method = "tick", at = @At(
            value = "JUMP", opcode = Opcodes.IF_ICMPLT, ordinal = 0, shift = At.Shift.BEFORE
    ), index = 3)
    private static BellBlockEntity enhanced_bes$listenForStopRinging(BellBlockEntity blockEntity) {
        int mState = blockEntity.ticks > 0 ? 1 : 0;
        if (EnhancedBlockEntities.CONFIG.renderEnhancedBells && ((AppearanceStateHolder)blockEntity).getModelState() != mState) {
            ((AppearanceStateHolder)blockEntity).updateAppearanceState(mState, blockEntity.getLevel(), blockEntity.getBlockPos());
        }
        return blockEntity;
    }
    //?}

    @Override
    public int getModelState() {
        return enhanced_bes$modelState;
    }

    @Override
    public void setModelState(int state) {
        this.enhanced_bes$modelState = state;
    }

    @Override
    public int getRenderState() {
        return enhanced_bes$renderState;
    }

    @Override
    public void setRenderState(int state) {
        this.enhanced_bes$renderState = state;
    }
}

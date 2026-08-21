package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.config.gui.option.ConfigButtonOption;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoOptionsScreenMixin extends Screen {
    @Shadow protected OptionsList list;

    protected VideoOptionsScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    private void enhanced_bes$addEBEOptionButton(CallbackInfo ci) {
        this.list.addSmall(ConfigButtonOption.getOption(this));
    }
}

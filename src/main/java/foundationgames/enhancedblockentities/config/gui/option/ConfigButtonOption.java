package foundationgames.enhancedblockentities.config.gui.option;

//? if <= 1.18 {
/*import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
import foundationgames.enhancedblockentities.util.EBEUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;

public class ConfigButtonOption {
    public static Option getOption(Screen parent) {
        return new Option("option.ebe.config") {
            @Override
            public AbstractWidget createButton(Options gameOptions, int x, int y, int width) {
                return new Button(x, y, width, 20, EBEUtil.translate("option.ebe.config"), b -> {
                    Minecraft.getInstance().setScreen(new EBEConfigScreen(parent));
                });
            }
        };
    }
}
*///?} else {
import foundationgames.enhancedblockentities.util.EBEUtil;
import com.mojang.serialization.Codec;
import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConfigButtonOption {
    public static OptionInstance<?> getOption(Screen parent) {
        return new OptionInstance<>(
            "option.ebe.config",
            OptionInstance.noTooltip(),
            (title, object) -> title,
            new ConfigButtonCallbacks<>(parent),
            Optional.empty(),
            value -> {
            }
        );
    }

    private record ConfigButtonCallbacks<T>(Screen parent) implements OptionInstance.ValueSet<T> {
        //? if <= 1.19.2 {
        /*@Override
        public Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> tooltipFactory, Options gameOptions, int x, int y, int width) {
            return (option) -> new Button(x, y, width, 20, EBEUtil.translate("option.ebe.config"), b -> {
                Minecraft.getInstance().setScreen(new EBEConfigScreen(parent));
            });
        }
        *///?} else {
        @Override
        public Function<OptionInstance<T>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<T> tooltipFactory, Options gameOptions, int x, int y, int width, Consumer<T> changed) {
            return (option) -> Button.builder(EBEUtil.translate("option.ebe.config"), b -> {
                Minecraft.getInstance().setScreen(new EBEConfigScreen(parent));
            }).bounds(x, y, width, 20).build();
        }
        //?}

        @Override
        public Optional<T> validateValue(T value) {
            return Optional.empty();
        }

        @Override
        public Codec<T> codec() {
            return null;
        }
    }
}
//?}

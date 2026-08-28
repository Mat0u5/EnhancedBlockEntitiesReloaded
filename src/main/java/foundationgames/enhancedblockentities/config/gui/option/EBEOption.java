package foundationgames.enhancedblockentities.config.gui.option;

import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.ReloadType;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.GuiUtil;
import net.minecraft.ChatFormatting;
//? if >= 1.19.4 {
import net.minecraft.client.gui.components.Tooltip;
//?}
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
//? if <= 1.16 {
/*import net.minecraft.network.chat.TextColor;
*///?}
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class EBEOption {
    private static final Component NEWLINE = Component.nullToEmpty("\n");
    private static final String OPTION_VALUE = "options.generic_value";
    private static final String DIVIDER = "text.ebe.option_value_division";
    private static final String OVERRIDDEN = "warning.ebe.overridden";

    public final String key;
    public final boolean hasValueComments;
    public final Component comment;
    public final ReloadType reloadType;
    public final TextPalette palette;
    public final @Nullable EBEConfig.Override override;

    private final List<String> values;
    private final int defaultValue;

    private int selected;
    private Component tooltip = null;
    private Component text = null;

    public EBEOption(String key, List<String> values, ConfigView config, boolean hasValueComments, TextPalette palette, ReloadType reloadType) {
        this.key = key;
        this.values = values;
        this.defaultValue = Mth.clamp(values.indexOf(config.configValues.getProperty(key)), 0, values.size());
        this.override = config.overrides.get(key);
        this.selected = this.defaultValue;
        this.hasValueComments = hasValueComments;
        this.palette = palette;
        this.reloadType = reloadType;

        String commentKey = I18n.get(String.format("option.ebe.%s.comment", key));
        comment = GuiUtil.shorten(commentKey, 20);
    }

    public String getValue() {
        return values.get(selected);
    }

    public String getOptionKey() {
        return String.format("option.ebe.%s", key);
    }

    public String getValueKey() {
        return String.format("value.ebe.%s", getValue());
    }

    public Component getText() {
        //? if <= 1.16 {
        /*var option = EBEUtil.translate(this.getOptionKey()).withStyle(style -> style.withColor(TextColor.fromRgb(isDefault() ? 0xFFFFFF : 0xFFDA5E)));
        var value = EBEUtil.translate(this.getValueKey()).withStyle(style -> style.withColor(TextColor.fromRgb(this.palette.getColor((float)this.selected / this.values.size()))));
        *///?} else {
        var option = EBEUtil.translate(this.getOptionKey()).withStyle(style -> style.withColor(isDefault() ? 0xFFFFFF : 0xFFDA5E));
        var value = EBEUtil.translate(this.getValueKey()).withStyle(style -> style.withColor(this.palette.getColor((float)this.selected / this.values.size())));
        //?}

        if (text == null) text = option.append(EBEUtil.translate(DIVIDER).append(value));
        return text;
    }

    public Component getTooltipText() {
        if (tooltip == null) {
            if (override != null) {
                var text = EBEUtil.translate(OVERRIDDEN, override.modResponsible())
                        .withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE);
                if (override.reason() != null) {
                    text.append(NEWLINE).append(override.reason());
                }

                tooltip = text;
            }
            else if (hasValueComments) tooltip = EBEUtil.translate(String.format("option.ebe.%s.valueComment.%s", key, getValue())).append(NEWLINE).append(comment.plainCopy());
            else tooltip = comment.plainCopy();
        }
        return tooltip;
    }

    //? if >= 1.19.4 {
    public Tooltip getTooltip() {
        return Tooltip.create(getTooltipText());
    }
    //?}

    public void next() {
        selected++;
        if (selected >= values.size()) selected = 0;
        tooltip = null;
        text = null;
    }

    public boolean isDefault() {
        return selected == defaultValue;
    }

    public record ConfigView(Properties configValues, Map<String, EBEConfig.Override> overrides) {}
}

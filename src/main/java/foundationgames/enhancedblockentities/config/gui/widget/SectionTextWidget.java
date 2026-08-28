package foundationgames.enhancedblockentities.config.gui.widget;

import net.minecraft.client.gui.Font;
//? if <= 1.16 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
*///?} else if <= 1.19.2 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
*///?} else if <= 1.19.4 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractStringWidget;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
//?}
import net.minecraft.network.chat.Component;

//? if <= 1.19.2 {
/*public class SectionTextWidget extends AbstractWidget {
    private final Font font;
*///?} else {
public class SectionTextWidget extends AbstractStringWidget {
//?}
    public SectionTextWidget(Component message, Font textRenderer) {
        this(0, 0, 200, 20, message, textRenderer);
    }

    public SectionTextWidget(int x, int y, int width, int height, Component message, Font textRenderer) {
        //? if <= 1.19.2 {
        /*super(x, y, width, height, message);
        this.font = textRenderer;
        *///?} else {
        super(x, y, width, height, message, textRenderer);
        //?}
        this.active = false;
    }

    //? if <= 1.19.2 {
    /*public Font getFont() {
        return this.font;
    }
    *///?}

    //? if >= 1.17 && <= 1.19.2 {
    /*@Override
    public void updateNarration(NarrationElementOutput output) {
    }
    *///?}

    @Override
    //? if <= 1.19.2 {
    /*public void renderButton(PoseStack context, int mouseX, int mouseY, float delta) {
    *///?} else if <= 1.19.4 {
    /*public void renderWidget(PoseStack context, int mouseX, int mouseY, float delta) {
    *///?} else {
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
    //?}
        final int white = 0xFFFFFFFF;
        var font = this.getFont();
        var msg = this.getMessage();

        //? if <= 1.19.2 {
        /*int l = this.x;
        int top = this.y;
        *///?} else {
        int l = this.getX();
        int top = this.getY();
        //?}
        int w = this.getWidth();
        int r = l + w;
        int y = (top + this.getHeight()) - 6;

        int tx = l + (w / 2);
        int ty = y - (font.lineHeight / 2);
        int tw = font.width(msg);

        int ml = l + ((w - tw) / 2) - 5;
        int mr = ml + tw + 10;

        l += 1;
        r -= 1;

        //? if <= 1.19.4 {
        /*fill(context, l, y, ml, y + 2, white);
        fill(context, mr, y, r, y + 2, white);

        drawCenteredString(context, font, msg, tx, ty, 0xFFFFFFFF);
        *///?} else {
        context.fill(l, y, ml, y + 2, white);
        context.fill(mr, y, r, y + 2, white);

        context.drawCenteredString(font, msg, tx, ty, 0xFFFFFFFF);
        //?}
    }
}

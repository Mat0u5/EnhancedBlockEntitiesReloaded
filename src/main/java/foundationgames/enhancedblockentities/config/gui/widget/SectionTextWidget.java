package foundationgames.enhancedblockentities.config.gui.widget;

import net.minecraft.client.gui.Font;
//? if <= 1.19.4 {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.network.chat.Component;

public class SectionTextWidget extends AbstractStringWidget {
    public SectionTextWidget(Component message, Font textRenderer) {
        this(0, 0, 200, 20, message, textRenderer);
    }

    public SectionTextWidget(int x, int y, int width, int height, Component message, Font textRenderer) {
        super(x, y, width, height, message, textRenderer);
        this.active = false;
    }

    @Override
    //? if <= 1.19.4 {
    /*public void renderWidget(PoseStack context, int mouseX, int mouseY, float delta) {
    *///?} else {
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
    //?}
        final int white = 0xFFFFFFFF;
        var font = this.getFont();
        var msg = this.getMessage();

        int l = this.getX();
        int w = this.getWidth();
        int r = l + w;
        int y = (this.getY() + this.getHeight()) - 6;

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

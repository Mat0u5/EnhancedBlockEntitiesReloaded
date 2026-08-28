package foundationgames.enhancedblockentities.config.gui.widget;

import net.minecraft.client.Minecraft;
//? if <= 1.19.4 {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
//? if >= 1.19.4 {
import net.minecraft.client.gui.layouts.GridLayout;
//?}
//? if >= 1.17 {
import net.minecraft.client.gui.narration.NarratableEntry;
//?}

import java.util.ArrayList;
import java.util.List;

public class WidgetRowListWidget extends ContainerObjectSelectionList<WidgetRowListWidget.Entry> {
    public static final int SPACING = 3;

    public final int rowWidth;
    public final int rowHeight;

    public WidgetRowListWidget(Minecraft mc, int w, int h, int y, int rowWidth, int rowHeight) {
        //? if <= 1.20 {
        /*super(mc, w, h, y, y + h, rowHeight + SPACING);
        *///?} else {
        super(mc, w, h, y, rowHeight + SPACING);
        //?}
        this.rowWidth = rowWidth;
        this.rowHeight = rowHeight;
    }

    public void add(AbstractWidget ... widgets) {
        if (widgets.length == 0) return;

        int width = (this.rowWidth - ((widgets.length - 1) * SPACING)) / widgets.length;

        //? if <= 1.19.2 {
        /*for (var widget : widgets) {
            widget.setWidth(width);
        }

        this.addEntry(new Entry(List.of(widgets)));
        *///?} else {
        var grid = new GridLayout();
        grid.columnSpacing(SPACING);
        var adder = grid.createRowHelper(widgets.length);

        for (var widget : widgets) {
            //? if <= 1.20 {
            /*widget.setWidth(width);
            *///?} else {
            widget.setSize(width, this.rowHeight);
            //?}
            adder.addChild(widget);
        }

        grid.arrangeElements();

        this.addEntry(new Entry(grid));
        //?}
    }

    @Override
    public int getRowWidth() {
        return rowWidth;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width - 6;
    }

    //? if >= 1.21 {
    @Override
    protected void renderListBackground(GuiGraphics context) {
    }
    //?}

    public static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
        //? if <= 1.19.2 {
        /*private final List<AbstractWidget> children;

        public Entry(List<AbstractWidget> widgets) {
            this.children = widgets;
        }
        *///?} else {
        private final GridLayout widget;
        private final List<AbstractWidget> children = new ArrayList<>();

        public Entry(GridLayout widget) {
            this.widget = widget;
            widget.visitWidgets(children::add);
        }
        //?}

        @Override
        public List<? extends GuiEventListener> children() {
            return this.children;
        }

        //? if >= 1.17 {
        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.children;
        }
        //?}

        @Override
        //? if <= 1.19.4 {
        /*public void render(PoseStack context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        *///?} else {
        public void render(GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        //?}
            //? if <= 1.19.2 {
            /*int left = x - 3;

            for (var child : this.children) {
                child.x = left;
                child.y = y;
                child.render(context, mouseX, mouseY, tickDelta);

                left += child.getWidth() + SPACING;
            }
            *///?} else {
            this.widget.setPosition(x - 3, y);
            this.widget.arrangeElements();

            this.widget.visitWidgets(c -> c.render(context, mouseX, mouseY, tickDelta));
            //?}
        }
    }
}

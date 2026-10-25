package com.github.alexthe666.alexsmobs.client.gui;

import com.github.alexthe666.alexsmobs.client.model.ModelLaviathan;
import com.github.alexthe666.alexsmobs.client.render.RenderLaviathan;
import com.github.alexthe666.alexsmobs.client.render.RenderMurmurBody;
import com.github.alexthe666.alexsmobs.client.render.RenderUnderminer;
import com.github.alexthe666.citadel.client.gui.GuiBasicBook;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;


@OnlyIn(Dist.CLIENT)
public class GUIAnimalDictionary extends GuiBasicBook {

    private static final Identifier ROOT = Identifier.parse("alexsmobs:book/animal_dictionary/root.json");

    public GUIAnimalDictionary(ItemStack bookStack) {
        super(bookStack, Component.translatable("animal_dictionary.title"));
    }

    public GUIAnimalDictionary(ItemStack bookStack, String page) {
        super(bookStack, Component.translatable("animal_dictionary.title"));
        this.currentPageJSON = this.getTextFileDirectory().withSuffix(page + ".json");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        RenderLaviathan.renderWithoutShaking = true;
        ModelLaviathan.renderStaticInBook = true;
        RenderMurmurBody.renderWithHead = true;
        RenderUnderminer.renderWithPickaxe = true;
        super.render(guiGraphics, x, y, partialTicks);
        RenderLaviathan.renderWithoutShaking = false;
        ModelLaviathan.renderStaticInBook = false;
        RenderMurmurBody.renderWithHead = false;
        RenderUnderminer.renderWithPickaxe = false;
    }

    @Override
    protected int getBindingColor() {
        return 0X606B26;
    }

    @Override
    public Identifier getRootPage() {
        return ROOT;
    }

    @Override
    public Identifier getTextFileDirectory() {
        return Identifier.parse("alexsmobs:book/animal_dictionary/");
    }
}

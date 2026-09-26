package com.coloryr.allmusic.client.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiGraphics.class)
public interface GuiRender {
    @Accessor("bufferSource")
    MultiBufferSource.BufferSource getBufferSource();
}
package com.coloryr.allmusic.client;

import com.coloryr.allmusic.client.core.AllMusicHud;
import com.coloryr.allmusic.client.core.Point2f;
import com.coloryr.allmusic.client.core.render.TextureRender;
import com.coloryr.allmusic.client.mixins.GuiRender;
import com.coloryr.allmusic.codec.HudPosType;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.joml.Matrix4f;

import java.io.IOException;

public class TexRender extends TextureRender {
    private final ResourceLocation location;

    public TexRender(String texture) {
        super(texture);
        location = ResourceLocation.fromNamespaceAndPath(AllMusicClient.MODID, texture);
        try {
            ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            var resource = resourceManager.getResource(location).orElseThrow();
            try (var input = resource.open()) {
                var nativeImage = NativeImage.read(input);
                width = nativeImage.getWidth();
                height = nativeImage.getHeight();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawPic(float x, float y, float alpha) {
        GuiGraphics gui = AllMusicClient.context;
        if (gui == null) return;
        GuiRender render = (GuiRender) gui;

        float w1 = (float) width / 2;
        float h1 = (float) height / 2;

        Matrix4f matrix = new Matrix4f().translation(x + w1, y + h1, 0);

        float x0 = -w1;
        float x1 = w1;
        float y0 = -h1;
        float y1 = h1;
        float z = 0;
        float u0 = 0;
        float u1 = 1;
        float v0 = 0;
        float v1 = 1;

        RenderType renderType = RenderType.guiTexturedOverlay(location);
        VertexConsumer bufferBuilder = render.getBufferSource().getBuffer(renderType);
        bufferBuilder.addVertex(matrix, x0, y1, z).setUv(u0, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y1, z).setUv(u1, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y0, z).setUv(u1, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x0, y0, z).setUv(u0, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
    }

    @Override
    public void drawPic(float x, float y, float width, float alpha) {
        GuiGraphics gui = AllMusicClient.context;
        if (gui == null) return;
        GuiRender render = (GuiRender) gui;

        float w1 = (float) (this.width / 2) * width;
        float h1 = (float) height / 2;

        Matrix4f matrix = new Matrix4f().translation(x + w1, y + h1, 0);

        float x0 = -w1;
        float x1 = w1;
        float y0 = -h1;
        float y1 = h1;
        float z = 0;
        float u0 = 0;
        float u1 = width;
        float v0 = 0;
        float v1 = 1;

        RenderType renderType = RenderType.guiTexturedOverlay(location);
        VertexConsumer bufferBuilder = render.getBufferSource().getBuffer(renderType);
        bufferBuilder.addVertex(matrix, x0, y1, z).setUv(u0, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y1, z).setUv(u1, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y0, z).setUv(u1, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x0, y0, z).setUv(u0, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
    }

    @Override
    public void drawPic(float x, float y, float width, float height, HudPosType dir, float alpha) {
        GuiGraphics gui = AllMusicClient.context;
        if (gui == null) return;
        GuiRender render = (GuiRender) gui;

        Point2f point = AllMusicHud.getPos(width, height, x, y, dir);

        float w1 = width / 2;
        float h1 = height / 2;

        Matrix4f matrix = new Matrix4f().translation(point.x + w1, point.y + h1, 0);

        float x0 = -w1;
        float x1 = w1;
        float y0 = -h1;
        float y1 = h1;
        float z = 0;
        float u0 = 0;
        float u1 = 1;
        float v0 = 0;
        float v1 = 1;

        RenderType renderType = RenderType.guiTexturedOverlay(location);
        VertexConsumer bufferBuilder = render.getBufferSource().getBuffer(renderType);
        bufferBuilder.addVertex(matrix, x0, y1, z).setUv(u0, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y1, z).setUv(u1, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y0, z).setUv(u1, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x0, y0, z).setUv(u0, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
    }
}
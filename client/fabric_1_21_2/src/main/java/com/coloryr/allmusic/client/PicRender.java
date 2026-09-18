package com.coloryr.allmusic.client;

import com.coloryr.allmusic.client.core.AllMusicHud;
import com.coloryr.allmusic.client.core.Point2f;
import com.coloryr.allmusic.client.core.render.PictureFrameBuffer;
import com.coloryr.allmusic.client.mixins.GuiRender;
import com.coloryr.allmusic.codec.HudPosType;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.io.ByteArrayInputStream;

public class PicRender extends PictureFrameBuffer {
    private static final ResourceLocation SOURCE_LOC = ResourceLocation.fromNamespaceAndPath("allmusic_client", "pic_source");
    private static final ResourceLocation ROTATE_LOC = ResourceLocation.fromNamespaceAndPath("allmusic_client", "pic_rotate");

    private final DynamicTexture sourceTexture;
    private final DynamicTexture rotateTexture;

    public PicRender(int size) {
        sourceTexture = new DynamicTexture(size, size, false);
        rotateTexture = new DynamicTexture(size, size, false);
        Minecraft.getInstance().getTextureManager().register(SOURCE_LOC, sourceTexture);
        Minecraft.getInstance().getTextureManager().register(ROTATE_LOC, rotateTexture);
    }

    @Override
    public void update(byte[] source, byte[] rotate) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(source);
            var image1 = NativeImage.read(stream);
            sourceTexture.setPixels(image1);
            sourceTexture.upload();

            stream = new ByteArrayInputStream(rotate);
            var image2 = NativeImage.read(stream);
            rotateTexture.setPixels(image2);
            rotateTexture.upload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(boolean rotate, int size, float x, float y, int ang, HudPosType dir, float alpha) {
        GuiGraphics gui = AllMusicClient.context;
        if (gui == null) return;
        GuiRender render = (GuiRender) gui;

        ResourceLocation loc = rotate ? ROTATE_LOC : SOURCE_LOC;

        Point2f point = AllMusicHud.getPos(size, size, x, y, dir);
        Matrix4f matrix;

        int a = size / 2;

        if (rotate) {
            matrix = new Matrix4f().translationRotate(point.x + a, point.y + a, 0,
                    new Quaternionf().fromAxisAngleDeg(0, 0, 1, ang));
        } else {
            matrix = new Matrix4f().translation(point.x + a, point.y + a, 0);
        }

        int x0 = -a;
        int x1 = a;
        int y0 = -a;
        int y1 = a;
        int z = 0;
        int u0 = 0;
        float u1 = 1;
        float v0 = 0;
        float v1 = 1;

        RenderType renderType = RenderType.guiTexturedOverlay(loc);
        VertexConsumer bufferBuilder = render.getBufferSource().getBuffer(renderType);
        bufferBuilder.addVertex(matrix, x0, y1, z).setUv(u0, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y1, z).setUv(u1, v1).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x1, y0, z).setUv(u1, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.addVertex(matrix, x0, y0, z).setUv(u0, v0).setColor(1.0f, 1.0f, 1.0f, alpha);
    }
}

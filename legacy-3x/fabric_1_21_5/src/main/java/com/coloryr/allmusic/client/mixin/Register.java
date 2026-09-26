package com.coloryr.allmusic.client.mixin;

import com.coloryr.allmusic.client.core.AllMusicCore;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 1.21.5 移除了 RenderSystem.recordRenderCall，无法在模组初始化时排队渲染任务，
 * 改为在 Minecraft 构造完成时初始化渲染资源。
 */
@Mixin(Minecraft.class)
public class Register {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void register(CallbackInfo info) {
        AllMusicCore.glInit();
    }
}
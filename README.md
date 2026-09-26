*本项目由AI生成
# Allmusic-Client-MoreVersion

AllMusic Client（Fabric 客户端模组）多版本适配工程：将官方 [AllMusic](https://github.com/Coloryr/AllMusic) 客户端模组适配到多个 Minecraft 版本，统一构建、统一产物格式。

## 支持的 Minecraft 版本

| 版本 | 构建模块 | 兼容层 |
| --- | --- | --- |
| 1.21.1 | `client:fabric_1_21_1` | legacy |
| 1.21.2 | `client:fabric_1_21_2` | legacy |
| 1.21.5 | `client:fabric_1_21_5` | legacy |
| 1.21.7 | `client:fabric_1_21_7` | compat_1_21_6 |
| 1.21.8 | `client:fabric_1_21_8` | compat_1_21_6 |
| 1.21.10 | `client:fabric_1_21_10` | compat_1_21_11 |
| 26.1.2 | `client:fabric_26_1_2` | modern |

## 构建

```bash
gradlew buildAll        # 一次性构建全部 6 个版本
gradlew :client:fabric_1_21_5:build    # 只构建指定版本
```

构建产物输出到根目录 `build/`，命名格式：

```
build/[fabric-<MC版本>]AllMusic_Client-4.2.2.jar
```

## 关键适配点

- **Loom 版本按世代动态选择**：1.21.x 混淆环境使用 `fabric-loom 1.17.20`；26.x 官方命名环境使用 `1.17-SNAPSHOT`
- **1.21.5**：官方移除 `RenderSystem.recordRenderCall`，改用 `Register` mixin（Minecraft 构造尾注入 `renderInit`）；`DynamicTexture` 构造器改为带名称的 4 参版本
- **1.21.10**：`Identifier` 改名 `ResourceLocation`；渲染管线改为 `addVertexWith2DPose(pose, x, y)` 双参、`buildVertices(VertexConsumer)` 单参；字体样式使用 `FontDescription.Resource`
- **26.1.2**：HUD 类名为 `Gui`（非 26.2 的 `Hud`），需修改 mixin 目标

## 项目结构

- `client/<模块>/` —— 各 Minecraft 版本的 Fabric 客户端模组（含 mixin、渲染、MiniMessage 等）
- `codec/` —— 通用编解码模块
- `buildSrc/` —— 构建脚本工具

## 元数据

- 模组 ID：`allmusic_client`
- 版本：4.2.2
- 许可证：GPL-3.0

## legacy-3x：AllMusic 客户端 3.x 老协议产线

`legacy-3x/` 是 AllMusic 客户端 **3.x（3.7.4，tag `3.7.4`）老协议** 产线的独立副本，与上面的 4.x 产线（`client/`）**并存、源码互不混合**。两者构建体系彼此独立：4.x 使用本仓库根的多项目 Gradle；3.x 沿用其自带的「每个模块一个独立 Gradle 工程 + 目录联接（junction）共享核心代码」方式，因此不作为根工程的子项目被 `include`。

- 覆盖 Minecraft 版本（Fabric，共 11 个，均为官方 3.7.4 未提供的版本）：
  `1.21.1 / 1.21.2 / 1.21.3 / 1.21.4 / 1.21.5 / 1.21.7 / 1.21.8 / 1.21.9 / 1.21.10 / 26.1.2 / 26.2`
- 官方 3.7.4 已支持的版本（`1.16.5 / 1.20.1 / 1.21 / 1.21.6 / 1.21.11 / 26.1`）**不在本产线内**，这些版本请直接使用上游 [AllMusic_Client](https://github.com/Coloryr/AllMusic_Client) 的产物
- 模块目录：`legacy-3x/fabric_<版本>/`
- 共享核心代码：`legacy-3x/core`、`legacy-3x/codec`、`legacy-3x/buffercodec`、`legacy-3x/mp3`，由 `legacy-3x/link.cmd` 以 junction 链接进各模块的源码/资源目录（这些链接目录已被 `.gitignore` 忽略）
- 与 4.x 产线的区别：3.x 使用老协议（`com.coloryr.allmusic.client.core` 的 `Object` 材质桥、`MiniMessage` 等），4.x 是重写后的新协议；两者互不引用来回。

### 构建

1. 首次或清理后先创建目录联接：运行 `legacy-3x/link.cmd`（若在非 cmd 环境，可用等价脚本创建 junction）
2. 逐模块构建（每个模块是独立 Gradle 工程，自带 wrapper）：
   - 1.21.x 世代：**JDK 21**，Loom `1.17.20`，`fabric-loom`（官方映射 + remap）
   - 26.x 世代：**JDK 25**，Loom `1.17-SNAPSHOT`，`net.fabricmc.fabric-loom`（官方命名环境，无需映射）
   - 例：`cd legacy-3x/fabric_26_2 ; ./gradlew build`
   - 或使用 `legacy-3x/build.cmd`（交互式菜单）
3. 产物统一输出到 `legacy-3x/build/libs/`（各模块的 `build/libs` 是指向该目录的 junction），命名 `[fabric-<MC版本>]AllMusic_Client-<版本>.jar`
4. 本地汇总产物在 `legacy-3x/releases/`（构建输出，不入库）；对外分发使用本仓库 Release 中本产线补充的 11 个版本

### 世代与适配要点

| 世代 | 版本 | 关键差异 |
| --- | --- | --- |
| A | 1.21、1.21.1 | `RenderSystem` + `Tesselator` 立即模式渲染 |
| A' | 1.21.2、1.21.3、1.21.4 | 改用 `CoreShaders.POSITION_TEX`；`Player.sendSystemMessage` 改 `displayClientMessage` |
| A'' | 1.21.5 | 移除 `recordRenderCall`/旧 `setShaderTexture`；改用 `GpuTexture`(`GpuDevice.createTexture` 5 参) + `RenderType.guiTexturedOverlay` + `GuiRender` 访问器 mixin，并新增 `Register` mixin 做渲染初始化 |
| B | 1.21.6、1.21.7、1.21.8 | `GpuTexture` + `RenderPipelines.GUI_TEXTURED` + `Matrix3x2fStack` |
| C | 1.21.9、1.21.10 | 仍名为 `ResourceLocation`（`Identifier` 自 1.21.11 起） |
| C' | 1.21.11 | `Identifier` 命名 |
| D | 26.1、26.1.2、26.2 | 官方命名环境（无映射）、Java 25、`GuiGraphicsExtractor`；`GuiShow` 注入 `extractRenderState`，26.2 的 HUD 类为 `Hud`；26.2 纹理格式改用 `com.mojang.blaze3d.GpuFormat.RGBA8_UNORM` |

> 注：上表中 `1.21`、`1.21.6`、`1.21.11`、`26.1` 对应的官方模块已按「只保留官方 3.7.4 未提供的版本」从本仓库移除，相关条目仅作世代差异的对照参考。

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

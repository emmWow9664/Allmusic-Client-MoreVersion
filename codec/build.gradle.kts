// 编解码模块：源码直接复用 AllMusic-main 的 codec 模块（相对本子项目目录上两级）
sourceSets {
    main {
        java.srcDir(file("../../AllMusic-main/codec/src/main/java"))
        resources.srcDir(file("../../AllMusic-main/codec/src/main/resources"))
    }
}
sourceSets {
    main {
        // 共享客户端核心：源码直接复用 AllMusic-main 的 client 共享模块（相对本子项目目录上两级）
        java.srcDir(file("../../AllMusic-main/client/src/main/java"))
        resources.srcDir(file("../../AllMusic-main/client/src/main/resources"))
    }
}

subprojects {
    val shadowImplementation by configurations.getting

    dependencies {
        shadowImplementation(project(":client"))
        shadowImplementation(project(":codec"))
    }

    tasks {
        shadowJar {
            configurations = listOf(project.configurations.shadow.get())
        }
    }
}

tasks {
    clean {
        delete("$projectDir/../build")
    }
}

dependencies {
    shadowImplementation(project(":codec"))

    shadowImplementation("org.apache.httpcomponents.client5:httpclient5:${Versions.httpclient5}")
    shadowImplementation("org.apache.httpcomponents.core5:httpcore5:${Versions.httpcore5}")
    shadowImplementation("org.apache.httpcomponents.core5:httpcore5-h2:${Versions.httpcore5_h2}")

    compileOnly("com.google.code.gson:gson:${Versions.gson}")
    compileOnly("org.lwjgl.lwjgl:lwjgl:2.9.3")
    compileOnly("org.apache.logging.log4j:log4j-core:2.25.4")
}

tasks.register("buildClient") {
    group = "build"
    dependsOn(rootProject.subprojects.filter { it.path.startsWith(":client:") }
        .map { it.tasks.named("build") })
}
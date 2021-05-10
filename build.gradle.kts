import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("multiplatform") version "1.5.0"
    application
    id("net.saliman.properties") version "1.5.1"
}

repositories {
    jcenter()
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://carrat.jfrog.io/artifactory/carrat-dev")
    if(project.properties["useMavenLocal"] == "true") {
        mavenLocal()
    }
}

val kotlinWrappersVersion = "pre.126-kotlin-1.4.10"
val carratVersion: String by project

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
        withJava()
    }
    js(LEGACY) {
        binaries.executable()
        useCommonJs()
        browser {
            dceTask {
                keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
            }
            commonWebpackConfig {
                cssSupport.enabled = true
            }
            webpackTask {
                sourceMaps = true
                destinationDirectory = project.buildDir.resolve("distributions/js")
                outputFileName = "js.js"
            }
            runTask {
                devServer = org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer(
                    port = 3000,
                    contentBase = listOf(
                        project.projectDir.absolutePath + "/src/jsTest/resources",
                        project.buildDir.absolutePath + "/processedResources/js/main"
                    ),
                    overlay = true,
                    open = false
                )
                outputFileName = "js.js"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.carrat:carrat-web-builder-document:$carratVersion")
                implementation("org.carrat:carrat-web-webapi:0.0alpha0.0preview0")
                implementation("org.carrat:carrat-flow:0.0alpha0.0preview0")
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
                implementation("org.jetbrains:kotlin-css:1.0.0-$kotlinWrappersVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:1.4.0")
                implementation("io.ktor:ktor-html-builder:1.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.1")
                implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        all {
            languageSettings.languageVersion = "1.5"
            languageSettings.useExperimentalAnnotation("org.carrat.experimental.CarratExperimental")
            languageSettings.useExperimentalAnnotation("org.carrat.experimental.ExperimentalMultipleReceivers")
        }
    }
}

application {
    mainClass.set("org.carrat.demo.tictactoe.MainKt")
}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "js.js"
}

tasks.getByName<KotlinWebpack>("jsBrowserDevelopmentWebpack") {
    destinationDirectory = project.buildDir.resolve("dev/static")
    sourceMaps = true
    outputFileName = "js.js"
}

tasks.getByName<Jar>("jvmJar") {
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
    into("static") {
        from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
        from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName + ".map"))
    }
}

tasks.create<JavaExec>("runDev") {
    mainClass.set("org.carrat.demo.tictactoe.MainKt")
    dependsOn(tasks.getByName<KotlinWebpack>("jsBrowserDevelopmentWebpack"))
    classpath(sourceSets["main"].runtimeClasspath, project.buildDir.resolve("dev"))
}

tasks.create<JavaExec>("writeStatic") {
    group = "static"
    mainClass.set("org.carrat.demo.tictactoe.static.WriteStaticKt")
    args = listOf(project.buildDir.resolve("processedResources/static/index.html").absolutePath)
    classpath(sourceSets["main"].runtimeClasspath)
    dependsOn(tasks.getByName("jvmMainClasses"))
}

fun AbstractCopyTask.buildStatic() {
    from(project.buildDir.resolve("distributions/static")){
        into("static")
    }
    from(project.buildDir.resolve("processedResources/static"))
    from(project.buildDir.resolve("distributions")){
        include("js.js")
        into("static")
    }
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
    dependsOn(tasks.getByName("writeStatic"))
}

tasks.create<Copy>("staticDir") {
    group = "static"
    destinationDir = project.buildDir.resolve("static")
    buildStatic()
}

tasks.create<Zip>("staticZip") {
    group = "static"
    archiveClassifier.set("static")
    buildStatic()
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}

dependencies {
    platform("org.carrat:carrat-bom:$carratVersion")
    platform("org.apache.logging.log4j:log4j-bom:2.14.1")
}

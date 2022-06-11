plugins {
	`maven-publish`
	alias(libs.plugins.quilt.loom)
}

//archivesBaseName = project.archives_base_name
version = "0.0.0-beta+1.18.2"
group = "gay.pyrrha"
base.archivesName.set("Qord")

repositories {
	maven("https://maven.jaackson.me/repo/")
}

// All the dependencies are declared at gradle/libs.version.toml and referenced with "libs.<id>"
// See https://docs.gradle.org/current/userguide/platforms.html for information on how version catalogs work.
dependencies {
	minecraft(libs.minecraft)
	mappings(loom.layered {
		addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${libs.versions.quilt.mappings.get()}:v2"))
		// officialMojangMappings() // Uncomment if you want to use Mojang mappings as your primary mappings, falling back on QM for parameters and Javadocs
	})
	modImplementation(libs.quilt.loader)

    include(modImplementation("com.jagrosh:DiscordIPC:0.5") {})

	// QSL is not a complete API; You will need Quilted Fabric API to fill in the gaps.
	// Quilted Fabric API will automatically pull in the correct QSL version.
	modImplementation(libs.quilted.fabric.api)
}

tasks.processResources {
	inputs.property("version", version)

	filesMatching("quilt.mod.json") {
		expand ("version" to version)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8"
	// Minecraft 1.18 (1.18-pre2) upwards uses Java 17.
	options.release.set(17)
}

java {
	// Still required by IDEs such as Eclipse and Visual Studio Code
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17

	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	// If this mod is going to be a library, then it should also generate Javadocs in order to aid with developement.
	// Uncomment this line to generate them.
	// withJavadocJar()
}

// If you plan to use a different file for the license, don't forget to change the file name here!
tasks.jar {
	from("LICENSE") {
		rename { "${it}_${base.archivesName}" }
	}
}

// Configure the maven publication
publishing {
	publications {
        create("mavenJava", MavenPublication::class.java) {
            from(components["java"])
        }
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}

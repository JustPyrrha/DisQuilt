package config;

import org.quiltmc.loader.api.QuiltLoader;

import java.io.*;

public class ConfigManager {

	public static ConfigManager instance = new ConfigManager();

	private final File configDir = new File(QuiltLoader.getConfigDir().toFile(), "Qord");
	private final File configFile = new File(configDir, "Qord.json5");

	public void init() {
		if(!configDir.exists()) configDir.mkdirs();

		if(configFile.exists()) {
			//load
		} else {
			try(var file = new FileOutputStream(configFile); var defaultConfig = ConfigManager.class.getResourceAsStream("/assets/qord/config/Qord.json5")) {
				file.write(defaultConfig.readAllBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

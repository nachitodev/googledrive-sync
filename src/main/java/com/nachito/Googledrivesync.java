package com.nachito;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import java.nio.file.*;

public class Googledrivesync implements ModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Googledrivesync.class);
	public static ModConfig config;
	@Override
	public void onInitialize() {
		AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);

		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		sync();
	}

	public static void sync() {
		if (config == null) {
			AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
			config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		}
		Path gameDir = FabricLoader.getInstance().getGameDir();

		Path schematicsDir = gameDir.resolve("schematics");

		Path targetDir = Paths.get(config.targetDir);

		try {
			if (Files.exists(schematicsDir)) {
				Files.delete(schematicsDir);
			}

			Files.createSymbolicLink(schematicsDir, targetDir);

			LOGGER.info("Enlace simbólico creado desde {} a {}", schematicsDir, targetDir);
		} catch (UnsupportedOperationException e) {
			LOGGER.error("El sistema operativo no soporta enlaces simbólicos.", e);
		} catch (FileAlreadyExistsException e) {
			LOGGER.error("El archivo o directorio ya existe.", e);
		} catch (java.io.IOException e) {
			LOGGER.error("Error al crear el enlace simbólico: {}", e.getMessage(), e);
		} catch (SecurityException e) {
			LOGGER.error("Permisos insuficientes para crear el enlace simbólico.", e);
		}
	}
	public static void onConfigChanged() {
		LOGGER.info("Configuración guardada. Ejecutando cambios.");
		sync();
	}
}

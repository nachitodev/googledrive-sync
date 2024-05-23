package com.nachito;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import com.nachito.Googledrivesync;

@Config(name = "Googledrivesync")
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public String targetDir = "G:\\Mi unidad\\Schematics";

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }
}

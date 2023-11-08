package me.nuclearteam.nuclearmotd;

import me.nuclearteam.nuclearmotd.Commands.addmsg;
import me.nuclearteam.nuclearmotd.Commands.setmotd;
import me.nuclearteam.nuclearmotd.Listeners.MOTD;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class NuclearMOTD extends Plugin {
    private static NuclearMOTD instance;

    private Configuration config;
    private FormattingParser formattingParser;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        File dataFolder = getDataFolder();
        formattingParser = new FormattingParser();

        // Create the data folder if it doesn't exist
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File configFile = new File(dataFolder, "config.yml");
        if (!configFile.exists()) {
            // Copy the default configuration from resources if it doesn't exist
            try (InputStream defaultConfig = getResourceAsStream("config.yml")) {
                Files.copy(defaultConfig, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config = YamlConfiguration.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getProxy().getPluginManager().registerCommand(this, new setmotd("setmotd", config));
        getProxy().getPluginManager().registerCommand(this, new addmsg("addmsg", config));
        getProxy().getPluginManager().registerListener(this, new MOTD(config, formattingParser));
    }

}

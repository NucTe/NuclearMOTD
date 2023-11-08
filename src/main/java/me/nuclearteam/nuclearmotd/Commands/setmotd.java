package me.nuclearteam.nuclearmotd.Commands;

import me.nuclearteam.nuclearmotd.NuclearMOTD;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class setmotd extends Command {
    private Configuration config;

    public setmotd(String name, Configuration config) {
        super(name);
        this.config = config;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (!player.hasPermission("nuclearteam.nuclearmotd.command.setmotd")) {
                player.sendMessage("You do not have the permission to set the MOTD.");
            } else {
                // Player has the permission, allow them to execute the command
                // Your command logic goes here

                // Example: Set the first line from the configuration
                if (args.length >= 1) {
                    String newFirstLine = String.join(" ", args);
                    config.set("motd.line1", newFirstLine);
                    File dataFolder = NuclearMOTD.getInstance().getDataFolder();
                    // Save the configuration
                    try {
                        YamlConfiguration.getProvider(YamlConfiguration.class).save(config, new File(dataFolder, "config.yml"));
                        player.sendMessage("MOTD first line updated!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        player.sendMessage("An error occurred while updating the MOTD.");
                    }
                } else {
                    player.sendMessage("Usage: /setmotd <new first line>");
                }
            }
        }
    }
}

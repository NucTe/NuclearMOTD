package me.nuclearteam.nuclearmotd.Commands;

import me.nuclearteam.nuclearmotd.NuclearMOTD;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class addmsg extends Command {
    private Configuration config;

    public addmsg(String name, Configuration config) {
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
                if (args.length < 1) {
                    sender.sendMessage("Usage: /addmsg <message>");
                    return;
                }
                File dataFolder = NuclearMOTD.getInstance().getDataFolder();

                String newMessage = String.join(" ", args);

                // Get the list of messages from the configuration
                List<String> messages = config.getStringList("messages");

                // Add the new message to the list
                messages.add(newMessage);

                // Update the configuration with the modified list
                config.set("messages", messages);

                // Save the configuration to the file
                File configFile = new File(dataFolder, "config.yml");
                try {
                    YamlConfiguration.getProvider(YamlConfiguration.class).save(config, configFile);
                    sender.sendMessage("Message added: " + newMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                    sender.sendMessage("An error occurred while adding the message.");
                }
            }
        }
    }

}

package me.nuclearteam.nuclearmotd.Listeners;

import me.nuclearteam.nuclearmotd.FormattingParser;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.Random;

public class MOTD implements Listener {
    private Configuration config;
    private FormattingParser formattingParser;
    public MOTD(Configuration config, FormattingParser formattingParser) {
        this.config = config;
        this.formattingParser = formattingParser;
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players players = ping.getPlayers();
        ServerPing.Protocol version = ping.getVersion();

        // Get the first line of the MOTD from the configuration
        String firstLine = config.getString("motd.line1", "You can change the MOTD in the config.yml");
        String firstlinep = formattingParser.parse(firstLine);

        // Get the list of questions from the configuration
        List<String> questions = config.getStringList("messages");

        // Choose a random question
        String randomQuestion = getRandomMsg(questions);
        String randomQuestionp = formattingParser.parse("&r" + randomQuestion);

        // Set the MOTD in the response
        ping.setDescription(firstlinep + "\n" + randomQuestionp);

        e.setResponse(ping);
    }

    private String getRandomMsg(List<String> questions) {
        if (questions.isEmpty()) {
            return "No questions available.";
        }

        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }
}

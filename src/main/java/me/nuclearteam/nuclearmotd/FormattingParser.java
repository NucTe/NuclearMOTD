package me.nuclearteam.nuclearmotd;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormattingParser {
    public String parse(String text) {
        // Compile the pattern for color and formatting codes
        Pattern pattern = Pattern.compile("&([0-9a-fk-or])(.*?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        // Replace color and formatting codes
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String code = matcher.group(1);
            String content = matcher.group(2);

            // Translate color codes and formatting codes
            String formattedContent = ChatColor.translateAlternateColorCodes('&', "&" + code + content);
            matcher.appendReplacement(result, Matcher.quoteReplacement(formattedContent));
        }
        matcher.appendTail(result);

        return result.toString();
    }
}

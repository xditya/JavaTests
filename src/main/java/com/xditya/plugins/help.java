package com.xditya.plugins;

import com.xditya.helpers.Config;
import com.xditya.pluginHandler;
import com.xditya.bot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class help extends bot implements pluginHandler {
    public void commandInvoked(Update update, String command, String args) {
        String chatID = update.getMessage().getChatId().toString();
        String help_menu = "*Help Menu*\n\n" + Config.handler + "help - Show this menu.\n" + Config.handler
                + "start - Start the bot\n" + Config.handler + "lyrics - Fetch song lyrics.\n";
        if (command.equals("help") || command.equalsIgnoreCase("help@" + getBotUsername()))
            sendMessage(chatID, help_menu).getMessageId();
    }
}

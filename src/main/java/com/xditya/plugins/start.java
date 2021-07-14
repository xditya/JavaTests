package com.xditya.plugins;

import com.xditya.pluginHandler;
import com.xditya.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public class start extends bot implements pluginHandler {
    public void commandInvoked(Update update, String command, String args) {
        if (command.equals("start") || command.equals("start" + "@" + getBotUsername()))
            sendmsg(update.getMessage().getChatId().toString(), "Hello. I'm a test bot. Please get lost.");
    }
}
package com.xditya.plugins;

import com.xditya.helpers.Config;
import com.xditya.pluginHandler;
import com.xditya.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public class help extends bot implements pluginHandler{
    public void commandInvoked(Update update, String command, String args) {
        if(command.equals("help") || command.equals("help@" + getBotUsername()))
            sendmsg(update.getMessage().getChatId().toString(), "<b>Help menu.</b>\n- <code>" + Config.handler + "start</code>", "html");
    }
}

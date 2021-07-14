package com.xditya.plugins;

import com.xditya.pluginHandler;
import com.xditya.bot;
import java.util.concurrent.TimeUnit;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ping extends bot implements pluginHandler {
    public void commandInvoked(Update update, String command, String args) {
        String chatID = update.getMessage().getChatId().toString();
        long start, end, responseTime;
        int id;

        if (command.equals("ping") || command.equalsIgnoreCase("ping@" + getBotUsername())) {
            start = System.nanoTime();
            id = sendMessage(chatID, "*Pong!* 🏓").getMessageId();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            end = System.nanoTime();
            responseTime = end - start;
            editMessage(chatID, id, "*Pong!* 🏓\n*Response Time:* `" + responseTime / 1000 + "ms`");
        }
    }
}
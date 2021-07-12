package com.xditya;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.xditya.Config;

public class pdiskbot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        String cmd = update.getMessage().getText();
        if (cmd.equals("/start")) {
            if (update.getMessage().getChat().isUserChat() == true)
                sendmsg(update.getMessage().getChatId().toString(), "Hi. I am a pdisk uploader bot. Send me a direct video link and I'll upload it to pdisk.\n\n@xditya.");
        }
    }

    public void sendmsg(String chatid, String text) {
        SendMessage msg = new SendMessage(chatid, text);
        msg.enableHtml(true);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        String username = Config.botUserName;
        return username;
    }

    @Override
    public String getBotToken() {
        String token = Config.botToken;
        return token;
    }
}
package com.xditya;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class bot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        String cmd, welcome_msg, res[], command, args;
        cmd = update.getMessage().getText();
        welcome_msg = "Hello. I'm a test bot. Please get lost.";
        res = getArgs(cmd);
        command = res[0];
        args = res[1];
        if (command.equalsIgnoreCase("/start") || command.equalsIgnoreCase("/start" + "@" + getBotUsername()))
            if (update.getMessage().getChat().isUserChat() == true)
    }

    // this func make its easier to send a message to the specified chat.
    public void sendmsg(String chatid, String text) {
        SendMessage msg = new SendMessage(chatid, text);
        msg.enableHtml(true);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // this splits the recieved input into a command and the arguments. 
    public String[] getArgs(String cmd) {
        String temp[] = cmd.split(" ");
        String command="", args="", results[] = new String[2];
        for (int i = 0; i < temp.length; i++) {
            if (i == 0)
                command = temp[i];
            else
                args += temp[i] + " ";
        }
        results[0] = command;
        results[1] = args;
        return results;
    }

    public String getBotUsername() {
        String username = "";
        try{
            User me = getMe();
            username = me.getUserName();
        }
        catch(TelegramApiException e){
            e.printStackTrace();
        }
        if(username != "")
            return username;
        else {
            System.out.println("Getting botUserName from env vars...");
            username = Config.botUserName;
            return username;
        }
    }

    @Override
    public String getBotToken() {
        String token = Config.botToken;
        return token;
    }
}
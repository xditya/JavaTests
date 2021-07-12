package com.xditya;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class bot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        String cmd, welcome_msg, res[], command, args, isCommand;
        cmd = update.getMessage().getText();
        welcome_msg = "Hello. I'm a test bot. Please get lost.";
        res = getArgs(cmd);
        isCommand = res[0];
        command = res[1];
        args = res[2];
        System.out.println("isCommand: " + isCommand + "\nCommand: " + command + "\nArgs: " + args);
        if(isCommand == "false")
            return;
        if (command.equalsIgnoreCase("start") || command.equalsIgnoreCase("start" + "@" + getBotUsername()))
            if (update.getMessage().getChat().isUserChat() == true)
                sendmsg(update.getMessage().getChatId().toString(), welcome_msg);
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
        String command="", args="", results[] = new String[3];
        if((cmd.charAt(0)) == ((Config.handler).charAt(0)))
            results[0] = "true";
        else
            results[0] = "false";
        for (int i = 0; i < temp.length; i++) {
            if (i == 0)
                command = temp[i];
            else
                args += temp[i] + " ";
        }
        if(results[0] == "true")
            results[1] = command.substring(1);
        else
        results[1] = command;
        results[2] = args;
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
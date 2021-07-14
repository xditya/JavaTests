package com.xditya;

import com.xditya.helpers.Config;
import com.xditya.plugins.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class bot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        String cmd, res[], command, args, isCommand;
        cmd = update.getMessage().getText();
        res = getArgs(cmd);
        isCommand = res[0]; // "true", if the update text starts with handler, or "false"
        command = res[1]; // the command used, excluding the hanlder, or None if isCommand is "false"
        args = res[2]; // the text after the command, or None if the command has no following text, or
                       // the whole update text, if isCommand is "false"
        if (isCommand == "false")
            return; // we handle only commands, as of now.
        else
            doPluginAction(update, command, args); // handle commands
    }

    public void doPluginAction(Update update, String command, String args) {
        new start().commandInvoked(update, command, args);
        new help().commandInvoked(update, command, args);
        new lyrics().commandInvoked(update, command, args);
        new ping().commandInvoked(update, command, args);
    }

    // this func make its easier to send a message to the specified chat (parses as
    // markdown).
    public Message sendMessage(String chatid, String text) {
        SendMessage msg = new SendMessage(chatid, text);
        msg.enableMarkdown(true);
        try {
            Message m = execute(msg);
            return m;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    // the same sendMessage function with a definable parse mode, as "html" or "md"
    public Message sendMessage(String chatid, String text, String parse_mode) {
        SendMessage msg = new SendMessage(chatid, text);
        if (parse_mode == "html")
            msg.enableHtml(true);
        else if (parse_mode == "md" || parse_mode == "markdown" || parse_mode == null)
            msg.enableMarkdown(true);
        try {
            Message m = execute(msg);
            return m;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    // edit a message.
    public void editMessage(String chatID, int messageID, String text) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatID);
        editMessageText.setMessageId(messageID);
        editMessageText.setText(text);
        editMessageText.enableMarkdown(true);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // this splits the recieved input into a command and the arguments.
    public String[] getArgs(String cmd) {
        String temp[] = cmd.split(" ");
        String command = "", args = "", results[] = new String[3];
        if ((cmd.charAt(0)) == ((Config.handler).charAt(0)))
            results[0] = "true";
        else
            results[0] = "false";
        for (int i = 0; i < temp.length; i++) {
            if (i == 0)
                command = temp[i];
            else
                args += temp[i] + " ";
        }
        if (results[0] == "true") {
            results[1] = command.substring(1);
            results[2] = args;
        } else {
            results[1] = "";
            results[2] = command + " " + args;
        }
        return results;
    }

    @Override
    public String getBotUsername() {
        String username = "";
        try {
            User me = getMe();
            username = me.getUserName();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        if (username != "")
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
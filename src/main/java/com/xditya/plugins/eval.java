package com.xditya.plugins;

import java.io.*;
import java.nio.file.*;

import com.xditya.helpers.Config;
import com.xditya.helpers.send;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.xditya.pluginHandler;
import com.xditya.bot;

public class eval extends bot implements pluginHandler {
    public void commandInvoked(Update update, String command, String args) {
        String chatID = update.getMessage().getChatId().toString();
        if (command.equals("eval") || command.equalsIgnoreCase("eval@" + getBotUsername())) {
            if (args.length() == 0) {
                sendMessage(chatID, "Please use " + Config.handler + "eval <a command>.");
                return;
            }
            boolean done = WriteToFile(args);
            if (done) {
                sendMessage(chatID, "Done. Wrote to file.");
                new send().file(update, "run.java", "Done. Written to file.");
                Path fileToDeletePath = Paths.get("run.java");
                try {
                    Files.delete(fileToDeletePath);
                    sendMessage(chatID, "Deleted File.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Boolean WriteToFile(String message) {
        try {
            File file = new File("run.java");
            if (file.createNewFile()) {
                FileWriter myWriter = new FileWriter("run.java");
                myWriter.write(message);
                myWriter.close();
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }
}
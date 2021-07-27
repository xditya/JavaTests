package com.xditya.plugins;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import com.xditya.helpers.Config;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.xditya.pluginHandler;
import com.xditya.bot;

public class eval extends bot implements pluginHandler {
    private static String defpath = "src/main/java/com/xditya/run.java";

    public void commandInvoked(Update update, String command, String args) {
        String chatID = update.getMessage().getChatId().toString();
        int msgID;
        if (command.equals("eval") || command.equalsIgnoreCase("eval@" + getBotUsername())) {
            if (args.length() == 0) {
                sendMessage(chatID, "Please use " + Config.handler + "eval <a command>.");
                return;
            }
            boolean done = WriteToFile(
                    "import org.telegram.telegrambots.bots.TelegramLongPollingBot;\nimport org.telegram.telegrambots.meta.api.objects.Update;\n\npublic class run extends TelegramLongPollingBot {\n    public void main(Update update) {\n   "
                            + args + "    }\n}");
            // "class run {\n public static void main(String args[]) {\n " + args + "\n
            // }\n}");
            if (done) {
                msgID = sendMessage(chatID, "Done. Wrote to file.").getMessageId();
                Process process;
                try {
                    process = Runtime.getRuntime().exec("java " + defpath);
                    InputStream stdout = process.getInputStream();
                    InputStream stderr = process.getErrorStream();
                    BufferedReader err = new BufferedReader(new InputStreamReader(stderr, StandardCharsets.UTF_8));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
                    String line, ln, output = "";
                    try {
                        output += "*OUTPUT:*\n";
                        while ((line = reader.readLine()) != null) {
                            output += "`" + line + "`\n";
                        }
                    } catch (IOException e) {
                        output += "*Exception in reading output:*\n`" + e.toString() + "`\n";
                    }
                    output += "\n";
                    try {
                        output += "*ERROR:*\n";
                        while ((ln = err.readLine()) != null) {
                            output += "`" + ln + "`\n";
                        }
                    } catch (IOException e) {
                        output += "*Exception in reading error output:*\n`" + e.toString() + "`\n";
                    }
                    editMessage(chatID, msgID, output);
                } catch (Exception e) {
                    System.out.println("Exception Raised: " + e.toString());
                }
            }

            Path fileToDeletePath = Paths.get(defpath);
            try {
                Files.delete(fileToDeletePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Boolean WriteToFile(String message) {
        try {
            File file = new File(defpath);
            if (file.createNewFile()) {
                FileWriter myWriter = new FileWriter(defpath);
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

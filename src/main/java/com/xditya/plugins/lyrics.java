package com.xditya.plugins;

import com.xditya.pluginHandler;
import com.xditya.helpers.Config;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.xditya.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public class lyrics extends bot implements pluginHandler {
    public void commandInvoked(Update update, String command, String args) {
        if (command.equals("lyrics") || command.equals("lyrics@" + getBotUsername())) {
            String chatID = update.getMessage().getChatId().toString(), results = "";
            if (args.length() == 0) {
                sendmsg(chatID, "Please use `" + Config.handler + "lyrics <song name>`");
                return;
            } else {
                String base_url = "http://jostapi.notavailable.live/lyrics/";
                try {
                    base_url += args.trim().replace(" ", "%20");
                    // System.out.println(base_url);
                    URL url = new URL(base_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int responsecode = conn.getResponseCode();
                    if (responsecode != 200) {
                        sendmsg(chatID, "API is down/unrechable at the moment!");
                        return;
                    } else {
                        Scanner sc = new Scanner(url.openStream());
                        while (sc.hasNext()) {
                            results += sc.nextLine();
                        }
                        sendmsg(chatID, "Results: \n `" + results + "`");
                        sc.close();
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
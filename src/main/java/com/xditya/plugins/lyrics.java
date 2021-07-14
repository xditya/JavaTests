package com.xditya.plugins;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;
import com.xditya.bot;
import com.xditya.pluginHandler;
import com.xditya.helpers.Config;

import org.telegram.telegrambots.meta.api.objects.Update;

public class lyrics extends bot implements pluginHandler {
    public void commandInvoked(Update update, String command, String args) {
        if (command.equals("lyrics") || command.equals("lyrics@" + getBotUsername())) {
            String chatID = update.getMessage().getChatId().toString(), results = "";
            if (args.length() == 0) {
                sendMessage(chatID, "Please use `" + Config.handler + "lyrics <song name>`");
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
                        sendMessage(chatID, "API is down/unrechable at the moment!");
                        return;
                    } else {
                        Scanner sc = new Scanner(url.openStream());
                        while (sc.hasNext()) {
                            results += sc.nextLine();
                        }
                        sc.close();
                        JSONArray array = new JSONArray("[" + results + "]");
                        /*
                         * for (int i = 0; i < array.length(); i++) { we do not iter here, because the
                         * api returns just one match, and not a list.
                         */
                        JSONObject object = array.getJSONObject(0);
                        sendMessage(chatID, object.getString("lyrics").replace("EmbedShare URLCopyEmbedCopy", ""));
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
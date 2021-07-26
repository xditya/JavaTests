package com.xditya.helpers;

import org.telegram.telegrambots.meta.api.objects.Update;

public class userInfo {
    public static String userID(Update update) {
        return update.getMessage().getFrom().getId().toString();
    }

    public static String firstName(Update update) {
        return update.getMessage().getFrom().getFirstName().toString();
    }

    public static String userName(Update update) {
        return update.getMessage().getFrom().getUserName().toString();
    }
}
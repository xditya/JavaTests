package com.xditya;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface pluginHandler{
    public abstract void commandInvoked(Update update, String command, String args);
}
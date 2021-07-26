package com.xditya.helpers;

import java.io.File;

import com.xditya.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class send extends bot {
    public void file(Update update, String location, String caption) {
        InputFile file = new InputFile(new File(location));
        SendDocument sent_ = new SendDocument(update.getMessage().getChatId().toString(), file);
        sent_.setCaption(caption);

        try {
            execute(sent_);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
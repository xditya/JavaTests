package com.xditya;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

public class Config {

    private static final Dotenv dotenv = new DotenvBuilder().ignoreIfMissing().load();

    public static String botToken = dotenv.get("botToken");
    public static String botUserName = dotenv.get("botUserName");

}
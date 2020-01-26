package com.hackerman.plugin.configManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configManager
{
    private static Properties configProperties = new Properties ();

    public static void loadConfig(String configId, Boolean force, Boolean critical)
    {
        try
        {
            String current = new File (".").getCanonicalPath();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream (current + "/plugins/BetterAbout/" + configId + ".properties"));
            configProperties.load(stream);
            stream.close();
        }
        catch (Exception e)
        {
            if (critical) {
                System.out.println("[!] [CONFIG....] [BETTER ABOUT] Failed to load configuration file `" + configId + ".properties`. This configuration file is critical.");
            } else {
                System.out.println("[!] [CONFIG....] [BETTER ABOUT] Failed to load configuration file `" + configId + ".properties`.");
            }
        }
    }

    public static String getConfigValue(String module, String key, String def)
            throws IOException
    {
        loadConfig(module, Boolean.valueOf(false), Boolean.valueOf(false));
        if (!configProperties.containsKey(key)) {
            System.out.println("[!] [CONFIG....] [BETTER ABOUT] Error in file `" + module + ".properties`. Key `" + key + "` doesn't exist.");
        }
        return configProperties.getProperty(key, def);
    }
}


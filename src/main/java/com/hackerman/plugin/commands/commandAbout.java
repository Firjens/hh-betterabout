package com.hackerman.plugin.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.habboway.nux.NuxAlertComposer;
import com.hackerman.plugin.configManager.configManager;
import gnu.trove.map.hash.THashMap;

import java.util.concurrent.TimeUnit;

public class commandAbout
        extends Command
{
    public commandAbout()
    {
        super(null, new String[] { "about", "info", "online", "server", "krews" });
    }

    public boolean handle(GameClient gameClient, String[] params)
            throws Exception
    {
        if (configManager.getConfigValue("config", "use_habbopage", "false").equalsIgnoreCase ( "true" )) {
            gameClient.sendResponse(new NuxAlertComposer ("habbopages/betterabout.txt"));
        } else {
            Emulator.getRuntime ( ).gc ( );

            int seconds = Emulator.getIntUnixTimestamp ( ) - Emulator.getTimeStarted ( );
            int day = (int) TimeUnit.SECONDS.toDays ( seconds );
            long hours = TimeUnit.SECONDS.toHours ( seconds ) - day * 24;
            long minute = TimeUnit.SECONDS.toMinutes ( seconds ) - TimeUnit.SECONDS.toHours ( seconds ) * 60L;
            long second = TimeUnit.SECONDS.toSeconds ( seconds ) - TimeUnit.SECONDS.toMinutes ( seconds ) * 60L;

            THashMap <String, String> codes;

            codes = new THashMap ( );
            codes.put ( "image" , "${image.library.url}/better-about/logo.png" );
            codes.put ( "version" , Emulator.version );
            codes.put ( "runtime" , day + (day > 1 ? " days, " : " day, ") + hours + (hours > 1L ? " hours, " : " hour, ") + minute + (minute > 1L ? " minutes, " : " minute, ") + second + (second > 1L ? " seconds!" : " second!") );
            codes.put ( "online" , String.valueOf ( Emulator.getGameEnvironment ( ).getHabboManager ( ).getOnlineCount ( ) ) );
            codes.put ( "rooms" , String.valueOf ( Emulator.getGameEnvironment ( ).getRoomManager ( ).getActiveRooms ( ).size ( ) ) );
            codes.put ( "ram" , "RAM Usage: " + (Emulator.getRuntime ( ).totalMemory ( ) - Emulator.getRuntime ( ).freeMemory ( )) / 1048576L + "/" + Emulator.getRuntime ( ).freeMemory ( ) / 1048576L + "MB" );
            ServerMessage msg = new BubbleAlertComposer ( "about" , codes ).compose ( );
            gameClient.getHabbo ( ).getClient ( ).sendResponse ( msg );
        }
        return true;
    }
}

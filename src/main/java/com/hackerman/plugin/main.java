package com.hackerman.plugin;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.hackerman.plugin.commands.*;
import com.hackerman.plugin.updater.checkUpdate;

import java.io.IOException;

import static com.hackerman.plugin.updater.checkUpdate.getLatestVersion;

public class main extends HabboPlugin implements EventListener {
    public void onEnable () throws Exception {
        Emulator.getPluginManager().registerEvents(this, this);
        System.out.println ( "[~] Detected plugin `Better About`");
    }

    @EventHandler
    public void onEmulatorLoadedEVERYTHING ( EmulatorLoadedEvent e ) throws IOException {
        Emulator.getPluginManager().registerEvents(this, this );

        long startTime = System.currentTimeMillis ();
        System.out.println ( "\n[~] Loading plugin `Better About command` by Hackerman (Version " + checkUpdate.version + ")" );
        getLatestVersion();
        CommandHandler.addCommand ( new commandAbout() );
        System.out.println ( "[~] Loaded `Better About command` in " + (System.currentTimeMillis () - startTime) + "ms. Request support @ Hackerman.tech\n" );
    }

    public void onDisable () throws Exception {

    }

    public boolean hasPermission ( Habbo habbo , String s ) {
        return false;
    }
}

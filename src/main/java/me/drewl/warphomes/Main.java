package me.drewl.warphomes;

import me.drewl.warphomes.Commands.*;
import me.drewl.warphomes.Events.PlayerListeners;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public final class Main extends JavaPlugin {
    FileConfiguration config = this.getConfig();

    public Main() {
    }
    @Override
    public void onEnable() {
        this.loadConfig();
        this.config = this.getConfig();
        registerCommands();
        getServer().getPluginManager().registerEvents(new PlayerListeners(),this);
        getServer().getConsoleSender().sendMessage("[WarpHomes] Starting WarpHomes...");
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("Stopping WarpHomes...");
        getServer().getConsoleSender().sendMessage("Plugin stopped.");
    }
    public void registerCommands() {
        getServer().getPluginCommand("delhome").setExecutor(new delhome());
        getServer().getPluginCommand("home").setExecutor(new home());
        getServer().getPluginCommand("homes").setExecutor(new homes());
        getServer().getPluginCommand("sethome").setExecutor(new sethome());
    }
    public void loadConfig() {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
}
package me.drewl.warphomes.Commands;

import me.drewl.warphomes.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;


public class sethome implements CommandExecutor {
    Main main = Main.getPlugin(Main.class);
    FileConfiguration config = main.getConfig();
    File file = new File(main.getDataFolder(), "config.yml");

    public boolean isValidColor(String color) {
        String[] validColors = {"white", "orange", "magenta", "lightblue", "yellow", "lime","pink", "gray", "lightgray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
        for (String validColor : validColors) {
            if (color.equalsIgnoreCase(validColor)) {
                return true;
            }
        }
        return false;
    }

    public void setHome(Player player, String homeName, String bedColor) {
        boolean setHomeBool;
        int defaultAllowedHomes = config.getInt("default-Allowed-Homes");
        int homes = 0;
        try {
            homes = this.config.getConfigurationSection("players.homes." + player.getUniqueId() + ".").getKeys(false).size();
        } catch (Exception ignored) {}
        int allowedHomes = 0;
        int h;
        for (h = 54; h > 0; h--) {
            if (player.hasPermission("sethome." + h)) {
                allowedHomes = h;
                break;
            }
        }
        if (allowedHomes == 0) {
            allowedHomes = defaultAllowedHomes;
        }

        if (homes >= allowedHomes) {
            setHomeBool = false;
        } else {
            setHomeBool = true;
        }


        if (setHomeBool) {
            Location location = player.getLocation();
            int locx = (int) Math.floor(location.getX());
            int locy = (int) Math.floor(location.getY());
            int locz = (int) Math.floor(location.getZ());

            this.config.set("players.homes." + player.getUniqueId() + "." + homeName + ".x", locx);
            this.config.set("players.homes." + player.getUniqueId() + "." + homeName + ".y", locy);
            this.config.set("players.homes." + player.getUniqueId() + "." + homeName + ".z", locz);
            this.config.set("players.homes." + player.getUniqueId()+ "." + homeName + ".world", location.getWorld().getName());
            this.config.set("players.homes." + player.getUniqueId() + "." + homeName + ".color", bedColor);
            try {
                this.config.save(this.file);
                player.sendMessage(ChatColor.GREEN + "Successfully set home " + ChatColor.GOLD + homeName + ChatColor.GREEN + ".");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "There was an error setting your home.");
              }
        } else {
            if (h == 0) {
                player.sendMessage(ChatColor.RED + "You can only set " + defaultAllowedHomes + " home(s)!");
            } else {
                player.sendMessage(ChatColor.RED + "You can only set " + h + " home(s)!");
            }
        }

    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        String bedColor;
        try {
            bedColor = args[1];
        } catch (Exception ignored) {}

        if (args.length == 0 || args == null) {
            p.sendMessage(ChatColor.RED + "You must provide a name for your home!");
        } else {
            String homeName = "home";
            try {
                homeName = args[0];
            } catch (Exception ex) {
                main.getLogger().severe("problem setting homename");
            }
            if (args.length == 1) {
                bedColor = "red";
            } else {
                bedColor = args[1];
            }
            if (!this.config.contains("players.homes." + p.getUniqueId() + "." + homeName + ".")) {
                if (isValidColor(bedColor)) {
                    this.setHome(p, homeName, bedColor);
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid color entered!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "That home already exists!");
            }
        }
        return false;
    }
}
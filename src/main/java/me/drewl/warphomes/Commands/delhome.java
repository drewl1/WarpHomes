package me.drewl.warphomes.Commands;

import me.drewl.warphomes.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class delhome implements CommandExecutor {
    Main main = Main.getPlugin(Main.class);
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File file = new File(this.main.getDataFolder(), "config.yml");
        FileConfiguration config = this.main.getConfig();
        Player player = (Player) sender;
        if (args.length != 1 && args != null) {
            player.sendMessage(ChatColor.RED + "Your provided arguments were too many or too few for this command!");
        } else {
            String homeName = args[0];
            String world = config.getString("players.homes." + player.getUniqueId()+ "." + homeName + ".world");
            if (world == null) {
                player.sendMessage(ChatColor.RED + "That home doesn't exist!");
            } else {
                config.set("players.homes." + player.getUniqueId() + "." + args[0], null);
                try {
                    config.save(file);
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "A problem was encountered while deleting that home!");
                }
                player.sendMessage(ChatColor.RED + "Your home " + ChatColor.GOLD + args[0] + ChatColor.RED + " was deleted successfully.");
            }
        }
        return false;
    }
}


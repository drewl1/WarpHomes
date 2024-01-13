package me.drewl.warphomes.Commands;

import me.drewl.warphomes.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class home implements CommandExecutor {
    Main main = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        String homeName;
        FileConfiguration config = main.getConfig();
        if (args.length != 1 && args != null) {
            p.sendMessage(ChatColor.RED + "Provide only one argument!");
        } else {
            homeName = args[0];
            if (!config.contains("players.homes." + p.getUniqueId() + "." + homeName)) {
                p.sendMessage(ChatColor.RED + "That home doesn't exist!");
                return true;
            }
            double locx = config.getDouble("players.homes." + p.getUniqueId() + "." + homeName + ".x");
            double locy = config.getDouble("players.homes." + p.getUniqueId() + "." + homeName + ".y");
            double locz = config.getDouble("players.homes." + p.getUniqueId() + "." + homeName + ".z");
            String world = config.getString("players.homes." + p.getUniqueId() + "." + homeName + ".world");
            World des = Bukkit.getServer().getWorld(world);
            try {
                if (world != null) {
                    if (locx != 0.0 && locy != 0.0 && locz != 0.0) {
                        Location location = new Location(des, locx + 0.5, locy, locz + 0.5);
                        p.teleport(location);
                    }
                }
            } catch (Exception except) {
                main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Ran into problem while trying to teleport player" + except);
            }
        }
        return false;
    }
}

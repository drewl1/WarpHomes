package me.drewl.warphomes.Commands;

import me.drewl.warphomes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.*;

public class homes implements CommandExecutor {
    Main main = Main.getPlugin(Main.class);
    FileConfiguration config;

    private ItemStack setBedIcon(String bedColor) {
        ItemStack bed;
        switch (bedColor.toLowerCase()) {
            case "white":
                bed = new ItemStack(Material.WHITE_BED);
                break;
            case "orange":
                bed = new ItemStack(Material.ORANGE_BED);
                break;
            case "magenta":
                bed = new ItemStack(Material.MAGENTA_BED);
                break;
            case "lightblue":
                bed = new ItemStack(Material.LIGHT_BLUE_BED);
                break;
            case "yellow":
                bed = new ItemStack(Material.YELLOW_BED);
                break;
            case "lime":
                bed = new ItemStack(Material.LIME_BED);
                break;
            case "pink":
                bed = new ItemStack(Material.PINK_BED);
                break;
            case "gray":
                bed = new ItemStack(Material.GRAY_BED);
                break;
            case "lightgray":
                bed = new ItemStack(Material.LIGHT_GRAY_BED);
                break;
            case "cyan":
                bed = new ItemStack(Material.CYAN_BED);
                break;
            case "purple":
                bed = new ItemStack(Material.PURPLE_BED);
                break;
            case "blue":
                bed = new ItemStack(Material.BLUE_BED);
                break;
            case "brown":
                bed = new ItemStack(Material.BROWN_BED);
                break;
            case "green":
                bed = new ItemStack(Material.GREEN_BED);
                break;
            case "red":
                bed = new ItemStack(Material.RED_BED);
                break;
            case "black":
                bed = new ItemStack(Material.BLACK_BED);
                break;
            default:
                bed = new ItemStack(Material.RED_BED);
                break;
        }
        return bed;
    }
    public void openHomesMenu(Player p) {
        FileConfiguration config = main.getConfig();

        String playername = p.getDisplayName();
        Inventory homemenu = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + playername +"'s Homes");

        int i = 0;
        for (Iterator<String> homeitem = config.getConfigurationSection("players.homes." + p.getUniqueId() + ".").getKeys(false).iterator(); homeitem.hasNext(); ++i) {
            String homeName = homeitem.next();
            String bedColor = config.getString("players.homes." + p.getUniqueId() + "." + homeName + ".color");
            ItemStack bedIcon = setBedIcon(bedColor);
            ItemMeta itemMeta = bedIcon.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + homeName);
            String world = config.getString("players.homes." + p.getUniqueId() + "." + homeName + ".world");
            int locy = (int) Math.floor(config.getDouble("players.homes." + p.getUniqueId() + "." + homeName + ".y"));
            int locz = (int) Math.floor(config.getDouble("players.homes." + p.getUniqueId() + "." + homeName + ".z"));
            int locx = (int) Math.floor(config.getDouble("players.homes." + p.getUniqueId() + "." + homeName + ".x"));
            List<String> lore = new ArrayList<>();
            lore.add("§e(" + locx + "§e, " + locy + "§e, " + locz + "§e)");
            lore.add("§e" + world);
            lore.add("§4Shift+Right-Click");
            lore.add("§4to Delete");
            itemMeta.setLore(lore);
            itemMeta.setCustomModelData(96467);
            bedIcon.setItemMeta(itemMeta);
            homemenu.setItem(i, bedIcon);
        }
        p.openInventory(homemenu);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        config = main.getConfig();
        if (!config.isConfigurationSection("players.homes." + p.getUniqueId())) {
            p.sendMessage(ChatColor.RED + "You haven't set any homes!");
        } else {
            int homes = this.config.getConfigurationSection("players.homes." + p.getUniqueId() + ".").getKeys(false).size();
            if (homes >= 1) {
                openHomesMenu(p);
            } else {
                p.sendMessage(ChatColor.RED + "You haven't set any homes!");
            }
        }
        return false;
    }
}

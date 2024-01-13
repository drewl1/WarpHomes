package me.drewl.warphomes.Events;



import java.io.File;
import java.io.IOException;

import me.drewl.warphomes.Main;
import org.bukkit.*;
import me.drewl.warphomes.Commands.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListeners implements Listener {
    Main main = Main.getPlugin(Main.class);
    FileConfiguration config = main.getConfig();
    private void openConfirmationGUI(Player player, String homeName) {
        Inventory confirmationGUI = Bukkit.createInventory(player, 9, ChatColor.RED + "Delete "+ homeName + "?");

        // Add Yes and No panes to the GUI
        ItemStack yesPane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemStack noPane = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        // Set display names if needed
        ItemMeta yesMeta = yesPane.getItemMeta();
        ItemMeta noMeta = noPane.getItemMeta();
        yesMeta.setDisplayName(ChatColor.GREEN + "Yes");
        noMeta.setDisplayName(ChatColor.RED + "No");
        yesMeta.setCustomModelData(11001);
        noMeta.setCustomModelData(01110);
        yesPane.setItemMeta(yesMeta);
        noPane.setItemMeta(noMeta);
        confirmationGUI.setItem(3, yesPane); // Place Yes pane in slot 3
        confirmationGUI.setItem(5, noPane); // Place No pane in slot 5
        player.openInventory(confirmationGUI);
    }
    private void deleteHome(Player player, String homeName) {
        File file = new File(main.getDataFolder(), "config.yml");
        config.set("players.homes." + player.getUniqueId() + "." + homeName, null);
        try {
            config.save(file);
        } catch (IOException ieo) {
            ieo.printStackTrace();
            player.sendMessage(ChatColor.RED + "A problem was encountered while deleting that home!");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        homes homesmenu = new homes();

        if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
            if (e.getCurrentItem().getItemMeta().hasCustomModelData() && !e.getCurrentItem().getType().equals(Material.AIR)) {
                if (e.getCurrentItem().getItemMeta().getCustomModelData() == 96467) {
                    if (e.isShiftClick() && e.isLeftClick()) {
                        e.setCancelled(true);
                        return;
                    }
                    String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
                    String homeName = itemName.substring(2, itemName.length());
                    if (e.isShiftClick() && e.isRightClick()) {
                        e.setCancelled(true);
                        openConfirmationGUI(p, homeName);
                        return;
                    }
                    if (e.isLeftClick() || e.isRightClick()) {
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
                            main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Ran into problem while trying to teleport player from homes menu:: ");
                        }
                        e.setCancelled(true);
                    }
                }
                if (e.getCurrentItem().getItemMeta().getCustomModelData() == 11001) {
                    String homeName = e.getView().getTitle();
                    homeName = homeName.substring(9, homeName.length());
                    homeName = homeName.substring(0, homeName.length() - 1);
                    deleteHome(p,homeName);
                    p.sendMessage(ChatColor.RED + "Your home " + ChatColor.GOLD + homeName + ChatColor.RED + " was deleted successfully.");
                    e.setCancelled(true);
                    int homes = config.getConfigurationSection("players.homes." + p.getUniqueId() + ".").getKeys(false).size();
                    if (homes >= 1) {
                        homesmenu.openHomesMenu(p);
                    } else {
                        p.closeInventory();
                    }
                }
                if (e.getCurrentItem().getItemMeta().getCustomModelData() == 01110) {
                    e.setCancelled(true);
                    homesmenu.openHomesMenu(p);
                }
            }
        }
    }
}
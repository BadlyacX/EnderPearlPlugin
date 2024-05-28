package org.badlyac;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SuperEnderPearl extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerUseEnderPearl(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL) {
            ItemMeta meta = event.getItem().getItemMeta();
            if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("SuperEnderPearl")) {
                if (event.getPlayer().getGameMode() == GameMode.SURVIVAL || event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
                    ItemStack enderPearl = event.getItem();

                    ItemStack superPearl = new ItemStack(Material.ENDER_PEARL);
                    ItemMeta superPearlMeta = superPearl.getItemMeta();
                    if (superPearlMeta != null) {
                        superPearlMeta.setDisplayName("SuperEnderPearl");
                        superPearl.setItemMeta(superPearlMeta);
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            event.getPlayer().getInventory().remove(enderPearl);
                            event.getPlayer().getInventory().addItem(superPearl);}
                    }.runTaskLater(this,1);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
            if (itemInHand != null && itemInHand.getType() == Material.ENDER_PEARL) {
                ItemMeta meta = itemInHand.getItemMeta();
                if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("SuperEnderPearl")) {
                    final double previousHealth = event.getPlayer().getHealth();

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            event.getPlayer().setHealth(previousHealth);
                        }
                    }.runTaskLater(this, 1);
                }
            }
        }
    }
}

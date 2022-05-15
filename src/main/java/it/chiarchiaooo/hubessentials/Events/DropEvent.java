package it.chiarchiaooo.hubessentials.Events;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Set;

public class DropEvent implements Listener {
    private final FileConfiguration config;
    private final HubEssentials plugin;

    public DropEvent(HubEssentials pl) {
        this.config = pl.getConfig();
        this.plugin =pl;
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent e) {
        Set<String> keys = config.getConfigurationSection("join-leave.items").getKeys(false);
        for (String pos : keys) {
            if (!e.getItemDrop().getItemStack().getType().isAir() && e.getItemDrop().getItemStack().getType().equals(Material.valueOf(config.getString("join-leave.items."+pos+".material"))) && e.getItemDrop().getItemStack().getItemMeta().hasDisplayName() && e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(plugin.sendmsg(e.getPlayer(),config.getString("join-leave.items."+pos+".name")))) {
                e.setCancelled(true);
                break;
            }
        }
    }
}

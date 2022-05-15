package it.chiarchiaooo.hubessentials.Events;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;


public class ChatEvent implements Listener {

    private final FileConfiguration config;
    private final HubEssentials plugin;

    public ChatEvent(HubEssentials pl) {
        this.plugin =pl;
        this.config = pl.getConfig();
    }

    @EventHandler
    public void onChat (AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String format = config.getString("chat-formats.default-format");
        String msg = e.getMessage();
        Set<String> keys = config.getConfigurationSection("chat-formats.groups").getKeys(false);
        for (String s : keys) {
            if (p.hasPermission(config.getString("chat-formats.groups."+s+".permission"))) {
                format = config.getString("chat-formats.groups."+s+".format");
                break;
            }
        }
        if (p.hasPermission("hubessentials.chat.color")) msg = plugin.sendmsg(null,msg);
        if (p.hasPermission("hubessentials.chat.emojis")) {
            for (String s: config.getStringList("chat-formats.emojis")) {
                String[] a = s.split(" , ");
                if (e.getMessage().contains(a[0])) {
                    msg = msg.replace(a[0],plugin.sendmsg(null,a[1]));
                    break;
                }
            }
        }
        format = plugin.sendmsg(e.getPlayer(), format).replace("%message%",msg);
        e.setFormat(format.replace("%","%%"));
    }
}

package it.chiarchiaooo.hubessentials.Utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import it.chiarchiaooo.hubessentials.HubEssentials;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JoinItems implements Listener {

    private final FileConfiguration config;
    private final HubEssentials plugin;

    public JoinItems(HubEssentials pl) {
        this.config = pl.getConfig();
        this.plugin = pl;

    }

    @EventHandler
    @SuppressWarnings({"deprecation","ConstantConditions"})
    public void onRightClick (PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType().isAir() || !p.getItemInHand().getItemMeta().hasDisplayName() || !config.getBoolean("join-leave.enabled")) return;
        Set<String> keys = config.getConfigurationSection("join-leave.items").getKeys(false);
        for (String pos : keys) {
            if (!p.getItemInHand().getItemMeta().getDisplayName().equals(plugin.sendmsg(p, config.getString("join-leave.items." + pos + ".name")))) continue;
            for (String s : config.getStringList("join-leave.items."+pos+".actions")) {
                if (s.startsWith("{command}")) {
                    String a = s.replace("{command} ", "");
                    Bukkit.dispatchCommand(p, a);
                }else if(s.startsWith("{console}")) {
                    String a = s.replace("{console} ", "");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a);
                }else if (s.startsWith("{action-bar}")) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.sendmsg(p,s.replace("{action-bar} ",""))));
                } else if(s.startsWith("{message}")) {
                    String a = s.replace("{message} ", "");
                    p.sendMessage(plugin.sendmsg(p,a));
                } else if(s.startsWith("{server}")) {
                    String a = s.replace("{server} ","");
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("ConnectOther");
                    out.writeUTF(p.getName());
                    out.writeUTF(a);
                    p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                }
            }
            return;
        }
    }
}

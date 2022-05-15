package it.chiarchiaooo.hubessentials.Events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import it.chiarchiaooo.hubessentials.HubEssentials;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiEvent implements Listener {
    private final FileConfiguration config;
    private final HubEssentials plugin;

    public GuiEvent(HubEssentials pl) {
        this.config = pl.getConfig();
        this.plugin = pl;
    }
    
    

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getView().getTitle().equals("Crafting") && (config.getString("join-leave.items."+e.getSlot()+".nome") != null || e.getCurrentItem() != null)) return;
        e.setCancelled(true);
        if (e.getView().getTitle().equals(plugin.sendmsg((Player) e.getWhoClicked(),config.getString("selector-gui.name")))) {
            for (String s : config.getStringList("selector-gui.slot." + e.getSlot() + ".actions")) {
                if (s.startsWith("{message}")) {
                    p.sendMessage(plugin.sendmsg((Player) e.getWhoClicked(),s.replace("{message} ", "")));
                }
                else if (s.startsWith("{action-bar}")) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(plugin.sendmsg((Player) e.getWhoClicked(),s.replace("{action-bar} ",""))));
                }else if(s.startsWith("{console}")) {
                    String a = s.replace("{console} ", "");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a);
                } else if (s.startsWith("{server}")) {
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("ConnectOther");
                    out.writeUTF(p.getName());
                    out.writeUTF(s.replace("{server} ",""));

                    p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());

                }else if(s.startsWith("{command}")) {
                    Bukkit.dispatchCommand(e.getWhoClicked(),s.replace("{command} ",""));
                }
            }
        }
    }
}

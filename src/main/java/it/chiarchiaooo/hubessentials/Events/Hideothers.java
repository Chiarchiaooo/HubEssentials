package it.chiarchiaooo.hubessentials.Events;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Hideothers implements Listener {

    private final FileConfiguration config;
    private final List<Player> hiddenpl;
    private final HubEssentials plugin;
    private final FileConfiguration messages;

    public Hideothers(HubEssentials pl) {
        this.config = pl.getConfig();
        this.messages = pl.messagescfg;
        this.plugin = pl;
        this.hiddenpl = pl.hiddenpl;

    }

    @EventHandler
    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public void hide(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        long timeLeft = System.currentTimeMillis() -3000;
        if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) <= 100) return;
        if (p.getItemInHand().getType().isAir() || !p.getItemInHand().getItemMeta().hasDisplayName() || e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (!(p.getItemInHand().getType().equals(Material.LIME_DYE) || p.getItemInHand().getType().equals(Material.GRAY_DYE))) return;
        if (p.hasPermission("hubessentials.vanish.command") && !config.getBoolean("hide-others.staff-block-bypass")) {p.sendMessage(plugin.sendmsg(p,messages.getString("staffblock-msg"))); return;}
        Set<String> keys = config.getConfigurationSection("join-leave.items").getKeys(false);
        for (String pos : keys) {
            if (!config.getStringList("join-leave.items." + pos + ".actions").contains("{hide-others}")) continue;
            List<String> lore = new ArrayList<>();
            if (hiddenpl.contains(p)) {
                for (Player ppl : Bukkit.getOnlinePlayers()) {
                    p.showPlayer(plugin, ppl);
                    ppl.showPlayer(plugin, p);
                }
                hiddenpl.remove(p);
                p.sendMessage(plugin.sendmsg(p, messages.getString("players-shown")));
                ItemStack i = new ItemStack(Material.LIME_DYE, 1);
                ItemMeta m = i.getItemMeta();
                m.setDisplayName(plugin.sendmsg(p, config.getString("hide-others.hidden-player-item-name")));
                //m.setDisplayName(plugin.sendmsg(p, config.getString("join-leave.items."+pos+".name")));
                m.setUnbreakable(true);
                for (String d : config.getStringList("shown-item-lore.shown-item-lore")) lore.add(plugin.sendmsg(p, d));
                m.setLore(lore);
                m.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
                m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                i.setItemMeta(m);
                p.getInventory().setItem(Integer.parseInt(pos), new ItemStack(Material.AIR,1));
                p.getInventory().setItem(Integer.parseInt(pos), i);
                return;
            } else {
                for (Player ppl : Bukkit.getOnlinePlayers()) {
                    p.hidePlayer(plugin, ppl);
                    ppl.hidePlayer(plugin, p);
                }
                hiddenpl.add(p);
                p.sendMessage(plugin.sendmsg(p, messages.getString("players-hidden")));
                ItemStack item = new ItemStack(Material.GRAY_DYE, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(plugin.sendmsg(p, config.getString("hide-others.shown-player-item-name")));
                for (String d : config.getStringList("hidden-item-lore.hidden-item-lore")) lore.add(plugin.sendmsg(p, d));
                meta.setLore(lore);
                if (config.getBoolean("join-leave.items." + pos + ".unbreakable")) {
                    meta.setUnbreakable(true);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }
                item.setItemMeta(meta);
                p.getInventory().setItem(Integer.parseInt(pos), new ItemStack(Material.AIR,1));
                p.getInventory().setItem(Integer.parseInt(pos), item);
                return;
            }
        }
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType().isAir() || !e.getItemDrop().getItemStack().getItemMeta().hasDisplayName()) return;
        if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(plugin.sendmsg(e.getPlayer(), config.getString("hide-others.shown-player-item-name"))) || e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(plugin.sendmsg(e.getPlayer(), config.getString("hide-others.shown-player-item-name")))) {
            e.setCancelled(true);
        }
    }
}
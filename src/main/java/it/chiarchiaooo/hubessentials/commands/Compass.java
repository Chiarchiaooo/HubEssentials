package it.chiarchiaooo.hubessentials.commands;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;


public class Compass implements CommandExecutor {

    private final FileConfiguration config;
    private final HubEssentials plugin;

    public Compass(HubEssentials pl) {
        this.config = pl.getConfig();
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (!(config.getInt("selector-gui.size") % 9 == 0)) {
            p.sendMessage(plugin.sendmsg(null,"&cInternal error occured while executing this action, check console for details"));
            plugin.getLogger().log(Level.SEVERE,"Error: selector gui size not a multiple of 9");
            return true;
        } else if (config.getInt("selector-gui.size") > 54) {
            p.sendMessage(plugin.sendmsg(null,"&cInternal error occured while executing this action, check console for details"));
            plugin.getLogger().log(Level.SEVERE,"Error: Max gui size is 54");
            return true;
        }
        Set<String> keys = config.getConfigurationSection("selector-gui.slot").getKeys(false);
        Inventory inv = Bukkit.createInventory(null,config.getInt("selector-gui.size"), plugin.sendmsg((Player) sender,config.getString("selector-gui.name")));
        for (String pos : keys) {
            if (Material.valueOf(config.getString("selector-gui.slot." + pos + ".item")).isAir()) continue;
            ItemStack is = new ItemStack(Material.valueOf(config.getString("selector-gui.slot." + pos + ".item")), 1);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(plugin.sendmsg(p,config.getString("selector-gui.slot." + pos + ".name")));
            List<String> lore = new ArrayList<>();
            for (String s : config.getStringList("selector-gui.slot." + pos + ".lore"))
                lore.add(plugin.sendmsg(((Player) sender), s));
            im.setLore(lore);
            if (config.getBoolean("selector-gui.slot." + pos + ".enchanted")) {
                im.addEnchant(Enchantment.LURE, 10, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            is.setItemMeta(im);
            inv.setItem(Integer.parseInt(pos), is);
        }
        for (int i =0; i< inv.getSize();i++) {
            if (Material.valueOf(config.getString("selector-gui.empty-slot-item")).isAir()) break;
            if (inv.getItem(i) == null) {
                ItemStack im = new ItemStack(Material.valueOf(config.getString("selector-gui.empty-slot-item")));
                ItemMeta m = im.getItemMeta();
                m.setDisplayName("§c");
                im.setItemMeta(m);
                inv.setItem(i,im);
            }
        }
        p.openInventory(inv);
        return false;
    }
}




        /*if (!(sender instanceof Player)) return true;
        Set<String> keys = config.getConfigurationSection("gui.slot").getKeys(false);
        //Inventory inv = Bukkit.createInventory(null,config.getInt("gui.size"),config.getString("gui.name"));
        for (String pos : keys){
            sender.sendMessage(pos);
            sender.sendMessage(config.getString("gui.slot."+pos+".name"));
        }
        return false;*/
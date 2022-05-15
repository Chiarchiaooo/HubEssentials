package it.chiarchiaooo.hubessentials.Events;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BowEvent implements Listener {
    private final FileConfiguration config;
    private final HubEssentials pl;

    public BowEvent(HubEssentials plg) {
        this.pl = plg;
        this.config = plg.getConfig();
    }

    @EventHandler
    public void onconsume(EntityShootBowEvent e) {
        long timeLeft = System.currentTimeMillis() -3000;
        if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) <= 500) return;
        if (!(e.getEntity() instanceof Player) || !(e.getProjectile() instanceof Arrow)) return;
        ItemStack i = new ItemStack(Material.ARROW,1);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(pl.sendmsg(((Player)e.getEntity()),"&A Strange Arrow"));
        m.setLore(Arrays.asList("",pl.sendmsg(((Player)e.getEntity()),"&7An arrow is Will thrown through space."),pl.sendmsg(((Player)e.getEntity()),"&7Once fired..."),pl.sendmsg(((Player)e.getEntity()),"&7There is nothing the archer can do,"),pl.sendmsg(((Player)e.getEntity()),"&7Except observe its trajectory to its target."),""));
        m.setUnbreakable(true);
        m.addEnchant(Enchantment.ARROW_INFINITE,10,true);
        m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(m);
        ((Player)e.getEntity()).getInventory().setItem(9,i);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onFire(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player) || !(e.getEntity() instanceof Arrow)) return;
        Set<String> keys = config.getConfigurationSection("join-leave.items").getKeys(false);
        Player p = (Player) e.getEntity().getShooter();
        ItemStack i = new ItemStack(Material.ARROW,1);
        for (String pos : keys) {
            if (!config.getStringList("join-leave.items."+pos+".actions").contains("{bow-tp}") && config.getStringList("join-leave.items."+pos+".actions").size() ==1) continue;
            String s = pl.sendmsg(p, config.getString("join-leave.items." + pos + ".name"));
            if (p.getItemInHand().getItemMeta().getDisplayName().equals(s)) {
                Location loc = e.getEntity().getLocation();
                loc.setPitch(p.getLocation().getPitch());
                loc.setYaw(p.getLocation().getYaw());
                e.getEntity().remove();
                p.teleport(loc);
                break;
            }
        }
    }
}

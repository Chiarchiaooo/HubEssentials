package it.chiarchiaooo.hubessentials.Events;



import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class JoinEvent implements Listener {

    private final FileConfiguration config;
    private final FileConfiguration messages;
    private final HubEssentials plugin;
    private final List<Player> vanishpl;

    public JoinEvent(HubEssentials pl) {
        this.plugin = pl;
        this.messages = pl.messagescfg;
        this.config = pl.getConfig();
        this.vanishpl = pl.vanishpl;
    }


    @EventHandler
    @SuppressWarnings({"ConstantConditions"})
    public void PJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.getInventory().clear();
        for (Player ppl: Bukkit.getOnlinePlayers()) {
            if ((vanishpl.contains(ppl) && (config.getBoolean("join-leave.vanish-on-join") && p.hasPermission("hubessentials.vanish.command")))) {
                p.hidePlayer(plugin,ppl);
                vanishpl.add(ppl);
            }
        }

        String d = plugin.sendmsg(e.getPlayer(),messages.getString("join-msg"));
        e.setJoinMessage(d);
        for (PotionEffect pe : p.getActivePotionEffects()) p.removePotionEffect(pe.getType());
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,1,false,false));
        if (config.getBoolean("join-leave.title-enable")) {
            p.sendTitle(config.getString("join-leave.title-settings.title"), config.getString("join-leave.title-settings.subtitle"), config.getInt("join-leave.title-settings.fade-in"), config.getInt("join-leave.title-settings.duration"), config.getInt("join-leave.title-settings.fade-out"));
        }
        Set<String> keys = config.getConfigurationSection("join-leave.items").getKeys(false);
        for (String pos : keys) {
            if (Material.valueOf(config.getString("join-leave.items."+pos+".material").toUpperCase()).equals(Material.BOW)) {
                ItemStack i = new ItemStack(Material.ARROW,1);
                ItemMeta m = i.getItemMeta();
                m.setDisplayName(plugin.sendmsg(e.getPlayer(),"&bA Strange Arrow"));
                m.setLore(Arrays.asList("",plugin.sendmsg(p,"&7An arrow is Will thrown through space."),plugin.sendmsg(p,"&7Once fired..."),plugin.sendmsg(p,"&7There is nothing the archer can do,"),plugin.sendmsg(p,"&7Except observe its trajectory to its target."),""));
                m.setUnbreakable(true);
                m.addEnchant(Enchantment.ARROW_INFINITE,10,true);
                m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS);
                i.setItemMeta(m);
                e.getPlayer().getInventory().setItem(9,i);
            }
            ItemStack item = new ItemStack(Material.valueOf(config.getString("join-leave.items."+pos+".material").toUpperCase()), config.getInt("join-leave.items."+pos+".quantity"));
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if ((Material.valueOf(config.getString("join-leave.items."+pos+".material").toUpperCase()).equals(Material.LIME_DYE) || config.getStringList("join-leave.items."+pos+".actions").contains("{hide-others}"))) {
                for (String s : config.getStringList("hide-others.hidden-item-lore")) {
                    lore.add(plugin.sendmsg(e.getPlayer(),s));
                }
            } else {
                for (String s : config.getStringList("join-leave.items."+pos+".lore")) lore.add(plugin.sendmsg(e.getPlayer(),s));
            }
            meta.setDisplayName(plugin.sendmsg(e.getPlayer(),config.getString("join-leave.items."+pos+".name")));
            meta.setLore(lore);
            if (config.getBoolean("join-leave.items."+pos+".enchanted")) {
                meta.addEnchant(Enchantment.ARROW_INFINITE,10,true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }if(config.getBoolean("join-leave.items."+pos+".unbreakable")) {
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
            item.setItemMeta(meta);
            e.getPlayer().getInventory().setItem(Integer.parseInt(pos), item);
        }
    }
}

       /*if (config.getBoolean("scoreboard.enabled")) {
            Scoreboard sboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective o = sboard.registerNewObjective(plugin.sendmsg(e.getPlayer(),config.getString("scoreboard.title")), "dummy",plugin.sendmsg(e.getPlayer(),config.getString("scoreboard.title")));
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            List<String> lines = config.getStringList("scoreboard.lines");
            Set<String> entries = sboard.getEntries();
            for(String entry : entries) sboard.resetScores(entry);
            for (int x =1; x<lines.size();x++) {
                Score score = o.getScore(plugin.sendmsg(e.getPlayer(),lines.get(x).replace("%blank%"," ")));
                score.setScore(lines.size() - x);
            }
            p.setScoreboard(sboard);
        }*/
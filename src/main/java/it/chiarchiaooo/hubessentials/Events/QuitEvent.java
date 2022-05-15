package it.chiarchiaooo.hubessentials.Events;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class QuitEvent implements Listener {

    private final FileConfiguration config;
    private final List<Player> vanishpl;
    private final HubEssentials plugin;
    private final FileConfiguration messages;

    public QuitEvent(HubEssentials pl) {
        this.config = pl.getConfig();
        this.messages = pl.messagescfg;
        this.vanishpl = pl.vanishpl;
        this.plugin = pl;
    }

    @EventHandler
    public void JoinEvent(PlayerQuitEvent e){
        if (messages.getString("leave-msg") == null || messages.getString("leave-msg").isEmpty()) e.setQuitMessage(null);
        else e.setQuitMessage(plugin.sendmsg(e.getPlayer(),messages.getString("leave-msg")));
        //e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        if (vanishpl.contains(e.getPlayer()) && config.getBoolean("join-leave.vanish-settings.reveal-on-quit")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.showPlayer(plugin,e.getPlayer());
                vanishpl.remove(p);
            }
        }
        
    }
}

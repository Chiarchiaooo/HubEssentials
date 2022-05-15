package it.chiarchiaooo.hubessentials.Events;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.Set;


public class CmdEvent implements Listener {

    private final FileConfiguration config;
    private final FileConfiguration messages;
    private final HubEssentials plugin;

    public CmdEvent(HubEssentials pl) {
        this.messages = pl.messagescfg;
        this.config = pl.getConfig();
        this.plugin = pl;
    }


    @EventHandler
    public void cmd(PlayerCommandPreprocessEvent e) {
            String cmd = e.getMessage().split(" ")[0];
            if (config.getBoolean("cmd-blocker.enabled") || e.getPlayer().hasPermission("hubessentials.cmdblock.bypass.*") || config.getStringList("cmd-blocker.cmd-whitelist").contains(cmd) || cmd.equalsIgnoreCase("hubessentials help") || cmd.equalsIgnoreCase("hess help")) return;
            Set<String> keys = config.getConfigurationSection("cmd-blocker.groups").getKeys(false);
            for (String s : keys) {
                if (e.getPlayer().hasPermission(config.getString("cmd-blocker.groups." + s + ".permission")) && config.getStringList("cmd-blocker.groups." + s + ".commands").contains(cmd))
                    return;
            }

            e.setCancelled(true);
            e.getPlayer().sendMessage(plugin.sendmsg(e.getPlayer(), messages.getString("unknown-command")).replace("%command%", cmd));
        }
}
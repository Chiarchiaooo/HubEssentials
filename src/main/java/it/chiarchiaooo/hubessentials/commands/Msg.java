package it.chiarchiaooo.hubessentials.commands;

import it.chiarchiaooo.hubessentials.HubEssentials;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class Msg implements CommandExecutor {

    private final HubEssentials plugin;
    private final FileConfiguration messages;
    private final List<UUID> socialspiedPls;

    public Msg(HubEssentials pl) {
        this.plugin = pl;
        this.messages = pl.messagescfg;
        this.socialspiedPls = pl.socialspiedPls;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player p = Bukkit.getPlayer(args[0]);

        if (p == null || !p.isOnline()) {
            sender.sendMessage(plugin.sendmsg(null, messages.getString("player-not-found")));
            return true;

        } else if (p.equals(sender)) {
            sender.sendMessage(plugin.sendmsg((Player) sender,messages.getString("cannot-message-self")));
            return true;

        } if (sender instanceof Player){
            sender.sendMessage(plugin.sendmsg((Player) sender,messages.getString("msg-format").replace("%addresser%",ChatColor.stripColor(p.getName()))));
                for (Player staffs : Bukkit.getOnlinePlayers()) {
                    if ((staffs.hasPermission("hubessentials.socialspy.command") && socialspiedPls.contains(staffs.getUniqueId())) && !p.hasPermission("hubessentials.socialspy.bypass") && !staffs.equals(sender)) {
                        staffs.sendMessage(plugin.sendmsg((Player) sender,messages.getString("socialspy-format").replace("%addresser%",ChatColor.stripColor(p.getName()))));
                    }
                }

        } else sender.sendMessage(plugin.sendmsg(null,messages.getString("msg-format").replace("%addresser%",ChatColor.stripColor(p.getName()))));

        p.sendMessage(plugin.sendmsg((Player) sender,messages.getString("msg-format").replace("%addresser%",ChatColor.stripColor(p.getName()))));
        return false;
    }
}

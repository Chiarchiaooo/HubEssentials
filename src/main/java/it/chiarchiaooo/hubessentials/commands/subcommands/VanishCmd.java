package it.chiarchiaooo.hubessentials.commands.subcommands;


import it.chiarchiaooo.hubessentials.HubEssentials;
import it.chiarchiaooo.hubessentials.Utils.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VanishCmd implements CommandHandler.CommandInterface {


    private final HubEssentials plugin;
    private final FileConfiguration messages;
    private final List<Player> vanishpl;


    public VanishCmd(HubEssentials pl) {
        this.plugin = pl;
        this.messages = pl.messagescfg;
        this.vanishpl = pl.vanishpl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        switch (args.length) {

            case 1 -> { //vanish/ reveal yourself
                if (!(sender instanceof Player p)) return true;

                if (!vanishpl.contains(p)) {
                    for (Player ppl : Bukkit.getOnlinePlayers()) {
                        if (!ppl.hasPermission("hubessentials.vanish.see")) {
                            ppl.hidePlayer(plugin, p);
                        }
                        vanishpl.add(p);
                        sender.sendMessage(plugin.sendmsg(p, messages.getString("vanish-msg")));
                    }
                    return true;
                } else {
                    for (Player ppl : Bukkit.getOnlinePlayers()) {
                        ppl.showPlayer(plugin, p);
                        vanishpl.remove(p);
                        sender.sendMessage(plugin.sendmsg(p, messages.getString("reveal-msg")));
                    }
                }
            }
            case 2 -> { //vanish/ reveal other staffs

                if (args[1].equals("list") && sender.hasPermission("hubessentials.vanish.list")) { // shows vanished staffs

                    final List<String> vstaffs = new ArrayList();
                    final String s = plugin.sendmsg(null, messages.getString("vanish-list-msg-format").replace("%offlinecolor%", "&cOffline").replace("%onlinecolor%", "&aOnline"));

                    if (vanishpl.isEmpty()) {
                        sender.sendMessage(plugin.sendmsg(null,messages.getString("no-staff-vanished")));
                        return true;
                    }
                    for (Player staff : vanishpl) {
                        if (staff.isOnline()) vstaffs.add("&a" + staff.getName());
                        else vstaffs.add("&c" + staff.getName());
                    }
                    sender.sendMessage(s.replace("%list%", ""));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',String.join(", ", vstaffs)));
                    vstaffs.clear();
                    return true;

                } else if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(plugin.sendmsg(null, messages.getString("player-not-found")));
                    return true;

                } else if (sender instanceof Player p && Bukkit.getPlayer(args[1]) == p) {

                        if (!vanishpl.contains(p)) {

                            for (Player ppl : Bukkit.getOnlinePlayers()) {
                                if (!ppl.hasPermission("hubessentials.vanish.see")) {
                                    ppl.hidePlayer(plugin, p);
                                }
                                vanishpl.add(p);
                                sender.sendMessage(plugin.sendmsg(p, messages.getString("vanish-msg")));
                            }
                            return true;

                        } else {

                            for (Player ppl : Bukkit.getOnlinePlayers()) {
                                ppl.showPlayer(plugin, p);
                                vanishpl.remove(p);
                                sender.sendMessage(plugin.sendmsg(p, messages.getString("reveal-msg")));
                            }
                        }
                        return true;
                    }

                else if (!vanishpl.contains(Bukkit.getPlayer(args[1]))) {

                    for (Player ppl : Bukkit.getOnlinePlayers()) {

                        if (!ppl.hasPermission("hubessentials.vanish.see")) ppl.hidePlayer(plugin, Bukkit.getPlayer(args[1]));

                    }
                    vanishpl.add(Bukkit.getPlayer(args[1]));
                    sender.sendMessage(plugin.sendmsg(null, messages.getString("vanish-other-msg").replace("%player%", Bukkit.getPlayer(args[1]).getName())));
                    Bukkit.getPlayer(args[1]).sendMessage(plugin.sendmsg(null, messages.getString("vanished-you")));
                    return true;

                } else {

                    for (Player ppl : Bukkit.getOnlinePlayers()) {

                        ppl.showPlayer(plugin, Bukkit.getPlayer(args[1]));
                        vanishpl.remove(Bukkit.getPlayer(args[1]));

                    }
                }
                sender.sendMessage(plugin.sendmsg(null, messages.getString("reveal-other-msg").replace("%player%", Bukkit.getPlayer(args[1]).getName())));
                Bukkit.getPlayer(args[1]).sendMessage(plugin.sendmsg(null, messages.getString("reveal-you")));
            }

            default -> sender.sendMessage(plugin.sendmsg(null, messages.getString("noarg")));
        }
        return true;
    }
}

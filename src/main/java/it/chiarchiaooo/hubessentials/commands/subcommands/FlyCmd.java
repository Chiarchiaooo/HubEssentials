package it.chiarchiaooo.hubessentials.commands.subcommands;

import it.chiarchiaooo.hubessentials.HubEssentials;
import it.chiarchiaooo.hubessentials.Utils.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class FlyCmd implements CommandHandler.CommandInterface {

    private final FileConfiguration messages;
    private final List<UUID> flypl;
    private final HubEssentials plugin;

    public FlyCmd(HubEssentials pl) {
        this.plugin = pl;
        this.messages = pl.messagescfg;
        this.flypl = pl.flypl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;
        switch (args.length) {

            case 1 -> { // arg0: cmd
                if (flypl.contains(p.getUniqueId())) {
                    p.setAllowFlight(false);
                    flypl.remove(p.getUniqueId());
                    p.sendMessage(plugin.sendmsg(p, messages.getString("disabled-fly")));
                } else {
                    p.setAllowFlight(true);
                    flypl.add(p.getUniqueId());
                    p.sendMessage(plugin.sendmsg(p, messages.getString("enabled-fly")));
                }
            }

            case 2 -> { // arg0: cmd arg1:player
                if (!p.hasPermission("hubessentials.fly.others")) {
                    p.sendMessage(plugin.sendmsg(p, messages.getString("noperms")));
                    return true;
                }
                Player pl = Bukkit.getPlayer(args[1]);
                if (pl == null) {
                    p.sendMessage(plugin.sendmsg(p, messages.getString("player-not-found")));
                    return true;

                } else if (pl == p) {
                    p.sendMessage(plugin.sendmsg(p, messages.getString("self-fly-error")));
                    return true;

                }
                if (flypl.contains(pl.getUniqueId())) {
                    pl.setAllowFlight(false);
                    flypl.remove(pl.getUniqueId());
                    pl.sendMessage(plugin.sendmsg(p, messages.getString("disabled-fly-you")));
                    p.sendMessage(plugin.sendmsg(pl, messages.getString("disabled-fly-other")));
                } else {
                    pl.setAllowFlight(true);
                    flypl.add(pl.getUniqueId());
                    pl.sendMessage(plugin.sendmsg(p, messages.getString("enabled-fly-you")));
                    p.sendMessage(plugin.sendmsg(pl, messages.getString("enabled-fly-other")));
                }
            }

            case 3 -> {// arg0:cmd, arg1:player arg2:enabled/disable
                Player pl = Bukkit.getPlayer(args[1]);
                if (!p.hasPermission("hubessentials.fly.others")) {
                    p.sendMessage(plugin.sendmsg(p, messages.getString("noperms")));
                    return true;

                } else if (pl == null) {
                    p.sendMessage(plugin.sendmsg(p, messages.getString("player-not-found")));
                    return true;

                } else if (pl == p) {
                    p.sendMessage(plugin.sendmsg(p, messages.getString("self-fly-error")));
                    return true;

                } else if (args[2].equalsIgnoreCase("disable")) {
                    pl.setAllowFlight(false);
                    flypl.remove(pl.getUniqueId());
                    pl.sendMessage(plugin.sendmsg(p, messages.getString("disabled-fly-you")));
                    p.sendMessage(plugin.sendmsg(pl, messages.getString("disabled-fly-other")));

                } else if (args[2].equalsIgnoreCase("enable")) {
                    pl.setAllowFlight(true);
                    flypl.add(pl.getUniqueId());
                    pl.sendMessage(plugin.sendmsg(p, messages.getString("enabled-fly-you")));
                    p.sendMessage(plugin.sendmsg(pl, messages.getString("enabled-fly-other")));
                }
            }
        }
        return false;
    }
}

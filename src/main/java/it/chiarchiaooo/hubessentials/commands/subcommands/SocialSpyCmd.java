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

public class SocialSpyCmd implements CommandHandler.CommandInterface {

    private final FileConfiguration messages;
    private final HubEssentials plugin;
    private final List<UUID> socialspiedPls;

    public SocialSpyCmd(HubEssentials pl) {
        this.plugin = pl;
        this.messages = pl.messagescfg;
        this.socialspiedPls = pl.socialspiedPls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length <1) return true;


        else if (args.length >2 && sender instanceof Player p) {

            if (!socialspiedPls.contains(p.getUniqueId())) {

                p.sendMessage(plugin.sendmsg(p,messages.getString("socialspy-enabled")));
                socialspiedPls.add(p.getUniqueId());

            } else {

                p.sendMessage(plugin.sendmsg(p,messages.getString("socialspy-disabled")));
                socialspiedPls.remove(p.getUniqueId());
            }

        } else if (sender.hasPermission("hubessentials.socialspy.others")) {
            sender.sendMessage(plugin.sendmsg((Player) sender,messages.getString("noperms")));
            return true;

        }else if (args.length ==2) {

            Player pl = Bukkit.getPlayer(args[1]);

            if (pl == null || !pl.isOnline()) {
                sender.sendMessage(plugin.sendmsg(pl,messages.getString("socialspy-enabled")));
                return true;
            }
            else if (!socialspiedPls.contains(pl.getUniqueId())) {

                sender.sendMessage(plugin.sendmsg(pl,messages.getString("socialspy-enabled")));
                socialspiedPls.add(pl.getUniqueId());

            } else {

                sender.sendMessage(plugin.sendmsg(pl,messages.getString("socialspy-disabled")));
                socialspiedPls.remove(pl.getUniqueId());
            }

        }

        return false;
    }
}

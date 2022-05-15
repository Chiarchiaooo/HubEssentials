package it.chiarchiaooo.hubessentials.commands.subcommands;


import it.chiarchiaooo.hubessentials.HubEssentials;
import it.chiarchiaooo.hubessentials.Utils.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Helpcmd implements CommandHandler.CommandInterface {
    private final HubEssentials plugin;

    public Helpcmd(HubEssentials plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length < 1) return true;

            if (sender instanceof Player) {
                sender.sendMessage(plugin.sendmsg(null,"\n&eHubEssentials &8&l - &eHelp: \n\n"));
                sender.sendMessage("");
                if (sender.hasPermission("hubessentials.help.staff")) {
                    sender.sendMessage(plugin.sendmsg(null, "&cStaff cmds:\n\n"));

                    if (plugin.getConfig().getBoolean("modules-enable.spawn-cmd"))
                        sender.sendMessage(plugin.sendmsg(null, "&e/hubess vanish [<player>] &8- &fMaks invisible you or another staff staff member"));

                    if (plugin.getConfig().getBoolean("modules-enable.spawn-cmd"))
                        sender.sendMessage(plugin.sendmsg(null, "&e/hubess fly [<player>] [<enable/disable>] &8- &fToggles fly for yourself or for someone else"));

                    if (plugin.getConfig().getBoolean("modules-enable.gamemodes-cmd")) {
                        sender.sendMessage(plugin.sendmsg(null, "&e/gm <s|survival / c|creative / sp|spectator> [<player>] &8- &fChange yourself or others gamemode"));
                        sender.sendMessage(plugin.sendmsg(null, "&e/gmc &8- &fAlias to /gm creative"));
                        sender.sendMessage(plugin.sendmsg(null, "&e/gms &8- &fAlias to /gm survival"));
                        sender.sendMessage(plugin.sendmsg(null, "&e/gmsp &8- &fAlias to /gm spectator"));
                    }

                    sender.sendMessage(plugin.sendmsg(null, "&e/hubess reload &8- &fReloads plugin configs\n\n"));
                    sender.sendMessage(plugin.sendmsg(null, "&e/hubess reset [<CONFIRM>] &8- &fResets plugin configs\n\n"));
                    sender.sendMessage("");
                }

                if (plugin.getConfig().getBoolean("modules-enable.rules-cmd")) sender.sendMessage(plugin.sendmsg(null, "&e/rules &8- &fShows server rules"));
                if (plugin.getConfig().getBoolean("modules-enable.spawn-cmd")) sender.sendMessage(plugin.sendmsg(null, "&e/menu &8- &fOpen selector"));
                if (plugin.getConfig().getBoolean("modules-enable.spawn-cmd")) sender.sendMessage(plugin.sendmsg(null, "&e/spawn &8- &fTp to spawn\n"));
                if (plugin.getConfig().getBoolean("modules-enable.spawn-cmd")) sender.sendMessage(plugin.sendmsg(null, "&e/fly &8- &fToggles fly for yourself"));

                sender.sendMessage(plugin.sendmsg(null, "&e/hubess &8- &fShows information about the plugin\n"));
                sender.sendMessage(plugin.sendmsg(null, "&e/hubess help &8- &fShows this list\n"));
                return false;

            } else {
                sender.sendMessage(plugin.sendmsg(null,"&eHubEssentials &8- &6Console help:"));
                sender.sendMessage(plugin.sendmsg(null,"&6Tip &8- &fUse /hubess help in game to see the full list"));
                sender.sendMessage("");
                if (plugin.getConfig().getBoolean("modules-enable.spawn-cmd")) sender.sendMessage(plugin.sendmsg(null,"&e/hubess vanish <player> &8- &fMaks invisible you or another staff staff member"));
                if (plugin.getConfig().getBoolean("modules-enable.spawn-cmd")) sender.sendMessage(plugin.sendmsg(null,"&e/hubess fly <player> [<enable/disable>] &8- &fToggles fly for yourself or for someone else"));
                if (plugin.getConfig().getBoolean("modules-enable.gamemodes-cmd")) {
                    sender.sendMessage(plugin.sendmsg(null,"&e/gm <s|survival / c|creative / sp|spectator> <player> &8- &fChange yourself or others gamemode"));
                    sender.sendMessage(plugin.sendmsg(null,"&e/gmc &8- &fAlias to /gm creative"));
                    sender.sendMessage(plugin.sendmsg(null,"&e/gms &8- &fAlias to /gm survival"));
                    sender.sendMessage(plugin.sendmsg(null,"&e/gmsp &8- &fAlias to /gm spectator"));

                }
                sender.sendMessage(plugin.sendmsg(null, "&e/hubess &8- &fShows information about the plugin"));
                sender.sendMessage(plugin.sendmsg(null, "&e/hubess help &8- &fShows this list"));
                sender.sendMessage("");

                return true;
            }
    }
}

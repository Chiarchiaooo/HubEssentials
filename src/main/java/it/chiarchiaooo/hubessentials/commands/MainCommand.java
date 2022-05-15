package it.chiarchiaooo.hubessentials.commands;


import it.chiarchiaooo.hubessentials.HubEssentials;
import it.chiarchiaooo.hubessentials.Utils.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandHandler.CommandInterface {

    private final HubEssentials plugin;

    public MainCommand(HubEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length > 0) return true;
        sender.sendMessage(plugin.sendmsg(null,"&6HubEssentials &7- &ePlugin made by &6Chiarchiaooo &8- &efor help, type /hubess help"));
        return true;
    }
}

package it.chiarchiaooo.hubessentials.commands.subcommands;


import it.chiarchiaooo.hubessentials.HubEssentials;
import it.chiarchiaooo.hubessentials.Utils.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;



public class ReloadCmd implements CommandHandler.CommandInterface {

    private final HubEssentials plugin;
    private final FileConfiguration messages;

    public ReloadCmd(HubEssentials pl) {
        this.plugin = pl;
        this.messages = pl.messagescfg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length <1) return true;
        final long x = System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage("§7----------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("[HubEssentials] "+ChatColor.GRAY+"Reloading config...");
        plugin.setupconfig();
        plugin.reloadConfig();
        plugin.setupEvents();
        plugin.setupCmds();
        if (sender instanceof Player) sender.sendMessage(plugin.sendmsg((Player) sender,messages.getString("reload-msg")));
        Bukkit.getConsoleSender().sendMessage(plugin.sendmsg(null,"[HubEssentials] &aConfig reloaded successfully (in "+(System.currentTimeMillis() - x)+"ms)"));
        Bukkit.getConsoleSender().sendMessage("§7----------------------------------------------");
        return true;
    }
}

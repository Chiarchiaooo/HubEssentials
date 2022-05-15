package it.chiarchiaooo.hubessentials.Utils;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class TabCompleteHandler implements TabCompleter {

    private final HashMap<String, CommandHandler.CommandInterface> commands;

    public TabCompleteHandler(CommandHandler cmdh) {
        this.commands = cmdh.commands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> l = new ArrayList<>();
        if ((cmd.getName().equalsIgnoreCase("hubessentials")) && !(args[0].equalsIgnoreCase("help")))  {
            switch (args.length) {
                case 1 -> {
                    for (String m : commands.keySet()) {
                        if (sender.hasPermission("hubessentials."+m+".command") || m.equals("help")) {
                            l.add(m);
                        }
                    }
                    l.remove("hubessentials");
                }
                case 2 -> {
                    if ((args[0].equalsIgnoreCase("vanish") && sender.hasPermission("hubessentials.vanish.others")) || (args[0].equalsIgnoreCase("fly") && sender.hasPermission("hubessentials.fly.others")) || (args[0].equalsIgnoreCase("gamemode") && sender.hasPermission("hubessentials.gamemode.others"))) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            l.add(p.getName());
                        }
                    }
                }

                case 3 -> {
                    if ((args[0].equalsIgnoreCase("fly")) && sender.hasPermission("hubessentials.fly.others")) return Arrays.asList("enable","disable");
                }
            }
            return l;
        } else if (cmd.getName().equalsIgnoreCase("fly") || cmd.getName().equalsIgnoreCase("vanish") || cmd.getName().equalsIgnoreCase("socialspy")) {
            if (args.length ==1) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    l.add(p.getName());
                }
            } else if (args.length ==2 && cmd.getName().equalsIgnoreCase("fly")) return Arrays.asList("enable","disable");
        }
        return l;
    }
}

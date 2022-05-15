package it.chiarchiaooo.hubessentials.commands;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Rules implements CommandExecutor {

    private final HubEssentials plugin;

    public Rules(HubEssentials pl) {
        this.plugin =pl;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {sender.sendMessage(plugin.getConfig().getString("console-cannot-execute")); return true;}
        try {
            Scanner s = new Scanner(plugin.rulesfile);
            while (s.hasNext()) {
                sender.sendMessage(plugin.sendmsg((Player) sender,s.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        return false;
    }
}

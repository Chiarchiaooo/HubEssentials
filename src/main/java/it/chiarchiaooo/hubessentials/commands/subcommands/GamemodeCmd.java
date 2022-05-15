package it.chiarchiaooo.hubessentials.commands.subcommands;

import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;


public class GamemodeCmd implements CommandExecutor {

    private final HubEssentials plugin;
    private final FileConfiguration messages;


    public GamemodeCmd(HubEssentials pl) {
        this.plugin = pl;
        this.messages = pl.messagescfg;
    }

    private void setGamemode(Player p,String gamemode) {
        p.setGameMode(GameMode.valueOf(gamemode));
        p.sendMessage(plugin.sendmsg(p,messages.getString("gamemode-changed").replace("%gamemode%",gamemode)));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            if (command.getName().startsWith("gm") && command.getName().length() <=3 && args.length > 1) {
                Player pl = Bukkit.getPlayer(args[1]);
                if (pl == null || !pl.isOnline()) {
                    sender.sendMessage(plugin.sendmsg(null, messages.getString("player-not-found").replace("%player%", args[1])));
                    return true;
                } else if (sender.hasPermission("hubessentials.gamemode.others")) {
                    sender.sendMessage(plugin.sendmsg(null, messages.getString("noperms")));
                    return true;

                } else if (!pl.hasPermission("hubessentials.gamemode." + args[0])) {
                    sender.sendMessage(plugin.sendmsg(pl, messages.getString("player-has-no-perms")));
                    return true;

                } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                    setGamemode(pl,"CREATIVE");
                } else if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                    setGamemode(pl,"SURVIVAL");
                } else if (args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator")) {
                    setGamemode(pl,"SPECTATOR");
                }
            } else if (command.getName().length() <=3) {
                plugin.getLogger().log(Level.SEVERE, "Console cannot execute this command");
            }
            return true;
        }
        switch (command.getName()) {
            case "gm" -> {
                switch (args.length) {
                    case 1 -> {
                        if (!sender.hasPermission("hubessentials.gamemode." + args[0])) {
                            sender.sendMessage(plugin.sendmsg(p, messages.getString("noperms")));
                            return true;
                        } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                            setGamemode(p,"CREATIVE");
                        } else if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                            setGamemode(p,"SURVIVAL");
                        } else if (args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator")) {
                            setGamemode(p,"SPECTATOR");
                        }
                    }
                    case 2 -> {
                        Player pl = Bukkit.getPlayer(args[1]);
                        if (pl == null || !pl.isOnline()) {
                            sender.sendMessage(plugin.sendmsg(null, messages.getString("player-not-found").replace("%player%", args[1])));
                            return true;
                        } else if (sender.hasPermission("hubessentials.gamemode.others")) {
                            sender.sendMessage(plugin.sendmsg(null, messages.getString("noperms")));
                            return true;

                        } else if (!pl.hasPermission("hubessentials.gamemode." + args[0])) {
                            sender.sendMessage(plugin.sendmsg(pl, messages.getString("player-has-no-perms")));
                            return true;

                        } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                            setGamemode(pl,"CREATIVE");
                        } else if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                            setGamemode(p,"SURVIVAL");
                        } else if (args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator")) {
                            setGamemode(p,"SPECTATOR");
                        }
                    }
                }
            }
            case "gmc" -> {
                if (!sender.hasPermission("hubessentials.gamemode.creative")) {
                    sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("noperms")));
                    return true;
                }
                setGamemode(p,"CREATIVE");
            }

            case "gms" -> {
                if (!sender.hasPermission("hubessentials.gamemode.survival")) {
                    sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("noperms")));
                    return true;
                }
                setGamemode(p,"SURVIVAL");
            }

            case "gmsp" -> {
                if (!sender.hasPermission("hubessentials.gamemode.spectator")) {
                    sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("noperms")));
                    return true;
                }
                setGamemode(p,"SPECTATOR");
            }
        }
    return false;
    }
}

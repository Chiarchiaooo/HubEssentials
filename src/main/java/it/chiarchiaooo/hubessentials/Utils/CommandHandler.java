package it.chiarchiaooo.hubessentials.Utils;


import it.chiarchiaooo.hubessentials.HubEssentials;
import it.chiarchiaooo.hubessentials.commands.MainCommand;
import it.chiarchiaooo.hubessentials.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;


//The class will implement CommandExecutor.
public class CommandHandler implements CommandExecutor {

    private final HubEssentials plugin;
    private final FileConfiguration messages;
    private final FileConfiguration config;
    public final HashMap<String, CommandHandler.CommandInterface> commands = new HashMap<>();

    public CommandHandler(HubEssentials pl) {
        this.plugin = pl;
        this.config = pl.getConfig();
        this.messages = pl.messagescfg;

    }

    //IMPORTANT: This is an interface, not a class.
    public interface CommandInterface {

        //Every time I make a command, I will use this same method.
        boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) throws InvalidPluginException, InvalidDescriptionException;

    }

        //Main command initialization
        public void init(String commandname) {

            if (config.getBoolean("modules-enable.vanish-cmd")) {
                commands.put("vanish",new VanishCmd(plugin));
                plugin.getCommand("vanish").setExecutor(this);
                plugin.getCommand("vanish").setTabCompleter(new TabCompleteHandler(this));

            } if (config.getBoolean("modules-enable.fly-cmd"))  {
                commands.put("fly",new FlyCmd(plugin));
                plugin.getCommand("fly").setExecutor(this);
                plugin.getCommand("fly").setTabCompleter(new TabCompleteHandler(this));

            } if ( config.getBoolean("msg-cmd") && config.getBoolean("socialspy-cmd")) {
                commands.put("socialspy",new SocialSpyCmd(plugin));
                plugin.getCommand("socialspy").setExecutor(this);
                plugin.getCommand("socialspy").setTabCompleter(new TabCompleteHandler(this));
            }

            commands.put("help",new Helpcmd(plugin));
            commands.put("reload",new ReloadCmd(plugin));
            commands.put("reset",new ResetCmd(plugin));
            commands.put(commandname,new MainCommand(plugin));
            plugin.getCommand(commandname).setExecutor(this);
            plugin.getCommand(commandname).setTabCompleter(new TabCompleteHandler(this));

        }

        //This will be used to check if a string exists or not.
        public boolean exists(String name) {

            //To actually check if the string exists, we will return the hashmap
            return commands.containsKey(name);
        }

        //Getter method for the Executor.
        public CommandInterface getExecutor(String name) {

            //Returns a command in the hashmap of the same name.
            return commands.get(name);
        }


        //This will be a template. All commands will have this in common.
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

            //For example, in each command, it will check if the sender is a player and if not, send an error message.
            if(sender instanceof Player) {
                List<String> l = new ArrayList<>(List.of("c"));
                switch (cmd.getName()) {
                    
                    case "fly" -> {
                        if (!sender.hasPermission("hubessentials.fly.command")) {
                            sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("noperms")));
                            return true;
                        }
                        try {
                            l.addAll(Arrays.asList(args));
                            getExecutor("fly").onCommand(sender, cmd, commandLabel, l.toArray(new String[args.length]));
                        } catch (InvalidPluginException | InvalidDescriptionException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    case "vanish" -> {
                        if (!sender.hasPermission("hubessentials.vanish.command")) {
                            sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("noperms")));
                            return true;

                        }try {
                            l.addAll(Arrays.asList(args));
                            getExecutor("vanish").onCommand(sender, cmd, commandLabel, l.toArray(new String[args.length]));

                        } catch (InvalidPluginException | InvalidDescriptionException e) {
                            e.printStackTrace();
                        }
                    }

                    case "socialspy" -> {
                        if (!sender.hasPermission("hubessentials.socialspy.command")) {
                            sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("noperms")));
                            return true;

                        }try {
                            l.addAll(Arrays.asList(args));
                            getExecutor("socialspy").onCommand(sender, cmd, commandLabel, l.toArray(new String[args.length]));

                        } catch (InvalidPluginException | InvalidDescriptionException e) {
                            e.printStackTrace();
                        }

                    }
                    
                    
                    case "hubessentials" -> {
                        //If there aren't any arguments, what is the command name going to be? For this example, we are going to call it /example.
                        //This means that all commands will have the base of /example.
                        if (args.length == 0) {
                            try {
                                getExecutor("hubessentials").onCommand(sender, cmd, commandLabel, args);
                            } catch (InvalidPluginException | InvalidDescriptionException e) {
                                e.printStackTrace();
                            }
                            return true;
                        } else {

                            //If that argument exists in our registration in the onEnable(); || !(sender.hasPermission())
                            if (exists(args[0]) && !(args[0].equalsIgnoreCase("hubessentials") || args[0].equalsIgnoreCase("hubess"))) {
                                //Get The executor with the name of args[0]. With our example, the name of the executor will be args because in
                                //the command /example args, args is our args[0].
                                if (!sender.hasPermission("hubessentials." + args[0] + ".command") && args[1].equals("help")) {
                                    sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("noperms")));
                                    return true;
                                }
                                try {
                                    getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
                                } catch (InvalidPluginException | InvalidDescriptionException e) {
                                    e.printStackTrace();
                                }
                            } else {

                                //We want to send a message to the sender if the command doesn't exist.
                                sender.sendMessage(plugin.sendmsg((Player) sender, messages.getString("unknown-command").replace("%cmd%", args[0])));
                            }
                        }
                    }
                }
            } else{
                if (!cmd.getName().equalsIgnoreCase("hubessentials")) {plugin.getLogger().log(Level.SEVERE, plugin.sendmsg(null, "Console cannot execute this command")); return true;}
                    if (args.length == 0) {
                        try {
                            getExecutor("hubessentials").onCommand(sender, cmd, commandLabel, args);
                        } catch (InvalidPluginException | InvalidDescriptionException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    if ((args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("vanish"))) {
                        try {
                            getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
                        } catch (InvalidPluginException | InvalidDescriptionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        plugin.getLogger().log(Level.SEVERE, plugin.sendmsg(null, "Console cannot execute this command"));
                    }
            }
            return true;
        }
}

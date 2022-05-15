package it.chiarchiaooo.hubessentials.commands;


import it.chiarchiaooo.hubessentials.HubEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;


public class Spawn implements CommandExecutor {

    private final FileConfiguration config;
    private final HubEssentials plugin;

    public Spawn(HubEssentials pl) {
        this.plugin =pl;
        this.config = pl.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {plugin.getLogger().log(Level.SEVERE, plugin.sendmsg(null,"Console cannot execute this command"));return true;}
        Location loc = new Location(Bukkit.getWorld(config.getString("spawn.coords.world")),config.getDouble("spawn.coords.x"),config.getDouble("spawn.coords.y"),config.getDouble("spawn.coords.z"),(float) config.getDouble("spawn.coords.yaw"),(float) config.getDouble("spawn.coords.pitch"));
        ((Player) sender).teleport(loc);
        return false;
    }
}

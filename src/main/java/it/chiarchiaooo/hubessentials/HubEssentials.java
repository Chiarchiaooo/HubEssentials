package it.chiarchiaooo.hubessentials;

import it.chiarchiaooo.hubessentials.Events.*;
import it.chiarchiaooo.hubessentials.Utils.CommandHandler;
import it.chiarchiaooo.hubessentials.Utils.JoinItems;
import it.chiarchiaooo.hubessentials.commands.*;
import it.chiarchiaooo.hubessentials.commands.subcommands.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public final class HubEssentials extends JavaPlugin {


    public final File messagefile = new File(getDataFolder(),"messages.yml");
    public final File rulesfile = new File(getDataFolder(),"rules.txt");
    public FileConfiguration messagescfg;


    public final List<UUID> socialspiedPls = new ArrayList<>();
    public final List<Player> vanishpl = new ArrayList<>();
    public final List<Player> hiddenpl = new ArrayList<>();
    public final List<UUID> flypl = new ArrayList<>();




    public String sendmsg (Player p,String s) {
        s = ChatColor.translateAlternateColorCodes('&',s.replace("%prefix%",getConfig().getString("prefix")));
        if (p == null) return s;
        s = s.replace("%player%", p.getName());
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") !=null) {
            return PlaceholderAPI.setPlaceholders(p, s);
        }
        return s;
    }


    public void setupCmds() {

        CommandHandler maincmd = new CommandHandler(this);
        maincmd.init("hubessentials");
        
        //single commands
        if (getConfig().getBoolean("modules-enable.selector-gui")) getCommand("menu").setExecutor(new Compass(this));
        if (getConfig().getBoolean("modules-enable.spawn-cmd"))    getCommand("spawn").setExecutor(new Spawn(this));
        if (getConfig().getBoolean("modules-enable.rules-cmd"))    getCommand("rules").setExecutor(new Rules(this));
        if (getConfig().getBoolean("modules-enable.msg-cmd"))      getCommand("msg").setExecutor(new Msg(this));
        if (getConfig().getBoolean("modules-enable.gamemodes-cmd")) {
            for (String s : Arrays.asList("c","s","sp","")) getCommand("gm"+s).setExecutor(new GamemodeCmd(this));
        }

        getLogger().info("Commands successfully registered");
    }

    public void setupEvents() {
        if (getConfig().getBoolean("modules-enable.cmd-block")) getServer().getPluginManager().registerEvents(new CmdEvent(this),this);
        if (getConfig().getBoolean("modules-enable.selector-gui")) getServer().getPluginManager().registerEvents(new GuiEvent(this),this);
        if (getConfig().getBoolean("modules-enable.chat")) getServer().getPluginManager().registerEvents(new ChatEvent(this),this);

        if (getConfig().getBoolean("modules-enable.join-leave")) {
            getServer().getPluginManager().registerEvents(new JoinEvent(this),this);
            getServer().getPluginManager().registerEvents(new QuitEvent(this),this);
            getServer().getPluginManager().registerEvents(new JoinItems(this), this);
            getServer().getPluginManager().registerEvents(new DropEvent(this), this);
            getServer().getPluginManager().registerEvents(new BowEvent(this), this);
            getServer().getPluginManager().registerEvents(new Hideothers(this), this);
        }
        getLogger().info("Events successfully registered");
    }

    public boolean setupconfig() {
        if (!new File(getDataFolder(),"config.yml").exists()) {
            getConfig().options().copyDefaults();
            saveDefaultConfig();
        }

        if (!messagefile.exists()) {
            saveResource(messagefile.getName(),false);
        }
        if (!rulesfile.exists()) {
            saveResource(rulesfile.getName(),false);
        }
        messagescfg = YamlConfiguration.loadConfiguration(messagefile);

        if (getConfig().getInt("config-version") != 1 || messagescfg.getInt("config-version") != 1 ) {
            getLogger().log(Level.SEVERE, "Invalid config version, disabling the plugin");
            onDisable();
            return true;
        }
        getLogger().info("Config succefully loaded");
        return false;
    }


    @Override
    public void onEnable() {
        final long x = System.currentTimeMillis();
        final List<String> s = Arrays.asList(
                "",
                "§7    _    _       _     ______                    _   _       _         ",
                "§7   | |  | |     | |   |  ____|                  | | (_)     | |        ",
                "§7   | |__| |_   _| |__ | |__   ___ ___  ___ _ __ | |_ _  __ _| |___     ",
                "§7   |  __  | | | | '_ \\|  __| / __/ __|/ _ | '_ \\| __| |/ _` | / __|  ",
                "§7   | |  | | |_| | |_) | |____\\__ \\__ |  __| | | | |_| | (_| | \\__ \\",
                "§7   |_|  |_|\\__,_|_.__/|______|___|___/\\___|_| |_|\\__|_|\\__,_|_|___/",
                "",
                "§6» Author: Chiarchiaooo",
                "§e» Version: "+getDescription().getVersion(),
                "§7» Running on "+Bukkit.getName() +" "+Bukkit.getVersion(),
                "",
                "§7----------------------------------------------"

        );
        for (String d : s) Bukkit.getConsoleSender().sendMessage(d);
        getLogger().info("Loading HubEssentials...");
        if (setupconfig()) return;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        setupEvents();
        setupCmds();
        Bukkit.getConsoleSender().sendMessage("[HubEssentials] "+ChatColor.GREEN+"Plugin successfully enabled (in "+(System.currentTimeMillis() - x)+"ms)");
        Bukkit.getConsoleSender().sendMessage("§7----------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("[HubEssentials] "+ChatColor.GOLD+"Remember to rate this plugin on spigotmc.org");
    }

    @Override
    public void onDisable() {
        long x = System.currentTimeMillis();
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        Bukkit.getConsoleSender().sendMessage("§7----------------------------------------------");
        getLogger().info("Disabling HubEssentials...");
        Bukkit.getConsoleSender().sendMessage("[HubEssentials] "+ChatColor.RED+"Plugin successfully disabled (in "+(System.currentTimeMillis() - x)+"ms)");
        Bukkit.getConsoleSender().sendMessage("§7----------------------------------------------");
    }
}

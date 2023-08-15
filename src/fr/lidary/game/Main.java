package fr.lidary.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.lidary.game.commands.PrivateGameCommand;
import fr.lidary.game.commands.PrivateGameInfoCommand;
import fr.lidary.game.commands.SlotCommand;
import fr.lidary.game.commands.WhitelistCommand;
import fr.lidary.game.discord.DiscordAlert;
import fr.lidary.game.discord.DiscordInitialisation;
import fr.lidary.game.listener.PlayerJoinListener;
import fr.lidary.game.manager.CustomWhitelistManager;

public class Main extends JavaPlugin {


    private static Main instance; 
    private DiscordInitialisation discordInit;
    private static DiscordAlert discordAlert;

    
    public static Main getInstance() {
        return instance;
    }

    
    public DiscordInitialisation getDiscordInit() {
        return discordInit;
    }
    
    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getLogger().info("Private Game activé !");
        CustomWhitelistManager.loadWhitelist();
        discordInit = new DiscordInitialisation();
        discordInit.connectToDiscord();
        discordAlert = new DiscordAlert(); 
        getCommand("private").setExecutor(new PrivateGameCommand()); 
        this.getCommand("slot").setExecutor(new SlotCommand());
        this.getCommand("privateinfo").setExecutor(new PrivateGameInfoCommand());
        this.getCommand("whitelist").setExecutor(new WhitelistCommand());
    }


    public static DiscordAlert getDiscordAlert() {
        return discordAlert; 
    }

    
    @Override
    public void onDisable() {
        this.getLogger().info("Private Game désactivé !");
    }
}

package fr.lidary.game.commands;

import fr.lidary.game.Main;
import fr.lidary.game.discord.DiscordAlert;
import fr.lidary.game.listener.ButtonClickListener;
import fr.lidary.game.manager.PrivateGameManager;
import fr.lidary.game.utils.GameInfo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.Message;

public class PrivateGameCommand implements CommandExecutor {

    public static List<String> participants = new ArrayList<>();
    public static Message embedMessage;

    private void scheduleTasks(CommandSender sender, long delayInSeconds, long delayForColorChangeInSeconds, long delayForRedAlertInSeconds) {
        long delayInTicks = delayInSeconds * 20;
        long delayForColorChangeInTicks = delayForColorChangeInSeconds * 20;
        long delayForRedAlertInTicks = delayForRedAlertInSeconds * 20;


        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            ButtonClickListener.updateEmbedColor(Color.YELLOW);
        }, delayForColorChangeInTicks);
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            PrivateGameManager.activateWhitelist();
            if (embedMessage != null) {
                embedMessage.delete().queue();
            }
            
            Main.getDiscordAlert().updateMainEmbed().thenAccept(updatedMessage -> {
                embedMessage = updatedMessage;
            });

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                if (embedMessage != null) {
                    embedMessage.delete().queue();
                }
            }, 10 * 60 * 20);
        }, delayInTicks);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
        	Main.getDiscordAlert().sendAlert();
            ButtonClickListener.updateEmbedColor2(Color.RED);
        }, delayForRedAlertInTicks);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§dVous n'avez pas la permission d'utiliser cette commande !");
            return true;
        }

        if (command.getName().equalsIgnoreCase("private") && args.length == 4 && args[0].equalsIgnoreCase("create")) {
            String hostName = args[1];
            int providedHour = Integer.parseInt(args[2]);
            int providedMinute = Integer.parseInt(args[3]);
            GameInfo.setHostName(hostName);
            GameInfo.setHour(providedHour);
            GameInfo.setMinute(providedMinute);
            if (sender instanceof Player) {
                Player player = (Player) sender;
                GameInfo.setPlayer(player.getName());
            }
            PrivateGameManager.sendDiscordMessage();
            ButtonClickListener.updateEmbed(sender, hostName);

            ZonedDateTime parisTime = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
            boolean isDaylightTime = parisTime.getZone().getRules().isDaylightSavings(parisTime.toInstant());
            if (isDaylightTime) {
                parisTime = parisTime.minusHours(1);
            }

            LocalTime now = parisTime.toLocalTime();
            LocalTime scheduledTime = LocalTime.of(providedHour, providedMinute);
            long delayInSeconds = ChronoUnit.SECONDS.between(now, scheduledTime);

            if (delayInSeconds < 0) {
                sender.sendMessage("§f[§d+§f] §dL'heure fournie est déjà passée !");
                return true;
            }

            long delayForColorChangeInSeconds = delayInSeconds - (10 * 60);
            long delayForRedAlertInSeconds = delayInSeconds - (5 * 60);

            scheduleTasks(sender, delayInSeconds, delayForColorChangeInSeconds, delayForRedAlertInSeconds);

            sender.sendMessage("§f[§d+§f] §dLe message Discord a été envoyé et la whitelist sera activée à l'heure désignée !");
            return true;
        }
        
        return false; 
    }
}

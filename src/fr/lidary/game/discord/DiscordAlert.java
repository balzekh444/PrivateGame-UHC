package fr.lidary.game.discord;

import fr.lidary.game.Main;
import fr.lidary.game.commands.PrivateGameCommand;
import fr.lidary.game.utils.GameInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import java.awt.Color;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.*;
import java.time.temporal.ChronoUnit;


public class DiscordAlert {

	 private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	
	public void sendAlert() {
	    for (String participant : PrivateGameCommand.participants) {
	        User user = Main.getInstance().getDiscordInit().getJDA().getUserById(participant);
	        if (user != null) {
	            user.openPrivateChannel().queue(privateChannel -> {
	                String playerName = GameInfo.getPlayer();
	                EmbedBuilder embed = new EmbedBuilder();
	                embed.setTitle("**Alerte Partie**");
	                embed.setDescription("> Bonjour, **" + playerName + "**. La partie commencera dans 5 minutes. Préparez-vous !");
	                
	                embed.setColor(Color.RED);
	                embed.setThumbnail("https://minotar.net/avatar/" + playerName);
	                privateChannel.sendMessageEmbeds(embed.build()).queue();
	            });
	        }
	    }
	}


	public CompletableFuture<Message> updateMainEmbed() {
	    CompletableFuture<Message> future = new CompletableFuture<>();

	    if (PrivateGameCommand.embedMessage != null) {
	        EmbedBuilder embed = new EmbedBuilder();
	        embed.setTitle("**Inscription Terminée**");
	        embed.setDescription("> **Merci d'avoir participé à la partie !**");
	        embed.setColor(Color.GRAY);

	        PrivateGameCommand.embedMessage.editMessageEmbeds(embed.build()).queue(updatedMessage -> {
	            updatedMessage.delete().queueAfter(10, TimeUnit.MINUTES);
	            future.complete(updatedMessage);
	        }, throwable -> {
	            future.completeExceptionally(throwable);
	        });
	    } else {
	        future.complete(null);
	    }

	    return future;
	}
	   
	   public void scheduleGameStartUpdate() {

		    LocalDateTime now = LocalDateTime.now();
		    LocalDateTime gameStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(GameInfo.getHour(), GameInfo.getMinute()));
		    if (now.isAfter(gameStartTime)) {
		        gameStartTime = gameStartTime.plusDays(1);
		    }
		    long delay = now.until(gameStartTime, ChronoUnit.SECONDS);

		    scheduler.schedule(this::updateMainEmbed, delay, TimeUnit.SECONDS);
		}

}

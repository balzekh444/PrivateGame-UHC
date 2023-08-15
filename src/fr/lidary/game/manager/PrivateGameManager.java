package fr.lidary.game.manager;

import fr.lidary.game.Main;
import fr.lidary.game.utils.EmbedUtil;
import fr.lidary.game.utils.GameInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivateGameManager {

    private static final List<String> participants = new ArrayList<>();
    private static final Map<String, String> discordToMinecraft = new HashMap<>();
    private static boolean isWhitelisted = false;
    private static Message embedMessage;

    public static List<String> getParticipants() {
        return participants;
    }

    public static void setEmbedMessage(Message message) {
        embedMessage = message;
    }

    public static Message getEmbedMessage() {
        return embedMessage;
    }

    public static void associatePseudo(String discordId, String minecraftPseudo) {
        discordToMinecraft.put(discordId, minecraftPseudo);
    }

    public static String getPseudo(String discordId) {
        return discordToMinecraft.get(discordId);
    }

    public static void removePseudo(String discordId) {
        discordToMinecraft.remove(discordId);
    }

    public static void sendDiscordMessage() {
    	TextChannel channel = Main.getInstance().getDiscordInit().getJDA().getTextChannelById(Main.getInstance().getDiscordInit().getDiscordChannelId());
        Message[] embedMessageHolder = new Message[1];

        int gameHour = GameInfo.getHour();
        int gameMinute = GameInfo.getMinute();
        int maxSlots = GameInfo.getMaxSlots();
        String playerName = GameInfo.getPlayer();

        MessageEmbed gameEmbed = EmbedUtil.createGameEmbed(GameInfo.getHostName(), gameHour, gameMinute, maxSlots, playerName, participants);

        Button btnInscription = Button.secondary("inscription", "S'inscrire");
        Button btnDesinscription = Button.danger("desinscription", "Se désinscrire");

        channel.sendMessage(gameEmbed).setActionRow(btnInscription, btnDesinscription).queue(message -> {
            embedMessageHolder[0] = message;
            setEmbedMessage(embedMessageHolder[0]);
        });
    }

    public static void activateWhitelist() {
        CustomWhitelistManager.setWhitelisted(true);

        for (String playerName : participants) {
            Bukkit.getLogger().info("Ajout du joueur " + playerName + " à la whitelist.");
            CustomWhitelistManager.addPlayerToWhitelist(playerName);
        }

        isWhitelisted = true;
    }

    public static boolean isWhitelisted() {
        return isWhitelisted;
    }
}

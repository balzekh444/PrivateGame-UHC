package fr.lidary.game.utils;

import java.util.List;

import fr.lidary.game.manager.PrivateGameManager;
import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedUpdateFactory {
	
	private static List<String> getParticipants() {
	    return PrivateGameManager.getParticipants();
	}

    public static EmbedBuilder createUpdatedEmbed() {
        EmbedBuilder updatedEmbed = new EmbedBuilder();
        int gameHour = GameInfo.getHour();
        int gameMinute = GameInfo.getMinute();
        int maxSlots = GameInfo.getMaxSlots();

        updatedEmbed.setTitle("```Lidary - " + GameInfo.getHostName() + "```");
        updatedEmbed.setDescription(
                "\n" +
                "\n" +
                "> ```Information de la Partie``` \n" +
                "> ```L'UHC, ou Ultra Hardcore, est une variante de Minecraft où les joueurs sont confrontés à un défi supplémentaire: la santé ne se régénère pas automatiquement. Parmi les nombreuses adaptations de ce mode de jeu, on trouve les versions DemonSlayer UHC, Naruto UHC et SpiderVerseUHC. Chacune de ces versions intègre des éléments, des mécaniques et des défis inspirés des univers de Demon Slayer, Naruto et Spider-Verse respectivement. Pour cette partie, le mode spécifié dans le titre du message est celui que nous allons explorer et jouer. ``` \n " 
                + "\n"
                + "> ``` Préparez-vous pour une expérience de jeu unique.``` \n" +
                "\n" +
                "> ``` La game est prévue pour" + gameHour + "h" + (gameMinute < 10 ? "0" + gameMinute : gameMinute) + ". ``` \n" +
                "> ``` Slots max : " + maxSlots + " ``` \n"
                + "\n"
            );
        String playerName = GameInfo.getPlayer();
        if (playerName != null && !playerName.isEmpty()) {
            updatedEmbed.setFooter("Host - " + playerName, "https://minotar.net/avatar/" + playerName);
        } else {
            updatedEmbed.setFooter("Host - Unknow");
        }
        updatedEmbed.setThumbnail("https://minotar.net/avatar/" + playerName);
        updatedEmbed.setColor(0x9933FF);
        updatedEmbed.setImage("https://cdn.discordapp.com/attachments/1138817988997435394/1138818047763824701/lidary_banner_v2.jpg");
        
        StringBuilder participantList = new StringBuilder();
        for (String player : getParticipants()) {
            if (player != null && !player.trim().isEmpty()) {
                participantList.append(">``` - ").append(player).append("``` \n");
            }
        }

        if (participantList.length() == 0) {
            participantList.append("> ```Aucun participant pour le moment.```");
        }

        updatedEmbed.addField("> ```Participants``` : ", participantList.toString(), false);
        updatedEmbed.addField("", "> ```Si vous avez des questions, contactez un admin```", false);

        return updatedEmbed;
    }
}


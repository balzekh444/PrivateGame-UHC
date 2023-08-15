package fr.lidary.game.utils;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class EmbedUtil {

    public static MessageEmbed createGameEmbed(String hostName, int gameHour, int gameMinute, int maxSlots, String playerName, List<String> participants) {
        EmbedBuilder embed = new EmbedBuilder();
        
        embed.setTitle("```Lidary - " + GameInfo.getHostName() + "```");
        embed.setDescription(
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
        embed.setColor(0x9933FF);
        embed.setImage("https://cdn.discordapp.com/attachments/1138817988997435394/1138818047763824701/lidary_banner_v2.jpg");
        if (playerName != null && !playerName.isEmpty()) {
            embed.setFooter("Host - " + playerName, "https://minotar.net/avatar//" + playerName);
        } else {
            embed.setFooter("Host - Unknow");
        }
        embed.setThumbnail("https://minotar.net/avatar/" + playerName);

        StringBuilder participantList = new StringBuilder();
        for (String pName : participants) {
            participantList.append("> ``` - ").append(pName).append("``` \n");
        }
        if (participantList.length() == 0) {
            participantList.append("> ```Aucun participant pour le moment. ```");
        }
        embed.addField("> ```Participants``` : ", participantList.toString(), false);
        embed.addField("", "> ```Si vous avez des questions, contactez un admin```", false);

        return embed.build();
    }
}

package fr.lidary.game.utils;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedMessageFactory {

    public static EmbedBuilder createSuccessEmbed(String pseudo) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0x00FF00);
        embed.setTitle("> **" + pseudo + "**, vous avez été inscrit avec succès !");
        embed.setDescription("> **Merci d'avoir fourni votre pseudo pour l'inscription.**");
        embed.setThumbnail("https://minotar.net/avatar/" + pseudo);
        return embed;
    }

    public static EmbedBuilder createAlreadyRegisteredEmbed(String pseudo) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0xFF0000);
        embed.setTitle("**Déjà inscrit !**");
        embed.setDescription("> Vous êtes déjà inscrit avec le pseudo : " + pseudo);
        return embed;
    }

    public static EmbedBuilder createInvalidPseudoEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0xFF0000);
        embed.setTitle("**Erreur d'inscription**");
        embed.setDescription("> Désolé, ce pseudo n'est pas valide. Veuillez réessayer.");
        return embed;
    }

    public static EmbedBuilder createFullRegistrationEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0xFF0000);
        embed.setTitle("**Inscription complète !**");
        embed.setDescription("> Désolé, la limite de participants a été atteinte.");
        return embed;
    }
    
}


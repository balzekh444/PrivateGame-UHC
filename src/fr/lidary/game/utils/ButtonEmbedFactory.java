package fr.lidary.game.utils;

import net.dv8tion.jda.api.EmbedBuilder;

public class ButtonEmbedFactory {

    public static EmbedBuilder alreadyRegisteredEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0xFF0000);
        embed.setTitle("**Déjà inscrit !**");
        embed.setDescription("> Vous êtes déjà inscrit à la partie privée. Si vous souhaitez vous désinscrire, veuillez utiliser le bouton approprié.");
        return embed;
    }

    public static EmbedBuilder replyEmbedForInscription() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0x9933FF);
        embed.setDescription("> **Vérifiez vos DM pour compléter votre inscription !**");
        return embed;
    }

    public static EmbedBuilder privateChannelInscriptionEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0x9933FF);
        embed.setTitle("**Inscription à la partie privée**");
        embed.setDescription("> **Veuillez entrer votre pseudo in-game pour vous inscrire à la partie privée.**");
        return embed;
    }

    public static EmbedBuilder successfulDesinscriptionEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0x00FF00);
        embed.setDescription("> **Vous vous êtes désinscrit avec succès !**");
        return embed;
    }
    
    public static EmbedBuilder createPseudoAlreadyTakenEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0xFF0000);
        embed.setTitle("**Erreur d'inscription**");
        embed.setDescription("> Ce Pseudo est déjà pris.");
        return embed;
    }

    
}

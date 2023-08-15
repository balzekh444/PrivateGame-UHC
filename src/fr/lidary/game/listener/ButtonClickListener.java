package fr.lidary.game.listener;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import fr.lidary.game.commands.SlotCommand;
import fr.lidary.game.manager.PrivateGameManager;
import fr.lidary.game.utils.ButtonEmbedFactory;
import fr.lidary.game.utils.EmbedMessageFactory;
import fr.lidary.game.utils.EmbedUpdateFactory;
import fr.lidary.game.utils.GameInfo;

import java.awt.Color;
import java.util.List;

import org.bukkit.command.CommandSender;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class ButtonClickListener extends ListenerAdapter {
    
	private static List<String> getParticipants() {
	    return PrivateGameManager.getParticipants();
	}

	private static Message getEmbedMessage() {
	    return PrivateGameManager.getEmbedMessage();
	}

	public static void updateEmbed(CommandSender sender, String hostName) {
	    Message embedMessage = getEmbedMessage();

	    if (embedMessage == null) {
	        System.out.println("embedMessage est nul !");
	        return; 
	    }

	    EmbedBuilder updatedEmbed = EmbedUpdateFactory.createUpdatedEmbed();
	    embedMessage.editMessage(updatedEmbed.build()).queue();
	}

	public static void updateEmbedColor(Color color) {
	    Message embedMessage = getEmbedMessage();

	    if (embedMessage == null) {
	        System.out.println("embedMessage est nul !");
	        return; 
	    }

	    EmbedBuilder updatedEmbed = EmbedUpdateFactory.createUpdatedEmbed();
	    updatedEmbed.setColor(color);  
	    embedMessage.editMessage(updatedEmbed.build()).queue();
	}
	
	public static void updateEmbedColor2(Color color) {
	    Message embedMessage = getEmbedMessage();

	    if (embedMessage == null) {
	        System.out.println("embedMessage est nul !");
	        return; 
	    }

	    EmbedBuilder updatedEmbed = EmbedUpdateFactory.createUpdatedEmbed();
	    updatedEmbed.setColor(color);
	    embedMessage.editMessage(updatedEmbed.build()).queue();
	}

	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
	    String messageContent = event.getMessage().getContentRaw();
	    User user = event.getAuthor();

	    EmbedBuilder embedBuilder;

	    if (PrivateGameManager.getPseudo(user.getId()) != null) {
	        embedBuilder = EmbedMessageFactory.createAlreadyRegisteredEmbed(messageContent);
	    } else if (isValidPseudo(messageContent)) {
	        if (getParticipants().contains(messageContent)) {
	        	embedBuilder = ButtonEmbedFactory.createPseudoAlreadyTakenEmbed();
	        } else if (getParticipants().size() < SlotCommand.maxParticipants) {
	            getParticipants().add(messageContent);
	            PrivateGameManager.associatePseudo(user.getId(), messageContent);
	            updateEmbed(null, messageContent);
	            embedBuilder = EmbedMessageFactory.createSuccessEmbed(messageContent);
	        } else {
	            embedBuilder = EmbedMessageFactory.createFullRegistrationEmbed();
	        }
	    } else {
	        embedBuilder = EmbedMessageFactory.createInvalidPseudoEmbed();
	    }

	    user.openPrivateChannel().queue((channel) -> {
	        channel.sendMessageEmbeds(embedBuilder.build()).queue();
	    });
	}




	
    private boolean isValidPseudo(String pseudo) {
        return !pseudo.contains(" ") && !pseudo.contains("\n"); 
    }


	public void onButtonClick(ButtonClickEvent event) {
	    String buttonId = event.getComponentId();
	    User user = event.getUser();

	    switch (buttonId) {
	    case "inscription":
	        if (PrivateGameManager.getPseudo(user.getId()) != null) {
	            event.replyEmbeds(ButtonEmbedFactory.alreadyRegisteredEmbed().build())
	                 .setEphemeral(true)
	                 .queue();
	            return;
	        }

	        event.replyEmbeds(ButtonEmbedFactory.replyEmbedForInscription().build())
	             .setEphemeral(true)
	             .queue();

	        user.openPrivateChannel().queue((channel) -> {
	            channel.sendMessageEmbeds(ButtonEmbedFactory.privateChannelInscriptionEmbed().build()).queue();
	        });
	        break;

	    case "desinscription":
	        String inGameName = PrivateGameManager.getPseudo(user.getId());
	        if (inGameName != null && getParticipants().contains(inGameName)) {
	            getParticipants().remove(inGameName);
	            PrivateGameManager.removePseudo(user.getId());
	            updateEmbed(null, inGameName);
	            
	            event.replyEmbeds(ButtonEmbedFactory.successfulDesinscriptionEmbed().build())
	                 .setEphemeral(true)
	                 .queue();
	                 
	            event.editButton(Button.secondary("inscription", "S'inscrire")).queue();
	        }
	        break;
	    }
	}

}
 	







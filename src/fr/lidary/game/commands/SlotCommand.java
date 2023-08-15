package fr.lidary.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.lidary.game.utils.GameInfo;

public class SlotCommand implements CommandExecutor {

	public static int maxParticipants = 40;

	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("slot")) {
            if (sender.isOp()) {
                if(args.length == 1) {
                	try {
                	    maxParticipants = Integer.parseInt(args[0]);
                	    sender.sendMessage("§dNombre maximum de participants défini à " + maxParticipants);
                	    GameInfo.setMaxSlots(maxParticipants); 
                	    return true;
                	} catch (NumberFormatException e) {
                	    sender.sendMessage("§cVeuillez entrer un nombre valide.");
                	    return true;
                	}
                } else {
                    sender.sendMessage("§cUsage correct: /slot <nombre>");
                    return true;
                }
            } else {
                sender.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
                return true;
            }
        }
        return false;  
    }

}

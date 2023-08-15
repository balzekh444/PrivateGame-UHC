package fr.lidary.game.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.lidary.game.manager.CustomWhitelistManager;

public class WhitelistCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (!sender.isOp()) {
    	    sender.sendMessage("§dVous n'avez pas la permission d'utiliser cette commande !");
    	    return true;
    	}
        if (args.length == 0) {
            sender.sendMessage("Utilisez /whitelist [on/off/list/add/remove] [playerName]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "on":
                CustomWhitelistManager.setWhitelisted(true);
                sender.sendMessage("Whitelist activée.");
                break;
            case "off":
                CustomWhitelistManager.setWhitelisted(false);
                sender.sendMessage("Whitelist désactivée.");
                break;
            case "list":
                sender.sendMessage("Joueurs en whitelist: " + String.join(", ", CustomWhitelistManager.getWhitelistedPlayers()));
                break;
            case "add":
                if (args.length > 1) {
                    CustomWhitelistManager.addPlayerToWhitelist(args[1]);
                    sender.sendMessage(args[1] + " ajouté à la whitelist.");
                } else {
                    sender.sendMessage("Précisez le nom du joueur.");
                }
                break;
            case "remove":
                if (args.length > 1) {
                    CustomWhitelistManager.removePlayerFromWhitelist(args[1]);
                    sender.sendMessage(args[1] + " retiré de la whitelist.");
                } else {
                    sender.sendMessage("Précisez le nom du joueur.");
                }
                break;
            default:
                sender.sendMessage("Commande inconnue.");
        }
        return true;
    }
}

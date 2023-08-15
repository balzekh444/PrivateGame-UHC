package fr.lidary.game.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.lidary.game.manager.PrivateGameManager;

public class PrivateGameInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("privateinfo")) {
            if (!sender.isOp()) {
                sender.sendMessage("§dVous n'avez pas la permission d'utiliser cette commande !");
                return true;
            }
        if (!PrivateGameManager.isWhitelisted()) {
            sender.sendMessage("§dLa partie n'a pas encore commencé. Veuillez attendre que la whitelist soit activée.");
            return true;
        }            
            if (PrivateGameCommand.participants.isEmpty()) {
                sender.sendMessage("§dIl n'y a actuellement aucun participant inscrit pour la partie privée.");
                return true;
            }
            
            sender.sendMessage("§dListe des participants à la partie privée :");
            for (String participant : PrivateGameCommand.participants) {
                Player player = Bukkit.getPlayer(participant);
                boolean isOnline = (player != null && player.isOnline());
                boolean isWhitelisted = isPlayerWhitelisted(participant);
                
                sender.sendMessage("§f- " + participant + (isOnline ? " §a[En ligne]" : " §c[Hors ligne]") + (isWhitelisted ? " §d[Whitelisté]" : " §7[Non-whitelisté]"));
            }

            return true;
        }
        return false;
    }

    private boolean isPlayerWhitelisted(String playerName) {
        return Bukkit.getOfflinePlayer(playerName).isWhitelisted();
    }
}

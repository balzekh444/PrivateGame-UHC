package fr.lidary.game.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import fr.lidary.game.manager.CustomWhitelistManager;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (player.isOp()) {
            return;
        }

        if (!CustomWhitelistManager.isPlayerWhitelisted(playerName)) {
            player.kickPlayer("§dVous n'êtes pas sur la whitelist !");
        }
    }
}
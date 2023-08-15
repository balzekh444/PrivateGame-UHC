package fr.lidary.game.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomWhitelistManager {
    private static boolean isWhitelistEnabled = false;
    private static final File WHITELIST_FILE = new File("plugins/PrivateUHC/whitelist.txt");
    private static final Set<String> whitelistedPlayers = new HashSet<>();

    public static boolean isWhitelisted() {
        return isWhitelistEnabled;
    }

    public static void setWhitelisted(boolean enabled) {
        isWhitelistEnabled = enabled;
    }

    public static boolean isPlayerWhitelisted(String playerName) {
        return whitelistedPlayers.contains(playerName.toLowerCase()); 
    }

    public static void addPlayerToWhitelist(String playerName) {
        whitelistedPlayers.add(playerName.toLowerCase());
        saveWhitelist();
    }

    public static void removePlayerFromWhitelist(String playerName) {
        whitelistedPlayers.remove(playerName.toLowerCase());
        saveWhitelist();
    }

    public static Set<String> getWhitelistedPlayers() {
        return whitelistedPlayers;
    }

    public static void loadWhitelist() {
        if (!WHITELIST_FILE.exists()) {
            try {
                WHITELIST_FILE.getParentFile().mkdirs(); 
                WHITELIST_FILE.createNewFile(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            List<String> players = Files.readAllLines(WHITELIST_FILE.toPath());
            whitelistedPlayers.clear();
            whitelistedPlayers.addAll(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveWhitelist() {
        try {
            Files.write(WHITELIST_FILE.toPath(), whitelistedPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

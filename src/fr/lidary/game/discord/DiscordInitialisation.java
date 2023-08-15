package fr.lidary.game.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.OnlineStatus;
import javax.security.auth.login.LoginException;

import fr.lidary.game.listener.ButtonClickListener;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Map;

public class DiscordInitialisation {

    private JDA jda;
    
    private static final File PRIVATE_UHC_DIRECTORY = new File("plugins/PrivateUHC");
    private static final File CONFIG_FILE = new File(PRIVATE_UHC_DIRECTORY, "config.yml");

    public DiscordInitialisation() {
        if (!PRIVATE_UHC_DIRECTORY.exists()) {
            PRIVATE_UHC_DIRECTORY.mkdir();
        }
        
        if (!CONFIG_FILE.exists()) {
            try {
                CONFIG_FILE.createNewFile();
                FileWriter writer = new FileWriter(CONFIG_FILE);
                writer.write("discord:\n");
                writer.write("  token: 'VOTRE_TOKEN_ICI'\n");
                writer.write("  channelid: 'ID_DU_CHANNEL_ICI'\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getDiscordChannelId() {
        try {
            Yaml yaml = new Yaml();
            Object configObj = yaml.load(new FileInputStream(CONFIG_FILE));
            
            if(!(configObj instanceof Map)) {
                System.err.println("Erreur dans le fichier de configuration. Vérifiez son contenu.");
                return null;
            }
            
            Map<String, Object> config = (Map<String, Object>) configObj;
            if(config.containsKey("discord") && config.get("discord") instanceof Map) {
                Map<String, Object> discordConfig = (Map<String, Object>) config.get("discord");
                return (String) discordConfig.get("channelid");
            } else {
                System.err.println("Section 'discord' manquante ou mal configurée dans le fichier de configuration.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public JDA getJDA() {
        return jda;
    }

    private String getDiscordToken() {
        try {
            Yaml yaml = new Yaml();
            Object configObj = yaml.load(new FileInputStream(CONFIG_FILE));
            
            if(!(configObj instanceof Map)) {
                System.err.println("Erreur dans le fichier de configuration. Vérifiez son contenu.");
                return null;
            }
            
            Map<String, Object> config = (Map<String, Object>) configObj;
            if(config.containsKey("discord") && config.get("discord") instanceof Map) {
                Map<String, Object> discordConfig = (Map<String, Object>) config.get("discord");
                return (String) discordConfig.get("token");
            } else {
                System.err.println("Section 'discord' manquante ou mal configurée dans le fichier de configuration.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void connectToDiscord() {
        try {
            String token = getDiscordToken();
            if (token == null || token.isEmpty()) {
                System.err.println("Erreur lors de la récupération du token Discord.");
                return;
            }
            
            JDABuilder builder = JDABuilder.createDefault(token);
            builder.setStatus(OnlineStatus.ONLINE);
            builder.setActivity(Activity.playing("lidary.fr"));         
            this.jda = builder.build();        
            jda.addEventListener(new ButtonClickListener());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}

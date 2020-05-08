package net.waterraid.PKLB.ConfigIO;

import net.waterraid.PKLB.Main;
import net.waterraid.PKLB.StatManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class ConfigIO {
    public static File CONFIGFILE = new File(Main.getFolder() + File.separator + "config.yml");

    public static void load() {
        if (!ConfigIO.CONFIGFILE.exists()){
            Main.getInstance().saveResource("config.yml", false);
        }
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(ConfigIO.CONFIGFILE);
        } catch (IOException e) {
            Main.fatalDisable("Error Loading Config File for PKLB");
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            Main.fatalDisable("Error Loading Configuration File for PKLB");
        }
        StatManager.activetime = ((long)config.getInt("activetime"))*24L*60L*60L*1000L;
        StatManager.refreshtime = config.getLong("refreshtime");
        if (StatManager.activetime <= 0){
            StatManager.activetime = 30L*24L*60L*60L*1000L;
        }
        if (StatManager.refreshtime < 20) {
            StatManager.refreshtime = 20;
        }
        config.getKeys(false).forEach((s)->{
            StatManager.activeplayers.put(UUID.fromString(s), config.getLong(s+".time"));
        });


    }
    public static void save() {
        if (!ConfigIO.CONFIGFILE.exists()){
            Main.getInstance().saveResource("config.yml", false);
        }
        YamlConfiguration config = new YamlConfiguration();
        for(Map.Entry<UUID, Long> s:StatManager.activeplayers.entrySet()) {
            config.set(s.getKey().toString()+".time", s.getValue());
        }
        try {
            config.save(ConfigIO.CONFIGFILE);
        } catch (IOException e) {
            Main.fatalDisable("Error Loading Config File for PKLB");
            e.printStackTrace();
        }


    }
}

package net.waterraid.PKLB.ConfigIO;

import net.waterraid.PKLB.Main;
import net.waterraid.PKLB.StatManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ConfigIO {
    public static File CONFIGFILE;
    public static File DATAFILE;

    public static void load() {
        if (!CONFIGFILE.exists()) {
            Main.getInstance().saveResource("config.yml", false);
        }
        if (!DATAFILE.exists()) {
            Main.getInstance().saveResource("data.yml", false);
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
        StatManager.refreshtime = config.getLong("refreshtime");
        if (StatManager.refreshtime < 20) {
            StatManager.refreshtime = 20;
        }
        StatManager.activetime = config.getLong("activetime");
        YamlConfiguration datum = new YamlConfiguration();
        try {
            datum.load(DATAFILE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        datum.getKeys(false).forEach(
                (entry)->StatManager.activeplayers.put(UUID.fromString(entry), datum.getLong(entry))
        );


    }

    public static void save() {
        if (!ConfigIO.DATAFILE.exists()) {
            Main.getInstance().saveResource("data.yml", false);
        }
        YamlConfiguration datum = new YamlConfiguration();
        StatManager.activeplayers.entrySet().forEach((entry)->datum.set(entry.getKey().toString(), entry.getValue()));

        try {
            datum.save(ConfigIO.DATAFILE);
        } catch (IOException e) {
            Main.fatalDisable("Error Loading Config File for PKLB");
            e.printStackTrace();
        }


    }
}

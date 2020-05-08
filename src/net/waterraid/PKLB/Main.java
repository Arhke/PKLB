package net.waterraid.PKLB;

import net.waterraid.PKLB.ConfigIO.ConfigIO;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    static JavaPlugin _instance;
    static File _dataFolder;
    @Override
    public void onEnable() {
        _instance = this;
        _dataFolder = this.getDataFolder();
        ConfigIO.load();
        this.getCommand("lb").setExecutor(new Commands());
        if(Bukkit.getPluginManager().getPlugin("ProjectKorra")==null){
            Main.fatalError("This Plugin Depends on Project Korra, please install project korra");
        }
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PlaceHolderHook(this).register();
        }
        Bukkit.getOnlinePlayers().forEach((player) -> StatManager.activeplayers.put(player.getUniqueId(), System.currentTimeMillis()));
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new StatManager(), 1L,StatManager.refreshtime);
        Bukkit.getScheduler().runTaskTimer(this, ()->{
            for(Map.Entry<UUID, Long> s:StatManager.activeplayers.entrySet()) {
                if (System.currentTimeMillis() - s.getValue() > StatManager.activetime) {
                    StatManager.activeplayers.remove(s.getKey());
                }
            }
        },1L, 10L);
    }

    @Override
    public void onDisable(){
        ConfigIO.save();
    }
    public static JavaPlugin getInstance(){
        return _instance;
    }
    public static File getFolder(){
        return _dataFolder;
    }
    public static void fatalError(String errorMsg) {
        Bukkit.getLogger().severe(errorMsg);
    }
    public static void fatalDisable(String errorMsg){
        Bukkit.getLogger().severe(errorMsg);
        Bukkit.getPluginManager().disablePlugin(Main.getInstance());
    }
    public static Logger logger(){
        return Bukkit.getLogger();
    }
}

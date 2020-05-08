package net.waterraid.PKLB;


import com.projectkorra.projectkorra.Manager;
import com.projectkorra.projectkorra.util.Statistic;
import com.projectkorra.projectkorra.util.StatisticsManager;
import com.projectkorra.projectkorra.util.StatisticsMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

public class StatManager implements Runnable, Listener {
    public static Map<UUID, Long> activeplayers = new HashMap<>();
    public static Map<String, Integer> idconvert;
    public static long activetime=30*24*60*60;
    public static long refreshtime = 50;

    @Override
    public void run() {
        calculate();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        activeplayers.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    public void calculate() {
        Map<Integer, String> map = Manager.getManager(StatisticsManager.class).getKeysById();
        int max = max(map.keySet())+1;
        List<List<StatSet>> statmap = new ArrayList<>();
        List<StatSet> levelmap = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            statmap.add(new ArrayList<>());
        }
        activeplayers.entrySet().forEach((entry) -> {
            UUID id = entry.getKey();
            Manager.getManager(StatisticsManager.class).getStatisticsMap(id).entrySet().forEach((Map.Entry<Integer, Long> a) -> {
                statmap.get(a.getKey()).add(new StatSet(id, a.getValue()));
            });
            levelmap.add(new StatSet(id, StatisticsMethods.getStatisticTotal(id, Statistic.ABILITY_LEVEL)));
        });
        for (int k = 0; k < statmap.size(); k++) {
            List<StatSet> statSets = statmap.get(k);
            String ret = "";
            for (int i = 1; i <= 10 && statSets.size() != 0; i++) {
                int maxIndex = 0;
                for (int index = 0; index < statSets.size(); index++) {
                    if (statSets.get(index).getStat() > statSets.get(maxIndex).getStat()) {
                        maxIndex = index;
                    }

                }
                StatSet ss = statSets.remove(maxIndex);
                if (ret.length() == 0) {
                    ret = "" + ChatColor.GOLD + i + ": " + Bukkit.getOfflinePlayer(ss.getID()).getName() + " " + ss.getStat();
                } else {
                    ret = ret + "\n" + ChatColor.GOLD + i + ": " + Bukkit.getOfflinePlayer(ss.getID()).getName() + " " + ss.getStat();
                }
            }
            Commands.LEADERBOARD.put(k,ret);
        }
        String ret = "";
        for (int i = 1; i <= 10 && levelmap.size() != 0; i++) {
            int maxIndex = 0;
            for (int index = 0; index < levelmap.size(); index++) {
                if (levelmap.get(index).getStat() > levelmap.get(maxIndex).getStat()) {
                    maxIndex = index;
                }

            }
            StatSet ss = levelmap.remove(maxIndex);
            if (ret.length() == 0) {
                ret = "" + ChatColor.GOLD + i + ": " + Bukkit.getOfflinePlayer(ss.getID()).getName() + " " + ss.getStat();
            } else {
                ret = ret + "\n" + ChatColor.GOLD + i + ": " + Bukkit.getOfflinePlayer(ss.getID()).getName() + " " + ss.getStat();
            }
        }
        Commands.TotalLevel = ret;
    }

    public int max(Collection<Integer> list) {
        int ret = 0;
        for (int i : list) {
            ret = Math.max(ret, i);
        }
        return ret;
    }

}

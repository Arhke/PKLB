package net.waterraid.PKLB;

import com.projectkorra.projectkorra.Manager;
import com.projectkorra.projectkorra.util.StatisticsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {
    public static String TotalLevel = new String();
    public static Map<Integer, String> LEADERBOARD = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(StatManager.idconvert == null){
            StatManager.idconvert = Manager.getManager(StatisticsManager.class).getKeysByName();
        }
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("level")){
                commandSender.sendMessage(ChatColor.GOLD + "===========<"+ChatColor.AQUA+"Total Level LeaderBoard" + ChatColor.GOLD+">===========\n" +TotalLevel);
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("level")) {
                int id = StatManager.idconvert.getOrDefault("AbilityLevel_"+args[1],-1);
                if (id == -1){
                    commandSender.sendMessage(ChatColor.RED+"Not a Valid Ability Parameter (It is Case Sensitive)");
                }else {
                    commandSender.sendMessage(ChatColor.GOLD + "===========<" + ChatColor.AQUA + "Ability Level LeaderBoard" + ChatColor.GOLD + ">===========\n" + LEADERBOARD.getOrDefault(id, ChatColor.GOLD + "No LeaderBoard"));
                }return true;
            }else if (args[0].equalsIgnoreCase("uses")) {
                int id = StatManager.idconvert.getOrDefault("AbilityUses_"+args[1],-1);
                if (id == -1){
                    commandSender.sendMessage(ChatColor.RED+"Not a Valid Ability Parameter (It is Case Sensitive)");
                }else {
                    commandSender.sendMessage(ChatColor.GOLD + "===========<" + ChatColor.AQUA + args[1]+" Use LeaderBoard" + ChatColor.GOLD + ">===========\n" + LEADERBOARD.getOrDefault(id, ChatColor.GOLD + "No LeaderBoard"));
                }return true;
            }
        }else {
            commandSender.sendMessage(ChatColor.YELLOW + "/leaderboard level");
            commandSender.sendMessage(ChatColor.YELLOW + "/leaderboard level <Ability>");
            commandSender.sendMessage(ChatColor.YELLOW + "/leaderboard uses <Ability>");
            return true;
        }
        return false;
    }
}

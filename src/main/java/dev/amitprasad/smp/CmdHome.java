package dev.amitprasad.smp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHome implements CommandExecutor {
    private Plugin plugin;

    public CmdHome(Plugin plugin) {
        this.plugin = plugin;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        switch (label) {
            case "sethome":
                if (args.length == 0) {
                    Msg.send(player, "[Homes] Usage: /sethome <home name>");
                    return true;
                }
                String homeName = args[0];
                if (this.plugin.homes.setHome(player.getUniqueId(), homeName, player.getLocation())) {
                    Msg.send(player, "[Homes] Home " + homeName + " set.");
                } else {
                    Msg.send(player, "[Homes] Home " + homeName + " already exists.");
                }
                return true;
            case "delhome":
                if (args.length == 0) {
                    Msg.send(player, "[Homes] Usage: /delhome <home name>");
                    return true;
                }
                homeName = args[0];
                if (this.plugin.homes.delHome(player.getUniqueId(), homeName)) {
                    Msg.send(player, "[Homes] Home " + homeName + " deleted.");
                } else {
                    Msg.send(player, "[Homes] Home " + homeName + " does not exist.");
                }
                return true;
            case "home":
                if (args.length == 0) {
                    Msg.send(player, "[Homes] Usage: /home <home name>");
                    return true;
                }
                homeName = args[0];
                Location home = this.plugin.homes.getHome(player.getUniqueId(), homeName);
                if (home == null) {
                    Msg.send(player, "[Homes] Home " + homeName + " does not exist.");
                    return true;
                }
                player.teleport(home);
                Msg.send(player, "[Homes] Teleported to home " + homeName + ".");
                return true;
            case "homes":
                Msg.send(player, "[Homes] Your homes:");
                for (String h : this.plugin.homes.getHomes(player.getUniqueId())) {
                    Msg.send(player, " - " + h);
                }
                return true;
            default:
                Msg.send(player, "[Homes] Usage:");
                Msg.send(player, "        /home <home name>");
                Msg.send(player, "        /delhome <home name>");
                Msg.send(player, "        /home <home name>");
                return true;
        }
    }
}
package dev.amitprasad.smp;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTPA implements CommandExecutor {
    private Plugin plugin;
    private final HashMap<Player, ArrayList<Player>> pendingTPA = new HashMap<>();

    public CmdTPA(Plugin plugin) {
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
            case "tpa":
                if (args.length == 0) {
                    Msg.send(player, "[TP] Usage: /tpa <player>");
                    return true;
                }
                Player target = this.plugin.getServer().getPlayer(args[0]);
                if (target == null) {
                    Msg.send(player, "[TP] Player " + args[0] + " not found.");
                    return true;
                }
                if (this.pendingTPA.containsKey(target)) {
                    ArrayList<Player> pending = this.pendingTPA.get(target);
                    if (pending != null && pending.contains(player)) {
                        Msg.send(player,
                                "[TP] Player " + target.getName() + " already has a pending request from you.");
                        Msg.send(target, "[TP] Player " + player.getName() + " still wants to teleport to you.");
                        return true;
                    }
                }
                this.pendingTPA.putIfAbsent(target, new ArrayList<Player>());
                this.pendingTPA.get(target).add(player);
                Msg.send(player, "[TP] Request sent to " + target.getName() + ".");
                Msg.send(target, "[TP] Player " + player.getName() + " has requested to teleport to you.");
                Msg.send(target, "[TP] Type /tpaccept to accept or /tpdeny to deny.");
                return true;
            case "tpaccept":
                if (this.pendingTPA.containsKey(player)) {
                    ArrayList<Player> pending = this.pendingTPA.get(player);
                    if (pending != null && !pending.isEmpty()) {
                        for (Player p : pending) {
                            p.teleport(player.getLocation());
                            Msg.send(p, "[TP] Teleported to " + player.getName() + ".");
                        }
                        Msg.send(player, "[TP] Accepted all pending requests.");
                        this.pendingTPA.remove(player);
                    } else {
                        Msg.send(player, "[TP] No pending requests.");
                    }
                } else {
                    Msg.send(player, "[TP] No pending requests.");
                }
                return true;
            case "tpdeny":
                if (this.pendingTPA.containsKey(player)) {
                    ArrayList<Player> pending = this.pendingTPA.get(player);
                    if (pending != null && !pending.isEmpty()) {
                        for (Player p : pending) {
                            Msg.send(p, "[TP] Player " + player.getName() + " denied your request.");
                        }
                        Msg.send(player, "[TP] Denied all pending requests.");
                        this.pendingTPA.remove(player);
                    } else {
                        Msg.send(player, "[TP] No pending requests.");
                    }
                } else {
                    Msg.send(player, "[TP] No pending requests.");
                }
                return true;
            default:
                return false;
        }
    }
}
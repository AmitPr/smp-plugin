package dev.amitprasad.smp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Msg {
    public static void send(Player p, String msg) {
        p.sendMessage(ChatColor.GRAY + msg);
    }
}

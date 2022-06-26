package dev.amitprasad.smp;

import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EventListener implements Listener {
    Plugin plugin;

    public EventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        // Prevents tamed entities from dying.
        if (e.getEntity() instanceof Tameable) {
            Tameable t = (Tameable) e.getEntity();
            if (t.isTamed()) {
                if (t.getHealth() - e.getFinalDamage() <= 0) {
                    e.setDamage(0);
                }
            }
        }
    }
}

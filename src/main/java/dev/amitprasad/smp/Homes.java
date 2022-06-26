package dev.amitprasad.smp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class Homes implements Serializable {
    public final HashMap<UUID, HashMap<String, Location>> homes;

    public boolean setHome(UUID uuid, String homeName, Location location) {
        if (!homes.containsKey(uuid)) {
            homes.put(uuid, new HashMap<String, Location>());
        }
        if (homes.get(uuid).containsKey(homeName)) {
            return false;
        }
        homes.get(uuid).put(homeName, location);
        return true;
    }

    public Location getHome(UUID uuid, String homeName) {
        if (!homes.containsKey(uuid)) {
            return null;
        }
        return homes.get(uuid).get(homeName);
    }

    public boolean delHome(UUID uuid, String homeName) {
        if (!homes.containsKey(uuid)) {
            return false;
        }
        if (!homes.get(uuid).containsKey(homeName)) {
            return false;
        }
        homes.get(uuid).remove(homeName);
        return true;
    }

    public ArrayList<String> getHomes(UUID uuid) {
        if (!homes.containsKey(uuid)) {
            return new ArrayList<String>();
        }
        return new ArrayList<String>(homes.get(uuid).keySet());
    }

    public Homes() {
        this.homes = new HashMap<>();
    }

    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(
                    new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Homes loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(
                    new GZIPInputStream(new FileInputStream(filePath)));
            Homes data = (Homes) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }

}

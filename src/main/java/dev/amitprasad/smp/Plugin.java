package dev.amitprasad.smp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    public Homes homes;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        homes = Homes.loadData("homes.dat");
        if (homes == null) {
            getLogger().info("Creating new homes.dat");
            homes = new Homes();
            homes.saveData("homes.dat");
        }
        CmdHome cmdHome = new CmdHome(this);
        getCommand("home").setExecutor(cmdHome);
        getCommand("sethome").setExecutor(cmdHome);
        getCommand("delhome").setExecutor(cmdHome);
        getCommand("homes").setExecutor(cmdHome);

        CmdTPA cmdTPA = new CmdTPA(this);
        getCommand("tpa").setExecutor(cmdTPA);
        getCommand("tpaccept").setExecutor(cmdTPA);
        getCommand("tpdeny").setExecutor(cmdTPA);

    }

    @Override
    public void onDisable() {
        homes.saveData("homes.dat");
    }
}
package org.plugin.test;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.test.event.MonsterHeatEvent;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Test extends JavaPlugin {
    private static Test test;
    private ProtocolManager protocolManager;

    @Override
    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();

    }

    @Override
    public void onEnable() {
        test = this;
        Bukkit.getPluginManager().registerEvents(new MonsterHeatEvent(), this);
    }

    @Override
    public void onDisable() {
    }

    public static Test getTest() {
        return test;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

}

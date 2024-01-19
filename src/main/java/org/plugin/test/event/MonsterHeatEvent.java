package org.plugin.test.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolConfig;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import net.kyori.adventure.Adventure;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.plugin.test.Test;

import java.awt.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MonsterHeatEvent implements Listener {

    private final static long timeOutSecond = 1;

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Player player = (Player) e.getDamager();
        Entity targetPlayer = e.getEntity();
        createArmorStand(targetPlayer, 100, e.getDamage(), player);
    }

    //    private void createArmorStand(Entity targetPlayer, double damage, Player player) {
//
//        Vector playerDirection = targetPlayer.getLocation().getDirection();
//        Location location = targetPlayer.getLocation().add(playerDirection.multiply(1));
//        location.setY(location.getY() + 1);
//        TextDisplay textDisplay = player.getWorld().spawn(
//                location, TextDisplay.class);
//
//        textDisplay.setDefaultBackground(false);
//        textDisplay.setText(damage+" 데미지 입힘");
//        textDisplay.setBackgroundColor(Color.fromARGB(0));
//        textDisplay.setBrightness(new Display.Brightness(10, 1));
//        textDisplay.setBillboard(Display.Billboard.CENTER);
//        textDisplay.setShadowed(true);
//    }
    private void createArmorStand(Entity targetPlayer, double targetHealth, double damage, Player player) {

        Vector playerDirection = targetPlayer.getLocation().getDirection();
        Location location = targetPlayer.getLocation().add(playerDirection.multiply(1));
        location.setY(location.getY() + 2);

        TextDisplay textDisplay = player.getWorld().spawn(
                location, TextDisplay.class);

        textDisplay.setText((targetHealth - damage) + " 데미지 입힘");
        textDisplay.setDefaultBackground(false);
        textDisplay.setText(damage + " 데미지 입힘");
        textDisplay.setBackgroundColor(Color.fromARGB(0));
        textDisplay.setBrightness(new Display.Brightness(10, 1));
        textDisplay.setBillboard(Display.Billboard.CENTER);

        PacketContainer container = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);

        container.getEntityModifier(player.getWorld()).write(0, textDisplay);

        Test.getTest().getProtocolManager().broadcastServerPacket(container, player, false);
        new BukkitRunnable() {
            int cnt = 0;

            @Override
            public void run() {
                if (cnt / 20 == timeOutSecond) {
                    textDisplay.remove();
                    cancel();
                }
                textDisplay.teleport(textDisplay.getLocation().add(0, cnt * 0.02, 0));
                cnt += 1;
            }

        }.runTaskTimer(Test.getTest(), 0, 1);
    }

//    private void createArmorStand(Entity targetPlayer, double damage, Player player) {
//        Vector playerDirection = targetPlayer.getLocation().getDirection();
//        Location location = targetPlayer.getLocation().add(playerDirection.multiply(1));
//        location.setY(location.getY() + 1);
//        TextDisplay textDisplay = targetPlayer.getWorld().spawn(
//                location, TextDisplay.class);
//        textDisplay.setCustomNameVisible(true);
//        textDisplay.setCustomName("데미지 : " + damage);
//        textDisplay.setGravity(false);
//        new BukkitRunnable() {
//            int cnt = 0;
//
//            @Override
//            public void run() {
//                if (cnt / 10 == timeOutSecond) {
//                    textDisplay.remove();
//                    cancel();
//                }
    //                if(cnt % 2 == 0)
    //                textDisplay.teleport(textDisplay.getLocation().add(0, cnt * 0.1, 0));
    //                cnt += 2;
    //            }
    //
    //        }.runTaskTimer(Test.getTest(), 0, 2);
//
//    }

}

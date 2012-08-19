package com.github.pfacheris.AvatarCraft;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class MovementListener
  implements Listener
{
  AvatarCraft plugin;
  public static HashSet<Player> climbingEarthbenders = new HashSet();

  public MovementListener(AvatarCraft plugin)
  {
    this.plugin = plugin;
    climbingEarthbenders.clear();
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerMoveEvent(PlayerMoveEvent event)
  {
    Player player = event.getPlayer();
    Location playerloc = player.getLocation();
    Block blockplayerin = playerloc.getBlock();

    if (player.hasPermission("AvatarCraft.Airbender"))
    {
      if (player.isSprinting())
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500, 1));
      else
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 0, 0), true);
      player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500, 1), true);
    }

    if (player.isSneaking())
    {
      if ((player.hasPermission("AvatarCraft.Waterbender")) && ((blockplayerin.getTypeId() == 8) || (blockplayerin.getTypeId() == 9))) {
        Vector dir = player.getLocation().getDirection().normalize().multiply(0.3999999999999999D);
        Vector vec = new Vector(dir.getX(), dir.getY(), dir.getZ());
        player.setVelocity(vec);
      }
      else if (player.hasPermission("AvatarCraft.Earthbender"))
      {
        Vector vel = player.getVelocity();
        double x = vel.getX();
        double z = vel.getZ();

        if (climbingEarthbenders.contains(player)) {
          climbingEarthbenders.remove(player);
        }
        if (Math.abs(playerloc.getDirection().getX()) > Math.abs(playerloc.getDirection().getZ()))
        {
          if (playerloc.getDirection().getX() > 0.0D)
          {
            int bType = blockplayerin.getRelative(1, 0, 0).getTypeId();

            if ((bType == 1) || (bType == 2) || (bType == 3) || (bType == 4) || (bType == 7) || (bType == 12) || (bType == 13) || (bType == 24) || (bType == 43) || (bType == 49) || (bType == 98) || (bType == 110) || (bType == 121))
            {
              if (((player.getLocation().getYaw() < 315.0F) && (player.getLocation().getYaw() > 225.0F)) || ((player.getLocation().getYaw() < -45.0F) && (player.getLocation().getYaw() > -135.0F)))
              {
                if (player.getLocation().getPitch() < 0.0F)
                  player.setVelocity(new Vector(x, 0.3D, z));
                else
                  player.setVelocity(new Vector(x, -0.15D, z));
                if (!climbingEarthbenders.contains(player)) {
                  climbingEarthbenders.add(player);
                }
              }
            }
          }
          else if (playerloc.getDirection().getX() < 0.0D)
          {
            int bType = blockplayerin.getRelative(-1, 0, 0).getTypeId();
            if ((bType == 1) || (bType == 2) || (bType == 3) || (bType == 4) || (bType == 7) || (bType == 12) || (bType == 13) || (bType == 24) || (bType == 43) || (bType == 49) || (bType == 98) || (bType == 110) || (bType == 121))
            {
              if (((player.getLocation().getYaw() > 45.0F) && (player.getLocation().getYaw() < 135.0F)) || ((player.getLocation().getYaw() < -225.0F) && (player.getLocation().getYaw() > -315.0F)))
              {
                if (player.getLocation().getPitch() < 0.0F)
                  player.setVelocity(new Vector(x, 0.3D, z));
                else
                  player.setVelocity(new Vector(x, -0.15D, z));
                if (!climbingEarthbenders.contains(player))
                  climbingEarthbenders.add(player);
              }
            }
          }
        }
        else if (Math.abs(playerloc.getDirection().getX()) < Math.abs(playerloc.getDirection().getZ()))
        {
          if (playerloc.getDirection().getZ() > 0.0D)
          {
            int bType = blockplayerin.getRelative(0, 0, 1).getTypeId();
            if ((bType == 1) || (bType == 2) || (bType == 3) || (bType == 4) || (bType == 7) || (bType == 12) || (bType == 13) || (bType == 24) || (bType == 43) || (bType == 49) || (bType == 98) || (bType == 110) || (bType == 121))
            {
              if ((Math.abs(player.getLocation().getYaw()) < 45.0F) || (Math.abs(player.getLocation().getYaw()) > 315.0F))
              {
                if (player.getLocation().getPitch() < 0.0F)
                  player.setVelocity(new Vector(x, 0.3D, z));
                else
                  player.setVelocity(new Vector(x, -0.15D, z));
                if (!climbingEarthbenders.contains(player))
                  climbingEarthbenders.add(player);
              }
            }
          }
          else if (playerloc.getDirection().getZ() < 0.0D)
          {
            int bType = blockplayerin.getRelative(0, 0, -1).getTypeId();
            if ((bType == 1) || (bType == 2) || (bType == 3) || (bType == 4) || (bType == 7) || (bType == 12) || (bType == 13) || (bType == 24) || (bType == 43) || (bType == 49) || (bType == 98) || (bType == 110) || (bType == 121))
            {
              if ((Math.abs(player.getLocation().getYaw()) < 225.0F) && (Math.abs(player.getLocation().getYaw()) > 135.0F))
              {
                if (player.getLocation().getPitch() < 0.0F)
                  player.setVelocity(new Vector(x, 0.3D, z));
                else
                  player.setVelocity(new Vector(x, -0.15D, z));
                if (!climbingEarthbenders.contains(player))
                  climbingEarthbenders.add(player);
              }
            }
          }
        }
      }
    }
  }
}
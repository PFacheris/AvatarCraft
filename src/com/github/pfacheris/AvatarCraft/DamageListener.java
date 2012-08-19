package com.github.pfacheris.AvatarCraft;

import java.util.HashSet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class DamageListener
  implements Listener
{
  AvatarCraft plugin;

  public DamageListener(AvatarCraft plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.NORMAL)
  public void onPlayerDamage(EntityDamageEvent event) {
    Entity ent = event.getEntity();
    if ((ent instanceof Player))
    {
      Player player = (Player)ent;

      if ((player.hasPermission("AvatarCraft.Airbender")) && (event.getCause() == EntityDamageEvent.DamageCause.FALL)) {
        event.setCancelled(true);
      }
      if ((player.hasPermission("AvatarCraft.Waterbender")) && (event.getCause() == EntityDamageEvent.DamageCause.DROWNING)) {
        event.setCancelled(true);
      }
      if ((player.hasPermission("AvatarCraft.Firebender")) && ((event.getCause() == EntityDamageEvent.DamageCause.FIRE) || (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK))) {
        event.setCancelled(true);
      }
      if (player.hasPermission("AvatarCraft.Earthbender")) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
          event.setCancelled(true);
        } else if ((event.getCause() == EntityDamageEvent.DamageCause.FALL) && (MovementListener.climbingEarthbenders.contains(player))) {
          event.setCancelled(true);
        }
        else {
          int dmg = event.getDamage();
          if (dmg > 2)
            event.setDamage(event.getDamage() - 2);
          else if ((dmg < 2) && (dmg > 0)) {
            event.setDamage(1);
          }
        }
      }
    }
    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
    {
      EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
      if ((e.getDamager() instanceof Player))
      {
        Player otherPlayer = (Player)e.getDamager();
        int inHand = otherPlayer.getItemInHand().getTypeId();
        if (((inHand == 268) || (inHand == 272) || (inHand == 267) || (inHand == 283) || (inHand == 276)) && ((otherPlayer.hasPermission("AvatarCraft.Airbender")) || (otherPlayer.hasPermission("AvatarCraft.Waterbender")) || (otherPlayer.hasPermission("AvatarCraft.Earthbender")) || (otherPlayer.hasPermission("AvatarCraft.Firebender"))))
        {
          event.setCancelled(true);
          otherPlayer.sendMessage("You lack the skill to use a sword correctly!");
        }
      }
    }
  }
}
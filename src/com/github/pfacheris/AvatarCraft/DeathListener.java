package com.github.pfacheris.AvatarCraft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.PluginManager;

public class DeathListener
implements Listener
{
	AvatarCraft plugin;
	private static final Logger log = Logger.getLogger("Minecraft");
	private String currentAvatar = "";

	public DeathListener(AvatarCraft plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		if ((player.getWorld() == this.plugin.getServer().getWorld("world")) && (player.getName().equalsIgnoreCase(this.plugin.getConfig().getString("Avatar.Current.Name")))) {
			event.setDeathMessage(ChatColor.GOLD + "The Avatar has made the jorney to the spirit realm. A new Avatar is born!");
			Reincarnate("");
		}
	}

	protected void Reincarnate(String newAvatarName)
	{
		String world = null;
		if (!this.plugin.getServer().getPluginManager().isPluginEnabled("PermissionsBukkit")) {
			world = "world";
		}
		if ((this.currentAvatar == null) || (this.currentAvatar.equals(""))) {
			this.currentAvatar = this.plugin.getConfig().getString("Avatar.Current.Name");
		}
		if ((this.currentAvatar != null) && (this.currentAvatar != "")) {
			OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(this.currentAvatar);
			String playerName = player.getName();
			WriteOldSpells(playerName);
			AvatarCraft.permission.playerRemoveGroup(world, player.getName(), "Avatar");
			AvatarCraft.permission.playerAddGroup(world, player.getName(), this.plugin.getConfig().getString("Avatar.Current.Type"));
		}
		OfflinePlayer newAvatar;
		if ((newAvatarName == null) || (newAvatarName.equals("")))
			newAvatar = getNewAvatar();
		else {
			newAvatar = this.plugin.getServer().getOfflinePlayer(newAvatarName);
		}
		if (newAvatar != null) {
			this.currentAvatar = newAvatar.getName();
			log.info("The new Avatar is: " + this.currentAvatar);
			this.plugin.getConfig().set("Avatar.Current.Name", this.currentAvatar);
			this.plugin.getConfig().set("Avatar.Current.Type", AvatarCraft.permission.getPrimaryGroup("world", this.currentAvatar));
			ReadOldSpells(this.currentAvatar);
			AvatarCraft.permission.playerRemoveGroup(world, newAvatar.getName(), AvatarCraft.permission.getPrimaryGroup("world", newAvatar.getName()));
			AvatarCraft.permission.playerAddGroup(world, newAvatar.getName(), "Avatar");

			this.plugin.saveConfig();
			this.plugin.reloadConfig();

			Bukkit.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "c reload");
		}
	}

	private OfflinePlayer getNewAvatar()
	{
		OfflinePlayer[] allPlayers = this.plugin.getServer().getOfflinePlayers();
		List eligiblePlayers = new ArrayList();

		for (OfflinePlayer p : allPlayers)
		{
			if (!p.hasPlayedBefore())
				continue;
			if ((p.getLastPlayed() <= System.currentTimeMillis() - 259200000L) || (p.getName().equalsIgnoreCase(this.plugin.getConfig().getString("Avatar.Current.Name"))) || (AvatarCraft.permission.playerHas("world", p.getName(), "AvatarCraft.Nonbender")))
				continue;
			eligiblePlayers.add(p);
		}

		if (eligiblePlayers.size() > 0) {
			Random generator = new Random();
			OfflinePlayer newAvatar;
			do { int randomIndex = generator.nextInt(eligiblePlayers.size());
			newAvatar = (OfflinePlayer)eligiblePlayers.get(randomIndex);
			}
			while (newAvatar == null);

			return newAvatar;
		}

		return null;
	}

	private void WriteOldSpells(String name)
	{
		String path = "plugins/MagicSpells/spellbooks/world/" + name + ".txt";
		File file = new File(path);
		try
		{
			FileOutputStream fout = new FileOutputStream(file);

			List<String> abilities = this.plugin.getConfig().getStringList("Avatar.Current.Abilities");
			if (abilities.size() > 0)
			{
				for (String s : abilities)
				{
					new PrintStream(fout).println(s);
				}
			}

			fout.close();
		}
		catch (IOException e)
		{
			System.err.println("Unable to write to file");
		}
	}

	private void ReadOldSpells(String name)
	{
		try {
			String path = "plugins/MagicSpells/spellbooks/world/" + name + ".txt";
			File file = new File(path);

			List abilities = new ArrayList();
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				abilities.add(sc.nextLine());
			}

			this.plugin.getConfig().set("Avatar.Current.Abilities", abilities);
			file.delete();
		}
		catch (Exception FileNotFoundException)
		{
			System.err.println("Unable to read to file");
		}
	}
}
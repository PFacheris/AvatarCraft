package com.github.pfacheris.AvatarCraft;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminExecutor
implements CommandExecutor
{
	private AvatarCraft plugin;

	public AdminExecutor(AvatarCraft plugin)
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (((Player)sender).hasPermission("AvatarCraft.Admin"))
		{
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Not enough arguments!");
				return false;
			}

			if (args.length > 3) {
				sender.sendMessage(ChatColor.RED + "Too many arguments!");
				return false;
			}
			if (args[0].equalsIgnoreCase("new"))
			{
				if (args.length == 1)
				{
					this.plugin.myDeathListener.Reincarnate("");
					return true;
				}
				OfflinePlayer[] arrayOfOfflinePlayer;
				if ((arrayOfOfflinePlayer = this.plugin.getServer().getOfflinePlayers()).length != 0) { OfflinePlayer p = arrayOfOfflinePlayer[0];

				if (p.getName().equalsIgnoreCase(args[1]));
				this.plugin.myDeathListener.Reincarnate(args[1]);
				return true;
				}

			}

		}

		return false;
	}
}
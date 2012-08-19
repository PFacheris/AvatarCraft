package com.github.pfacheris.AvatarCraft;

import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AvatarCraft extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	public final DamageListener myDamageListener = new DamageListener(this);
	public final MovementListener myMovementListener = new MovementListener(this);
	public final DeathListener myDeathListener = new DeathListener(this);
	public static Permission permission = null;

	public void onEnable()
	{
		loadConfiguration();
		this.log.info("AvatarCraft successfully enabled. Bending powers, activate!");
		setupPermissions();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.myDamageListener, this);
		pm.registerEvents(this.myMovementListener, this);
		pm.registerEvents(this.myDeathListener, this);
		AdminExecutor myAdminExecutor = new AdminExecutor(this);
		getCommand("avatar").setExecutor(myAdminExecutor);
	}

	private boolean setupPermissions()
	{
		RegisteredServiceProvider permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission)permissionProvider.getProvider();
		}
		return permission != null;
	}

	public void onDisable()
	{
		this.log.info("AvatarCraft shut down.");
	}

	public void registerEvents(Listener listener) {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
	}

	public void loadConfiguration() {
		String path = "Avatar.Current.Name";
		getConfig().addDefault(path, "Ryrados");
		path = "Avatar.Current.Abilities";
		getConfig().addDefault(path, "");
		path = "Avatar.Current.Type";
		getConfig().addDefault(path, "Airmaster");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}
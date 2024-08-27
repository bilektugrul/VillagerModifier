package io.github.bilektugrul.villagerModifier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class VillagerModifier extends JavaPlugin implements CommandExecutor, Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("villagermodifier").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("vm.admin")) {
            reloadConfig();
            sender.sendMessage("Config reloaded.");
        }
        return true;
    }

    @EventHandler
    public void onVillagerBreed(EntityBreedEvent e) {
        if (e.getEntityType() == EntityType.VILLAGER) {
            if (getConfig().getBoolean("disable-villager-breed")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onVillagerCareerChange(VillagerCareerChangeEvent e) {
        if (e.getEntity().getProfession() != Villager.Profession.NONE && getConfig().getBoolean("disable-career-change")) {
            e.setCancelled(true);
        }
    }

}
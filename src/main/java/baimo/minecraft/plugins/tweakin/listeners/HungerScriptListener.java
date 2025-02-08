package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HungerScriptListener implements Listener {
    private final Tweakin plugin;
    private final Map<UUID, Integer> lastFoodLevel = new HashMap<>();
    private final Map<UUID, Boolean> diedFromStarvation = new HashMap<>();
    
    public HungerScriptListener(Tweakin plugin) {
        this.plugin = plugin;
        startScriptChecker();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!plugin.getConfig().getBoolean("hunger.enabled", true)) {
            return;
        }
        
        Player player = event.getEntity();
        
        // 保存死亡时的饥饿值
        lastFoodLevel.put(player.getUniqueId(), player.getFoodLevel());
        
        // 检查是否是饿死
        EntityDamageEvent damageEvent = player.getLastDamageCause();
        boolean isStarvation = damageEvent != null && 
                             damageEvent.getCause() == EntityDamageEvent.DamageCause.STARVATION;
        diedFromStarvation.put(player.getUniqueId(), isStarvation);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!plugin.getConfig().getBoolean("hunger.enabled", true)) {
            return;
        }
        
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        
        // 设置饥饿值
        if (diedFromStarvation.getOrDefault(playerId, false)) {
            // 如果是饿死，给予配置中设定的饱食度
            int starvationFood = plugin.getConfig().getInt("hunger.starvation-respawn-food", 4);
            player.setFoodLevel(starvationFood);
        } else {
            // 否则恢复之前的饥饿值
            Integer previousFood = lastFoodLevel.get(playerId);
            if (previousFood != null) {
                player.setFoodLevel(previousFood);
            }
        }
        
        // 清理数据
        lastFoodLevel.remove(playerId);
        diedFromStarvation.remove(playerId);
    }
    
    private void startScriptChecker() {
        if (!plugin.getConfig().getBoolean("scripts.enabled", true)) {
            return;
        }
        
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    checkAndExecuteScripts(player);
                }
            }
        }.runTaskTimer(plugin, 20L, 20L); // 每秒检查一次
    }
    
    private void checkAndExecuteScripts(Player player) {
        ConfigurationSection triggers = plugin.getConfig().getConfigurationSection("scripts.triggers");
        if (triggers == null) return;
        
        for (String triggerName : triggers.getKeys(false)) {
            ConfigurationSection trigger = triggers.getConfigurationSection(triggerName);
            if (trigger == null) continue;
            
            if (checkConditions(player, trigger.getConfigurationSection("conditions"))) {
                executeCommands(player, trigger.getStringList("commands"));
            }
        }
    }
    
    private boolean checkConditions(Player player, ConfigurationSection conditions) {
        if (conditions == null) return false;
        
        double health = player.getHealth();
        int foodLevel = player.getFoodLevel();
        
        Double healthBelow = conditions.getDouble("health-below", -1);
        Double healthAbove = conditions.getDouble("health-above", -1);
        Integer foodBelow = conditions.getInt("food-below", -1);
        Integer foodAbove = conditions.getInt("food-above", -1);
        
        if (healthBelow > 0 && health >= healthBelow) return false;
        if (healthAbove > 0 && health <= healthAbove) return false;
        if (foodBelow > 0 && foodLevel >= foodBelow) return false;
        if (foodAbove > 0 && foodLevel <= foodAbove) return false;
        
        return true;
    }
    
    private void executeCommands(Player player, java.util.List<String> commands) {
        if (commands == null) return;
        
        for (String command : commands) {
            String[] parts = command.split(" ", 2);
            if (parts.length < 2) continue;
            
            String type = parts[0].toUpperCase();
            String cmd = parts[1].replace("%player%", player.getName())
                                .replace("@s", player.getName());
            
            switch (type) {
                case "[PLAYER]":
                    player.performCommand(cmd);
                    break;
                case "[CONSOLE]":
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                    break;
                case "[OP]":
                    boolean wasOp = player.isOp();
                    try {
                        player.setOp(true);
                        player.performCommand(cmd);
                    } finally {
                        if (!wasOp) {
                            player.setOp(false);
                        }
                    }
                    break;
            }
        }
    }
    
    public void cleanup() {
        lastFoodLevel.clear();
        diedFromStarvation.clear();
    }
} 
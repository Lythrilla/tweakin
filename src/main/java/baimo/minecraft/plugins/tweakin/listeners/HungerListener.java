package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HungerListener implements Listener {
    private final Tweakin plugin;
    private final Map<UUID, Integer> lastFoodLevel = new HashMap<>();
    
    public HungerListener(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!plugin.getConfig().getBoolean("hunger.enabled", true)) {
            return;
        }
        
        Player player = event.getEntity();
        // 保存死亡时的饱食度
        lastFoodLevel.put(player.getUniqueId(), player.getFoodLevel());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!plugin.getConfig().getBoolean("hunger.enabled", true)) {
            return;
        }
        
        final Player player = event.getPlayer();
        final UUID playerId = player.getUniqueId();
        
        // 延迟1tick设置饥饿值，确保在重生后应用
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // 设置饥饿值
            Integer previousFood = lastFoodLevel.get(playerId);
            if (previousFood != null) {
                if (previousFood == 0) {
                    // 如果死亡时饱食度为0，给予配置中设定的饱食度
                    int starvationFood = plugin.getConfig().getInt("hunger.starvation-respawn-food", 4);
                    player.setFoodLevel(starvationFood);
                } else {
                    // 其他情况，恢复之前的饥饿值
                    player.setFoodLevel(previousFood);
                }
            }
            
            // 清理数据
            lastFoodLevel.remove(playerId);
        }, 1L);
    }
    
    public void cleanup() {
        lastFoodLevel.clear();
    }
} 
package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.boss.DragonBattle;

public class DragonEggListener implements Listener {
    private final Tweakin plugin;
    
    public DragonEggListener(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDragonDeath(EntityDeathEvent event) {
        if (!plugin.getConfig().getBoolean("dragon-egg.enabled", true)) {
            return;
        }
        
        if (!(event.getEntity() instanceof EnderDragon)) {
            return;
        }
        
        World world = event.getEntity().getWorld();
        if (world.getEnvironment() != World.Environment.THE_END) {
            return;
        }
        
        DragonBattle battle = world.getEnderDragonBattle();
        if (battle == null) {
            return;
        }
        
        // 如果是首次击杀，游戏会自动生成龙蛋，所以我们不需要生成
        if (battle.hasBeenPreviouslyKilled()) {
            // 延迟生成龙蛋，确保末地传送门已经生成
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                // 获取末地传送门的位置
                Location portalLoc = battle.getEndPortalLocation();
                if (portalLoc == null) {
                    return;
                }
                
                // 在传送门上方生成龙蛋
                Location eggLoc = portalLoc.clone().add(0, 4, 0);
                eggLoc.getBlock().setType(Material.DRAGON_EGG);
            }, 100L); // 5秒延迟，确保传送门已完全生成
        }
    }
} 
package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.util.Vector;

public class CactusProtectionListener implements Listener {
    private final Tweakin plugin;
    
    public CactusProtectionListener(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemDamageByBlock(EntityDamageByBlockEvent event) {
        if (!plugin.getConfig().getBoolean("cactus-protection.enabled", true)) {
            return;
        }
        
        // 检查是否是掉落物
        if (!(event.getEntity() instanceof Item)) {
            return;
        }
        
        Block damager = event.getDamager();
        // 检查是否是仙人掌造成的伤害
        if (damager == null || damager.getType() != Material.CACTUS) {
            return;
        }
        
        // 取消伤害事件
        event.setCancelled(true);
        
        // 获取掉落物实体
        Entity item = event.getEntity();
        
        // 给物品一个轻微的弹开效果
        Vector velocity = item.getVelocity();
        velocity.setX(velocity.getX() + (Math.random() - 0.5) * 0.2);
        velocity.setY(0.2);
        velocity.setZ(velocity.getZ() + (Math.random() - 0.5) * 0.2);
        item.setVelocity(velocity);
    }
} 
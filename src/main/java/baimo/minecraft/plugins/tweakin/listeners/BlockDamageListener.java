package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Candle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.GameMode;
import org.bukkit.Location;

import java.util.EnumSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlockDamageListener implements Listener {
    private final Tweakin plugin;
    private final EnumSet<Material> candleBlocks;
    private final Map<UUID, BukkitTask> damageTasks = new ConcurrentHashMap<>();
    
    public BlockDamageListener(Tweakin plugin) {
        this.plugin = plugin;
        this.candleBlocks = EnumSet.of(
            Material.CANDLE,
            Material.WHITE_CANDLE,
            Material.ORANGE_CANDLE,
            Material.MAGENTA_CANDLE,
            Material.LIGHT_BLUE_CANDLE,
            Material.YELLOW_CANDLE,
            Material.LIME_CANDLE,
            Material.PINK_CANDLE,
            Material.GRAY_CANDLE,
            Material.LIGHT_GRAY_CANDLE,
            Material.CYAN_CANDLE,
            Material.PURPLE_CANDLE,
            Material.BLUE_CANDLE,
            Material.BROWN_CANDLE,
            Material.GREEN_CANDLE,
            Material.RED_CANDLE,
            Material.BLACK_CANDLE
        );
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!plugin.getConfig().getBoolean("block-damage.enabled", true)) {
            return;
        }
        
        Player player = event.getPlayer();
        
        // 检查玩家状态
        if (player.isDead() || !player.isValid() || player.isInvulnerable() || 
            player.getGameMode() == GameMode.CREATIVE || 
            player.getGameMode() == GameMode.SPECTATOR) {
            stopDamageTask(player);
            return;
        }
        
        // 获取玩家位置
        Location loc = player.getLocation();
        
        // 检查玩家脚下的方块
        Block blockBelow = loc.clone().subtract(0, 0.1, 0).getBlock();
        Block blockAt = loc.getBlock();
        
        // 检查是否站在切石机或点燃的蜡烛上
        boolean shouldDamage = blockBelow.getType() == Material.STONECUTTER ||
                             (isCandle(blockBelow) && isCandleLit(blockBelow)) ||
                             (isCandle(blockAt) && isCandleLit(blockAt));
        
        if (shouldDamage) {
            startDamageTask(player);
        } else {
            stopDamageTask(player);
        }
    }
    
    private boolean isCandle(Block block) {
        return candleBlocks.contains(block.getType());
    }
    
    private boolean isCandleLit(Block block) {
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Candle) {
            return ((Candle) blockData).isLit();
        }
        return false;
    }
    
    private void startDamageTask(Player player) {
        UUID playerId = player.getUniqueId();
        if (damageTasks.containsKey(playerId)) {
            return;
        }
        
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isValid() || !player.isOnline() || player.isDead()) {
                    stopDamageTask(player);
                    return;
                }
                
                // 再次检查玩家是否真的在伤害方块上
                Location loc = player.getLocation();
                Block blockBelow = loc.clone().subtract(0, 0.1, 0).getBlock();
                Block blockAt = loc.getBlock();
                
                boolean shouldDamage = blockBelow.getType() == Material.STONECUTTER ||
                                     (isCandle(blockBelow) && isCandleLit(blockBelow)) ||
                                     (isCandle(blockAt) && isCandleLit(blockAt));
                
                if (!shouldDamage) {
                    stopDamageTask(player);
                    return;
                }
                
                // 检查玩家状态
                if (player.isInvulnerable() || 
                    player.getGameMode() == GameMode.CREATIVE || 
                    player.getGameMode() == GameMode.SPECTATOR) {
                    stopDamageTask(player);
                    return;
                }
                
                // 造成伤害
                double damage = plugin.getConfig().getDouble("block-damage.damage", 2.0);
                player.damage(damage);
            }
        }.runTaskTimer(plugin, 5L, 20L); // 初始延迟0.25秒，每秒检查一次
        
        damageTasks.put(playerId, task);
    }
    
    private void stopDamageTask(Player player) {
        UUID playerId = player.getUniqueId();
        BukkitTask task = damageTasks.remove(playerId);
        if (task != null) {
            task.cancel();
        }
    }
    
    public void cleanup() {
        damageTasks.values().forEach(BukkitTask::cancel);
        damageTasks.clear();
    }
} 
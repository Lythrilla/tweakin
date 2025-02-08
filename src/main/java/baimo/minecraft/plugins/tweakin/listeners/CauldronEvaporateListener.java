package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Chunk;

public class CauldronEvaporateListener implements Listener {
    private final Tweakin plugin;
    
    public CauldronEvaporateListener(Tweakin plugin) {
        this.plugin = plugin;
        startEvaporationTask();
    }
    
    private void startEvaporationTask() {
        int checkInterval = plugin.getConfig().getInt("cauldron-evaporate.check-interval", 300) * 20; // 转换为tick
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!plugin.getConfig().getBoolean("cauldron-evaporate.enabled", true)) {
                    return;
                }
                
                // 遍历所有已加载的下界区块
                for (World world : plugin.getServer().getWorlds()) {
                    if (world.getEnvironment() != World.Environment.NETHER) {
                        continue;
                    }
                    
                    for (Chunk chunk : world.getLoadedChunks()) {
                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) {
                                // 只检查已加载的区块中的炼药锅
                                for (int y = 0; y < world.getMaxHeight(); y++) {
                                    Block block = chunk.getBlock(x, y, z);
                                    if (block.getType() == Material.WATER_CAULDRON) {
                                        evaporateWater(block);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, checkInterval, checkInterval);
    }
    
    private void evaporateWater(Block cauldron) {
        Levelled levelledData = (Levelled) cauldron.getBlockData();
        int currentLevel = levelledData.getLevel();
        
        if (currentLevel > 1) {
            // 降低水位
            levelledData.setLevel(currentLevel - 1);
            cauldron.setBlockData(levelledData);
        } else {
            // 如果只剩一格水，就变成空炼药锅
            cauldron.setType(Material.CAULDRON);
        }
        
        // 显示蒸发效果
        if (plugin.getConfig().getBoolean("cauldron-evaporate.particle-effect", true)) {
            cauldron.getWorld().spawnParticle(
                org.bukkit.Particle.CLOUD,
                cauldron.getLocation().add(0.5, 1.0, 0.5),
                8,
                0.2, 0.1, 0.2,
                0
            );
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        // 当新的区块加载时，检查是否需要开始蒸发计时
        if (!plugin.getConfig().getBoolean("cauldron-evaporate.enabled", true)) {
            return;
        }
        
        if (event.getWorld().getEnvironment() != World.Environment.NETHER) {
            return;
        }
        
        // 不需要立即检查，因为主任务会定期检查所有已加载的区块
    }
} 
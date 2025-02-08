package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SilkTouchPlusListener implements Listener {
    private final Tweakin plugin;
    private final Map<String, Boolean> configCache = new ConcurrentHashMap<>();
    
    public SilkTouchPlusListener(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        // 快速检查功能是否启用
        if (!plugin.getConfig().getBoolean("silk-touch-plus.enabled")) {
            return;
        }
        
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        
        // 快速检查是否有精准采集
        if (!tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }
        
        // 获取方块的命名空间ID
        String blockId = "minecraft:" + block.getType().name().toLowerCase();
        
        // 使用缓存检查配置
        if (!isBlockEnabled(blockId)) {
            return;
        }
        
        // 取消原始事件
        event.setDropItems(false);
        
        // 特殊处理刷怪笼
        if (block.getType() == Material.SPAWNER) {
            handleSpawner(block);
            return;
        }
        
        // 其他方块直接掉落原物品
        dropOriginalBlock(block);
    }
    
    private boolean isBlockEnabled(String blockId) {
        return configCache.computeIfAbsent(blockId,
            key -> plugin.getConfig().getBoolean("silk-touch-plus.blocks." + key, false));
    }
    
    private void handleSpawner(Block block) {
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) spawner.getItemMeta();
        if (meta != null) {
            CreatureSpawner spawnerState = (CreatureSpawner) block.getState();
            CreatureSpawner newSpawnerState = (CreatureSpawner) meta.getBlockState();
            
            // 复制刷怪笼的生物类型
            newSpawnerState.setSpawnedType(spawnerState.getSpawnedType());
            meta.setBlockState(newSpawnerState);
            spawner.setItemMeta(meta);
        }
        
        // 掉落带有生物类型的刷怪笼
        block.getWorld().dropItemNaturally(block.getLocation(), spawner);
    }
    
    private void dropOriginalBlock(Block block) {
        ItemStack drop = new ItemStack(block.getType());
        block.getWorld().dropItemNaturally(block.getLocation(), drop);
    }
    
    public void clearCache() {
        configCache.clear();
    }
    
    public void reloadCache() {
        clearCache();
    }
} 
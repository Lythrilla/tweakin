package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AutoPlantListener implements Listener {
    private final Tweakin plugin;
    private final Map<Material, Material> PLANTABLE_ITEMS = new EnumMap<>(Material.class);
    private final Map<Material, Material> SOIL_REQUIREMENTS = new EnumMap<>(Material.class);
    private final Map<UUID, BukkitTask> plantingTasks = new ConcurrentHashMap<>();
    private static final int MAX_CONCURRENT_ITEMS = 50;
    
    public AutoPlantListener(Tweakin plugin) {
        this.plugin = plugin;
        initPlantableItems();
    }
    
    private void initPlantableItems() {
        // 作物类
        addCropItems();
        // 花类
        addFlowerItems();
        // 蘑菇
        addMushroomItems();
        // 树苗
        addSaplingItems();
        // 其他植物
        addOtherPlantItems();
        // 初始化土壤要求
        initSoilRequirements();
    }
    
    private void addCropItems() {
        PLANTABLE_ITEMS.put(Material.WHEAT_SEEDS, Material.WHEAT);
        PLANTABLE_ITEMS.put(Material.BEETROOT_SEEDS, Material.BEETROOTS);
        PLANTABLE_ITEMS.put(Material.POTATO, Material.POTATOES);
        PLANTABLE_ITEMS.put(Material.CARROT, Material.CARROTS);
        PLANTABLE_ITEMS.put(Material.PUMPKIN_SEEDS, Material.PUMPKIN_STEM);
        PLANTABLE_ITEMS.put(Material.MELON_SEEDS, Material.MELON_STEM);
    }
    
    private void addFlowerItems() {
        // 普通花
        addSimplePlantable(Material.DANDELION);
        addSimplePlantable(Material.POPPY);
        addSimplePlantable(Material.BLUE_ORCHID);
        addSimplePlantable(Material.ALLIUM);
        addSimplePlantable(Material.AZURE_BLUET);
        addSimplePlantable(Material.RED_TULIP);
        addSimplePlantable(Material.ORANGE_TULIP);
        addSimplePlantable(Material.WHITE_TULIP);
        addSimplePlantable(Material.PINK_TULIP);
        addSimplePlantable(Material.OXEYE_DAISY);
        addSimplePlantable(Material.CORNFLOWER);
        addSimplePlantable(Material.LILY_OF_THE_VALLEY);
        addSimplePlantable(Material.WITHER_ROSE);
        
        // 高花
        addSimplePlantable(Material.SUNFLOWER);
        addSimplePlantable(Material.LILAC);
        addSimplePlantable(Material.ROSE_BUSH);
        addSimplePlantable(Material.PEONY);
        
        // 其他植物
        addSimplePlantable(Material.FERN);
        addSimplePlantable(Material.LARGE_FERN);
        addSimplePlantable(Material.GRASS);
        addSimplePlantable(Material.TALL_GRASS);
        addSimplePlantable(Material.DEAD_BUSH);
        addSimplePlantable(Material.SEAGRASS);
        addSimplePlantable(Material.SEA_PICKLE);
        addSimplePlantable(Material.PITCHER_PLANT);
        addSimplePlantable(Material.TORCHFLOWER);
        addSimplePlantable(Material.PINK_PETALS);
    }
    
    private void addMushroomItems() {
        addSimplePlantable(Material.BROWN_MUSHROOM);
        addSimplePlantable(Material.RED_MUSHROOM);
    }
    
    private void addSaplingItems() {
        addSimplePlantable(Material.OAK_SAPLING);
        addSimplePlantable(Material.SPRUCE_SAPLING);
        addSimplePlantable(Material.BIRCH_SAPLING);
        addSimplePlantable(Material.JUNGLE_SAPLING);
        addSimplePlantable(Material.ACACIA_SAPLING);
        addSimplePlantable(Material.DARK_OAK_SAPLING);
        addSimplePlantable(Material.MANGROVE_PROPAGULE);
        addSimplePlantable(Material.CHERRY_SAPLING);
    }
    
    private void addOtherPlantItems() {
        addSimplePlantable(Material.BAMBOO);
        PLANTABLE_ITEMS.put(Material.SWEET_BERRIES, Material.SWEET_BERRY_BUSH);
        addSimplePlantable(Material.SUGAR_CANE);
        addSimplePlantable(Material.KELP);
        addSimplePlantable(Material.NETHER_WART);
        addSimplePlantable(Material.TWISTING_VINES);
        addSimplePlantable(Material.WEEPING_VINES);
        addSimplePlantable(Material.CAVE_VINES);
        PLANTABLE_ITEMS.put(Material.GLOW_BERRIES, Material.CAVE_VINES);
        addSimplePlantable(Material.COCOA_BEANS);
        addSimplePlantable(Material.LILY_PAD);
        addSimplePlantable(Material.SPORE_BLOSSOM);
        addSimplePlantable(Material.HANGING_ROOTS);
        addSimplePlantable(Material.BIG_DRIPLEAF);
        addSimplePlantable(Material.SMALL_DRIPLEAF);
    }
    
    private void addSimplePlantable(Material material) {
        PLANTABLE_ITEMS.put(material, material);
    }
    
    private void initSoilRequirements() {
        SOIL_REQUIREMENTS.put(Material.WHEAT, Material.FARMLAND);
        SOIL_REQUIREMENTS.put(Material.BEETROOTS, Material.FARMLAND);
        SOIL_REQUIREMENTS.put(Material.POTATOES, Material.FARMLAND);
        SOIL_REQUIREMENTS.put(Material.CARROTS, Material.FARMLAND);
        SOIL_REQUIREMENTS.put(Material.PUMPKIN_STEM, Material.FARMLAND);
        SOIL_REQUIREMENTS.put(Material.MELON_STEM, Material.FARMLAND);
        SOIL_REQUIREMENTS.put(Material.NETHER_WART, Material.SOUL_SAND);
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemSpawn(ItemSpawnEvent event) {
        // 快速检查功能是否启用
        if (!plugin.getConfig().getBoolean("auto-plant.enabled")) {
            return;
        }
        
        Item item = event.getEntity();
        ItemStack itemStack = item.getItemStack();
        
        // 检查是否是可种植的物品
        if (!PLANTABLE_ITEMS.containsKey(itemStack.getType())) {
            return;
        }
        
        // 限制同时处理的物品数量
        if (plantingTasks.size() >= MAX_CONCURRENT_ITEMS) {
            return;
        }
        
        // 创建种植任务
        createPlantingTask(item);
    }
    
    private void createPlantingTask(Item item) {
        UUID itemId = item.getUniqueId();
        int delay = plugin.getConfig().getInt("auto-plant.plant-delay", 15) * 20;
        
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!item.isValid() || item.isDead()) {
                    cleanupTask(itemId);
                    return;
                }
                
                if (tryPlant(item)) {
                    cleanupTask(itemId);
                }
            }
        }.runTaskLater(plugin, delay);
        
        plantingTasks.put(itemId, task);
    }
    
    private boolean tryPlant(Item item) {
        Location loc = item.getLocation();
        Block soil = loc.getBlock().getRelative(BlockFace.DOWN);
        Block plantSpace = loc.getBlock();
        
        // 检查区块是否加载
        if (!soil.getChunk().isLoaded()) {
            return false;
        }
        
        ItemStack itemStack = item.getItemStack();
        Material plantType = PLANTABLE_ITEMS.get(itemStack.getType());
        
        if (canPlantHere(itemStack.getType(), soil, plantSpace)) {
            // 种植
            plantSpace.setType(plantType);
            
            // 减少一个物品
            itemStack.setAmount(itemStack.getAmount() - 1);
            if (itemStack.getAmount() <= 0) {
                item.remove();
            } else {
                item.setItemStack(itemStack);
            }
            
            // 显示粒子效果
            if (plugin.getConfig().getBoolean("auto-plant.particle-effect")) {
                showPlantParticles(plantSpace);
            }
            
            return true;
        }
        
        return false;
    }
    
    private boolean canPlantHere(Material itemType, Block soil, Block plantSpace) {
        if (plantSpace.getType() != Material.AIR) {
            return false;
        }
        
        Material plantType = PLANTABLE_ITEMS.get(itemType);
        
        // 检查特殊土壤要求
        if (SOIL_REQUIREMENTS.containsKey(plantType)) {
            return soil.getType() == SOIL_REQUIREMENTS.get(plantType);
        }
        
        // 检查普通植物的种植条件
        if (isNormalPlant(itemType)) {
            return soil.getType() == Material.GRASS_BLOCK || 
                   soil.getType() == Material.DIRT || 
                   soil.getType() == Material.PODZOL || 
                   soil.getType() == Material.COARSE_DIRT || 
                   soil.getType() == Material.ROOTED_DIRT || 
                   soil.getType() == Material.MOSS_BLOCK || 
                   soil.getType() == Material.MUD;
        }
        
        // 特殊情况
        return handleSpecialPlants(itemType, soil);
    }
    
    private boolean isNormalPlant(Material itemType) {
        return itemType.name().contains("SAPLING") || 
               itemType.name().contains("FLOWER") || 
               itemType.name().contains("TULIP") || 
               itemType.name().contains("ORCHID") ||
               itemType.name().contains("ALLIUM") || 
               itemType.name().contains("DAISY") ||
               itemType.name().contains("CORNFLOWER") || 
               itemType.name().contains("LILY");
    }
    
    private boolean handleSpecialPlants(Material itemType, Block soil) {
        if (itemType == Material.SUGAR_CANE) {
            return (soil.getType() == Material.GRASS_BLOCK || 
                   soil.getType() == Material.DIRT || 
                   soil.getType() == Material.SAND) && 
                   isWaterNearby(soil);
        }
        
        if (itemType == Material.BAMBOO) {
            return soil.getType() == Material.GRASS_BLOCK || 
                   soil.getType() == Material.DIRT ||
                   soil.getType() == Material.SAND ||
                   soil.getType() == Material.GRAVEL ||
                   soil.getType() == Material.BAMBOO ||
                   soil.getType() == Material.BAMBOO_SAPLING;
        }
        
        if (itemType == Material.KELP || itemType == Material.SEAGRASS) {
            return soil.getType() == Material.WATER;
        }

        if (itemType == Material.MANGROVE_PROPAGULE) {
            Block waterBlock = soil.getRelative(BlockFace.UP);
            // 检查是否可以在水中种植
            if (waterBlock.getType() == Material.WATER && soil.getType().isSolid()) {
                return true;
            }
            // 检查是否可以在泥土类方块上种植
            return soil.getType() == Material.MUD || 
                   soil.getType() == Material.CLAY || 
                   soil.getType() == Material.DIRT || 
                   soil.getType() == Material.GRASS_BLOCK;
        }

        if (itemType == Material.SWEET_BERRIES || itemType == Material.GLOW_BERRIES) {
            return soil.getType() == Material.GRASS_BLOCK || 
                   soil.getType() == Material.DIRT || 
                   soil.getType() == Material.PODZOL ||
                   soil.getType() == Material.COARSE_DIRT ||
                   soil.getType() == Material.ROOTED_DIRT ||
                   soil.getType() == Material.MOSS_BLOCK ||
                   (itemType == Material.GLOW_BERRIES && soil.getType() == Material.CAVE_VINES);
        }

        if (itemType == Material.NETHER_WART) {
            return soil.getType() == Material.SOUL_SAND || 
                   soil.getType() == Material.SOUL_SOIL;
        }

        if (itemType == Material.TWISTING_VINES || itemType == Material.WEEPING_VINES) {
            return soil.getType().isSolid();
        }

        if (itemType == Material.COCOA_BEANS) {
            return soil.getType() == Material.JUNGLE_LOG;
        }

        if (itemType == Material.LILY_PAD) {
            return soil.getType() == Material.WATER;
        }

        if (itemType == Material.SPORE_BLOSSOM || itemType == Material.HANGING_ROOTS) {
            Block above = soil.getRelative(BlockFace.UP);
            return above.getType().isSolid();
        }

        if (itemType == Material.BIG_DRIPLEAF || itemType == Material.SMALL_DRIPLEAF) {
            return (soil.getType() == Material.CLAY || soil.getType() == Material.MOSS_BLOCK) &&
                   isWaterNearby(soil);
        }
        
        return false;
    }
    
    private boolean isWaterNearby(Block block) {
        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}) {
            Block relative = block.getRelative(face);
            if (relative.getType() == Material.WATER || 
                relative.getType() == Material.WATER_CAULDRON) {
                return true;
            }
        }
        return false;
    }
    
    private void showPlantParticles(Block block) {
        block.getWorld().spawnParticle(
            Particle.VILLAGER_HAPPY,
            block.getLocation().add(0.5, 0.2, 0.5),
            6,
            0.3, 0.3, 0.3,
            0
        );
    }
    
    private void cleanupTask(UUID itemId) {
        BukkitTask task = plantingTasks.remove(itemId);
        if (task != null) {
            task.cancel();
        }
    }
    
    public void cleanup() {
        plantingTasks.values().forEach(BukkitTask::cancel);
        plantingTasks.clear();
    }
} 
package baimo.minecraft.plugins.tweakin.listeners;

import org.bukkit.Particle;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FallingBlockListener implements Listener {
    
    private final JavaPlugin plugin;
    
    public FallingBlockListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private boolean canBreakFallingBlock(Material material) {
        switch (material) {
            // 火把类
            case TORCH:
            case REDSTONE_TORCH:
            case WALL_TORCH:
            case REDSTONE_WALL_TORCH:
            case SOUL_TORCH:
            case SOUL_WALL_TORCH:
            
            // 按钮类
            case STONE_BUTTON:
            case OAK_BUTTON:
            case SPRUCE_BUTTON:
            case BIRCH_BUTTON:
            case JUNGLE_BUTTON:
            case ACACIA_BUTTON:
            case DARK_OAK_BUTTON:
            case CRIMSON_BUTTON:
            case WARPED_BUTTON:
            case POLISHED_BLACKSTONE_BUTTON:
            case MANGROVE_BUTTON:
            case BAMBOO_BUTTON:
            case CHERRY_BUTTON:
            
            // 压力板类
            case STONE_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case ACACIA_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case CRIMSON_PRESSURE_PLATE:
            case WARPED_PRESSURE_PLATE:
            case POLISHED_BLACKSTONE_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case MANGROVE_PRESSURE_PLATE:
            case BAMBOO_PRESSURE_PLATE:
            case CHERRY_PRESSURE_PLATE:
            
            // 红石类
            case REDSTONE_WIRE:
            case REPEATER:
            case COMPARATOR:
            case LEVER:
            case DAYLIGHT_DETECTOR:
            case SCULK_SENSOR:
            case CALIBRATED_SCULK_SENSOR:
            case SCULK_SHRIEKER:
            case SCULK_VEIN:
            case SCULK:
            case SCULK_CATALYST:
            
            // 铁轨类
            case RAIL:
            case POWERED_RAIL:
            case DETECTOR_RAIL:
            case ACTIVATOR_RAIL:
            
            // 门类
            case IRON_TRAPDOOR:
            case OAK_TRAPDOOR:
            case SPRUCE_TRAPDOOR:
            case BIRCH_TRAPDOOR:
            case JUNGLE_TRAPDOOR:
            case ACACIA_TRAPDOOR:
            case DARK_OAK_TRAPDOOR:
            case CRIMSON_TRAPDOOR:
            case WARPED_TRAPDOOR:
            case MANGROVE_TRAPDOOR:
            case BAMBOO_TRAPDOOR:
            case CHERRY_TRAPDOOR:
            
            // 蜡烛类
            case CANDLE:
            case WHITE_CANDLE:
            case ORANGE_CANDLE:
            case MAGENTA_CANDLE:
            case LIGHT_BLUE_CANDLE:
            case YELLOW_CANDLE:
            case LIME_CANDLE:
            case PINK_CANDLE:
            case GRAY_CANDLE:
            case LIGHT_GRAY_CANDLE:
            case CYAN_CANDLE:
            case PURPLE_CANDLE:
            case BLUE_CANDLE:
            case BROWN_CANDLE:
            case GREEN_CANDLE:
            case RED_CANDLE:
            case BLACK_CANDLE:
            case CANDLE_CAKE:
            case WHITE_CANDLE_CAKE:
            case ORANGE_CANDLE_CAKE:
            case MAGENTA_CANDLE_CAKE:
            case LIGHT_BLUE_CANDLE_CAKE:
            case YELLOW_CANDLE_CAKE:
            case LIME_CANDLE_CAKE:
            case PINK_CANDLE_CAKE:
            case GRAY_CANDLE_CAKE:
            case LIGHT_GRAY_CANDLE_CAKE:
            case CYAN_CANDLE_CAKE:
            case PURPLE_CANDLE_CAKE:
            case BLUE_CANDLE_CAKE:
            case BROWN_CANDLE_CAKE:
            case GREEN_CANDLE_CAKE:
            case RED_CANDLE_CAKE:
            case BLACK_CANDLE_CAKE:
            
            // 其他装饰性方块
            case TRIPWIRE:
            case TRIPWIRE_HOOK:
            case STRING:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case SNOW:
            case FLOWER_POT:
            case SCAFFOLDING:
            case TURTLE_EGG:
            case LANTERN:
            case SOUL_LANTERN:
            case CHAIN:
            case SMALL_DRIPLEAF:
            case BIG_DRIPLEAF:
            case HANGING_ROOTS:
            case SPORE_BLOSSOM:
            case POINTED_DRIPSTONE:
            case PITCHER_PLANT:
            case PITCHER_CROP:
            case TORCHFLOWER:
            case TORCHFLOWER_CROP:
            case DECORATED_POT:
            case PINK_PETALS:
            case PIGLIN_HEAD:
            case PIGLIN_WALL_HEAD:
            case DRAGON_HEAD:
            case DRAGON_WALL_HEAD:
            case PLAYER_HEAD:
            case PLAYER_WALL_HEAD:
            case ZOMBIE_HEAD:
            case ZOMBIE_WALL_HEAD:
            case CREEPER_HEAD:
            case CREEPER_WALL_HEAD:
            case SKELETON_SKULL:
            case SKELETON_WALL_SKULL:
            case WITHER_SKELETON_SKULL:
            case WITHER_SKELETON_WALL_SKULL:
            case END_ROD:
            case CHORUS_FLOWER:
            case CHORUS_PLANT:
            case LADDER:
            case VINE:
            case GLOW_LICHEN:
            case PAINTING:
            case ITEM_FRAME:
            case GLOW_ITEM_FRAME:
            case ARMOR_STAND:
            case CHISELED_BOOKSHELF:
            case SUSPICIOUS_SAND:
            case SUSPICIOUS_GRAVEL:
            case BAMBOO:
            case SMALL_AMETHYST_BUD:
            case MEDIUM_AMETHYST_BUD:
            case LARGE_AMETHYST_BUD:
            case AMETHYST_CLUSTER:
            case POWDER_SNOW:
            case MOSS_CARPET:
            case MOSS_BLOCK:
            case GLOW_BERRIES:
            case FLOWERING_AZALEA:
            case AZALEA:
            case BIG_DRIPLEAF_STEM:
            case LIGHTNING_ROD:
            case FROGSPAWN:
            case OCHRE_FROGLIGHT:
            case VERDANT_FROGLIGHT:
            case PEARLESCENT_FROGLIGHT:
            
            // 所有花类
            case DANDELION:
            case POPPY:
            case BLUE_ORCHID:
            case ALLIUM:
            case AZURE_BLUET:
            case RED_TULIP:
            case ORANGE_TULIP:
            case WHITE_TULIP:
            case PINK_TULIP:
            case OXEYE_DAISY:
            case CORNFLOWER:
            case LILY_OF_THE_VALLEY:
            case WITHER_ROSE:
            case SUNFLOWER:
            case LILAC:
            case ROSE_BUSH:
            case PEONY:
            case TALL_GRASS:
            case LARGE_FERN:
            case FERN:
            case DEAD_BUSH:
            case GRASS:
            case SEAGRASS:
            case SEA_PICKLE:
            
            // 农作物类
            case WHEAT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case MELON_STEM:
            case PUMPKIN_STEM:
            case ATTACHED_MELON_STEM:
            case ATTACHED_PUMPKIN_STEM:
            case SWEET_BERRY_BUSH:
            case CAVE_VINES:
            case CAVE_VINES_PLANT:
            case KELP:
            case KELP_PLANT:
            case BAMBOO_SAPLING:
            case COCOA:
            case SUGAR_CANE:
            case CACTUS:
            case NETHER_WART:
            
            // 告示牌类
            case OAK_SIGN:
            case SPRUCE_SIGN:
            case BIRCH_SIGN:
            case JUNGLE_SIGN:
            case ACACIA_SIGN:
            case DARK_OAK_SIGN:
            case CRIMSON_SIGN:
            case WARPED_SIGN:
            case MANGROVE_SIGN:
            case BAMBOO_SIGN:
            case CHERRY_SIGN:
            case OAK_WALL_SIGN:
            case SPRUCE_WALL_SIGN:
            case BIRCH_WALL_SIGN:
            case JUNGLE_WALL_SIGN:
            case ACACIA_WALL_SIGN:
            case DARK_OAK_WALL_SIGN:
            case CRIMSON_WALL_SIGN:
            case WARPED_WALL_SIGN:
            case MANGROVE_WALL_SIGN:
            case BAMBOO_WALL_SIGN:
            case CHERRY_WALL_SIGN:

            // 珊瑚类
            case TUBE_CORAL:
            case BRAIN_CORAL:
            case BUBBLE_CORAL:
            case FIRE_CORAL:
            case HORN_CORAL:
            case TUBE_CORAL_FAN:
            case BRAIN_CORAL_FAN:
            case BUBBLE_CORAL_FAN:
            case FIRE_CORAL_FAN:
            case HORN_CORAL_FAN:
            case TUBE_CORAL_WALL_FAN:
            case BRAIN_CORAL_WALL_FAN:
            case BUBBLE_CORAL_WALL_FAN:
            case FIRE_CORAL_WALL_FAN:
            case HORN_CORAL_WALL_FAN:
            case DEAD_TUBE_CORAL:
            case DEAD_BRAIN_CORAL:
            case DEAD_BUBBLE_CORAL:
            case DEAD_FIRE_CORAL:
            case DEAD_HORN_CORAL:
            case DEAD_TUBE_CORAL_FAN:
            case DEAD_BRAIN_CORAL_FAN:
            case DEAD_BUBBLE_CORAL_FAN:
            case DEAD_FIRE_CORAL_FAN:
            case DEAD_HORN_CORAL_FAN:
            case DEAD_TUBE_CORAL_WALL_FAN:
            case DEAD_BRAIN_CORAL_WALL_FAN:
            case DEAD_BUBBLE_CORAL_WALL_FAN:
            case DEAD_FIRE_CORAL_WALL_FAN:
            case DEAD_HORN_CORAL_WALL_FAN:

            // 地毯类
            case WHITE_CARPET:
            case ORANGE_CARPET:
            case MAGENTA_CARPET:
            case LIGHT_BLUE_CARPET:
            case YELLOW_CARPET:
            case LIME_CARPET:
            case PINK_CARPET:
            case GRAY_CARPET:
            case LIGHT_GRAY_CARPET:
            case CYAN_CARPET:
            case PURPLE_CARPET:
            case BLUE_CARPET:
            case BROWN_CARPET:
            case GREEN_CARPET:
            case RED_CARPET:
            case BLACK_CARPET:

            // 其他新增方块
            case CONDUIT:
            case DRAGON_EGG:
            case CAMPFIRE:
            case SOUL_CAMPFIRE:
            case WEEPING_VINES:
            case WEEPING_VINES_PLANT:
            case TWISTING_VINES:
            case TWISTING_VINES_PLANT:
            case NETHER_SPROUTS:
            case WARPED_ROOTS:
            case CRIMSON_ROOTS:
            case WARPED_FUNGUS:
            case CRIMSON_FUNGUS:
            case LILY_PAD:
            case BREWING_STAND:
            case BELL:
            case BARRIER:
            case LIGHT:
            case STRUCTURE_VOID:
            case STRUCTURE_BLOCK:
            case JIGSAW:
            case COMMAND_BLOCK:
            case CHAIN_COMMAND_BLOCK:
            case REPEATING_COMMAND_BLOCK:
            case BEACON:
            case END_PORTAL_FRAME:
            case END_GATEWAY:
            case END_PORTAL:
            case NETHER_PORTAL:
            case MOVING_PISTON:
            case PISTON_HEAD:
            case OBSERVER:
            case TARGET:
            case BEE_NEST:
            case BEEHIVE:
            case COMPOSTER:
            case LECTERN:
            case GRINDSTONE:
            case STONECUTTER:
            case LOOM:
            case CARTOGRAPHY_TABLE:
            case FLETCHING_TABLE:
            case SMITHING_TABLE:
            case LODESTONE:
            case RESPAWN_ANCHOR:
            case ANCIENT_DEBRIS:
            case NETHERITE_BLOCK:
            case CRYING_OBSIDIAN:
            case BLACKSTONE:
            case GILDED_BLACKSTONE:
            case POLISHED_BLACKSTONE:
            case CHISELED_POLISHED_BLACKSTONE:
            case POLISHED_BLACKSTONE_BRICKS:
            case CRACKED_POLISHED_BLACKSTONE_BRICKS:
            case BUDDING_AMETHYST:
            case TINTED_GLASS:
            case TUFF:
            case CALCITE:
            case OXIDIZED_COPPER:
            case WEATHERED_COPPER:
            case EXPOSED_COPPER:
            case COPPER_BLOCK:
            case OXIDIZED_CUT_COPPER:
            case WEATHERED_CUT_COPPER:
            case EXPOSED_CUT_COPPER:
            case CUT_COPPER:
            case WAXED_COPPER_BLOCK:
            case WAXED_WEATHERED_COPPER:
            case WAXED_EXPOSED_COPPER:
            case WAXED_OXIDIZED_COPPER:
            case WAXED_CUT_COPPER:
            case WAXED_WEATHERED_CUT_COPPER:
            case WAXED_EXPOSED_CUT_COPPER:
            case WAXED_OXIDIZED_CUT_COPPER:
            // 删除所有铜块楼梯和台阶
                return true;
            default:
                return false;
        }
    }
    
    private Material getDropItem(Material blockMaterial) {
        switch (blockMaterial) {
            // 火把类
            case WALL_TORCH:
                return Material.TORCH;
            case REDSTONE_WALL_TORCH:
                return Material.REDSTONE_TORCH;
            case SOUL_WALL_TORCH:
                return Material.SOUL_TORCH;
            
            // 农作物类
            case SWEET_BERRY_BUSH:
                return Material.SWEET_BERRIES;
            case CAVE_VINES:
            case CAVE_VINES_PLANT:
                return Material.GLOW_BERRIES;
            case KELP_PLANT:
                return Material.KELP;
            case BAMBOO_SAPLING:
                return Material.BAMBOO;
            case COCOA:
                return Material.COCOA_BEANS;
            case WHEAT:
                return Material.WHEAT;
            case CARROTS:
                return Material.CARROT;
            case POTATOES:
                return Material.POTATO;
            case BEETROOTS:
                return Material.BEETROOT;
            case MELON_STEM:
                return Material.MELON_SEEDS;
            case PUMPKIN_STEM:
                return Material.PUMPKIN_SEEDS;
            case ATTACHED_MELON_STEM:
                return Material.MELON_SEEDS;
            case ATTACHED_PUMPKIN_STEM:
                return Material.PUMPKIN_SEEDS;
            case PITCHER_CROP:
                return Material.PITCHER_POD;
            case TORCHFLOWER_CROP:
                return Material.TORCHFLOWER_SEEDS;
            
            // 告示牌类
            case OAK_WALL_SIGN:
                return Material.OAK_SIGN;
            case SPRUCE_WALL_SIGN:
                return Material.SPRUCE_SIGN;
            case BIRCH_WALL_SIGN:
                return Material.BIRCH_SIGN;
            case JUNGLE_WALL_SIGN:
                return Material.JUNGLE_SIGN;
            case ACACIA_WALL_SIGN:
                return Material.ACACIA_SIGN;
            case DARK_OAK_WALL_SIGN:
                return Material.DARK_OAK_SIGN;
            case CRIMSON_WALL_SIGN:
                return Material.CRIMSON_SIGN;
            case WARPED_WALL_SIGN:
                return Material.WARPED_SIGN;
            case MANGROVE_WALL_SIGN:
                return Material.MANGROVE_SIGN;
            case BAMBOO_WALL_SIGN:
                return Material.BAMBOO_SIGN;
            case CHERRY_WALL_SIGN:
                return Material.CHERRY_SIGN;
            
            // 头颅类
            case PLAYER_WALL_HEAD:
                return Material.PLAYER_HEAD;
            case ZOMBIE_WALL_HEAD:
                return Material.ZOMBIE_HEAD;
            case CREEPER_WALL_HEAD:
                return Material.CREEPER_HEAD;
            case DRAGON_WALL_HEAD:
                return Material.DRAGON_HEAD;
            case PIGLIN_WALL_HEAD:
                return Material.PIGLIN_HEAD;
            case SKELETON_WALL_SKULL:
                return Material.SKELETON_SKULL;
            case WITHER_SKELETON_WALL_SKULL:
                return Material.WITHER_SKELETON_SKULL;
            
            // 珊瑚扇类
            case TUBE_CORAL_WALL_FAN:
                return Material.TUBE_CORAL_FAN;
            case BRAIN_CORAL_WALL_FAN:
                return Material.BRAIN_CORAL_FAN;
            case BUBBLE_CORAL_WALL_FAN:
                return Material.BUBBLE_CORAL_FAN;
            case FIRE_CORAL_WALL_FAN:
                return Material.FIRE_CORAL_FAN;
            case HORN_CORAL_WALL_FAN:
                return Material.HORN_CORAL_FAN;
            case DEAD_TUBE_CORAL_WALL_FAN:
                return Material.DEAD_TUBE_CORAL_FAN;
            case DEAD_BRAIN_CORAL_WALL_FAN:
                return Material.DEAD_BRAIN_CORAL_FAN;
            case DEAD_BUBBLE_CORAL_WALL_FAN:
                return Material.DEAD_BUBBLE_CORAL_FAN;
            case DEAD_FIRE_CORAL_WALL_FAN:
                return Material.DEAD_FIRE_CORAL_FAN;
            case DEAD_HORN_CORAL_WALL_FAN:
                return Material.DEAD_HORN_CORAL_FAN;
            
            // 红石类
            case REDSTONE_WIRE:
                return Material.REDSTONE;
            
            // 作物类
            case SUGAR_CANE:
                return Material.SUGAR_CANE;
            case CACTUS:
                return Material.CACTUS;
            case NETHER_WART:
                return Material.NETHER_WART;
            
            // 其他特殊方块
            case TRIPWIRE:
                return Material.STRING;
            case SNOW:
                return Material.SNOWBALL;
            case GRASS:
            case TALL_GRASS:
                return null; // 这些方块不掉落物品
            case SEAGRASS:
            case TALL_SEAGRASS:
                return null; // 这些方块不掉落物品
            case VINE:
                return Material.VINE;
            case GLOW_LICHEN:
                return Material.GLOW_LICHEN;
            case WEEPING_VINES:
            case WEEPING_VINES_PLANT:
                return Material.WEEPING_VINES;
            case TWISTING_VINES:
            case TWISTING_VINES_PLANT:
                return Material.TWISTING_VINES;
            case SCAFFOLDING:
                return Material.SCAFFOLDING;
            case BIG_DRIPLEAF:
            case BIG_DRIPLEAF_STEM:
                return Material.BIG_DRIPLEAF;
            case SMALL_DRIPLEAF:
                return Material.SMALL_DRIPLEAF;
            case SPORE_BLOSSOM:
                return Material.SPORE_BLOSSOM;
            case HANGING_ROOTS:
                return Material.HANGING_ROOTS;
            case MOSS_CARPET:
                return Material.MOSS_CARPET;
            case PINK_PETALS:
                return Material.PINK_PETALS;
            
            default:
                return blockMaterial;
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onFallingBlockSpawn(EntitySpawnEvent event) {
        if (!plugin.getConfig().getBoolean("falling-block.enabled", true)) {
            return;
        }

        Entity entity = event.getEntity();
        if (!(entity instanceof FallingBlock)) {
            return;
        }

        FallingBlock fallingBlock = (FallingBlock) entity;
        
        new BukkitRunnable() {
            private Block lastCheckedBlock = null;
            private boolean hasDestroyed = false;
            
            @Override
            public void run() {
                if (!fallingBlock.isValid() || fallingBlock.isDead()) {
                    this.cancel();
                    return;
                }
                
                Block blockBelow = fallingBlock.getLocation().subtract(0, 1, 0).getBlock();
                
                if (lastCheckedBlock != null && lastCheckedBlock.equals(blockBelow)) {
                    return;
                }
                
                lastCheckedBlock = blockBelow;
                hasDestroyed = false;
                
                // 检查下方方块
                if (canBreakFallingBlock(blockBelow.getType())) {
                    breakBlock(blockBelow);
                    hasDestroyed = true;
                }
                
                // 检查周围的墙上方块
                for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}) {
                    Block adjacentBlock = blockBelow.getRelative(face);
                    if (canBreakFallingBlock(adjacentBlock.getType())) {
                        breakBlock(adjacentBlock);
                        hasDestroyed = true;
                    }
                }
                
                // 如果这一层没有破坏任何方块，并且下方是实心方块，则取消任务
                if (!hasDestroyed && !canBreakFallingBlock(blockBelow.getType()) && blockBelow.getType().isSolid()) {
                    this.cancel();
                }
            }
            
            private void breakBlock(Block block) {
                if (plugin.getConfig().getBoolean("falling-block.drop-items", true)) {
                    Material dropItem = getDropItem(block.getType());
                    if (dropItem != null) {
                        block.getWorld().dropItemNaturally(block.getLocation(), 
                            new ItemStack(dropItem, 1));
                    }
                }
                
                if (plugin.getConfig().getBoolean("falling-block.particle-effect", true)) {
                    block.getWorld().spawnParticle(Particle.BLOCK_CRACK, 
                        block.getLocation().add(0.5, 0.5, 0.5), 
                        10, 0.3, 0.3, 0.3, 0, 
                        block.getBlockData());
                }
                
                block.setType(Material.AIR);
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }
} 
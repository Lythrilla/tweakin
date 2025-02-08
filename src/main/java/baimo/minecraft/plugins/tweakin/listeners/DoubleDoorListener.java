package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;

public class DoubleDoorListener implements Listener {
    private final Tweakin plugin;
    
    public DoubleDoorListener(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDoorInteract(PlayerInteractEvent event) {
        if (!plugin.getConfig().getBoolean("double-door.enabled", true)) {
            return;
        }
        
        // 只处理右键点击
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }
        
        // 检查是否是木门
        if (!isWoodenDoor(clickedBlock.getType())) {
            return;
        }
        
        // 获取门的数据
        Door doorData = (Door) clickedBlock.getBlockData();
        
        // 如果点击的是上半部分，获取下半部分
        if (doorData.getHalf() == Bisected.Half.TOP) {
            clickedBlock = clickedBlock.getRelative(BlockFace.DOWN);
            doorData = (Door) clickedBlock.getBlockData();
        }
        
        // 寻找相邻的门
        Block adjacentDoor = findAdjacentDoor(clickedBlock, doorData);
        if (adjacentDoor == null) {
            return;
        }
        
        // 获取相邻门的数据
        Door adjacentDoorData = (Door) adjacentDoor.getBlockData();
        
        // 同步两个门的状态
        boolean newState = !doorData.isOpen();
        doorData.setOpen(newState);
        // 如果两个门的铰链在同一侧，状态应该相反
        adjacentDoorData.setOpen(doorData.getHinge() == adjacentDoorData.getHinge() ? !newState : newState);
        
        // 应用更改
        clickedBlock.setBlockData(doorData);
        adjacentDoor.setBlockData(adjacentDoorData);
    }
    
    private Block findAdjacentDoor(Block door, Door doorData) {
        // 检查四个方向
        BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
        
        for (BlockFace face : faces) {
            Block adjacent = door.getRelative(face);
            if (!isWoodenDoor(adjacent.getType())) {
                continue;
            }
            
            Door adjacentDoorData = (Door) adjacent.getBlockData();
            if (isDoubleDoor(doorData, adjacentDoorData)) {
                return adjacent;
            }
        }
        
        return null;
    }
    
    private boolean isWoodenDoor(Material material) {
        return material == Material.OAK_DOOR ||
               material == Material.SPRUCE_DOOR ||
               material == Material.BIRCH_DOOR ||
               material == Material.JUNGLE_DOOR ||
               material == Material.ACACIA_DOOR ||
               material == Material.DARK_OAK_DOOR ||
               material == Material.CRIMSON_DOOR ||
               material == Material.WARPED_DOOR ||
               material == Material.MANGROVE_DOOR ||
               material == Material.BAMBOO_DOOR ||
               material == Material.CHERRY_DOOR;
    }
    
    private boolean isDoubleDoor(Door door1, Door door2) {
        // 检查两个门是否朝向相同
        if (door1.getFacing() != door2.getFacing()) {
            return false;
        }
        
        // 检查两个门是否是一对（一个左铰链，一个右铰链）
        return door1.getHinge() != door2.getHinge();
    }
} 

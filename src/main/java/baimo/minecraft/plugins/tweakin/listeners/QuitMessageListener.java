package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitMessageListener implements Listener {
    private final Tweakin plugin;
    
    public QuitMessageListener(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!plugin.getConfig().getBoolean("quit-message.enabled", true)) {
            return;
        }
        
        Player player = event.getPlayer();
        
        // 取消默认的退服消息
        event.setQuitMessage(null);
        
        // 发送自定义退服消息
        String quitMessage = plugin.getConfig().getString("quit-message.quit", "&8[&c-&8] &7玩家 &f%player_name% &7离开了游戏");
        quitMessage = quitMessage.replace("%player_name%", player.getName());
        quitMessage = ChatColor.translateAlternateColorCodes('&', quitMessage);
        plugin.getServer().broadcastMessage(quitMessage);
    }
} 
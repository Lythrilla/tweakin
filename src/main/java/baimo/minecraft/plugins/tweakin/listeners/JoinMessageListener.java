package baimo.minecraft.plugins.tweakin.listeners;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.ChatColor;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class JoinMessageListener implements Listener {
    private final Tweakin plugin;
    
    public JoinMessageListener(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getConfig().getBoolean("join-message.enabled", true)) {
            return;
        }
        
        Player player = event.getPlayer();
        
        // 取消默认的进服消息
        event.setJoinMessage(null);
        
        // 发送自定义进服消息
        String joinMessage = plugin.getConfig().getString("join-message.join", "&8[&a+&8] &7玩家 &f%player_name% &7加入了游戏");
        joinMessage = joinMessage.replace("%player_name%", player.getName());
        joinMessage = ChatColor.translateAlternateColorCodes('&', joinMessage);
        plugin.getServer().broadcastMessage(joinMessage);
        
        // 发送个人欢迎消息
        List<String> messages = plugin.getConfig().getStringList("join-message.messages");
        for (String message : messages) {
            // 替换变量
            String parsedMessage = replaceVariables(message, player);
            // 替换颜色代码
            parsedMessage = ChatColor.translateAlternateColorCodes('&', parsedMessage);
            player.sendMessage(parsedMessage);
        }
    }

    private String replaceVariables(String message, Player player) {
        return message
            .replace("%player_name%", player.getName())
            .replace("%server_online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
            .replace("%statistic_time_played%", formatPlayTime(player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE)))
            .replace("%player_last_login%", formatLastLogin(player.getLastPlayed()));
    }

    private String formatPlayTime(int minutes) {
        Duration duration = Duration.ofMinutes(minutes);
        long hours = duration.toHours();
        long remainingMinutes = duration.toMinutesPart();
        return String.format("%d小时%d分钟", hours, remainingMinutes);
    }

    private String formatLastLogin(long lastPlayed) {
        if (lastPlayed == 0) return "首次登录";
        LocalDateTime lastLogin = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(lastPlayed),
            ZoneId.systemDefault()
        );
        return lastLogin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
} 

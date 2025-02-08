package baimo.minecraft.plugins.tweakin.commands;

import baimo.minecraft.plugins.tweakin.Tweakin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class ReloadCommand implements CommandExecutor {
    private final Tweakin plugin;
    
    public ReloadCommand(Tweakin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("tweakin")) {
            return false;
        }
        
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(ChatColor.RED + "用法: /tweakin reload");
            return true;
        }
        
        plugin.reloadPlugin();
        sender.sendMessage(ChatColor.GREEN + "插件配置已重新加载！");
        return true;
    }
} 
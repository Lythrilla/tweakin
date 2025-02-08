package baimo.minecraft.plugins.tweakin;

import org.bukkit.plugin.java.JavaPlugin;

import baimo.minecraft.plugins.tweakin.commands.ReloadCommand;
import baimo.minecraft.plugins.tweakin.listeners.AutoPlantListener;
import baimo.minecraft.plugins.tweakin.listeners.BlockDamageListener;
import baimo.minecraft.plugins.tweakin.listeners.CactusProtectionListener;
import baimo.minecraft.plugins.tweakin.listeners.CauldronEvaporateListener;
import baimo.minecraft.plugins.tweakin.listeners.DoubleDoorListener;
import baimo.minecraft.plugins.tweakin.listeners.DragonEggListener;
import baimo.minecraft.plugins.tweakin.listeners.FallingBlockListener;
import baimo.minecraft.plugins.tweakin.listeners.HungerListener;
import baimo.minecraft.plugins.tweakin.listeners.JoinMessageListener;
import baimo.minecraft.plugins.tweakin.listeners.QuitMessageListener;
import baimo.minecraft.plugins.tweakin.listeners.SilkTouchPlusListener;

public class Tweakin extends JavaPlugin {
    private AutoPlantListener autoPlantListener;
    private BlockDamageListener blockDamageListener;
    private CauldronEvaporateListener cauldronEvaporateListener;
    private DragonEggListener dragonEggListener;
    private FallingBlockListener fallingBlockListener;
    private SilkTouchPlusListener silkTouchPlusListener;
    private HungerListener hungerListener;
    private DoubleDoorListener doubleDoorListener;
    private JoinMessageListener joinMessageListener;
    private QuitMessageListener quitMessageListener;
    private CactusProtectionListener cactusProtectionListener;
    
    @Override
    public void onEnable() {
        // 保存默认配置
        saveDefaultConfig();
        
        // 注册监听器
        registerListeners();
        
        // 注册命令
        registerCommands();
        
        // 显示启动消息
        String prefix = "\u001B[38;2;85;85;85m"; // 深灰色
        String pink = "\u001B[38;2;255;105;180m"; // 粉色
        String white = "\u001B[38;2;255;255;255m"; // 白色
        String skyBlue = "\u001B[38;2;135;206;235m"; // 天蓝色
        String yellow = "\u001B[38;2;255;255;0m"; // 黄色
        String reset = "\u001B[0m";

        getLogger().info("");
        getLogger().info(prefix + "[ " + pink + "Tweakin" + prefix + " ]" + reset);
        getLogger().info(white + "欢迎加入插件社区 " + skyBlue + "QQ群: " + yellow + "528651839" + reset);
        getLogger().info(white + "获取更多插件资讯与技术支持" + reset);
        getLogger().info("");
        getLogger().info(white + "插件已成功启动！" + reset);
        getLogger().info("");
    }
    
    private void registerListeners() {
        autoPlantListener = new AutoPlantListener(this);
        blockDamageListener = new BlockDamageListener(this);
        cauldronEvaporateListener = new CauldronEvaporateListener(this);
        dragonEggListener = new DragonEggListener(this);
        fallingBlockListener = new FallingBlockListener(this);
        silkTouchPlusListener = new SilkTouchPlusListener(this);
        hungerListener = new HungerListener(this);
        doubleDoorListener = new DoubleDoorListener(this);
        joinMessageListener = new JoinMessageListener(this);
        quitMessageListener = new QuitMessageListener(this);
        cactusProtectionListener = new CactusProtectionListener(this);
        
        getServer().getPluginManager().registerEvents(autoPlantListener, this);
        getServer().getPluginManager().registerEvents(blockDamageListener, this);
        getServer().getPluginManager().registerEvents(cauldronEvaporateListener, this);
        getServer().getPluginManager().registerEvents(dragonEggListener, this);
        getServer().getPluginManager().registerEvents(fallingBlockListener, this);
        getServer().getPluginManager().registerEvents(silkTouchPlusListener, this);
        getServer().getPluginManager().registerEvents(hungerListener, this);
        getServer().getPluginManager().registerEvents(doubleDoorListener, this);
        getServer().getPluginManager().registerEvents(joinMessageListener, this);
        getServer().getPluginManager().registerEvents(quitMessageListener, this);
        getServer().getPluginManager().registerEvents(cactusProtectionListener, this);
    }
    
    private void registerCommands() {
        getCommand("tweakin").setExecutor(new ReloadCommand(this));
    }
    
    public void reloadPlugin() {
        // 重新加载配置文件
        reloadConfig();
        
        // 清理所有监听器的数据
        if (autoPlantListener != null) autoPlantListener.cleanup();
        if (blockDamageListener != null) blockDamageListener.cleanup();
        if (hungerListener != null) hungerListener.cleanup();
        
        // 重新注册所有监听器
        registerListeners();
        
        // 发送重载成功消息
        getLogger().info("插件重载成功！");
    }
    
    @Override
    public void onDisable() {
        // 清理所有监听器
        if (autoPlantListener != null) autoPlantListener.cleanup();
        if (blockDamageListener != null) blockDamageListener.cleanup();
        if (hungerListener != null) hungerListener.cleanup();
        if (silkTouchPlusListener != null) silkTouchPlusListener.clearCache();
        
        // 发送关闭消息
        getLogger().info("插件已关闭！");
    }
}

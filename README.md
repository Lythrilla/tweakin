# Tweakin - Minecraft 服务器增强插件

![License](https://img.shields.io/badge/License-MIT-green)

[English](README_EN.md) | 简体中文

## 📝 简介

Tweakin 是一个为 Minecraft 服务器设计的多功能增强插件。它提供了多个实用的游戏机制改进，让服务器游戏体验更加便捷和有趣。

## ✨ 功能特性

- 🌱 自动种植系统
  - 掉落的种子和作物会自动种植
  - 支持所有植物类型
  - 可配置种植延迟
  - 粒子效果反馈
- 🚪 双门同步
  - 自动同步相邻门的开关状态
  - 支持所有木门类型
- 🌵 仙人掌保护
  - 防止物品被仙人掌破坏
  - 物品会被弹开而不是销毁
- 🏃 进退服提示
  - 自定义进服欢迎消息
  - 显示玩家在线时长
  - 美观的退服提示
- 🍖 死亡饥饿保留
  - 死亡后保留饥饿值
  - 饥饿死亡特殊处理
- 🔨 精准采集增强
  - 可配置额外可精准采集的方块
  - 支持刷怪笼采集
- 🥚 末影龙蛋掉落
  - 每次击杀末影龙都掉落龙蛋
- 🕯️ 危险方块伤害
  - 站在蜡烛上会受到伤害
  - 站在切石机上会受到伤害

## 📥 安装

1. 下载最新版本的 `Tweakin.jar`
2. 将文件放入服务器的 `plugins` 文件夹
3. 重启服务器
4. 开始享受增强功能！

## ⚙️ 配置

插件会在首次运行时自动创建配置文件：
```
📁 plugins/Tweakin/
   └── 📄 config.yml    - 主配置文件
```

### 配置详情

`config.yml` 包含以下主要设置：

```yaml
# 自动种植设置
auto-plant:
  enabled: true
  plant-delay: 15
  particle-effect: true

# 双开门设置
double-door:
  enabled: true

# 仙人掌保护设置
cactus-protection:
  enabled: true

# 进服提示设置
join-message:
  enabled: true
  messages:
    - "&b欢迎回来，&f%player_name%"
    - "&7▶ 在线时长: &f%statistic_time_played%"

# 死亡饥饿值设置
hunger:
  enabled: true
  starvation-respawn-food: 4

# 精准采集增强设置
silk-touch-plus:
  enabled: true
  blocks:
    "minecraft:spawner": true
```

## 📌 命令

| 命令 | 权限 | 描述 |
|------|------|------|
| `/tweakin reload` | `tweakin.reload` | 重新加载插件配置 |

## 🔧 要求

- ☕ Java 17+
- 🎮 Minecraft 1.20.x
- 🛠️ Spigot/Paper 服务端

## 💬 支持

需要帮助？欢迎加入我们！

[![加入QQ群](https://img.shields.io/badge/QQ群-528651839-blue)](https://jq.qq.com/?_wv=1027&k=528651839)

## 📜 开源协议

本项目采用 [MIT](LICENSE) 开源协议。
"# tweakin" 

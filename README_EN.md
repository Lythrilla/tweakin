# Tweakin - Minecraft Server Enhancement Plugin

![License](https://img.shields.io/badge/License-MIT-green)

English | [简体中文](README.md)

## 📝 Introduction

Tweakin is a multi-functional enhancement plugin designed for Minecraft servers. It provides various useful game mechanism improvements to make the server gaming experience more convenient and fun.

## ✨ Features

- 🌱 Auto Planting System
  - Automatically plants dropped seeds and crops
  - Supports all plant types
  - Configurable planting delay
  - Particle effect feedback
- 🚪 Double Door Sync
  - Automatically syncs adjacent door states
  - Supports all wooden door types
- 🌵 Cactus Protection
  - Prevents items from being destroyed by cacti
  - Items bounce away instead of being destroyed
- 🏃 Join/Quit Messages
  - Custom welcome messages
  - Display player online time
  - Beautiful quit notifications
- 🍖 Death Hunger Retention
  - Retains hunger value after death
  - Special handling for starvation deaths
- 🔨 Silk Touch Enhancement
  - Configurable additional blocks for silk touch
  - Supports spawner collection
- 🥚 Dragon Egg Drops
  - Dragon egg drops on every dragon kill
- 🕯️ Dangerous Block Damage
  - Take damage when standing on candles
  - Take damage when standing on stonecutters

## 📥 Installation

1. Download the latest version of `Tweakin.jar`
2. Place the file in your server's `plugins` folder
3. Restart the server
4. Enjoy the enhanced features!

## ⚙️ Configuration

The plugin will automatically create configuration files on first run:
```
📁 plugins/Tweakin/
   └── 📄 config.yml    - Main configuration file
```

### Configuration Details

`config.yml` contains the following main settings:

```yaml
# Auto Plant Settings
auto-plant:
  enabled: true
  plant-delay: 15
  particle-effect: true

# Double Door Settings
double-door:
  enabled: true

# Cactus Protection Settings
cactus-protection:
  enabled: true

# Join Message Settings
join-message:
  enabled: true
  messages:
    - "&bWelcome back, &f%player_name%"
    - "&7▶ Playtime: &f%statistic_time_played%"

# Death Hunger Settings
hunger:
  enabled: true
  starvation-respawn-food: 4

# Silk Touch Plus Settings
silk-touch-plus:
  enabled: true
  blocks:
    "minecraft:spawner": true
```

## 📌 Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/tweakin reload` | `tweakin.reload` | Reload plugin configuration |

## 🔧 Requirements

- ☕ Java 17+
- 🎮 Minecraft 1.20.x
- 🛠️ Spigot/Paper Server

## 💬 Support

Need help? Join our community!

[![Join QQ Group](https://img.shields.io/badge/QQ_Group-528651839-blue)](https://jq.qq.com/?_wv=1027&k=528651839)

## 📜 License

This project is licensed under the [MIT](LICENSE) License. 
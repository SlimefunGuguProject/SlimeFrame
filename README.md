<div align="center">
  <img src="https://github.com/VoperAD/SlimeFrame/raw/main/images/SFrame-Banner.png" alt="SlimeFrame-Banner_2" width="1280" height="500">
</div>

## 下载

[![Build Status](https://builds.guizhanss.com/SlimefunGuguProject/SlimeFrame/main/badge.svg)](https://builds.guizhanss.com/SlimefunGuguProject/SlimeFrame/main)

## 关于本附属

SlimeFrame（粘液战甲）是一个受到 WarFrame 启发的 Slimefun 附属，添加了新的机器、发电机以及其他的物品，能让你的游戏体验变得更加丰富。  
添加的物品有：
- 自动交易机：可以自动化村民交易
- 物品投影仪：可以展示物品投影
- 混凝土/羊毛生成器

花一点时间来发掘更多的内容！

## 需求

Minecraft 版本：1.19+
Slimefun 版本：RC-32+

## 指令

### 允许所有人使用

- /sframe relics -> 打开遗物背包
- /sframe refine <refinement> -> Refine the relic that is in your hand.
- /sframe traces [player] -> 查看玩家的虚空光体数量（不输入玩家名则查看自己的）

### 需要管理员权限

- /sframe invsee <player> -> 查看玩家的遗物背包

## 这个附属有什么？

### 新资源

有很多新资源用于制作新的机器和发电机。其中一些是通过使用诺萨姆切割器在Minecraft矿石上采矿获得的新矿石。**放置的方块不会掉落这些矿石**。

### 遗物

遗物是用于获取 Prime 组件的物品，而这些组件是制作 Prime 机器所必需的。每个遗物都有3个铜奖励、2个银奖励和1个金奖励（普通、罕见和稀有）。共有四种类型的遗物：

- 古纪（Lith）：捕获50条鱼获得。
- 前纪（Meso）：消灭750个实体获得。
- 中纪（Neo）：破坏10000个方块获得。
- 后纪（Axi）：放置750个方块获得。

使用命令```/sframe relics```来查看你的遗物库存。
服主可以在config.yml文件中配置这些数量。

#### 如何打开遗物？

遗物有两个在描述中显示的属性：反应物和精炼度。要打开遗物，你必须用10个反应物填充它。要获得反应物，将**一个**遗物放在副手，并杀死末影人直到它被填满。每杀死一个末影人，有25%的几率获得一个反应物。当达到10个反应物时，只需右键点击手中的遗物，它将打开并给予你奖励。

***基岩版玩家：***如果你是基岩版玩家，你必须将遗物放在快捷栏的第一个槽位中（这是实验性的并且仍在测试中，所以如果有任何问题，请进行报告）。

#### 遗物精炼

遗物可以精炼以增加获得更稀有奖励的几率。要精炼遗物，使用命令```/sframe refine <精炼等级（英文）>```。要精炼遗物，你需要**虚空光体**，每次打开遗物都会得到1到20个随机数量的虚空光体。要检查你有多少虚空光体，请使用命令```/sframe traces```。共有四种精炼：

- 完整（INTACT）：默认精炼。
- 优良（EXCEPTIONAL）：需要25个虚空光体。
- 无暇（FLAWLESS）：需要50个虚空光体。
- 光辉（RADIANT）：需要100个虚空光体。

### 叠加式发电机

一种新型的能量发电机，它会检查其旁边、上方或下方的方块是否也是叠加式发电机。对于每个检测到的叠加式发电机，将会增加一定量的能量，该能量由发电机描述中的新属性**奖励能量**指示。
**注意：**不会计算对角线方向的方块。这意味着叠加式能量发电机将检查总共6个方块（每个侧面1个，上方1个，下方1个）。

### 自动交易机

一种可以自动与村民交易的新机器。要使其工作，你需要一份***商人灵魂契约***。获得契约后，选择一个村民并用手中的契约右键点击它。它将杀死该村民并将其交易填入契约中。之后，将契约放入自动交易机并选择其中一项交易。

**自动化新矿石的农场：**该机器可用于自动生产使用诺萨姆切割器获取的新矿石。每当村民获得新的交易时，它有很小的几率提供附加组件中的新资源之一。在这种情况下，再次使用商人灵魂契约并使用自动交易机与这份新契约进行交易。


## Credits

Thanks to <a href="https://minecraft-heads.com/">Minecraft Heads</a> for the heads the addon uses.
A big thanks to all the people that helped me in the programming channel in the Slimefun Discord Server.
Thanks to the server **AbsolutGG** for letting me test the addon.

## Donate

If you want to support the project and make someone's day, you can donate on <a href="https://ko-fi.com/voper">Ko-Fi</a> 🙂

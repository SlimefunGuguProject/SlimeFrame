<div align="center">
  <img src="https://github.com/VoperAD/SlimeFrame/blob/main/images/SFrame-Banner.png" alt="SlimeFrame-Banner_2" width="1280" height="500">
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#download">Download</a></li>
    <li><a href="#about-the-addon">About The Project</a></li>
    <li><a href="#requirements">Requirements</a></li>
    <li><a href="#commands">Commands</a></li>
    <li>
      <a href="#what-does-it-add">What does it add?</a>
      <ul>
        <li><a href="#new-resources">New Resources</a></li>
        <li><a href="#relics">Relics</a></li>
        <li><a href="#cumulative-generators">Cumulative Generators</a></li>
        <li><a href="#auto-trader">Auto Trader</a></li>
      </ul>
    </li>
    <li><a href="#credits">Credits</a></li>
    <li><a href="#donate">Donate</a></li>
  </ol>
</details>

## Download

- <a href="https://www.spigotmc.org/resources/slimeframe.111432/">Download from Spigot</a>

## About the Addon

SlimeFrame is a Slimefun addon inspired by Warframe that adds new machines, energy generators, and a lot of other items to make your gameplay even more fun. Among the added items are the Auto Trader, capable of making automatic trades with villagers, the Item Projector, which is basically a hologram for items, concrete and wool generators, and much more. Take your time to explore the full potential of this addon!

## Requirements

Minecraft Version: 1.19+ (_it might work on servers that support lower versions as long as 1.19 is supported too_)
Slimefun Version: RC-32+

## Commands

### Anyone Permission:

- /sframe relics -> Open your relics inventory
- /sframe refine <refinement> -> Refine the relic that is in your hand.
- /sframe traces [player] -> Show the amount of void traces a player has

### Admin Permission:

- /sframe invsee <player> -> Open a player's relics inventory

## What Does It Add?

### New Resources

There are a lot of new resources used to craft the new machines and generators. Some of them are new ores that you can get by mining Minecraft ores using a Nosam Pickaxe, a special pickaxe from the addon. **Placed blocks do not drop these ores.**

### Relics

Relics are items used to obtain Prime Components, which are components required to craft Prime-Tier machines, the best machines of the addon. Every relic has 3 Bronze rewards, 2 Silver rewards, and 1 Gold reward (common, uncommon, and rare). There are four types of relics:

- Lith: Obtained every 50 fishes caught.
- Meso: Obtained every 750 entities killed.
- Neo: Obtained every 10000 blocks broken.
- Axi: Obtained every 750 blocks placed.

Use the command ```/sframe relics``` to check your relic inventory.
Server owners can configure these numbers in the config.yml file.

#### How to Open a Relic?

Relics have two attributes shown in their lore: Reactants and Refinement. To open a relic, you must fill it up with 10 reactants. To get reactants, put **ONE** relic in your off hand and kill Endermen until it fills up. Every Enderman killed has a 25% chance to give you a reactant. When it reaches 10 reactants, simply right-click with the relic in your hand, and it will open, giving you a reward.

***Bedrock Players:*** If you are a Bedrock player, you must put the relic in your first slot from the left to the right (_it is experimental and still under testing, so if you have any issues, please make a report_).

#### Relic Refinements

Relics can be refined to increase the chances of getting rarer rewards. To refine a relic, use the command ```/sframe refine <refinement>```. To refine a relic, you will need **Void Traces**, which you get every time you open a relic (you get a random amount of void traces between 1 and 20). To check how many void traces you have, use the command ```/sframe traces```. There are four refinements:

- INTACT: The default refinement.
- EXCEPTIONAL: Costs 25 void traces.
- FLAWLESS: Costs 50 void traces.
- RADIANT: Costs 100 void traces.

### Cumulative Generators

A new type of energy generator that checks if the blocks beside, above, or below it are also Cumulative Generators. For each Cumulative Generator detected, an amount of energy, indicated by a new attribute in the generator's description called **Bonus Energy**, will be added to the total generated energy.
**Note:** Diagonal blocks are not counted. This means the cumulative energy generator will check a total of 6 blocks (1 for each side, 1 above, and 1 below).

### Auto Trader

A new machine that can automatically trade with villagers. To make it work, you will need a ***Merchant Soul Contract***. Once you have a contract, choose a villager and right-click it with the contract in your hand. It will kill the villager and fill the contract with its trades. After that, put the contract in the Auto Trader and select one of the trades.

**Automating the Farm of the New Ores:** This machine can be used to automatically produce the new ores that you get using the Nosam Pickaxe. Every time a villager gets a new trade, there is a small chance that it offers one of the new addon resources. In that case, use the Merchant Soul Contract again and use the Auto Trader with this new contract.

## Credits

Thanks to <a href="https://minecraft-heads.com/">Minecraft Heads</a> for the heads the addon uses.
A big thanks to all the people that helped me in the programming channel in the Slimefun Discord Server.
Thanks to the server **AbsolutGG** for letting me test the addon.

## Donate

If you want to support the project and make someone's day, you can donate on <a href="https://ko-fi.com/voper">Ko-Fi</a> 🙂

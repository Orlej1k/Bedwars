<p align="center">
      <img src="https://i.ibb.co/3f95tvH/2023-03-28-214548130.png" width="450">
</p>

<p align="center">
   <img src="https://img.shields.io/badge/Version-1.16.5--1.19.4-blue" alt="Version">
</p>

## About

Creating game: when you create a new game, a new world is created with the name BW-(id), for example BW-1 (or overwritten if it already exists in the server folder)

Game logic: there are only 4 teams. All players are divided into teams, each team has a bed. If the player dies, he will spawn after some time (which is set in the config), if the bed is broken, then he will simply turn into a spectator. There is also a system of drop spawners, there are 4 spawners in total (iron, gold, diamond and emerald). The player can break/put only some blocks (which were set in the config, for example: wool and red bed)

## Commands

**-** **`/bw`** - check commands.

**-** **`/bw create`** - create the game(arena).

**-** **`/bw join`** - join the game.

**-** **`/bw quit`** - quit the game.

**-** **`/bw getItem`** - get spawner item(iron, gold, etc).

**-** **`/bw tp`** - tp to the **original** world.

**-** **`/bw list`** - list of arenas.

## Permissions

**-** **`bw.create`** - allows to create the arena.

**-** **`bw.join`** - allows to join the arena.

**-** **`bw.delete`** - allows to delete the arena.

**-** **`bw.tp`** - allows to tp to the **original** world.

**-** **`bw.getItem`** - allows to get spawner item(iron, gold, etc).

## Configs

**-** **`blocks.yml`**

You can put the blocks you want players to be able to break/place

**canBreak** - player can break block

**NoPlace** - player can't place block

You must use only blocks from this [list](https://helpch.at/docs/1.16.5/org/bukkit/Material.html)

**-** **`locations.yml`**

You must insert the spawnpoint of the team and location of the bed into this config (since the bed is divided into two blocks, you need to insert the coordinates of these two blocks)

## Developers

- [Orlej1k](https://github.com/Orlej1k)

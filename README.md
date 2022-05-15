[licenseImg]: https://img.shields.io/badge/License-GPL--3.0-important
[license]: https://github.com/Chiarchiaooo/HubEssentials/blob/master/LICENSE
[mcversionImg]: https://img.shields.io/badge/MC%20Version-1.18x-success
[mcversion]: https://www.spigotmc.org/resources/hubessentials
[releaseImg]: https://img.shields.io/badge/Version-1.0-blue
[release]: https://github.com/Chiarchiaooo/HubEssentials/releases/latest

# HubEssentials
Simple Lobby plutin with some essential features<br>
Made for 1.18x servers
<br>

[![releaseImg]][release] ![mcversionImg] [![licenseImg]][license]


<br>

## Support

To get support open a issue on my <a href=https://github.com/Chiarchiaooo/HubEssentials/issues> GitHub issue page </a> or join my <a href=https://dsc.gg/cliffycommunity>discord</a><br><br>

## Features

* Customizable join-items (items given upon player join), including:
  * Bow-tp (teleport around the lobby by shooting an arrow)
  * Hide-item
  * server-selector (compass) editable from config.yml

* togglable modules and commands (check in config.yml), including:
  * Command whitelist / command blocker with groups (editable in config.yml)
  * Customizable chat formatting (colors codes and papi supported)
  * Customizable Server rules (edit in rules.txt)
  * /gamemode command
  * /fly command
  * Vanish command 
  * /msg and SocialSpy (spy private messages)
  * Scoreboard (coming soon...)
  * Tablist (Coming soon...)
  * server selector (compass) editable from config.yml

* Customizable messages (colors codes and mostly with papi support)
* Reload command (no need to use plugman or restart the server)
* 1.18x support

<br>

## Commands
<br>

### Staff Commands
| Command | Description | Permission | Aliases |
| --------------- | ---------------- | ---------------- | ---------------- |
| /hubessentials reload | Reloads plugin configs | hubessentials.vanish.reload | /hubess reload, /he reload |
| /hubessentials reset | Resets plugin configs | hubessentials.reset.command | /hubess reset, /he reset |
| /hubessentials vanish | Shows plugin help list | hubessentials.vanish.command | /hubess vanish, /he vanish , /vanish, /v |
| /hubessentials socialspy | Spy private messages | hubessentials.socialspy.command | /hubess socialspy, /he socialspy, /socialspy, /ss |
| /gm | Allows to change gamemode | hubessentials.gamemode.command  | /gamemode |
| /gmc | Change gamemode to Creative | hubessentials.gamemode.creative | /gm c, /gamemode creative |
| /gms | Change gamemode to Survival | hubessentials.gamemode.survival | /gm s, /gamemode survival |
| /gmsp | Change gamemode to Spectator | hubessentials.gamemode.spectator | /gm sp, /gamemode spectator |

<br> 

### Player Commands
| Command | Description | Aliases |
| --------------- | ---------------- | ---------------- |
| /hubessentials | Shows plugin infos | /hubess, /he |
| /hubessentials help | Shows plugin help list | /hubess help, /he help |
| /menu | Open Server selector menu | /selector, /compass |
| /rules | Shows server rules (configurable in rules.txt) | None |
| /spawn | Tp to spawn | None |
| /fly | Toggles fly mode (creative fly) | None |
| /msg | Allows to send private messages to other users | /pmsg |

<br><br>
### Permissions

| Permission | Description |
| --------------- | ---------------- |
| hubessentials.help.staff | Gives access to staff commands in /hubessentials help |
| hubessentials.\<sucommand>\.command | Gives access to /hubessentials subcommands |
| hubessentials.gamemode.\<gamemode> | Allows to change your gamemode to a specific one |
| hubessentials.gamemode.others | Allows to change others gamemode (if they have perms for that) |
| hubessentials.fly.others | Allows to toggle fly to other players |
| hubessentials.socialspy.others | Allows to toggle socialspy to other players |
| hubessentials.socialspy.bypass | Excludes a player from spychat |
| hubessentials.vanish.see  | Shows other vanished staffs |
| hubessentials.vanish.list |  Gives access to vanished staff list |
| hubessentials.cmdblock.bypass.* | General commandblocker bypass permission |
| hubessentials.cmdblock.group.\<group> | Group-based command blocking (*) |
| hubessentials.chat.group.\<group> | Group-based chat formatting (*) |
| hubessentials.chat.color |  Lets you use colors in chat  |
| hubessentials.chat.emojis|  Gives access to emojis (❤,★,☮,...) (*) |


## Config File
| File Name  | Link |
| ---------- | ---- |
| config.yml | https://github.com/Chiarchiaooo/HubEssentials/blob/master/src/main/resources/config.yml |
| messages.yml | https://github.com/Chiarchiaooo/HubEssentials/blob/master/src/main/resources/messages.yml |
| rules.txt | https://github.com/Chiarchiaooo/HubEssentials/blob/master/src/main/resources/rules.txt |

## Donations

If you like my content and you want to support what I do, consider donating to <a href='https://ko-fi.com/U7U59S2LZ'>my ko-fi page</a>. <br>
#### Thank you❤️
<a href='https://ko-fi.com/U7U59S2LZ' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://cdn.ko-fi.com/cdn/kofi1.png?v=3' border='0' alt='Donate at ko-fi.com' /></a>
<br><br>

## Rating

You can also check out the plugin on <a href=https://www.spigotmc.org/resources/hubessentials>spigotmc.org</a><br><br>


# bloodmoon

Bloodmoon is a Bukkit plugin that adds a repeating bloodmoon event. The bloodmoon event raises the game difficulty to
hard for a configurable duration before turning the difficulty back to peaceful.

## demo

The Bloodmoon plugin is running on the [bingecraft.net Minecraft server](https://bingecraft.net).

## links

[curseforge](https://www.curseforge.com/minecraft/bukkit-plugins/bc-bloodmoon)

## installation

```
git clone git@github.com:arctair/bloodmoon.git
cd bloodmoon
mvn clean package
```

In the `target` directory there will now be a file called `bloodmoon-$version.jar`. Put this in your Bukkit
server's `plugins` directory and start the server to activate the plugin.

## configuration

Starting a Bukkit server with the Bloodmoon plugin will generate `plugins/Bloodmoon/config.yml` in your server's
directory. From here you can tweak the plugin's timings. Here is the default configuration:

```
intervalTicks: 72000
startTicks: 60000
endTicks: 73000
```

Every day in Minecraft is 24000 ticks. Sunset is at 12000. Sunrise is at 23000. In the default
configuration, `intervalTicks` is set to three days (24000 * 3) thus there is a bloodmoon event every three
days. `startTicks` is set to the time of every third sunrise (24000 * 2 + 12000) and `endTicks` is set to the time of
2000 ticks after the next sunset thus the bloodmoon event rises with every third sunset and lasts until the following
morning.
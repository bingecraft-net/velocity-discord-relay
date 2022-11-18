# Velocity Discord Relay

Velocity Discord Relay is a [Velocity Proxy](https://velocitypowered.com/) plugin for Discord integration. You will
need [Backend Silencer](https://github.com/bingecraft-net/backend-silencer) on your backends to eliminate redundant
join + quit + chat messages and to forward death + advancement messages to Discord.

## Features

1. Forward join, quit, and chat messages between Velocity backends
2. Forward join, quit, chat, death, and advancement messages to a Discord text channel
3. Forward Discord text channel messages to Velocity backends
4. Discord messages for join, quit, death and advancement events include player avatar icon
5. Automatically delete rapid quit and join messages from Discord text channel

## installation

```
git clone git@github.com:bingecraft-net/velocity-discord-relay.git
cd velocity-discord-relay
mvn clean package
```

In the `target` directory there will now be a file called `velocity-discord-relay-$version.jar`. Put this in your
Velocity proxy's `plugins` directory and start the proxy to activate the plugin.

## configuration

This plugin will create two files upon starting the Velocity server:

### plugins/velocity-discord-relay/configuration.toml

```toml
# relayChannelId is the ID of your Discord text channel for bridging with game chat and player events
relayChannelId = 00000000000
# quitMessageMinAgeSeconds is the minimum age of a quit message to be preserved in Discord before a player rejoins
quitMessageMinAgeSeconds = 60
# notificationHost is the hostname or ip address of the backend-silencer server
notificationHost = "localhost"
# notificationPort is the port of the backend-silencer server
notificationPort = 8080
```

### plugins/velocity-discord-relay/token

This file should be populated with only your Discord bot token

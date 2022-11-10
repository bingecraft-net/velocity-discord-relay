# Velocity Discord Relay

Velocity Discord Relay is a [Velocity Proxy](https://velocitypowered.com/) plugin for Discord integration. You will
need [Backend Silencer](https://github.com/bingecraft-net/backend-silencer) on your backends to eliminate duplicate
messages upon join, quit, chat, and player backend switches.

## Features

1. Forward join, quit, and chat messages between Velocity backends
2. Forward join, quit, and chat messages to a Discord text channel
3. Forward Discord text channel messages between Velocity backends
4. Automatically delete rapid quit and join messages from Discord text channel

## installation

```
git clone git@github.com:bingecraft-net/velocity-discord-relay.git
cd velocity-discord-relay
mvn clean package
```

In the `target` directory there will now be a file called `velocity-discord-relay-$version.jar`. Put this in your
Velocity proxy's `plugins` directory and start the proxy to activate the plugin.

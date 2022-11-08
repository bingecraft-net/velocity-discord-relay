# Velocity Discord Relay

Velocity Discord Relay is a [Velocity Proxy](https://velocitypowered.com/) plugin for Discord integration. You will
need [Backend Silencer](https://github.com/bingecraft-net/backend-silencer) on your backends to eliminate duplicate
chat, join quit messages as well as join and quit messages upon players switching between backends.

## installation

```
git clone git@github.com:bingecraft-net/velocity-discord-relay.git
cd velocity-discord-relay
mvn clean package
```

In the `target` directory there will now be a file called `velocity-discord-relay-$version.jar`. Put this in your
Velocity proxy's `plugins` directory and start the proxy to activate the plugin.

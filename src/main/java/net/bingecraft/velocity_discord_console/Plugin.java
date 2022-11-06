package net.bingecraft.velocity_discord_console;


import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@com.velocitypowered.api.plugin.Plugin(id = "velocity-discord-console")
public final class Plugin {
  private final ProxyServer proxyServer;
  private final Logger logger;

  @Inject
  public Plugin(ProxyServer proxyServer, Logger logger) {
    this.proxyServer = proxyServer;
    this.logger = logger;
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    logger.info("initialized velocity-discord-console");
  }
}

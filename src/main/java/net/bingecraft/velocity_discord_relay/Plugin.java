package net.bingecraft.velocity_discord_relay;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;

import java.nio.file.Path;

@com.velocitypowered.api.plugin.Plugin(id = "velocity-discord-relay")
public final class Plugin {
  private final ProxyServer proxyServer;
  private final Path dataDirectory;
  private final Logger logger;

  @Inject
  public Plugin(
    ProxyServer proxyServer,
    @DataDirectory Path dataDirectory,
    Logger logger
  ) {
    this.proxyServer = proxyServer;
    this.dataDirectory = dataDirectory;
    this.logger = logger;
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) throws InterruptedException {
    Configuration configuration = new ConfigurationBuilder(dataDirectory).build();
    JDA jda = new JDABuilder(configuration).create();
    VelocityEventRelay velocityEventRelay = new VelocityEventRelay(jda, proxyServer, configuration);

    proxyServer.getEventManager().register(this, velocityEventRelay);
    jda.addEventListener(new JDAEventRelay(proxyServer, configuration));
  }
}

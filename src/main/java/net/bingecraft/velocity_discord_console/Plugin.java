package net.bingecraft.velocity_discord_console;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.dv8tion.jda.api.JDA;

import java.nio.file.Path;

@com.velocitypowered.api.plugin.Plugin(id = "velocity-discord-console")
public final class Plugin {
  private final ProxyServer proxyServer;
  private final Path dataDirectory;

  @Inject
  public Plugin(
    ProxyServer proxyServer,
    @DataDirectory Path dataDirectory
  ) {
    this.proxyServer = proxyServer;
    this.dataDirectory = dataDirectory;
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) throws InterruptedException {
    Configuration configuration = new ConfigurationBuilder(dataDirectory).build();
    JDA jda = new JDABuilder(configuration).create();
    proxyServer.getEventManager().register(this, new EventRelay(jda, configuration));
  }
}

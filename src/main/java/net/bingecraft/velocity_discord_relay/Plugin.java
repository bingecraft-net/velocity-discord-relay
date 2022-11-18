package net.bingecraft.velocity_discord_relay;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@com.velocitypowered.api.plugin.Plugin(id = "velocity-discord-relay")
public final class Plugin {
  private final ProxyServer proxyServer;
  private final Path dataDirectory;
  private final Logger logger;

  private NotificationClient notificationClient;

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
    if (!Files.exists(dataDirectory)) createDataDirectory();
    Configuration configuration = new ConfigurationReader(dataDirectory).read();
    String token = new TokenReader(dataDirectory).read();

    JDA jda = new JDABuilder(token).create();
    TextChannel relayChannel = new RelayChannelConnector(configuration, jda).connect();
    AvatarURLFactory avatarURLFactory = new AvatarURLFactory();

    VelocityEventRelay velocityEventRelay = new VelocityEventRelay(
      this,
      proxyServer,
      proxyServer.getScheduler(),
      configuration,
      relayChannel,
      avatarURLFactory
    );

    notificationClient = new NotificationClient(
      logger,
      configuration,
      new NotificationRelay(relayChannel, avatarURLFactory)
    );

    notificationClient.connect();
    proxyServer.getEventManager().register(this, velocityEventRelay);
    jda.addEventListener(new JDAEventRelay(proxyServer, configuration));
  }

  @Subscribe
  public void onProxyShutdown(ProxyShutdownEvent event) {
    notificationClient.shutdownGracefully();
  }

  private void createDataDirectory() {
    try {
      Files.createDirectory(dataDirectory);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}

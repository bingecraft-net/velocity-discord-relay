package net.bingecraft.velocity_discord_relay;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class NotificationClient {
  private final Logger logger;
  private final Consumer<Notification> listener;
  private final Bootstrap bootstrap;

  private final EventLoopGroup group = new NioEventLoopGroup();

  public NotificationClient(Logger logger, Consumer<Notification> listener, Configuration configuration) {
    this.logger = logger;
    this.listener = listener;
    this.bootstrap = new Bootstrap()
      .group(group)
      .channel(NioSocketChannel.class)
      .option(ChannelOption.TCP_NODELAY, true)
      .remoteAddress(configuration.notificationHost, configuration.notificationPort)
      .handler(new Initializer());
  }

  private class Initializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(final SocketChannel channel) {
      channel.pipeline().addLast(
        new JsonObjectDecoder(),
        new NotificationDecoder(),
        new ChannelHandler()
      );
    }
  }

  private class ChannelHandler extends SimpleChannelInboundHandler<Notification> {
    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final Notification notification) {
      listener.accept(notification);
    }

    @Override
    public void channelActive(final ChannelHandlerContext context) {
      logger.info("connected to {}", context.channel().remoteAddress());
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext context) {
      logger.info("reconnecting to {} in 5 seconds", context.channel().remoteAddress());
      context.channel().eventLoop().schedule(NotificationClient.this::connect, 5, TimeUnit.SECONDS);
    }
  }

  public void connect() {
    bootstrap.connect();
  }

  public void shutdownGracefully() {
    group.shutdownGracefully();
  }
}

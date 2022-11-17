package net.bingecraft.velocity_discord_relay;

import com.velocitypowered.api.scheduler.Scheduler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;

import java.util.function.Consumer;

public class NotificationClient {
  private final Configuration configuration;
  private final Consumer<Notification> listener;
  private final EventLoopGroup group = new NioEventLoopGroup();

  public NotificationClient(Configuration configuration, Consumer<Notification> listener) {
    this.configuration = configuration;
    this.listener = listener;
  }

  public void start(Plugin plugin, Scheduler scheduler) {
    scheduler.buildTask(plugin, this::run).schedule();
  }

  private void run() {
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .handler(new Handler());

      ChannelFuture future = bootstrap.connect(configuration.notificationHost, configuration.notificationPort).sync();
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      group.shutdownGracefully();
    }
  }

  private class Handler extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel channel) {
      channel.pipeline().addLast(
        new JsonObjectDecoder(),
        new NotificationDecoder(),
        new ListenerAdapter()
      );
    }
  }

  private class ListenerAdapter extends SimpleChannelInboundHandler<Notification> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Notification msg) {
      listener.accept(msg);
    }
  }

  public void shutdownGracefully() {
    group.shutdownGracefully();
  }
}

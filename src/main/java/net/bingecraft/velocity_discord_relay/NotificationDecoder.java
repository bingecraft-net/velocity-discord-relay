package net.bingecraft.velocity_discord_relay;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class NotificationDecoder extends MessageToMessageDecoder<ByteBuf> {
  private final Gson gson = new Gson();

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    out.add(gson.fromJson(in.toString(StandardCharsets.UTF_8), Notification.class));
  }
}

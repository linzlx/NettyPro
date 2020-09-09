package com.study.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zzy
 * @time 2020-09-08 11:16)
 */

/**解码
 *0) ReplayingDecoder 使用方便，但它也有一些局限性： 1. 并不是所有的 ByteBuf 操作都被支持，如果调用了一个不被支持的方法，将会抛出一个
 * UnsupportedOperationException。 2. ReplayingDecoder 在某些情况下可能稍慢于 ByteToMessageDecoder，例如网络缓慢并且消息格式复杂时，
 * 消息会被拆成了多个碎片，速度变慢
 * 1) LineBasedFrameDecoder：这个类在 Netty 内部也有使用，它使用行尾控制字符（\n 或者\r\n）作为分隔符来解 析数据。
 * 2) DelimiterBasedFrameDecoder：使用自定义的特殊字符作为消息的分隔符。
 * 3) HttpObjectDecoder：一个HTTP 数据的解码器
 * 4) LengthFieldBasedFrameDecoder：通过指定长度来标识整包消息，这样就可以自动的处理黏包和半包消息。
 *
 * 编码
 * )zlib 压缩
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用 \n进行解码");
        out.add(in.readLong());
    }
}

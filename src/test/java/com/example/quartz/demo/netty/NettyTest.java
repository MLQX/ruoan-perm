package com.example.quartz.demo.netty;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyTest {


    @Test
    public void test1() {
        System.out.println("test netty");
    }

    @Test
    public void testChannelFuture() {


        //
        Channel channel = null;
        ChannelFuture channelFuture = channel.connect(new InetSocketAddress("127.0.0.1", 9090));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    //成功 创建一个ByteBuf以持有数据
                    ByteBuf buff = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    ChannelFuture future = channelFuture.channel().writeAndFlush(buff);
                } else {
                    Throwable cause = channelFuture.cause();
                    cause.printStackTrace();
                }
            }
        });
    }



    @ChannelHandler.Sharable
    class EchoServerHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buff = (ByteBuf) msg;
            System.out.println("server received:"+buff.toString(CharsetUtil.UTF_8));
            ctx.write(buff);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }


}

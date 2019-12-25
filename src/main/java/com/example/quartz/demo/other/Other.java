package com.example.quartz.demo.other;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import javafx.beans.binding.ObjectExpression;

import java.util.Date;

public class Other {
    public void test5() {
        String a = "helloworld";
        String b = a;
        String c = "helloworld";
        String d = new String("helloworld");
//        System.out.println(a == b );
//        System.out.println(a.equals(b) );
        Object o;
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(a == c);
        System.out.println(a.equals(c));
        System.out.println(a == d);
        System.out.println(a);
        System.out.println(d);
        System.out.println(d.hashCode());  // a!=d但是 a.hashCode() == d.hashCode()
    }

    public void test6(){
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.valueOf("1062"));
    }
    public void test7(){
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(Integer.valueOf("1062"));
        Object o;
        String o1;
    }
    public static void main(String[] args) {
//        new Other().test5();
//        innerClass c1 = new Other().new innerClass();
//        innerClass c2 = new Other().new innerClass();
//        System.out.println(c1.hashCode());
//        System.out.println(c2.hashCode());
//        System.out.println();
//        new Other().test6();

        System.out.println(Math.round(11.5));
        System.out.println(Math.round(-11.5));   // -11
        System.out.println(Math.round(-11.6));
        System.out.println(Math.round(-11.4));


    }


    class innerClass{

    }




}

/**
 * netty 服务端
 */
class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boos, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8000);
    }
}

/**
 * netty客户端
 */
class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();

        while (true) {
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(2000);
        }
    }
}


class OOG{


    public static void main(String[] args) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int i=0;
                        while(true) {
                            i++;
                            System.out.println("hello"+i);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).run();

    }

}

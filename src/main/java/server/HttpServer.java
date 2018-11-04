package server;

import initalizer.HttpServerInitalizer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    public static void main(String[] args) {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        EventLoopGroup jonGroup = new NioEventLoopGroup();
        ServerBootstrap  bootstrap = new ServerBootstrap();

        ChannelFuture future = null;
        try {
            future = bootstrap.group(loopGroup, jonGroup).channel(NioServerSocketChannel.class).childHandler(new HttpServerInitalizer()).bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            loopGroup.shutdownGracefully();
            jonGroup.shutdownGracefully();
        }
    }
}

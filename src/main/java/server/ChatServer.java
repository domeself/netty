package server;

import initalizer.ChatServerInitalizer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class ChatServer {
    public static void main(String[] args) {
        EventLoopGroup boosGroup =  new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            ChannelFuture channelFuture = bootstrap.group(boosGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChatServerInitalizer()).bind(89).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

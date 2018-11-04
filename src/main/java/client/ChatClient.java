package client;

import initalizer.ChatClientInitalizer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap  bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new ChatClientInitalizer());
        try {
            Channel channel = bootstrap.connect("localhost", 89).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                    channel.writeAndFlush(reader.readLine()+"\n");
            }
        }  finally {
            loopGroup.shutdownGracefully();
        }
    }
}

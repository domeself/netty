package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static  ChannelGroup  channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)  {
        System.out.println("channelActive");
        Channel channel = ctx.channel();
        channels.writeAndFlush("客户端："+channel.remoteAddress()+"active\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)  {
        System.out.println("channelInactive");
        Channel channel = ctx.channel();
        channels.writeAndFlush("客户端："+channel.remoteAddress()+"inactive\n");
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx)  {
        System.out.println("handlerAdded");
        Channel channel = ctx.channel();
        channels.writeAndFlush("客户端："+channel.remoteAddress()+"handlerAdded\n");
        channels.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("handlerRemoved");
        Channel channel = ctx.channel();
        channels.writeAndFlush("客户端："+channel.remoteAddress()+"removed\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)  {
        System.out.println("channelRead0");
        Channel channel = ctx.channel();
        channels.forEach(ch -> {
            if(ch==channel){
                channel.writeAndFlush("自己："+msg+"\n");
            }else {
                channel.writeAndFlush("客户端"+ch.remoteAddress()+":"+msg+"\n");
            }
        });
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
    }


}

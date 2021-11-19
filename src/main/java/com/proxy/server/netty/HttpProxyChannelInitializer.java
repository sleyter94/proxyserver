package com.proxy.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


@Component
public class HttpProxyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private AtomicLong taskCounter;

    @Autowired private ApplicationContext appCtx;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
            new LoggingHandler(LogLevel.DEBUG),
            appCtx.getBean(HttpProxyClientHandler.class, "task-" + taskCounter.getAndIncrement())
        );
    }
}
package com.wolf.shoot.service.net.message.encoder;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.rpc.serialize.RpcSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by jwp on 2017/3/7.
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            RpcSerialize rpcSerialize = LocalMananger.getInstance().getLocalSpringBeanManager().getProtostuffSerialize();
            byte[] data = rpcSerialize.serialize(in);
            //byte[] data = JsonUtil.serialize(in); // Not use this, have some bugs
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
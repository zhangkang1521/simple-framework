package org.zk.dubbo.remoting;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * 编解码接口
 *
 * @author zhangkang
 * @date 2022/5/8 18:05
 */
public interface Codec2 {

    /**
     * 编码，将message已一定格式写入byteBuf
     * @param bytebuf
     * @param message
     */
    void encode(ByteBuf bytebuf, Object message) throws IOException;

    /**
     * 解码，将byteBuf中的字节解码为对象
     * @param byteBuf
     * @return
     */
    Object decode(ByteBuf byteBuf) throws IOException;
}

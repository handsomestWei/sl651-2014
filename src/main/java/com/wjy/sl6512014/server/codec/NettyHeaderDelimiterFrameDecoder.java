package com.wjy.sl6512014.server.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义头标识符包分割处理器。累积数据，直到包含两个头标识符，截取数据返回一个包。netty自带的DelimiterBasedFrameDecoder不适用
 * 
 * @see io.netty.handler.codec.DelimiterBasedFrameDecoder
 * @author weijiayu
 * @date 2022/5/11 10:54
 */
@Slf4j
public class NettyHeaderDelimiterFrameDecoder extends ByteToMessageDecoder {

    private final int maxFrameLength;
    private final String delimiterHexStr;

    public NettyHeaderDelimiterFrameDecoder(int maxFrameLength, String delimiterHexStr) {
        this.maxFrameLength = maxFrameLength;
        this.delimiterHexStr = delimiterHexStr;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf decoded = this.decodeHexString(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    protected ByteBuf decodeHexString(ChannelHandlerContext ctx, ByteBuf buffer) {
        if (buffer.readableBytes() > maxFrameLength) {
            log.warn("buffer length over maxFrameLength");
            // 超过最大长度，丢弃数据，避免一直占用内存
            buffer.clear();
            return null;
        }
        String data = ByteBufUtil.hexDump(buffer).toUpperCase();
        int startIndex = data.indexOf(delimiterHexStr);
        if (startIndex < 0) {
            // 不满足条件，继续累积等待
            return null;
        }
        int endIndex = data.indexOf(delimiterHexStr, startIndex + delimiterHexStr.length());
        if (endIndex < 0) {
            // 不满足条件，继续累积等待
            return null;
        }
        // 累积数据，直到包含两个头标识符，截取数据返回一个包
        if (startIndex > 0) {
            // 丢弃标识符左区间前的数据
            buffer.skipBytes(startIndex/2);
        }
        // TODO 若后续没有数据，最后一个包将永远得不到处理
        return buffer.readRetainedSlice((endIndex - startIndex)/2);
    }
}

package carmelo.netty.tcp;

import carmelo.netty.codec.ServerDecoder;
import carmelo.netty.codec.ServerEncoder;
import carmelo.servlet.Servlet;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

	private Servlet servlet;
	
	private final static EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(8);;
	
    public TcpServerInitializer(Servlet servlet){
    	this.servlet = servlet;
    }
    
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new ServerDecoder());
        pipeline.addLast("encoder", new ServerEncoder());
        // and then business logic
        pipeline.addLast(businessGroup, "handler", new TcpServerHandler(servlet));
    }
}

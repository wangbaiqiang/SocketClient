package com.pq.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2016/12/19.
 */
public class MinaClient {
    public static void main(String[] args) throws Exception {
        NioSocketConnector connector=new NioSocketConnector();
        connector.setHandler(new MyClientHandler());
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory()));
        ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9898));
        //mina是非阻塞的 那么当走到这里时并不会阻塞住，我们如果希望阻塞住就可以调用这个方法在这里等待服务端的响应
        future.awaitUninterruptibly();
        //这个session对象就是ioSession对象
        IoSession session = future.getSession();
        BufferedReader inputReader=new BufferedReader(new InputStreamReader(System.in));
        String inputContent=null;
        while (!(inputContent=inputReader.readLine()).equals("bye")){
            session.write(inputContent);
        }
    }
}

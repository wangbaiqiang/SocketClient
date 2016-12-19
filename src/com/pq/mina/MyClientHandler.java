package com.pq.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Administrator on 2016/12/15.
 */
public class MyClientHandler extends IoHandlerAdapter{

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("sessionCreated");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("sessionOpened");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("sessionClosed");
    }

    /**
     * 客户端进入空闲状态时候调用
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        System.out.println("sessionIdle");
    }

    /**
     * 发送消息过程中出现异常会调用这个方法
     * 我遇到的一个异常bufferunderflowexception ：原因：你编解码的过程中操作iobuffer超过了limit值，需要你重新去review代码
     * @param session
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        System.out.println("exceptionCaught"+cause.getMessage());
    }

    /**
     * 这里message可以发送任何对象 网络传输肯定是字节 字节和对象之间的转换 其实就是通过拦截器来实现的
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        String msg=(String)message;
        System.out.println("messageReceived:"+msg);
        session.write("我是服务端的响应："+msg);
    }

    /**
     * 我们在messageReceived中 session.write()数据之后会调用该方法
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        System.out.println("messageSent:"+message);
        //这里调用这个会一直发送该数据
//        session.write("我是服务端的相应："+message+"\n");
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
        System.out.println("inputClosed");
    }
}

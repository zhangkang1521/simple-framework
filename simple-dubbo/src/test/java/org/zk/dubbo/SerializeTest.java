package org.zk.dubbo;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zk.dubbo.rpc.RpcInvocation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * java序列化测试
 *
 * @author zhangkang
 * @date 2022/5/1 22:04
 */
public class SerializeTest {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    RpcInvocation rpcInvocation1 = new RpcInvocation();

    @Before
    public void before() {
        rpcInvocation1.setMethodName("sayHello");
    }

    @Test
    public void testJdk() throws Exception {
        for (int i = 0; i < 1000; i++) {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(rpcInvocation1);
            oos.flush();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bais);
            RpcInvocation rpcInvocation2 = (RpcInvocation) oi.readObject();

            Assert.assertEquals(rpcInvocation2.getMethodName(), rpcInvocation1.getMethodName());
        }
    }

    @Test
    public void testHession2() throws Exception {
        Hessian2Output hessian2Output = new Hessian2Output(baos);
        hessian2Output.writeObject(rpcInvocation1);
        hessian2Output.flush();
        hessian2Output.close();

        ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
        Hessian2Input hessian2Input = new Hessian2Input(bin);
        RpcInvocation rpcInvocation2 = (RpcInvocation) hessian2Input.readObject();
        hessian2Input.close();

        Assert.assertEquals(rpcInvocation2.getMethodName(), rpcInvocation1.getMethodName());
    }
}

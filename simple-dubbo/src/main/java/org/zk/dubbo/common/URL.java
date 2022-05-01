package org.zk.dubbo.common;

import lombok.Data;

/**
 * dubbo里的统一资源定位符
 */
@Data
public class URL {

    /**
     * 原字符串，例如dubbo://localhost:20880
     */
    private String string;

    /**
     * 协议
     */
    private String protocol;

    /**
     * host
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 服务名，例如org.zk.dubbo.DemoService
     */
    private String path;

    public URL(String string, String protocol, String host, int port) {
        this.string = string;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public URL(String protocol, String host, int port, String path) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
    }

    /**
     * 解析形如 dubbo://localhost:20881
     * @param str
     * @return
     */
    public static URL valueOf(String str) {
        int hostIndex = str.indexOf("://");
        String protocol = str.substring(0, hostIndex);

        // localhost:20880
        String hostPort = str.substring(hostIndex + 3);
        int portIndex = hostPort.indexOf(":");
        String host = hostPort.substring(0, portIndex);
        int port = Integer.valueOf(hostPort.substring(portIndex + 1));


        return new URL(str, protocol, host, port);
    }


}

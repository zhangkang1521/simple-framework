package org.zk.dubbo.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 参数
     */
    private Map<String, String> parameters = new HashMap<>();

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

    /**
     * 添加参数
     * @param key
     * @param value
     */
    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    /**
     * String形式展示
     * @return 例如 dubbo://localhost:20881/com.xxx.DemoService?key1=value1&key2=value2
     */
    public String toFullString() {
        StringBuilder buff = new StringBuilder();
        buff.append(protocol);
        buff.append("://");
        buff.append(host);
        buff.append(":");
        buff.append(port);
        buff.append("/");
        buff.append(path);
        boolean isFirst = true;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (isFirst) {
                buff.append("?");
                isFirst = false;
            } else {
                buff.append("&");
            }
            buff.append(entry.getKey());
            buff.append("=");
            buff.append(entry.getValue());
        }
        return buff.toString();
    }

}

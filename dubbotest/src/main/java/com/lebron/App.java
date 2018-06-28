package com.lebron;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = extensionLoader.getAdaptiveExtension();
        System.out.println(protocol.getDefaultPort());
    }
}

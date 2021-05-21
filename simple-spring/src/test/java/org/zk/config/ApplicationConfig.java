package org.zk.config;

import org.zk.simplespring.context.annotation.ComponentScan;
import org.zk.simplespring.context.annotation.Configuration;
import org.zk.simplespring.stereotype.Component;

@Configuration
@ComponentScan("org.zk.service")
public class ApplicationConfig {
}

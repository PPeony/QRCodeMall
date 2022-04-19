package com.qrcodemall.configure.shuwdown;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Peony
 * @Date: 2022/3/1 14:31
 */
@Slf4j
public class CustomShutdown implements TomcatConnectorCustomizer,
        ApplicationListener<ContextClosedEvent> {

    private static final int TIME_OUT = 30;

    private volatile Connector connector;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

        String displayName = "Web";
        if (event.getSource() instanceof AnnotationConfigApplicationContext){
            displayName =  ((AnnotationConfigApplicationContext) event.getSource()).getDisplayName();
        }/* Suspend all external requests*/
        this.connector.pause();
        /* Get ThreadPool For current connector */
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            log.warn("当前{}应用准备关闭",displayName);
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                /* Initializes a shutdown task after the current one has been processed task*/
                threadPoolExecutor.shutdown();
                if (!threadPoolExecutor.awaitTermination(TIME_OUT, TimeUnit.SECONDS)) {
                    log.warn("当前{}应用等待超过最大时长{}秒，将强制关闭", displayName,TIME_OUT);
                    /* Try shutDown Now*/
                    threadPoolExecutor.shutdownNow();
                    if (!threadPoolExecutor.awaitTermination(TIME_OUT, TimeUnit.SECONDS)) {
                        log.error("强制关闭失败", TIME_OUT);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
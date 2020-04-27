package com.etoak.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Session;

//@Configuration
public class ActiveMQConfig {

    //1.ActiveMQConnectionFactory
    //2.CachingConnectionFactory
    //3.JmsTemplate
    @Bean
    public ActiveMQConnectionFactory mqConnectionFactory() {
        return new ActiveMQConnectionFactory(null, null, "tcp://localhost:61616");
    }

    @Bean
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setTargetConnectionFactory(this.mqConnectionFactory());
        factory.setSessionCacheSize(10);
        return factory;
    }

    @Bean
    public JmsTemplate JmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(this.connectionFactory());
        jmsTemplate.setExplicitQosEnabled(true);
        //设置持久化
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        //设置客户端手动签收消息
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return jmsTemplate;
    }
}

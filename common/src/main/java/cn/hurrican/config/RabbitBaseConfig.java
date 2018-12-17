package cn.hurrican.config;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/23
 * @Modified 11:00
 */
@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class RabbitBaseConfig {

    @Autowired
    private Environment env;


    @Bean(name = "cachingConnectionFactory4Producer")
    CachingConnectionFactory initConnectionFactory4Producer() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(env.getProperty("rabbit.hosts"));
        connectionFactory.setUsername(env.getProperty("rabbit.username"));
        connectionFactory.setPassword(env.getProperty("rabbit.password"));
        connectionFactory.setVirtualHost(env.getProperty("rabbit.virtual.host"));
        connectionFactory.setPort(env.getProperty("rabbit.port", Integer.class));
        return connectionFactory;

    }

    @Bean(name = "cachingConnectionFactory4Consumer")
    CachingConnectionFactory initConnectionFactory4Consumer() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(env.getProperty("rabbit.hosts"));
        connectionFactory.setUsername(env.getProperty("rabbit.username"));
        connectionFactory.setPort(env.getProperty("rabbit.port", Integer.class));
        connectionFactory.setPassword(env.getProperty("rabbit.password"));
        connectionFactory.setVirtualHost(env.getProperty("rabbit.virtual.host"));
        return connectionFactory;

    }


    @Bean(name = "rabbitAdmin")
    public RabbitAdmin rabbitAdmin(ConnectionFactory cachingConnectionFactory4Producer) {
        return new RabbitAdmin(cachingConnectionFactory4Producer);
    }

    @Bean(name = "connection4Producer")
    public Connection getConnection4Producer(ConnectionFactory cachingConnectionFactory4Producer) {
        return cachingConnectionFactory4Producer.createConnection();
    }


    @Bean(name = "connection4Consumer")
    public Connection getConnection4Consumer(ConnectionFactory cachingConnectionFactory4Consumer) {
        return cachingConnectionFactory4Consumer.createConnection();
    }






}

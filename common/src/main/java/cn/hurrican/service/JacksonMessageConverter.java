package cn.hurrican.service;

import cn.hurrican.exception.UnsupportedConvertMessageException;
import cn.hurrican.rabbitmq.AsyncMessageAction;
import cn.hurrican.rabbitmq.MessageConsumerHandlerMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JacksonMessageConverter extends AbstractMessageConverter implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static Logger logger = LogManager.getLogger(JacksonMessageConverter.class);

    private volatile String defaultCharset = DEFAULT_CHARSET;

    public JacksonMessageConverter() {
        super();
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null) ? defaultCharset : DEFAULT_CHARSET;
    }


    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        return null;
    }

    @Override
    protected Message createMessage(Object objectToConvert, MessageProperties messageProperties) {
        if(!(objectToConvert instanceof AsyncMessageAction)){
            throw new UnsupportedConvertMessageException(
                    String.format("参数objectToConvert未实现%s接口", AsyncMessageAction.class.getName()));
        }
        try {
            System.out.println("objectToConvert.getClass() = " + objectToConvert.getClass());
            MessageConsumerHandlerMapping handlerMapping = context.getBean(MessageConsumerHandlerMapping.class);

            String consumerHandlerName = handlerMapping.getMessageConsumerHandlerName(objectToConvert.getClass().getName());
            System.out.println("consumerHandlerName = " + consumerHandlerName);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(objectToConvert);
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding(this.defaultCharset);
            byte[] bytes = jsonString.getBytes(this.defaultCharset);
            messageProperties.setContentLength(bytes.length);
            Map<String, Object> headers = messageProperties.getHeaders();
            if(headers != null){
                headers.put(MessageConsumerHandlerMapping.ACTION, ((AsyncMessageAction) objectToConvert).getAction());
                headers.put(MessageConsumerHandlerMapping.MESSAGE_CONSUMER_HANDLER_NAME, consumerHandlerName);
            }
            return new Message(bytes, messageProperties);
        } catch (UnsupportedEncodingException e) {
            logger.error("发生异常：{}", e);
            throw new MessageConversionException("Failed to convert Message content", e);
        } catch (JsonProcessingException e) {
            logger.error("发生异常：{}", e);
            e.printStackTrace();
            throw new MessageConversionException("Failed to convert Message content", e);
        }catch (Exception e){
            logger.error("发生异常：{}", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}

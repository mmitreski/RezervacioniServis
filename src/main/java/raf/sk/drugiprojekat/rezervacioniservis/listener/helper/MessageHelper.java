package raf.sk.drugiprojekat.rezervacioniservis.listener.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageHelper {
    private ObjectMapper objectMapper;

    public <T> T getMessage(Message message, Class<T> clazz) throws RuntimeException, JMSException, JsonProcessingException {
        String json = ((TextMessage) message).getText();
        return objectMapper.readValue(json, clazz);
    }

    public String createMessage(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem with creating text message");
        }
    }
}

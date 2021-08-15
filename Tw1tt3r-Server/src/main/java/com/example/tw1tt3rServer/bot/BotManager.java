package com.example.tw1tt3rServer.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class BotManager {
    private Map<String, Class> valueToClass = new HashMap<String, Class>();
    private Map<String, Map<Integer, Object>> valueToRoomToBot = new HashMap<String, Map<Integer, Object>>();

    @SneakyThrows
    public Object createInstance(String value){
        Class c = valueToClass.get(value);
        Constructor constructor = c.getConstructor();
        Object object = constructor.newInstance();
        return(object);
    }

    @SneakyThrows
    public String getResponse(String botValue, String input, int roomId, int personId){
        Object object = valueToRoomToBot.get(botValue).get(roomId);
        Class c = valueToClass.get(botValue);
        Method method = c.getMethod("action", new Class[]{String.class, Integer.class});
        return((String) method.invoke(object, input, personId));
    }





















}

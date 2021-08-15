package com.example.tw1tt3rServer.runner;

import com.example.tw1tt3rServer.bot.BotManager;
import com.example.tw1tt3rServer.repository.entity.Person;
import com.example.tw1tt3rServer.service.PersonService;
import entities.enums.LastSeenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Component
public class BotLoader implements CommandLineRunner {

    @Autowired
    BotManager botManager;

    @Autowired
    PersonService personService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true){
            String url = scanner.nextLine();
            JarFile jarFile = new JarFile(url);
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls = { new URL("jar:file:" + url+"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()){
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class")){
                    continue;
                }
                String className = je.getName().substring(0,je.getName().length()-6);
                className = className.replace('/', '.');
                if(className.equals("BotInterface") || className.equals("Bot")){
                    continue;
                }
                Class c = cl.loadClass(className);
                Field field = c.getField("value");
                String value = (String) field.get(null);
                botManager.getValueToClass().put(value, c);
                botManager.getValueToRoomToBot().put(value, new HashMap<Integer, Object>());
                Person person = personService.makePerson(value, value, value, value, value, true, new Timestamp(System.currentTimeMillis()), false, LastSeenType.EVERYBODY, false, value);
            }
        }
    }
}

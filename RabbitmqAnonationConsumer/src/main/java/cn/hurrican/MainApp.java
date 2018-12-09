package cn.hurrican;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        String path = "classpath:applicationContext.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(path);
        System.out.println("start...");
        context.registerShutdownHook();
        System.out.println("end...");

    }
}

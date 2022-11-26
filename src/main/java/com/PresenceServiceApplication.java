package com;
import com.service.PresenceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PresenceServiceApplication {

    public static void main(String[] args) throws Exception {
        PresenceService ps = (PresenceService) SpringApplication.run(PresenceServiceApplication.class, args).getBean("presenceService");
        ps.getQueueContent();
    }

}

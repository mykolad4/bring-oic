package org.example.bring.context;

import org.example.bring.exception.NoSuchBeanException;
import org.example.bring.exception.NoUniqueBeanException;
import org.example.bring.model.MessageService;
import org.example.bring.model.NoImplementation;
import org.example.bring.model.SenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextImplTest {

    ApplicationContext applicationContext;
    private static final String BASE_PACKAGE = "org.example.bring.model";

    @BeforeEach
    void startUp() {
        applicationContext = new ApplicationContextImpl(BASE_PACKAGE);
    }

    @Test
    void allSenderBeansInstantiatedTest() {
        Map<String, SenderService> allBeans = applicationContext.getAllBeans(SenderService.class);
        assertEquals(2, allBeans.size());
    }

    @Test
    void throwNoUniqueBeanExceptionTest() {
        assertThrows(NoUniqueBeanException.class, () -> applicationContext.getBean(SenderService.class));
    }

    @Test
    void throwNoSuchBeanExceptionTest() {
        assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean(NoImplementation.class));
    }

    @Test
    void getBeanByNameTest() {
        SenderService httpSender = applicationContext.getBean("httpSender", SenderService.class);
        assertEquals("http test", httpSender.sendMessage("test"));
    }

    @Test
    void getBeanByTypeTest() {
        MessageService bean = applicationContext.getBean(MessageService.class);
        assertNotNull(bean);
    }

    @Test
    void getBeanWhenNameNotGivenTest() {
        MessageService bean = applicationContext.getBean("messageService", MessageService.class);
        assertNotNull(bean);
    }
}
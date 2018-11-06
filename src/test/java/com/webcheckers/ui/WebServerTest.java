package com.webcheckers.ui;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class WebServerTest {

    private TemplateEngine templateEngine;
    private Gson gson;
    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {
        templateEngine = mock(TemplateEngine.class);
        gson = new Gson();
    }

    @Test
    public void testInitialize(){
        WebServer testWebServer = new WebServer(templateEngine, gson);
        try {
            testWebServer.initialize();
        }catch(Exception e){fail();}
    }
}

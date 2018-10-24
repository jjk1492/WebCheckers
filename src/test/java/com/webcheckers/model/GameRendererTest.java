package com.webcheckers.model;

import com.webcheckers.application.GameCenter;
import com.webcheckers.ui.GameRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

@Tag("UI-Tier")
public class GameRendererTest {

    private GameRenderer CuT;
    private TemplateEngine engine;
    private GameCenter gameCenter;

    @BeforeEach
    public void setup(){
        engine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
    }

    @Test
    public void ConstructorTest_DoubleNull(){
        boolean failed = false;
        try {
            new GameRenderer(null, null);
            fail("Expected a NullPointerException to be thrown");
        } catch (NullPointerException exception) {
            failed = true;
        }

        assertTrue(failed, "NullPointerException wasn't thrown");
    }
}

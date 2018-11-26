package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.Mockito;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.SignInRenderer.SIGNIN_VIEW_NAME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignInRendererTest {

    private SignInRenderer signInRenderer;
    private TemplateEngine templateEngine;
    private TemplateEngineTester tester;

    @BeforeEach
    private void setup() {
        tester = new TemplateEngineTester();
        templateEngine = mock( TemplateEngine.class );
        when( templateEngine.render( Mockito.any() ) ).then( tester.makeAnswer() );
        signInRenderer = new SignInRenderer( templateEngine );
    }

    @Test
    public void defaultMapBehavior() {
        Session session = mock(Session.class);
        signInRenderer.render( session );
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( SIGNIN_VIEW_NAME );
    }

    @Test
    public void nullMap() {
        Session session = mock(Session.class);
        signInRenderer.render( session, null );
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( SIGNIN_VIEW_NAME );
    }


    @Test
    public void valuesKept() {
        Session session = mock(Session.class);
        String key = "latch";
        Object value = mock(SignInRendererTest.class);
        Map<String, Object> map = new HashMap<>();
        map.put( key, value );
        signInRenderer.render( session, map );
        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( SIGNIN_VIEW_NAME );
        tester.assertViewModelAttribute( key, value );
    }
}

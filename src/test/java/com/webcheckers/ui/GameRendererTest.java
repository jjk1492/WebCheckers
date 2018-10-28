package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.model.Color.RED;
import static com.webcheckers.ui.GameRenderer.*;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class GameRendererTest {

    private GameRenderer CuT;
    private TemplateEngine engine;
    private GameCenter gameCenter;
    private String Player1Name = "Player1";
    private String Player2Name = "Player2";
    private String Player3Name = "Player3";

    @BeforeEach
    public void setup(){
        engine = mock(TemplateEngine.class);
        gameCenter = mock(GameCenter.class);
    }

    @Test
    public void ConstructorTest_DoubleNull(){
        Executable test = () -> new GameRenderer(null, null);
        assertThrows( NullPointerException.class, test,
                   "NullPointerException wasn't thrown");
    }

    @Test
    public void ConstructorTest_NullEngine(){
        Executable test = () -> new GameRenderer( null, gameCenter);
        assertThrows(NullPointerException.class, test,
                     "NullPointerException wasn't thrown");
    }

    @Test
    public void ConstructorTest_NullGameCenter(){
        Executable test = () -> new GameRenderer( engine, null);
        assertThrows(NullPointerException.class, test,
                     "NullPointerException wasn't thrown");
    }


    @Test
    public void PlayerInGameTest(){
        String expectedView = "game.ftl";

        Player player1 = mock(Player.class);
        when( player1.getName()).thenReturn(Player1Name);

        Player player2 = mock(Player.class);
        when( player2.getName()).thenReturn(Player2Name);

        Player player3 = mock(Player.class);
        when( player3.getName()).thenReturn(Player3Name);

        Board boardMock = mock(Board.class);

        Game gameMock = mock(Game.class);
        when( gameMock.getRedPlayer()).thenReturn(player1);
        when( gameMock.getWhitePlayer()).thenReturn(player2);
        when( gameMock.getRedBoard()).thenReturn(boardMock);

        when( gameCenter.getGame("Player1")).thenReturn(gameMock);

        TemplateEngineTester engineTester = new TemplateEngineTester();
        when( engine.render( Mockito.any() ) ).then( engineTester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute(PLAYER_NAME_ATTR)).thenReturn("Player1");

        CuT = new GameRenderer(engine, gameCenter);
        CuT.render(sessionMock);

        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewName(expectedView);
        engineTester.assertViewModelAttribute(RED_PLAYER_ATTR, player1);
        engineTester.assertViewModelAttribute(WHITE_PLAYER_ATTR, player2);
        engineTester.assertViewModelAttribute(CURRENT_PLAYER_ATTR, player1);
        engineTester.assertViewModelAttribute(BOARD_ATTR, boardMock);
        engineTester.assertViewModelAttribute(CURRENT_PLAYER_ATTR, player1);

    }
}

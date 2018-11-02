package com.webcheckers.ui;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

@Tag("UI-Tier")
public class PostValidateRouteTest {

    private String redPlayerName = "BigRed";
    private String whitePlayerName = "BigWhite";

    private GameCenter gameCenterMock = mock(GameCenter.class);
    private Game gameMock = mock(Game.class);
    private Move moveMock = mock(Move.class);
    private Position startPosMock = mock(Position.class);
    private Position endPosMock = mock(Position.class);

    private Session sessionMock = mock( Session.class );
    private Request requestMock = mock( Request.class );
    private static final Response responseMock = mock( Response.class );

    private void setup(){
        when( startPosMock.getRow()).thenReturn(5);
        when( startPosMock.getCell()).thenReturn(0);

        when( endPosMock.getRow()).thenReturn(6);
        when( endPosMock.getCell()).thenReturn(1);

        when( moveMock.getStart()).thenReturn(startPosMock);
        when( moveMock.getEnd()).thenReturn(endPosMock);

        when( requestMock.session().attribute(PLAYER_NAME_ATTR)).thenReturn(redPlayerName);
        when( gameCenterMock.getGame(redPlayerName)).thenReturn(gameMock);
    }
}

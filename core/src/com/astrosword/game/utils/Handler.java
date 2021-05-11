package com.astrosword.game.utils;

import com.astrosword.game.states.GameState;
import com.astrosword.game.states.instruction.InstructionState;
import com.astrosword.game.states.play.PlayState;
import com.astrosword.game.states.score.ScoreState;
import com.astrosword.game.states.title.TitleState;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class Handler {

    private static Handler handler = null;
    public GameState activeState;
    public TitleState titleState;
    public InstructionState insState;
    public PlayState playState;
    public ScoreState scoreState;
    public OrthographicCamera camera;
    public float screenWidth;
    public float screenHeight;

    public Handler(){

    }

    public static Handler getInstance() {
        if (Handler.handler == null) {
            Handler.handler = new Handler();
        }
        return Handler.handler;
    }

    public void setActiveState(GameState gs){
        this.activeState = gs;
        this.activeState.setActiveInputProcessor();
    }

}

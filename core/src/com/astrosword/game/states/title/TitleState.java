package com.astrosword.game.states.title;

import com.astrosword.game.states.GameState;
import com.astrosword.game.utils.Handler;
import com.astrosword.game.utils.Utils;
import com.astrosword.game.widgets.Button;
import com.astrosword.game.widgets.Dimension;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TitleState extends GameState {
    private Texture backgroundImage, startButtonImg, insButtonImg;
    private Dimension backgroundSize, startSize, insSize;
    private Button startButton, insButton;
    private TitleInputProcessor inputProcessor;
    private Handler handler;

    public TitleState() {
        handler = Handler.getInstance();
        backgroundImage = new Texture("AstroSwordTitleScreen.png");
        startButtonImg = new Texture("AstroSwordStartButton.png");
        insButtonImg = new Texture("AstroSwordInstructionButton.png");
        backgroundSize = new Dimension(0, 0, 1080, 1920);
        inputProcessor = new TitleInputProcessor();
        startSize = new Dimension(321, 1920 - 1437 - 145, 450, 145);
        insSize = new Dimension(321, 1920 - 1638 - 145, 450, 145);
        this.startButton = new Button(startSize, startButtonImg) {
            @Override
            public void onClick(float x, float y) {
                handler.playState.foes.clear();
                handler.setActiveState(handler.playState);
            }
        };
        this.insButton = new Button(insSize, insButtonImg) {
            @Override
            public void onClick(float x, float y) {handler.setActiveState(handler.insState);}
        };
        inputProcessor.addButton(this.startButton);
        inputProcessor.addButton(this.insButton);
    }

    public void setActiveInputProcessor() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    public void render(SpriteBatch batch){
        Utils.drawImg(batch, backgroundImage, backgroundSize);
        startButton.render(batch);
        insButton.render(batch);
    }

    public void tick(){

    }


}

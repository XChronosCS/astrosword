package com.astrosword.game.states.instruction;

import com.astrosword.game.states.GameState;
import com.astrosword.game.utils.Handler;
import com.astrosword.game.utils.Utils;
import com.astrosword.game.widgets.Button;
import com.astrosword.game.widgets.Dimension;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InstructionState extends GameState {
    private Texture backgroundImage, startButtonImg;
    private Dimension backgroundSize, startSize;
    private Button startButton;
    private InstructionInputProcessor inputProcessor;
    private Handler handler;

    public InstructionState() {
        handler = Handler.getInstance();
        backgroundImage = new Texture("AstroSwordInstructionScreen.png");
        startButtonImg = new Texture("AstroSwordStartButton.png");
        backgroundSize = new Dimension(0, 0, 1080, 1920);
        inputProcessor = new InstructionInputProcessor();
        startSize = new Dimension(0, 1920 - 1632 - 145, 450, 145);
        startSize.centerX(handler.screenWidth);
        this.startButton = new Button(startSize, startButtonImg) {
            @Override
            public void onClick(float x, float y) {
                handler.setActiveState(handler.playState);
            }
        };
        inputProcessor.addButton(this.startButton);
    }

    public void setActiveInputProcessor() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    public void render(SpriteBatch batch){
        Utils.drawImg(batch, backgroundImage, backgroundSize);
        startButton.render(batch);
    }

    public void tick(){

    }


}
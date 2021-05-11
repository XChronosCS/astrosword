package com.astrosword.game.states.score;

import com.astrosword.game.AstroSword;
import com.astrosword.game.states.GameState;
import com.astrosword.game.states.instruction.InstructionInputProcessor;
import com.astrosword.game.utils.Handler;
import com.astrosword.game.utils.Utils;
import com.astrosword.game.widgets.Button;
import com.astrosword.game.widgets.Dimension;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreState extends GameState {
    private Preferences preferences;
    private Texture backgroundImg, restartImg, quitImg;
    private Dimension backgroundSize, restartSize, quitSize;
    private Button restart, quit;
    private ScoreInputProcessor inputProcessor;
    private Handler handler;
    BitmapFont font;

    String score;


    public ScoreState(Preferences prefs){
            font = new BitmapFont();
            font.getData().setScale(10,10);
            font.setColor(Color.RED);
            this.preferences = prefs;
            handler = Handler.getInstance();
            backgroundImg = new Texture("AstroSwordScoreScreen.png");
            restartImg = new Texture("restartbutton.png");
            quitImg = new Texture("exitbutton.png");
            backgroundSize = new Dimension(0, 0, 1080, 1920);
            restartSize = new Dimension(0, 1920 - 1632 + 50, 450, 145);
            quitSize = new Dimension(0, 1920 - 1632 - 145, 450, 145);
            restartSize.centerX(handler.screenWidth);
            quitSize.centerX(handler.screenWidth);
            restart = new Button(restartSize, restartImg) {
                @Override
                public void onClick(float x, float y) {
                    handler.playState.foes.clear();
                    handler.playState.score = 0;
                    handler.playState.interval = 3.2f;
                    handler.setActiveState(handler.playState);
                }
            };
            quit = new Button(quitSize, quitImg) {
                @Override
                public void onClick(float x, float y) {
                    Gdx.app.exit();
                }
            };
            inputProcessor = new ScoreInputProcessor();
            inputProcessor.addButton(this.restart);
            inputProcessor.addButton(this.quit);
        }

        public void setActiveInputProcessor() {
            Gdx.input.setInputProcessor(inputProcessor);
        }

    public void render(SpriteBatch batch){
        score = Integer.toString(preferences.getInteger("High Score"));
        Utils.drawImg(batch, backgroundImg, backgroundSize);
        restart.render(batch);
        quit.render(batch);
        font.draw(batch, score, 456, 1043);
        saveHighScore(handler.playState.score);
    }

    public void saveHighScore(int highScore){
        if(preferences.getInteger("High Score", 0) < highScore){
            preferences.putInteger("High Score", highScore);
        }
        preferences.flush();
    }
}

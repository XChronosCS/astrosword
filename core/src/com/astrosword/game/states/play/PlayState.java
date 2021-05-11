package com.astrosword.game.states.play;

import com.astrosword.game.states.GameState;
import com.astrosword.game.states.instruction.InstructionInputProcessor;
import com.astrosword.game.utils.Handler;
import com.astrosword.game.utils.Utils;
import com.astrosword.game.widgets.Animated;
import com.astrosword.game.widgets.Button;
import com.astrosword.game.widgets.Dimension;
import com.astrosword.game.widgets.Enemy;
import com.astrosword.game.widgets.Laser;
import com.astrosword.game.widgets.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public class PlayState extends GameState {
    private final Texture backgroundImage;
    private final Texture fireButtonImg;
    private final Texture arrowUpImg;
    private final Texture arrowDownImg;
    private final Texture arrowLeftImg;
    private final Texture arrowRightImg;
    private final Texture shipImg;
    private final Texture laserImg;
    private final Texture waitImg;
    private final Dimension backgroundSize;
    private final Dimension otherBackgroundSize;
    private final Dimension fireButtonSize;
    private final Dimension arrowLSize;
    private final Dimension arrowRSize;
    private final Dimension arrowUSize;
    private final Dimension arrowDSize;
    private final Dimension shipSize;
    private final Dimension laserSize;
    private final Button upArrow;
    private final Button leftArrow;
    private final Button downArrow;
    private final Button rightArrow;
    private final Button fireButton;
    private final PlayInputProcessor inputProcessor;
    private final Handler handler;
    private final Ship aSword;
    private Laser bullet;
    private final int SKYSPEED = 10;
    private final int SPEED = 15;
    private final int MOVEMENT = 30;
    private final float DELAY = 3.2f;
    private final ArrayList<Laser> blasts;
    public ArrayList<Enemy> foes;
    private final Timer clock;
    public int score = 0;
    private final BitmapFont scorebox;
    private GlyphLayout scoreLayout;
    public float interval = 3;
    private Task createGoblin;
    private Task increaseFrequency;

    public PlayState() {
        handler = Handler.getInstance();
        backgroundImage = new Texture("AstroSwordSkyBackground.png");
        backgroundSize = new Dimension(0, 0, handler.screenWidth, handler.screenHeight);
        otherBackgroundSize = new Dimension(0, backgroundSize.height, handler.screenWidth, handler.screenHeight);
        fireButtonSize = new Dimension(38, 1920-1795, 247, 246);
        arrowLSize = new Dimension(709, 1920-1756, 100, 170);
        arrowRSize = new Dimension(955, 1920-1756, 100, 170);
        arrowUSize = new Dimension(797, 1920-1595, 170, 100);
        arrowDSize = new Dimension(797, 1920-1844, 170, 100);
        arrowUpImg = new Texture("uparrow.png");
        arrowDownImg = new Texture("downarrow.png");
        arrowLeftImg = new Texture("leftarrow.png");
        arrowRightImg = new Texture("rightarrow.png");
        fireButtonImg = new Texture("fire.png");
        waitImg = new Texture("wait.png");
        shipImg = new Texture("astrosword.png");
        shipSize = new Dimension(71, 1920-1207, 320, 320);
        laserImg = new Texture("bullet.png");
        laserSize = new Dimension(0,0, 50,50);
        aSword = new Ship(shipSize, shipImg, MOVEMENT, laserImg, laserSize, SPEED, DELAY);
        blasts = new ArrayList<>();
        rightArrow = new Button(arrowRSize, arrowRightImg) {
            @Override
            public void onClick(float x, float y) {
                aSword.moveRight();
            }

            @Override
            public void heldClick(){
                aSword.moveRight();
            }
        };
        leftArrow = new Button(arrowLSize, arrowLeftImg) {
            @Override
            public void onClick(float x, float y) {
                aSword.moveLeft();
            }

            @Override
            public void heldClick(){
                aSword.moveLeft();
            }
        };
        upArrow = new Button(arrowUSize, arrowUpImg) {
            @Override
            public void onClick(float x, float y) {
                aSword.moveUp();
            }

            @Override
            public void heldClick(){
                aSword.moveUp();
            }
        };
        downArrow = new Button(arrowDSize, arrowDownImg) {
            @Override
            public void onClick(float x, float y) {
                aSword.moveDown();
            }

            @Override
            public void heldClick(){
                aSword.moveDown();
            }
        };
        fireButton = new Button(fireButtonSize, fireButtonImg) {
            @Override
            public void onClick(float x, float y) {
                if(aSword.canShoot()) {
                    blasts.add(aSword.shoot());
                    fireButton.setImage(waitImg);
                }
            }
        };
        inputProcessor = new PlayInputProcessor();
        inputProcessor.addButton(rightArrow);
        inputProcessor.addButton(leftArrow);
        inputProcessor.addButton(upArrow);
        inputProcessor.addButton(downArrow);
        inputProcessor.addButton(fireButton);
        foes = new ArrayList<>();
        scorebox = new BitmapFont();
        scorebox.setColor(Color.GOLD);
        scorebox.getData().setScale(5, 4);
        clock = new Timer();
        createGoblin = new Task() {
            @Override
            public void run() {
                foes.add(spawnGoblin());
            }
        };
        increaseFrequency = new Task() {
            @Override
            public void run() {
                if(interval > .6) {
                    interval -= .2;
                }
                if(createGoblin.isScheduled()){
                    createGoblin.cancel();
                }
                Timer.schedule(createGoblin, 1, interval);
            }
        };
        Timer.schedule(increaseFrequency, 3, 15);
    }

    public void setActiveInputProcessor() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    public void render(SpriteBatch batch){
        loopingBackground(batch, backgroundImage, backgroundSize, otherBackgroundSize);

        for(Button button : inputProcessor.getButtonArrayList()){
            button.render(batch);
        }
        for(Laser laser : blasts){
            laser.render(batch);
            laser.fly();
        }
        fireButton.render(batch);
        for(Enemy goblin  : foes) {
            goblin.render(batch);
            goblin.descend();
        }
        aSword.render(batch);
        inputProcessor.touchHeld();
        aSword.update((float).05);
        collisionDetection();
        shipCollision();
        for(int i = 0; i < blasts.size(); i++){
            Laser laser = blasts.get(i);
            if(laser.getYVal() >= handler.screenHeight){
                blasts.remove(laser);
                i--;
            }
        }
        for(int i = 0; i < foes.size(); i++){
            Enemy goblin = foes.get(i);
            if(goblin.getYVal() <= 0 - goblin.getHeight()){
                foes.remove(goblin);
                i--;
            }
        }
        if(fireButton.getImgButton() == waitImg && aSword.canShoot()){
            fireButton.setImage(fireButtonImg);
        }
        scoreLayout = new GlyphLayout(scorebox, "Score: " + score);
        scorebox.draw(batch, scoreLayout, 400, 80);
    }

    public void tick(){
    }

    public void loopingBackground(SpriteBatch batch, Texture background, Dimension sizeA, Dimension sizeB){
        Utils.drawImg(batch, background, sizeA);
        Utils.drawImg(batch, background, sizeB);
        if(sizeA.y - SKYSPEED > 0 - sizeA.height){
            sizeA.y -= SKYSPEED;
        }else{
            sizeA.y = sizeA.height - 1;
        }
        if(sizeB.y - SKYSPEED > 0 - sizeB.height){
            sizeB.y -= SKYSPEED;
        }else{
            sizeB.y = sizeA.height - 1;
        }
    }

    public Enemy spawnGoblin(){
        Enemy goblin = new Enemy(new Texture("goblinsheet.png"), 2, 2, new Dimension(300, 300, 500, 500), handler.screenWidth, handler.screenHeight);
        goblin.generate();
        return goblin;
    }

    public void collisionDetection(){
        for(int i = 0; i < foes.size(); i++){
            Enemy goblin = foes.get(i);
            for(int j = 0; j < blasts.size(); j++){
                Laser laser = blasts.get(j);
                if(goblin.isHit(laser.getHitBox())){
                    blasts.remove(laser);
                    j--;
                    foes.remove(goblin);
                    i--;
                    score++;
                }
            }
        }
    }

    public void shipCollision(){
        for(int i = 0; i < foes.size(); i++){
            Enemy goblin = foes.get(i);
            if(goblin.isHit(aSword.getHitBox())){
                clock.stop();
                handler.setActiveState(handler.scoreState);
                }
            }
    }

}

package com.astrosword.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Animated{

    private int columns, rows;
    Animation<TextureRegion> spriteAnimation;
    Dimension dim;
    Texture spriteSheet;
    float stateTime;

    public Animated(Texture sheet, int col, int row, Dimension dim){
        this.spriteSheet = sheet;
        columns = col;
        rows = row;
        this.dim = dim;
        TextureRegion[][] temp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / columns, spriteSheet.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[columns * rows];
        this.dim.width = spriteSheet.getWidth() / (float) columns;
        this.dim.width = dim.width;
        this.dim.height = spriteSheet.getHeight() / (float) rows;
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = temp[i][j];
            }
        }

        spriteAnimation = new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0f;
    }


    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = spriteAnimation.getKeyFrame(stateTime,true);
        batch.draw(currentFrame, dim.x, dim.y);

    }

    public Rectangle getHitBox() {return new Rectangle(dim.x + 80 , dim.y + 130, dim.width - 160, dim.height - 260);}



}

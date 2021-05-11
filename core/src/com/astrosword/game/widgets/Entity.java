package com.astrosword.game.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Entity {

    public float getXVal() {
        return dim.x;
    }

    public float getYVal() {
        return dim.y;
    }

    public float getWidth() {
        return dim.width;
    }

    public float getHeight() {
        return dim.height;
    }
    
    protected Texture img;
    protected Dimension dim;

    public Entity(Dimension dim, Texture img){
        this.dim = dim;
        this.img = img;
    }

    public void render(SpriteBatch batch){
        batch.draw(img, dim.x, dim.y, dim.width, dim.height);
    }

    public void setPosition(float xNow, float yNow){
        this.dim.x = xNow;
        this.dim.y = yNow;
    }

    public Rectangle getHitBox() {return new Rectangle(dim.x, dim.y, dim.width, dim.height);}

}

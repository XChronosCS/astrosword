package com.astrosword.game.widgets;
import java.util.Random;

import com.astrosword.game.utils.Handler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Animated{

    float screen_width, screen_height;
    public Enemy(Texture sheet, int col, int row, Dimension dim, float width, float height) {
        super(sheet, col, row, dim);
        this.screen_height = height;
        this.screen_width = width;
    }

    public void generate(){
        Random rand = new Random();
        float x_val = 0 + rand.nextFloat() * (screen_width - dim.width);
        dim.x = x_val;
        dim.y = screen_height;
    }

    public void descend(){
        dim.y -= 5;
    }

    public boolean isHit(Rectangle hitBox){
        return hitBox.overlaps(getHitBox());
    }

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


}

package com.astrosword.game.widgets;

import com.badlogic.gdx.graphics.Texture;

public class Projectile extends Entity{
    float velocity;
    public Projectile(Dimension dim, Texture img, float speed) {
        super(dim, img);
        this.velocity = speed;
    }

    public void fly(){
        this.dim.y += velocity;
    }

}

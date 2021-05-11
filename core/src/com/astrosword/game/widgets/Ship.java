package com.astrosword.game.widgets;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;

import javax.xml.soap.Text;

public class Ship extends Entity{
    float velocity;
    Rectangle hitBox;
    float timeBetweenShots;
    float timeSinceLastShot = 0;
    Texture laserImg;
    Dimension laserDetails;
    float laserSpeed;
    public Ship(Dimension dim, Texture img,  float speed,  Texture laser, Dimension laserDeets, float lSpeed, float delay) {
        super(dim, img);
        this.hitBox = this.getHitBox();
        this.laserImg = laser;
        this.laserDetails = laserDeets;
        this.velocity = speed;
        this.laserSpeed = lSpeed;
        this.timeBetweenShots = delay;
    }

    public void update(float time){
        hitBox.set(getXVal(), getYVal(), getWidth(), getHeight());
        timeSinceLastShot += time;
    }

    public boolean canShoot(){
        return (timeSinceLastShot - timeBetweenShots) >= 0;}

    public boolean intersects(Rectangle Enemy){
        return hitBox.overlaps(Enemy);
    }

    public Laser shoot(){
        timeSinceLastShot = 0;
        return new Laser(new Dimension((getXVal() + getWidth() / 2) - laserDetails.width/2, (getYVal()+getHeight()/2) - laserDetails.height/2, laserDetails.width, laserDetails.height), laserImg, laserSpeed);
    }

    public void moveLeft(){
        this.dim.x -= velocity;
    }

    public void moveRight(){
        this.dim.x += velocity;
    }

    public void moveUp(){
        this.dim.y += velocity;
    }

    public void moveDown(){
        this.dim.y -= velocity;
    }
}

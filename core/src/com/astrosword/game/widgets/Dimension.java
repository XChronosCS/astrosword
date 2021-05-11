package com.astrosword.game.widgets;

public class Dimension {
    public float x;
    public float y;
    public float width;
    public float height;


    public Dimension(float xVal, float yVal, float widthVal, float heightVal){
        this.x = xVal;
        this.y = yVal;
        this.width = widthVal;
        this.height = heightVal;
    }

    public void centerX(float screenWidth) {
        this.x = (screenWidth / 2) - (width / 2);
    }
}

package com.astrosword.game.states.play;

import com.astrosword.game.utils.Handler;
import com.astrosword.game.utils.Utils;
import com.astrosword.game.widgets.Button;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class PlayInputProcessor implements InputProcessor {

    private Handler handler;

    public ArrayList<Button> getButtonArrayList() {
        return buttonArrayList;
    }

    private ArrayList<Button> buttonArrayList;

    public PlayInputProcessor(){
        buttonArrayList = new ArrayList<>();
        handler = Handler.getInstance();
    }


    public void addButton(Button button) {buttonArrayList.add(button);}

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 mathVector = Utils.getCameraPosFromMousePos(handler.camera, screenX, screenY);
        float translatedX = mathVector.x;
        float translatedY = mathVector.y;
        for(int i = 0; i < buttonArrayList.size(); i++){
            Button curButton = buttonArrayList.get(i);
            if(curButton.wasClicked(translatedX, translatedY)){
                curButton.isPushed = true;
                curButton.onClick(translatedX, translatedY);
            }
        }
        return false;
    }

    public boolean touchHeld(){
        for(int i = 0; i < buttonArrayList.size(); i++) {
            Button curButton = buttonArrayList.get(i);
            if (curButton.isPushed) {
                curButton.heldClick();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 mathVector = Utils.getCameraPosFromMousePos(handler.camera, screenX, screenY);
        float translatedX = mathVector.x;
        float translatedY = mathVector.y;
        for(int i = 0; i < buttonArrayList.size(); i++){
            Button curButton = buttonArrayList.get(i);
            curButton.isPushed = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
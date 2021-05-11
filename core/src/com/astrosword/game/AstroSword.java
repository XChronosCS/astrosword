package com.astrosword.game;

import com.astrosword.game.states.instruction.InstructionState;
import com.astrosword.game.states.play.PlayState;
import com.astrosword.game.states.score.ScoreState;
import com.astrosword.game.states.title.TitleState;
import com.astrosword.game.utils.Handler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AstroSword extends ApplicationAdapter {
	SpriteBatch batch;
	private Handler handler;
	private TitleState titleState;
	private InstructionState insState;
	private PlayState playState;
	private ScoreState scoreState;
	private static final float SCREEN_WIDTH = 1080;
	private static final float SCREEN_HEIGHT = 1920;
	OrthographicCamera camera;

	@Override
	public void create () {
		handler = Handler.getInstance();
		Preferences preferences = Gdx.app.getPreferences("My Preferences");
		handler.screenHeight = SCREEN_HEIGHT;
		handler.screenWidth = SCREEN_WIDTH;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		handler.camera = camera;
		batch = new SpriteBatch();
		titleState = new TitleState();
		playState = new PlayState();
		insState = new InstructionState();
		scoreState = new ScoreState(preferences);
		handler.titleState = titleState;
		handler.scoreState = scoreState;
		handler.playState = playState;
		handler.insState = insState;
		handler.setActiveState(handler.titleState);
	}

	@Override
	public void render () {
			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			handler.activeState.render(batch);
			handler.activeState.tick();
			batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

package com.cosmosis.icicles.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cosmosis.icicles.Constants;
import com.cosmosis.icicles.Constants.Difficulty;
import com.cosmosis.icicles.IciclesGame;
import com.cosmosis.icicles.Sprites.Icicles.Icicle;
import com.cosmosis.icicles.Sprites.Icicles.Icicles;
import com.cosmosis.icicles.Sprites.Player;

/**
 * Created by cal on 3/23/17.
 */

public class IciclesScreen extends ScreenAdapter implements InputProcessor{
    public static final String TAG = IciclesScreen.class.getName();

    IciclesGame game;

    ExtendViewport viewport;
    ShapeRenderer renderer;
    Icicles icicles;
    Player player;
    Difficulty difficulty;

    ScreenViewport hudViewport;
    SpriteBatch batch;
    BitmapFont font;

    int highScore;

    public IciclesScreen(IciclesGame game, Difficulty difficulty) {
        super();
        this.game = game;
        this.difficulty = difficulty;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        player = new Player(viewport);
        icicles = new Icicles(viewport, difficulty);

        hudViewport = new ScreenViewport();
        batch = new SpriteBatch();
        font = new BitmapFont();
        highScore = 0;

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        //icicle = new Icicle(new Vector2(Constants.WORLD_SIZE / 2, Constants.WORLD_SIZE / 2));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);

        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);

        player.init();
        icicles.init();
    }

    public void update(float delta) {
        player.update(delta);
        icicles.update(delta);

        if (player.isHitByIcicle(icicles)) icicles.init();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply(true);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        player.render(renderer);
        icicles.render(renderer);

        highScore = Math.max(highScore, icicles.iciclesDodged);

        hudViewport.apply(true);
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        String deathString = "Deaths: " + player.numDeaths + "\nDifficulty: " + difficulty.label;
        font.draw(batch, deathString, Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN);
        String scoreString = "Score: " + icicles.iciclesDodged + "\nHigh Score: " + highScore;
        font.draw(batch, scoreString, hudViewport.getWorldWidth() - Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        batch.end();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }

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
        game.showDifficultyScreen();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
    public boolean scrolled(int amount) {
        return false;
    }
}

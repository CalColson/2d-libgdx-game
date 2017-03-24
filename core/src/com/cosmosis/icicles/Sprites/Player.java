package com.cosmosis.icicles.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cosmosis.icicles.Constants;
import com.cosmosis.icicles.Sprites.Icicles.Icicle;
import com.cosmosis.icicles.Sprites.Icicles.Icicles;

/**
 * Created by cal on 3/23/17.
 */

public class Player {
    public static final String TAG = Player.class.getName();

    Vector2 position;
    Viewport viewport;
    public int numDeaths;

    public Player(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        position = new Vector2(viewport.getWorldWidth() / 2, Constants.PLAYER_HEAD_RADIUS + Constants.BOT_MARGIN);
        numDeaths = 0;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= delta * Constants.PLAYER_MOVEMENT_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += delta * Constants.PLAYER_MOVEMENT_SPEED;
        }

        float accelerometerInput = Gdx.input.getAccelerometerY() / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY);
        position.x += delta * accelerometerInput * Constants.PLAYER_MOVEMENT_SPEED;

        ensureInBounds();
    }

    public void render(ShapeRenderer renderer) {

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Constants.PLAYER_COLOR);
        renderer.circle(position.x, position.y, Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_HEAD_SEGMENTS);
        renderer.end();
    }

    public boolean isHitByIcicle(Icicles icicles) {
        boolean isHit = false;

        Vector2 pointPosition = new Vector2();
        for (Icicle i : icicles.getIcicles()) {
            pointPosition.x = i.getPosition().x + 0.5f * i.getWidth();
            pointPosition.y = i.getPosition().y - i.getHeight();
            if (pointPosition.dst(position) < Constants.PLAYER_HEAD_RADIUS) isHit = true;
        }

        if (isHit) numDeaths++;

        return isHit;
    }

    private void ensureInBounds() {
        if (position.x - Constants.PLAYER_HEAD_RADIUS < 0) {
            position.x = Constants.PLAYER_HEAD_RADIUS;
        }
        else if (position.x + Constants.PLAYER_HEAD_RADIUS > viewport.getWorldWidth()) {
            position.x = viewport.getWorldWidth() - Constants.PLAYER_HEAD_RADIUS;
        }
    }
}

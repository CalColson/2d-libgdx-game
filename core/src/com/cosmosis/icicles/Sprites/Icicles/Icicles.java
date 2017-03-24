package com.cosmosis.icicles.Sprites.Icicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cosmosis.icicles.Constants;
import com.cosmosis.icicles.Constants.Difficulty;

/**
 * Created by cal on 3/23/17.
 */

public class Icicles {
    public static final String TAG = Icicles.class.getName();

    Viewport viewport;
    DelayedRemovalArray<Icicle> icicles;
    Difficulty difficulty;

    public int iciclesDodged;

    public Icicles(Viewport viewport, Difficulty difficulty) {
        this.viewport = viewport;
        this.difficulty = difficulty;
        init();
    }

    public void init() {
        icicles = new DelayedRemovalArray<Icicle>(false, 100);
        iciclesDodged = 0;
    }

    public void update(float delta) {
        if (MathUtils.random() < delta * difficulty.spawnRate) {
            Vector2 position = new Vector2(MathUtils.random() * viewport.getWorldWidth(),
                    viewport.getWorldHeight());
            Icicle icicle = new Icicle(position);
            icicles.add(icicle);
        }

        icicles.begin();

        Icicle icicle = null;
        for (int i = 0; i < icicles.size; i++) {
            icicle = icicles.get(i);
            icicle.update(delta);
            if (icicle.getPosition().y < 0) {
                icicles.removeIndex(i);
                iciclesDodged++;
            }
        }

        icicles.end();

        //Gdx.app.log(TAG, String.valueOf(icicles.size));
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.ICICLE_COLOR);
        for (Icicle i : icicles) i.render(renderer);
    }

    public DelayedRemovalArray<Icicle> getIcicles() {
        return icicles;
    }
}

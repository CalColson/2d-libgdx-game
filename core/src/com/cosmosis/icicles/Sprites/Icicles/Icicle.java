package com.cosmosis.icicles.Sprites.Icicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.cosmosis.icicles.Constants;

/**
 * Created by cal on 3/23/17.
 */

public class Icicle {
    public static final String TAG = Icicle.class.getName();

    private Vector2 position;
    private float width;
    private float height;
    private Color color;

    public Icicle(Vector2 position) {
        this.position = position;
        width = Constants.ICICLES_WIDTH;
        height = Constants.ICICLES_HEIGHT;
        color = Constants.ICICLE_COLOR;
    }

    public void render(ShapeRenderer renderer) {
        Vector2 pointA = position;
        Vector2 pointB = new Vector2(pointA.x + width, pointA.y);
        Vector2 pointC = new Vector2(pointA.x + width / 2, pointA.y - height);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.triangle(pointA.x, pointA.y, pointB.x, pointB.y, pointC.x, pointC.y);
        renderer.end();
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }
}

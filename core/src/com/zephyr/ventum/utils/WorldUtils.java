package com.zephyr.ventum.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by sashaklimenko on 7/12/17.
 */

public class WorldUtils {

    public static World createWorld() {

        return new World(Constants.GRAVITY, true);
    }


    public static Body createSpinner(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Constants.SPINNER_POSITIONS);
        Body body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(Constants.SPINNER_SIZE);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.55f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        circle.dispose();
        return body;
    }

    public static Array<Body> createGround(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        Body bodyGround = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_SIZE.x, Constants.GROUND_SIZE.y);
        bodyGround.createFixture(shape, 0f);
        shape.dispose();

        Array<Body> bodies = new Array<Body>();
        bodies.add(bodyGround);
        bodies.add(createSky(world));
        return bodies;
    }

    public static Body createSky(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, Constants.HEIGHT);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_SIZE.x, 0);
        body.createFixture(shape, 0f);
        shape.dispose();
        return body;
    }

    public static Array<Body> createTopTube(World world, float posX) {

        float posY = generateTubePosition();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(posX, posY);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.TUBE_WIDTH/2, 30/2);
        Body bodyTop = world.createBody(bodyDef);
        bodyTop.createFixture(shape, 12f);
        bodyTop.resetMassData();
        shape.dispose();
        bodyTop.setLinearVelocity(Constants.TUBE_SPEED, 0.0f);

        Array<Body> bodies = new Array<Body>();
        bodies.add(bodyTop);
        bodies.add(createBottomTube(world, posX, posY));
        return bodies;
    }

    public static Body createBottomTube(World world,float posX, float posY) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(posX, posY - Constants.TUBE_SPACING - 30);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.TUBE_WIDTH/2, 30/2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, 12f);
        body.resetMassData();
        shape.dispose();
        body.setLinearVelocity(Constants.TUBE_SPEED, 0.0f);
        return body;
    }

    public static float generateTubePosition(){
        int margin = random.nextInt(20) - 10;
        return Constants.HEIGHT + margin;
    }


}

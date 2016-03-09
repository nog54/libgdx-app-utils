/** Copyright 2015 Goshi Noguchi (noggon54@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. */

package org.nognog.gdx.activity.transition;

import static org.nognog.gdx.activity.transition.SlideOutAndInTransition.TransitionPhase.START;
import static org.nognog.gdx.activity.transition.SlideOutAndInTransition.TransitionPhase.SLIDE_OUT;
import static org.nognog.gdx.activity.transition.SlideOutAndInTransition.TransitionPhase.CHANGE_BACKGROUND;
import static org.nognog.gdx.activity.transition.SlideOutAndInTransition.TransitionPhase.SET_ACTIVITY;
import static org.nognog.gdx.activity.transition.SlideOutAndInTransition.TransitionPhase.SLIDE_IN;
import static org.nognog.gdx.activity.transition.SlideOutAndInTransition.TransitionPhase.END;

import org.nognog.gdx.activity.ActorsActivity;
import org.nognog.gdx.ui.Direction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

/**
 * @author goshi 2015/11/18
 */
public class SlideOutAndInTransition extends Transition {

	private Border fromActivityBorder;
	private Border toActivityBorder;
	private Vector2 initialPositionOfFromCamera;
	private Vector2 initialPositionOfToCamera;
	private Color initialBackgroundColorOfFrom;

	private float timeToChangeBackground;
	private float totalChangingBackgroundTime;
	private float slopeR;
	private float slopeG;
	private float slopeB;
	private float slopeA;

	private final Direction direction;
	private float slideSpeed;

	private TransitionPhase phase;

	/**
	 * default speed of slide [logicalWidth / sec]
	 */
	public static final float defaultSlideSpeed = 4000;
	/**
	 * defalut time to change background [sec]
	 */
	public static final float defaultTimeToChangeBackground = 0.125f;

	/**
	 * @param direction
	 * 
	 */
	public SlideOutAndInTransition(Direction direction) {
		this(direction, defaultSlideSpeed);
	}

	/**
	 * @param direction
	 * @param slideSpeed
	 */
	public SlideOutAndInTransition(Direction direction, float slideSpeed) {
		this(direction, slideSpeed, defaultTimeToChangeBackground);
	}

	/**
	 * @param direction
	 * @param slideSpeed
	 * @param timeToChangeBackground
	 * 
	 */
	public SlideOutAndInTransition(Direction direction, float slideSpeed, float timeToChangeBackground) {
		this.phase = START;
		if (direction != null) {
			this.direction = direction;
		} else {
			this.direction = Direction.RIGHT;
		}
		this.slideSpeed = slideSpeed;
		this.timeToChangeBackground = Math.max(0, timeToChangeBackground);
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * @return the slideSpeed
	 */
	public float getSlideSpeed() {
		return this.slideSpeed;
	}

	/**
	 * @param slideSpeed
	 *            the slideSpeed to set
	 */
	public void setSlideSpeed(float slideSpeed) {
		this.slideSpeed = slideSpeed;
	}

	/**
	 * @return the timeToChangeBackground
	 */
	public float getTimeToChangeBackground() {
		return this.timeToChangeBackground;
	}

	/**
	 * @param timeToChangeBackground
	 *            the timeToChangeBackground to set
	 */
	public void setTimeToChangeBackground(float timeToChangeBackground) {
		this.timeToChangeBackground = timeToChangeBackground;
	}

	@Override
	public boolean proceed(float deltaTime) {
		final ActorsActivity from = (ActorsActivity) this.getFromActivity();
		final ActorsActivity to = (ActorsActivity) this.getToActivity();
		if (this.phase == START) {
			this.performStart(deltaTime, from, to);
			return false;
		}
		try {
			if (this.phase == SLIDE_OUT) {
				this.performSlideOut(deltaTime, from, to);
				return false;
			}
			if (this.phase == CHANGE_BACKGROUND) {
				this.performChangeBackground(deltaTime, from, to);
				return false;
			}
			if (this.phase == SET_ACTIVITY) {
				this.performSetActivity(deltaTime, from, to);
				return false;
			}
			if (this.phase == SLIDE_IN) {
				this.performSlideIn(deltaTime, from, to);
				if (this.phase != END) {
					return false;
				}
			}
		} catch (Exception e) {
			this.getApplication().setActivity(to);
		}
		this.setTargetValues(from, to);
		return true;
	}

	private void setTargetValues(final ActorsActivity from, final ActorsActivity to) {
		from.getCamera().position.x = this.initialPositionOfFromCamera.x;
		from.getCamera().position.y = this.initialPositionOfFromCamera.y;
		to.getCamera().position.x = this.initialPositionOfToCamera.x;
		to.getCamera().position.y = this.initialPositionOfToCamera.y;
		from.setBackgroundColorOfLogicalWorld(this.initialBackgroundColorOfFrom);
	}

	/**
	 * 
	 */
	private void performStart(float deltaTime, ActorsActivity from, ActorsActivity to) {
		this.getApplication().disableInput();
		this.fromActivityBorder = this.getBorder(from);
		this.toActivityBorder = this.getBorder(to);
		this.initialPositionOfFromCamera = new Vector2(from.getCamera().position.x, from.getCamera().position.y);
		this.initialPositionOfToCamera = new Vector2(to.getCamera().position.x, to.getCamera().position.y);
		this.initialBackgroundColorOfFrom = new Color(from.getBackgroundColorOfLogicalWorld());

		from.setBackgroundColorOfLogicalWorld(new Color(this.initialBackgroundColorOfFrom));
		final Color fromColor = from.getBackgroundColorOfLogicalWorld();
		final Color toColor = to.getBackgroundColorOfLogicalWorld();

		this.slopeR = (toColor.r - fromColor.r) / this.timeToChangeBackground;
		this.slopeG = (toColor.g - fromColor.g) / this.timeToChangeBackground;
		this.slopeB = (toColor.b - fromColor.b) / this.timeToChangeBackground;
		this.slopeA = (toColor.a - fromColor.a) / this.timeToChangeBackground;
		this.phase = SLIDE_OUT;
	}

	private Border getBorder(ActorsActivity activity) {
		final Border result = new Border();
		final Actor[] actors = getAllElementaryActors(activity.getStage().getRoot()).toArray(Actor.class);
		if (actors.length == 0) {
			return result;
		}
		Vector2 tmp = new Vector2(0, 0);
		result.leftEndX = actors[0].localToStageCoordinates(tmp).x;
		result.rightEndX = result.leftEndX + actors[0].getWidth();
		result.buttomEndY = actors[0].localToStageCoordinates(tmp).y;
		result.topEndY = result.buttomEndY + actors[0].getHeight();
		for (int i = 1; i < actors.length; i++) {
			final Vector2 actorPosition = actors[i].localToStageCoordinates(tmp.set(0, 0));
			final float x = actorPosition.x;
			final float y = actorPosition.y;
			result.leftEndX = Math.min(x, result.leftEndX);
			result.rightEndX = Math.max(x + actors[i].getWidth(), result.rightEndX);
			result.buttomEndY = Math.min(y, result.buttomEndY);
			result.topEndY = Math.max(y + actors[i].getHeight(), result.topEndY);
		}
		return result;
	}

	private Array<Actor> getAllElementaryActors(Group group) {
		Array<Actor> result = new Array<>();
		for (Actor actor : group.getChildren()) {
			if (actor instanceof Group) {
				result.addAll(getAllElementaryActors((Group) actor));
			} else {
				result.add(actor);
			}
		}
		return result;
	}

	private void performSlideOut(float deltaTime, ActorsActivity from, ActorsActivity to) {
		final OrthographicCamera camera = (OrthographicCamera) from.getCamera();
		this.moveCamera(camera, deltaTime);
		final float viewingWidth = camera.viewportWidth * camera.zoom;
		final float viewingHeight = camera.viewportHeight * camera.zoom;
		final boolean completeSlideOut = (camera.position.x + viewingWidth / 2 < this.fromActivityBorder.leftEndX) || (camera.position.x - viewingWidth / 2 > this.fromActivityBorder.rightEndX)
				|| (camera.position.y + viewingHeight / 2 < this.fromActivityBorder.buttomEndY) || (camera.position.y - viewingHeight / 2 > this.fromActivityBorder.topEndY);
		if (completeSlideOut) {
			if (this.timeToChangeBackground == 0) {
				this.phase = SET_ACTIVITY;
			} else {
				this.phase = CHANGE_BACKGROUND;
			}
		}
	}

	private void moveCamera(OrthographicCamera camera, float deltaTime) {
		final float speedX = this.getSpeedX();
		final float speedY = this.getSpeedY();
		camera.position.x += speedX * deltaTime;
		camera.position.y += speedY * deltaTime;
	}

	private float getSpeedX() {
		if (this.direction == Direction.LEFT) {
			return -this.slideSpeed;
		}
		if (this.direction == Direction.RIGHT) {
			return this.slideSpeed;
		}
		return 0;
	}

	private float getSpeedY() {
		if (this.direction == Direction.DOWN) {
			return -this.slideSpeed;
		}
		if (this.direction == Direction.UP) {
			return this.slideSpeed;
		}
		return 0;
	}

	private void performChangeBackground(float deltaTime, ActorsActivity from, ActorsActivity to) {
		this.totalChangingBackgroundTime += deltaTime;
		if (this.totalChangingBackgroundTime >= this.timeToChangeBackground) {
			this.phase = TransitionPhase.SET_ACTIVITY;
			return;
		}
		final float addendR = this.slopeR * deltaTime;
		final float addendG = this.slopeG * deltaTime;
		final float addendB = this.slopeB * deltaTime;
		final float addendA = this.slopeA * deltaTime;
		from.getBackgroundColorOfLogicalWorld().add(addendR, addendG, addendB, addendA);
	}

	private void performSetActivity(float deltaTime, ActorsActivity from, ActorsActivity to) {
		this.getApplication().setActivity(to);

		final OrthographicCamera camera = (OrthographicCamera) to.getCamera();
		final float viewingWidth = camera.viewportWidth * camera.zoom;
		final float viewingHeight = camera.viewportHeight * camera.zoom;
		if (this.direction == Direction.LEFT) {
			to.getCamera().position.x = this.toActivityBorder.rightEndX + viewingWidth;
		} else if (this.direction == Direction.RIGHT) {
			to.getCamera().position.x = this.toActivityBorder.leftEndX - viewingWidth;
		} else if (this.direction == Direction.DOWN) {
			to.getCamera().position.y = this.toActivityBorder.topEndY + viewingHeight;
		} else if (this.direction == Direction.UP) {
			to.getCamera().position.y = this.toActivityBorder.buttomEndY - viewingHeight;
		}
		this.phase = SLIDE_IN;
	}

	private void performSlideIn(float deltaTime, ActorsActivity from, ActorsActivity to) {
		final OrthographicCamera camera = (OrthographicCamera) to.getCamera();
		this.moveCamera(camera, deltaTime);

		if (this.isCompleteSlideOut(camera)) {
			this.phase = END;
		}
	}

	private boolean isCompleteSlideOut(final OrthographicCamera camera) {
		if (this.direction == Direction.LEFT) {
			return (camera.position.x <= this.initialPositionOfToCamera.x);
		}
		if (this.direction == Direction.RIGHT) {
			return (camera.position.x >= this.initialPositionOfToCamera.x);
		}
		if (this.direction == Direction.DOWN) {
			return (camera.position.y <= this.initialPositionOfToCamera.y);
		}
		if (this.direction == Direction.UP) {
			return (camera.position.y >= this.initialPositionOfToCamera.y);
		}
		throw new RuntimeException("direction hasn't setted"); //$NON-NLS-1$
	}

	enum TransitionPhase {
		START, SLIDE_OUT, CHANGE_BACKGROUND, SET_ACTIVITY, SLIDE_IN, END
	}

	private static class Border {
		public Border() {
		}

		public float leftEndX, rightEndX, buttomEndY, topEndY;
	}
}

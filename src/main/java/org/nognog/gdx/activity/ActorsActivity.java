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

package org.nognog.gdx.activity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author goshi 2015/09/18
 */
public abstract class ActorsActivity extends ApplicationActivity {
	private final Stage stage;
	private Color wholeBackgroundColor;
	private ShapeRenderer logicalWorldBackgroundRenderer;

	private boolean isEnabledToMoveCameraX = false;
	private boolean isEnabledToMoveCameraY = false;
	private final Range movableRange;
	private boolean isEnabledInertia = false;
	protected float cameraVX = 0;
	protected float cameraVY = 0;
	private float decelaration = 100;

	private final InputMultiplexer inputMultiplexer;

	/**
	 * 
	 */
	public ActorsActivity() {
		this(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}

	/**
	 * @param viewport
	 */
	public ActorsActivity(Viewport viewport) {
		this.stage = new Stage(viewport);
		this.wholeBackgroundColor = new Color(Color.BLACK);
		this.movableRange = new Range(0, viewport.getWorldWidth(), 0, viewport.getWorldHeight());
		this.inputMultiplexer = new InputMultiplexer();
		final GestureListener gestureListener = new GestureAdapter() {

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				ActorsActivity.this.cameraVX = 0;
				ActorsActivity.this.cameraVY = 0;
				return super.touchDown(x, y, pointer, button);
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				if (ActorsActivity.this.isEnabledInertia()) {
					if (ActorsActivity.this.isEnabledToMoveCameraX()) {
						ActorsActivity.this.cameraVX = -velocityX;
					}
					if (ActorsActivity.this.isEnabledToMoveCameraY()) {
						ActorsActivity.this.cameraVY = velocityY;
					}
				}
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				if (!ActorsActivity.this.isEnabledToMoveCameraX() && !ActorsActivity.this.isEnabledToMoveCameraY()) {
					return false;
				}
				final Camera camera = ActorsActivity.this.getCamera();
				final Vector2 delta = new Vector2(deltaX, deltaY);
				delta.x *= camera.viewportWidth / Gdx.graphics.getWidth();
				delta.y *= camera.viewportHeight / Gdx.graphics.getHeight();
				if (camera instanceof OrthographicCamera) {
					this.moveCameraX(camera, -delta.x * ((OrthographicCamera) camera).zoom);
					this.moveCameraY(camera, delta.y * ((OrthographicCamera) camera).zoom);
				} else {
					this.moveCameraX(camera, -delta.x);
					this.moveCameraY(camera, delta.y);
				}
				ActorsActivity.this.adjustCameraPosition();
				return false;
			}

			private void moveCameraX(Camera camera, float amountX) {
				if (ActorsActivity.this.isEnabledToMoveCameraX()) {
					camera.position.x += amountX;
				}
			}

			private void moveCameraY(Camera camera, float amountY) {
				if (ActorsActivity.this.isEnabledToMoveCameraY()) {
					camera.position.y += amountY;
				}
			}
		};
		this.inputMultiplexer.addProcessor(new GestureDetector(gestureListener));
		this.inputMultiplexer.addProcessor(this.stage);
	}

	protected void adjustCameraPosition() {
		final Camera camera = this.getCamera();
		float viewingWidth = camera.viewportWidth;
		float viewingHeight = camera.viewportHeight;
		if (camera instanceof OrthographicCamera) {
			viewingWidth *= ((OrthographicCamera) camera).zoom;
			viewingHeight *= ((OrthographicCamera) camera).zoom;
		}
		final Range range = ActorsActivity.this.getMovableRange();
		if (viewingWidth >= range.getWidth()) {
			camera.position.x = (range.getLeftBorder() + range.getRightBorder()) / 2;
		} else {
			final float minCameraPositionX = range.getLeftBorder() + viewingWidth / 2f;
			final float maxCameraPositionX = range.getRightBorder() - viewingWidth / 2f;
			camera.position.x = MathUtils.clamp(camera.position.x, minCameraPositionX, maxCameraPositionX);
		}
		if (viewingHeight >= range.getHeight()) {
			camera.position.y = (range.getBottomBorder() + range.getTopBorder()) / 2;
		} else {
			final float minCameraPositionY = range.getBottomBorder() + viewingHeight / 2f;
			final float maxCameraPositionY = range.getTopBorder() - viewingHeight / 2f;
			camera.position.y = MathUtils.clamp(camera.position.y, minCameraPositionY, maxCameraPositionY);
		}
	}

	@Override
	public void setApplication(ActivityBasedApplication application) {
		if (this.getApplication() == application) {
			return;
		}
		if (!(application instanceof ActivityBased2dApplication)) {
			throw new IllegalArgumentException();
		}
		super.setApplication(application);
		this.init(((ActivityBased2dApplication) application).getSkin(), application.getConfigurations());
	}

	protected abstract void init(Skin applicationSkin, ApplicationConfigurations configurations);

	/**
	 * @return the skin
	 */
	public Skin getSkin() {
		if (this.getApplication() == null) {
			return null;
		}
		return ((ActivityBased2dApplication) this.getApplication()).getSkin();
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this.inputMultiplexer;
	}

	@Override
	public void render(float delta) {
		this.stage.act(delta);
		if (this.isEnabledInertia) {
			this.applyInertia(delta);
		}
		Gdx.gl.glClearColor(this.wholeBackgroundColor.r, this.wholeBackgroundColor.g, this.wholeBackgroundColor.b, this.wholeBackgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (this.logicalWorldBackgroundRenderer != null) {
			this.logicalWorldBackgroundRenderer.begin(ShapeType.Filled);
			this.logicalWorldBackgroundRenderer.rect(0, 0, this.getLogicalViewportWidth(), this.getLogicalViewportHeight());
			this.logicalWorldBackgroundRenderer.end();
		}
		this.stage.draw();
	}

	private void applyInertia(float delta) {
		if (this.cameraVX == 0 && this.cameraVY == 0) {
			return;
		}
		final Camera camera = this.stage.getCamera();
		this.applyInertiaX(delta, camera);
		this.applyInertiaY(delta, camera);
		final float prevX = camera.position.x;
		final float prevY = camera.position.y;
		this.adjustCameraPosition();
		if (prevX != camera.position.x) {
			this.cameraVX = 0;
		}
		if (prevY != camera.position.y) {
			this.cameraVY = 0;
		}
	}

	private void applyInertiaX(float delta, final Camera camera) {
		if (this.cameraVX != 0) {
			camera.position.x += this.cameraVX * delta;
			if (Math.abs(this.cameraVX) <= this.decelaration) {
				this.cameraVX = 0;
				return;
			}
			if (this.cameraVX < 0) {
				this.cameraVX += this.decelaration;
			} else {
				this.cameraVX -= this.decelaration;
			}
		}
	}

	private void applyInertiaY(float delta, final Camera camera) {
		if (this.cameraVY != 0) {
			camera.position.y += this.cameraVY * delta;
			if (Math.abs(this.cameraVY) <= this.decelaration) {
				this.cameraVY = 0;
				return;
			}
			if (this.cameraVY < 0) {
				this.cameraVY += this.decelaration;
			} else {
				this.cameraVY -= this.decelaration;
			}
		}
	}

	@Override
	public void hide() {
		this.cameraVX = 0;
		this.cameraVY = 0;
	}

	@Override
	public void show() {
		// this method should be overridden
	}

	@Override
	public void pause() {
		// this method should be overridden
	}

	@Override
	public void resume() {
		// this method should be overridden
	}

	@Override
	public void dispose() {
		// this method should be overridden
	}

	/**
	 * @param actor
	 */
	public void addActor(Actor actor) {
		this.stage.getRoot().addActor(actor);
	}

	/**
	 * @param actor
	 */
	public void removeActor(Actor actor) {
		this.stage.getRoot().removeActor(actor);
	}

	/**
	 * @return stage
	 */
	public Stage getStage() {
		return this.stage;
	}

	@Override
	public void resize(int width, int height) {
		this.stage.getViewport().update(width, height);
	}

	/**
	 * @return width of viewport
	 */
	public float getLogicalViewportWidth() {
		return this.stage.getViewport().getWorldWidth();
	}

	/**
	 * @return height of viewport
	 */
	public float getLogicalViewportHeight() {
		return this.stage.getViewport().getWorldHeight();
	}

	/**
	 * @return camera
	 */
	public Camera getCamera() {
		return this.stage.getCamera();
	}

	/**
	 * @return the wholeBackgroundColor
	 */
	public Color getWholeBackgroundColor() {
		return this.wholeBackgroundColor;
	}

	/**
	 * @param wholeBackgroundColor
	 *            the wholeBackgroundColor to set
	 */
	public void setWholeBackgroundColor(Color wholeBackgroundColor) {
		this.wholeBackgroundColor = wholeBackgroundColor;
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColorOfLogicalWorld() {
		if (this.logicalWorldBackgroundRenderer == null) {
			return null;
		}
		return this.logicalWorldBackgroundRenderer.getColor();
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColorOfLogicalWorld(Color backgroundColor) {
		if (backgroundColor == null) {
			this.logicalWorldBackgroundRenderer = null;
			return;
		}
		if (this.logicalWorldBackgroundRenderer == null) {
			this.logicalWorldBackgroundRenderer = new ShapeRenderer();
		}
		this.logicalWorldBackgroundRenderer.setColor(backgroundColor);
	}

	/**
	 * @return the isEnabledToMoveCameraX
	 */
	public boolean isEnabledToMoveCameraX() {
		return this.isEnabledToMoveCameraX;
	}

	/**
	 * @param isEnabledToMoveCameraX
	 *            the isEnabledToMoveCameraX to set
	 */
	public void setEnabledToMoveCameraX(boolean isEnabledToMoveCameraX) {
		this.isEnabledToMoveCameraX = isEnabledToMoveCameraX;
	}

	/**
	 * @return the isEnabledToMoveCameraY
	 */
	public boolean isEnabledToMoveCameraY() {
		return this.isEnabledToMoveCameraY;
	}

	/**
	 * @param isEnabledToMoveCameraY
	 *            the isEnabledToMoveCameraY to set
	 */
	public void setEnabledToMoveCameraY(boolean isEnabledToMoveCameraY) {
		this.isEnabledToMoveCameraY = isEnabledToMoveCameraY;
	}

	/**
	 * @return the enabledInertia
	 */
	public boolean isEnabledInertia() {
		return this.isEnabledInertia;
	}

	/**
	 * @param enabledInertia
	 *            the enabledInertia to set
	 */
	public void setEnabledInertia(boolean enabledInertia) {
		this.isEnabledInertia = enabledInertia;
	}

	/**
	 * @return the decelaration
	 */
	public float getDecelaration() {
		return this.decelaration;
	}

	/**
	 * @param decelaration
	 *            the decelaration to set
	 */
	public void setDecelaration(float decelaration) {
		this.decelaration = decelaration;
	}

	/**
	 * @return movable range of camera
	 */
	public Range getMovableRange() {
		return this.movableRange;
	}

	/**
	 * @author goshi 2015/12/14
	 */
	public static class Range {
		private float leftBorder, rightBorder, bottomBorder, topBorder;

		/**
		 * @param leftBorder
		 * @param rightBorder
		 * @param bottomBorder
		 * @param topBorder
		 */
		public Range(float leftBorder, float rightBorder, float bottomBorder, float topBorder) {
			this.setBorders(leftBorder, rightBorder, bottomBorder, topBorder);
		}

		/**
		 * @param leftBorder
		 * @param rightBorder
		 * @param bottomBorder
		 * @param topBorder
		 */
		public void setBorders(float leftBorder, float rightBorder, float bottomBorder, float topBorder) {
			this.leftBorder = leftBorder;
			this.rightBorder = rightBorder;
			this.bottomBorder = bottomBorder;
			this.topBorder = topBorder;
			this.validate();
		}

		/**
		 * 
		 */
		private void validate() {
			this.validateWidth();
			this.validateHeight();
		}

		private void validateWidth() {
			if (this.getWidth() < 0) {
				throw new RuntimeException("Invalid width is set"); //$NON-NLS-1$
			}
		}

		private void validateHeight() {
			if (this.getHeight() < 0) {
				throw new RuntimeException("Invalid height is set"); //$NON-NLS-1$
			}
		}

		/**
		 * @return the leftBorder
		 */
		public float getLeftBorder() {
			return this.leftBorder;
		}

		/**
		 * @param leftBorder
		 *            the leftBorder to set
		 */
		public void setLeftBorder(float leftBorder) {
			this.leftBorder = leftBorder;
			this.validateWidth();
		}

		/**
		 * @return the rightBorder
		 */
		public float getRightBorder() {
			return this.rightBorder;
		}

		/**
		 * @param rightBorder
		 *            the rightBorder to set
		 */
		public void setRightBorder(float rightBorder) {
			this.rightBorder = rightBorder;
			this.validateWidth();
		}

		/**
		 * @return the bottomBorder
		 */
		public float getBottomBorder() {
			return this.bottomBorder;
		}

		/**
		 * @param bottomBorder
		 *            the bottomBorder to set
		 */
		public void setBottomBorder(float bottomBorder) {
			this.bottomBorder = bottomBorder;
			this.validateHeight();
		}

		/**
		 * @return the topBorder
		 */
		public float getTopBorder() {
			return this.topBorder;
		}

		/**
		 * @param topBorder
		 *            the topBorder to set
		 */
		public void setTopBorder(float topBorder) {
			this.topBorder = topBorder;
			this.validateHeight();
		}

		/**
		 * @return width
		 */
		public float getWidth() {
			return this.rightBorder - this.leftBorder;
		}

		/**
		 * @return height
		 */
		public float getHeight() {
			return this.topBorder - this.bottomBorder;
		}
	}
}

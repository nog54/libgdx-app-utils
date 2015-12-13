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
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author goshi 2015/09/18
 */
public abstract class ActorsActivity extends ApplicationActivity {
	private final Stage stage;
	private Color backgroundColor;

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
		this.setBackgroundColor(Color.WHITE);
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this.stage;
	}

	@Override
	public void render(float delta) {
		this.stage.act(delta);
		Gdx.gl.glClearColor(this.getBackgroundColor().r, this.getBackgroundColor().g, this.getBackgroundColor().b, this.getBackgroundColor().a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.stage.draw();
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
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}

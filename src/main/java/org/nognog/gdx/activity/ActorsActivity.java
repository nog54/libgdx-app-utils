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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * @author goshi 2015/09/18
 */
public abstract class ActorsActivity extends ApplicationActivity {
	private final Stage stage;

	/**
	 * 
	 */
	public ActorsActivity() {
		this.stage = new Stage();
	}

	
	@Override
	public InputProcessor getInputProcessor() {
		return this.stage;
	}
	
	@Override
	public final void render(float delta) {
		this.stage.act(delta);
		Gdx.gl.glClearColor(1, 1, 1, 1);
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
	protected Stage getStage() {
		return this.stage;
	}
}

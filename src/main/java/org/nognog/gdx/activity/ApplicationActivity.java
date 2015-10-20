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

import org.nognog.gdx.ActivityBasedApplication;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * @author goshi 2015/09/18
 */
public abstract class ApplicationActivity implements Screen {
	private ActivityBasedApplication application;
	
	protected void changeActivity(ApplicationActivity activity) {
		this.application.setActivity(activity);
	}

	/**
	 * @param application
	 */
	public void setApplication(ActivityBasedApplication application) {
		this.application = application;
	}

	/**
	 * @return input processor
	 */
	@SuppressWarnings("static-method")
	public InputProcessor getInputProcessor() {
		return null;
	}
}

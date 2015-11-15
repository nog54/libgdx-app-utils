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

package org.nognog.gdx;

import org.nognog.gdx.activity.ApplicationActivity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author goshi 2015/09/18
 */
public abstract class ActivityBasedApplication extends Game {
	private ApplicationActivity activity;

	/**
	 * @return the activity
	 */
	public ApplicationActivity getActivity() {
		return this.activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(ApplicationActivity activity) {
		this.activity = activity;
		this.activity.setApplication(this);
		this.setScreen(activity);
		Gdx.input.setInputProcessor(activity.getInputProcessor());
	}
	
	@Override
	public void setScreen(Screen screen) {
		if(!(screen instanceof ApplicationActivity)){
			throw new RuntimeException("screen must extend ApplicationActivity"); //$NON-NLS-1$
		}
		super.setScreen(screen);
	}
	
	/**
	 * @return skin
	 */
	public abstract Skin getSkin();

}

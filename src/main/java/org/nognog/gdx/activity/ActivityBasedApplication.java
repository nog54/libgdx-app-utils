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

import org.nognog.gdx.activity.transition.Transition;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectSet;

/**
 * @author goshi 2015/09/18
 */
public abstract class ActivityBasedApplication extends Game {
	private ApplicationActivity activity;
	private Transition transition;

	private ObjectSet<ApplicationActivity> usedActivities;

	/**
	 * @return the activity
	 */
	public ApplicationActivity getActivity() {
		return this.activity;
	}

	/**
	 * @param newActivity
	 * @param usedTransition
	 */
	public void performTransition(ApplicationActivity newActivity, Transition usedTransition) {
		if (this.transition != null) {
			return;
		}
		try {
			newActivity.setApplication(this);
			usedTransition.setFromActivity(this.activity);
			usedTransition.setToActivity(newActivity);
			usedTransition.setApplication(this);
			this.transition = usedTransition;
		} catch (Exception e) {
			e.printStackTrace();
			this.setActivity(newActivity);
			return;
		}
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(ApplicationActivity activity) {
		this.activity = activity;
		this.addDispositionTargetActivity(activity);
		this.activity.setApplication(this);
		this.setScreen(activity);
		this.enableInput();
	}

	/**
	 * @param targetActivity
	 */
	public void addDispositionTargetActivity(ApplicationActivity targetActivity) {
		this.usedActivities.add(targetActivity);
	}

	/**
	 * enable input
	 */
	public void enableInput() {
		if (this.activity != null) {
			Gdx.input.setInputProcessor(this.activity.getInputProcessor());
		}
	}

	/**
	 * disable input
	 */
	@SuppressWarnings("static-method")
	public void disableInput() {
		Gdx.input.setInputProcessor(null);
	}

	/**
	 * @return true if enable input
	 */
	@SuppressWarnings("static-method")
	public boolean isEnableInput() {
		return Gdx.input.getInputProcessor() != null;
	}

	@Override
	public void setScreen(Screen screen) {
		if (!(screen instanceof ApplicationActivity)) {
			throw new RuntimeException("screen must extend ApplicationActivity"); //$NON-NLS-1$
		}
		super.setScreen(screen);
	}

	@Override
	public void create() {
		this.usedActivities = new ObjectSet<>();
	}

	@Override
	public void render() {
		if (this.screen != null) {
			final float deltaTime = Gdx.graphics.getDeltaTime();
			if (this.transition != null) {
				final boolean finishedTransition = this.transition.proceed(deltaTime);
				if (finishedTransition) {
					this.transition = null;
				}
			}
			this.screen.render(deltaTime);
		}
	}

	@Override
	public void dispose() {
		synchronized (this.usedActivities) {
			for (ApplicationActivity usedActivity : this.usedActivities) {
				usedActivity.dispose();
			}
		}
	}

	/**
	 * @return skin
	 */
	public abstract Skin getSkin();

	/**
	 * @return configurations
	 */
	public abstract ApplicationConfigurations getConfigurations();

}

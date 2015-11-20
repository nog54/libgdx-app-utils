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

import org.nognog.gdx.activity.ActivityBasedApplication;
import org.nognog.gdx.activity.ApplicationActivity;

/**
 * @author goshi 2015/11/18
 */
public abstract class Transition {
	private ActivityBasedApplication application;
	private ApplicationActivity fromActivity;
	private ApplicationActivity toActivity;

	/**
	 * 
	 */
	public Transition() {
	}
	
	/**
	 * proceed transition
	 * 
	 * @param deltaTime
	 * @return true if it ends
	 */
	public abstract boolean proceed(float deltaTime);

	/**
	 * @return the fromActivity
	 */
	public ApplicationActivity getFromActivity() {
		return this.fromActivity;
	}

	/**
	 * @param fromActivity
	 *            the fromActivity to set
	 */
	public void setFromActivity(ApplicationActivity fromActivity) {
		if (fromActivity == null) {
			throw new NullPointerException();
		}
		if (this.fromActivity != null) {
			throw new RuntimeException("can't set fromActivity if it has already setted"); //$NON-NLS-1$
		}
		this.fromActivity = fromActivity;
	}

	/**
	 * @return the toActivity
	 */
	public ApplicationActivity getToActivity() {
		return this.toActivity;
	}

	/**
	 * @param toActivity
	 *            the toActivity to set
	 */
	public void setToActivity(ApplicationActivity toActivity) {
		if (toActivity == null) {
			throw new NullPointerException();
		}
		if (this.toActivity != null) {
			throw new RuntimeException("can't set toActivity if it has already setted"); //$NON-NLS-1$
		}
		this.toActivity = toActivity;
	}

	/**
	 * @return the application
	 */
	public ActivityBasedApplication getApplication() {
		return this.application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(ActivityBasedApplication application) {
		if (application == null) {
			throw new NullPointerException();
		}
		if (this.application != null) {
			throw new RuntimeException("can't set application if it has already setted"); //$NON-NLS-1$
		}
		this.application = application;
	}
}

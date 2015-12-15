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

package org.nognog.gdx.ui.button;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

/**
 * @author goshi 2015/09/18
 */
public abstract class Button extends WidgetGroup {

	private ButtonListener listener;
	private boolean enabled;

	/**
	 * constract new instance without listener
	 */
	public Button() {
		this(null);
	}

	/**
	 * constract new instance with listener
	 * 
	 * @param listener
	 */
	public Button(ButtonListener listener) {
		this.listener = listener;
		this.enabled = true;
	}

	/**
	 * @return the listener
	 */
	public ButtonListener getListener() {
		return this.listener;
	}

	/**
	 * press this button
	 */
	public void press() {
		if (this.listener != null) {
			this.listener.click();
		}
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(ButtonListener listener) {
		this.listener = listener;
	}

	/**
	 * @return true if it is enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @return true if it is disabled
	 */
	public boolean isDisabled() {
		return !this.enabled;
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		if (this.enabled == enabled) {
			return;
		}
		this.enabled = enabled;
		if (this.enabled) {
			this.enableMyself();
		} else {
			this.disableMyself();
		}
	}

	/**
	 * Enable myself. Maybe this or {@link #disableMyself()} should be called in
	 * the constructor
	 */
	protected abstract void enableMyself();

	/**
	 * Disable myself. Maybe this or {@link #disableMyself()} should be called
	 * in the constructor
	 */
	protected abstract void disableMyself();
}

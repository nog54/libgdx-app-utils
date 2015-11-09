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

package org.nognog.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author goshi 2015/09/18
 */
public abstract class Switch extends Group {

	private boolean on;
	private SwitchListener listener;

	/**
	 * constract new instance without listener
	 */
	public Switch() {
		this(null);
	}
	
	/**
	 * constract new instance without listener
	 * @param on 
	 */
	public Switch(boolean on) {
		this(on, null);
	}

	/**
	 * constract new instance with listener
	 * 
	 * @param listener
	 */
	public Switch(SwitchListener listener) {
		this(false, listener);
	}

	/**
	 * constract new instance with listener
	 * 
	 * @param on 
	 * @param listener
	 */
	public Switch(boolean on, SwitchListener listener) {
		this.on = on;
		this.listener = listener;
	}

	/**
	 * turn on switch
	 */
	public synchronized void on() {
		if (this.isOn()) {
			return;
		}
		if (this.listener != null) {
			this.listener.onFromOff();
		}
		this.turnOn();
	}

	private void turnOn() {
		this.on = true;
	}

	/**
	 * turn off switch
	 */
	public synchronized void off() {
		if (this.isOff()) {
			return;
		}
		if (this.listener != null) {
			this.listener.offFromOn();
		}
		this.turnOff();
	}

	private void turnOff() {
		this.on = false;
	}

	/**
	 * @return true if switch is "on"
	 */
	public boolean isOn() {
		return this.on;
	}

	/**
	 * @return true if switch is "off"
	 */
	public boolean isOff() {
		return !this.isOn();
	}

	/**
	 * @return the listener
	 */
	public SwitchListener getListener() {
		return this.listener;
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(SwitchListener listener) {
		this.listener = listener;
	}
}

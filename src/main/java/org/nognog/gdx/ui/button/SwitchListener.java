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

/**
 * @author goshi 2015/09/17
 */
public interface SwitchListener {
	/**
	 * be executed when switch is changed to "on" from "off"
	 * 
	 * @return whether make the switch turn off
	 */
	public boolean onFromOff();

	/**
	 * be executed when switch is changed to "off" from "on"
	 * 
	 * @return whether make the switch turn on
	 */
	public boolean offFromOn();
}

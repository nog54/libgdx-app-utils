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

import java.util.Map;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author goshi 2015/12/07
 */
public class ApplicationConfigurations {
	private final ObjectMap<String, Object> configurations;

	/**
	 * 
	 */
	public ApplicationConfigurations() {
		this.configurations = new ObjectMap<>();
	}

	/**
	 * @param klass
	 * @param name
	 * @return configuration
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> klass, String name) {
		try {
			return (T) this.configurations.get(name);
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * @param name
	 * @param object
	 */
	public void put(String name, Object object) {
		this.configurations.put(name, object);
	}

	/**
	 * @param pref
	 * @param overwrite
	 */
	public void readPreferences(Preferences pref, boolean overwrite) {
		final Map<String, ?> map = pref.get();
		for (String key : map.keySet()) {
			if (overwrite == false && this.configurations.containsKey(key)) {
				continue;
			}
			this.configurations.put(key, map.get(key));
		}
	}

	/**
	 * @return map
	 */
	public ObjectMap<String, Object> getMap() {
		return this.configurations;
	}
}

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

package org.nognog.gdx.jmatcher.requester;

/**
 * @author goshi 2015/12/10
 * @param <T>
 *            type of main returned object
 */
public interface ResponseListener<T> {
	
	/**
	 * It's called when succeed in connecting the server
	 * @param object 
	 * 
	 */
	public void success(T object);

	/**
	 * It's called when fail to connect the server
	 * 
	 */
	public void failure();
}
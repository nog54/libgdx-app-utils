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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * @author goshi 2015/09/18
 */
public class SimpleSwitch extends Switch {

	private final Image onImage;
	private final Image offImage;

	private static final Texture onTexture = createSimpleTexture(new Color(0.2f, 1f, 0.2f, 1));
	private static final Texture offTexture = createSimpleTexture(new Color(0.6f, 1f, 0.6f, 1));

	private static final Texture createSimpleTexture(Color color) {
		final Pixmap texturePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		texturePixmap.setColor(color);
		texturePixmap.drawPixel(0, 0);

		final Texture result = new Texture(texturePixmap);
		texturePixmap.dispose();
		return result;
	}

	/**
	 * 
	 */
	public SimpleSwitch() {
		this(true, null);
	}

	/**
	 * @param on
	 * @param listener
	 */
	public SimpleSwitch(boolean on, SwitchListener listener) {
		super(on, listener);
		this.onImage = new Image(onTexture);
		this.onImage.addListener(new ActorGestureListener() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				SimpleSwitch.this.removeActor(SimpleSwitch.this.onImage);
				SimpleSwitch.this.addActor(SimpleSwitch.this.offImage);
				SimpleSwitch.this.off();
			}
		});
		final int switchSize = 100;
		this.onImage.setSize(switchSize, switchSize);
		this.offImage = new Image(offTexture);
		this.offImage.addListener(new ActorGestureListener() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				SimpleSwitch.this.removeActor(SimpleSwitch.this.offImage);
				SimpleSwitch.this.addActor(SimpleSwitch.this.onImage);
				SimpleSwitch.this.on();
			}
		});
		this.offImage.setSize(switchSize, switchSize);
		if (this.isOn()) {
			this.addActor(this.onImage);
		} else {
			this.addActor(this.offImage);
		}
	}

}

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

	private static final Texture defaultOnTexture = createSimpleTexture(new Color(0.2f, 1f, 0.2f, 1));
	private static final Texture defaultOffTexture = createSimpleTexture(new Color(0.6f, 1f, 0.6f, 1));

	private static final Texture createSimpleTexture(Color color) {
		final Pixmap texturePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		texturePixmap.setColor(color);
		texturePixmap.drawPixel(0, 0);

		final Texture result = new Texture(texturePixmap);
		texturePixmap.dispose();
		return result;
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 */
	public SimpleSwitch(boolean initValue, int width, int height) {
		this(initValue, width, height, null);
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param listener
	 */
	public SimpleSwitch(boolean initValue, int width, int height, SwitchListener listener) {
		this(initValue, 0, 0, listener, defaultOnTexture, defaultOffTexture);
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param listener
	 * @param onTextureColor
	 * @param offTextureColor
	 */
	public SimpleSwitch(boolean initValue, int width, int height, SwitchListener listener, Color onTextureColor, Color offTextureColor) {
		this(initValue, width, height, listener, createSimpleTexture(onTextureColor), createSimpleTexture(offTextureColor));
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param listener
	 * @param onTexture
	 * @param offTexture
	 */
	public SimpleSwitch(boolean initValue, int width, int height, SwitchListener listener, Texture onTexture, Texture offTexture) {
		super(initValue, listener);
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
		this.onImage.setSize(width, height);
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
		this.offImage.setSize(width, height);
		if (this.isOn()) {
			this.addActor(this.onImage);
		} else {
			this.addActor(this.offImage);
		}
	}

	@Override
	public void setWidth(int width) {
		this.onImage.setWidth(width);
		this.offImage.setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		this.onImage.setHeight(height);
		this.offImage.setHeight(height);
	}

}

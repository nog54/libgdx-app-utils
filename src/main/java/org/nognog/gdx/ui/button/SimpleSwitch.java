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

import org.nognog.gdx.ui.UiUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;

/**
 * @author goshi 2015/09/18
 */
public class SimpleSwitch extends Switch {

	private Group onActors;
	private Group offActors;
	private final Image onImage;
	private final Image offImage;
	private Label onLabel;
	private Label offLabel;

	private static final Texture defaultOnTexture = UiUtils.createSimpleTexture(new Color(0.2f, 1f, 0.2f, 1));
	private static final Texture defaultOffTexture = UiUtils.createSimpleTexture(new Color(0.6f, 1f, 0.6f, 1));

	/**
	 * @param initValue
	 */
	public SimpleSwitch(boolean initValue) {
		this(initValue, 0, 0);
	}

	/**
	 * @param initValue
	 * @param font
	 */
	public SimpleSwitch(boolean initValue, BitmapFont font) {
		this(initValue, 0, 0, font);
	}

	/**
	 * @param initValue
	 * @param font
	 * @param upTextureColor
	 * @param downTextureColor
	 */
	public SimpleSwitch(boolean initValue, BitmapFont font, Color upTextureColor, Color downTextureColor) {
		this(initValue, 0, 0, font, UiUtils.createSimpleTexture(upTextureColor), UiUtils.createSimpleTexture(downTextureColor));
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 */
	public SimpleSwitch(boolean initValue, float width, float height) {
		this(initValue, width, height, new BitmapFont());
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param font
	 */
	public SimpleSwitch(boolean initValue, float width, float height, BitmapFont font) {
		this(initValue, width, height, font, defaultOnTexture, defaultOffTexture);
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param font
	 * @param onTextureColor
	 * @param offTextureColor
	 */
	public SimpleSwitch(boolean initValue, float width, float height, BitmapFont font, Color onTextureColor, Color offTextureColor) {
		this(initValue, width, height, font, UiUtils.createSimpleTexture(onTextureColor), UiUtils.createSimpleTexture(offTextureColor));
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param skin
	 */
	public SimpleSwitch(boolean initValue, float width, float height, Skin skin) {
		this(initValue, width, height, skin, "default", "onTexture", "offTexture"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param skin
	 * @param fontName
	 * @param onTextureName
	 * @param offTextureName
	 */
	public SimpleSwitch(boolean initValue, float width, float height, Skin skin, String fontName, String onTextureName, String offTextureName) {
		this(initValue, width, height, skin.get(fontName, BitmapFont.class), skin.get(onTextureName, Texture.class), skin.get(offTextureName, Texture.class));
	}

	/**
	 * @param initValue
	 * @param width
	 * @param height
	 * @param font
	 * @param onTexture
	 * @param offTexture
	 */
	public SimpleSwitch(boolean initValue, float width, float height, BitmapFont font, Texture onTexture, Texture offTexture) {
		super(initValue);
		this.onImage = new Image(onTexture);
		this.onImage.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SimpleSwitch.this.showOffActors();
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				final Actor touchingActor = SimpleSwitch.this.hit(x, y, true);
				if (touchingActor == SimpleSwitch.this.getOffImage() || touchingActor == SimpleSwitch.this.getOnImage()) {
					SimpleSwitch.this.showOffActors();
				} else {
					SimpleSwitch.this.showOnActors();
				}
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (SimpleSwitch.this.hit(x, y, true) == SimpleSwitch.this.getOffImage()) {
					SimpleSwitch.this.off();
				} else {
					SimpleSwitch.this.showOnActors();
				}

			}
		});
		this.offImage = new Image(offTexture);
		this.offImage.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (SimpleSwitch.this.hit(x, y, true) == SimpleSwitch.this.getOffImage()) {
					SimpleSwitch.this.on();
				}

			}
		});
		this.onLabel = new Label("on", new LabelStyle(font, Color.WHITE)); //$NON-NLS-1$
		this.onLabel.setTouchable(Touchable.disabled);
		this.onLabel.setAlignment(Align.center);
		this.onLabel.setVisible(width != 0 && height != 0);
		this.offLabel = new Label("off", new LabelStyle(font, Color.WHITE)); //$NON-NLS-1$
		this.offLabel.setTouchable(Touchable.disabled);
		this.offLabel.setAlignment(Align.center);
		this.offLabel.setVisible(width != 0 && height != 0);
		this.setSize(width, height);
		
		this.onActors = new Group();
		this.onActors.addActor(this.onImage);
		this.onActors.addActor(this.onLabel);
		this.addActor(this.onActors);
		
		this.offActors = new Group();
		this.offActors.addActor(this.offImage);
		this.offActors.addActor(this.offLabel);
		this.addActor(this.offActors);
		if (this.isOn()) {
			this.showOnActors();
		} else {
			this.showOffActors();
		}
	}

	@Override
	protected void afterOn() {
		this.showOnActors();
	}

	protected void showOnActors() {
		this.offActors.setVisible(false);
		this.onActors.setVisible(true);
	}

	@Override
	protected void afterOff() {
		this.showOffActors();
	}

	protected void showOffActors() {
		this.onActors.setVisible(false);
		this.offActors.setVisible(true);
	}

	@Override
	protected void sizeChanged() {
		this.onImage.setSize(this.getWidth(), this.getHeight());
		this.offImage.setSize(this.getWidth(), this.getHeight());
		this.onLabel.setPosition(this.getWidth() / 2, this.getHeight() / 2, Align.center);
		this.offLabel.setPosition(this.getWidth() / 2, this.getHeight() / 2, Align.center);
		this.onLabel.setVisible(this.getWidth() != 0 && this.getHeight() != 0);
		this.offLabel.setVisible(this.getWidth() != 0 && this.getHeight() != 0);
	}

	@Override
	public float getMinWidth() {
		return this.getPrefWidth();
	}

	@Override
	public float getMinHeight() {
		return this.getPrefHeight();
	}

	@Override
	public float getMaxWidth() {
		return this.getPrefWidth();
	}

	@Override
	public float getMaxHeight() {
		return this.getPrefHeight();
	}

	@Override
	public float getPrefWidth() {
		return this.getWidth();
	}

	/**
	 * @return the onLabel
	 */
	public String getOnText() {
		return this.onLabel.getText().toString();
	}

	/**
	 * @param newText
	 *            the newText to set
	 */
	public void setOnText(String newText) {
		this.onLabel.setText(newText);
	}

	/**
	 * @return the offLabel
	 */
	public String getOffText() {
		return this.offLabel.getText().toString();
	}

	/**
	 * @param newText
	 *            the newText to set
	 */
	public void setOffText(String newText) {
		this.offLabel.setText(newText);
	}

	/**
	 * @param newScale
	 */
	public void setLabelScale(float newScale) {
		this.onLabel.setFontScale(newScale);
		this.offLabel.setFontScale(newScale);
	}

	/**
	 * @param newScale
	 */
	public void setOnLabelScale(float newScale) {
		this.onLabel.setFontScale(newScale);
	}

	/**
	 * @param newScale
	 */
	public void setOffLabelScale(float newScale) {
		this.offLabel.setFontScale(newScale);
	}

	/**
	 * @return the scale of onLabel
	 */
	public float getOnLabelScale() {
		return this.onLabel.getFontScaleX();
	}

	/**
	 * @return the scale of offLabel
	 */
	public float getOffLabelScale() {
		return this.offLabel.getFontScaleX();
	}

	/**
	 * @param color
	 */
	public void setOnLabelColor(Color color) {
		this.onLabel.setColor(color);
	}

	/**
	 * @return a color of the onLabel
	 */
	public Color getOnLabelColor() {
		return this.onLabel.getColor();
	}

	/**
	 * @param color
	 */
	public void setOffLabelColor(Color color) {
		this.offLabel.setColor(color);
	}

	/**
	 * @return a color of the offLabel
	 */
	public Color getOffLabelColor() {
		return this.offLabel.getColor();
	}

	protected Image getOnImage() {
		return this.onImage;
	}

	protected Image getOffImage() {
		return this.offImage;
	}

}

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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
public class SimpleButton extends Button {

	private final Image upImage;
	private final Image downImage;
	private Label label;

	private static final Texture defaultUpTexture = createSimpleTexture(new Color(0.2f, 0.2f, 1, 1));
	private static final Texture defaultDownTexture = createSimpleTexture(new Color(0.6f, 0.6f, 1, 1));

	private static final Texture createSimpleTexture(Color color) {
		final Pixmap texturePixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		texturePixmap.setColor(color);
		texturePixmap.drawPixel(0, 0);

		final Texture result = new Texture(texturePixmap);
		texturePixmap.dispose();
		return result;
	}

	/**
	 * @param width
	 * @param height
	 */
	public SimpleButton(float width, float height) {
		this(width, height, new BitmapFont());
	}

	/**
	 * @param width
	 * @param height
	 * @param font
	 */
	public SimpleButton(float width, float height, BitmapFont font) {
		this(width, height, font, defaultDownTexture, defaultUpTexture);
	}

	/**
	 * @param width
	 * @param height
	 * @param font
	 * @param upTextureColor
	 * @param downTextureColor
	 */
	public SimpleButton(float width, float height, BitmapFont font, Color upTextureColor, Color downTextureColor) {
		this(width, height, font, createSimpleTexture(upTextureColor), createSimpleTexture(downTextureColor));
	}

	/**
	 * @param width
	 * @param height
	 * @param skin
	 * @param fontName
	 * @param upTextureName
	 * @param downTextureName
	 */
	public SimpleButton(float width, float height, Skin skin) {
		this(width, height, skin, "default", "upTexture", "downTexture"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @param width
	 * @param height
	 * @param skin
	 * @param fontName
	 * @param upTextureName
	 * @param downTextureName
	 */
	public SimpleButton(float width, float height, Skin skin, String fontName, String upTextureName, String downTextureName) {
		this(width, height, skin.get(fontName, BitmapFont.class), skin.get(upTextureName, Texture.class), skin.get(downTextureName, Texture.class));
	}

	/**
	 * @param width
	 * @param height
	 * @param font
	 * @param upTexture
	 * @param downTexture
	 */
	public SimpleButton(float width, float height, BitmapFont font, Texture upTexture, Texture downTexture) {
		this.upImage = new Image(upTexture);
		this.upImage.addListener(new ActorGestureListener() {

			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SimpleButton.this.showDownImage();
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				final Actor touchingActor = SimpleButton.this.hit(x, y, true);
				if (touchingActor == SimpleButton.this.getDownImage() || touchingActor == SimpleButton.this.getUpImage()) {
					SimpleButton.this.showDownImage();
				} else {
					SimpleButton.this.showUpImage();
				}
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (SimpleButton.this.hit(x, y, true) == SimpleButton.this.getDownImage()) {
					SimpleButton.this.press();
				}
				SimpleButton.this.showUpImage();
			}
		});
		this.downImage = new Image(downTexture);
		this.label = new Label("button", new LabelStyle(font, Color.WHITE)); //$NON-NLS-1$
		this.label.setTouchable(Touchable.disabled);
		this.label.setAlignment(Align.center);
		this.setSize(width, height);
		this.addActor(this.upImage);
		this.addActor(this.downImage);
		this.addActor(this.label);
		this.showUpImage();
	}

	protected void showUpImage() {
		this.downImage.setVisible(false);
		this.upImage.setVisible(true);
	}

	protected void showDownImage() {
		this.upImage.setVisible(false);
		this.downImage.setVisible(true);
	}

	@Override
	protected void sizeChanged() {
		this.upImage.setSize(this.getWidth(), this.getHeight());
		this.downImage.setSize(this.getWidth(), this.getHeight());
		this.label.setPosition(this.getWidth() / 2, this.getHeight() / 2, Align.center);
	}

	/**
	 * @return a text of the label
	 */
	public String getText() {
		return this.label.getText().toString();
	}

	/**
	 * @param newText
	 *            the newText to set
	 */
	public void setText(String newText) {
		this.label.setText(newText);
	}

	/**
	 * @param newScale
	 */
	public void setLabelScale(float newScale) {
		this.label.setFontScale(newScale);
	}

	/**
	 * @return the scale of the label
	 */
	public float getLabelScale() {
		return this.label.getFontScaleX();
	}

	/**
	 * @param color
	 */
	public void setLabelColor(Color color) {
		this.label.setColor(color);
	}

	/**
	 * @return a color of the label
	 */
	public Color getLabelColor() {
		return this.label.getColor();
	}

	protected Image getUpImage() {
		return this.upImage;
	}

	protected Image getDownImage() {
		return this.downImage;
	}

}

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

package org.nognog.gdx.ui.dialog;

import org.nognog.gdx.ui.ColorUtils;
import org.nognog.gdx.ui.Constants;
import org.nognog.gdx.ui.UiUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author goshi 2015/04/24
 */
public class SimpleOneButtonDialog extends SimpleDialog {
	private Label textLabel;
	private TextButton button;
	private SimpleOneButtonDialogListener listener;

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param buttonText
	 * @param font
	 */
	public SimpleOneButtonDialog(float width, float height, String text, String buttonText, BitmapFont font) {
		this(width, height, text, buttonText, font, createLabelStyle(font), createButtonStyle(font));
	}

	protected static TextButtonStyle createButtonStyle(BitmapFont font) {
		final TextureRegionDrawable up = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.softClearPeterRiver);
		final TextureRegionDrawable down = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.softClearBelizeHole);
		final TextureRegionDrawable checked = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.softClearPeterRiver);
		return new TextButtonStyle(up, down, checked, font);
	}

	protected static LabelStyle createLabelStyle(BitmapFont font) {
		return new LabelStyle(font, new Color(ColorUtils.carrot));
	}

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param buttonText
	 * @param skin
	 */
	public SimpleOneButtonDialog(float width, float height, String text, String buttonText, Skin skin) {
		this(width, height, text, buttonText, skin.get(BitmapFont.class), skin.get(LabelStyle.class), skin.get(TextButtonStyle.class));
	}

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param buttonText
	 * @param skin
	 * @param labelStyleName
	 * @param textButtonStyleName
	 */
	public SimpleOneButtonDialog(float width, float height, String text, String buttonText, Skin skin, String labelStyleName, String textButtonStyleName) {
		this(width, height, text, buttonText, skin.get(BitmapFont.class), skin.get(labelStyleName, LabelStyle.class), skin.get(textButtonStyleName, TextButtonStyle.class));
	}

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param buttonText
	 * @param textFont
	 * @param labelStyle
	 * @param buttonStyle
	 */
	public SimpleOneButtonDialog(float width, float height, String text, String buttonText, BitmapFont textFont, LabelStyle labelStyle, TextButtonStyle buttonStyle) {
		super(width, height);
		this.textLabel = new Label(text, labelStyle);
		this.textLabel.setWidth(width);
		this.textLabel.setWrap(true);
		final float goldenRatio = Constants.getGoldenRatio();
		final float wholeSpace = width / (3 + 2 * goldenRatio) / 2;
		final Table table = this.getTable();
		table.pad(wholeSpace);
		table.add(this.textLabel).left().fillX().row();
		this.button = new TextButton(buttonText, buttonStyle);
		this.button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (SimpleOneButtonDialog.this.getListener() != null) {
					SimpleOneButtonDialog.this.getListener().clickButton();
				}
			}
		});
		table.add(this.button).expandX().fillX().expandY();
	}

	/**
	 * @return listener
	 */
	public SimpleOneButtonDialogListener getListener() {
		return this.listener;
	}

	/**
	 * @param listener
	 */
	public void setListener(SimpleOneButtonDialogListener listener) {
		this.listener = listener;
	}

	/**
	 * @return button text
	 */
	public CharSequence getButtonText() {
		return this.button.getText();
	}

	/**
	 * @param text
	 */
	public void setButtonText(String text) {
		this.button.setText(text);
	}

	/**
	 * @param text
	 */
	public void setText(String text) {
		this.textLabel.setText(text);
	}

	/**
	 * @author goshi 2015/05/05
	 */
	public static abstract class SimpleOneButtonDialogListener {
		/**
		 * called when the button is clicked
		 */
		public abstract void clickButton();
	}
}

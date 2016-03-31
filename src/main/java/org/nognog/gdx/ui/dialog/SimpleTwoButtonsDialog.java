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
public class SimpleTwoButtonsDialog extends SimpleDialog {
	private Label textLabel;
	private TextButton leftButton;
	private TextButton rightButton;
	private SimpleTwoButtonsDialogListener listener;

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param font
	 */
	public SimpleTwoButtonsDialog(float width, float height, String text, String leftButtonText, String rightButtonText, BitmapFont font) {
		this(width, height, text, leftButtonText, rightButtonText, font, createLabelStyle(font), createButtonStyle(font), createButtonStyle(font));
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
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param skin
	 */
	public SimpleTwoButtonsDialog(float width, float height, String text, String leftButtonText, String rightButtonText, Skin skin) {
		this(width, height, text, leftButtonText, rightButtonText, skin.get(BitmapFont.class), skin.get(LabelStyle.class), skin.get(TextButtonStyle.class), skin.get(TextButtonStyle.class));
	}

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param skin
	 * @param labelStyleName
	 * @param textButtonStyleName
	 */
	public SimpleTwoButtonsDialog(float width, float height, String text, String leftButtonText, String rightButtonText, Skin skin, String labelStyleName, String textButtonStyleName) {
		this(width, height, text, leftButtonText, rightButtonText, skin.get(BitmapFont.class), skin.get(labelStyleName, LabelStyle.class), skin.get(textButtonStyleName, TextButtonStyle.class),
				skin.get(textButtonStyleName, TextButtonStyle.class));
	}

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param textFont
	 * @param labelStyle
	 * @param leftButtonStyle
	 * @param rightButtonStyle
	 */
	public SimpleTwoButtonsDialog(float width, float height, String text, String leftButtonText, String rightButtonText, BitmapFont textFont, LabelStyle labelStyle, TextButtonStyle leftButtonStyle,
			TextButtonStyle rightButtonStyle) {
		this.textLabel = new Label(text, labelStyle);
		this.textLabel.setWrap(true);
		final Table table = this.getTable();
		table.add(this.textLabel).left().fillX().colspan(2).row();
		this.leftButton = new TextButton(leftButtonText, leftButtonStyle);
		this.rightButton = new TextButton(rightButtonText, rightButtonStyle);
		this.leftButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (SimpleTwoButtonsDialog.this.getListener() != null) {
					SimpleTwoButtonsDialog.this.getListener().clickLeftButton();
				}
			}
		});
		this.rightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (SimpleTwoButtonsDialog.this.getListener() != null) {
					SimpleTwoButtonsDialog.this.getListener().clickRightButton();
				}
			}
		});
		
		table.add(this.leftButton).fillX().expand().uniform();
		table.add(this.rightButton).fillX().expand().uniform();
		this.setSize(width, height);
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		if(this.leftButton != null && this.rightButton != null){
			final float widgetsSpan = this.getWidth() / (3 + 2 * Constants.getGoldenRatio());
			this.getTable().getCell(this.leftButton).padRight(widgetsSpan / 2);
			this.getTable().getCell(this.rightButton).padLeft(widgetsSpan / 2);
		}
	}
	
	/**
	 * @return listener
	 */
	public SimpleTwoButtonsDialogListener getListener() {
		return this.listener;
	}

	/**
	 * @param listener
	 */
	public void setListener(SimpleTwoButtonsDialogListener listener) {
		this.listener = listener;
	}

	/**
	 * @return left button text
	 */
	public CharSequence getLeftButtonText() {
		return this.leftButton.getText();
	}

	/**
	 * @param text
	 */
	public void setLeftButtonText(String text) {
		this.leftButton.setText(text);
	}

	/**
	 * @return right button text
	 */
	public CharSequence getRightButtonText() {
		return this.rightButton.getText();
	}

	/**
	 * @param text
	 */
	public void setRightButtonText(String text) {
		this.rightButton.setText(text);
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
	public static abstract class SimpleTwoButtonsDialogListener {
		/**
		 * called when left button is clicked
		 */
		public abstract void clickLeftButton();

		/**
		 * called when right button is clicked
		 */
		public abstract void clickRightButton();
	}
}

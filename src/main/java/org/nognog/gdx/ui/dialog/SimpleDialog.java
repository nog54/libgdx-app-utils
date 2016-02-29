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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author goshi 2015/04/24
 */
public class SimpleDialog extends Group {
	private Label textLabel;
	private TextButton leftButton;
	private TextButton rightButton;
	private SimpleDialogListener listener;
	private Table table;

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param font
	 */
	public SimpleDialog(float width, float height, String text, String leftButtonText, String rightButtonText, BitmapFont font) {
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
	public SimpleDialog(float width, float height, String text, String leftButtonText, String rightButtonText, Skin skin) {
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
	public SimpleDialog(float width, float height, String text, String leftButtonText, String rightButtonText, Skin skin, String labelStyleName, String textButtonStyleName) {
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
	public SimpleDialog(float width, float height, String text, String leftButtonText, String rightButtonText, BitmapFont textFont, LabelStyle labelStyle, TextButtonStyle leftButtonStyle,
			TextButtonStyle rightButtonStyle) {
		this.setWidth(width);
		this.setHeight(height);
		this.table = new Table();
		this.textLabel = new Label(text, labelStyle);
		this.textLabel.setWidth(width);
		this.textLabel.setWrap(true);
		final float goldenRatio = Constants.getGoldenRatio();
		final float wholeSpace = width / (3 + 2 * goldenRatio) / 2;
		this.table.pad(wholeSpace);
		this.table.add(this.textLabel).left().fillX().colspan(2).row();
		this.leftButton = new TextButton(leftButtonText, leftButtonStyle);
		this.rightButton = new TextButton(rightButtonText, rightButtonStyle);
		this.leftButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (SimpleDialog.this.getListener() != null) {
					SimpleDialog.this.getListener().leftButtonClicked();
					SimpleDialog.this.getListener().beforeClickedHandle();
				}
			}
		});
		this.rightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (SimpleDialog.this.getListener() != null) {
					SimpleDialog.this.getListener().rightButtonClicked();
					SimpleDialog.this.getListener().beforeClickedHandle();
				}
			}
		});
		final float widgetsSpan = wholeSpace * 2;
		this.table.add(this.leftButton).expandX().fillX().expandY().uniform().padRight(widgetsSpan / 2);
		this.table.add(this.rightButton).expandX().fillX().expandY().uniform().padLeft(widgetsSpan / 2);
		this.table.setBackground(UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.clearBlack));
		this.table.setWidth(width);
		this.table.setHeight(height);
		this.addActor(this.table);
	}

	/**
	 * @return listener
	 */
	public SimpleDialogListener getListener() {
		return this.listener;
	}

	/**
	 * @param listener
	 */
	public void setListener(SimpleDialogListener listener) {
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
	 * @param background
	 */
	public void setBackground(Drawable background) {
		this.table.setBackground(background);
	}

	/**
	 * @return the background
	 */
	public Drawable getBackground() {
		return this.table.getBackground();
	}

	/**
	 * @author goshi 2015/05/05
	 */
	public static abstract class SimpleDialogListener {

		/**
		 * called when before {@link SimpleDialogListener#leftButtonClicked()}
		 * or {@link SimpleDialogListener#rightButtonClicked()}
		 */
		public void beforeClickedHandle() {
			// nothing in default
		}

		/**
		 * called when after {@link SimpleDialogListener#leftButtonClicked()} or
		 * {@link SimpleDialogListener#rightButtonClicked()}
		 */
		public void afterClickedHandle() {
			// nothing in default
		}

		/**
		 * called when left button clicked
		 */
		public abstract void leftButtonClicked();

		/**
		 * called when right button clicked
		 */
		public abstract void rightButtonClicked();
	}
}

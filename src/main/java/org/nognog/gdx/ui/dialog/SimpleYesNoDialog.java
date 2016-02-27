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

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * @author goshi 2015/04/24
 */
public abstract class SimpleYesNoDialog extends SimpleDialog {

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param font
	 */
	public SimpleYesNoDialog(float width, float height, String text, BitmapFont font) {
		this(width, height, text, font, createLabelStyle(font), createButtonStyle(font), createButtonStyle(font));
	}

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param skin
	 */
	public SimpleYesNoDialog(float width, float height, String text, Skin skin) {
		this(width, height, text, skin.get(BitmapFont.class), skin.get(LabelStyle.class), skin.get(TextButtonStyle.class), skin.get(TextButtonStyle.class));
	}

	/**
	 * @param width
	 * @param height
	 * @param text
	 * @param textFont
	 * @param labelStyle
	 * @param leftButtonStyle
	 * @param rightButtonStyle
	 */
	public SimpleYesNoDialog(float width, float height, String text, BitmapFont textFont, LabelStyle labelStyle, TextButtonStyle leftButtonStyle, TextButtonStyle rightButtonStyle) {
		super(width, height, text, "Yes", "No", textFont, labelStyle, leftButtonStyle, rightButtonStyle); //$NON-NLS-1$ //$NON-NLS-2$
		this.setListener(new SimpleDialog.SimpleDialogListener() {
			@Override
			public void rightButtonClicked() {
				SimpleYesNoDialog.this.no();
			}

			@Override
			public void leftButtonClicked() {
				SimpleYesNoDialog.this.yes();
			}
		});
	}

	/**
	 * called when yes clicked
	 */
	protected abstract void yes();

	/**
	 * called when no clicked
	 */
	protected abstract void no();

}

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

import org.nognog.gdx.camera.CameraObserver;
import org.nognog.gdx.camera.ObservableCamera;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * You should add the instance of this class via
 * {@link ObservableCamera#addCameraObserverAndUpdateIfAdded(CameraObserver)} so
 * that the instance will be shown properly.
 * 
 * @author goshi 2015/05/05
 */
public abstract class CameraFitSimpleYesNoDialog extends CameraFitSimpleDialog {

	/**
	 * @param camera
	 * @param text
	 * @param font
	 */
	public CameraFitSimpleYesNoDialog(String text, BitmapFont font) {
		this(text, font, createLabelStyle(font), createButtonStyle(font), createButtonStyle(font));
	}

	/**
	 * @param camera
	 * @param text
	 * @param skin
	 */
	public CameraFitSimpleYesNoDialog(String text, Skin skin) {
		this(text, skin.get(BitmapFont.class), skin.get(LabelStyle.class), skin.get(TextButtonStyle.class), skin.get(TextButtonStyle.class));
	}

	/**
	 * @param camera
	 * @param text
	 * @param font
	 * @param labelStyle
	 * @param leftButtonStyle
	 * @param rightButtonStyle
	 */
	public CameraFitSimpleYesNoDialog(String text, BitmapFont font, LabelStyle labelStyle, TextButtonStyle leftButtonStyle, TextButtonStyle rightButtonStyle) {
		super(text, "Yes", "No", font, labelStyle, leftButtonStyle, rightButtonStyle); //$NON-NLS-1$ //$NON-NLS-2$
		this.setListener(new SimpleDialogListener() {
			@Override
			public void rightButtonClicked() {
				CameraFitSimpleYesNoDialog.this.no();
			}

			@Override
			public void leftButtonClicked() {
				CameraFitSimpleYesNoDialog.this.yes();
			}
		});
	}

	protected abstract void yes();

	protected abstract void no();

}

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
import org.nognog.gdx.camera.ICamera;
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
public class CameraFitSimpleTwoButtonDialog extends SimpleTwoButtonsDialog implements CameraObserver {

	/**
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param font
	 */
	public CameraFitSimpleTwoButtonDialog(String text, String leftButtonText, String rightButtonText, BitmapFont font) {
		this(text, leftButtonText, rightButtonText, font, createLabelStyle(font), createButtonStyle(font), createButtonStyle(font));
	}

	/**
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param font
	 * @param labelStyle
	 * @param leftButtonStyle
	 * @param rightButtonStyle
	 */
	public CameraFitSimpleTwoButtonDialog(String text, String leftButtonText, String rightButtonText, BitmapFont font, LabelStyle labelStyle, TextButtonStyle leftButtonStyle,
			TextButtonStyle rightButtonStyle) {
		super(0, 0, text, leftButtonText, rightButtonText, font, labelStyle, leftButtonStyle, rightButtonStyle);
	}

	/**
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param skin
	 */
	public CameraFitSimpleTwoButtonDialog(String text, String leftButtonText, String rightButtonText, Skin skin) {
		super(0, 0, text, leftButtonText, rightButtonText, skin);
	}
	
	/**
	 * @param text
	 * @param leftButtonText
	 * @param rightButtonText
	 * @param skin
	 * @param labelStyleName 
	 * @param textButtonStyleName 
	 */
	public CameraFitSimpleTwoButtonDialog(String text, String leftButtonText, String rightButtonText, Skin skin, String labelStyleName, String textButtonStyleName) {
		super(0, 0, text, leftButtonText, rightButtonText, skin, labelStyleName, textButtonStyleName);
	}

	@Override
	public void updateCamera(ICamera camera) {
		this.setSize(camera.getViewportWidth(), camera.getViewportHeight());
		final float currentCameraZoom = camera.getZoom();
		final float newX = camera.getX() - currentCameraZoom * (camera.getViewportWidth() / 2);
		final float newY = camera.getY() + currentCameraZoom * (camera.getViewportHeight() / 2 - this.getHeight());
		this.setPosition(newX, newY);
		this.setScale(currentCameraZoom);
	}

}

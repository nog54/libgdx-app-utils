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

import java.io.File;

import net.dermetfan.gdx.scenes.scene2d.ui.FileChooser.Listener;
import net.dermetfan.gdx.scenes.scene2d.ui.ListFileChooser;
import net.dermetfan.gdx.scenes.scene2d.ui.ListFileChooser.Style;

import org.nognog.gdx.camera.ICamera;
import org.nognog.gdx.camera.ObservableCamera;
import org.nognog.gdx.camera.CameraObserver;
import org.nognog.gdx.ui.ColorUtils;
import org.nognog.gdx.ui.UiUtils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * You should add the instance of this class via
 * {@link ObservableCamera#addCameraObserverAndUpdateIfAdded(CameraObserver)} so
 * that the instance will be shown properly.
 * 
 * @author goshi 2015/04/20
 */
public class CameraFitFileChooser extends Group implements CameraObserver {

	private ListFileChooser chooser;

	/**
	 * @param camera
	 * @param font
	 * @param listener
	 */
	public CameraFitFileChooser(BitmapFont font, Listener listener) {
		this.chooser = new ListFileChooser(createFileChooserStyle(font), listener);
		this.chooser.setFileFilter(new java.io.FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return true;
				}
				final String fileExtension = this.getExtension(pathname.getName());
				return fileExtension.equals("png") || fileExtension.equals("jpg") || fileExtension.equals("jpeg"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}

			private String getExtension(String filename) {
				return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase(); //$NON-NLS-1$
			}
		});
		this.chooser.getContents().addListener(new ClickListener() {
			private String lastTouchPath;

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (this.lastTouchPath != null && this.lastTouchPath == this.getFileChooser().getContents().getSelected()) {
					final FileHandle currentlySelected = this.getFileChooser().currentlySelected();
					if (currentlySelected.isDirectory()) {
						this.getFileChooser().setDirectory(currentlySelected);
					} else {
						for (@SuppressWarnings("hiding")
						EventListener listener : this.getFileChooser().getChooseButton().getListeners()) {
							if (listener instanceof ClickListener) {
								((ClickListener) listener).clicked(event, x, y);
							}
						}

					}
				}
				this.lastTouchPath = this.getFileChooser().getContents().getSelected();
			}

			private ListFileChooser getFileChooser() {
				return CameraFitFileChooser.this.getBody();
			}
		});
		this.addActor(this.chooser);
	}

	protected ListFileChooser getBody() {
		return this.chooser;
	}

	@Override
	public void updateCamera(ICamera camera) {
		final float width = camera.getViewportWidth();
		final float height = camera.getViewportHeight();
		this.setSize(width, height);
		this.chooser.setSize(width, height);
		final float currentCameraZoom = camera.getZoom();
		final float newX = camera.getX() - currentCameraZoom * (width / 2);
		final float newY = camera.getY() + currentCameraZoom * (height / 2 - this.getHeight());
		this.setPosition(newX, newY);
		this.setScale(currentCameraZoom);
	}

	private static Style createFileChooserStyle(BitmapFont font) {
		final TextureRegionDrawable clear1 = UiUtils.getPlaneTextureRegionDrawable(1, 1, Color.CLEAR);
		final TextureRegionDrawable clearBlack1 = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.clearBlack);
		final TextureRegionDrawable softClearBlack1 = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.softClearBlack);
		final TextureRegionDrawable softClearPeterRiver1 = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.softClearPeterRiver);
		final TextureRegionDrawable softClearBelizeHose1 = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.softClearBelizeHole);

		final TextFieldStyle textFieldStyle = new TextFieldStyle(font, ColorUtils.carrot, clear1, clear1, clear1);
		final ListStyle listStyle = new ListStyle(font, ColorUtils.carrot, Color.WHITE, softClearBlack1);
		final TextButtonStyle buttonStyles = new TextButtonStyle(softClearPeterRiver1, softClearBelizeHose1, softClearPeterRiver1, font);
		final Style style = new Style(textFieldStyle, listStyle, buttonStyles, clearBlack1);
		style.space = 16;
		return style;
	}
}

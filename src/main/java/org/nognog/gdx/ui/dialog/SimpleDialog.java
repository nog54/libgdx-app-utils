/** Copyright 2016 Goshi Noguchi (noggon54@gmail.com)
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

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author goshi 2016/03/18
 */
public abstract class SimpleDialog extends Group {
	private final ScrollPane scrollPane;
	private final Table table;

	/**
	 * 
	 */
	public SimpleDialog() {
		this.table = new Table();
		this.scrollPane = new ScrollPane(this.table);
		this.scrollPane.setFillParent(true);
		this.scrollPane.setupOverscroll(0, 0, 0);
		this.scrollPane.getStyle().background = UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.clearBlack);
		this.addActor(this.scrollPane);
	}

	/**
	 * @return the table which is contained in the scrollPane
	 */
	public Table getTable() {
		return this.table;
	}

	/**
	 * @return the scrollPane
	 */
	public ScrollPane getScrollPane() {
		return this.scrollPane;
	}

	/**
	 * @param pad
	 */
	public void setWholePad(float pad) {
		this.getTable().pad(pad);
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		if (this.table != null) {
			final float wholeSpace = this.getWidth() / (3 + 2 * Constants.getGoldenRatio()) / 2;
			this.table.pad(wholeSpace);
		}
	}

	/**
	 * @param background
	 */
	public void setBackground(Drawable background) {
		this.scrollPane.getStyle().background = background;
	}

	/**
	 * @return the background
	 */
	public Drawable getBackground() {
		return this.scrollPane.getStyle().background;
	}
}

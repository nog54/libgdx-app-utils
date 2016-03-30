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
import org.nognog.gdx.ui.UiUtils;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author goshi 2016/03/18
 */
public abstract class SimpleDialog extends Group {
	private final Table table;

	/**
	 * @param width
	 * @param height
	 * 
	 */
	public SimpleDialog(float width, float height) {
		this.setSize(width, height);
		this.table = new Table();
		this.table.setFillParent(true);
		this.table.setBackground(UiUtils.getPlaneTextureRegionDrawable(1, 1, ColorUtils.clearBlack));
		this.addActor(this.table);
	}

	protected Table getTable() {
		return this.table;
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

}

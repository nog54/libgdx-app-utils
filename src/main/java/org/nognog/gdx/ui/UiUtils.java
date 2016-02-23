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

package org.nognog.gdx.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author goshi 2015/01/17
 */
public class UiUtils {
	/**
	 * @param width
	 * @param height
	 * @param color
	 * @return plane texture region
	 */
	public static TextureRegionDrawable getPlaneTextureRegionDrawable(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		return new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
	}

	/**
	 * @param color
	 * @return texture
	 */
	public static Texture createSimpleTexture(Color color) {
		return createSimpleTexture(color, 1, 1);
	}

	/**
	 * @param color
	 * @param width
	 * @param height
	 * @return texture
	 */
	public static Texture createSimpleTexture(Color color, int width, int height) {
		if (width < 1 || height < 1 || color == null) {
			throw new IllegalArgumentException();
		}
		final Pixmap texturePixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		texturePixmap.setColor(color);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				texturePixmap.drawPixel(i, j);
			}
		}
		final Texture result = new Texture(texturePixmap);
		texturePixmap.dispose();
		return result;
	}
}

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

/**
 * @author goshi 2015/03/23
 */
@SuppressWarnings("javadoc")
public class ColorUtils {

	private static final float softClearA = 0.25f;
	private static final float clearA = 0.75f;

	// use flat colors
	public static final Color emerald = Color.valueOf("2ecc71"); //$NON-NLS-1$
	public static final Color nephritis = Color.valueOf("27ae60"); //$NON-NLS-1$
	//public static final Color greemSea = Color.valueOf("16a085"); //$NON-NLS-1$
	public static final Color peterRiver = Color.valueOf("3498db"); //$NON-NLS-1$
	public static final Color belizeHole = Color.valueOf("2980b9"); //$NON-NLS-1$
	//public static final Color midnightBlue = Color.valueOf("2c3e50"); //$NON-NLS-1$
	public static final Color carrot = Color.valueOf("e67e22"); //$NON-NLS-1$
	public static final Color pampkin = Color.valueOf("d35400"); //$NON-NLS-1$

	// use soft-clear flat colors
	public static final Color softClearBlack = toSoftClearColor(Color.BLACK);
	public static final Color softClearPeterRiver = toSoftClearColor(peterRiver);
	public static final Color softClearBelizeHole = toSoftClearColor(belizeHole);

	// use clear flat colors
	public static final Color clearBlack = toClearColor(Color.BLACK);

	private static Color toSoftClearColor(Color baseColor) {
		final Color result = new Color(baseColor);
		result.a = softClearA;
		return result;
	}

	private static Color toClearColor(Color baseColor) {
		final Color result = new Color(baseColor);
		result.a = clearA;
		return result;
	}
}

/*
 * Copyright (C) 2022 Parisi Alessandro
 * This file is part of MaterialFX (https://github.com/palexdev/MaterialFX).
 *
 * MaterialFX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MaterialFX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MaterialFX.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nep;

import java.io.InputStream;
import java.net.URL;

/**
 * Utility class which manages the access to this project's assets.
 * Helps keeping the assets files structure organized.
 */
public class MFXDemoResourcesLoader {

	private MFXDemoResourcesLoader() {
	}

	public static URL loadURL(String path) {
		return MFXDemoResourcesLoader.class.getResource(path);
	}

	public static String load(String path) {
		return loadURL(path).toString();
	}

	public static InputStream loadStream(String name) {
		return MFXDemoResourcesLoader.class.getResourceAsStream(name);
	}

}

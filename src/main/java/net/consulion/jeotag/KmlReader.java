/*
 * Copyright (C) 2014 Consulion
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.consulion.jeotag;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import java.io.File;

public class KmlReader {

    public static void read(final File file) {
        final Kml kml = Kml.unmarshal(file);
        final Feature feature = kml.getFeature();
        if (feature instanceof Document) {
            final Document document = (Document) feature;
            final Feature inner = document.getFeature().get(0);
            if (inner instanceof Placemark) {
                final Placemark pm = (Placemark) inner;
                int x = 0;
            }
        }
    }

    private KmlReader() {
    }
}

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
package net.consulion.jeotag.model;

import java.time.Instant;

public class LocationRecord implements Comparable<LocationRecord> {

    private final float latitude;
    private final float longitude;
    private final float altitude;
    private final Instant time;

    public LocationRecord(final float latitude,
            final float longitude, final float altitude, final Instant time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.time = time;
    }

    @Override
    public int compareTo(final LocationRecord o) {
        final int retVal;
        if (time.isBefore(o.getTime())) {
            retVal = -1;
        } else if (time.isAfter(o.getTime())) {
            retVal = 1;
        } else {
            retVal = 0;
        }
        return retVal;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public Instant getTime() {
        return time;
    }

}

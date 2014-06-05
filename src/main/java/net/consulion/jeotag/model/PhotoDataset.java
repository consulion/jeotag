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

import java.io.File;
import java.time.Instant;

public class PhotoDataset implements Comparable<PhotoDataset> {

    private final File filePhoto;
    private Instant instantTaken;
    private float latitude;
    private float longitude;

    public PhotoDataset(final File filePhoto) {
        this.filePhoto = filePhoto;
    }

    public File getFilePhoto() {
        return filePhoto;
    }

    public Instant getInstantTaken() {
        return instantTaken;
    }

    public void setInstantTaken(final Instant instantTaken) {
        this.instantTaken = instantTaken;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(final float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(final float longitude) {
        this.longitude = longitude;
    }

    @Override
    public int compareTo(final PhotoDataset o) {
        final String a = getFilePhoto().getName();
        final String b = o.getFilePhoto().getName();
        return a.compareTo(b);
    }
}

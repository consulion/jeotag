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
import java.util.Objects;

public class PhotoDataset implements Comparable<PhotoDataset> {

    private final File filePhoto;
    private Instant instantTaken;
    private LocationRecord locationRecord;

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

    public LocationRecord getLocationRecord() {
        return locationRecord;
    }

    public void setLocationRecord(final LocationRecord locationRecord) {
        this.locationRecord = locationRecord;
    }

    @Override
    public int compareTo(final PhotoDataset o) {
        final String a = getFilePhoto().getName();
        final String b = o.getFilePhoto().getName();
        return a.compareTo(b);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.filePhoto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhotoDataset other = (PhotoDataset) obj;
        return Objects.equals(this.getFilePhoto(), other.getFilePhoto());
    }

}

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.consulion.jeotag.model.LocationRecord;
import net.consulion.jeotag.model.PhotoDataset;

public class DataHolder {

    public static DataHolder getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final List<PhotoDataset> photos;
    private final List<LocationRecord> locations;

    private DataHolder() {
        photos = new ArrayList<>();
        locations = new ArrayList<>();
    }

    public List<PhotoDataset> getPhotos() {
        return Collections.unmodifiableList(photos);
    }

    public List<LocationRecord> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    public void addPhoto(final PhotoDataset photoDataset) {
        photos.add(photoDataset);
    }

    public void addPhotos(final List<PhotoDataset> photoDatasets) {
        photos.addAll(photoDatasets);
    }

    public void addLocation(final LocationRecord locationRecord) {
        locations.add(locationRecord);
    }

    public void addLocations(final List<LocationRecord> locationRecords) {
        locations.addAll(locationRecords);
    }

    private static final class InstanceHolder {

        static final DataHolder INSTANCE = new DataHolder();

        private InstanceHolder() {
        }
    }

}

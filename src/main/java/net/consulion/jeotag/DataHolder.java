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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.consulion.jeotag.model.LocationRecord;
import net.consulion.jeotag.model.PhotoDataset;

public class DataHolder {

    private static final Logger LOG = Logger.getLogger(DataHolder.class.getName());

    public static DataHolder getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final ObservableList<PhotoDataset> photos;
    private final ObservableList<LocationRecord> locations;

    private DataHolder() {
        photos = FXCollections.observableArrayList();
        locations = FXCollections.observableArrayList();
    }

    public ObservableList<PhotoDataset> getPhotos() {
        return photos;
    }

    public ObservableList<LocationRecord> getLocations() {
        return locations;
    }

    public void addPhoto(final PhotoDataset photoDataset) {
        final Set<PhotoDataset> noDupSet = new HashSet<>(photos);
        noDupSet.add(photoDataset);
        photos.setAll(noDupSet);
        photos.sort(PhotoDataset::compareTo);
    }

    public void addPhotos(final List<PhotoDataset> photoDatasets) {
        final Set<PhotoDataset> noDupSet = new HashSet<>(photos);
        noDupSet.addAll(photoDatasets);
        photos.setAll(noDupSet);
        photos.sort(PhotoDataset::compareTo);
    }

    public void addLocation(final LocationRecord locationRecord) {
        final Set<LocationRecord> noDupSet = new HashSet<>(locations);
        noDupSet.add(locationRecord);
        locations.setAll(noDupSet);
        locations.sort(LocationRecord::compareTo);
    }

    public void addLocations(final List<LocationRecord> locationRecords) {
        final Set<LocationRecord> noDupSet = new HashSet<>(locations);
        noDupSet.addAll(locationRecords);
        locations.setAll(noDupSet);
        locations.sort(LocationRecord::compareTo);
    }

    private static class InstanceHolder {

        static final DataHolder INSTANCE = new DataHolder();

        private InstanceHolder() {
        }
    }

}

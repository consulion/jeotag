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

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.consulion.jeotag.model.PhotoDataset;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;

public class PhotoLoader {

    public static void load(final List<File> files) {
        final List<PhotoDataset> photos = new ArrayList<>(files.size());
        files.stream().forEach((file) -> {
            final PhotoDataset photoDataset = new PhotoDataset(file);
            final TiffImageMetadata exif = getExif(file);
            if (exif != null) {
                final Instant timeTaken = getTimeTaken(exif, file);
                if (timeTaken != null) {
                    photoDataset.setInstantTaken(timeTaken);
                    photoDataset.setHasGeotag(photoHasGeotag(exif, file));
                    photos.add(photoDataset);
                } else {
                    log(Level.ALL, String.format(
                            "Couldn't parse date/time for file %s", file));
                }
            } else {
                log(Level.WARNING, String.format(
                        "No EXIF metadata found for file %s", file));
            }
        });
        DataHolder.getInstance().addPhotos(photos);
    }

    private static Instant getTimeTaken(final TiffImageMetadata exif,
            final File file) {
        Instant instant = null;
        if (exif != null) {
            try {
                final String[] dateTimeOriginal = exif.getFieldValue(
                        ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                if (dateTimeOriginal.length == 1
                        && dateTimeOriginal[0].matches(
                                "\\d{4}\\:\\d{2}:\\d{2}\\s\\d{2}:\\d{2}:\\d{2}")) {
                    final String[] split = dateTimeOriginal[0].split("\\s");
                    final String dateString = split[0];
                    final String timeString = split[1];
                    final String[] dateSplit = dateString.split("\\:");
                    final int year = Integer.parseInt(dateSplit[0]);
                    final int month = Integer.parseInt(dateSplit[1]);
                    final int day = Integer.parseInt(dateSplit[2]);
                    final String[] timeSplit = timeString.split("\\:");
                    final int hour = Integer.parseInt(timeSplit[0]);
                    final int minute = Integer.parseInt(timeSplit[1]);
                    final int second = Integer.parseInt(timeSplit[2]);
                    final LocalDateTime ldt = LocalDateTime.of(
                            year, month, day, hour, minute, second);
                    instant = ldt.toInstant(ZoneOffset.ofHoursMinutes(0, 0));
                } else {
                    log(Level.WARNING, String.format(
                            "unknown date format in file %s", file.getPath()));
                }
            }
            catch (ImageReadException ex) {
                Logger.getLogger(PhotoLoader.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
        return instant;
    }

    private static TiffImageMetadata getExif(final File file) {
        TiffImageMetadata exif = null;
        try {
            final IImageMetadata metadata = Imaging.getMetadata(file);
            if (metadata != null) {
                final JpegImageMetadata jpegMetadata
                        = (JpegImageMetadata) metadata;
                exif = jpegMetadata.getExif();
            } else {
                log(Level.WARNING, String.format(
                        "No metadata found for file %s", file));
            }

        }
        catch (final ImageReadException | IOException ex) {
            Logger.getLogger(PhotoLoader.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return exif;
    }

    private static void log(final Level level, final String message) {
        Logger.getLogger(PhotoLoader.class.getName()).log(level, message);
    }

    private static Boolean photoHasGeotag(final TiffImageMetadata exif,
            final File file) {
        Boolean hasGeoTag = false;
        try {
            final TiffImageMetadata.GPSInfo gps = exif.getGPS();
            if (gps != null
                    && gps.getLatitudeAsDegreesNorth() != 0
                    && gps.getLongitudeAsDegreesEast() != 0) {
                hasGeoTag = Boolean.TRUE;
            }
        }
        catch (ImageReadException ex) {
            Logger.getLogger(PhotoLoader.class.getName()).log(
                    Level.FINE, null, String.format(
                            "File: %s couldn't be read. Cause: %s",
                            file.getPath(), ex.getMessage()));
        }
        return hasGeoTag;
    }

    private PhotoLoader() {
    }

}

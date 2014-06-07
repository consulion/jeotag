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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.consulion.jeotag.model.LocationRecord;

public class KmlReader {

    private static final Logger LOG = Logger.getLogger(KmlReader.class.getName());

    public static void read(final File file) {
        final Path path = file.toPath();
        final Charset charset = Charset.forName("UTF-8");
        final List<Instant> rawInstants = new ArrayList<>();
        final List<float[]> rawLocations = new ArrayList<>();
        List<LocationRecord> locations = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("<when>\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d-\\d\\d:\\d\\d<\\/when>")) {
                    rawInstants.add(decodeInstant(line));
                } else if (line.matches("<gx\\:coord>\\d+\\.\\d+\\s\\d+\\.\\d+\\s\\d+<\\/gx\\:coord>")) {
                    rawLocations.add(decodeLocation(line));
                } else if (line.matches("<when>.+<\\when>|<gx\\:coord>.+<\\/gx\\:coord>")) {
                    LOG.log(Level.WARNING,
                            String.format("Couldn't decode line: %s", line));
                }
            }
            locations = assembleList(rawInstants, rawLocations);
            if (locations != null) {
                DataHolder.getInstance().addLocations(locations);
            }
        }
        catch (final IOException | RuntimeException x) {
            LOG.log(Level.SEVERE, x.getMessage());
        }
    }

    private static List<LocationRecord> assembleList(
            final List<Instant> rawInstants, final List<float[]> rawLocations) {
        List<LocationRecord> locations = null;
        if (rawInstants.size() > 0 && rawInstants.size() == rawLocations.size()) {
            locations = new ArrayList<>(rawInstants.size());
            for (int i = 0; i < rawInstants.size(); i++) {
                final float latitude = rawLocations.get(i)[0];
                final float longitude = rawLocations.get(i)[1];
                final float altitude = rawLocations.get(i)[2];
                final Instant instant = rawInstants.get(i);
                final LocationRecord lr
                        = new LocationRecord(
                                latitude, longitude, altitude, instant);
                locations.add(lr);
            }
        }
        return locations;
    }

    private static float[] decodeLocation(final String line) throws RuntimeException {
        final String replaceAll = line.replaceAll("[<>\\/\\:]+|[a-z]+", "");
        final String[] split = replaceAll.split("\\s");
        float[] rawLoc = new float[3];
        if (split.length == 3) {
            final String stringLatitude = split[0];
            final String stringLongitude = split[1];
            final String stringAltidtude = split[2];
            final float latitude = Float.parseFloat(stringLatitude);
            final float longitude = Float.parseFloat(stringLongitude);
            final float altitude = Integer.parseInt(stringAltidtude);
            rawLoc[0] = latitude;
            rawLoc[1] = longitude;
            rawLoc[2] = altitude;
        } else {
            throw new RuntimeException("Couldn't decode Location " + line);
        }

        return rawLoc;
    }

    private static Instant decodeInstant(final String line) {
        final String year = line.substring(6, 10);
        final String month = line.substring(11, 13);
        final String day = line.substring(14, 16);
        final String hour = line.substring(17, 19);
        final String minute = line.substring(20, 22);
        final String second = line.substring(23, 25);
        final String millies = line.substring(26, 29);
        final String offsetHours = line.substring(29, 32);
        final String offsetMinutes = line.substring(33, 35);

        final LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(year),
                Integer.parseInt(month), Integer.parseInt(day),
                Integer.parseInt(hour), Integer.parseInt(minute),
                Integer.parseInt(second), Integer.parseInt(millies) * 1000);
        //minus 22*60*60 seconds because all times are off by 15 hours
        // + time zone offset is always 7 hours.
        //still waiting for an explanation from google...
        return ldt.toInstant(ZoneOffset.ofHoursMinutes(
                Integer.parseInt(offsetHours),
                Integer.parseInt(offsetMinutes))).minusSeconds(22 * 60 * 60);
    }

    private KmlReader() {
    }
}

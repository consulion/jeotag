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
import net.consulion.jeotag.model.LocationRecord;

public class KmlReader {

    public static void read(final File file) {

        final Path path = file.toPath();
        final Charset charset = Charset.forName("UTF-8");
        final List<Instant> rawInstants = new ArrayList<>();
        final List<float[]> rawLocations = new ArrayList<>();
        final List<LocationRecord> locations = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("<when>\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d-\\d\\d:\\d\\d<\\/when>")) {
                    rawInstants.add(decodeInstant(line));
                } else if (line.matches("<gx\\:coord>\\d\\d\\.\\d+\\s\\d\\d\\.\\d+\\s\\d<\\/gx\\:coord>")) {
                    final String replaceAll = line.replaceAll("[<>\\/\\:]+|[a-z]+", "");
                    final String[] split = replaceAll.split("\\s");
                    if (split.length == 3) {
                        float[] rawLoc = new float[2];
                        final String floatLatitude = split[0];
                        final String floatLongitude = split[1];
                        final float latitude = Float.parseFloat(floatLatitude);
                        final float longitude = Float.parseFloat(floatLongitude);
                        rawLoc[0] = latitude;
                        rawLoc[1] = longitude;
                        rawLocations.add(rawLoc);
                    }
                }
            }
        }
        catch (final IOException x) {
            System.err.format("IOException: %s%n", x);
        }
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

        return ldt.toInstant(ZoneOffset.ofHoursMinutes(
                Integer.parseInt(offsetHours),
                Integer.parseInt(offsetMinutes)));
    }

    private KmlReader() {
    }
}

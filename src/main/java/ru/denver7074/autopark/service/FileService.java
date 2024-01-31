package ru.denver7074.autopark.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.denver7074.autopark.cach.CoordinateHash;
import ru.denver7074.autopark.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileService {

    CoordinateHash hash;

    public Map<String, Double> calculateWay(MultipartFile file) {
        try (InputStream stream = file.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(stream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String line;
            while (isNotEmpty(line = bufferedReader.readLine())) {
                String[] split = line.split(",");
                if (split[0].contains("GPGGA")) {
                    Double n = parseCoordinate(split[2]);
                    Double e = parseCoordinate(split[4]);
                    if (ObjectUtils.anyNull(n, e)) continue;
                    hash.addCoordinate(n, e);
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(hash.getCoordinates().size());
        return calculate();
    }

    private Map<String, Double> calculate() {
        List<Pair<Double, Double>> values = hash.getCoordinates().values().stream().toList();
        double allWay = 0;
        for (int i = 0; i < values.size() - 1; i++) {
            allWay = allWay + calculateDistance(values.get(i), values.get(i + 1));
        }
        hash.clearMap();
        return Map.of("Пройденный путь, м ", allWay);
    }

    private static double calculateDistance(Pair<Double, Double> start, Pair<Double, Double> end) {
        final int R = 6371;
        double dLat = Math.toRadians(end.getKey() - start.getKey());
        double dLon = Math.toRadians(end.getValue() - start.getValue());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(start.getKey())) * Math.cos(Math.toRadians(end.getKey())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000;
    }

    private static Double parseCoordinate(String coordinate) {
        Double degrees = Utils.safeGet(() -> Double.parseDouble(coordinate.substring(0, 2)));
        Double minutes = Utils.safeGet(() -> Double.parseDouble(coordinate.substring(2)));
        if (ObjectUtils.anyNull(degrees, minutes)) return null;
        return degrees + minutes / 60.0;
    }
}

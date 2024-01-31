package ru.denver7074.autopark.cach;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoordinateHash {

    Map<Integer, Pair<Double, Double>> map = new HashMap<>();
    int currentIndex = 0;

    public void addCoordinate(Double n, Double e) {
        map.put(currentIndex++,Pair.of(n, e));
    }

    public Map<Integer, Pair<Double, Double>> getCoordinates() {
        return map;
    }

    public void clearMap() {
        map.clear();
        currentIndex = 0;
    }

}

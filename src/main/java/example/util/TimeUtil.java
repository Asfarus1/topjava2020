package example.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenInclusive(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return !lt.isBefore(startTime) && !lt.isAfter(endTime);
    }
}

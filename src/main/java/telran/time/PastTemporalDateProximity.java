package telran.time;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class PastTemporalDateProximity implements TemporalAdjuster{
    Temporal[] temporals;
//TODO some encapsulation
//array of temporals supprting Day, Month, Year (Dates) 

public PastTemporalDateProximity(Temporal[] temporals) {
    Temporal[] resultTemporals = Arrays.copyOf(temporals, temporals.length);
    java.util.Arrays.sort(resultTemporals);
    this.temporals = resultTemporals;
}    

   @Override
    public Temporal adjustInto(Temporal temporal) {
        Temporal result = null;
        int index = getIndexOfPastTemporal(temporal);
        if ( index >= 0 && index < temporals.length) {
            result = temporals[index];
            long days = ChronoUnit.DAYS.between(temporal, result);
            result = temporal.plus(days, ChronoUnit.DAYS);
        }
        return result;
    }

   

    private int getIndexOfPastTemporal(Temporal temporal) {
        int start = 0;
        int end = temporals.length - 1;
        int mid = start + (end - start) / 2;
        while (start <= end) {
            if (temporal.until(temporals[mid], ChronoUnit.DAYS) >= 0) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
            mid = start + (end - start) / 2;
        }
        return start - 1;
    }

}
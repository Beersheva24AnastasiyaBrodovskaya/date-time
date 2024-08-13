package telran.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.temporal.Temporal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


public class DataTimeTest {
    @Test
    void nextFriday13Test() {
        LocalDate current = LocalDate.of(2024, 8, 11);
        LocalDate expected = LocalDate.of(2024, 9, 13);
        NextFriday13 adjuster = new NextFriday13();

        assertEquals(expected, current.with(adjuster));
        assertThrows(RuntimeException.class, () -> LocalTime.now().with(adjuster));
    }

    @Test
    void pastTemporalDateProximityTest() {
        LocalDate date20Jan = LocalDate.of(2024, 20, 1);
        LocalDate date4Feb = LocalDate.of(2024, 2, 4);
        LocalDate date3Aug = LocalDate.of(2024, 8, 3);
        LocalDate date1Sep = LocalDate.of(2024, 9, 1);
        LocalDate date26Oct = LocalDate.of(2024, 10, 26);
        LocalDate date14Nov = LocalDate.of(2024, 11, 14);

        PastTemporalDateProximity adjuster = new PastTemporalDateProximity(new Temporal[]{
            date26Oct, date14Nov, date3Aug, 
        });

        assertNull(date1Sep.with(adjuster));
        assertNull(date4Feb.with(adjuster));
        assertEquals(date20Jan, date1Sep.with(adjuster));
        assertEquals(date4Feb, date3Aug.with(adjuster));
        assertEquals(date3Aug, date1Sep.with(adjuster));
        assertEquals(date26Oct, date14Nov.with(adjuster));

        assertEquals(MinguoDate.from(date3Aug), MinguoDate.from(date26Oct).with(adjuster));
        assertEquals(JapaneseDate.from(date3Aug), JapaneseDate.from(date26Oct).with(adjuster));

        assertThrows(RuntimeException.class, () -> LocalTime.now().with(adjuster));
    }

}

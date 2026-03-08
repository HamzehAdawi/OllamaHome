package com.HamzehAdawi.OllamaApp.tools;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeToolsTest {

    @Test
    void getCurrentDateTime_returnsParsableZonedDateTime_closeToNow() {
        DateTimeTools dt = new DateTimeTools();

        String val = dt.getCurrentDateTime();

        assertNotNull(val);

        ZonedDateTime zdt = ZonedDateTime.parse(val);
        Instant then = zdt.toInstant();
        Instant now = Instant.now();

        long seconds = Math.abs(Duration.between(now, then).getSeconds());

        // Should be very recent (within 10 seconds)
        assertTrue(seconds < 10, "Returned time should be near now");
    }
}

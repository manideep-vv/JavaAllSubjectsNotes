package java8;

import java.time.Clock;
import java.time.ZoneId;

public class java8practice {
    public static void main(String[] args) {
//        ZoneId.getAvailableZoneIds().forEach(z-> System.out.println(z));
        Clock c=Clock.system(ZoneId.of("America/New_York"));
        System.out.println(c.getZone());
        System.out.println(Clock.systemUTC().instant());
    }

}

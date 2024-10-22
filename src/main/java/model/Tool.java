package main.java.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Tool {
    private final String code;
    private final String type;
    private final String brand;
    private final BigDecimal dailyCharge;
    private final boolean chargeWeekday;
    private final boolean chargeWeekend;
    private final boolean chargeHoliday;

    public Tool(String code, String type, String brand, BigDecimal dailyCharge,
                boolean chargeWeekday, boolean chargeWeekend, boolean chargeHoliday) {
        this.code = code;
        this.type = type;
        this.brand = brand;
        this.dailyCharge = dailyCharge;
        this.chargeWeekday = chargeWeekday;
        this.chargeWeekend = chargeWeekend;
        this.chargeHoliday = chargeHoliday;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public boolean isChargeable(LocalDate date) {
        return (chargeWeekday && date.getDayOfWeek().getValue() < 6 && !isHoliday(date)) ||
                (chargeWeekend && date.getDayOfWeek().getValue() == 6 && !isHoliday(date)) ||
                (chargeWeekend && date.getDayOfWeek().getValue() == 7 && !isHoliday(date)) ||
                (chargeHoliday && isHoliday(date));
    }

    private boolean isHoliday(LocalDate date) {
        // Check for Independence Day and Labor Day
        LocalDate independenceDay = getIndependenceDay(date.getYear());
        LocalDate laborDay = getLaborDay(date.getYear());

        return date.equals(independenceDay) || date.equals(laborDay);
    }

    private LocalDate getLaborDay(int year) {
        // Find the first Monday in September for a given year to find Labor Day
        LocalDate firstOfSeptember = LocalDate.of(year, 9, 1);
        while (firstOfSeptember.getDayOfWeek().getValue() != 1) { // Monday
            firstOfSeptember = firstOfSeptember.plusDays(1);
        }
        return firstOfSeptember;
    }

    private LocalDate getIndependenceDay(int year) {
        // Find the nearest weekday to Independence Day to observe the holiday if it falls on a Weekend
        LocalDate independenceDay = LocalDate.of(year, 7, 4);

        if (independenceDay.getDayOfWeek().getValue() == 6) { // Saturday
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek().getValue() == 7) { // Sunday
            independenceDay = independenceDay.plusDays(1);
        }
        return independenceDay;
    }
}
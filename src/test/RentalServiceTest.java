package test;

import main.java.model.RentalAgreement;
import main.java.service.RentalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        rentalService = new RentalService();
    }

    @Test
    void testCheckoutInvalidDiscount() { // TEST 1
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
    }

    @Test
    void testCheckoutInvalidRentalDays() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("CHNS", 0, 25, LocalDate.of(2015, 7, 2));
        });
        assertEquals("Rental day count must be 1 or greater.", exception.getMessage());
    }


    @Test
    void testCheckoutValidCaseLadder() { // TEST 2
        RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        Assertions.assertEquals("LADW", agreement.getToolCode());
        Assertions.assertEquals("Ladder", agreement.getToolType());
        Assertions.assertEquals("Werner", agreement.getToolBrand());
        Assertions.assertEquals(3, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());
        Assertions.assertEquals(new BigDecimal("1.99"), agreement.getDailyRentalCharge());
        Assertions.assertEquals(2, agreement.getChargeableDays()); // One day is free (7/4)
        Assertions.assertEquals(new BigDecimal("3.98"), agreement.getPreDiscountCharge());
        Assertions.assertEquals(10, agreement.getDiscountPercent());
        Assertions.assertEquals(new BigDecimal("0.40"), agreement.getDiscountAmount());
        Assertions.assertEquals(new BigDecimal("3.58"), agreement.getFinalCharge());
    }


    @Test
    void testCheckoutChainsawHoliday() { // TEST 3
        RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));

        Assertions.assertEquals("CHNS", agreement.getToolCode());
        Assertions.assertEquals("Chainsaw", agreement.getToolType());
        Assertions.assertEquals("Stihl", agreement.getToolBrand());
        Assertions.assertEquals(5, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate());
        Assertions.assertEquals(new BigDecimal("1.49"), agreement.getDailyRentalCharge());
        Assertions.assertEquals(3, agreement.getChargeableDays());
        Assertions.assertEquals(new BigDecimal("4.47"), agreement.getPreDiscountCharge());
        Assertions.assertEquals(25, agreement.getDiscountPercent());
        Assertions.assertEquals(new BigDecimal("1.12"), agreement.getDiscountAmount());
        Assertions.assertEquals(new BigDecimal("3.35"), agreement.getFinalCharge());

    }

    @Test
    void testCheckoutJackhammerLaborDay() { // TEST 4
        RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));

        Assertions.assertEquals("JAKD", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("DeWalt", agreement.getToolBrand());
        Assertions.assertEquals(6, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate());
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge());
        Assertions.assertEquals(3, agreement.getChargeableDays());
        Assertions.assertEquals(new BigDecimal("8.97"), agreement.getPreDiscountCharge());
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount());
        Assertions.assertEquals(new BigDecimal("8.97"), agreement.getFinalCharge());

    }

    @Test
    void testCheckoutMultiYearAgreement() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 1000, 0, LocalDate.of(2015, 7, 2));

        Assertions.assertEquals("JAKR", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("Ridgid", agreement.getToolBrand());
        Assertions.assertEquals(1000, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2018, 3, 28), agreement.getDueDate());
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge());
        Assertions.assertEquals(708, agreement.getChargeableDays());
        Assertions.assertEquals(new BigDecimal("2116.92"), agreement.getPreDiscountCharge());
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount());
        Assertions.assertEquals(new BigDecimal("2116.92"), agreement.getFinalCharge());

        // agreement.printAgreement();

    }

    @Test
    void testCheckoutJackhammerIndependenceDay() { // TEST 5
        RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));

        Assertions.assertEquals("JAKR", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("Ridgid", agreement.getToolBrand());
        Assertions.assertEquals(9, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate());
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge());
        Assertions.assertEquals(5, agreement.getChargeableDays());
        Assertions.assertEquals(new BigDecimal("14.95"), agreement.getPreDiscountCharge());
        Assertions.assertEquals(0, agreement.getDiscountPercent());
        Assertions.assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount());
        Assertions.assertEquals(new BigDecimal("14.95"), agreement.getFinalCharge());

        // agreement.printAgreement();

    }

    @Test
    void testCheckoutJackhammerIndependenceDay2020() { // TEST 6
        RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));

        Assertions.assertEquals("JAKR", agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("Ridgid", agreement.getToolBrand());
        Assertions.assertEquals(4, agreement.getRentalDays());
        Assertions.assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        Assertions.assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate());
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge());
        Assertions.assertEquals(1, agreement.getChargeableDays());
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.getPreDiscountCharge());
        Assertions.assertEquals(50, agreement.getDiscountPercent());
        Assertions.assertEquals(new BigDecimal("1.50"), agreement.getDiscountAmount());
        Assertions.assertEquals(new BigDecimal("1.49"), agreement.getFinalCharge());

        agreement.printAgreement();

    }
}
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
    void checkout_shouldThrowException_WhenDiscountPercentIsGreaterThan100() { // TEST 1
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
    }

    @Test
    void checkout_shouldThrowException_WhenRentalDayCountIsLessThanOne() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("CHNS", 0, 25, LocalDate.of(2015, 7, 2));
        });
        assertEquals("Rental day count must be 1 or greater.", exception.getMessage());
    }


    @Test
    void checkout_shouldReturnValidRentalAgreement_whenLadderIsCheckedOutOverIndependenceDay() { // TEST 2
        RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        Assertions.assertAll("Verify rental agreement details",
                () -> Assertions.assertEquals("LADW", agreement.getToolCode(), "Tool code should match"),
                () -> Assertions.assertEquals("Ladder", agreement.getToolType(), "Tool type should be Ladder"),
                () -> Assertions.assertEquals("Werner", agreement.getToolBrand(), "Tool brand should be Werner"),
                () -> Assertions.assertEquals(3, agreement.getRentalDays(), "Rental days should match"),
                () -> Assertions.assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate(), "Checkout date should match"),
                () -> Assertions.assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate(), "Due date should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("1.99"), agreement.getDailyRentalCharge(), "Daily rental charge should match"),
                () -> Assertions.assertEquals(2, agreement.getChargeableDays(), "Chargeable days should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("3.98"), agreement.getPreDiscountCharge(), "Pre-discount charge should match"),
                () -> Assertions.assertEquals(10, agreement.getDiscountPercent(), "Discount percent should match"),
                () -> Assertions.assertEquals(new BigDecimal("0.40"), agreement.getDiscountAmount(), "Discount amount should match"),
                () -> Assertions.assertEquals(new BigDecimal("3.58"), agreement.getFinalCharge(), "Final charge should match")
        );
    }

    @Test
    void checkout_shouldReturnValidRentalAgreement_whenChainsawIsCheckedOutOverIndependenceDay() { // TEST 3
        RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));

        Assertions.assertAll("Verify rental agreement details",
                () -> Assertions.assertEquals("CHNS", agreement.getToolCode(), "Tool code should match"),
                () -> Assertions.assertEquals("Chainsaw", agreement.getToolType(), "Tool type should be Chainsaw"),
                () -> Assertions.assertEquals("Stihl", agreement.getToolBrand(), "Tool brand should be Stihl"),
                () -> Assertions.assertEquals(5, agreement.getRentalDays(), "Rental days should match"),
                () -> Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate(), "Checkout date should match"),
                () -> Assertions.assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate(), "Due date should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("1.49"), agreement.getDailyRentalCharge(), "Daily rental charge should match"),
                () -> Assertions.assertEquals(3, agreement.getChargeableDays(), "Chargeable days should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("4.47"), agreement.getPreDiscountCharge(), "Pre-discount charge should match"),
                () -> Assertions.assertEquals(25, agreement.getDiscountPercent(), "Discount percent should match"),
                () -> Assertions.assertEquals(new BigDecimal("1.12"), agreement.getDiscountAmount(), "Discount amount should match"),
                () -> Assertions.assertEquals(new BigDecimal("3.35"), agreement.getFinalCharge(), "Final charge should match")
        );
    }

    @Test
    void checkout_shouldReturnValidRentalAgreement_whenJackhammerIsCheckedOutOverLaborDay() { // TEST 4
        RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));

        Assertions.assertAll("Verify rental agreement details",
                () -> Assertions.assertEquals("JAKD", agreement.getToolCode(), "Tool code should match"),
                () -> Assertions.assertEquals("Jackhammer", agreement.getToolType(), "Tool type should be Jackhammer"),
                () -> Assertions.assertEquals("DeWalt", agreement.getToolBrand(), "Tool brand should be DeWalt"),
                () -> Assertions.assertEquals(6, agreement.getRentalDays(), "Rental days should match"),
                () -> Assertions.assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate(), "Checkout date should match"),
                () -> Assertions.assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate(), "Due date should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge(), "Daily rental charge should match"),
                () -> Assertions.assertEquals(3, agreement.getChargeableDays(), "Chargeable days should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("8.97"), agreement.getPreDiscountCharge(), "Pre-discount charge should match"),
                () -> Assertions.assertEquals(0, agreement.getDiscountPercent(), "Discount percent should match"),
                () -> Assertions.assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount(), "Discount amount should match"),
                () -> Assertions.assertEquals(new BigDecimal("8.97"), agreement.getFinalCharge(), "Final charge should match")
        );
    }

    @Test
    void checkout_shouldReturnValidRentalAgreement_whenMultiYearAgreementIsProcessed() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 1000, 0, LocalDate.of(2015, 7, 2));

        Assertions.assertAll("Verify rental agreement details",
                () -> Assertions.assertEquals("JAKR", agreement.getToolCode(), "Tool code should match"),
                () -> Assertions.assertEquals("Jackhammer", agreement.getToolType(), "Tool type should be Jackhammer"),
                () -> Assertions.assertEquals("Ridgid", agreement.getToolBrand(), "Tool brand should be Ridgid"),
                () -> Assertions.assertEquals(1000, agreement.getRentalDays(), "Rental days should match"),
                () -> Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate(), "Checkout date should match"),
                () -> Assertions.assertEquals(LocalDate.of(2018, 3, 28), agreement.getDueDate(), "Due date should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge(), "Daily rental charge should match"),
                () -> Assertions.assertEquals(708, agreement.getChargeableDays(), "Chargeable days should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("2116.92"), agreement.getPreDiscountCharge(), "Pre-discount charge should match"),
                () -> Assertions.assertEquals(0, agreement.getDiscountPercent(), "Discount percent should match"),
                () -> Assertions.assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount(), "Discount amount should be zero"),
                () -> Assertions.assertEquals(new BigDecimal("2116.92"), agreement.getFinalCharge(), "Final charge should match")
        );
    }

    @Test
    void checkout_shouldReturnValidRentalAgreement_whenJackhammerIsCheckedOutOverIndependenceDay() { // TEST 5
        RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));

        Assertions.assertAll("Verify rental agreement details",
                () -> Assertions.assertEquals("JAKR", agreement.getToolCode(), "Tool code should match"),
                () -> Assertions.assertEquals("Jackhammer", agreement.getToolType(), "Tool type should be Jackhammer"),
                () -> Assertions.assertEquals("Ridgid", agreement.getToolBrand(), "Tool brand should be Ridgid"),
                () -> Assertions.assertEquals(9, agreement.getRentalDays(), "Rental days should match"),
                () -> Assertions.assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate(), "Checkout date should match"),
                () -> Assertions.assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate(), "Due date should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge(), "Daily rental charge should match"),
                () -> Assertions.assertEquals(5, agreement.getChargeableDays(), "Chargeable days should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("14.95"), agreement.getPreDiscountCharge(), "Pre-discount charge should match"),
                () -> Assertions.assertEquals(0, agreement.getDiscountPercent(), "Discount percent should match"),
                () -> Assertions.assertEquals(new BigDecimal("0.00"), agreement.getDiscountAmount(), "Discount amount should match"),
                () -> Assertions.assertEquals(new BigDecimal("14.95"), agreement.getFinalCharge(), "Final charge should match")
        );
    }

    @Test
    void checkout_shouldReturnValidRentalAgreement_whenJackhammerIsCheckedOutOverIndependenceDayIn2020() { // TEST 6
        RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));

        Assertions.assertAll("Verify rental agreement details",
                () -> Assertions.assertEquals("JAKR", agreement.getToolCode(), "Tool code should match"),
                () -> Assertions.assertEquals("Jackhammer", agreement.getToolType(), "Tool type should be Jackhammer"),
                () -> Assertions.assertEquals("Ridgid", agreement.getToolBrand(), "Tool brand should be Ridgid"),
                () -> Assertions.assertEquals(4, agreement.getRentalDays(), "Rental days should match"),
                () -> Assertions.assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate(), "Checkout date should match"),
                () -> Assertions.assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate(), "Due date should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("2.99"), agreement.getDailyRentalCharge(), "Daily rental charge should match"),
                () -> Assertions.assertEquals(1, agreement.getChargeableDays(), "Chargeable days should be calculated correctly"),
                () -> Assertions.assertEquals(new BigDecimal("2.99"), agreement.getPreDiscountCharge(), "Pre-discount charge should match"),
                () -> Assertions.assertEquals(50, agreement.getDiscountPercent(), "Discount percent should match"),
                () -> Assertions.assertEquals(new BigDecimal("1.50"), agreement.getDiscountAmount(), "Discount amount should match"),
                () -> Assertions.assertEquals(new BigDecimal("1.49"), agreement.getFinalCharge(), "Final charge should match")
        );
    }

}
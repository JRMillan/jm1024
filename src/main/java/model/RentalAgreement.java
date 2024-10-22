package main.java.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;

public class RentalAgreement {


    private final String toolCode;
    private final String toolType;
    private final String toolBrand;
    private final int rentalDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final BigDecimal dailyRentalCharge;
    private final int chargeableDays;
    private final BigDecimal preDiscountCharge;
    private final int discountPercent;
    private final BigDecimal discountAmount;
    private final BigDecimal finalCharge;
    private final List<LocalDate> listOfChargeableDays = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    private final NumberFormat decimalFormatter = NumberFormat.getCurrencyInstance();

    public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, int discountPercent) {
        this.toolCode = tool.getCode();
        this.toolType = tool.getType();
        this.toolBrand = tool.getBrand();
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.dailyRentalCharge = tool.getDailyCharge();

        this.chargeableDays = calculateChargeableDays(tool);
        this.preDiscountCharge = dailyRentalCharge.multiply(BigDecimal.valueOf(chargeableDays)).setScale(2, RoundingMode.HALF_UP);
        this.discountPercent = discountPercent;
        this.discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
        this.finalCharge = preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    private int calculateChargeableDays(Tool tool) {
        int chargeableDays = 0;
        for (int i = 1; i <= rentalDays; i++) {
            LocalDate date = checkoutDate.plusDays(i);
            if (tool.isChargeable(date)) {
                chargeableDays++;
                listOfChargeableDays.add(date);
            }
        }
        return chargeableDays;
    }

    public void printAgreement() {
        System.out.printf("Tool code: %s\n", toolCode);
        System.out.printf("Tool type: %s\n", toolType);
        System.out.printf("Tool brand: %s\n", toolBrand);
        System.out.printf("Rental days: %d\n", rentalDays);
        System.out.printf("Checkout date: %s\n", checkoutDate.format(dateFormatter));
        System.out.printf("Due date: %s\n", dueDate.format(dateFormatter));
        System.out.printf("Daily rental charge: %s\n", decimalFormatter.format(dailyRentalCharge));
        System.out.printf("Chargeable days: %d\n", chargeableDays);
        System.out.printf("Pre-discount charge: %s\n", decimalFormatter.format(preDiscountCharge));
        System.out.printf("Discount percent: %d%%\n", discountPercent);
        System.out.printf("Discount amount: %s\n", decimalFormatter.format(discountAmount));
        System.out.printf("Final charge: %s\n", decimalFormatter.format(finalCharge));
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public int getChargeableDays() {
        return chargeableDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    public void getListOfChargeableDays() {
        for (int i = 0; i < listOfChargeableDays.size(); i++) {
            System.out.println(listOfChargeableDays.get(i));
        }
    }
}
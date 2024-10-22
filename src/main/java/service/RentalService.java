package main.java.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import main.java.model.RentalAgreement;
import main.java.model.Tool;


public class RentalService {

    private final Tool[] tools = {
            new Tool("CHNS", "Chainsaw", "Stihl", new BigDecimal("1.49"), true, false, true),
            new Tool("LADW", "Ladder", "Werner", new BigDecimal("1.99"), true, true, false),
            new Tool("JAKD", "Jackhammer", "DeWalt", new BigDecimal("2.99"), true, false, false),
            new Tool("JAKR", "Jackhammer", "Ridgid", new BigDecimal("2.99"), true, false, false)
    };

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        Tool tool = findToolByCode(toolCode);
        if (tool == null) {
            throw new IllegalArgumentException("Invalid tool code.");
        }

        return new RentalAgreement(tool, rentalDays, checkoutDate, discountPercent);
    }

    private Tool findToolByCode(String toolCode) {
        for (Tool tool : tools) {
            if (tool.getCode().equalsIgnoreCase(toolCode)) {
                return tool;
            }
        }
        return null;
    }


}

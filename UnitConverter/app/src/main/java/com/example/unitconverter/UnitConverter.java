package com.example.unitconverter;

public class UnitConverter {

    public static double convertLength(double value, String fromUnit, String toUnit) {
        switch (fromUnit) {
            case "Centimeters":
                if (toUnit.equals("Meters")) {
                    return value / 100;
                }
                break;
            case "Meters":
                if (toUnit.equals("Centimeters")) {
                    return value * 100;
                }
                break;
        }
        return value; // Default case
    }

    public static double convertWeight(double value, String fromUnit, String toUnit) {
        switch (fromUnit) {
            case "Grams":
                if (toUnit.equals("Kilograms")) {
                    return value / 1000;
                }
                break;
            case "Kilograms":
                if (toUnit.equals("Grams")) {
                    return value * 1000;
                }
                break;
        }
        return value; // Default case
    }

    public static double convertVolume(double value, String fromUnit, String toUnit) {
        switch (fromUnit) {
            case "Milliliters":
                if (toUnit.equals("Liters")) {
                    return value / 1000;
                }
                break;
            case "Liters":
                if (toUnit.equals("Milliliters")) {
                    return value * 1000;
                }
                break;
        }
        return value; // Default case
    }

    public static double convertTemperature(double value, String fromUnit, String toUnit) {
        switch (fromUnit) {
            case "Celsius":
                if (toUnit.equals("Fahrenheit")) {
                    return (value * 9/5) + 32;
                }
                break;
            case "Fahrenheit":
                if (toUnit.equals("Celsius")) {
                    return (value - 32) * 5/9;
                }
                break;
        }
        return value; // Default case
    }

    public static double convertTime(double value, String fromUnit, String toUnit) {
        switch (fromUnit) {
            case "Seconds":
                switch (toUnit) {
                    case "Minutes":
                        return value / 60;
                    case "Hours":
                        return value / 3600;
                }
                break;
            case "Minutes":
                switch (toUnit) {
                    case "Seconds":
                        return value * 60;
                    case "Hours":
                        return value / 60;
                }
                break;
            case "Hours":
                switch (toUnit) {
                    case "Seconds":
                        return value * 3600;
                    case "Minutes":
                        return value * 60;
                }
                break;
        }
        return value; // Default case
    }

    public static String convertDecimalToBinary(int decimal) {
        return Integer.toBinaryString(decimal);
    }

    public static String convertBinaryToDecimal(String binary) {
        return String.valueOf(Long.parseLong(binary, 2));
    }

    public static String convertDecimalToHexadecimal(int decimal) {
        return Integer.toHexString(decimal).toUpperCase();
    }

    public static String convertHexadecimalToDecimal(String hexadecimal) {
        return String.valueOf(Long.parseLong(hexadecimal, 16));
    }

    public static String convertDecimalToOctal(int decimal) {
        return Integer.toOctalString(decimal);
    }

    public static String convertOctalToDecimal(String octal) {
        return String.valueOf(Long.parseLong(octal, 8));
    }
}

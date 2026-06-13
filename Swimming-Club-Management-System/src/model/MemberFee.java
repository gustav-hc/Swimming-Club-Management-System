package model;

public class MemberFee {
    private static double junior = 1000;
    private static double senior = 1600;
    private static double passive = 500;
    private static double seniorDiscountMultiplier = 0.75;


    public static double getJuniorPrice() {
        return junior;
    }

    public static double getSeniorPrice() {
        return senior;
    }

    public static double getPassivePrice() {
        return passive;
    }

    public static double getSeniorDiscountPrice() {
        return senior*seniorDiscountMultiplier;
    }

    public static void setJuniorPrice(double price) {
        if (price > 0) {
            junior = price;
        }else System.out.println("Prisen skal være over 0");
    }

    public static void setSeniorPrice(double price) {
        if (price > 0) {
            senior = price;
        } else System.out.println("Prisen skal være over 0");
    }

    public static void setPassivePrice(double price) {
        if (price > 0) {
            passive = price;
        } else System.out.println("Prisen skal være over 0");
    }

    public static void setSeniorDiscountMultiplier(double price) {
        if (price > 0 && price < 1) {
            seniorDiscountMultiplier = price;
        } else System.out.println("Rabatten skal være over 0% og under 100%");
    }



    public double getSeniorDiscountMultiplier() {
        return seniorDiscountMultiplier;
    }
}


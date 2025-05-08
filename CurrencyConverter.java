import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        // Predefined exchange rates (example rates, these can be updated)
        double usdToInr = 82.0;  // Example: 1 USD = 82 INR
        double usdToEur = 0.93;  // Example: 1 USD = 0.93 EUR
        double eurToInr = 88.0;  // Example: 1 EUR = 88 INR

        // Taking input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Currency Converter!");
        
        System.out.print("Enter the base currency (USD, EUR): ");
        String baseCurrency = scanner.nextLine().toUpperCase();
        
        System.out.print("Enter the target currency (USD, EUR, INR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();
        
        System.out.print("Enter the amount to convert: ");
        double amount = scanner.nextDouble();
        
        // Perform the conversion based on user input
        double convertedAmount = 0.0;
        String result = "";
        
        if (baseCurrency.equals("USD") && targetCurrency.equals("INR")) {
            convertedAmount = amount * usdToInr;
            result = amount + " USD = " + convertedAmount + " INR";
        } else if (baseCurrency.equals("USD") && targetCurrency.equals("EUR")) {
            convertedAmount = amount * usdToEur;
            result = amount + " USD = " + convertedAmount + " EUR";
        } else if (baseCurrency.equals("EUR") && targetCurrency.equals("INR")) {
            convertedAmount = amount * eurToInr;
            result = amount + " EUR = " + convertedAmount + " INR";
        } else {
            result = "Conversion rate not available for the selected currencies.";
        }
        
        // Display the result
        System.out.println(result);
        
        scanner.close();
    }
}

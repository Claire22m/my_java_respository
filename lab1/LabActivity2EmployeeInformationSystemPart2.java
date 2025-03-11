import java.util.Scanner;

public class LabActivity2EmployeeInformationSystem {
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt for employee details
        System.out.print("Enter Employee's First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Employee's Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Employee's Age: ");
        int age = scanner.nextInt();

        System.out.print("Enter Number of Hours Worked in a Day: ");
        double hoursWorked = scanner.nextDouble();

        System.out.print("Enter Hourly Wage: ");
        double hourlyWage = scanner.nextDouble();

        // Compute necessary details
        String fullName = (lastName + " " + firstName).toUpperCase();
        int retirementAge = 65;
        int yearsToRetirement = Math.abs(retirementAge - age);

        // Daily wage calculation
        double dailyWage = Math.round(hoursWorked * hourlyWage);
        
        // Weekly, Monthly, and Yearly wage calculations
        double weeklyWage = dailyWage * 5;
        double monthlyWage = weeklyWage * 4;
        double grossYearlyWage = monthlyWage * 12;

        // Net yearly wage calculation
        double benefits = 1500.00;
        double taxRate = 0.32;
        double netYearlyWage = grossYearlyWage - (grossYearlyWage * taxRate) - benefits;

        // Output results
        System.out.println("\nEmployee Full Name: " + fullName);
        System.out.println("Years to Retirement: " + yearsToRetirement);
        System.out.println("Daily Wage: Php " + dailyWage);
        System.out.println("Weekly Wage: Php " + weeklyWage);
        System.out.println("Monthly Wage: Php " + monthlyWage);
        System.out.println("Gross Yearly Wage: Php " + grossYearlyWage);
        System.out.println("Net Yearly Wage: Php " + netYearlyWage);
        
        // Close the scanner
        scanner.close();
    }
}

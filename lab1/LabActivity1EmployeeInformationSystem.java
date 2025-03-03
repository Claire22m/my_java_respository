import java.util.Scanner;

public class EmployeeInformationSystem {
    public static void main(String[] args) {
        // Create a Scanner object to read input
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user for employee's first name
        System.out.print("Enter employee's first name: ");
        String firstName = scanner.nextLine();
        
        // Prompt the user for employee's last name
        System.out.print("Enter employee's last name: ");
        String lastName = scanner.nextLine();
        
        // Prompt the user for employee's age
        System.out.print("Enter employee's age: ");
        int age = scanner.nextInt();
        
        // Prompt the user for number of hours worked in a day
        System.out.print("Enter number of hours worked in a day: ");
        double hoursWorked = scanner.nextDouble();
        
        // Prompt the user for hourly wage
        System.out.print("Enter hourly wage: ");
        double hourlyWage = scanner.nextDouble();
        
        // Compute the employee's full name
        String fullName = firstName + " " + lastName;
        
        // Compute the employee's daily wage
        double dailyWage = hoursWorked * hourlyWage;
        
        // Output the results
        System.out.println("\nEmployee Information:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Age: " + age);
        System.out.printf("Daily Wage: $%.2f\n", dailyWage);
        
        // Close the scanner to avoid memory leaks
        scanner.close();
    }
}

import java.util.Scanner;

public class LabActivity3ConditionalStatement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt for employee's information
        System.out.print("Enter Employee's First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Employee's Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter Employee's Age: ");
        int age = scanner.nextInt();
        
        System.out.print("Enter Employee's Job Role (1-Manager, 2-Supervisor, 3-Staff, 4-Intern): ");
        int jobRole = scanner.nextInt();
        
        System.out.print("Enter Number of Hours Worked in a Day: ");
        double hoursWorked = scanner.nextDouble();
        
        System.out.print("Enter Hourly Wage: ");
        double hourlyWage = scanner.nextDouble();
        
        // Validate hours worked
        if (hoursWorked > 24) {
            System.out.println("Number of hours worked cannot exceed 24 hours");
            return;
        }

        if (hoursWorked <= 0) {
            System.out.println("Wrong input on daily work hours");
            return;
        }

        // Check age restrictions
        if (age < 18) {
            System.out.println("Minors are not allowed");
            return;
        }

        if (age >= 65) {
            System.out.println("Senior Citizens are not allowed");
            return;
        }

        // Determine job role
        String role;
        switch (jobRole) {
            case 1:
                role = "Manager";
                break;
            case 2:
                role = "Supervisor";
                break;
            case 3:
                role = "Staff";
                break;
            case 4:
                role = "Intern";
                break;
            default:
                role = "Undefined";
                break;
        }

        // Calculate yearly salary
        double dailySalary = hoursWorked * hourlyWage;
        double yearlySalary = dailySalary * 365; // Assuming working days every day for simplification
        
        double governmentBenefits = 1500;
        double taxDeduction = (yearlySalary > 250000) ? yearlySalary * 0.32 : 0;

        double netSalary = yearlySalary - governmentBenefits - taxDeduction;

        // Output the results
        System.out.println("\nEmployee Information:");
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Role: " + role);
        System.out.println("Yearly Salary: $" + yearlySalary);
        System.out.println("Tax Deduction: $" + taxDeduction);
        System.out.println("Net Salary after deductions: $" + netSalary);
        
        scanner.close();
    }
          }

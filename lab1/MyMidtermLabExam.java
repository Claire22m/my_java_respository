import java.util.Scanner;

public class ITTicketProcessingSystem {
    private static final int MAX_TICKETS = 5;
    private static Ticket[] tickets = new Ticket[MAX_TICKETS];
    private static int ticketCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            
            switch (choice) {
                case 1:
                    addTicket(scanner);
                    break;
                case 2:
                    updateTicketStatus(scanner);
                    break;
                case 3:
                    showTickets();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    System.out.println("Exiting the program. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid selection! Please choose an option from the menu.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== IT Ticket Processing System =====");
        System.out.println("1. Add Ticket");
        System.out.println("2. Update Ticket Status");
        System.out.println("3. Show All Tickets");
        System.out.println("4. Generate Report");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");
    }

    private static void addTicket(Scanner scanner) {
        if (ticketCount >= MAX_TICKETS) {
            System.out.println("Ticket limit reached! Cannot add more tickets.");
            return;
        }

        System.out.print("Enter issue description: ");
        String description = scanner.nextLine();
        System.out.print("Enter urgency level (Low, Medium, High): ");
        String urgency = scanner.nextLine();
        
        tickets[ticketCount] = new Ticket(description, urgency, "Pending");
        ticketCount++;
        System.out.println("Ticket added successfully! Ticket Number: " + ticketCount);
    }

    private static void updateTicketStatus(Scanner scanner) {
        System.out.print("Enter ticket number to update (1 to " + ticketCount + "): ");
        int ticketNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (ticketNumber < 1 || ticketNumber > ticketCount) {
            System.out.println("Invalid ticket number!");
            return;
        }

        Ticket ticket = tickets[ticketNumber - 1];
        if (ticket.getStatus().equals("Resolved")) {
            System.out.println("Cannot update a resolved ticket.");
            return;
        }

        System.out.print("Enter new status (In Progress or Resolved): ");
        String newStatus = scanner.nextLine();

        if (newStatus.equalsIgnoreCase("In Progress") || newStatus.equalsIgnoreCase("Resolved")) {
            ticket.setStatus(newStatus);
            System.out.println("Ticket status updated successfully!");
        } else {
            System.out.println("Invalid status update! Please enter 'In Progress' or 'Resolved'.");
        }
    }

    private static void showTickets() {
        System.out.println("\n===== All Tickets =====");
        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = tickets[i];
            System.out.println("Ticket Number: " + (i + 1) + 
                               ", Description: " + ticket.getDescription() + 
                               ", Urgency: " + ticket.getUrgency() + 
                               ", Status: " + ticket.getStatus());
        }
    }

    private static void generateReport() {
        int pendingCount = 0;
        int resolvedCount = 0;

        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].getStatus().equals("Pending") || tickets[i].getStatus().equals("In Progress")) {
                pendingCount++;
            } else if (tickets[i].getStatus().equals("Resolved")) {
                resolvedCount++;
            }
        }

        System.out.println("\n===== Ticket Report =====");
        System.out.println("Total Tickets: " + ticketCount);
        System.out.println("Total Pending Tickets (including In Progress): " + pendingCount);
        System.out.println("Total Resolved Tickets: " + resolvedCount);
    }
}

class Ticket {
    private String description;
    private String urgency;
    private String status;

    public Ticket(String description, String urgency, String status) {
        this.description = description;
        this.urgency = urgency;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

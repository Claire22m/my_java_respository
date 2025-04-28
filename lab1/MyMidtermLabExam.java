import java.util.Scanner;

class Ticket {
    private String description;
    private String urgency;
    private String status;

    public Ticket(String description, String urgency) {
        this.description = description;
        this.urgency = urgency;
        this.status = "Pending"; // Default status
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

    public void updateStatus(String newStatus) {
        this.status = newStatus; // Update ticket status
    }

    @Override
    public String toString() {
        return "[" + urgency + "] " + description + " - Status: " + status; // Display ticket info
    }
}

public class ITTicketSystem {

    private static final int MAX_TICKETS = 5; // Define the maximum tickets
    private Ticket[] tickets; // Array to store tickets
    private int ticketCount; // Counter for stored tickets
    private Scanner scanner;

    public ITTicketSystem() {
        tickets = new Ticket[MAX_TICKETS]; // Initialize the ticket array
        ticketCount = 0; // Initially no tickets
        scanner = new Scanner(System.in); // Setup scanner for input
    }

    public static void main(String[] args) {
        ITTicketSystem system = new ITTicketSystem();
        system.run(); // Start the system
    }

    public void run() {
        while (true) {
            displayMenu(); // Show the menu
            int choice = scanner.nextInt(); // Get user choice
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addTicket(); // Add a new ticket
                    break;
                case 2:
                    updateTicketStatus(); // Update ticket status
                    break;
                case 3:
                    showTickets(); // Show all tickets
                    break;
                case 4:
                    generateReport(); // Generate ticket report
                    break;
                case 5:
                    exit(); // Exit the program
                    return;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== IT Ticket System ===");
        System.out.println("1. Add Ticket");
        System.out.println("2. Update Ticket Status");
        System.out.println("3. Show All Tickets");
        System.out.println("4. Generate Report");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    private void addTicket() {
        if (ticketCount >= MAX_TICKETS) {
            System.out.println("Ticket limit reached. Cannot add more tickets.");
            return;
        }

        System.out.print("Enter issue description: ");
        String description = scanner.nextLine(); // Read ticket description
        System.out.print("Enter urgency level (Low / Medium / High): ");
        String urgency = scanner.nextLine(); // Read urgency level

        tickets[ticketCount] = new Ticket(description, urgency); // Create and store the new ticket
        ticketCount++; // Increment ticket count
        System.out.println("Ticket successfully added!");
    }

    private void updateTicketStatus() {
        showTickets(); // Show existing tickets

        if (ticketCount == 0) {
            System.out.println("No tickets to update.");
            return; // Exit if there are no tickets
        }

        System.out.print("Enter ticket number to update: ");
        int ticketNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (ticketNumber < 1 || ticketNumber > ticketCount) {
            System.out.println("Invalid ticket number.");
            return; // Handle invalid ticket number
        }

        Ticket ticket = tickets[ticketNumber - 1]; // Get the selected ticket
        if (ticket.getStatus().equals("Resolved")) {
            System.out.println("Cannot update a resolved ticket.");
            return; // Prevent updating resolved tickets
        }

        System.out.print("Enter new status (In Progress / Resolved): ");
        String newStatus = scanner.nextLine();
        if (newStatus.equals("In Progress") || newStatus.equals("Resolved")) {
            ticket.updateStatus(newStatus); // Update ticket status
            System.out.println("Ticket status updated.");
        } else {
            System.out.println("Invalid status. Please enter 'In Progress' or 'Resolved'."); // Handle invalid status input
        }
    }

    private void showTickets() {
        System.out.println("\n=== All Tickets ===");
        for (int i = 0; i < ticketCount; i++) {
            System.out.println((i + 1) + ". " + tickets[i].toString()); // Display each ticket
        }
        if (ticketCount == 0) {
            System.out.println("No tickets available."); // Handle no available tickets
        }
    }

    private void generateReport() {
        int totalTickets = ticketCount;
        int pendingTickets = 0;
        int resolvedTickets = 0;

        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].getStatus().equals("Pending") || tickets[i].getStatus().equals("In Progress")) {
                pendingTickets++; // Count pending tickets
            } else if (tickets[i].getStatus().equals("Resolved")) {
                resolvedTickets++; // Count resolved tickets
            }
        }

        System.out.println("\n=== Ticket Report ===");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Pending Tickets (including In Progress): " + pendingTickets);
        System.out.println("Resolved Tickets: " + resolvedTickets); // Display report
    }

    private void exit() {
        System.out.println("Exiting the system. Goodbye!"); // Exit message
    }
}

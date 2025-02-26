import math

def compute_surface_area(R, r):
    """Calculate surface area of a torus."""
    return (2 * math.pi * R) * (2 * math.pi * r)

def compute_volume(R, r):
    """Calculate volume of a torus."""
    return (2 * math.pi * R) * (math.pi * (r ** 2))

def main():
    while True:
        print("Torus Calculator:")
        print("1. Compute Surface Area")
        print("2. Compute Volume")
        choice = input("Select an option (1-2): ")

        if choice.lower() == 'q':
            print("Exiting the program.")
            break
        
        if choice not in ['1', '2']:
            print("Invalid choice. Please choose 1 or 2.")
            continue
        
        try:
            R = float(input("Enter the major radius (R): "))
            r = float(input("Enter the minor radius (r): "))
            if R <= 0 or r <= 0:
                print("Both radii must be greater than zero. Please try again.")
                continue
            if r > R:
                print("Minor radius (r) must not be greater than major radius (R).")
                continue
        except ValueError:
            print("Invalid input. Please enter numeric values for radii.")
            continue

        if choice == '1':
            area = compute_surface_area(R, r)
            print(f"The Surface Area of the torus is: {area:.4f}")
        elif choice == '2':
            volume = compute_volume(R, r)
            print(f"The Volume of the torus is: {volume:.4f}")

        again = input("Do you want to compute another value? (yes/no): ").strip().lower()
        if again not in ['yes', 'y']:
            print("Exiting the program. Goodbye!")
            break

if __name__ == "__main__":
    main()

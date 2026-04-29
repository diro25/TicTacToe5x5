
// Import Scanner class to take input from user
import java.util.Scanner;

public class StudentMarks {
    public static void main(String[] args) {
        // Create a Scanner object to read input
        Scanner input = new Scanner(System.in);

        // Taking student details as input
        System.out.print("Enter student name: ");
        String name = input.nextLine(); // Read string input

        System.out.print("Enter student ID: ");
        int id = input.nextInt(); // Read integer input

        System.out.print("Enter marks in Subject 1: ");
        double sub1 = input.nextDouble(); // Read double input

        System.out.print("Enter marks in Subject 2: ");
        double sub2 = input.nextDouble();

        System.out.print("Enter marks in Subject 3: ");
        double sub3 = input.nextDouble();

        // Calculate total marks
        double total = sub1 + sub2 + sub3;

        // Calculate average marks (total divided by number of subjects)
        double average = total / 3;

        // Display the results
        System.out.println("\n--- Student Report ---");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Total Marks: " + total);
        System.out.println("Average Marks: " + average);

        // Close the scanner to prevent resource leak
        input.close();
    }
}
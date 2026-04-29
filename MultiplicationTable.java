import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Ask user for the number
        System.out.print("Enter a number to display its multiplication table: ");
        int number = input.nextInt();

        System.out.println("\nMultiplication Table of " + number + ":");
        System.out.println("--------------------------------");

        // For loop runs from 1 to 10
        for (int i = 1; i <= number; i++) {
            for (int j = 1; j <= number; j++) {
                {
                    {
                        System.out.print(i + "×" + j + "=" + (i * j) + "\t");
                    }
                    System.out.println();
                }

            }

        }

        input.close();
    }
}
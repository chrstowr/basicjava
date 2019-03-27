package instrumentsearch.ui;

import java.util.Scanner;

/*
    Christopher Torres
    12/13/2018

    The console class is used to get input from the user.
*/

public class Console {
    
    private static Scanner sc = new Scanner(System.in);
    
    //Get Integer input from user
    public static int getInt(String prompt) {
        int i = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                i = sc.nextInt();
                isValid = true;
            } else {
                System.out.println("Error! Invalid integer value. Try again.");
            }
            sc.nextLine();  // discard any other data entered on the line
        }
        return i;
    }
}
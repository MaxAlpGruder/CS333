package Project_2;

import java.util.Scanner;

public class pr2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();                   //5; // 
        sc.nextLine();
        String outlets_String = sc.nextLine();  //"BE DC 72 AA B4"; // 
        String lamps_String = sc.nextLine();    //"AA B4 BE DC 72"; // 

        String[] old_lamps = lamps_String.split(" ");
        String[] old_outlets = outlets_String.split(" ");

        String[] lamps = new String[a + 1];
        String[] outlets = new String[a + 1];

        lamps[0] = "asdfg";
        outlets[0] = "zxcvb";
        for (int i = 0; i < outlets.length - 1; i++) {
            lamps[i + 1] = old_lamps[i];
            outlets[i + 1] = old_outlets[i];
        }

        dynamic_solution(lamps, outlets);
    }

    static void dynamic_solution(String[] lamps, String[] outlets) {
        int size = lamps.length;
        // tabulation array
        int[][] tabulation = new int[size][size];

        // for loop
        for (int i = 0; i < tabulation.length; i++) {
            for (int j = 0; j < tabulation.length; j++) {
                // fill the edges with 0
                if (i == 0) {
                    tabulation[i][j] = 0;
                }
                if (j == 0) {
                    tabulation[i][j] = 0;
                }

                if (outlets[i].equals(lamps[j]) && i > 0 && j > 0) {
                    tabulation[i][j] = tabulation[i][j - 1] + 1;
                }
                if (!outlets[i].equals(lamps[j]) && i > 0 && j > 0) {
                    tabulation[i][j] = Math.max(tabulation[i][j - 1], tabulation[i - 1][j]);
                }
            }
        }

        System.out.println(tabulation[size - 1][size - 1]);

        // calculating the codes of the lamps we turned on
        String[] turned_on_lamps = new String[tabulation[size - 1][size - 1]];
        int counter = 0;
        int j = tabulation.length;
        for (int i = tabulation.length - 1; i >= 0; i--) {
            if (tabulation[i][j - 1] != 0 && tabulation[i][j - 1] != tabulation[i - 1][j - 1]) {
                turned_on_lamps[counter] = outlets[i];
                counter++;
                j = j - tabulation[i - 1][j - 1];
            }
        }
        for (int i = 0; i < turned_on_lamps.length; i++) {
            System.out.print(turned_on_lamps[turned_on_lamps.length - i - 1] + " ");
        }

    }

}

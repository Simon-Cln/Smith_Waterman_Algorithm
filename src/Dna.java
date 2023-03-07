

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;

import static java.lang.Character.getType;
import static java.lang.Character.isUpperCase;


public class Dna extends DefaultTableCellRenderer {
    File doc = new File("DNA_data.txt");
    ArrayList<String> fill = new ArrayList<>();
    ArrayList<String> gattaca = new ArrayList<>();
    ArrayList<String> aminoAcids = new ArrayList<>();
    ArrayList<String> aminoAcids2 = new ArrayList<>();
    JTextField textBox = new JTextField();
    private static final long serialVersionUID = 1L;


    public Dna() {
    }

    public ArrayList<String> retrieveAminoAcids() throws IOException {

            try (BufferedReader reader = new BufferedReader(new FileReader(doc))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fill.add(line);
                }
                for (int i = 0; i < fill.size(); i++) {
                    if (fill.get(i).length() == 10) { //for each line in the file, if the length of the line is 10, add it to the gattaca arraylist
                        gattaca.add(fill.get(i));
                    }
                }

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Null pointer exception"); //if the file is empty, print this
            } finally {
                System.out.println("\nBlock executed"); //print this when the block is executed
            }
            return gattaca; //return the arraylist

    }

    public void displayGattaca(){
        System.out.println("\n"); // print out the gattaca arraylist
        System.out.print(" [ ");
        for (String s : gattaca) {
            System.out.print(s + " ");
        }
        System.out.println(" ] ");
    }

    public void SmithWaterman(String firstGattaca, String secondGattaca){
        int [][] matrix = new int[firstGattaca.length()+1][secondGattaca.length()+1]; //create a matrix with the length of the first gattaca sequence + 1 and the length of the second gattaca sequence + 1
        for (int i = 0; i < firstGattaca.length()+1; i++) {
            matrix[i][0] = 0; //fill the first column with 0s
        }
        for (int i = 0; i < secondGattaca.length()+1; i++) {
            matrix[0][i] = 0; //fill the first row with 0s
        }
        int max = 0;
        int maxCoordI = 0;
        int maxCoordJ = 0;

        for (int i = 1; i < firstGattaca.length()+1; i++) {
            for (int j = 1; j < secondGattaca.length()+1; j++) {
                if (firstGattaca.charAt(i-1) == secondGattaca.charAt(j-1)){
                    matrix[i][j] = matrix[i-1][j-1] + 1; //if the characters at the same position in the two sequences are the same, add 1 to the value of the cell in the matrix
                }
                else{
                    matrix[i][j] = 0; //if the characters at the same position in the two sequences are not the same, set the value of the cell in the matrix to 0
                }
                if (matrix[i][j] > max){
                    max = matrix[i][j];
                    maxCoordI = i; //set the max value to the value of the cell in the matrix
                    maxCoordJ = j; //set the max value to the value of the cell in the matrix
                }
            }
        }

        System.out.println("\n Now that matrix is filled, we can find the longest common substring");
        System.out.println("The maximum score is : " + max); //print out the max value
        System.out.println("The maximum score is at : " + (maxCoordI+1) + " " + (maxCoordJ+1)); //print out the coordinates of the max value
        System.out.println("\n");
        System.out.println("The matrix is : ");

        for (int i = 0; i < firstGattaca.length()+1; i++) {
            for (int j = 0; j < secondGattaca.length()+1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("\n");
        }
        System.out.println("\n");
        System.out.println("The alignment for these 2 amino acids is : ");
        String first = ""; //create a string to store the sequence alignement
        String second = "";
        int i = maxCoordI;
        int j = maxCoordJ;
        while (matrix[i][j] != 0){
            first = firstGattaca.charAt(i-1) + first; //add the characters at the same position in the two sequences to the string
            second = secondGattaca.charAt(j-1) + second;
            i--;
            j--;
        }
        System.out.println(first);

        final JFrame frame = new JFrame("ADN Alignment"); //create a new frame

        String[][] columns = new String[11][11]; //create a 2D array to store the values of the matrix

        Object[][] data = new Object[firstGattaca.length()+1][secondGattaca.length()+1]; //create a 2D array to store the values of the matrix with Object type
        data[0][0] = " ";
       for(int k = 1; k < firstGattaca.length()+1; k++){
            data[0][k] = String.valueOf(secondGattaca.charAt((k-1)));
       }
       for (int z = 1; z < secondGattaca.length()+1; z++){
           data[z][0] = String.valueOf(firstGattaca.charAt((z-1)));}

       for (int r = 1; r < firstGattaca.length() + 1; r++) {
            for (int s = 1; s < secondGattaca.length() + 1; s++) {
                data[r][s] = String.valueOf(matrix[r][s]);
            }
        }



        JTable table = new JTable(data, columns); //create a new table with the 2D array as data and where columns are the
        table.setPreferredScrollableViewportSize(new Dimension(500, 70)); //set the size of the table
        for (int r = 1; r < firstGattaca.length()+1; r++) {
            for (int s = 1; s < secondGattaca.length()+1; s++) {
                if (matrix[r-1][s-1] == max){
                    table.setValueAt("<html><font color='red'>" + matrix[r-1][s-1] + "</font></html>", r, s); //if the value of the cell in the matrix is the max value, set the color of the cell to red
                }
            }
        }
        for (int r = 1; r < firstGattaca.length()+1; r++) {
            if ((matrix[0][r-1] == 'G') || (matrix[0][r-1] == 'A') || (matrix[0][r-1] == 'T') || (matrix[0][r-1] == 'C')){
                    table.setFont(new Font("Dialog", Font.BOLD, 25)); //if the value of the cell in the matrix is the max value, set the color of the cell to red
            }
        }




        JScrollPane scroll = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JLabel labelHead = new JLabel("ADN Alignment");
        labelHead.setFont(new Font("Dialog",Font.BOLD,20));
        labelHead.setForeground(Color.red);

        frame.getContentPane().add(labelHead,BorderLayout.PAGE_START);
        //ajouter la table au frame
        frame.getContentPane().add(scroll,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 400);
        frame.setVisible(true);

    }
}


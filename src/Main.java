
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class Main  {
    public static void main(String[] args) throws IOException {
        Runnable r = new Gui(); //create a new thread
        Thread t = new Thread(r);
        t.start(); //start the thread
    }
}




class Gui implements Runnable{


    @Override
    public void run() //this is the code that will be executed in the new thread
    {
        int n = 0;
        Dna dna = new Dna();
        try {
            dna.retrieveAminoAcids(); //retrieve the amino acids from the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        dna.displayGattaca(); //display the amino acids
        try{
            while(n < dna.gattaca.size()){

                System.out.println("\n\nNEW COMPARISON ");
                System.out.println("Comparing DNA sequence number " + (n+1) + " with DNA sequence number " + (n+2) + ", that is to say " + dna.gattaca.get(n) + " with " + dna.gattaca.get(n+1));
                dna.SmithWaterman(dna.gattaca.get(n), dna.gattaca.get(n+1));
                Thread.sleep(1000); //wait 5 seconds between each display of the amino acids (GUI is updated every 5 seconds)
                n++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds");
        } catch (InterruptedException e) { //goes with the Thread.sleep
            e.printStackTrace();
        } finally {
            System.out.println("Number of comparisons: " + n);
        }
    }
}


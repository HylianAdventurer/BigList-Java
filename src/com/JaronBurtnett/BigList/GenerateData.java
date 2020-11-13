package com.JaronBurtnett.BigList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class GenerateData {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        PrintWriter file = null;
        Integer n = null, max = null, min = null;
        Random rng = null;
        for(int i = 0; i < args.length; i++) {
            switch(args[i]) {
                case "-file":
                    file = new PrintWriter(new File(args[i+1]));
                    break;
                case "-n":
                    n = Integer.parseInt(args[i+1]);
                    break;
                case "-max":
                    max = Integer.parseInt(args[i+1]);
                    break;
                case "-min":
                    min = Integer.parseInt(args[i+1]);
                    break;
                case "-s":
                case "-seed":
                    rng = new Random(Long.parseLong(args[i+1]));
                    break;
            }
        }
        if(file == null) {
            System.out.print("Enter filename: ");
            file = new PrintWriter(new File(in.next()));
        }
        if(n == null) {
            System.out.print("Enter the number of integers: ");
            n = in.nextInt();
        }
        if(max == null)
            max = 1000;
        if(min == null)
            min = 0;
        if(rng == null)
            rng = new Random();

        for(int i = 0; i < n; i++)
            file.println(rng.nextInt(max)+min);

        file.close();
    }
}

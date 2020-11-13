package com.JaronBurtnett.BigList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Thread {
    static int[] M, N, P, Q, R; // Static Arrays for the five lists
    static Integer num_threads = null; // The number of threads
    static int next = 0;

    public static synchronized int next() {
        return next++;
    }

    Main m1, m2;

    public static void main(String[] args){
        try {
            setup(args);
            long time = System.nanoTime();
            new Main(num_threads);
            time = System.nanoTime() - time;
            System.out.println("Time elapsed: " + time);
            finish();
        }
        catch(FileNotFoundException e) {
            System.out.println("Failed to open file.");
            e.printStackTrace();
        }
        return;
    }

    public Main(int n) {
        n--;
        if(n > 1)
            m1 = new Main((n / 2) + 1);
        if(n > 2)
            m2 = new Main((n / 2) + 1);
        if(n > 1)
            m1.start();
        if(n > 2)
            m2.start();
        if(num_threads == n + 1)
            run();
    }

    public static void setup(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in), mFile = null, nFile = null;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mFile":
                    mFile = new Scanner(new File(args[i + 1]));
                    break;
                case "-nFile":
                    nFile = new Scanner(new File(args[i + 1]));
                case "-n":
                    num_threads = Integer.parseInt(args[i + 1]);
                    break;
            }
        }
        if (num_threads == null) {
            System.out.print("Enter the number of threads: ");
            num_threads = in.nextInt();
            in.nextLine();
        }
        if (mFile == null) {
            System.out.print("Enter filename for list M: ");
            mFile = new Scanner(new File(in.next()));
            in.nextLine();
        }
        if (nFile == null) {
            System.out.print("Enter filename for list N: ");
            nFile = new Scanner(new File(in.next()));
            in.nextLine();
        }
        List<Integer> mList = new ArrayList<>(), nList = new ArrayList<>();
        while (mFile.hasNextInt() && nFile.hasNextInt()) {
            mList.add(mFile.nextInt());
            nList.add(nFile.nextInt());
        }

        M = new int[mList.size()];
        N = new int[mList.size()];
        P = new int[mList.size()];
        Q = new int[mList.size()];
        R = new int[mList.size()];

        for (int i = 0; i < mList.size(); i++) {
            M[i] = mList.get(i);
            N[i] = nList.get(i);
        }

        mFile.close();
        nFile.close();
    }

    public void run() {
        int i;
        while((i = next()) < M.length) {
            P[i] = Math.abs(M[i] - N[i]);
            Q[i] = Math.max(M[i], N[i]);
            while(M[i] % Q[i] > 1 || N[i] % Q[i] > 1)
                Q[i]--;
            R[i] = Math.max(M[i], P[i]);
            while(M[i] % R[i] > 1 || P[i] % R[i] > 1)
                R[i]--;
        }

        while((m1 != null && !m1.isAlive()) || (m2 != null && !m2.isAlive()));
    }

    public static void finish() throws FileNotFoundException {
        PrintWriter
                pOut = new PrintWriter(new File("P.dat")),
                qOut = new PrintWriter(new File("Q.dat")),
                rOut = new PrintWriter(new File("R.dat"));

        for(int i = 0; i < M.length; i++) {
            pOut.println(P[i]);
            qOut.println(Q[i]);
            rOut.println(R[i]);
        }
        pOut.close();
        qOut.close();
        rOut.close();
        System.out.println("Finished saving lists");
    }
}

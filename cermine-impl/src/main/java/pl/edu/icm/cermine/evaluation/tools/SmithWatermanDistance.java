package pl.edu.icm.cermine.evaluation.tools;

import java.util.ArrayList;
import java.util.List;


public class SmithWatermanDistance {

    private Double mu;
    private Double delta;

    public SmithWatermanDistance(Double mu, Double delta) {
        this.mu = mu;
        this.delta = delta;
    }

    private double similarityScore(String a, String b) {

        double result;
        if (a.equals(b)) {
            result = 1.;
        } else {
            result = -mu;
        }
        return result;
    }

    private double findArrayMax(double array[]) {

        double max = array[0];            // start with max = first element

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;                    // return highest value in array
    }

    private enum Move {

        OMIT_S1, OMIT_S2, OMIT_BOTH, MATCH, NN
    };

    public Double compare(List<String> s1, List<String> s2) {
        // string s_a=seq_a,s_b=seq_b;
        int N_a = s1.size();                     // get the actual lengths of the sequences
        int N_b = s2.size();

        // initialize H
        double H[][] = new double[N_a + 1][N_b + 1];
        for (int i = 0; i <= N_a; i++) {
            for (int j = 0; j <= N_b; j++) {
                H[i][j] = 0;
            }
        }

        double temp[] = new double[4];
        // here comes the actual algorithm

        for (int i = 1; i <= N_a; i++) {
            for (int j = 1; j <= N_b; j++) {
                temp[0] = H[i - 1][j - 1] + similarityScore(s1.get(i - 1), s2.get(j - 1));
                temp[1] = H[i - 1][j] - delta;
                temp[2] = H[i][j - 1] - delta;
                temp[3] = 0.;
                H[i][j] = findArrayMax(temp);
            }
        }
        // search H for the maximal score
        double H_max = 0.;
        for (int i = 1; i <= N_a; i++) {
            for (int j = 1; j <= N_b; j++) {
                if (H[i][j] > H_max) {
                    H_max = H[i][j];
                }
            }
        }

        return H_max;
    }

    public static void main(String[] args) {
        SmithWatermanDistance smith = new SmithWatermanDistance(.0, .0);
        List<String> s1 = new ArrayList<String>();
        List<String> s2 = new ArrayList<String>();
        s1.add("1");
        s1.add("2");
        s1.add("3");
        s1.add("4");
        s1.add("5");
        s1.add("6");
        
        s2.add("1");
        s2.add("2");
        s2.add("1");
        s2.add("4");
        s2.add("5");
        s2.add("7");
        System.out.println(smith.compare(s1, s2));
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void hashCode(String inputFileName, String outputFileName) throws Exception {
        // Read input file
        File inputFile = new File("input/" + inputFileName);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String firstLine = reader.readLine();
        String[] firstLineItems = firstLine.split(" ");
        int rowCount = Integer.parseInt(firstLineItems[0]);
        int colCount = Integer.parseInt(firstLineItems[1]);
        int minIngredient = Integer.parseInt(firstLineItems[2]);
        int maxArea = Integer.parseInt(firstLineItems[3]);
        // Pizza contains every row of the pizza as a String
        List<String> pizza = new ArrayList<>();
        for (int i=0; i<rowCount; i++) {
            pizza.add(reader.readLine());
        }
        reader.close();

        // Greedy solution from video 'Get Ready for Hash Code 2018'
        // Looks only horizontally
        List<String> result = new ArrayList<>();
        for (int r=0; r<rowCount; r++) {
            int beg = 0;
            int end = 0;
            int mushrooms = 0;
            int tomatos = 0;
            while (end < colCount) {
                if (pizza.get(r).charAt(end) == 'M') mushrooms++;
                else if (pizza.get(r).charAt(end) == 'T') tomatos++;
                end++;
                if (end - beg > maxArea) {
                    if (pizza.get(r).charAt(beg) == 'M') mushrooms--;
                    else if (pizza.get(r).charAt(beg) == 'T') tomatos--;
                    beg++;
                }
                if (end - beg <= maxArea && mushrooms >= minIngredient && tomatos >= minIngredient) {
                    result.add(r + " " + beg + " " + r + " " + (end-1));
                    beg = end;
                    tomatos = 0;
                    mushrooms = 0;
                }
            }
        }

        // Create output file
        BufferedWriter writer = new BufferedWriter(new FileWriter("output/" + outputFileName));
        writer.write("" + result.size());
        writer.newLine();
        for (String s : result) {
            writer.write(s);
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) {
        try {
            hashCode("a_example.in", "a_output");
            hashCode("b_small.in", "b_output");
            hashCode("c_medium.in", "c_output");
            hashCode("d_big.in", "d_output");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

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
        int photoCount = Integer.parseInt(firstLineItems[0]);
        // Pizza contains every row of the pizza as a String
        List<String> photos = new ArrayList<>();
        for (int i=0; i<photoCount; i++) {
            photos.add(reader.readLine());
        }
        reader.close();

        // Greedy solution from video 'Get Ready for Hash Code 2018'
        // Looks only horizontally
        List<String> result = new ArrayList<>();
        for (int r=0; r<photoCount; r++) {

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
            hashCode("a_example.txt", "a_output");
            hashCode("b_lovely_landscapes.in", "b_output");
            hashCode("c_memorable_moments.in", "c_output");
            hashCode("d_pet_pictures.in", "d_output");
            hashCode("e_shiny_selfies.in", "e_output");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

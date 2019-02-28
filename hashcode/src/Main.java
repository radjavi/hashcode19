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

        // Solution
        List<String> result = new ArrayList<>();
        int temp = -1;
        for (int id=0; id<photoCount; id++) {
            String[] photoSplit = photos.get(id).split(" ");
            String type = photoSplit[0]; // V or H

            if (type.equals("H")) result.add("" + id);
            if (type.equals("V")) {
                if (temp < 0) temp = id;
                else {
                    result.add("" + temp + " " + id);
                    temp = -1;
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
            hashCode("a_example.txt", "a_output");
            hashCode("b_lovely_landscapes.txt", "b_output");
            hashCode("c_memorable_moments.txt", "c_output");
            hashCode("d_pet_pictures.txt", "d_output");
            hashCode("e_shiny_selfies.txt", "e_output");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

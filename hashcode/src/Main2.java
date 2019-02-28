import java.io.*;
import java.util.*;

public class Main2 {
    static class Slide {
        int id1 = -1;
        int id2 = -1;
        Slide next = null;
        Set<String> tags;
        //Slide prev = null;

        public Slide(int id, String[] tags) {
            id1 = id;
            this.tags = new HashSet<>(Arrays.asList(tags));
        }

        public Slide(int id1, int id2, String[] tags1, String[] tags2) {
            this.id1 = id1;
            this.id2 = id2;
            Set<String> set1 = new HashSet<>(Arrays.asList(tags1));
            Set<String> set2 = new HashSet<>(Arrays.asList(tags2));
            set1.addAll(set2);
            tags = set1;
        }

        void append(Slide end) {
            Slide s = this;
            while (s.next != null) {
                s = s.next;
            }
            s.next = end;
        }
    }

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
        List<Slide> result2 = new ArrayList<>();
        Slide top = null;
        String[] tagTemp = {};
        int temp = -1;
        for (int id=0; id<photoCount; id++) {
            String[] photoSplit = photos.get(id).split(" ");
            String type = photoSplit[0]; // V or H

            if (type.equals("H")) {
                if (top == null) top = new Slide(id, photoSplit);
                else top.append(new Slide(id, photoSplit));
                result.add("" + id);
            }
            if (type.equals("V")) {
                if (temp < 0) {
                    temp = id;
                    tagTemp = photoSplit;
                }
                else {
                    if (top == null) top = new Slide(temp, id, photoSplit, tagTemp);
                    else top.append(new Slide(temp, id, photoSplit, tagTemp));
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

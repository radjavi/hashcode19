import java.io.*;
import java.util.*;

public class Main3 {
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
        List<Slide> result = new ArrayList<>();
        Slide top = null;
        String[] tagTemp = {};
        int temp = -1;
        for (int id=0; id<photoCount; id++) {
            String[] photoSplit = photos.get(id).split(" ");
            String[] tags = Arrays.copyOfRange(photoSplit, 2, photoSplit.length-1);
            String type = photoSplit[0]; // V or H

            if (type.equals("H")) {
                result.add(new Slide(id, tags));
            }
            if (type.equals("V")) {
                if (temp < 0) {
                    temp = id;
                    tagTemp = tags;
                }
                else {
                    result.add(new Slide(temp, id, tagTemp, tags));
                    temp = -1;
                }
            }
        }


        for (int i=0; i<result.size(); i++) {
            Slide slide1 = result.get(i);
            int idSwitch = -1;
            int scoreSwitch = -1;
            for (int j=i+1; j<result.size(); j++) {
                Slide slide2 = result.get(j);
                Set<String> a = slide1.tags;
                a.removeAll(slide2.tags);
                int sizeA = a.size();
                Set<String> b = slide2.tags;
                b.removeAll(slide1.tags);
                int sizeB = b.size();
                Set<String> c = slide1.tags;
                c.retainAll(slide2.tags);
                int sizeC = c.size();
                int score = Math.min(Math.min(sizeA, sizeB), sizeC);
                if (score > scoreSwitch) {
                    scoreSwitch = score;
                    idSwitch = j;
                }
            }
            if (idSwitch >= 0 && (idSwitch-i) > 1) {
                Slide sTemp = result.get(idSwitch);
                result.set(idSwitch, result.get(i+1));
                result.set(i+1, sTemp);
            }
        }

        // Create output file
        BufferedWriter writer = new BufferedWriter(new FileWriter("output/" + outputFileName));
        writer.write("" + result.size());
        writer.newLine();
        for (Slide s : result) {
            if (s.id2 < 0) {
                writer.write("" + s.id1);
            } else {
                writer.write("" + s.id1 + " " + s.id2);
            }
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) {
        try {
            //hashCode("a_example.txt", "a_output");
            //hashCode("b_lovely_landscapes.txt", "b_output");
            //hashCode("c_memorable_moments.txt", "c_output");
            hashCode("d_pet_pictures.txt", "d_output");
            //hashCode("e_shiny_selfies.txt", "e_output");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

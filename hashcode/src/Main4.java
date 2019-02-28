import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main4 {
    static class Slide {
        int id1 = -1;
        int id2 = -1;
        Main3.Slide next = null;
        Set<String> tags;
        //Slide prev = null;

        public Slide(int id, String[] tags) {
            id1 = id;
            this.tags = new HashSet<>(Arrays.asList(tags));
        }

        public Slide(int id1, int id2, Set<String> set) {
            this.id1 = id1;
            this.id2 = id2;
            tags = set;
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
        
        String[] photoline;
        String[] temp;
        Map<Integer, String[]> tagMap = new HashMap<>();
        
        List<Slide> result = new ArrayList<>();
        List<Integer> keylist = new ArrayList<>();
        
        for (int id = 0; id<photoCount; id++) {
        	photoline = photos.get(id).split(" ");
            String[] tags = Arrays.copyOfRange(photoline, 2, photoline.length);
            String type = photoline[0]; // V or H
            if (type.equals("H")) result.add(new Slide(id, tags));
        	if(type.equals("V")){
        		String[] temp1 = Arrays.copyOfRange(photoline, 2, photoline.length);
        		tagMap.put(id, temp1);
        		keylist.add(id);
        	}
        }

        //Tags in common
        int prevTagsInCommon = Integer.MAX_VALUE;
        int prevId = -1;

        Set<Integer> taken = new HashSet<>();
    	for (int i=0; i<keylist.size(); i++) {
    	    if (taken.contains(i)) continue;
            for (int j = 0; j < keylist.size(); j++){
                if (i == j) continue;
                if (taken.contains(j)) continue;
                Set<String> set1 = new HashSet<>(Arrays.asList(tagMap.get(keylist.get(i))));
                Set<String> set2 = new HashSet<>(Arrays.asList(tagMap.get(keylist.get(j))));
                int totalSize = set1.size()+set2.size();
                set1.addAll(set2);
                int newSize = set1.size();


                if((totalSize-newSize) < prevTagsInCommon){
                    prevTagsInCommon = totalSize-newSize;
                    prevId = keylist.get(j);
                }
                // All tags unique from both sets
                if(totalSize == newSize){
                    // Make slide of 0 + j
                    Slide s = new Slide(keylist.get(i), keylist.get(j), set1);
                    taken.add(i);
                    taken.add(j);
                    result.add(s);
                    break;

                    // END Make slide
                }
                else if(j == (keylist.size()-1)){
                    Slide s = new Slide(keylist.get(i), keylist.get(j), set1);
                    taken.add(i);
                    taken.add(j);
                    result.add(s);
                    break;

                    // Make slide of 0 + prevId

                    // END Make slide
                }

            }
        }

        // Solution
        for (int i=0; i<result.size(); i++) {
            Slide slide1 = result.get(i);
            int idSwitch = -1;
            int scoreSwitch = -1;
            for (int j=i+1; j<result.size(); j++) {
                Slide slide2 = result.get(j);
                Set<String> a = new HashSet<>(slide1.tags);
                a.removeAll(slide2.tags);
                int sizeA = a.size();
                Set<String> b = new HashSet<>(slide2.tags);
                b.removeAll(slide1.tags);
                int sizeB = b.size();
                Set<String> c = new HashSet<>(slide1.tags);
                c.retainAll(slide2.tags);
                int sizeC = c.size();
                int score = Math.min(Math.min(sizeA, sizeB), sizeC);
                //System.out.println("a:" + sizeA + ",b: " + sizeB + ",c: " + sizeC);
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
            hashCode("a_example.txt", "a_output");
            //hashCode("b_lovely_landscapes.txt", "b_output");
            hashCode("c_memorable_moments.txt", "c_output");
            //hashCode("d_pet_pictures.txt", "d_output");
            //hashCode("e_shiny_selfies.txt", "e_output");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

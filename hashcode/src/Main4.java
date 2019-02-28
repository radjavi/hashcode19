import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main4 {
	
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
        
        List<String> result = new ArrayList<>();
        List<Integer> keylist = new ArrayList<>();
        
        for (int id = 0; id<photoCount; id++) {
        	photoline = photos.get(id).split(" ");
        	if(photoline[0].equals("V")){
        		String[] temp1 = Arrays.copyOfRange(photoline, 2, photoline.length);
        		tagMap.put(id, temp1);
        		keylist.add(id);
        	}
        }
        
        //Tags in common
        int prevTagsInCommon = Integer.MAX_VALUE;
        int prevId = -1;
        
    	for (int j = 1; j < keylist.size(); j++){
    		Set<String> set1 = new HashSet<>(Arrays.asList(tagMap.get(keylist.get(0))));
    		Set<String> set2 = new HashSet<>(Arrays.asList(tagMap.get(keylist.get(j))));
    		int totalSize = set1.size()+set2.size();
    		set1.addAll(set2);
    		int newSize = set1.size();
    		
    		// All tags unique from both sets
    		if(totalSize == newSize){
    			keylist.remove(0);
    			keylist.remove(j);
    			j = 1;
    			// Make slide of 0 + j
    			
    			// END Make slide
    		}
    		else if((totalSize-newSize) < prevTagsInCommon){
    			prevTagsInCommon = totalSize-newSize;
    			prevId = keylist.get(j);
    		}
    		
    		if(j == (keylist.size()-1)){
    			keylist.remove(0);
    			keylist.remove(j);
    			j = 1;
    			
    			// Make slide of 0 + prevId
    			
    			// END Make slide
    		}
    				
    	}
        
        
        // Solution
        
        
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

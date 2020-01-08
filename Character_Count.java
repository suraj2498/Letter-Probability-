import java.io.*;
import java.util.*;

public class Character_Count {
    private TreeMap<Character,Double> wordMap = new TreeMap<>();
    private String pathName;

    // TAKES A PATHNAME WHEN INSTANTIATED
    public Character_Count(String pathName) {
        this.pathName = pathName;
    }

    // READS THE FILE LINE BY LINE THEN READS EACH LINE CHAR BY CHAR AND IF CHARACTER IS A LETTER THEN
    // CAPITALIZE THE CHARACTER AND ADD IT TO THE TREE MAP
    public void characterCount() throws IOException {
        //READ FILE
        File file = new File(pathName);
        Scanner scanner = new Scanner(file);

        // ADD LETTER TO THE TREE MAP IF ITS THE FIRST OCCURRENCE OR INCREASE THE COUNT OF THE LETTER BY ONE IF ITS A
        // LETTER THAT WAS ALREADY IN THE MAP
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            for(int i = 0; i < currentLine.length(); i++){
                char character = currentLine.charAt(i);
                if ((character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z')) {
                    character = Character.toUpperCase(character);
                    if (wordMap.containsKey(character)) {
                        double count = wordMap.get(character) + 1;
                        wordMap.put(character, count);
                    } else {
                        wordMap.put(character, 1.0);
                    }
                }
            }
        }
        scanner.close();
        // TREE MAP IS FILLED WITH ALL THE CHARACTERS THAT OCCUR AND HOW MANY TIMES THEY OCCUR
    }

    // PROVIDES A TOTAL COUNT OF ALL LETTERS BY ADDING VALUES IN THE TREE MAP
    public double totalCount(){
        double total = 0;
        for (char ch = 'A'; ch <= 'Z'; ch++){
            if (wordMap.containsKey(ch))
                total += wordMap.get(ch);
        }
        return total;
    }

    // EXTRACTS THE VALUES FROM THE TREE MAP INTO AN ARRAY LIST THEN ORGANIZES THE LIST IN DESCENDING ORDER
    public List<Double> valueSortedList(){
        List<Double> list = new ArrayList<>(wordMap.values());
        Collections.sort(list);
        Collections.reverse(list);
        return list;
    }

    // CYCLES THROUGH BOTH THE WORD MAP AND THE VALUE SORTED LIST AND LOOKS FOR THE CHARACTER KEY IN THE WORD MAP THAT
    // HAS A CORRESPONDING VALUE IN THE LISTS 1ST POSITION, THEN 2ND AND SO ON, WHEN IT FINDS A CORRESPONDING CHAR IT
    // ADDS IT TO A LIST OR CHARACTERS.
    public List<Character> organizeChars(){
        List<Character> charList = new ArrayList<>();
        List<Double> list = valueSortedList();

        for(int i = 0; i < wordMap.size(); i++){
            for(char ch = 'A'; ch <= 'Z'; ch++){
                if (wordMap.containsKey(ch)){
                    if (wordMap.get(ch).equals(list.get(i))) {
                        charList.add(ch);
                    }
                }
            }
        }
        return charList;
        // AT THIS POINT WE HAVE TWO PARALLEL LISTS OF THE MOST OCCURRING CHARACTER AND THE NUMBER OF TIMES IT HAS
        // OCCURRED
    }
}
import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class HistogramLetters {
    private String pathName;
    private int numberOfLetters;
    private int numberOfLettersInFile;
    private LinkedHashMap<Character, Double> probabilityMap = new LinkedHashMap<>();
    private LinkedHashMap<Character, Double> mostFrequentLetters = new LinkedHashMap<>();
    private List<Double> valueList;
    private List<Character> keyList;

    // TAKES PATHNAME AND USER INPUTTED AMOUNT OF LETTERS TO SHOW
    public HistogramLetters(String pathName, int numberOfLetters) {
        this.pathName = pathName;
        this.numberOfLetters = numberOfLetters;
    }

    // CREATES A LINKED HASH MAP USING THE VALUESORTEDLIST AND ORGANIZECHARS TO GET A CHARACTER AND ITS MATCHING NUMBER
    // OF OCCURRENCES
    private void mostFrequentLetters()throws IOException{
        Character_Count temp = new Character_Count(pathName);
        temp.characterCount();
        valueList = temp.valueSortedList();
        keyList = temp.organizeChars();

        for (int i = 0; i < numberOfLetters; i++){
            mostFrequentLetters.put(keyList.get(i), valueList.get(i));
        }
    }

    // CREATES A LINKED HASH MAP USING THE VALUESORTEDLIST AND ORGANIZECHARS TO GET A CHARACTER AND ITS MATCHING
    // PROBABILITY OF OCCURRENCES
    private void probability()throws IOException{
        Character_Count temp = new Character_Count(pathName);
        temp.characterCount();
        double total = temp.totalCount();
        valueList = temp.valueSortedList();
        keyList = temp.organizeChars();

        for(int i = 0; i < numberOfLetters; i++){
            probabilityMap.put(keyList.get(i), valueList.get(i)/total);
        }
        numberOfLettersInFile = valueList.size(); // FOR USE AS PARAMETER FOR CONSTRUCTOR OF PIE CHART CLASS
    }

    // CREATES AN INSTANCE OF PIE CHART CLASS AND CALLS DRAW FUNCTION TO DRAW A PIE CHART BASED ON USER INPUTTED NUMBER
    // OF LETTERS TO VIEW
    public void drawPieChart(GraphicsContext gc) throws IOException{
        mostFrequentLetters();
        probability();
        PieChart pieChart = new PieChart(numberOfLettersInFile);
        pieChart.draw(gc,probabilityMap,mostFrequentLetters);
    }
}

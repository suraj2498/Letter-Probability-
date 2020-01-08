import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import java.util.*;
import java.util.List;

public class PieChart {
    private Color[] colors = new Color[26];
    private int numberOfLettersInFile;

    public PieChart(int numberOfLettersInFile){
        // FILLS AN ARRAY WITH RANDOM COLORS TO SELECT WHEN DRAWING THE PIE CHART
        // ARRAY IS FILLED WHEN A NEW OBJECT OF THE CLASS IS INSTANTIATED
        this.numberOfLettersInFile = numberOfLettersInFile;
        Random random = new Random();
        for (int i = 0; i < 26; i++){
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);
            colors[i] = Color.rgb(r,g,b);
        }
    }

    private void drawLegend(GraphicsContext gc,LinkedHashMap<Character,Double> probability,
                            LinkedHashMap<Character, Double> mostFrequentLetters){
        // PARAMETERS REQUIRE GRAPHICSCONTEXT, AND TWO PARALLEL ARRAYS OF MOST FREQUENT LETTERS AND THEIR CORRESPONDING
        // PROBABILITIES - PROVIDED IN THE HISTOGRAM_LETTERS CLASS

        // ARRAY LISTS TO HOLD THE REQUESTED NUMBER OF MOST POPULAR CHARACTERS AND THEIR CORRESPONDING PROBABILITIES
        // PROBABILITIES
        List<Double> valueList = new ArrayList<>(probability.values());
        List<Character> keyList = new ArrayList<>(mostFrequentLetters.keySet());

        // VARIABLES TO GET HEIGHT AND WIDTH OF CANVAS REGARDLESS OF SIZE
        double canvasHeight = gc.getCanvas().getHeight();
        double canvasWidth = gc.getCanvas().getWidth();

        // YPOS IS TO KEEP THE LEGEND BOX CENTERED HEIGHT-WISE AND ABSOLUTELY POSITIONED WIDTH-WISE
        double yPos = (canvasHeight/2 - ((valueList.size()+2)*18)/2) + 10;

        // DRAWS LEGEND AND ADJUSTS THE SIZE OF THE LEGEND TO GROW AS NEEDED FOR MORE REQUESTED LETTERS
        for (int i = 0; i < valueList.size()+2; i++){
            gc.setFill(Color.WHITE);
            gc.fillRect(canvasWidth*(5.0/8.0),(canvasHeight/2)-(9*i),220,i*18);
            gc.strokeRect(canvasWidth*(5.0/8.0),(canvasHeight/2)-(9*i),220,i*18);
        }

        // CREATES LITTLE BOXES WITH CORRESPONDING COLORS OF THE KEY TO DISPLAY LETTER AND ITS PROBABILITY OF OCCURRENCE
        for (int i = 1; i < valueList.size()+1; i++){
            //CREATING KEY BOXES
            gc.setFill(colors[i-1]);
            gc.fillRect(canvasWidth*(5.0/8.0)+10,yPos,15,15);
            //CREATING THE CORRESPONDING TEXT
            gc.setFont(new Font("Arial", 10));
            gc.setLineWidth(1);
            gc.strokeText("Letter " + (i) + ": " + keyList.get(i-1) + " -> Probability: " +
                            String.format("%.04f",valueList.get(i-1)),canvasWidth*(5.0/8.0)+30, yPos+10);
            yPos += 18;
        }

        // DISPLAYS THE REMAINING CHARACTERS PROBABILITY, IF NECESSARY
        if(valueList.size() < numberOfLettersInFile){
            double sumOfProb = 0;
            //ADDS TOTAL PROBABILITY OF ALL PRECEDING CHARACTERS
            for (int i = 0; i < valueList.size(); i++){
                sumOfProb += valueList.get(i);
            }
            //CREATES REMAINING CHARACTERS KEY BOX IS=F NECESSARY
            gc.setFill(Color.ROYALBLUE);
            gc.fillRect(canvasWidth*(5.0/8.0)+10,yPos,15,15);
            gc.setFont(new Font("Arial", 10));
            gc.setLineWidth(1);
            gc.strokeText("Remaining Letters -> Probability: " + String.format("%.04f",
                    1-sumOfProb),canvasWidth*(5.0/8.0)+30, yPos+10);
        }
        else {
            //DEFAULT MESSAGE OTHERWISE
            gc.setFont(new Font("Arial", 10));
            gc.setLineWidth(1);
            gc.strokeText("All Letters Included In Graph",canvasWidth*(5.0/8.0)+45, yPos+10);
        }

        // LEGEND TITLE
        gc.setFont(new Font("Arial", 14));
        gc.setLineWidth(1.5);
        gc.strokeText("LEGEND",(canvasWidth*(5.0/8.0))+80,(canvasHeight/2 - (double)((valueList.size()+2)*18)/2));
    }

    public void draw(GraphicsContext gc, LinkedHashMap<Character,Double> probability,
                     LinkedHashMap<Character, Double> mostFrequentLetters) {
        double canvasHeight = gc.getCanvas().getHeight();
        double canvasWidth = gc.getCanvas().getWidth();
        double heightAndWidthOFArc = (canvasWidth*(3.0/8.0));
        List<Double> valueList = new ArrayList<>(probability.values());

        if (valueList.size() == 0){
            gc.setFill(Color.ROYALBLUE);
            gc.fillOval((canvasWidth/2)-heightAndWidthOFArc,
                    (canvasHeight-heightAndWidthOFArc)/2, heightAndWidthOFArc, heightAndWidthOFArc);
            gc.strokeOval((canvasWidth/2)-heightAndWidthOFArc,
                    (canvasHeight-heightAndWidthOFArc)/2, heightAndWidthOFArc, heightAndWidthOFArc);
        }

        //DRAW PAGE TITLE
        gc.setFont(new Font("Arial", 18));
        gc.setLineWidth(2);
        gc.strokeText("PROBABILITY OF OCCURRENCE OF THE " + valueList.size() + " MOST FREQUENT LETTERS",
                (canvasWidth/2)-300, (canvasHeight*0.05),600);

        // START AND ENDPOINT FOR EACH SLICE IN PIE CHART
        double startPoint = 0,endpoint;

        // DRAWING PIE CHART
        for (int i = 0; i < valueList.size(); i++){
            //UPDATES THE ENDPOINT FOR EACH SLICE
            endpoint = startPoint+valueList.get(i)*360;
            gc.setFill(colors[i]);
            gc.setLineWidth(1);
            gc.strokeArc((canvasWidth/2)-heightAndWidthOFArc,(canvasHeight-heightAndWidthOFArc)/2,
                    heightAndWidthOFArc,heightAndWidthOFArc,startPoint,endpoint-startPoint,ArcType.ROUND);

            gc.fillArc((canvasWidth/2)-heightAndWidthOFArc,(canvasHeight-heightAndWidthOFArc)/2,
                    heightAndWidthOFArc,heightAndWidthOFArc,startPoint,endpoint-startPoint,ArcType.ROUND);

            startPoint = endpoint; // UPDATES STARTING POINT TO BEGIN WHERE THE PREVIOUS SLICE ENDED

            //REMAINDER SLICE IF ANY
            gc.setFill(Color.ROYALBLUE);
            gc.setLineWidth(1);
            gc.fillArc((canvasWidth/2)-heightAndWidthOFArc,(canvasHeight-heightAndWidthOFArc)/2,
                    heightAndWidthOFArc,heightAndWidthOFArc,startPoint,360-startPoint,ArcType.ROUND);
            gc.strokeArc((canvasWidth/2)-heightAndWidthOFArc,(canvasHeight-heightAndWidthOFArc)/2,
                    heightAndWidthOFArc,heightAndWidthOFArc,startPoint,360-startPoint,ArcType.ROUND);
        }
        drawLegend(gc, probability,mostFrequentLetters);
    }
}




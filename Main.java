import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(1250,650);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        stage.setTitle("CSC 221 Project 3");
        stage.setScene(new Scene(root,canvas.getWidth(),canvas.getHeight(), Color.GRAY));

        //"C:\Users\srjkm\Desktop\Emma.txt"

        //DIALOG BOX
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        Object[] fields = {"Enter Path Name", field1, "Enter Number of Letters To Check", field2};

        boolean fileFound = false;

        while (!fileFound){
            try {
                // WE TRY CREATING THE DIALOG BOX AND GETTIN THE USER INPUT AND PASSING INTO THE EMMA.DRAWPIECHART
                // METHOD, IF THE USER PRESSESS CANCEL, THEN WE BREAK OUT AND AN EMPTYT JAVAFX WINDOW IS OPENED AS
                // NO FILE WAS PASSED IN. THEN IF WE SUCCESSFULLY RUN THE DRAWPIECHART METHOD WITH NO
                // EXCEPTIONS THROWN WE SET FILE FOUND BOOLEAN TO TRUE TO BRAK OUT OF WHILE LOOP
                int n = JOptionPane.showConfirmDialog(null, fields,"User Input Prompt",JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                String pathName = field1.getText();
                String numberOfLetters = field2.getText();

                if (n == JOptionPane.CANCEL_OPTION){
                    break;
                }
                // CREATING OBJECT OF HISTOGRAM LETTERS AND
                if (numberOfLetters.equals(""))
                    numberOfLetters = "0";

                HistogramLetters emma = new HistogramLetters(pathName, Integer.parseInt(numberOfLetters));
                emma.drawPieChart(gc);
                fileFound = true;
            }catch (FileNotFoundException f){
                // IF THE EXCEPTION WAS THROWN WE JUST CHANGE THE FIRST FIELD AND THE DIALOG BOX IS CALLED WITH THE
                // UPDATED LABEL, SAME FOR THE SECOND CATCH BLOCK
                fields[0] = "Path Not Found Please Re-Enter Path Name";
                fields[2] = "Enter Number of Letters To Check";
            }catch (IndexOutOfBoundsException i){
                fields[0] = "Enter Path Name";
                fields[2] = "Number Entered is greater than Unique Letters in the File,\nPlease Re-Enter a Number";
            }
        }
        root.getChildren().add(canvas);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

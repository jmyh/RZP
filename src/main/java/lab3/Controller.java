package lab3;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.lang.model.element.AnnotationValue;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    private FileChooser fileChooser=new FileChooser();
    private Stage primaryStage;
    private int shift;
    private File book;
    private StringBuilder text;
    private String encryptText;
    private String decryptText;
    private Map<Character, Integer> mapChar=new HashMap<>();

    @FXML
    private Button encryptButton;

    @FXML
    private Button decryptButton;

    @FXML
    private Label chosenBook;

    @FXML
    private TextArea textArea;

    @FXML
    private TableView<ObservableList<Integer>> table;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage=stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void search() throws FileNotFoundException {
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        book=fileChooser.showOpenDialog(primaryStage);
        chosenBook.setText(book.getName());

        Scanner input=new Scanner(new FileInputStream(book));
        text=new StringBuilder();
        for (int i=0;i<250;i++) {
            text.append(input.nextLine()+"\n");
        }
        textArea.setText(text.toString());

        fillTable(text);

        encryptButton.setDisable(false);
    }



    @FXML
    public void encrypt() {
        shift=findShift();
        char[] sourceArray=text.toString().toCharArray();
        StringBuilder encryptText=new StringBuilder();
        for (char c : sourceArray) {
            if(c!='\n')
                encryptText.append((char)(c+shift));
            else encryptText.append(c);
        }
        textArea.setText(encryptText.toString());
        this.encryptText=encryptText.toString();

        //write file
        File encryptFile=new File(book.getParent()+"/encrypt.txt");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(encryptFile))) {
            bw.write(this.encryptText,0,this.encryptText.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        decryptButton.setDisable(false);
    }



    @FXML
    public void decrypt() {
        char[] sourceArray=encryptText.toCharArray();
        StringBuilder decryptText=new StringBuilder();
        for (char c : sourceArray) {
            if(c!='\n')
                decryptText.append((char)(c-shift));
            else decryptText.append(c);
        }
        textArea.setText(decryptText.toString());
        this.decryptText=decryptText.toString();

        //write file
        File decryptFile=new File(book.getParent()+"/decrypt.txt");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(decryptFile))) {
            bw.write(this.decryptText,0,this.decryptText.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        encryptButton.setDisable(false);

    }

    private int findShift() {
        int wordCounter=0;
        int charCounter=0;
        char[] chars=text.toString().toCharArray();
        while (wordCounter<10) {
            if(chars[charCounter]=='\n' || chars[charCounter]==' ') wordCounter++;
            charCounter++;
        }
        String header=text.toString().substring(0,charCounter-1);

        int shift=(header.lastIndexOf(" ")>header.lastIndexOf("\n"))?header.lastIndexOf(" ")+3:header.lastIndexOf("\n")+3;

        return shift;
    }

    private void fillTable(StringBuilder text) {
        char[] chars=new char[text.length()];
        text.getChars(0,chars.length,chars,0);

        //count chars
        int counter;
        for (char character : chars) {
            if(mapChar.get(character)==null) {
                mapChar.put(character,1);
            } else {
                counter=mapChar.get(character);
                mapChar.put(character,counter+1);
            }
        }

        Set<Map.Entry<Character,Integer>> set=mapChar.entrySet();
        table.setItems(buildData(set));
        int i=0;
        for (Map.Entry<Character, Integer> entry : set) {
            int curColumn=i;
            TableColumn<ObservableList<Integer>, Integer> column=new TableColumn<>("\""+entry.getKey()+"\"");
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curColumn))
            );
            i++;

            table.getColumns().add(column);
        }


    }

    private ObservableList<ObservableList<Integer>> buildData(Set<Map.Entry<Character, Integer>> set) {
        ObservableList<Integer> rowValues=FXCollections.observableArrayList();
        for (Map.Entry<Character, Integer> entry : set)
            rowValues.add(entry.getValue());
        ObservableList<ObservableList<Integer>> tableValues=FXCollections.observableArrayList();
        tableValues.add(rowValues);
        return tableValues;
    }
}

package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Stack;

public class Controller {
    @FXML
    private TextField firstnumber;
    @FXML
    private TextField secondnumber;
    @FXML
    private TextField thirdnumber;
    @FXML
    private TextField fourthnumber;
    @FXML
    private TextField fifthnumber;
    @FXML
    private TextField goal;
    @FXML
    private TextArea resultbox;
    @FXML
    private Text timer;
    @FXML
    private WebView mywebview;

    ArrayList <String> createStates(ArrayList<Integer> Liste) {
        ArrayList<String> mystates = new ArrayList<>();
        for (int i = 0; i < Liste.size(); ++i) {
            for (int j = 0; j < Liste.size(); ++j) {
                if (i == j || Liste.get(i) == 0 || Liste.get(j) == 0) {
                    continue;
                }

                mystates.add((Liste.get(i) + " * "+ Liste.get(j) +" = "+(Liste.get(i) * Liste.get(j))));
                mystates.add((Liste.get(i) + " + "+ Liste.get(j) +" = "+(Liste.get(i) + Liste.get(j))));

                if ((!(Liste.get(i) / Liste.get(j) == 0)) && Liste.get(i) % Liste.get(j) == 0) {
                    mystates.add((Liste.get(i) + " / "+ Liste.get(j) +" = "+(Liste.get(i) / Liste.get(j))));
                }

                if (!(Liste.get(i) - Liste.get(j) == 0) && Liste.get(i) - Liste.get(j) > 0) {
                    mystates.add((Liste.get(i) + " - "+ Liste.get(j) +" = "+(Liste.get(i) - Liste.get(j))));
                }
            }
        }
        return mystates;
    }



    Stack<String> Solve(ArrayList<Integer> numbers, int goal){
        Stack<String> mystack = new Stack<>();
        Stack<String> save = new Stack<>();
        int diff = goal;
        ArrayList<Integer> firstarray;
        ArrayList<Integer> secondarray;
        ArrayList<Integer> thirdarray;
        ArrayList<Integer> fourtharray;
        ArrayList<String> firstmap;
        ArrayList<String> secondmap;
        ArrayList<String> thirdmap;
        ArrayList<String> fourthmap;
        ArrayList<String> fifthmap;
        String[] parser;

        firstmap = createStates(numbers);
        for (String first : firstmap){
            firstarray = new ArrayList<>(numbers);

            parser = first.split(" ");
            mystack.add(first);

            firstarray.remove(firstarray.indexOf(Integer.parseInt(parser[0])));
            firstarray.remove(firstarray.indexOf(Integer.parseInt(parser[2])));

            firstarray.add(Integer.parseInt(parser[4]));
            if (Integer.parseInt(parser[4]) == goal) { return mystack; }
            if (Math.abs(goal - Integer.parseInt(parser[4])) < diff) {
                save.clear();
                save.addAll(mystack);
                diff = Math.abs(goal - Integer.parseInt(parser[4]));
            }

            secondmap = createStates(firstarray);
            for (String second : secondmap){
                secondarray = new ArrayList<>(firstarray);


                parser = second.split(" ");
                mystack.add(second);
                secondarray.remove(secondarray.indexOf(Integer.parseInt(parser[0])));
                secondarray.remove(secondarray.indexOf(Integer.parseInt(parser[2])));
                secondarray.add(Integer.parseInt(parser[4]));

                if (Integer.parseInt(parser[4]) == goal) { return mystack; }
                if (Math.abs(goal - Integer.parseInt(parser[4])) < diff) {
                    save.clear();
                    save.addAll(mystack);
                    diff = Math.abs(goal - Integer.parseInt(parser[4]));
                }

                thirdmap = createStates(secondarray);
                for (String third : thirdmap){
                    thirdarray = new ArrayList<>(secondarray);

                    parser = third.split(" ");
                    mystack.add(third);
                    thirdarray.remove(thirdarray.indexOf(Integer.parseInt(parser[0])));
                    thirdarray.remove(thirdarray.indexOf(Integer.parseInt(parser[2])));
                    thirdarray.add(Integer.parseInt(parser[4]));
                    if (Integer.parseInt(parser[4]) == goal) { return mystack; }
                    if (Math.abs(goal - Integer.parseInt(parser[4])) < diff) {
                        save.clear();
                        save.addAll(mystack);
                        diff = Math.abs(goal - Integer.parseInt(parser[4]));
                    }

                    fourthmap = createStates(thirdarray);
                    for (String fourth : fourthmap){
                        fourtharray = new ArrayList<>(thirdarray);

                        parser = fourth.split(" ");
                        mystack.add(fourth);
                        fourtharray.remove(fourtharray.indexOf(Integer.parseInt(parser[0])));
                        fourtharray.remove(fourtharray.indexOf(Integer.parseInt(parser[2])));
                        fourtharray.add(Integer.parseInt(parser[4]));
                        if (Integer.parseInt(parser[4]) == goal) { return mystack; }
                        if (Math.abs(goal - Integer.parseInt(parser[4])) < diff) {
                            save.clear();
                            save.addAll(mystack);
                            diff = Math.abs(goal - Integer.parseInt(parser[4]));
                        }


                        fifthmap = createStates(fourtharray);
                        for (String fifth : fifthmap){
                            mystack.add(fifth);
                            parser = fifth.split(" ");
                            if (Integer.parseInt(parser[4]) == goal) { return mystack; }
                            if (Math.abs(goal - Integer.parseInt(parser[4])) < diff) {
                                save.clear();
                                save.addAll(mystack);
                                diff = Math.abs(goal - Integer.parseInt(parser[4]));
                            }
                            mystack.pop();

                        }
                        mystack.pop();
                    }
                    mystack.pop();
                }
                mystack.pop();
            }
            mystack.pop();
        }
        return save;
    }



    @FXML
    public void runsearch(javafx.event.ActionEvent actionEvent) {
        try{
            resultbox.clear();
            ArrayList<Integer> numbersequence = new ArrayList<Integer>();
            numbersequence.add(Integer.parseInt(firstnumber.getText()));
            numbersequence.add(Integer.parseInt(secondnumber.getText()));
            numbersequence.add(Integer.parseInt(thirdnumber.getText()));
            numbersequence.add(Integer.parseInt(fourthnumber.getText()));
            numbersequence.add(Integer.parseInt(fifthnumber.getText()));

            int mygoal = Integer.parseInt(goal.getText());

            long time = ZonedDateTime.now().toInstant().toEpochMilli();

            Stack<String> Result =  Solve(numbersequence, mygoal);

            time = ZonedDateTime.now().toInstant().toEpochMilli() - time;
            timer.setText(Long.toString(time));

            for (String iter : Result){
                resultbox.appendText(iter+"\n");
            }
        }
        catch (Exception exception) { System.out.println("An exception has occured"); }
    }

    @FXML
    private void initialize(){
        WebEngine engine = mywebview.getEngine();
        URL url = this.getClass().getResource("demo/Index.html");
        engine.load(url.toString());
    }
}

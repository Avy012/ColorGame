package project3;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ColorTrap extends Application
{
    private Scene scene;
    private BorderPane borderPane;
    private Text txtCountDown = new Text();
    private Timeline timeline;


    private final int TIMER = 15;
    private int count = 0;

    @Override
    public void start(Stage primaryStage)
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        initializeGame();
        startPlay();

        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();

        count = TIMER;
        txtCountDown.setText(TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {


                    if(count >= 0)
                    {
                        txtCountDown.setText(count + "");
                        count--;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline.setCycleCount(TIMER + 2);
        timeline.play();

    }
    
    public void endOfGame()
    {
        //TODO complete this method as required.
        Button btn = new Button("Play again");

        HBox htop = (HBox) borderPane.getTop();
        VBox hcenter = (VBox) borderPane.getCenter();
        BorderPane hbottom = (BorderPane) borderPane.getBottom();

        HBox boxbottom = (HBox) hbottom.getLeft();
        Text scoreNumber = (Text) boxbottom.getChildren().get(1);
        String num = scoreNumber.getText();

        Text endScore = new Text("Your score: "+num);
        endScore.setFont(Font.font("Marker Felt",30));

        VBox end = new VBox();
        end.getChildren().addAll(endScore,btn);
        htop.getChildren().clear();
        hcenter.getChildren().clear();
        hbottom.getChildren().clear();

        hcenter.getChildren().add(end);
        end.setAlignment(Pos.CENTER);
        end.setTranslateY(-30);
        end.setSpacing(30);

        btn.setOnAction(e -> {
            initializeGame();
            startPlay();
        });

    }


    public void checkChoice(Text choice) // 정답확인, choice는 내가 누른 거
    {
        //TODO complete this method as required.

        Color[] colors = {Color.BROWN, Color.RED, Color.YELLOW, Color.BLACK, Color.PURPLE, Color.BLUE, Color.ORANGE};
        int colorIndex = 0;

        String colorNames[] = new String[7];
        ColorsEnum[] ce = ColorsEnum.values();
        int x=0;
        for(ColorsEnum colorname: ce)
        {
            colorNames[x++] = colorname.toString();
        }

        String choiceText = choice.getText(); // get the clicked text
        ///클릭한 글자랑 트랩 색이랑 같아야 함

        if (choiceText == "BROWN") colorIndex = 0;
        else if (choiceText == "RED") colorIndex = 1;
        else if (choiceText == "YELLOW") colorIndex = 2;
        else if (choiceText == "BLACK") colorIndex = 3;
        else if (choiceText == "PURPLE") colorIndex = 4;
        else if (choiceText == "BLUE") colorIndex = 5;
        else if (choiceText == "ORANGE") colorIndex = 6;


        HBox hb = (HBox) borderPane.getTop(); // get the trap color
        Text trapText = (Text) hb.getChildren().get(0);
        Color trapColor = (Color) trapText.getFill();


        BorderPane hBottom = (BorderPane) borderPane.getBottom();
        HBox boxbottom = (HBox) hBottom.getLeft();
        Text scoreNumber = (Text) boxbottom.getChildren().get(1);
        String num = scoreNumber.getText();
        int realnum = Integer.parseInt(num);

        if (trapColor == colors[colorIndex]){ // 정답일때
            realnum ++;
            HBox bleft = new HBox();
            HBox bright = new HBox();
            Text score = new Text("Score: ");
            Text scoreNum = new Text(Integer.toString(realnum));
            score.setFont(Font.font("Arial",20));
            scoreNum.setFont(Font.font("Arial",20));
            Text time = new Text("Time: ");
            time.setFont(Font.font("Arial",20));
            Image image = new Image("file:image/correct.png");
            ImageView img = new ImageView(image);
            img.setFitHeight(30);
            img.setFitWidth(30);

            bleft.getChildren().addAll(score,scoreNum);
            bright.getChildren().addAll(time,txtCountDown);
            txtCountDown.setFont(Font.font("Arial",20));
            bleft.setPadding(new Insets(10));
            bright.setPadding(new Insets(10));
            hBottom.setLeft(bleft);
            hBottom.setRight(bright);
            hBottom.setCenter(img);

        }
        else{ // 오답일때
            HBox bleft = new HBox();
            HBox bright = new HBox();
            Text score = new Text("Score: ");
            Text scoreNum = new Text(Integer.toString(realnum));
            score.setFont(Font.font("Arial",20));
            scoreNum.setFont(Font.font("Arial",20));
            Text time = new Text("Time: ");
            time.setFont(Font.font("Arial",20));
            Image image = new Image("file:image/wrong.png");
            ImageView img = new ImageView(image);
            img.setFitHeight(30);
            img.setFitWidth(30);

            bleft.getChildren().addAll(score,scoreNum);
            bright.getChildren().addAll(time,txtCountDown);
            txtCountDown.setFont(Font.font("Arial",20));
            bleft.setPadding(new Insets(10));
            bright.setPadding(new Insets(10));
            hBottom.setLeft(bleft);
            hBottom.setRight(bright);
            hBottom.setCenter(img);

        }



        //Do NOT add any code after this comment
        //Choose a new trap word and options list
        chooseTrapWordAndColor();
        colorNameOptions();
    }

    public void chooseTrapWordAndColor()
    {
        Color[] colors = {Color.BROWN, Color.RED, Color.YELLOW, Color.BLACK, Color.PURPLE, Color.BLUE, Color.ORANGE};

        String colorNames[] = new String[7];
        ColorsEnum[] ce = ColorsEnum.values();
        int x=0;
        for(ColorsEnum colorname: ce)
        {
            colorNames[x++] = colorname.toString();
        }

        int randomIndex = (int) (Math.random() * colorNames.length);
        String trapWord = colorNames[randomIndex];
        randomIndex = (int) (Math.random() * colorNames.length);
        Color trapColor = colors[randomIndex];

        Text trap = new Text(trapWord);
        trap.setFill(trapColor);
        trap.setFont(Font.font("Marker Felt",60));

        HBox hTop = (HBox) borderPane.getTop();
        hTop.getChildren().clear();
        hTop.getChildren().add(trap);

    }
    
    public void colorNameOptions() // checkChoice 여기서 사용 (마우스 클릭에 반응)
    {
        //TODO complete this method as required.
        Color[] color = {Color.BROWN, Color.RED, Color.YELLOW, Color.BLACK, Color.PURPLE, Color.BLUE, Color.ORANGE};

        String colorNames[] = new String[7];
        ColorsEnum[] ce = ColorsEnum.values();
        int x=0;
        for(ColorsEnum colorname: ce)
        {
            colorNames[x++] = colorname.toString();
        }

        HBox hCenter1 = new HBox();
        HBox hCenter2 = new HBox();
        HBox hCenter3 = new HBox();

//      Shuffle the color
        List<Color> shuffledColors = Arrays.asList(color);
        Collections.shuffle(shuffledColors);
        shuffledColors.toArray(color);

        // shuffle the names
        List<String> shuffledNames = Arrays.asList(colorNames);
        Collections.shuffle(shuffledNames);
        shuffledNames.toArray(colorNames);

        Text colors[] = new Text[7];

        for(int i=0;i<7;i++){

            colors[i] = new Text();
            colors[i].setText(String.valueOf((colorNames[i])));
            colors[i].setFont(Font.font("Marker Felt",40));
            colors[i].setFill(color[i]);
        }

        hCenter1.getChildren().addAll(colors[0],colors[1],colors[2]);
        hCenter2.getChildren().addAll(colors[3],colors[4],colors[5]);
        hCenter3.getChildren().addAll(colors[6]);

        colors[0].setOnMouseClicked(e -> {
            checkChoice(colors[0]);
        });
        colors[1].setOnMouseClicked(e -> {
            checkChoice(colors[1]);
        });
        colors[2].setOnMouseClicked(e -> {
            checkChoice(colors[2]);
        });
        colors[3].setOnMouseClicked(e -> {
            checkChoice(colors[3]);
        });
        colors[4].setOnMouseClicked(e -> {
            checkChoice(colors[4]);
        });
        colors[5].setOnMouseClicked(e -> {
            checkChoice(colors[5]);
        });
        colors[6].setOnMouseClicked(e -> {
            checkChoice(colors[6]);
        });

        hCenter1.setSpacing(20);
        hCenter2.setSpacing(20);

        VBox vb = (VBox) borderPane.getCenter();
        vb.getChildren().clear();
        vb.getChildren().addAll(hCenter1,hCenter2,hCenter3);
        hCenter1.setAlignment(Pos.CENTER);
        hCenter2.setAlignment(Pos.CENTER);
        hCenter3.setAlignment(Pos.CENTER);

    }

    public void initializeGame()
    {
        //TODO complete this method as required.
        //making top
        HBox hTop = new HBox();
        Text trap = new Text();
        trap.setFont(Font.font("Marker Felt",60));
        hTop.getChildren().addAll(trap);

        //making center
        VBox Center = new VBox();
        HBox hCenter1 = new HBox();
        HBox hCenter2 = new HBox();
        HBox hCenter3 = new HBox();

        String colorNames[] = new String[7];
        ColorsEnum[] ce = ColorsEnum.values();
        int x=0;
        for(ColorsEnum colorname: ce)
        {
            colorNames[x++] = colorname.toString();
        }

        Text colors[] = new Text[7];

        for(int i=0;i<7;i++){

            colors[i] = new Text();
            colors[i].setText(String.valueOf((colorNames[i])));
            colors[i].setFont(Font.font("Marker Felt",40));
        }

        hCenter1.getChildren().addAll(colors[0],colors[1],colors[2]);
        hCenter2.getChildren().addAll(colors[3],colors[4],colors[5]);
        hCenter3.getChildren().addAll(colors[6]);

        hCenter1.setSpacing(20);
        hCenter2.setSpacing(20);

        Center.getChildren().addAll(hCenter1,hCenter2,hCenter3);

        //making bottom
        BorderPane hBottom = new BorderPane();
        HBox bleft = new HBox();
        HBox bright = new HBox();
        Text score = new Text("Score: ");
        Text scoreNum = new Text ("0");
        scoreNum.setFont(Font.font("Arial",20));
        score.setFont(Font.font("Arial",20));
        Text time = new Text("Time: ");
        time.setFont(Font.font("Arial",20));

        bleft.getChildren().addAll(score,scoreNum);
        bright.getChildren().addAll(time,txtCountDown);
        txtCountDown.setFont(Font.font("Arial",20));
        bleft.setPadding(new Insets(10));
        bright.setPadding(new Insets(10));
        hBottom.setLeft(bleft);
        hBottom.setRight(bright);


        //add
        hTop.setAlignment(Pos.CENTER);
        hCenter1.setAlignment(Pos.CENTER);
        hCenter2.setAlignment(Pos.CENTER);
        hCenter3.setAlignment(Pos.CENTER);
        Center.setAlignment(Pos.CENTER);


        borderPane.setTop(hTop);
        borderPane.setCenter(Center);
        borderPane.setBottom(hBottom);
        colorNameOptions();

    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
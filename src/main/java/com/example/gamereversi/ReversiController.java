package com.example.gamereversi;

import com.example.gamereversi.model.Owner;
import com.example.gamereversi.model.ReversiGameModel;
import com.example.gamereversi.model.ReversiSq;
import javafx.fxml.FXML;
import com.example.gamereversi.model.ReversiPiece;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ReversiController {

    @FXML
    // Игровая панель, содержит игровое поле, кнопку "Новая игра" и тп.
   private  GridPane gridPane;
    //игровая панель,содержит игровое поле ,кнопку "Новая игра", и т.п.
    @FXML
    private BorderPane borderPane;

    /**
     * Label'ы, в которых отображается текущий счёт.
     */
    @FXML
    private Label whiteScore;

    @FXML
    private Label blackScore;

   private static String boardBackground ="-fx-background-color: green .6;";
   private final ReversiGameModel model = ReversiGameModel.getInstance();
    /**
     * Инициализация доски при запуске приложения.
     */
   @FXML
   void initialize(){
    borderPane.getCenter().setStyle(boardBackground);
    for (int i =0;i<model.BOARD_SIZE;i++){
     for (int j=0;j<model.BOARD_SIZE;j++){
      ReversiSq squre=new ReversiSq(i,j);
      ReversiPiece piece = new ReversiPiece();
    piece.getOwnerOfPieceProperty().bind(model.gameBoard[i][j]);
     piece.getHighlightProperty().bind(model.highlight[i][j]);
     gridPane.add(new StackPane(piece,squre),i,j);
     }
    }
    displayScore();
   }
private void displayScore(){
       blackScore.textProperty().bind(model.getScore(Owner.BLACK).asString());
        whiteScore.textProperty().bind(model.getScore(Owner.WHITE).asString());
   }
    /**
     * Обработчик нажатия кнопки "Новая игра".
     */
@FXML
    protected void onNewGameButtonClicked(){
       model.restart();
    }
}
package com.example.gamereversi.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;

public class ReversiGameModel {
    /**
     *  Размер доски (8 по усл.)
     */
    public static int BOARD_SIZE = 8 ;
/**
 * Текущий игрок
 */
public ObjectProperty<Owner> turn =new SimpleObjectProperty<>(Owner.BLACK);
public  ObjectProperty<Owner>[][] gameBoard = new ObjectProperty[BOARD_SIZE][BOARD_SIZE];
public ObjectProperty<Boolean>[][] highlight = new ObjectProperty[BOARD_SIZE][BOARD_SIZE];
public ObjectProperty<Boolean> endGame = new SimpleObjectProperty<>(false);

private ReversiGameModel(){
    for (int i=0;i<BOARD_SIZE;i++){
        for (int j=0;j<BOARD_SIZE;j++){
            gameBoard[i][j]= new SimpleObjectProperty<>(Owner.NONE);
            highlight[i][j]= new SimpleObjectProperty<>(false);
        }
    }
initBoard();
}
    /**
     * Инициализация доски.
     * Расставляем фишки в 4 центральные клетки, назначаем первого ходящего и подсвечиваем возможные для хода клетки.
     */
private void initBoard(){
    int center1 =BOARD_SIZE/2 - 1;
    int center2 =BOARD_SIZE/2;
gameBoard[center1][center1].setValue(Owner.WHITE);
gameBoard[center1][center2].setValue(Owner.BLACK);
gameBoard[center2][center1].setValue(Owner.BLACK);
gameBoard[center2][center2].setValue(Owner.WHITE);
    // подсвечиваем для текущего игрока (черные) возможные клетки для хода
    turn.setValue(Owner.BLACK);
    highlightPossibleMoves();
}
    /**
     * Перезапуск игры.
     * Надо подчистить все клетки, проинициализировать доску
     */
public void restart(){
    for(int i = 0; i < BOARD_SIZE; i++){
        for(int j = 0; j < BOARD_SIZE; j++){
            gameBoard[i][j].setValue(Owner.NONE);
            highlight[i][j].setValue(false);
        }
    }
    initBoard();
}
    /**
     * Будут ли перевороты фишек в направлении (dirX, dirY), если игрок походит в клетку (x, y)?
     *  x - координата по x (абсцисса) клетки, в которую хочет пойти игрок.
     *  y - координата по y (ордината) клетки, в которую хочет пойти игрок.
     *  dirX - направление по оси абсцисс, в котором будет проводиться поиск ряда фишек противоположного цвета,
     *      *             заканчивающегося клеткой с фишкой текущего цвета.
     *  dirY - направление по оси ординат, в котором будет проводиться поиск ряда фишек противоположного цвета,
     *      *             заканчивающегося клеткой с фишкой текущего цвета.
     * return - true, если и точки (x, y) в направлении (newX, newY) есть ряд фишек противоположного цвета,
     *      *             заканчивающийся клеткой с фишкой текущего цвета.
     */
    public boolean isPossibleToFlip(int x, int y, int dirX, int dirY) {
        int newX = x + dirX;
        int newY = y + dirY;
        boolean result = false;
        // двигаемся в направлении (dirX, dirY)
        // если не вышли за пределы доски и не уперлись в пустую клетку, то заходим внутрь цикла
        while (newX >= 0 && newX < BOARD_SIZE && newY >=0 && newY < BOARD_SIZE && gameBoard[newX][newY].getValue() != Owner.NONE) {
            // если встреченная клетка содержит фишку текущего цвета, то выводмс ответ
            if (gameBoard[newX][newY].getValue().equals(turn.get())) {
                // если попадём сюда при первой же возможности (т.е. ближайший сосед содержит фишку текущего цвета, то ответ false)
                return result;
            }
            result = true;
            newX += dirX;
            newY += dirY;
        }
        return false;
    }
    /**
     * Проверяет допустимость хода в текущую клетку.
     * Для этого необходимо выполнение двух условий - эта клетка должна быть пуста и
     * Хотя в одном направлении перевернутся фишки.
     *  x - координата по x (абсцисса) клетки, в которую хочет пойти игрок.
     *  y - координата по y (ордината) клетки, в которую хочет пойти игрок.
     * return - true, если в данную клетку совершить ход (текущий игрок может походить).
     */
    public boolean isLegalMove(int x, int y) {
        boolean isEmpty = gameBoard[x][y].getValue().equals(Owner.NONE);

        boolean isAnyPossibleFlip = isPossibleToFlip(x, y, 0, -1) || isPossibleToFlip(x, y, -1, -1) ||
                isPossibleToFlip(x, y, -1, 0) || isPossibleToFlip(x, y, -1, 1) ||
                isPossibleToFlip(x, y, 0, 1) ||
                isPossibleToFlip(x, y, 1, 1) ||
                isPossibleToFlip(x, y, 1, 0) || isPossibleToFlip(x, y, 1, -1);
        return isEmpty && isAnyPossibleFlip;
    }
    /**
     * Метод, посвечивающий возможные клетки для хода
     * return - true, если есть хотя бы одна клетка, в которую может походить текущий игрок;
     * false - в противном случае.
     */
private boolean highlightPossibleMoves(){
    boolean result = false;
    for (int i=0;i<BOARD_SIZE;i++){
        for (int j=0;j<BOARD_SIZE;j++){
            if (gameBoard[i][j].getValue().equals(Owner.NONE)){
                if (isLegalMove(i,j)){
                    highlight[i][j].setValue(true);
                    result= true;
                }
                else {
                    highlight[i][j].setValue(false);
                }
            }
        }
    }
    return result;
}   /**
     * Метод возвращает объект-свойство скол-вом фишек на доске опред. цыета,заданного в аргументе.
     */
    public NumberExpression getScore(Owner owner) {
        NumberExpression score = new SimpleIntegerProperty();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                score = score.add(Bindings.when(gameBoard[i][j].isEqualTo(owner)).then(1).otherwise(0));
            }
        }
        return score;
    }
    /**
     * Переворачивает фишки в направлении (dirX, dirY), если игрок походит в клетку (x, y).
     *  x - координата по x (абсцисса) клетки, в которую ходит игрок.
     *  y - координата по y (ордината) клетки, в которую ходит игрок.
     *  dirX - направление по оси абсцисс
     *  dirY - направление по оси ординат
     */
    protected void flip(int x, int y, int dirX, int dirY) {
        // проверяем условие, что в этом направлении можно провести "переворот" фишек
        if (isPossibleToFlip(x, y, dirX, dirY)) {
            // можно совершить переворот, поэтому переходим к ближайшей к (x, y)
            // точке в направлении (dirX, dirY)
            int newX = x + dirX;
            int newY = y + dirY;
            // если не вылезли за пределы доски и пока не уперлись в фишку текущего цвета
            while (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE && gameBoard[newX][newY].getValue() != turn.getValue()) {
                // меняем фишке цвет
                gameBoard[newX][newY].setValue(turn.getValue());
                // идём в соседнюю клетку
                newX += dirX;
                newY += dirY;
            }
        }
}   /**
     * Выводит окно с сообщением о победителе, или о ничейном результате.
     */
        private void showAlertEndOfGame () {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Конец игры!");
            alert.setHeaderText(null);
            Integer whiteScore = getScore(Owner.WHITE).getValue().intValue();
            Integer blackScore = getScore(Owner.BLACK).getValue().intValue();

            if (whiteScore.equals(blackScore)) {
                alert.setContentText("Игра окончена! Ничья!");
            } else {
                String winner = (whiteScore > blackScore ? "WHITE" : "BLACK");
                alert.setContentText("Игра окончена! Со счётом " + blackScore.toString()
                        + " - " + whiteScore.toString() + " победил "
                        + winner.toUpperCase() + "!");
            }
            alert.showAndWait();
            restart();
        }
        public static ReversiGameModel getInstance () {
            return ReversiModelHolder.INSTANCE;
        }

        private static class ReversiModelHolder {

            private static final ReversiGameModel INSTANCE = new ReversiGameModel();
        }
    /**
     * Выполнение хода (если возможно) при нажатии на ячейку (x, y)
     *  x - координата по x (абсцисса) клетки, в которую ходит игрок.
     *  y - координата по y (ордината) клетки, в которую ходит игрок.
     */
        protected void play ( int x, int y){
            // проверяем, что в клетку (x, y) можно совершить ход
            if (isLegalMove(x, y)) {
                // помещаем в клетку (x, y) фишку цвета текущего игрока
                gameBoard[x][y].setValue(turn.getValue());
                // совершаем "переворот" во всех возможных направлениях
                flip(x, y, 0, -1);
                flip(x, y, -1, -1);
                flip(x, y, -1, 0);
                flip(x, y, -1, 1);
                flip(x, y, 0, 1);
                flip(x, y, 1, 1);
                flip(x, y, 1, 0);
                flip(x, y, 1, -1);
                // Передаём ход другому игроку
                turn.setValue(turn.getValue().opposite());
                // Подсвечиваем для него доступные для хода клетки
                boolean isNeedToFlipTurn = highlightPossibleMoves();
                // Если ему некуда походить, то передаём ход другому игроку
                if (!isNeedToFlipTurn) {
                    turn.setValue(turn.getValue().opposite());
                }
                // Подсвечиваем для текущего игрока досутпные для хода клетки
                boolean isEndOfGame = highlightPossibleMoves();
                // Если ему тоже некуда идти, то это конец игры!
                // Выделяем победителя!
                if (!isEndOfGame) {
                    showAlertEndOfGame();
                    endGame.setValue(true);
                }
            }
        }
}
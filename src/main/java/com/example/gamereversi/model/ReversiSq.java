package com.example.gamereversi.model;

import javafx.scene.layout.Region;
/**
 * Специальный класс для обработки нажатия на клетку.
 * Наследник стандартного класса Region из JavaFX.
 * Интересует обработка нажатия на эту область.
 */
public class ReversiSq extends Region {
    ReversiGameModel model = ReversiGameModel.getInstance();
    public ReversiSq(final int x,final int y){
        /**
         * Обработка нажатия на область
         * Вызывем метод play, который выполняет ход(если это возможно)
         */
    setOnMouseClicked(t->{model.play(x,y);});
    }
}

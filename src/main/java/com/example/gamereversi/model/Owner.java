package com.example.gamereversi.model;

public enum Owner {
    NONE,
    WHITE,
    BLACK;

    /** Метод, возвращающий противоположный цвет.
     * Нужен для смены текущего игрока и смены цвета в ячейках.
     */


    public Owner opposite() {
        return this == WHITE ? BLACK : this == BLACK ? WHITE : NONE;
    }
}

package com.example.gamereversi.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;

public class ReversiPiece extends Region {
    //Объект-св-во, в котором хранится тип фишки в текущей ячейке
    private ObjectProperty<Owner> ownerOfPiece;
    //Объект-св-во, в котором хранится логическое значение, отвечающее за необходимость подсветки ячейки
    private ObjectProperty<Boolean> toHighlight;
//getter к свойству ownerOfPiece, будет использован для привязки к соответствующему объекту-свойству на игровой доске
    public ObjectProperty<Owner> getOwnerOfPieceProperty() {
        return ownerOfPiece;
    }
//getter к свойству toHighlight, будет использован для привязки к соответствующему объекту-свойству на игровой доске
    public ObjectProperty<Boolean> getHighlightProperty() {
        return toHighlight;
    }

    public ReversiPiece() {
        //инициализируем объекты-свойства
        ownerOfPiece = new SimpleObjectProperty<>(Owner.NONE);
        toHighlight = new SimpleObjectProperty<>(false);

//в зависимости от значений ownerOfPiece и toHighlight определяем наполнение ячейки
// (отрисовываем фишку, оставляем прозрачный фон или подсвечиваем для возможного хода).
        styleProperty().bind(Bindings.when(ownerOfPiece.isEqualTo(Owner.NONE).and(toHighlight.isEqualTo(true)))
                .then("-fx-background-color: #9bb4fa; -fx-border-color: black; -fx-border-width: 0.1;")
                .otherwise(Bindings.when(ownerOfPiece.isEqualTo(Owner.NONE).and(toHighlight.isEqualTo(false)))
                        .then("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0.1;")
                        .otherwise(Bindings.when(ownerOfPiece.isEqualTo(Owner.WHITE))
                                .then("-fx-background-color: radial-gradient(radius 100%, white .4, gray .9, darkgray 1); -fx-background-radius: 1000em; -fx-background-insets: 5")
                                .otherwise("-fx-background-color: radial-gradient(radius 100%, white 0, black .6); -fx-background-radius: 1000em; -fx-background-insets: 5"))));

    }
}

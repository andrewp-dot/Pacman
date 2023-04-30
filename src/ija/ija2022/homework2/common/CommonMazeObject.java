package ija.ija2022.homework2.common;

public interface CommonMazeObject {
    boolean canMove(CommonField.Direction dir);
    boolean move(CommonField.Direction dir);
    CommonField getField();
}
package ija.ija2022.homework2.common;

public interface CommonField {
    enum Direction {
        L,U,R,D
    }
    boolean canMove();
    CommonMazeObject get();
    boolean isEmpty();
    CommonField nextField(CommonField.Direction dir);
    boolean put(CommonMazeObject object);
    boolean remove(CommonMazeObject object);
    void setMaze(CommonMaze maze);
    void notifyObservers();

}
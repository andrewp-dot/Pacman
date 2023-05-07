package game.astar;

import game.common.Field;

import java.util.ArrayList;

/**
 * @author Adrián Ponechal
 *
 * Field node used for a-star algorithm
 */
public class FieldNode {
    private final Field field;
    private final FieldNode prevNode;
    private double fcost;

    /**
     * Creates field node
     * @param field field to be put
     * @param prevNode reference on previous node
     * @param start starting node
     * @param end goal node
     */
    public FieldNode(Field field, FieldNode prevNode, Field start, Field end)
    {
        this.field = field;
        this.prevNode = prevNode;
        if(start == null && end == null) this.fcost = 0;
    }

    public void setCost(double cost) { this.fcost = cost; }
    public double getCost() { return this.fcost; }

    /**
     * Gets previous node
     * @return FieldNode prevNode
     */
    public FieldNode getPreviousNode() { return this.prevNode; }

    /**
     * Gets current node
     * @return Field current field
     */
    public Field getCurrentField() { return this.field; }

}

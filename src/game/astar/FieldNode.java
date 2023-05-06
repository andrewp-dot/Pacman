package game.astar;

import game.common.Field;

public class FieldNode {
    private Field field;
    private FieldNode prevNode;

    public FieldNode(Field field, FieldNode prevNode)
    {
        this.field = field;
        this.prevNode = prevNode;
    }

    /**
     * Gets previous node
     * @return FieldNode prevNode
     */
    public FieldNode getPreviousNode() { return this.prevNode; }

    /**
     * Gets current node
     * @return Field field
     */
    public Field getCurrentField() { return this.field; }

}

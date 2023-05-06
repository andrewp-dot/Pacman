package game.astar;

import game.common.Field;
import game.fields.WallField;

import java.security.PublicKey;
import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * A-star algorithm for pathfinding
 */
public class Astar {
    private ArrayList<FieldNode> open;
    private ArrayList<FieldNode> closed;
    private final Field[][] maze;
    private final Field start;
    private final Field goal;

    public Astar(Field[][] maze,Field start, Field goal)
    {
        this.maze = maze;
        this.start = start;
        this.goal = goal;
    }
    /**
     * A-star pathfinding algorithm
     * @return list of moves to the goal
     */
    //public ArrayList<Field.Direction> aStar()
    public void aStar()
    {
        open.add(new FieldNode(start,null, start,goal));

        while (!open.isEmpty())
        {
            FieldNode currentNode = getLowestCostNode();

            //remove currentNode from open and add it to close
            open.remove(currentNode);
            closed.add(currentNode);

            if(currentNode.getCurrentField().equals(goal))  return;

            //for child in children
            addReachablesToOpen(currentNode);
        }
    }

    /**
     * Finds node with lowest f-cost
     * @return node
     */
    private FieldNode getLowestCostNode()
    {
        FieldNode min = open.get(0);
        for(FieldNode node: open)
        {
            if(min.getCost() > node.getCost()) min = node;
        }
        return min;
    }

    /**
     * Adds all reachable nodes to open list
     * @param node
     */
    private void addReachablesToOpen(FieldNode node)
    {
        int currentRow = node.getCurrentField().getRow();
        int currentCol = node.getCurrentField().getCol();

        Field left = this.getField(currentRow,currentCol - 1);
        Field right = this.getField(currentRow,currentCol + 1);
        Field up = this.getField(currentRow - 1,currentCol);
        Field down = this.getField(currentRow + 1,currentCol);

        //init array of children
        Field[] children = new Field[4];
        children[0] = left;
        children[1] = up;
        children[2] = right;
        children[3] = down;

        for(Field child: children)
        {
            if(this.isInClosed(child)) continue;
            if(!(child instanceof WallField))
            {
                FieldNode current = new FieldNode(child,node,start,goal);
                double gCost = computeGCost(current.getCurrentField(),start) + computeDistance(child,node.getCurrentField());
                double hCost = computeHCost(current.getCurrentField(),goal);
                current.setCost(gCost + hCost);

                FieldNode clone = findInOpen(child);
                if(clone != null)
                {
                    if (clone.getCost() < current.getCost()) continue;
                }
                this.open.add(current);
            }
        }
    }

    /**
     * gets field from maze
     * @param row
     * @param num
     * @return field
     */
    public Field getField(int row, int num)
    {
        return this.maze[row][num];
    }

    /**
     * finds out if the field node is in closed
     * @param field
     * @return
     */
    private boolean isInClosed(Field field)
    {
        for (FieldNode node: closed)
        {
            if(field.equals(node.getCurrentField())) return true;
        }
        return false;
    }

    /**
     *
     */
    private FieldNode findInOpen(Field field)
    {
        for (FieldNode node: open)
        {
            if(field.equals(node.getCurrentField())) return node;
        }
        return null;
    }


    /**
     * computes distance from starting node
     * @param start start position
     * @return gCost value
     */
    private double computeGCost(Field current, Field start){ return computeDistance(current,start); }

    /**
     * computes distance from ending node
     * @param end goal position
     * @return hCost value
     */
    private double computeHCost(Field current, Field end) { return computeDistance(current,end);}

    /**
     * computes distance of given field using Pythagorean theorem
     * @param goal given field
     * @return distance
     */
    public double computeDistance(Field current, Field goal)
    {
        float horizontalSide = abs(current.getCol() - goal.getCol());
        float verticalSide = abs(current.getRow() - goal.getRow());
        return sqrt(horizontalSide*horizontalSide + verticalSide*verticalSide);
    }

    /**
     * Get array of fields
     * @param current
     * @return
     */
    public ArrayList<Field> getPathForDisplay(FieldNode current)
    {
        ArrayList<Field> path = new ArrayList<Field>(0);
        while (current != null)
        {
            path.add(current.getCurrentField());
            current = current.getPreviousNode();
        }
        return path;
    }
    /*
    /**
     * Gets path
     * @return array of moves

    public ArrayList<Field.Direction> getPath(FieldNode current)
    {
        ArrayList<Field.Direction> path = new ArrayList<Field.Direction>(0);
        while (current != null)
        {
            System.out.println("[" + current.field.getRow() + "," + current.field.getCol() + "]");
            current = current.prevNode;
        }
        return null;
    }

    /**
     * Converts path to move
     * @param prev
     * @param current
     * @return

    private Field.Direction getDirection(Field prev, Field current)
    {

    }
    */
}

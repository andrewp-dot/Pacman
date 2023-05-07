package game.astar;

import game.common.Field;
import game.fields.WallField;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.*;

/**
 * @author Adrián Ponechal
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
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
    }
    /**
     * A-star pathfinding algorithm
     * @return list of moves to the goal
     */
    public  ArrayList<Field.Direction> aStar()
    {
        if(this.start.equals(this.goal)) return null;

        open.add(new FieldNode(start,null, start,goal));

        while (!open.isEmpty())
        {
            FieldNode currentNode = getLowestCostNode();

            //remove currentNode from open and add it to close
            open.remove(currentNode);
            closed.add(currentNode);

            if(currentNode.getCurrentField().equals(goal))  return getPathForDisplay(currentNode);

            //for child in children
            addReachablesToOpen(currentNode);
        }
        return null;
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
     * Finds the node in open list
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
    public ArrayList<Field.Direction> getPathForDisplay(FieldNode current)
    {
        ArrayList<Field> path = new ArrayList<Field>(0);
        while (current != null)
        {
            path.add(current.getCurrentField());
            current = current.getPreviousNode();
        }
        // reverse
        Collections.reverse(path);
        ArrayList<Field.Direction> moves = new ArrayList<>();
        for(int i = 0; i + 1 < path.size(); i++)
        {
            Field.Direction dir;
            try{
                dir = getDirection(path.get(i),path.get(i + 1));
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                return null;
            }
            moves.add(dir);
        }
        return moves;
    }

    /**
     * Converts path to move
     * @param current current position
     * @param next next position
     * @return direction
    */
    private Field.Direction getDirection(Field current, Field next) throws Exception
    {
        int horizontalDir = current.getCol() - next.getCol();
        int verticalDir = current.getRow() - next.getRow();
        if(horizontalDir != 0 && verticalDir != 0)
        {
            throw new Exception("[A-star]: Unsupported direction movement.");
        }

        if (horizontalDir != 0) return horizontalDir < 0 ? Field.Direction.R : Field.Direction.L;
        else if(verticalDir != 0) return verticalDir > 0 ? Field.Direction.U : Field.Direction.D;
        return null;
    }

}

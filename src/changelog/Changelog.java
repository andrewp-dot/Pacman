package changelog;

import game.MazeClass;
import game.MazeConfigure;

import java.io.*;
import java.util.ArrayList;

public class Changelog {
    ArrayList<Coordinates> pacmanCoords;
    ArrayList<ArrayList<Coordinates>> ghostsCoors;
    ArrayList<ArrayList<Boolean>> keysArePicked;
    ArrayList<Integer> pacmanLives;

    MazeClass maze;

    public Changelog() {
        this.pacmanCoords = new ArrayList<>();
        this.ghostsCoors = new ArrayList<>();
        this.keysArePicked = new ArrayList<>();
        this.pacmanLives = new ArrayList<>();
        this.maze = null;
    }

    void Parse(File file) throws Exception {
        MazeConfigure conf = new MazeConfigure();
        FileReader fr = new FileReader(file);
        BufferedReader buffr = new BufferedReader(fr);

        if (!buffr.ready()) {
            throw new Exception("File is empty.");
        }

        String line = buffr.readLine();
        String[] rowsCols = line.split(" ");
        if (rowsCols.length != 2) {
            throw new Exception("Invalid maze map format.");
        }

        int rows = Integer.parseInt(rowsCols[0]);
        int cols = Integer.parseInt(rowsCols[1]);
        conf.startReading(rows, cols);
        for (int i = 0; i < rows && buffr.ready(); i++) {
            if (!conf.processLine(buffr.readLine())) {
                throw new Exception("Invalid maze map format.");
            }
        }

        if (!conf.stopReading()) {
            throw new Exception("Invalid maze map format.");
        }
        maze = conf.createMaze(); // maze part was successfully

        // create ArrayList for each ghost
        for (int i = 0; i < maze.getGhosts().size(); i++) {
            this.ghostsCoors.add(new ArrayList<>());
        }
        // create ArrayList for each key
        for (int i = 0; i < maze.getKeys().size(); i++) {
            this.keysArePicked.add(new ArrayList<>());
        }

        // load the lines
        // line format pac_coord ghosts_coord keys_isPicked pacman_lives
        // coord format "\d+ \d+"
        // key_isPicked "Picked|Active"

        int valueCount = (1 + maze.getGhosts().size()) * 2 + maze.getKeys().size() + 1; // number of items for each line
        int valueIndex = 0;
        while (buffr.ready()) {
            String[] values = buffr.readLine().split(" ");
            if (values.length != valueCount) {
                throw new Exception("Invalid Changelog format");
            }

            // pacman_coord
            this.pacmanCoords.add(new Coordinates(
                    Integer.parseInt(values[valueIndex]),
                    Integer.parseInt(values[valueIndex + 1])));
            valueIndex += 2;

            // ghost_coord
            for (int i = 0; i < maze.getGhosts().size(); i++) {
                this.ghostsCoors.get(i).add(new Coordinates(
                        Integer.parseInt(values[valueIndex]),
                        Integer.parseInt(values[valueIndex + 1])));
                valueIndex += 2;
            }

            // keys_isPicked
            for (int i = 0; i < maze.getKeys().size(); i++) {
                this.keysArePicked.get(i).add(values[valueIndex].equals("Picked"));
                valueIndex++;
            }

            // pacman_lives
            this.pacmanLives.add(Integer.parseInt(values[valueIndex]));
        }
        buffr.close();
    }
}

package view;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* javafx */
import view.menu.Menu;

public class LevelMenu extends Menu {

    ArrayList<String> maps = new ArrayList<>(0);
    public LevelMenu(int minWidth,int minHeight)
    {
        super(minWidth,minHeight);
        this.loadMaps();
    }

    private void loadMaps()
    {
        File src = new File("src");
        File mapFolder = new File(src,"maps");
        File[] listOfFiles = mapFolder.listFiles();
        if(listOfFiles == null) return;

        this.parseMaps(listOfFiles);

        // add options
        this.sortOptions();
        for(String map: maps)
        {
            this.addOption(map);
        }
        //printOptions();
    }

    /**
     * Filters maps from files in "maps" folder
     * @param listOfFiles - files in "maps" folder
     */
    private void parseMaps(File[] listOfFiles)
    {
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();

            if(fileName.matches("^map[0-9]*.txt$"))
            {
                System.out.println("Map found " + fileName);
                maps.add(fileName);
            }
        }
    }

    /**
     * Print options of menu
     */
    private void printOptions()
    {
        for(String map: maps)
        {
            System.out.println(map);
        }
    }

    private void sortOptions()
    {
        Collections.sort(this.maps, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                final Pattern lvlNums = Pattern.compile("\\d+");
                Matcher n1 = lvlNums.matcher(s1);
                Matcher n2 = lvlNums.matcher(s2);

                // exception here and exit
                if(n1.find() && n2.find())
                {
                    return  Integer.parseInt(n1.group()) - Integer.parseInt(n2.group());
                }
                else return 0;

            }
        });
    }

    public List<String> getMaps() { return this.maps; }

}



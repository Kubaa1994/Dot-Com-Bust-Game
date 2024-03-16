
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class DotComBust2 {

    private ArrayList<DotCom> dotComs = new ArrayList<>();

    public static void main(String[] args) {
        DotComBust2 game = new DotComBust2();
        game.setUpGame();

        game.revealDotComLocations();

        while (!game.dotComs.isEmpty()) {
            String userGuess = game.getUserInput("Enter a guess: ");
            String result = game.checkUserGuess(userGuess);
            System.out.println(result);
        }

        game.finishGame();
    }

    private String checkUserGuess(String userGuess) {
        String result = "Miss";
        Iterator<DotCom> iterator = dotComs.iterator();

        while (iterator.hasNext()) {
            DotCom dotCom = iterator.next();
            String dotComResult = dotCom.checkYourself(userGuess);

            if (dotComResult.equals("Hit")) {
                result = "Hit";
            } else if (dotComResult.equals("Kill")) {
                result = "Kill";
                iterator.remove();
                break;
            }
        }

        return result;
    }

    private void finishGame() {
        System.out.println("All DotComs are dead! Your stock is now worthless.");
    }

    private String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private void setUpGame() {
        DotCom dotCom1 = new DotCom("Pets.com");
        DotCom dotCom2 = new DotCom("eToys.com");
        DotCom dotCom3 = new DotCom("Go2.com");

        dotComs.add(dotCom1);
        dotComs.add(dotCom2);
        dotComs.add(dotCom3);

        GameHelper helper = new GameHelper();
        for (DotCom dotCom : dotComs) {
            ArrayList<String> locations = helper.placeDotCom(3);
            dotCom.setLocationCells(locations);
        }
    }

    // Cheat method to reveal DotCom locations
    private void revealDotComLocations() {
        for (DotCom dotCom : dotComs) {
            System.out.println(dotCom.getName() + " locations: " + dotCom.getLocationCells());
        }
    }

    class DotCom {
        private String name;
        private ArrayList<String> locationCells;

        public void setLocationCells(ArrayList<String> locations) {
            locationCells = locations;
        }

        public String checkYourself(String userInput) {
            String result = "Miss";
            int index = locationCells.indexOf(userInput);
            if (index >= 0) {
                locationCells.remove(index);
                if (locationCells.isEmpty()) {
                    result = "Kill";
                } else {
                    result = "Hit";
                }
            }
            return result;
        }

        public DotCom(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public ArrayList<String> getLocationCells() {
            return locationCells;
        }
    }

    class GameHelper {
        private static final String alphabet = "abcdefg";
        private int gridLength = 7;
        private int gridSize = 49;
        private int[] grid = new int[gridSize];

        public ArrayList<String> placeDotCom(int size) {
            ArrayList<String> alphaCells = new ArrayList<>();
            String temp;
            int[] coords = new int[size];
            int attempts = 0;
            boolean success;

            int incr = 1;
            if ((size % 2) == 1) {
                incr = gridLength;
            }

            while (attempts++ < 200) {
                int location = (int) (Math.random() * gridSize);
                int x = 0;
                success = true;

                while (success && x < size) {
                    if (grid[location] == 0) {
                        coords[x++] = location;
                        location += incr;

                        if (location >= gridSize) {
                            success = false;
                        }

                        if (x > 0 && (location % gridLength == 0)) {
                            success = false;
                        }
                    } else {
                        success = false;
                    }
                }
            }

            int x = 0;
            int row, column;
            while (x < size) {
                grid[coords[x]] = 1;
                row = coords[x] / gridLength;
                column = coords[x] % gridLength;
                temp = String.valueOf(alphabet.charAt(column));
                alphaCells.add(temp.concat(Integer.toString(row)));
                x++;
            }

            return alphaCells;
        }
    }
}

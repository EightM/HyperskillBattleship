package battleship;

import battleship.ships.Ship;

import java.util.*;

public class Board {

    private final String[][] gameBoard;
    private final Map<String, Integer> rowMapping = new HashMap<>();
    private final int boardSize;
    private static final String FREE_CELL = "~";
    private static final String SHIP_CELL = "O";
    private static final String MISS_CELL = "M";
    private static final String HIT_CELL = "X";
    private final String[][] fightBoard;
    private final Map<Ship, Set<Point>> ships = new HashMap<>();

    

    public Board(int size) {
        this.boardSize = size;
        gameBoard = new String[boardSize][boardSize];
        fightBoard = new String[boardSize][boardSize];
        fillEmptyBoard();
        fillRowMapping();
    }

    public void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowChar = 'A';
        for (String[] strings : gameBoard) {
            System.out.print(rowChar + " ");
            System.out.println(String.join(" ", strings));
            rowChar++;
        }
    }

    private void printBattleField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowChar = 'A';
        for (String[] strings : fightBoard) {
            System.out.print(rowChar + " ");
            System.out.println(String.join(" ", strings));
            rowChar++;
        }
    }

    public void placeShip(Ship ship) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the coordinates of the %s (%d cells)%n",
                ship.getName(), ship.getSize());
        String firstCoordinate = scanner.next();
        String secondCoordinate = scanner.next();
        placeShipOnBoard(firstCoordinate, secondCoordinate, ship);
    }

    public void takeAShot(Board enemyBoard) {
        Scanner scanner = new Scanner(System.in);

        String coordinate = scanner.nextLine();
        if (!checkShotCoordinate(coordinate, enemyBoard)) {
            return;
        }

        Point shot = getPointFromCoordinates(coordinate);
        placeShotOnBoard(shot, enemyBoard);
    }

    private void placeShotOnBoard(Point shot, Board enemyBoard) {
        if (enemyBoard.gameBoard[shot.getY()][shot.getX()].equals(FREE_CELL)) {
            enemyBoard.gameBoard[shot.getY()][shot.getX()] = MISS_CELL;
            fightBoard[shot.getY()][shot.getX()] = MISS_CELL;
            System.out.println("You missed!");
        } else {
            enemyBoard.gameBoard[shot.getY()][shot.getX()] = HIT_CELL;
            fightBoard[shot.getY()][shot.getX()] = HIT_CELL;
            hitShip(shot, enemyBoard);
        }
    }

    private void hitShip(Point shot, Board enemyBoard) {
        enemyBoard.ships.values().forEach(points -> points.remove(shot));
        var iterator = enemyBoard.ships.keySet().iterator();

        while (iterator.hasNext()) {
            var ship = iterator.next();
            if (enemyBoard.ships.get(ship).isEmpty()) {
                System.out.println("You sank a ship! Specify a new target:");
                iterator.remove();
                return;
            }
        }

        System.out.println("You hit she ship!");
    }

    private boolean checkShotCoordinate(String coordinate, Board enemyBoard) {
        if (rowMapping.get(coordinate.substring(0, 1)) == null || Integer.parseInt(coordinate.substring(1)) > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            takeAShot(enemyBoard);
            return false;
        }

        return true;
    }

    private void fillRowMapping() {
        char rowChar = 'A';
        for (int i = 0; i < boardSize; i++) {
            rowMapping.put(String.valueOf(rowChar), i);
            rowChar++;
        }
    }

    private void fillEmptyBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                gameBoard[i][j] = FREE_CELL;
                fightBoard[i][j] = FREE_CELL;
            }
        }
    }

    private void placeShipOnBoard(String firstCoordinate, String secondCoordinate, Ship ship) {
        Point firstPoint = getPointFromCoordinates(firstCoordinate);
        Point secondPoint = getPointFromCoordinates(secondCoordinate);
        swapPointsIfNeeded(firstPoint, secondPoint);
        if (!checkPlacement(firstPoint, secondPoint, ship)) {
            return;
        }

        boolean isShipHorizontal = isShipHorizontal(firstPoint, secondPoint);
        drawShipOnBoard(firstPoint, secondPoint, isShipHorizontal, ship);
        printField();
    }

    private void swapPointsIfNeeded(Point firstPoint, Point secondPoint) {
        if (firstPoint.getX() > secondPoint.getX() || firstPoint.getY() > secondPoint.getY()) {
            Point tempPoint = new Point(firstPoint.getX(), firstPoint.getY());
            firstPoint.setX(secondPoint.getX());
            firstPoint.setY(secondPoint.getY());

            secondPoint.setX(tempPoint.getX());
            secondPoint.setY(tempPoint.getY());
        }
    }

    private void drawShipOnBoard(Point firstPoint, Point secondPoint, boolean isShipHorizontal, Ship ship) {
        ships.putIfAbsent(ship, new HashSet<>());
        if (isShipHorizontal) {
            for (int i = firstPoint.getX(); i <= secondPoint.getX(); i++) {
                gameBoard[firstPoint.getY()][i] = SHIP_CELL;
                ships.get(ship).add(new Point(i, firstPoint.getY()));
            }
        } else {
            for (int i = firstPoint.getY(); i <= secondPoint.getY(); i++) {
                gameBoard[i][firstPoint.getX()] = SHIP_CELL;
                ships.get(ship).add(new Point(firstPoint.getX(), i));
            }
        }
    }

    private boolean checkPlacement(Point firstPoint, Point secondPoint, Ship ship) {

        if (isShipBiggerThanShould(firstPoint, secondPoint, ship) || isShipLesserThanShould(firstPoint, secondPoint, ship)) {
            System.out.printf("Error! Wrong length of the %s! Try again:%n",
                    ship.getName());
            this.placeShip(ship);
            return false;
        } else if (firstPoint.getX() != secondPoint.getX() && firstPoint.getY() != secondPoint.getY()) {
            System.out.printf("Error! Wrong ship location! Try again:%n");
            this.placeShip(ship);
            return false;
        } else if (!checkBorders(firstPoint, secondPoint)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            this.placeShip(ship);
            return false;
        }

        return true;
    }

    private boolean isShipBiggerThanShould(Point firstPoint, Point secondPoint, Ship ship) {
        return (secondPoint.getX() - firstPoint.getX() > ship.getSize() - 1)
                || (secondPoint.getY() - firstPoint.getY() > ship.getSize() - 1);
    }

    private boolean isShipLesserThanShould(Point firstPoint, Point secondPoint, Ship ship) {
        return (secondPoint.getX() - firstPoint.getX() < ship.getSize() - 1)
                && (secondPoint.getY() - firstPoint.getY() < ship.getSize() - 1);
    }

    private boolean checkBorders(Point firstPoint, Point secondPoint) {
        Point leftUpperCorner = getLeftUpperCornerOfCheckingArea(firstPoint);
        Point bottomRightCorner = getBottomRightCornerOfCheckingArea(secondPoint);
        for (int i = leftUpperCorner.getY(); i <= bottomRightCorner.getY(); i++) {
            for (int j = leftUpperCorner.getX(); j <= bottomRightCorner.getX(); j++) {
                if (!gameBoard[i][j].equals(FREE_CELL)) {
                    return false;
                }
            }
        }

        return true;
    }

    private Point getBottomRightCornerOfCheckingArea(Point point) {
        int bottomRightCornerX = point.getX() == boardSize - 1 ? point.getX() : point.getX() + 1;
        int bottomRightCornerY = point.getY() == boardSize - 1 ? point.getX() : point.getY() + 1;
        return new Point(bottomRightCornerX, bottomRightCornerY);
    }

    private Point getLeftUpperCornerOfCheckingArea(Point point) {
        int leftUpperCornerX = point.getX() == 0 ? 0 : point.getX() - 1;
        int leftUpperCornerY = point.getY() == 0 ? 0 : point.getY() - 1;
        return new Point(leftUpperCornerX, leftUpperCornerY);
    }

    private boolean isShipHorizontal(Point firstPoint, Point secondPoint) {
        return firstPoint.getY() == secondPoint.getY();
    }

    private Point getPointFromCoordinates(String coordinate) {
        int x = Integer.parseInt(coordinate.substring(1)) - 1;
        int y = rowMapping.get(coordinate.substring(0, 1));
        return new Point(x, y);
    }

    public boolean allShipsDestroyed() {
        return ships.isEmpty();
    }

    public void printAllFields() {
        printBattleField();
        System.out.println("---------------------");
        printField();
    }
}

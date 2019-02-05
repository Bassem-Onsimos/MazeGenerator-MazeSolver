package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class MazeConverter extends JPanel{

    private int rows, columns;
    private int size;
    private Random rand = new Random();

    private Wall[][] tiles;
    private Cell[][] cells;
    private Cell[] grid;

    private int startY;
    private int endY;

    public MazeConverter(BlockWiseMazeGenerator mazeGen, JFrame frame) {
        this.rows = 2 * (mazeGen.getRows()) + 1;
        this.columns = 2 * (mazeGen.getColumns()) + 1;
        tiles = new Wall[this.columns][this.rows];
        cells = new Cell[this.columns][this.rows];
        this.grid = mazeGen.getGrid();
        this.size = mazeGen.getTileSize()/2;
        
        
        setPreferredSize(new Dimension(this.columns*size, this.rows*size));
        setMaximumSize(new Dimension(this.columns*size, this.rows*size));
        setMinimumSize(new Dimension(this.columns*size, this.rows*size));

        frame.add(this);
        frame.pack();
        //frame.setLocationRelativeTo(null);
        //frame.setLocation(970, 50);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        readGrid();
        repaint();
    }

    public int index(int x, int y) {
        if (x > -1 && x < columns && y > -1 && y < rows) 
            return x + y * columns;
        
        return -1;
    }

    public void readGrid() {

        for (int i = 0; i < grid.length; i++) {
            int x = 2 * grid[i].getColumn() + 1;
            int y = 2 * grid[i].getRow() + 1;

            /*
            int indexA = index(x, y); //always a passage
            int indexB = index(x + 1, y); //depends - right
            int indexC = index(x, y + 1); //depends - bottom
            int indexD = index(x + 1, y + 1); //allways a wall - corner
             */
            
            //cells[x][y] = grid[i];
            
            cells[x][y] = new Cell(x, y, size);
            cells[x][y].setTopWall(grid[i].hasTopWall());
            cells[x][y].setBottomWall(grid[i].hasBottomWall());
            cells[x][y].setRightWall(grid[i].hasRightWall());
            cells[x][y].setLeftWall(grid[i].hasLeftWall());

            tiles[x + 1][y + 1] = new Wall(x+1, y+1, size);

            if (grid[i].hasRightWall()) {
                tiles[x + 1][y] = new Wall(x+1, y, size);
            } else {
                cells[x + 1][y] = new Cell(x + 1, y, size);
                cells[x + 1][y].setTopWall(true);
                cells[x + 1][y].setBottomWall(true);
                cells[x + 1][y].setRightWall(false);
                cells[x + 1][y].setLeftWall(false);
                
            }

            if (grid[i].hasBottomWall()) {
                tiles[x][y + 1] = new Wall(x, y+1, size);
            } else {
                cells[x][y + 1] = new Cell(x, y + 1, size);
                cells[x][y + 1].setTopWall(false);
                cells[x][y + 1].setBottomWall(false);
                cells[x][y + 1].setRightWall(true);
                cells[x][y + 1].setLeftWall(true);

            }

        }
        
        for (int i = 0; i < rows; i++) {
            tiles[0][i] = new Wall(0, i, size);
            if(cells[1][i]!=null)
                cells[1][i].setLeftWall(true);
        }

        for (int i = 0; i < columns; i++) {
            tiles[i][0] = new Wall(i, 0, size);
            if(cells[i][1]!=null)
                cells[i][1].setTopWall(true);
        }

        startY = rand.nextInt(rows - (3*rows)/4) + rows/4;
        while (startY % 2 != 0 || tiles[1][startY] != null) {
            startY = rand.nextInt(rows - (3*rows)/4) + rows/4;
        }

        tiles[0][startY] = null;
        cells[0][startY] = new Cell(0, startY, size);
        
        cells[0][startY].setRightWall(false);
        cells[0][startY].setTopWall(true);
        cells[0][startY].setBottomWall(true);
        cells[0][startY].setLeftWall(true);
        
        cells[1][startY].setLeftWall(false);
        //cells[index(0, startY)] = new Cell(0, startY, size);

        endY = rand.nextInt(rows - (3*rows)/4) + rows/4;
        while (endY % 2 != 0 || tiles[columns - 2][endY] != null) {
            endY = rand.nextInt(rows - (3*rows)/4) + rows/4;
        }

        tiles[columns - 1][endY] = null;
        cells[columns - 1][endY] = new Cell(columns - 1, endY, size);
        
        cells[columns - 1][endY].setRightWall(true);
        cells[columns - 1][endY].setTopWall(true);
        cells[columns - 1][endY].setBottomWall(true);
        cells[columns - 1][endY].setLeftWall(false);
        
        cells[columns - 2][endY].setRightWall(false);
        //cells[index(columns-1, endY)] = new Cell(columns-1, endY, size);

    }

    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.red);
        g.fillRect(0, 0, columns*size, rows*size);
        
        
        for (int i=0; i<columns; i++) {
            for(int j=0; j<rows; j++){
                if (tiles[i][j] != null)
                    tiles[i][j].draw(g);
            }
           
        }
        
        
        /*
        for (int i=0; i<columns; i++) {
            for(int j=0; j<rows; j++){
                if (cells[i][j] != null)
                    cells[i][j].draw(g);
            }
           
        }
        */
        
        
    }
     
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Wall[][] getWalls() {
        return tiles;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndY() {
        return endY;
    }
    
    public int getTileSize() {
        return size;
    }
    
}


package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

public class DFSMazeSolver extends JPanel {
    
    private Cell[][] cells; 
    private Stack<Cell> stack = new Stack<>();
    private Cell current = null;
    private int rows, columns;
    private int size;
    private int startY, endY;
    private Random rand = new Random();
    
    public DFSMazeSolver(MazeConverter maze, JFrame frame){
        cells = maze.getCells();
        rows = maze.getRows();
        columns = maze.getColumns();
        size = maze.getTileSize();
        startY = maze.getStartY();
        endY = maze.getEndY();
        
        setPreferredSize(new Dimension(this.columns*size, this.rows*size));
        setMaximumSize(new Dimension(this.columns*size, this.rows*size));
        setMinimumSize(new Dimension(this.columns*size, this.rows*size));

        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        unvisitAll();
        repaint();
        solveMaze();
        
    }
    
    public void unvisitAll() {
        for(int i=0; i<columns; i++){
            for(int j=0; j<rows; j++){
                if(cells[i][j] != null)
                    cells[i][j].setVisited(false);
            }
        }
    }
    
    public void solveMaze() {
        
        current = cells[0][startY];
        stack.push(current);
        current.setVisited(true);
        Cell next = null;
        
        while(!stack.isEmpty()){
            
            current = (Cell) stack.peek();
            
            if(mazeSolved(current))
                break;
            
            
            try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
            next = checkNeighbours();
            
            if(next != null) {
                next.setVisited(true);
                stack.push(next);
                repaint();
                
            }
            else {
                stack.pop();
                repaint();
            }
        
        }
        
        if(stack.isEmpty())
            System.out.println("Solving failed");
        else
            System.out.println("solved");

        
    }
    
    public Cell checkNeighbours() {
        
        ArrayList<Cell> neighbours = new ArrayList<>(); 
        
        if(!current.hasTopWall()) {
            Cell cell = getCell(current.getColumn(), current.getRow() - 1);
            if(cell!=null)
                neighbours.add(cell);
        }
        
        if(!current.hasBottomWall()) {
            Cell cell = getCell(current.getColumn(), current.getRow() + 1);
            if(cell!=null)
                neighbours.add(cell);
        }
        
        if(!current.hasLeftWall()) {
            Cell cell = getCell(current.getColumn() - 1, current.getRow());
            if(cell!=null)
                neighbours.add(cell);
        }
        
        if(!current.hasRightWall()) {
            Cell cell = getCell(current.getColumn() + 1, current.getRow());
            if(cell!=null)
                neighbours.add(cell);
        }
        
        if(!neighbours.isEmpty()) {
            
            for(int i=0; i<neighbours.size(); i++){
                if(mazeSolved(neighbours.get(i)))
                    return neighbours.get(i);
            }
            
            return neighbours.get(rand.nextInt(neighbours.size()));
        }
        return null;
    }
    
    public Cell getCell(int x, int y){
        //System.out.println(x +", "+ y);
        if(x>=0 && x<columns && y>=0 && y<rows){
            if(!cells[x][y].isVisited())
                return cells[x][y];
        }
        return null;
    }
    
    public boolean mazeSolved(Cell cell) {
        if(cell.getColumn()==columns-1 && cell.getRow()==endY)
            return true;
        
        else
            return false;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, columns*size, rows*size);
        
        for(int i=0; i<columns; i++) {
            for(int j=0; j<rows; j++) {
                if(cells[i][j] != null){
                
                    if(stack.contains(cells[i][j]))
                        g.setColor(Color.PINK);
                    else
                        g.setColor(Color.red);

                    cells[i][j].drawSolver(g);
                }
            }
        }
        
        if(current!=null){
            g.setColor(Color.yellow);
            current.drawSolver(g);
        }
    }
     
    
}

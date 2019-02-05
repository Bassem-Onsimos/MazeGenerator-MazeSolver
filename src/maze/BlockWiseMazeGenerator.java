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

public class BlockWiseMazeGenerator extends JPanel implements Runnable {

    private int columns;
    private int rows;
    private int size;
    
    private int width;
    private int height;
    
    private Cell[] grid;
    private Cell current;
    private Stack stack = new Stack();
    
    private Random rand = new Random();

    public BlockWiseMazeGenerator(int columns, int rows, int size, JFrame frame) {
        this.columns = columns;
        this.rows = rows;
        this.size = size;
        
        width = columns*size + size/2;
        height = rows*size + size/2;
        
        grid = new Cell[rows*columns];
        int n = 0;
        
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                grid[n++] = new Cell(j, i, size);
            }
        }
        
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLocation(7, 0);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        this.repaint();
        generateMaze();
        
    }
    
    public void generateMaze(){
        
        current = grid[index(0, 0)];
        current.setVisited(true);
        stack.push(current);
        Cell next = null;

        while(!stack.isEmpty()){
            
            current = (Cell) stack.peek();
            
            try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
            
            next = checkNeighbours(current);
            if(next!=null){
               
                //System.out.println(next.getColumn() +", "+next.getRow());
               
                next.setVisited(true);
                stack.push(next);
                removeWalls(current, next);
                this.repaint();
            }
            else{
                stack.pop();
                this.repaint();
            }
        }
    }
    
    public int index(int x, int y){
        int index;
        if(x>-1 && x<columns && y>-1 && y<rows)
            index = x + y*columns;          
        else
            index = -1;
            
        //System.out.println(index + " - " + x + ", " + y);
        return index;
    }
    
    public Cell checkNeighbours(Cell current){
        ArrayList<Cell> neighbours = new ArrayList<>();
        
        int topIndex = index(current.getColumn(), current.getRow()-1);
        if(topIndex!=-1){
            Cell Top = grid[topIndex];
            if(!Top.isVisited())
                neighbours.add(Top);
        }
        
        int bottomIndex = index(current.getColumn(), current.getRow()+1);
        if(bottomIndex!=-1){
            Cell Bottom = grid[bottomIndex];
            if(!Bottom.isVisited())
                neighbours.add(Bottom);
        }
        
        int leftIndex = index(current.getColumn()-1, current.getRow());        
        if(leftIndex!=-1){
            Cell Left = grid[leftIndex];
            if(!Left.isVisited())
                neighbours.add(Left);
        }
        
        int rightIndex = index(current.getColumn()+1, current.getRow());
        if(rightIndex!=-1){
            Cell Right = grid[rightIndex];
            if(!Right.isVisited())
                neighbours.add(Right);
        }
        
        if(!neighbours.isEmpty())
            return neighbours.get(rand.nextInt(neighbours.size()));
       
        else
            return null;
        
    }
    
    public void removeWalls(Cell a, Cell b){
        int x = a.getColumn()-b.getColumn();
        if(x==1){
           a.setLeftWall(false);
           b.setRightWall(false);
        }else if(x==-1){
           a.setRightWall(false);
           b.setLeftWall(false);            
        }
        
        int y = a.getRow()-b.getRow();
        if(y==1){
           a.setTopWall(false);
           b.setBottomWall(false);
        }else if(y==-1){
           a.setBottomWall(false);
           b.setTopWall(false);            
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.black);
        g.fillRect(0, 0, width, size/2);
        g.fillRect(0, 0, size/2, height);
        
        g.setColor(Color.white);
        g.fillRect(size/2, size/2, columns*size, rows*size);
        
        for(int i=0; i<grid.length; i++){
            grid[i].drawBlockWise(g);
        }
        
        if(current!=null){
            g.setColor(Color.yellow);
            g.fillRect(current.getX()+size/2, current.getY()+size/2, size/2, size/2);
        }

    }

    public Cell[] getGrid() {
        return grid;
    }

    @Override
    public void run() {
        generateMaze();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getTileSize() {
        return size;
    }
    
    
}

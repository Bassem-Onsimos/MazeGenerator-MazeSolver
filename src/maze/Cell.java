
package maze;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
    
    private int x, y, size;
    private int row, column;
    private boolean visited = false;
    private boolean topWall = true;
    private boolean rightWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;
    
    public Cell(int column, int row, int size){
        this.column = column;
        this.row = row;
        this.x = column*size;
        this.y = row*size;
        this.size = size;
    }
    
    public void drawSolver(Graphics g) {
        g.fillRect(x, y, size, size);
    }
    
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, size, size);
        
        g.setColor(Color.black);
        
       if(topWall)
           g.fillRect(x, y-size, size, size);
       
       if(bottomWall)
           g.fillRect(x, y+size, size, size);
       
       if(leftWall)
           g.fillRect(x-size, y, size, size);
       
       if(rightWall)
           g.fillRect(x+size, y, size, size);
        
        
    }
    
    public void drawLineWise(Graphics g){
       
       if(visited){
           //g.setColor(Color.red);
           g.fillRect(x, y, size, size);
       }
        
       g.setColor(Color.black);
        
       if(topWall)
           g.drawLine(x, y, x+size, y);
       
       if(bottomWall)
           g.drawLine(x, y+size, x+size, y+size);
       
       if(leftWall)
           g.drawLine(x, y, x, y+size);
       
       if(rightWall)
           g.drawLine(x+size, y, x+size, y+size);
       
    }
    
    public void drawBlockWise(Graphics g){
        int x = this.x + size/2;
        int y = this.y + size/2;
        
       if(visited){
           g.setColor(Color.red);
           g.fillRect(x, y, size/2, size/2);
       }
       
       if(bottomWall)
           g.setColor(Color.black);
       else
           g.setColor(Color.red);
        
        g.fillRect(x, y+size/2, size/2, size/2);
       
       if(rightWall)
           g.setColor(Color.black);
       else
           g.setColor(Color.red);
       
       g.fillRect(x+size/2, y, size/2, size/2);
       
       
       g.setColor(Color.black);
       g.fillRect(x+size/2, y+size/2, size/2, size/2);
       
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    
    public void setVisited(boolean visited){
        this.visited = visited;
    }
    
    public boolean isVisited(){
        return visited;
    }

    public void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    public boolean hasTopWall() {
        return topWall;
    }

    public boolean hasRightWall() {
        return rightWall;
    }

    public boolean hasBottomWall() {
        return bottomWall;
    }

    public boolean hasLeftWall() {
        return leftWall;
    }
    
    
    
    
}

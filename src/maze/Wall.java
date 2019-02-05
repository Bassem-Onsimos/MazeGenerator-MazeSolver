/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Color;
import java.awt.Graphics;



public class Wall {
  
    private int x, y;
    private int size;
    
    public Wall(int column, int row, int size){
        this.x = column*size;
        this.y = row*size;
        this.size = size;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x, y, size, size);
    }
}

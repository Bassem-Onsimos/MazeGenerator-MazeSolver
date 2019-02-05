
package maze;

import javax.swing.JFrame;

public class Initialize {
    
     public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        
        BlockWiseMazeGenerator mazeGen = new BlockWiseMazeGenerator(20, 20, 30, frame);
        MazeConverter maze = new MazeConverter(mazeGen, frame);
        DFSMazeSolver mazeSolver = new DFSMazeSolver(maze, frame);
           
    }
     
}

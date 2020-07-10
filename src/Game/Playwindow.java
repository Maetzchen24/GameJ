package Game;
import javax.swing.*;

public class Playwindow extends JFrame {


    public static void main(String[] args) {
        JFrame Obj = new JFrame();
        Gameplay Gp = new Gameplay(Obj);

        //Window settings
        Obj.setBounds(600, 200 ,700, 600);
        Obj.setTitle("GameJ");
        Obj.setResizable(false);
        Obj.setVisible(true);
        Obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Obj.add(Gp);
    }
}

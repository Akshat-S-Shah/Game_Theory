import javax.swing.JFrame;

public class BrickBreaker {
    public static void main(String[] args)
    {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10,10,1450,835);
        obj.setTitle("Bouncing Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);

    }
}
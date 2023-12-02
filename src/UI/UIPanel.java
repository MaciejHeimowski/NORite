package UI;

import javax.swing.*;
import java.awt.*;

public abstract class UIPanel extends JPanel {
    boolean color = false;
    public UIPanel(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setBackground(Color.BLACK);

        System.out.println(this.getHeight());
    }
}

package UI;

import java.awt.*;

public class MenuBar extends UIPanel {
    public MenuBar() {
        super(0, 0, 1024, 32);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        super.paint(g);

        paintBase(g2);
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,30, 1024, 2);
    }
}

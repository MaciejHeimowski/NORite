package UI;

import java.awt.*;

public class Editor extends UIPanel {
    public Editor() {
        super(0, 32, 896, 736);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        super.paint(g);

        paintBase(g2);
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, 1024, 2);
        g2.fillRect(894,0, 2, 736);
    }
}

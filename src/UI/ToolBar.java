package UI;

import java.awt.*;

public class ToolBar extends UIPanel {
    public ToolBar() {
        super(vertBarX, horizBarY, gameWidth - vertBarX, gameHeight - horizBarY);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2 * barThickness));

        super.paint(g2);

        this.paintBase(g2);
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, gameWidth - vertBarX, barThickness);
        g2.fillRect(0,0, barThickness, gameHeight - horizBarY);
    }
}

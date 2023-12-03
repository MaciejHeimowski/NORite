package UI;

import java.awt.*;

public class MenuBar extends UIPanel {
    public MenuBar() {
        super(0, 0, gameWidth, horizBarY);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2 * barThickness));

        super.paint(g);

        this.paintBase(g2);
        this.paintOptions(g2);
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,horizBarY - barThickness, gameWidth, barThickness);
    }

    private void paintOptions(Graphics2D g2) {
        g2.setColor(Color.WHITE);

        g2.drawRect(2 * barThickness, 2 * barThickness, gameWidth / 10, horizBarY - (5 * barThickness));
        g2.drawRect(gameWidth / 10 + (5 * barThickness),2 * barThickness, gameWidth / 10, horizBarY - (5 * barThickness));
        g2.drawRect(gameWidth / 5 + (8 * barThickness),2 * barThickness, gameWidth / 10, horizBarY - (5 * barThickness));

        g2.setColor(new Color(0, 255, 168));

        Polygon startButton = new Polygon(
            new int[] {3 * gameWidth / 10 + (11 * barThickness),
            3 * gameWidth / 10 + (11 * barThickness) + (gameWidth / 30),
            3 * gameWidth / 10 + (11 * barThickness)},
            new int[] {
                    3 * barThickness,
                    (horizBarY - (4 * barThickness) + (3 * barThickness)) / 2,
                    horizBarY - (4 * barThickness)
            },
   3
        );

        g2.drawPolygon(startButton);

        g2.setColor(new Color(255, 32, 64));

        g2.fillOval((int)(startButton.getBounds2D().getX() + startButton.getBounds2D().getWidth() + (5 * barThickness)), 3 * barThickness, horizBarY - (7 * barThickness), horizBarY - (7 * barThickness));

        g2.setColor(Color.WHITE);
    }
}

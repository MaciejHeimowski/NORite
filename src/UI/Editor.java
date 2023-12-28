package UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Core.Elements.*;

public class Editor extends UIPanel {

    private final int tileSize = 20;

    private Tile[][] map;

    public Editor() {
        super(0, horizBarY, vertBarX, gameHeight - horizBarY);

        map = new Tile[((gameHeight - horizBarY - 2) / tileSize) - 2][(vertBarX - 2) / tileSize];

        for(int i = 0; i < (vertBarX - 2) / tileSize; i++) {
            for(int j = 0; j < ((gameHeight - horizBarY - 2) / tileSize) - 2; j++) {
                map[j][i] = new Empty();
            }
        }

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println(((me.getX() - 10) / tileSize) + " " + ((me.getY() - 10) / tileSize));
            }
        });

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2 * barThickness));

        super.paint(g2);

        this.paintBase(g2);

        for(int i = 0; i < (vertBarX - 2) / tileSize; i++) {
            for(int j = 0; j < ((gameHeight - horizBarY - 2) / tileSize) - 2; j++) {
                g2.setColor(map[j][i].getColor());
                g2.fillRect(10 + tileSize * i, 10 + tileSize *  j + 2, tileSize, tileSize);
            }
        }
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, vertBarX, barThickness);
        g2.fillRect(vertBarX - barThickness, 0, barThickness, gameHeight - horizBarY);
    }
}

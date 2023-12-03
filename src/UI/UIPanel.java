package UI;

import javax.swing.*;
import java.awt.*;

public abstract class UIPanel extends JPanel {
    protected static int gameWidth, gameHeight, horizBarY, vertBarX, barThickness;
    public UIPanel(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setBackground(Color.BLACK);

        System.out.println(this.getHeight());
    }

    public static void setParameters(int gameWidth, int gameHeight, int horizBarY, int vertBarX, int barThickness) {
        UIPanel.gameWidth = gameWidth;
        UIPanel.gameHeight = gameHeight;
        UIPanel.horizBarY = horizBarY;
        UIPanel.vertBarX = vertBarX;
        UIPanel.barThickness = barThickness;
    }
}

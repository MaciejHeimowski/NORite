import javax.swing.*;
import java.awt.*;

public class ToolBar extends UIPanel {
    public ToolBar() {
        super(896, 32, 128, 736);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        super.paint(g);

        paintBase(g2);
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, 128, 2);
        g2.fillRect(0,0, 2, 736);
    }
}

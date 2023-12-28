package UI;

import Core.Elements.Empty;
import Core.Elements.NOR;
import Core.Elements.Wire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends UIPanel {
    public ToolBar() {
        super(vertBarX, horizBarY, gameWidth - vertBarX, gameHeight - horizBarY);

        this.setLayout(new GridLayout(3, 1, 10, 10));

        JButton NORButton = new JButton("NOR");
        NORButton.setBackground(Color.GRAY);
        NORButton.addActionListener(e -> Editor.setCurrentTile(new NOR()));

        JButton WireButton = new JButton("Wire");
        WireButton.setBackground(Color.GRAY);
        WireButton.addActionListener(e -> Editor.setCurrentTile(new Wire()));

        JButton EraseButton = new JButton("Erase");
        EraseButton.setBackground(Color.GRAY);
        EraseButton.addActionListener(e -> Editor.setCurrentTile(new Empty(0, 0)));

        this.add(NORButton);
        this.add(WireButton);
        this.add(EraseButton);
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

package UI;

import Core.Elements.*;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends UIPanel {
    public ToolBar() {
        super(vertBarX, horizBarY, gameWidth - vertBarX, gameHeight - horizBarY);

        this.setLayout(new GridLayout(6, 1, 10, 4));

        JButton NORButton = new JButton("NOR");
        NORButton.setBackground(Color.BLACK);
        NORButton.setForeground(Color.WHITE);
        NORButton.addActionListener(e -> Editor.setCurrentTile(new NOR(0, 0)));

        JButton WireButton = new JButton("Wire");
        WireButton.setBackground(Color.BLACK);
        WireButton.setForeground(Color.WHITE);
        WireButton.addActionListener(e -> Editor.setCurrentTile(new Wire()));

        JButton BridgeButton = new JButton("Bridge");
        BridgeButton.setBackground(Color.BLACK);
        BridgeButton.setForeground(Color.WHITE);
        BridgeButton.addActionListener(e -> Editor.setCurrentTile(new Bridge(0, 0)));

        JButton InputButton = new JButton("Input");
        InputButton.setBackground(Color.BLACK);
        InputButton.setForeground(Color.WHITE);
        InputButton.addActionListener(e -> Editor.setCurrentTile(new Input(0, 0)));

        JButton EraseButton = new JButton("Eraser");
        EraseButton.setBackground(Color.BLACK);
        EraseButton.setForeground(Color.WHITE);
        EraseButton.addActionListener(e -> Editor.setCurrentTile(new Empty(0, 0)));

        this.add(NORButton);
        this.add(WireButton);
        this.add(BridgeButton);
        this.add(InputButton);
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

package UI;

import Core.Elements.*;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends UIPanel {

    // References to buttons that can change their color
    private JButton SelectButton, MoveButton, CopyButton;

    public ToolBar() {
        super(vertBarX, horizBarY, gameWidth - vertBarX, gameHeight - horizBarY);

        this.setLayout(new GridLayout(11, 1, 10, 4));

        JButton NANDButton = new JButton("NAND");
        NANDButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        NANDButton.setFocusPainted(false);
        NANDButton.setBackground(Color.BLACK);
        NANDButton.setForeground(Color.WHITE);
        NANDButton.addActionListener(e -> Editor.setCurrentTile(new NAND(0, 0)));

        JButton NORButton = new JButton("NOR");
        NORButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        NORButton.setFocusPainted(false);
        NORButton.setBackground(Color.BLACK);
        NORButton.setForeground(Color.WHITE);
        NORButton.addActionListener(e -> Editor.setCurrentTile(new NOR(0, 0)));

        JButton WireButton = new JButton("Wire");
        WireButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        WireButton.setFocusPainted(false);
        WireButton.setBackground(Color.BLACK);
        WireButton.setForeground(Color.WHITE);
        WireButton.addActionListener(e -> Editor.setCurrentTile(new Wire()));

        JButton BridgeButton = new JButton("Bridge");
        BridgeButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        BridgeButton.setFocusPainted(false);
        BridgeButton.setBackground(Color.BLACK);
        BridgeButton.setForeground(Color.WHITE);
        BridgeButton.addActionListener(e -> Editor.setCurrentTile(new Bridge(0, 0)));

        JButton InputButton = new JButton("Input");
        InputButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        InputButton.setFocusPainted(false);
        InputButton.setBackground(Color.BLACK);
        InputButton.setForeground(Color.WHITE);
        InputButton.addActionListener(e -> Editor.setCurrentTile(new Input(0, 0)));

        JButton EraseButton = new JButton("Eraser");
        EraseButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        EraseButton.setFocusPainted(false);
        EraseButton.setBackground(Color.BLACK);
        EraseButton.setForeground(Color.WHITE);
        EraseButton.addActionListener(e -> Editor.setCurrentTile(new Empty(0, 0)));

        SelectButton = new JButton("Select");
        SelectButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        SelectButton.setFocusPainted(false);
        SelectButton.setBackground(Color.BLACK);
        SelectButton.setForeground(Color.WHITE);
        SelectButton.addActionListener(e -> Editor.select());

        CopyButton = new JButton("Copy");
        CopyButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        CopyButton.setFocusPainted(false);
        CopyButton.setBackground(Color.BLACK);
        CopyButton.setForeground(Color.WHITE);
        CopyButton.addActionListener(e -> Editor.copy());

        MoveButton = new JButton("Move");
        MoveButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        MoveButton.setFocusPainted(false);
        MoveButton.setBackground(Color.BLACK);
        MoveButton.setForeground(Color.WHITE);
        MoveButton.addActionListener(e -> Editor.move());

        JButton DeleteButton = new JButton("Delete");
        DeleteButton.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        DeleteButton.setFocusPainted(false);
        DeleteButton.setBackground(Color.BLACK);
        DeleteButton.setForeground(Color.WHITE);
        DeleteButton.addActionListener(e -> Editor.delete());

        this.add(NANDButton);
        this.add(NORButton);
        this.add(WireButton);
        this.add(BridgeButton);
        this.add(InputButton);
        this.add(EraseButton);
        this.add(SelectButton);
        this.add(CopyButton);
        this.add(MoveButton);
        this.add(DeleteButton);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2 * barThickness));

        if(Editor.isSelectionModeEnabled()) {
            this.SelectButton.setBackground(new Color(0, 255, 255));
        }
        else {
            this.SelectButton.setBackground(Color.BLACK);
        }

        if(Editor.isMoveModeEnabled()) {
            this.MoveButton.setBackground(new Color(0, 255, 255));
        }
        else {
            this.MoveButton.setBackground(Color.BLACK);
        }

        if(Editor.isPasteModeEnabled()) {
            this.CopyButton.setBackground(new Color(0, 255, 255));
        }
        else {
            this.CopyButton.setBackground(Color.BLACK);
        }

        super.paint(g2);

        this.paintBase(g2);
    }

    private void paintBase(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, gameWidth - vertBarX, barThickness);
        g2.fillRect(0,0, barThickness, gameHeight - horizBarY);
    }
}

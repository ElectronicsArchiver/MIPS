package org.parker.mips.gui.userpanes.editor.hexeditor;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.IntPredicate;

public abstract class EditableHexGrid extends JPanel {
    protected final JLabel[] labels;
    protected final JTextField editText = new JTextField();

    protected int currModify = -1;
    protected String prevText = null;

    protected final GroupSize groupSize;
    protected final int rows;
    protected final int columns;

    public enum GroupSize{
        Byte (1), HalfWord(2), Word(4);
        public final int value;

        GroupSize(int value){
            this.value = value;
        }
    }

    public EditableHexGrid(int rows, int columns, int cellWidth, int cellHeight, int hGap, int vGap,
                           IntPredicate editable, GroupSize groupSize)
    {
        super();
        this.groupSize =  groupSize;
        this.rows = rows;
        this.columns = columns;

        this.setLayout(new GridLayoutManager(rows, columns,new Insets(0,0,0,0), hGap, vGap));
        //this.setLayout(new GridBagLayout());

        int cnt = rows * columns;
        this.labels = new JLabel[cnt];
        for(int i = 0; i < cnt; i++)
        {
            if(editable.test(i))
            {
                this.labels[i] = new IndexedLabel(i);
                this.labels[i].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        onLabelClicked(e);
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
            else
            {
                this.labels[i] = new JLabel();
                this.labels[i].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelModify();
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
            this.labels[i].setMinimumSize(new Dimension(cellWidth, cellHeight));
            this.labels[i].setAlignmentX(JLabel.CENTER);
            this.labels[i].setAlignmentY(JLabel.CENTER);
            //this.labels[i].setText(String.format("%0" + byteCount * 2 + "X", 0));
            //this.add(this.labels[i], i % columns, i / columns);


                this.add(this.labels[i], new GridConstraints(
                        i / columns,
                        i % columns,
                        1, 1
                        , GridConstraints.ANCHOR_CENTER,
                        GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_CAN_SHRINK,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_CAN_SHRINK,
                        null,
                        null,
                        null,
                        0,
                        false));


        }

        //this.editText.setAlignmentX(JLabel.CENTER);
        //this.editText.setAlignmentY(JLabel.CENTER);
        //this.editText.setHorizontalAlignment(JTextField.CENTER);
        this.editText.setBorder(null);
        this.editText.setColumns(1);
        //this.editText.setAu
        //this.editText.setMinimumSize(new Dimension(1, 1));
        this.editText.setVisible(false);
        this.editText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                onTextKeyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    private void onLabelClicked(MouseEvent event)
    {
        if(event.getClickCount() == 1)
            this.cancelModify();
        else if(event.getClickCount() == 2)
            this.startModify(((IndexedLabel)event.getSource()).index);
    }

    private void onTextKeyPressed(KeyEvent event)
    {
        if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
            this.cancelModify();
        else if(event.getKeyCode() == KeyEvent.VK_ENTER)
        {
            try
            {
                if(this.editText.getText().length() > groupSize.value * 2){
                    throw new NumberFormatException("");
                }
                int i = Integer.parseUnsignedInt(this.editText.getText(), 16);
                //this.labels[this.currModify].setGraphic(null);
                this.labels[this.currModify].setText(String.format("%0"+ groupSize.value * 2 +"X", i));
                this.editText.setVisible(false);
                this.remove(editText);
                this.commitModify(i);
                this.currModify = -1;
            }
            catch(NumberFormatException e)
            {
                this.cancelModify();
            }
        }
    }

    public void cancelModify()
    {
        if(this.currModify > -1)
        {
            //this.labels[this.currModify].setGraphic(null);
            this.labels[this.currModify].setText(this.prevText);
            this.editText.setVisible(false);
            this.currModify = -1;
        }
    }

    public void startModify(int index)
    {
        this.prevText = this.labels[index].getText();
        Dimension size = this.labels[index].getSize();
        this.labels[index].setText("");
        this.editText.setText(this.prevText);

        //size.setSize(size.getWidth() - 1 ,size.getHeight() -1 );
        this.editText.setPreferredSize(size);
        this.editText.setMaximumSize(size);
        this.editText.setMaximumSize(size);
        this.editText.setSize(size);
        //this.labels[index].setUI(this.editText.getUI());

        this.add(this.editText, new GridConstraints(index / columns,index % columns, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        //this.add(this.editText, new GridConstraints(index / columns,index % columns, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, size, size, size, 0, false));

        this.editText.setVisible(true);
        this.editText.requestFocus();
        this.currModify = index;
        this.editText.requestFocus();
    }

    public abstract void commitModify(int newValue);

    protected static class IndexedLabel extends JLabel
    {
        public final int index;

        public IndexedLabel(int index)
        {
            super();
            this.index = index;
        }

        public IndexedLabel(String text, int index)
        {
            super(text);
            this.index = index;
        }
    }
}

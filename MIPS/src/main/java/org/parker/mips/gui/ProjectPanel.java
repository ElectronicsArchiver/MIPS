package org.parker.mips.gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatTree;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.parker.mips.gui.components.FlatFileTree;
import org.parker.mips.gui.components.FlatMatteBorder;
import org.parker.mips.util.FileUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class ProjectPanel extends JPanel {
    private JPanel rootPanel;
    private JComboBox cardSelector;
    private JPanel ContentPane;
    private JButton button1;
    private JPanel projectFileStructure;
    private JPanel projectInformation;
    private JPanel topPanel;
    private FlatTree fileStructTree;

    public ProjectPanel() {
        $$$setupUI$$$();

        cardSelector.addItem("Project Information");
        cardSelector.addItem("Project File Structure");

        cardSelector.setRenderer(new CellRendererWithIcons());

        cardSelector.addActionListener(e -> {
            CardLayout cl = (CardLayout) (ContentPane.getLayout());
            cl.show(ContentPane, (String) cardSelector.getSelectedItem());
        });
        cardSelector.setSelectedIndex(0);

        topPanel.setBorder(new FlatMatteBorder(0, 0, 1, 0));

        this.setVisible(false);
        //fileStructTree.setModel(new FileTreeModel(new File("C:\\Users\\parke\\OneDrive\\Documents\\MIPS\\Projects\\project_0")));
    }

    static private class CellRendererWithIcons extends DefaultListCellRenderer {

        Icon icons[] = new Icon[]{
                new FlatSVGIcon("Images/Icons/SVG/project.svg"),
                new FlatSVGIcon("Images/Icons/SVG/projectDirectory.svg")
        };

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (index == -1) {
                index = list.getSelectedIndex();
            }
            JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            comp.setIcon(icons[index]);

            return comp;
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayoutManager(1, 2, new Insets(7, 10, 7, 0), -1, -1));
        panel1.add(topPanel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cardSelector = new JComboBox();
        topPanel.add(cardSelector, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        topPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 10, 0, 10), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ContentPane = new JPanel();
        ContentPane.setLayout(new CardLayout(0, 0));
        panel2.add(ContentPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        projectFileStructure = new JPanel();
        projectFileStructure.setLayout(new BorderLayout(0, 0));
        projectFileStructure.setOpaque(true);
        ContentPane.add(projectFileStructure, "Project File Structure");
        projectFileStructure.add(fileStructTree, BorderLayout.CENTER);
        projectInformation = new JPanel();
        projectInformation.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        ContentPane.add(projectInformation, "Project Information");
        button1 = new JButton();
        button1.setText("Button");
        projectInformation.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        projectInformation.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        projectInformation.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    private void createUIComponents() {
        rootPanel = this;
        try {
            this.fileStructTree = new FlatFileTree("C:\\Users\\parke\\OneDrive\\Documents\\MIPS\\Projects\\project_0");
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        }
    }
}

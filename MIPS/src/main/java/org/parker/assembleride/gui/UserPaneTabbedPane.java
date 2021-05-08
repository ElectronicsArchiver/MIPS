/*
 *    Copyright 2021 ParkerTenBroeck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.parker.assembleride.gui;


import org.parker.assembleride.gui.userpanes.UserPane;
import org.parker.assembleride.gui.userpanes.editor.FileEditor;
import org.parker.assembleride.gui.userpanes.editor.EditorHandler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;

import static com.formdev.flatlaf.FlatClientProperties.TABBED_PANE_TAB_CLOSABLE;
import static com.formdev.flatlaf.FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK;

/**
 *
 * @author parke
 */
public class UserPaneTabbedPane extends javax.swing.JPanel {
    
    public static void addUserPane(UserPane userPane) {
        jTabbedPane1.add(userPane);
        jTabbedPane1.setSelectedComponent(userPane);
        jTabbedPane1.setTabComponentAt(jTabbedPane1.getSelectedIndex(), userPane.getTitleLabel());
    }
    
    public static void removeUserPane(UserPane userPane) {
        jTabbedPane1.remove(userPane);
    }
    
    public static void setSelectedUserPane(UserPane userPane) {
        jTabbedPane1.setSelectedComponent(userPane);
    }
    
    public UserPaneTabbedPane() {
        initComponents();
        
        jTabbedPane1.setTabLayoutPolicy( JTabbedPane.SCROLL_TAB_LAYOUT );
        jTabbedPane1.putClientProperty(TABBED_PANE_TAB_CLOSABLE, true);
        //jTabbedPane1.putClientProperty(TABBED_PANE_TAB_CLOSE_TOOLTIPTEXT, "Close"); //USING THIS WILL CAUSE CRASHES
        jTabbedPane1.putClientProperty(TABBED_PANE_TAB_CLOSE_CALLBACK,
                (BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
                    AWTEvent e = EventQueue.getCurrentEvent();
                    MouseEvent me = (e instanceof MouseEvent) ? ((MouseEvent) e) : null;
                    if (me == null) {
                        return;
                    }
                    if (me.getButton() == MouseEvent.BUTTON1) {
                        UserPane userPane = (UserPane) tabPane.getComponentAt(tabIndex);
                        if(userPane.close()){
                         jTabbedPane1.remove(userPane);
                        }
                    }
                });
        
        jTabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                if(jTabbedPane1.getSelectedComponent() instanceof FileEditor){
                    EditorHandler.setLastFocused((FileEditor) jTabbedPane1.getSelectedComponent());
                }
            }
        });
    }

    public static void updateOpenUserPanes() {

        if(jTabbedPane1.getSelectedComponent() != null) {
            ((UserPane) jTabbedPane1.getSelectedComponent()).update();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();

        setBackground(new java.awt.Color(102, 102, 0));
        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
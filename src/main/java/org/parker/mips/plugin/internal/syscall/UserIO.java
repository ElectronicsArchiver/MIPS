/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parker.mips.plugin.internal.syscall;

import org.parker.mips.plugin.syscall.SystemCallPluginFrame;

/**
 *
 * @author parke
 */
public class UserIO extends SystemCallPluginFrame {

    private static String enteredText;

    public void openUserIO() {
        if (!isVisible()) {
            setVisible(true);
        }
    }

    public static int getInt() {
        while (!UserIO.hasChar()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }

        try {
            int i = Integer.parseInt(enteredText.substring(0, enteredText.length() - 1));
            enteredText = "";
            return i;
        } catch (Exception e) {
            enteredText = "";
        } finally {
            enteredText = "";
        }
        return -1;
    }

    public static void clearOutput() {
        outputTextArea.setText("");
    }

    public static int lastChar() {
        String tempText = inputTextFeild.getText();
        inputTextFeild.setText("");
        if (tempText.length() > 0) {
            return tempText.charAt(tempText.length() - 1);
        } else {
            return 0;
        }
    }

    public static boolean waitForEnter() {
        while (enteredText.charAt(enteredText.length() - 1) == '\n') {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public UserIO() {
        super("UserIO");
        this.setTitle("UserIO");
        initComponents();
    }

    public static void checkTextOutput() {
        if (outputTextArea.getText().length() > 1000) {

            String test = outputTextArea.getText();

            outputTextArea.setText(test.substring(test.length() - 1000, test.length()));
        }
    }

    public static void outputUnicode(int c) {
        checkTextOutput();
        outputTextArea.append(String.valueOf((char) c));
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
        try {
            Thread.sleep(1);
        } catch (Exception e) {

        }
    }

    public static void outputNumber(int val) {
        checkTextOutput();
        outputTextArea.append(String.valueOf(val));
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
        try {
            Thread.sleep(1);
        } catch (Exception e) {

        }
    }

    public static boolean hasChar() {

        if (enteredText == null) {
            return false;
        }
        return enteredText.length() > 0;
    }

    public static char getNextChar() {

        while (!UserIO.hasChar()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }

        char temp = enteredText.charAt(0);
        enteredText = enteredText.substring(1);
        return temp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        modernScrollPane1 = new org.parker.mips.gui.theme.lookandfeel.ModernScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        themedJButton1 = new javax.swing.JButton();
        inputTextFeild = new javax.swing.JTextField();

        setTitle("User IO");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        outputTextArea.setEditable(false);
        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        outputTextArea.setFocusable(false);
        outputTextArea.setRequestFocusEnabled(false);
        modernScrollPane1.setViewportView(outputTextArea);

        themedJButton1.setText("Clear");
        themedJButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themedJButton1ActionPerformed(evt);
            }
        });

        inputTextFeild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTextFeildActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(inputTextFeild)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(themedJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(modernScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(modernScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputTextFeild, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themedJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        inputTextFeild.requestFocus();
    }//GEN-LAST:event_formFocusGained

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        stopProcessor();
    }//GEN-LAST:event_formWindowClosing

    private void themedJButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themedJButton1ActionPerformed
        clearOutput();
    }//GEN-LAST:event_themedJButton1ActionPerformed

    private void inputTextFeildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTextFeildActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputTextFeildActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextField inputTextFeild;
    private javax.swing.JPanel jPanel2;
    private org.parker.mips.gui.theme.lookandfeel.ModernScrollPane modernScrollPane1;
    private static javax.swing.JTextArea outputTextArea;
    private javax.swing.JButton themedJButton1;
    // End of variables declaration//GEN-END:variables
}

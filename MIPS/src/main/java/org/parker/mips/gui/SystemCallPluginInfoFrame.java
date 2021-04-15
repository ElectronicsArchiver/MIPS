/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parker.mips.gui;

import org.parker.mips.plugin.syscall.SystemCall;
import org.parker.mips.plugin.syscall.SystemCallPlugin;
import org.parker.mips.plugin.syscall.SystemCallPluginHandler;

import javax.swing.*;
import java.util.Arrays;

import static org.parker.mips.plugin.syscall.SystemCallPluginHandler.getSystemCallNumberFromGeneratedNumber;

/**
 *
 * @author parke
 */
public class SystemCallPluginInfoFrame extends javax.swing.JFrame {

    /**
     * Creates new form SystemCallPluginInfoFrame
     */
    public SystemCallPluginInfoFrame(SystemCallPlugin plugin) {
        initComponents();
        jEditorPane1.setBackground(themedJPanel11.getBackground());
        jEditorPane1.setText(generatePluginInfoHTML(plugin));
        jEditorPane1.setCaretPosition(0);
        this.setVisible(true);
    }

    private static String generatePluginInfoHTML(SystemCallPlugin plugin) {
        String html = "<html>";

        html += "<head>\n"
                + "<style>\n"
                + "h1 {text-align: center;color:white}\n"
                + "h2 {text-align: center;color:white}\n"
                + "h3 {text-align: center;color:red}\n"
                + "p {text-align: center;font-weight: bold;color:white}\n"
                + "div {text-align: center;font-weight: bold;color:white}\n"
                + "</style>\n"
                + "</head>";

        for (SystemCall sc : plugin.getSystemCalls()) {

            html += "<h1>" + sc.DATA.SYSTEM_CALL_NAME + "</h1>"; // name

            if (!SystemCallPluginHandler.isSystemCallRegistered(sc)) {
                html += "<h3>System Call was not registered properly</h3>";
            }

            int index;
            boolean generated;

            if (sc.DATA.SYSTEM_CALL_NUMBER >= 0) {
                index = sc.DATA.SYSTEM_CALL_NUMBER;
                generated = false;
            } else {
                index = getSystemCallNumberFromGeneratedNumber(sc);
                generated = true;
            }

            if (!generated) {
                html += "<h2>System Call ID: " + sc.DATA.SYSTEM_CALL_NUMBER + "</h2>"; //ID
            } else {
                html += "<h2>Generated System Call ID: " + index + " Real System Call ID is: " + sc.DATA.SYSTEM_CALL_NUMBER + "</h2>"; //ID
            }

            html += "<div>Registers Read From</div><div>" + Arrays.toString(sc.DATA.REGISTERS_READ_FROM) + "</div>";

            html += "<div>Registers Written To</div><div>" + Arrays.toString(sc.DATA.REGISTERS_WRITTEN_TO) + "</div>";

            //html += "<p><b></b></p>";
            html += "<div>PC Reg Read From: " + sc.DATA.PC_REG_READ_FROM + "</div>";
            html += "<div>PC Reg Written to: " + sc.DATA.PC_REG_WRITTEN_TO + "</div>";
            html += "<div>High Reg Read From: " + sc.DATA.HIGH_REG_READ_FROM + "</div>";
            html += "<div>High Reg Written to: " + sc.DATA.HIGH_REG_WRITTEN_TO + "</div>";
            html += "<div>Low Reg Read From: " + sc.DATA.LOW_REG_READ_FROM + "</div>";
            html += "<div>Low Reg Written to: " + sc.DATA.LOW_REG_WRITTEN_TO + "</div>";
            html += "<div>Memory Read From: " + sc.DATA.MEMORY_READ_FROM + "</div>";
            html += "<div>Memory Written to: " + sc.DATA.MEMORY_WRITTEN_TO + "</div>";

            html += "<br><p>" + sc.DATA.SYSTEM_CALL_DESCRIPTION + "</p><br><br>";
        }

        return html + "</html>";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        themedJPanel11 = new javax.swing.JPanel();
        modernScrollPane1 = new JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        modernScrollPane1.setOpaque(false);

        jEditorPane1.setEditable(false);
        jEditorPane1.setContentType("text/html"); // NOI18N
        modernScrollPane1.setViewportView(jEditorPane1);

        javax.swing.GroupLayout themedJPanel11Layout = new javax.swing.GroupLayout(themedJPanel11);
        themedJPanel11.setLayout(themedJPanel11Layout);
        themedJPanel11Layout.setHorizontalGroup(
            themedJPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(modernScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
        );
        themedJPanel11Layout.setVerticalGroup(
            themedJPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(modernScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(themedJPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(themedJPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane modernScrollPane1;
    private javax.swing.JPanel themedJPanel11;
    // End of variables declaration//GEN-END:variables
}

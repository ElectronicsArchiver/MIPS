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
package org.parker.mips.log;


import org.parker.mips.assembler.util.AssemblerLevel;
import org.parker.mips.core.SystemPreferences;
import org.parker.mips.preferences.Preference;
import org.parker.mips.preferences.Preferences;
import org.parker.mips.architectures.mips.emulator.exceptions.RunTimeLevel;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.*;

/**
 *
 * @author parke
 */
public class LogPanel extends javax.swing.JPanel {

    private final static Logger LOGGER = Logger.getLogger(LogPanel.class.getName());

	static {
        LogPanel.initComponents();
    }

    private static void logCustomMessage(String message, SimpleAttributeSet att) {
        LogPanel.appendMessageToVirtualConsoleLog("[Message] " + message, att);
    }

    private static void logCustomMessage(String message, boolean bold, boolean italic, boolean underline, Color color, String font) {
	    SimpleAttributeSet att = new SimpleAttributeSet();
        StyleConstants.setForeground(att, color);
        StyleConstants.setBold(att, bold);
        StyleConstants.setItalic(att, italic);
        StyleConstants.setUnderline(att, underline);
        if (font != null) {
            StyleConstants.setFontFamily(att, font);
        }
        LogPanel.appendMessageToVirtualConsoleLog("[Message] " + message, att);
    }

    private static void appendMessageToVirtualConsoleLog(String message, SimpleAttributeSet att) {
        Document doc = LogPanel.jTextPane1.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), message + "\n", att);
        } catch (Exception exc) {
            //LogFrame.logError(LogFrame.getFullExceptionMessage(exc));
        }

    }

    private static void appendMessageToVirtualConsoleLog(String message){
	    Document doc = LogPanel.jTextPane1.getStyledDocument();
	    try{
            doc.insertString(doc.getLength(), message + "\n", null);
        }catch(Exception e){

        }
    }

    public LogPanel() {
        initLayout();
        this.setVisible(true);
    }

    private void initLayout() {

        initComponents();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }

    private static void initComponents() {

        if (jTextPane1 == null) {

        } else {
            return;
        }

        jScrollPane1 = new JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        //jTextPane1.setBackground((Color) ThemeHandler.getThemeObjectFromThemeName(ThemeHandler.TEXT_AREA_BACKGROUND_2_PROPERTY_NAME));
        //jTextPane1.setFont((Font) ThemeHandler.getThemeObjectFromThemeName(ThemeHandler.GENERAL_TEXT_FONT_PROPERTY_NAME)); // NOI18N
        jTextPane1.setEditable(false);
        jTextPane1.setContentType("HTML/plain"); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        // Get the text area's scroll pane:
        final JScrollPane scrollPane = (JScrollPane) (jTextPane1.getParent().getParent());

        // Disable the auto scroll :
        ((DefaultCaret) jTextPane1.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        // Add a listener to the vertical scroll bar :
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            private int _val = 0;
            private int _ext = 0;
            private int _max = 0;

            private final BoundedRangeModel _model = scrollPane.getVerticalScrollBar().getModel();

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {

                // Get the new max :
                int newMax = _model.getMaximum();

                // If the new max has changed and if we were scrolled to bottom :
                if (newMax != _max && (_val + _ext == _max)) {

                    // Scroll to bottom :
                    _model.setValue(_model.getMaximum() - _model.getExtent());
                }

                // Save the new values :
                _val = _model.getValue();
                _ext = _model.getExtent();
                _max = _model.getMaximum();
            }
        });

    }// </editor-fold>

    // Variables declaration - do not modify                     
    private static javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextPane jTextPane1;
    // End of variables declaration                   

    public static class LogFrameHandler extends Handler {

        private static Level systemLevel;
        private static Level assemblerLevel;
        private static Level runtimeLevel;

        private static Preference<Boolean> showStackTrace;
        private static Preference<Boolean> logSystemCallMessages;
        private static Preference<Boolean> showCallerClass;
        private static Preference<Boolean> showCallerMethod;

        static{

            try{
                showStackTrace = SystemPreferences.showStackTrace;
                logSystemCallMessages = SystemPreferences.logSystemCallMessages;
                showCallerClass = SystemPreferences.showCallerClass;
                showCallerMethod = SystemPreferences.showCallerMethod;
            }catch(Exception e){
              LOGGER.log(Level.SEVERE, "Failed to get Logger Preferences", e);
            }

            try {
                SystemPreferences.systemLogLevel.addObserver((o, v) -> {
                    systemLevel = Level.parse((String) v);
                });
                SystemPreferences.assemblerLogLevel.addObserver((o, v) -> {
                    assemblerLevel = Level.parse((String) v);
                });
                SystemPreferences.runtimeLogLevel.addObserver((o, v) -> {
                    runtimeLevel = Level.parse((String) v);
                });
            }catch(Exception e){
                LOGGER.log(Level.SEVERE, "Failed to add Observers to logPreferences", e);
            }
            try {
                systemLevel = Level.parse(SystemPreferences.systemLogLevel.val());
            }catch(Exception e){
                systemLevel = Level.INFO;
                LOGGER.log(Level.SEVERE, "Failed to load systemLevel from Preferences defaulting to level INFO",e);
            }
            try {
                assemblerLevel = Level.parse(SystemPreferences.assemblerLogLevel.val());
            }catch (Exception e){
                assemblerLevel = AssemblerLevel.ASSEMBLER_MESSAGE;
                LOGGER.log(Level.SEVERE, "Failed to load assemblerLevel from Preferences defaulting to level ASSEMBLER_MESSAGE",e);
            }
            try {
                runtimeLevel = Level.parse(SystemPreferences.runtimeLogLevel.val());
            }catch(Exception e){
                runtimeLevel = RunTimeLevel.RUN_TIME_MESSAGE;
                LOGGER.log(Level.SEVERE, "Failed to load runtimeLevel from Preferences defaulting to level RUN_TIME_MESSAGE",e);
            }
        }

        @Override
        public void publish(LogRecord record) {

            if (showStackTrace == null ||
                logSystemCallMessages == null ||
                showCallerMethod == null ||
                showCallerClass == null) {
                return;
            }

            String message = "";

            if (showCallerClass.val()) {
                message += record.getSourceClassName();
            }
            if (showCallerMethod.val()) {
                if (showCallerClass.val()) {
                    message += " ";
                }
                message += record.getSourceMethodName() + ":\n";
            } else {
                if (showCallerClass.val()) {
                    message += ":\n";
                }
            }
            String[] classPath = record.getLoggerName().split("\\.");
            String className = classPath[classPath.length - 1];

            message += "[" + record.getLevel().getName() + "] "
                    + "[" + className + "]"
                    //+ "[" + String.join("] [", record.getSourceClassName().replaceFirst("org.parker.mips.", "").split("\\.")) + "] "
                    + (record.getMessage() == null ? "" : record.getMessage());

            SimpleAttributeSet sas = new SimpleAttributeSet();

            int value = record.getLevel().intValue();


            if (value == AssemblerLevel.ASSEMBLER_MESSAGE.intValue()|| value == RunTimeLevel.RUN_TIME_MESSAGE.intValue()) {

                StyleConstants.setForeground(sas, Color.LIGHT_GRAY);
                StyleConstants.setBold(sas, false);

            } else if (value == AssemblerLevel.ASSEMBLER_WARNING.intValue()|| value == RunTimeLevel.RUN_TIME_WARNING.intValue()) {

                StyleConstants.setForeground(sas, Color.YELLOW);
                StyleConstants.setBold(sas, false);

            } else if (value == AssemblerLevel.ASSEMBLER_ERROR.intValue() || value == RunTimeLevel.RUN_TIME_ERROR.intValue()) {

                StyleConstants.setForeground(sas, Color.RED);
                StyleConstants.setBold(sas, false);
                Throwable tmp = record.getThrown();
                while(tmp != null) {
                    message += ": " + tmp.getMessage();
                    tmp = tmp.getCause();
                }
            }

            if(record.getLevel().getName().contains("ASSEMBLER_")){
                if (record.getLevel().intValue() < assemblerLevel.intValue() ) {
                    return;
                }
            }else if(record.getLevel().getName().contains("RUN_TIME_")){
                if (record.getLevel().intValue() < runtimeLevel.intValue() ) {
                    return;
                }
            }else {

                //if(systemLevel.intValue() < record.getLevel().intValue()){//ignore
                //    return;
                //}
                if (record.getLevel().intValue() < systemLevel.intValue() ) {
                    return;
                }

            if (record.getLevel() == Level.INFO) {

                SimpleAttributeSet att = new SimpleAttributeSet();
                StyleConstants.setForeground(att, Color.LIGHT_GRAY);
                StyleConstants.setBold(att, false);

            } else if (record.getLevel() == Level.WARNING) {

                StyleConstants.setForeground(sas, Color.YELLOW);
                StyleConstants.setBold(sas, false);

            } else if (record.getLevel() == Level.SEVERE) {

                StyleConstants.setForeground(sas, Color.RED);
                StyleConstants.setBold(sas, false);

                if (showStackTrace.val() && record.getThrown() != null) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    record.getThrown().printStackTrace(pw);
                    message += ":\n" + sw.toString();
                }

            }
        }

            LogPanel.appendMessageToVirtualConsoleLog(message,sas);

            return;

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }
}
/*
 * LogWindowView.java
 *
 * Created on 2010-03-18, 03:58:20
 */

package com.panozona.converter;

import java.awt.Toolkit;

/**
 *
 * @author Marek Standio
 */
public class LogWindowView extends javax.swing.JFrame {

    /** Creates new form LogWindowView */
    public LogWindowView(MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView; // TODO: this is wrong
        initComponents();
        setTitle("SaladoConverter log");
        //setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindowView.class.getResource("resources/icons/appicon.png")));
        setRunning(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jButtonCancel = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();

        setName("Form"); // NOI18N
        setResizable(false);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextAreaLog.setColumns(20);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.panozona.converter.SaladoConverter.class).getContext().getResourceMap(LogWindowView.class);
        jTextAreaLog.setFont(resourceMap.getFont("jTextAreaLog.font")); // NOI18N
        jTextAreaLog.setRows(5);
        jTextAreaLog.setName("jTextAreaLog"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaLog);

        jButtonCancel.setText(resourceMap.getString("jButtonCancel.text")); // NOI18N
        jButtonCancel.setName("jButtonCancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonClose.setText(resourceMap.getString("jButtonClose.text")); // NOI18N
        jButtonClose.setName("jButtonClose"); // NOI18N
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        if(isRunning){
            isCanceled = true;
            mainWindowView.cancelRunningTasks();
        }
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    public void append(String text){
        jTextAreaLog.append(text);
    }
        
    public void setRunning(Boolean value){
        isRunning = value;        
        jButtonCancel.setEnabled(isRunning);
        if (isRunning){
            setCanceled(false);
            jTextAreaLog.setText("");
        }
    }

    public Boolean getRunning(){
        return isRunning;
    }

    public void setCanceled(Boolean value){
        isCanceled = value;        
        jButtonCancel.setEnabled(!isCanceled);
    }

    public Boolean getCanceled(){
        return isCanceled;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaLog;
    // End of variables declaration//GEN-END:variables

    private MainWindowView mainWindowView;
    private boolean isRunning;
    private boolean isCanceled;
    
}
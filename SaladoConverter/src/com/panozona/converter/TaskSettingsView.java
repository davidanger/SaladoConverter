/*
 * TaskSettingsView.java
 *
 * Created on 2011-04-17, 21:41:19
 */
package com.panozona.converter;

import com.panozona.converter.maintable.TaskTableModel;
import com.panozona.converter.task.TaskData;
import com.panozona.converter.task.TaskDataEquirectangular;
import javax.swing.JOptionPane;

/**
 * @author Marek Standio
 */
public class TaskSettingsView extends javax.swing.JFrame {

    private Controller controller;
    private TaskTableModel taskTableModel;
    private TaskData currentTaskData;
    private boolean allowCloseFlag;

    /** Creates new form TaskSettingsView */
    public TaskSettingsView(TaskTableModel taskTableModel) {
        initComponents();
        setTitle("Edit task");
        this.taskTableModel = taskTableModel;
        controller = Controller.getInstance();
        allowCloseFlag = true;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupCubeTileSize = new javax.swing.ButtonGroup();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        ctstab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jRadioButtonAutosize = new javax.swing.JRadioButton();
        jRadioButtonCustom = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCubeSize = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldTileSize = new javax.swing.JTextField();
        jLabelTileDefaultCubeSize = new javax.swing.JLabel();
        jLabelTileDefaultTileSize = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        eqtab = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldEquiOffset = new javax.swing.JTextField();
        jTextFieldEquiFov = new javax.swing.JTextField();
        jButtonEquiApplyChanges = new javax.swing.JButton();
        jButtonTaskCancel = new javax.swing.JButton();
        jButtonTaskOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setName("Form"); // NOI18N
        setResizable(false);

        jTabbedPane2.setName(""); // NOI18N

        ctstab.setName("ct"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cube and tile size"));
        jPanel1.setName("jPanel1"); // NOI18N

        buttonGroupCubeTileSize.add(jRadioButtonAutosize);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.panozona.converter.SaladoConverter.class).getContext().getResourceMap(TaskSettingsView.class);
        jRadioButtonAutosize.setText(resourceMap.getString("jRadioButtonAutosize.text")); // NOI18N
        jRadioButtonAutosize.setName("jRadioButtonAutosize"); // NOI18N
        jRadioButtonAutosize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAutosizeActionPerformed(evt);
            }
        });

        buttonGroupCubeTileSize.add(jRadioButtonCustom);
        jRadioButtonCustom.setText(resourceMap.getString("jRadioButtonCustom.text")); // NOI18N
        jRadioButtonCustom.setName("jRadioButtonCustom"); // NOI18N
        jRadioButtonCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonCustomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonCustom)
                .addGap(18, 18, 18)
                .addComponent(jRadioButtonAutosize)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRadioButtonCustom)
                .addComponent(jRadioButtonAutosize, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldCubeSize.setName("jTextFieldCubeSize"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextFieldTileSize.setName("jTextFieldTileSize"); // NOI18N

        jLabelTileDefaultCubeSize.setText(resourceMap.getString("jLabelTileDefaultCubeSize.text")); // NOI18N
        jLabelTileDefaultCubeSize.setName("jLabelTileDefaultCubeSize"); // NOI18N

        jLabelTileDefaultTileSize.setText(resourceMap.getString("jLabelTileDefaultTileSize.text")); // NOI18N
        jLabelTileDefaultTileSize.setName("jLabelTileDefaultTileSize"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelTileDefaultTileSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelTileDefaultCubeSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTileSize, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(jTextFieldCubeSize, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCubeSize, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelTileDefaultCubeSize)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTileSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabelTileDefaultTileSize)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ctstabLayout = new javax.swing.GroupLayout(ctstab);
        ctstab.setLayout(ctstabLayout);
        ctstabLayout.setHorizontalGroup(
            ctstabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctstabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ctstabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ctstabLayout.setVerticalGroup(
            ctstabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ctstabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab(resourceMap.getString("ct.TabConstraints.tabTitle"), ctstab); // NOI18N

        eqtab.setName("eq"); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextFieldEquiOffset.setName("jTextFieldEquiOffset"); // NOI18N

        jTextFieldEquiFov.setName("jTextFieldEquiFov"); // NOI18N

        jButtonEquiApplyChanges.setText(resourceMap.getString("jButtonEquiApplyChanges.text")); // NOI18N
        jButtonEquiApplyChanges.setActionCommand(resourceMap.getString("jButtonEquiApplyChanges.actionCommand")); // NOI18N
        jButtonEquiApplyChanges.setName("jButtonEquiApplyChanges"); // NOI18N
        jButtonEquiApplyChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEquiApplyChangesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldEquiOffset, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(jTextFieldEquiFov, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)))
                    .addComponent(jButtonEquiApplyChanges, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextFieldEquiFov, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEquiOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButtonEquiApplyChanges)
                .addContainerGap())
        );

        jButtonEquiApplyChanges.getAccessibleContext().setAccessibleName(resourceMap.getString("jButtonEquiApplyChanges.AccessibleContext.accessibleName")); // NOI18N

        javax.swing.GroupLayout eqtabLayout = new javax.swing.GroupLayout(eqtab);
        eqtab.setLayout(eqtabLayout);
        eqtabLayout.setHorizontalGroup(
            eqtabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eqtabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        eqtabLayout.setVerticalGroup(
            eqtabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eqtabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab(resourceMap.getString("eq.TabConstraints.tabTitle"), eqtab); // NOI18N

        jButtonTaskCancel.setText(resourceMap.getString("jButtonTaskCancel.text")); // NOI18N
        jButtonTaskCancel.setName("jButtonTaskCancel"); // NOI18N
        jButtonTaskCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTaskCancelActionPerformed(evt);
            }
        });

        jButtonTaskOK.setText(resourceMap.getString("jButtonTaskOK.text")); // NOI18N
        jButtonTaskOK.setName("jButtonTaskOK"); // NOI18N
        jButtonTaskOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTaskOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonTaskOK, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonTaskCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonTaskOK)
                    .addComponent(jButtonTaskCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName(resourceMap.getString("jTabbedPane2.AccessibleContext.accessibleName")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonTaskOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTaskOKActionPerformed
        collectAllData();
        if (allowCloseFlag) {
            this.dispose();
            taskTableModel.fireTableDataChanged();
            controller.applyCommand();
        }
        allowCloseFlag = true;
}//GEN-LAST:event_jButtonTaskOKActionPerformed

    private void jButtonTaskCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTaskCancelActionPerformed
        this.dispose();
}//GEN-LAST:event_jButtonTaskCancelActionPerformed

    private void jRadioButtonCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonCustomActionPerformed
        jTextFieldCubeSize.setEditable(jRadioButtonCustom.isSelected());
        jTextFieldTileSize.setEditable(jRadioButtonCustom.isSelected() && !currentTaskData.surpressOptimalisation);
        if (jRadioButtonCustom.isSelected()) {
            currentTaskData.setIsOptimalisated(false);
            jTextFieldCubeSize.setText("");
            jTextFieldTileSize.setText("");
        }
}//GEN-LAST:event_jRadioButtonCustomActionPerformed

    private void jRadioButtonAutosizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAutosizeActionPerformed
        jTextFieldCubeSize.setEditable(jRadioButtonCustom.isSelected());
        jTextFieldTileSize.setEditable(jRadioButtonCustom.isSelected() && !currentTaskData.surpressOptimalisation);
        if (jRadioButtonAutosize.isSelected()) {
            currentTaskData.setIsOptimalisated(true);
            currentTaskData.optimalize();
            jTextFieldCubeSize.setText("" + currentTaskData.getNewCubeSize());
            jTextFieldTileSize.setText("" + currentTaskData.getNewTileSize());
        }
}//GEN-LAST:event_jRadioButtonAutosizeActionPerformed

private void jButtonEquiApplyChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEquiApplyChangesActionPerformed
    try {
        if (currentTaskData instanceof TaskDataEquirectangular) {
            ((TaskDataEquirectangular) currentTaskData).setFov(jTextFieldEquiFov.getText());
            ((TaskDataEquirectangular) currentTaskData).setOffset(jTextFieldEquiOffset.getText());
            displayTaskData(currentTaskData);
        }
    } catch (IllegalArgumentException ex) {
        showOptionPane(ex.getMessage());
    }
}//GEN-LAST:event_jButtonEquiApplyChangesActionPerformed

    public void displayTaskData(TaskData taskData) {

        currentTaskData = taskData;

        jLabelTileDefaultCubeSize.setText(Integer.toString(currentTaskData.getOriginalCubeSize()));
        jLabelTileDefaultTileSize.setText(Integer.toString(currentTaskData.getOriginalTileSize()));

        if (currentTaskData.surpressOptimalisation) {
            jRadioButtonCustom.setSelected(true);
            jRadioButtonAutosize.setSelected(false);
            jRadioButtonAutosize.setEnabled(false);
        } else {
            jRadioButtonAutosize.setEnabled(true);
            if (currentTaskData.getIsOptimalisated()) {
                jRadioButtonCustom.setSelected(false);
                jRadioButtonAutosize.setSelected(true);
            } else {
                jRadioButtonCustom.setSelected(true);
                jRadioButtonAutosize.setSelected(false);
            }
        }
        jRadioButtonAutosizeActionPerformed(null);
        jRadioButtonCustomActionPerformed(null);

        if (taskData instanceof TaskDataEquirectangular) {
            jPanel3.setEnabled(true);
            jTextFieldEquiFov.setText(Integer.toString(((TaskDataEquirectangular) taskData).getFov()));
            jTextFieldEquiOffset.setText(Integer.toString(((TaskDataEquirectangular) taskData).getOffset()));
            jTextFieldEquiFov.setEnabled(true);
            jTextFieldEquiOffset.setEnabled(true);
            jButtonEquiApplyChanges.setEnabled(true);
        } else {
            jTextFieldEquiFov.setEnabled(false);
            jTextFieldEquiOffset.setEnabled(false);
            jButtonEquiApplyChanges.setEnabled(false);
        }
    }
    
    public void refreshTaskData(){
        if(currentTaskData != null){
            displayTaskData(currentTaskData);
        }
    }
    
    public void removeTaskData(TaskData taskData){
        if(currentTaskData.equals(taskData)){
            dispose();
        }
    }

    private void collectAllData() {
        try {
            if (!currentTaskData.surpressOptimalisation && jRadioButtonCustom.isSelected()) {
                currentTaskData.setIsOptimalisated(true);
            } else {
                currentTaskData.setIsOptimalisated(false);
                currentTaskData.setNewCubeSize(jTextFieldCubeSize.getText());
                currentTaskData.setNewTileSize(jTextFieldTileSize.getText());
            }
        } catch (IllegalArgumentException ex) {
            showOptionPane(ex.getMessage());
        }
    }

    private void showOptionPane(String message) {
        JOptionPane.showMessageDialog(this, message);
        allowCloseFlag = false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupCubeTileSize;
    private javax.swing.JPanel ctstab;
    private javax.swing.JPanel eqtab;
    private javax.swing.JButton jButtonEquiApplyChanges;
    private javax.swing.JButton jButtonTaskCancel;
    private javax.swing.JButton jButtonTaskOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelTileDefaultCubeSize;
    private javax.swing.JLabel jLabelTileDefaultTileSize;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButtonAutosize;
    private javax.swing.JRadioButton jRadioButtonCustom;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextFieldCubeSize;
    private javax.swing.JTextField jTextFieldEquiFov;
    private javax.swing.JTextField jTextFieldEquiOffset;
    private javax.swing.JTextField jTextFieldTileSize;
    // End of variables declaration//GEN-END:variables
}

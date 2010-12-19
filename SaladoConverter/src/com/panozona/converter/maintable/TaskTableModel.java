package com.panozona.converter.maintable;

import com.panozona.converter.settings.AggregatedSettings;
import com.panozona.converter.task.TaskData;
import com.panozona.converter.task.TaskImages;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Marek
 */
public class TaskTableModel extends AbstractTableModel {

    public String[] columnNames = {"R", "Status", "Cube size", "Directory"};
    public ArrayList<TaskData> rows;
    private AggregatedSettings aggstngs;

    public TaskTableModel(ArrayList<TaskData> rows, AggregatedSettings aggstngs) {
        super();
        this.rows = rows;
        this.aggstngs = aggstngs;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return rows.get(rowIndex);
            case 1:
                return rows.get(rowIndex).taskState;
            case 2:
                if (rows.get(rowIndex).taskSettings.getTaskImages().getPanoType().equals(TaskImages.panoType.cubic)) {
                    return rows.get(rowIndex).taskSettings.cubeSizeDescription();
                } else {
                    return "2x" + aggstngs.ec.getOverlap() + "+" + rows.get(rowIndex).taskSettings.cubeSizeDescription();
                }
            case 3:
                return rows.get(rowIndex).taskSettings.getTaskImages().getTaskPathDescription();
            default:
                return null;
        }
    }

    public void addRow(TaskData newTaskData) {
        rows.add(newTaskData);
        fireTableDataChanged();
    }

    public void removeItem(TaskData taskData) {
        rows.remove(taskData);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (col == 0);
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        rows.set(row, (TaskData) value);
        fireTableCellUpdated(row, col);
    }

    public boolean hasActiveTasks() {
        for (TaskData taskData : rows) {
            if (taskData.checkBoxEnabled && taskData.checkBoxSelected) {
                return true;
            }
        }
        return false;
    }
}

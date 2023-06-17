package Presentation.GUI_HR;
import BussinesLogic.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShiftsTable extends JFrame {
    private List<List<CellValues>> cellData;
    private JButton submitButton;
    private int ID;
    private LocalDate shiftDate;
    private ManageShifts ms;
    String[] rowNames;

    public List<List<CellValues>> getCellData() {
        return cellData;
    }

    public String getRowsName(int i){
        return rowNames[i];
    }
    public ShiftsTable(ManageShifts object, LocalDate startWeek, int id) {
        ms = object;
        setVisible(true);
        ID = id;
        shiftDate = startWeek;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Shifts weekly table for:  " + startWeek.toString() + " until " + startWeek.plusDays(6) + " for branch " + id);

        // Define the column names (weekdays)
        String[] columnNames = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        // Define the row names (roles)
        Role[] rows = Role.values();
        rowNames = new String[rows.length - 1];
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < rows.length - 1; i++) {
            String lowercaseString = rows[i].toString().toLowerCase();
            String modifiedString = lowercaseString.substring(0, 1).toUpperCase() + lowercaseString.substring(1);
            rowNames[i] = modifiedString;
        }

        cellData = new ArrayList<>();
        for (int i = 0; i < rows.length - 1; i++) {
            List<CellValues> rowData = new ArrayList<>();
            for (int j = 0; j < columnNames.length; j++) {
                rowData.add(new CellValues());
            }
            cellData.add(rowData);
        }

        // Create a DefaultTableModel with empty cells
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Object getValueAt(int row, int column) {
                if (column == 0)
                    return rowNames[row];
                else
                    return cellData.get(row).get(column); // Default value is 0
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (column > 0) {
                    String[] values = aValue.toString().split("\\s+");

                    String mValue = values[0];
                    String eValue = values[1];

                    // Retrieve the existing cell values from cellData
                    List<CellValues> rowData = cellData.get(row);
                    CellValues cellValues = rowData.get(column);

                    // Update the cell values
                    cellValues.setValueM(mValue);
                    cellValues.setValueE(eValue);

                    // Update the modified cell values back in the rowData list
                    rowData.set(column, cellValues);

                    // Update the modified rowData back in the cellData list
                    cellData.set(row, rowData);

                    // Call super to set the value in the table model
                    super.setValueAt(aValue, row, column);
                }
            }


            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Allow editing cell values
            }
        };

        // Set the row names
        for (String rowName : rowNames) {
            model.addRow(new Object[]{rowName});
        }
        JTable table = new JTable(model) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column != 0) {
                    return new CellRenderer();
                } else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column != 0) {
                    return new CellEditor();
                } else {
                    return super.getCellEditor(row, column);
                }
            }
        };


        // Create a JTable using the custom DefaultTableModel

        // Customize cell renderer for displaying the values
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                if (value instanceof Component) {
                    Component component = (Component) value;
                    if (isSelected) {
                        component.setBackground(table.getSelectionBackground());
                        component.setForeground(table.getSelectionForeground());
                    } else {
                        component.setBackground(table.getBackground());
                        component.setForeground(table.getForeground());
                    }
                    return component;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        // Apply the custom renderer to all cells
        table.setDefaultRenderer(Object.class, renderer);

        // Set preferred column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        for (int i = 1; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(60);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 160)); // Set the preferred size

        getContentPane().add(scrollPane);
        JPanel panel = new JPanel();
        panel.add(getContentPane());
        panel.add(submitButton);
        setContentPane(panel);

        // Add the table to the JFrame
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ms.submit();
                setVisible(false);
            }
        });
    }



    static class CellValues {
        private String valueM = "0";
        private String valueE = "0";

        public String getValueM() {
            String val = valueM.substring(0,1);
            return val;
        }

        public void setValueM(String valueM) {
            this.valueM = valueM;
        }

        public String getValueE() {
            return valueE;
        }

        public void setValueE(String valueE) {
            this.valueE = valueE;
        }

        @Override
        public String toString() {
            return valueM + ", " + valueE;
        }
    }

    // Custom cell renderer
    private static class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel(new GridLayout(1, 4));
            JTextField textFieldM = new JTextField();
            JTextField textFieldE = new JTextField();
            CellValues cellValues = (CellValues) value;
            textFieldM.setText(cellValues.getValueM());
            textFieldE.setText(cellValues.getValueE());
            panel.add(new JLabel("M: "));
            panel.add(textFieldM);
            panel.add(new JLabel("E: "));
            panel.add(textFieldE);
            return panel;
        }
    }

    // Custom cell editor
    private static class CellEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JTextField textFieldM;
        private JTextField textFieldE;

        public CellEditor() {
            panel = new JPanel(new GridLayout(1, 4));
            textFieldM = new JTextField();
            textFieldE = new JTextField();
            panel.add(new JLabel("M: "));
            panel.add(textFieldM);
            panel.add(new JLabel("E: "));
            panel.add(textFieldE);
        }

        @Override
        public Object getCellEditorValue() {
            CellValues cellValues = new CellValues();
            cellValues.setValueM(textFieldM.getText());
            cellValues.setValueE(textFieldE.getText());
            return cellValues;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            CellValues cellValues = (CellValues) value;
            textFieldM.setText(cellValues.getValueM());
            textFieldE.setText(cellValues.getValueE());
            return panel;
        }
        @Override
        public boolean stopCellEditing() {
            try {
                if(textFieldM.getText() != null)
                    Integer.parseInt(textFieldM.getText()); // Attempt to parse the input as an integer
                if(textFieldE.getText() != null)
                    Integer.parseInt(textFieldE.getText());
            } catch (NumberFormatException e) {
                // Input is not a valid integer
                JOptionPane.showMessageDialog(panel, "Please enter valid integer values.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return false; // Prevent cell editing from stopping
            }
            return super.stopCellEditing();
        }
    }

}

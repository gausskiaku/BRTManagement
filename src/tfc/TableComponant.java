/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tfc;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Gauss
 */
public class TableComponant extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//Si la valeur de la cellule est un JButton, on transtype cette valeur
    if (value instanceof JButton)
    return (JButton) value;
    else if(value instanceof JComboBox)
    return (JComboBox) value;
    else
    return this;
}
}
 

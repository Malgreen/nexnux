package main.java.gui.utilities;

import javax.swing.*;
import java.awt.*;

public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {
    // Taken from: http://link-intersystems.com/blog/2014/10/19/custom-swing-component-renderers/
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        setComponentOrientation(list.getComponentOrientation());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setSelected(isSelected);
        setEnabled(list.isEnabled());

        setText(value == null ? "" : value.toString());

        return this;
    }
}

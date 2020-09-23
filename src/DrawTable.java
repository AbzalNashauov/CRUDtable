import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DrawTable {
    private JTable table = new JTable();
    private final int maxWidth;
    private final double[] tableProps = {0.1, 0.5, 0.15, 0.25, 0.0};

    public DrawTable(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public JTable drawPeopleTable() {

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            Font font = new Font("TAHOMA", 0, 18);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setFont(font);
                return this;
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        table.setShowGrid(true);
        table.setModel(new DefaultTableModel(
            new Object[][] {
            },
                new String[] {"№", "ТАӘ", "Жасы", "Қылмыс түрі", ""}
        ) {
            final boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        if (table.getColumnModel().getColumnCount() > 0 ) {
            for (int i = 0; i < 5; i++) {
                table.getColumnModel().getColumn(i).setMinWidth(new Double(maxWidth * tableProps[i]).intValue());
                table.getColumnModel().getColumn(i).setPreferredWidth(new Double(maxWidth * tableProps[i]).intValue());
                table.getColumnModel().getColumn(i).setMaxWidth(new Double(maxWidth * tableProps[i]).intValue());
            }
        }
        table.setRowHeight(30);

        return table;
    }


}

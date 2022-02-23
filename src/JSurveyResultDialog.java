import assets.SurveyImageIcons;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class JSurveyResultDialog extends JDialog {
    private final JTable resultTable;
    private DefaultTableModel model;

    public JSurveyResultDialog() {
        super();
        this.setLocationByPlatform(true);
        this.setModal(true);
        this.setTitle("결과 확인");
        this.setPreferredSize(new Dimension(400, 400));
        this.setResizable(true);
        this.setIconImage(SurveyImageIcons.surveyIcon.getImage());


        String[] header = {"종류", "이름"};
        model = new DefaultTableModel(header, 0);
        resultTable = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        JScrollPane jScrollPane = new JScrollPane(resultTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        jScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(jScrollPane);
        this.pack();
    }

    public void setResult(HashMap<String, String> resultMap) {
        int modelSize = model.getRowCount();
        for (int i = 0; i < modelSize; i++) {
            model.removeRow(0);
        }
        String[] row = new String[2];
        for (String type :
                resultMap.keySet()) {
            row[0] = type;
            row[1] = resultMap.get(type);
            model.addRow(row);
        }
        resizeColumnWidth(resultTable);
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 180; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }


}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.HashMap;

public class JSurveyResultDialog extends JDialog {
    private DefaultTableModel model;

    public JSurveyResultDialog() {
        super();
        this.setLocationByPlatform(true);
        this.setModal(true);
        this.setTitle("결과 확인");
        this.setResizable(false);

        JPanel centerPanel = new JPanel();
        String[] header = {"종류", "이름"};
        model = new DefaultTableModel(header, 0);
        JTable resultTable = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        JScrollPane jScrollPane = new JScrollPane(resultTable);

        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(jScrollPane);


        this.add(centerPanel);
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
    }


}

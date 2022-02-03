import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class JSurveyEditDialog extends JDialog implements ActionListener {

    // Controller in MVC
    private final SurveyController controller;
    // Parent View in MVC
    private final SurveyView parentView;

    private JList<JSurveyEntity> list;
    private JTextField inputField;
    private JButton addBtn;
    private JButton delBtn;
    private JButton editPrescriptionsBtn;
    private JButton confirmBtn;
    private JButton cancelBtn;

    private DefaultListModel<JSurveyEntity> model;
    private JScrollPane jScrollPane;

    private JSurvey jSurvey;

    // child dialog
    private final JSurveyEditEntityDialog jSurveyEditEntityDialog;
    private String childAction = null;

    public JSurveyEditDialog(SurveyController controller, SurveyView parentView) {
        this.controller = controller;
        this.parentView = parentView;
        this.jSurveyEditEntityDialog = new JSurveyEditEntityDialog(controller, this);

        this.setTitle("설문 편집");
        this.setModal(true);
        this.setLocationByPlatform(true);
        this.setPreferredSize(new Dimension(800, 600));
        this.setSize(new Dimension(800, 600));
        this.setResizable(false);

        model = new DefaultListModel<>();
        list = new JList<>(model);
        inputField = new JTextField(22);
        addBtn = new JButton("추가");
        delBtn = new JButton("삭제");
        editPrescriptionsBtn = new JButton("구성요소 편집");
        confirmBtn = new JButton("확인");
        cancelBtn = new JButton("취소");

        inputField.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

        confirmBtn.addActionListener(this);
        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        delBtn.addActionListener(this);
        editPrescriptionsBtn.addActionListener(this);


        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel jLabel = new JLabel("질문 : ");
        jLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        topPanel.add(jLabel);
        topPanel.add(inputField);
        topPanel.add(addBtn);
        topPanel.add(delBtn);
        topPanel.add(editPrescriptionsBtn);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jScrollPane = new JScrollPane(list);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.add(confirmBtn);
        bottomPanel.add(cancelBtn);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    public DefaultListModel<JSurveyEntity> getModel() {
        return model;
    }

    public void setModel(DefaultListModel<JSurveyEntity> model) {
        this.model = model;
    }

    public JSurvey getJSurvey() {
        return jSurvey;
    }

    public void setJSurvey(JSurvey jSurvey) {
        this.jSurvey = jSurvey;
        model.removeAllElements();
        inputField.setText("");

        for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
            model.addElement(jSurveyEntity);
        }

        refresh();
    }

    public JList<JSurveyEntity> getList() {
        return list;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JButton getDelBtn() {
        return delBtn;
    }

    public JButton getEditPrescriptionsBtn() {
        return editPrescriptionsBtn;
    }

    public JButton getConfirmBtn() {
        return confirmBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public String getChildAction() {
        return childAction;
    }

    public void setChildAction(String childAction) {
        this.childAction = childAction;
    }

    public JSurveyEntity getSelectedJSurveyEntity() {
        int selected = list.getSelectedIndex();
        return selected == -1 ? null : model.get(selected);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmBtn) {
            controller.editSurvey(jSurvey);
            this.dispose();
        } else if (e.getSource() == cancelBtn) {
            this.dispose();
        } else if (e.getSource() == addBtn) {
            String description = inputField.getText().trim();
            if(description.equals(""))
                return;
            JSurveyEntity jSurveyEntity = new JSurveyEntity(description, new Vector<>(), jSurvey, parentView.getFont());
            jSurvey.getJSurveyEntities().add(jSurveyEntity);
            model.addElement(jSurveyEntity);
            inputField.setText("");
            inputField.requestFocus();
            refresh();
        } else if (e.getSource() == delBtn) {
            int selected = list.getSelectedIndex();
            if (selected != -1) {
                JSurveyEntity jSurveyEntity = model.getElementAt(selected);
                model.remove(selected);
                jSurvey.getJSurveyEntities().removeElement(jSurveyEntity);
            }
            refresh();
        } else if (e.getSource() == editPrescriptionsBtn) {
            int selected = list.getSelectedIndex();
            if (selected != -1) {
                jSurveyEditEntityDialog.updateList(model.getElementAt(selected));
                jSurveyEditEntityDialog.setVisible(true);
                // update list
                setJSurvey(jSurvey);
            }
        }
    }



    void refresh() {
        this.revalidate();
        this.repaint();
    }
}


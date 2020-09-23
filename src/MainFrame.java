



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


//import org.sqlite.SQLiteDataSource;

public class MainFrame extends JFrame {

    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int maxWidth = dim.width-1000;
    private final int maxHeight = dim.height-500;
    private JButton crimeTypeBtn;
    private JButton exitBtn;
    private final CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;
    private JPanel crimeTypePanel;
    private final JPanel compPanel = new JPanel();
    private JButton backMainBtn;
    private JButton editHumanBtn;
    private JButton deleteHumanBtn;
    private JButton addHumanBtn;
    private JButton backCrimeTypeBtn;
    private JPanel showHumansPanel;
    private JTable peopleTable = new JTable();
    private JScrollPane tableContainer;
    private final HumanDA controller;
    private CrimeType crimeTypeVar;

    private JFrame componentFrame;
    private JPanel compChildPanel;
    private final CardLayout compCardLayout = new CardLayout();
    private JPanel informationHumanPanel;
    private JPanel editHumanPanel;
    private JPanel addHumanPanel;
    private JButton saveHumanBtn;
    private JButton exitSaveBtn;

    private JLabel humanNameLabel;
    private JLabel humanAgeLabel;
    private JLabel severityTypeLabel;

    private JTextField humanNameText;
    private JSpinner humanAgeSpinner;
    private JComboBox<String> severityTypeComboBox;
    private int currentHumanId;
    private JLabel editSeverityTypeLabel;
    private JLabel editHumanNameLabel;
    private JLabel editHumanAgeLabel;

    private JTextField editHumanNameText;
    private JSpinner editHumanAgeSpinner;
    private JComboBox<String> editSeverityTypeComboBox;

    private String currentHumanName;
    private int currentHumanAge;
    private CrimeSeverity currentHumanSeverity;

    public MainFrame() {

       this.setPreferredSize(new Dimension(maxWidth, maxHeight));
       this.setMaximumSize(new Dimension(maxWidth, maxHeight));
       this.setMinimumSize(new Dimension(maxWidth, maxHeight));
       this.setSize(maxWidth, maxHeight);

       this.setLocation(dim.width/2-maxWidth/2, dim.height/2-maxHeight/2);
       this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

       this.getContentPane().setLayout(null);
       controller = new HumanDA();

        initComponents();
        tableIsEmpty();
        drawTable();
        addTableListener();
        fillComboBox();
      // fillPeopleTable();




       personalizePanels();
       cardLayout.show(compPanel, "show-main");


   }



    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    UIDefaults defaults = UIManager.getLookAndFeelDefaults();
                    defaults.put("Table.alternateRowColor", new Color(191, 174, 174));
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));


    }

    public void initComponents() {
        crimeTypeBtn = new JButton("Құқық бұзушылық түрлері");
        tableContainer = new JScrollPane();
        componentFrame = new JFrame();
        compChildPanel = new JPanel();
        crimeTypeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        crimeTypeBtn.setBounds(maxWidth/2 - 150, maxHeight/2 - 100 , 300, 100);
        crimeTypeBtn.setHorizontalAlignment(SwingConstants.CENTER);
        crimeTypeBtn.addActionListener(e -> {
            cardLayout.show(compPanel, "crime-type");
            setTitle("Құқық бұзушылық түрлері");
        });

        exitBtn = new JButton("Шығу");
        exitBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        exitBtn.setBounds(maxWidth/2 - 100, maxHeight/2 + 60, 200, 50);
        exitBtn.addActionListener(e -> System.exit(0));
        mainPanel = new JPanel();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Жастар арасындағы құқық бұзушылықты есепке алатын бағдарлама");
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGap(0,0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGap(0,0,Short.MAX_VALUE)
        );

        tableContainer.setBounds(10, 50, maxWidth-25, maxHeight - 200);


        crimeTypePanel = new JPanel();
        GroupLayout crimeTypePanelLayout = new GroupLayout(crimeTypePanel);
        crimeTypePanel.setLayout(crimeTypePanelLayout);
        crimeTypePanelLayout.setHorizontalGroup(
                crimeTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0, Short.MAX_VALUE)
        );
        crimeTypePanelLayout.setVerticalGroup(
                crimeTypePanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGap(0,0, Short.MAX_VALUE)
        );

        addHumanBtn = new JButton("Қосу");
        addHumanBtn.setBounds(10, maxHeight - 100, 100, 50);
        addHumanBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        addHumanBtn.addActionListener(e -> {
            componentFrame.setTitle("Жаңа мәліметтер енгізу");
            componentFrame.setVisible(true);
            fillComboBox();
            compCardLayout.show(compChildPanel, "add");

        });

        severityTypeComboBox = new JComboBox<>();
        editSeverityTypeComboBox = new JComboBox<>();
        
        editHumanBtn = new JButton("Өзгерту");
        editHumanBtn.setBounds(115, maxHeight - 100, 100, 50);
        editHumanBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        editHumanBtn.addActionListener(evt -> {
            int[] checkArray = peopleTable.getSelectedRows();
            if (checkArray.length > 0) {
                componentFrame.setTitle("Мәліметтерді өзгерту");
                editHumanNameText.setText(currentHumanName);
                editHumanAgeSpinner.setValue(currentHumanAge);
                editSeverityTypeComboBox.setSelectedItem(currentHumanSeverity.getTranslateStr());
                componentFrame.setVisible(true);
                fillComboBox();
                compCardLayout.show(compChildPanel, "edit");
            } else {
                JOptionPane.showMessageDialog(null, "Жазбаны таңдаңыз", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteHumanBtn = new JButton("Өшіру");
        deleteHumanBtn.setBounds(220, maxHeight - 100, 100, 50);
        deleteHumanBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        deleteHumanBtn.addActionListener(this::deleteHumanBtnActionPerformed);
        


        backCrimeTypeBtn = new JButton("Артқа");
        backCrimeTypeBtn.setBounds(325, maxHeight - 100, 100, 50);
        backCrimeTypeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        backCrimeTypeBtn.addActionListener(e -> {
            cardLayout.show(compPanel, "crime-type");
            setTitle("Құқық бұзушылық түрлері");
        });

        showHumansPanel = new JPanel();
        GroupLayout showHumanLayout = new GroupLayout(showHumansPanel);
        showHumansPanel.setLayout(showHumanLayout);
        showHumanLayout.setHorizontalGroup(
                showHumanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0, Short.MAX_VALUE)
        );
        showHumanLayout.setVerticalGroup(
                showHumanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0,Short.MAX_VALUE)
        );

        humanNameLabel = new JLabel();
        humanNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        humanNameLabel.setBounds(10, 10, 100, 30);
        humanNameLabel.setText("ТАӘ");

        humanAgeLabel = new JLabel();
        humanAgeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        humanAgeLabel.setBounds(10, 50, 100, 30);
        humanAgeLabel.setText("Жасы");

        severityTypeLabel = new JLabel();
        severityTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        severityTypeLabel.setBounds(10, 90, 100,30);
        severityTypeLabel.setText("Қылмыс түрі");

        humanNameText = new JTextField();
        humanNameText.setFont(new Font("Tahoma", Font.PLAIN, 12));
        humanNameText.setBounds(115,10,300,30);

        SpinnerModel value = new SpinnerNumberModel(18, 14, 120,1);
        humanAgeSpinner = new JSpinner(value);
        humanAgeSpinner.setBounds(115,50,50,30);
        humanAgeSpinner.setFont(new Font("Tahoma", Font.PLAIN, 12));

        severityTypeComboBox.setBounds(115, 90, 100, 30);
        severityTypeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));


        editHumanNameLabel = new JLabel();
        editHumanNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        editHumanNameLabel.setBounds(10, 10, 100, 30);
        editHumanNameLabel.setText("ТАӘ");

        editHumanAgeLabel = new JLabel();
        editHumanAgeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        editHumanAgeLabel.setBounds(10, 50, 100, 30);
        editHumanAgeLabel.setText("Жасы");

        editSeverityTypeLabel = new JLabel();
        editSeverityTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        editSeverityTypeLabel.setBounds(10, 90, 100,30);
        editSeverityTypeLabel.setText("Қылмыс түрі");

        editHumanNameText = new JTextField();
        editHumanNameText.setFont(new Font("Tahoma", Font.PLAIN, 12));
        editHumanNameText.setBounds(115,10,300,30);

        SpinnerModel editValue = new SpinnerNumberModel(18, 14, 120,1);
        editHumanAgeSpinner = new JSpinner(editValue);
        editHumanAgeSpinner.setBounds(115,50,50,30);
        editHumanAgeSpinner.setFont(new Font("Tahoma", Font.PLAIN, 12));

        editSeverityTypeComboBox.setBounds(115, 90, 100, 30);
        editSeverityTypeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));



        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(compPanel, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(compPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        componentFrame.setMaximumSize(new Dimension(500, 400));
        componentFrame.setPreferredSize(new Dimension(500,400));
        componentFrame.setResizable(false);
        componentFrame.setSize(new Dimension(500,400));
        componentFrame.getContentPane().setLayout(null);
        editHumanPanel = new JPanel();
        GroupLayout editHumanPanelLayout = new GroupLayout(editHumanPanel);
        editHumanPanel.setLayout(editHumanPanelLayout);
        editHumanPanelLayout.setHorizontalGroup(
                editHumanPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0, Short.MAX_VALUE)
        );
        editHumanPanelLayout.setVerticalGroup(
                editHumanPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0, Short.MAX_VALUE)
        );

        addHumanPanel = new JPanel();
        GroupLayout addHumanPanelLayout = new GroupLayout(addHumanPanel);
        addHumanPanel.setLayout(addHumanPanelLayout);
        addHumanPanelLayout.setHorizontalGroup(
                addHumanPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0, Short.MAX_VALUE)
        );
        addHumanPanelLayout.setVerticalGroup(
                addHumanPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0, Short.MAX_VALUE)
        );

        informationHumanPanel = new JPanel();
        GroupLayout informationHumanPanelLayout = new GroupLayout(informationHumanPanel);
        informationHumanPanel.setLayout(informationHumanPanelLayout);
        informationHumanPanelLayout.setHorizontalGroup(
                informationHumanPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE)
        );
        informationHumanPanelLayout.setVerticalGroup(
                informationHumanPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0,0,Short.MAX_VALUE)
        );
        GroupLayout compLayout = new GroupLayout(componentFrame.getContentPane());

        componentFrame.getContentPane().setLayout(compLayout);
        compLayout.setHorizontalGroup(
                compLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(compChildPanel, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        compLayout.setVerticalGroup(
                compLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(compChildPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));


        pack();



    }

    private void fillComboBox() {
        severityTypeComboBox.removeAllItems();
        editSeverityTypeComboBox.removeAllItems();
        for (CrimeSeverity severity : CrimeSeverity.values()) {
            severityTypeComboBox.addItem(severity.getTranslateStr());
            editSeverityTypeComboBox.addItem(severity.getTranslateStr());
        }
    }


    private void personalizePanels() {
        compPanel.setLayout(cardLayout);
        mainPanel.add(crimeTypeBtn);
        mainPanel.add(exitBtn);
        compPanel.add(mainPanel, "main");

        for (CrimeType crimeType : CrimeType.values()) {
             JButton button = new JButton(crimeType.getTranslateStr());
             int crimeTypeOrdinal = crimeType.ordinal();
             button.setFont(new Font("Tahoma", Font.PLAIN, 18));
             button.setBounds(maxWidth / 2 - 100,crimeTypeOrdinal * 50 + 100,200, 50);
             button.addActionListener(e -> {
                 fillPeopleTableByCrimeType(crimeType);
                 cardLayout.show(compPanel, "show-people");
                 crimeTypeVar = crimeType;
                 setTitle(String.format("%s құқық бұзушылықты есепке алу", crimeType.getTranslateStr()));
                 tableIsEmpty();
             });
             crimeTypePanel.add(button);
         }
         backMainBtn = new JButton("Артқа");
         backMainBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
         backMainBtn.setBounds(maxWidth/2 - 60, maxHeight - 300, 120, 50);
         backMainBtn.addActionListener(e -> {
             cardLayout.show(compPanel, "main");
             removeSelection();
             setTitle("Жастар арасындағы құқық бұзушылықты есепке алатын бағдарлама");
         });
         crimeTypePanel.add(backMainBtn);

         compPanel.add(crimeTypePanel, "crime-type");

         showHumansPanel.add(tableContainer);
         showHumansPanel.add(addHumanBtn);
         showHumansPanel.add(editHumanBtn);
         showHumansPanel.add(deleteHumanBtn);
         showHumansPanel.add(backCrimeTypeBtn);
         compPanel.add(showHumansPanel, "show-people");

         componentFrame.setLocation(dim.width/2 - 250, dim.height/2 - 200);
         compChildPanel.setLayout(compCardLayout);


        saveHumanBtn = new JButton("Сақтау");
        saveHumanBtn.setBounds(10, componentFrame.getHeight() - 100, 100, 50);
        saveHumanBtn.addActionListener(this::saveHumanBtnActionPerformed);

        JButton editSaveHumanBtn = new JButton("Сақтау");
        editSaveHumanBtn.setBounds(10, componentFrame.getHeight() - 100, 100, 50);
        editSaveHumanBtn.addActionListener(this::editSaveHumanActionPerformed);

        JButton exitEditSaveHumanBtn = new JButton("Артқа");
        exitEditSaveHumanBtn.setBounds(120, componentFrame.getHeight() - 100, 100, 50);
        exitEditSaveHumanBtn.addActionListener(this::exitEditSaveHumanActionPerformed);


        exitSaveBtn = new JButton("Артқа");
        exitSaveBtn.setBounds(120, componentFrame.getHeight() - 100, 100, 50);
        componentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        exitSaveBtn.addActionListener(e -> {
            componentFrame.setVisible(false);
            clearAddPanel();
            cardLayout.show(compPanel, "show-people");
            removeSelection();
        });

         addHumanPanel.add(humanNameLabel);
         addHumanPanel.add(humanAgeLabel);
         addHumanPanel.add(severityTypeLabel);
         addHumanPanel.add(humanNameText);
         addHumanPanel.add(humanAgeSpinner);
         addHumanPanel.add(saveHumanBtn);
         addHumanPanel.add(exitSaveBtn);
         addHumanPanel.add(severityTypeComboBox);
         addHumanPanel.add(saveHumanBtn);
         addHumanPanel.add(exitSaveBtn);
         compChildPanel.add(addHumanPanel, "add");

        editHumanPanel.add(editHumanNameLabel);
        editHumanPanel.add(editHumanAgeLabel);
        editHumanPanel.add(editSeverityTypeLabel);
        editHumanPanel.add(editHumanNameText);
        editHumanPanel.add(editSeverityTypeComboBox);
        editHumanPanel.add(editHumanAgeSpinner);
        editHumanPanel.add(editSaveHumanBtn);
        compChildPanel.add(editHumanPanel, "edit");

    }

    private void drawTable() {
        DrawTable drawTable = new DrawTable(maxWidth);
        peopleTable = drawTable.drawPeopleTable();
        peopleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableContainer.setViewportView(peopleTable);

    }

    private void removeSelection() {
        tableContainer.requestFocus();
    }

//    private void fillPeopleTable() {
//
//        DefaultTableModel dtm = (DefaultTableModel) peopleTable.getModel();
//        for (Human human : controller.allHumans) {
//
//            dtm.addRow(new Object[] {human.getName(), human.getAge(), human.getSeverityName()});
//        }
//    }

    private void fillPeopleTableByCrimeType(CrimeType crimeType) {
        clearPeopleTable();

        controller.update();
        DefaultTableModel dtm = (DefaultTableModel) peopleTable.getModel();
        int num = 0;
        for (Human human : controller.listForCrimeType(crimeType)) {
            num++;
            dtm.addRow(new Object[] {num, human.getName(), human.getAge(), human.getSeverityName(), human.getId()});
        }
    }

    private void clearPeopleTable() {
       DefaultTableModel dtm = (DefaultTableModel) peopleTable.getModel();
       dtm.setRowCount(0);
    }

    private void saveHumanBtnActionPerformed(ActionEvent e) {
        if (humanNameText.getText().length() > 5) {
            Human human;
            int id =  controller.getMaxId();
            id++;
            human = new Human(
                    id,
                    humanNameText.getText(),
                    (int) humanAgeSpinner.getValue(),
                    crimeTypeVar,
                    getCrimeSeverityByValue(severityTypeComboBox.getSelectedItem().toString()));

            controller.addHuman(human);
            JOptionPane.showMessageDialog(null, "Мәлімет сәтті қосылды","Сәттілік", JOptionPane.INFORMATION_MESSAGE);
            componentFrame.setVisible(false);
            clearAddPanel();
            removeSelection();
            fillPeopleTableByCrimeType(crimeTypeVar);

        } else {
            JOptionPane.showMessageDialog(null, "Аты-жөнін енгізіңіз", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private CrimeSeverity getCrimeSeverityByValue (String value) {
        for (CrimeSeverity crimeSeverity : CrimeSeverity.values()) {
            if (crimeSeverity.getTranslateStr().equalsIgnoreCase(value)) {
                return crimeSeverity;
            }
        }
        return null;
    }



    private void deleteHumanBtnActionPerformed(ActionEvent e) {
        int[] checkArray = peopleTable.getSelectedRows();
        if (checkArray.length > 0) {
            int res = JOptionPane.showConfirmDialog(null, "Сіз жазбаны өшіресіз ба?", "Жазбаны өшіру",
                    JOptionPane.YES_NO_OPTION);
            if (res == 0) {
                controller.deleteHuman(currentHumanId);
                fillPeopleTableByCrimeType(crimeTypeVar);
            }

            removeSelection();



        } else {
            JOptionPane.showMessageDialog(null, "Жазбаны таңдаңыз", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void addTableListener() {
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();

                    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                    String key = dtm.getValueAt(table.getSelectedRow(), 4).toString();
                    currentHumanId = Integer.parseInt(key);
                    String name = dtm.getValueAt(table.getSelectedRow(), 1).toString();
                    currentHumanName = name;
                    String severity = dtm.getValueAt(table.getSelectedRow(), 3).toString();
                    currentHumanSeverity = getCrimeSeverityByValue(severity);
                    String age = dtm.getValueAt(table.getSelectedRow(), 2).toString();
                    currentHumanAge = Integer.parseInt(age);


            }
        });
    }

    private void tableIsEmpty() {
        editHumanBtn.setEnabled(controller.listForCrimeType(crimeTypeVar).size() != 0);
    }

    private void clearAddPanel() {
        for (Object obj : addHumanPanel.getComponents()) {
            if (obj instanceof JTextField) {
                JTextField textField = (JTextField) obj;
                textField.setText("");
            }
        }
        fillComboBox();
        fillSpinner();
        tableIsEmpty();
    }

    private void  editSaveHumanActionPerformed(ActionEvent evt) {
        String name = editHumanNameText.getText();
        int age = (int) editHumanAgeSpinner.getValue();
        CrimeType crimeType = crimeTypeVar;
        CrimeSeverity crimeSeverity = getCrimeSeverityByValue(editSeverityTypeComboBox.getSelectedItem().toString());
        int id = controller.getMaxId();
        id++;
        Human human = new Human(id, name, age, crimeType, crimeSeverity);
            controller.updateHuman(currentHumanId, human);
            fillPeopleTableByCrimeType(crimeTypeVar);
            removeSelection();
            componentFrame.setVisible(false);
    }

    private void fillSpinner() {
        humanAgeSpinner.setValue(18);
        editHumanAgeSpinner.setValue(18);
    }

    private void exitEditSaveHumanActionPerformed(ActionEvent evt) {
        fillPeopleTableByCrimeType(crimeTypeVar);
        componentFrame.setVisible(false);
        removeSelection();
        cardLayout.show(compPanel, "show-people");
    }
}

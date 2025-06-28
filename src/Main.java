import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("User Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ===== TOP LEFT Input Panel =====
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("User Info"));
        inputPanel.setPreferredSize(new Dimension(300, 180));

        JTextField nameField = new JTextField(); nameField.setMaximumSize(new Dimension(300, 25));
        JTextField emailField = new JTextField(); emailField.setMaximumSize(new Dimension(300, 25));
        JTextField passField = new JTextField(); passField.setMaximumSize(new Dimension(300, 25));

        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(new JLabel("Email:")); inputPanel.add(emailField);
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(new JLabel("Password:")); inputPanel.add(passField);


        // ===== CENTER Table =====
        String[] columns = {"ID", "Name", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        updateTable(tableModel); // fetch users into table

        // ===== BOTTOM-RIGHT Search Panel =====
        JPanel bottomSearchPanel = new JPanel(new BorderLayout());
        JPanel searchRight = new JPanel();
        searchRight.setLayout(new BoxLayout(searchRight, BoxLayout.Y_AXIS));
        searchRight.setBorder(BorderFactory.createTitledBorder("Search"));

        JTextField searchField = new JTextField(); searchField.setMaximumSize(new Dimension(200, 25));
        JButton searchBtn = new JButton("Search");

        searchRight.add(new JLabel("Search by Name:"));
        searchRight.add(searchField);
        searchRight.add(Box.createRigidArea(new Dimension(0, 10)));
        searchRight.add(searchBtn);

        bottomSearchPanel.add(searchRight, BorderLayout.EAST);
        frame.add(bottomSearchPanel, BorderLayout.NORTH);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                List<User> filteredUsers = DAO.filterUsersByName(keyword, tableModel);

                if (filteredUsers.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "No users found with name: " + keyword,
                            "No Match",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder result = new StringBuilder("Matching Users:\n");
                    for (User user : filteredUsers) {
                        result.append(user.toString()).append("\n");
                    }

                    JOptionPane.showMessageDialog(null, result.toString());
                }
            }
        });


        // ===== Bottom Buttons (on a separate panel) =====
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        buttonPanel.add(addBtn);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String n = nameField.getText();
                String p = passField.getText();
                String emailText = emailField.getText();
                boolean success = DAO.insertUser(n, emailText, p);

                if (success) {
                    JOptionPane.showMessageDialog(null,
                            "User created successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Failed to create user. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // Add that under the table
        frame.add(buttonPanel, BorderLayout.PAGE_END);

        JPanel topContainerPanel = new JPanel(new BorderLayout());
        topContainerPanel.add(inputPanel, BorderLayout.WEST);
        topContainerPanel.add(searchRight, BorderLayout.EAST); // directly add searchRight

        frame.add(topContainerPanel, BorderLayout.NORTH);
        // === Optional: Fill inputs when table row is selected ===
        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                emailField.setText(tableModel.getValueAt(row, 2).toString());
                // password not shown
            }
        });

        frame.setVisible(true);
    }

    private static void updateTable(DefaultTableModel model) {
        List<User> users = DAO.getAllUsers(); // You must implement this
        for (User u : users) {
            model.addRow(new Object[]{u.getId(), u.getName(), u.getEmail()});
        }
    }

}

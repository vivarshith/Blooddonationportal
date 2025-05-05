import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BloodDonationPortal {
    private List<Donor> donors = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private Color primaryColor = new Color(204, 0, 0); // Blood red
    private Color secondaryColor = new Color(255, 235, 235);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new BloodDonationPortal().createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Blood Donation Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(secondaryColor);
        tabbedPane.setForeground(primaryColor);

        tabbedPane.addTab("Register Donor", createDonorRegistrationPanel());
        tabbedPane.addTab("Request Blood", createBloodRequestPanel());
        tabbedPane.addTab("View Donors", createViewDonorsPanel());
        tabbedPane.addTab("View Requests", createViewRequestsPanel());
        tabbedPane.addTab("Find Match", createMatchingPanel());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("BLOOD DONATION PORTAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(primaryColor);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JLabel footerLabel = new JLabel("© 2023 Blood Donation Portal - Save Lives, Donate Blood");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createDonorRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = createFormLabel("Name:");
        JTextField nameField = createFormTextField();
        
        JLabel ageLabel = createFormLabel("Age:");
        JTextField ageField = createFormTextField();
        
        JLabel bloodTypeLabel = createFormLabel("Blood Type:");
        JComboBox<String> bloodTypeCombo = createBloodTypeComboBox();
        
        JLabel contactLabel = createFormLabel("Contact:");
        JTextField contactField = createFormTextField();
        
        JLabel cityLabel = createFormLabel("City:");
        JTextField cityField = createFormTextField();
        
        JLabel lastDonationLabel = createFormLabel("Last Donation (days ago):");
        JSpinner lastDonationSpinner = new JSpinner(new SpinnerNumberModel(90, 0, 365, 1));
        
        JButton registerButton = createPrimaryButton("Register Donor");
        registerButton.addActionListener(e -> {
            try {
                if (nameField.getText().isEmpty() || contactField.getText().isEmpty() || cityField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Please fill all required fields");
                }
                
                Donor donor = new Donor(
                    nameField.getText(),
                    Integer.parseInt(ageField.getText()),
                    (String) bloodTypeCombo.getSelectedItem(),
                    contactField.getText(),
                    cityField.getText(),
                    (Integer) lastDonationSpinner.getValue()
                );
                donors.add(donor);
                JOptionPane.showMessageDialog(panel, "Donor registered successfully!");
                clearFields(nameField, ageField, contactField, cityField);
                lastDonationSpinner.setValue(90);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid numbers for age", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; panel.add(nameLabel, gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(ageLabel, gbc);
        gbc.gridx = 1; panel.add(ageField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(bloodTypeLabel, gbc);
        gbc.gridx = 1; panel.add(bloodTypeCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(contactLabel, gbc);
        gbc.gridx = 1; panel.add(contactField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(cityLabel, gbc);
        gbc.gridx = 1; panel.add(cityField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(lastDonationLabel, gbc);
        gbc.gridx = 1; panel.add(lastDonationSpinner, gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        return panel;
    }

    private JPanel createBloodRequestPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel patientNameLabel = createFormLabel("Patient Name:");
        JTextField patientNameField = createFormTextField();
        
        JLabel hospitalLabel = createFormLabel("Hospital:");
        JTextField hospitalField = createFormTextField();
        
        JLabel bloodTypeLabel = createFormLabel("Blood Type Needed:");
        JComboBox<String> bloodTypeCombo = createBloodTypeComboBox();
        
        JLabel unitsLabel = createFormLabel("Units Needed:");
        JTextField unitsField = createFormTextField();
        
        JLabel urgencyLabel = createFormLabel("Urgency Level:");
        JComboBox<String> urgencyCombo = new JComboBox<>(new String[]{"Low", "Medium", "High", "Critical"});
        urgencyCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JLabel contactLabel = createFormLabel("Contact Person:");
        JTextField contactField = createFormTextField();
        
        JLabel cityLabel = createFormLabel("City:");
        JTextField cityField = createFormTextField();

        JButton submitButton = createPrimaryButton("Submit Request");
        submitButton.addActionListener(e -> {
            try {
                if (patientNameField.getText().isEmpty() || contactField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Please fill all required fields");
                }
                
                Request request = new Request(
                    patientNameField.getText(),
                    hospitalField.getText(),
                    (String) bloodTypeCombo.getSelectedItem(),
                    Integer.parseInt(unitsField.getText()),
                    (String) urgencyCombo.getSelectedItem(),
                    contactField.getText(),
                    cityField.getText()
                );
                requests.add(request);
                JOptionPane.showMessageDialog(panel, "Blood request submitted successfully!");
                clearFields(patientNameField, hospitalField, unitsField, contactField, cityField);
                urgencyCombo.setSelectedIndex(0);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid number for units needed", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; panel.add(patientNameLabel, gbc);
        gbc.gridx = 1; panel.add(patientNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(hospitalLabel, gbc);
        gbc.gridx = 1; panel.add(hospitalField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(bloodTypeLabel, gbc);
        gbc.gridx = 1; panel.add(bloodTypeCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(unitsLabel, gbc);
        gbc.gridx = 1; panel.add(unitsField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(urgencyLabel, gbc);
        gbc.gridx = 1; panel.add(urgencyCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(contactLabel, gbc);
        gbc.gridx = 1; panel.add(contactField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; panel.add(cityLabel, gbc);
        gbc.gridx = 1; panel.add(cityField, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        return panel;
    }

    private JPanel createViewDonorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(secondaryColor);
        
        String[] columnNames = {"Name", "Age", "Blood Type", "Contact", "City", "Last Donation (days)"};
        Object[][] data = new Object[donors.size()][6];
        
        for (int i = 0; i < donors.size(); i++) {
            Donor donor = donors.get(i);
            data[i][0] = donor.getName();
            data[i][1] = donor.getAge();
            data[i][2] = donor.getBloodType();
            data[i][3] = donor.getContact();
            data[i][4] = donor.getCity();
            data[i][5] = donor.getLastDonationDays();
        }
        
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = createPrimaryButton("Refresh List");
        refreshButton.addActionListener(e -> {
            JTabbedPane tabbedPane = (JTabbedPane)panel.getParent();
            tabbedPane.setComponentAt(2, createViewDonorsPanel());
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(secondaryColor);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createViewRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(secondaryColor);
        
        String[] columnNames = {"Patient", "Hospital", "Blood Type", "Units", "Urgency", "Contact", "City"};
        Object[][] data = new Object[requests.size()][7];
        
        for (int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            data[i][0] = request.getPatientName();
            data[i][1] = request.getHospital();
            data[i][2] = request.getBloodType();
            data[i][3] = request.getUnitsNeeded();
            data[i][4] = request.getUrgency();
            data[i][5] = request.getContactPerson();
            data[i][6] = request.getCity();
        }
        
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = createPrimaryButton("Refresh List");
        refreshButton.addActionListener(e -> {
            JTabbedPane tabbedPane = (JTabbedPane)panel.getParent();
            tabbedPane.setComponentAt(3, createViewRequestsPanel());
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(secondaryColor);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createMatchingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(secondaryColor);
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(secondaryColor);
        
        // Blood Type Selection
        JLabel bloodTypeLabel = createFormLabel("Blood Type Needed:");
        JComboBox<String> bloodTypeCombo = createBloodTypeComboBox();
        
        // City Filter
        JLabel cityLabel = createFormLabel("City (leave blank for any):");
        JTextField cityField = new JTextField(15);
        cityField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // Include compatible types checkbox
        JCheckBox compatibleCheck = new JCheckBox("Include compatible blood types");
        compatibleCheck.setSelected(true);
        compatibleCheck.setBackground(secondaryColor);
        
        JButton findButton = createPrimaryButton("Find Matching Donors");
        
        inputPanel.add(bloodTypeLabel);
        inputPanel.add(bloodTypeCombo);
        inputPanel.add(cityLabel);
        inputPanel.add(cityField);
        inputPanel.add(compatibleCheck);
        inputPanel.add(findButton);
        
        // Results Area
        JTextArea resultArea = new JTextArea(15, 60);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        findButton.addActionListener(e -> {
            String bloodType = (String) bloodTypeCombo.getSelectedItem();
            String city = cityField.getText().trim();
            boolean includeCompatible = compatibleCheck.isSelected();
            
            List<Donor> matches = findMatchingDonors(bloodType, city, includeCompatible);
            
            if (matches.isEmpty()) {
                resultArea.setText("No matching donors found.\n\nSuggestions:\n"
                    + "1. Try including compatible blood types\n"
                    + "2. Remove city filter\n"
                    + "3. Check if donors have registered\n"
                    + "4. Some donors may not be eligible (donated recently)");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-20s %-8s %-15s %-20s %s\n", 
                      "Name", "Blood", "Contact", "City", "Last Donation"));
                sb.append("------------------------------------------------------------\n");
                
                for (Donor donor : matches) {
                    sb.append(String.format("%-20s %-8s %-15s %-20s %d days ago\n",
                        donor.getName(),
                        donor.getBloodType(),
                        donor.getContact(),
                        donor.getCity(),
                        donor.getLastDonationDays()));
                }
                resultArea.setText(sb.toString());
            }
        });
        
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private List<Donor> findMatchingDonors(String neededBloodType, String cityFilter, boolean includeCompatible) {
        List<Donor> matches = new ArrayList<>();
        
        for (Donor donor : donors) {
            // Check city filter (case insensitive, empty means any)
            boolean cityMatch = cityFilter.isEmpty() || 
                              donor.getCity().equalsIgnoreCase(cityFilter);
            
            // Check donation eligibility (at least 90 days since last donation)
            boolean eligible = donor.getLastDonationDays() >= 90;
            
            // Check blood type compatibility
            boolean bloodMatch = includeCompatible ? 
                               isBloodCompatible(donor.getBloodType(), neededBloodType) :
                               donor.getBloodType().equalsIgnoreCase(neededBloodType);
            
            if (cityMatch && eligible && bloodMatch) {
                matches.add(donor);
            }
        }
        return matches;
    }

    private boolean isBloodCompatible(String donorType, String recipientType) {
        // Universal donor
        if (donorType.equals("O-")) return true;
        
        // Universal recipient
        if (recipientType.equals("AB+")) return true;
        
        // Exact match
        if (donorType.equals(recipientType)) return true;
        
        // Handle Rh factor
        String donorRh = donorType.substring(donorType.length()-1);
        String recipientRh = recipientType.substring(recipientType.length()-1);
        
        // Rh compatibility: + can receive -, but not vice versa
        if (donorRh.equals("+") && recipientRh.equals("-")) return false;
        
        // Main blood type compatibility
        String donorMain = donorType.substring(0, donorType.length()-1);
        String recipientMain = recipientType.substring(0, recipientType.length()-1);
        
        // O can donate to any (Rh already checked)
        if (donorMain.equals("O")) return true;
        
        // A can donate to A or AB
        if (donorMain.equals("A") && 
            (recipientMain.equals("A") || recipientMain.equals("AB"))) return true;
        
        // B can donate to B or AB
        if (donorMain.equals("B") && 
            (recipientMain.equals("B") || recipientMain.equals("AB"))) return true;
        
        return false;
    }

    // Helper methods
    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return label;
    }

    private JTextField createFormTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return field;
    }

    private JComboBox<String> createBloodTypeComboBox() {
        JComboBox<String> combo = new JComboBox<>(new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        combo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return combo;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return button;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}

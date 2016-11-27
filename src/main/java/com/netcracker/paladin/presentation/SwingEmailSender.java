package com.netcracker.paladin.presentation;

/**
 * Created by ivan on 26.11.16.
 */
import com.netcracker.paladin.application.encryption.EncryptionUtility;
import com.netcracker.paladin.infrastructure.ConfigUtility;
import com.netcracker.paladin.infrastructure.mail.EmailUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SwingEmailSender extends JFrame {
    private final ConfigUtility configUtility;
    private final EmailUtility emailUtility;
    private final EncryptionUtility encryptionUtility;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuItemSetting = new JMenuItem("Settings..");

    private JLabel labelTo = new JLabel("To: ");
    private JLabel labelSubject = new JLabel("Subject: ");

    private JTextField fieldTo = new JTextField(30);
    private JTextField fieldSubject = new JTextField(30);

    private JButton buttonSend = new JButton("SEND");

    private JFilePicker filePicker = new JFilePicker("Attached", "Attach File...");

    private JTextArea textAreaMessage = new JTextArea(10, 30);

    private GridBagConstraints constraints = new GridBagConstraints();

    public SwingEmailSender(ConfigUtility configUtility, EmailUtility emailUtility, EncryptionUtility encryptionUtility) {
        super("Swing E-mail Sender Program");

        this.configUtility = configUtility;
        this.emailUtility = emailUtility;
        this.encryptionUtility = encryptionUtility;

        // set up layout
        setLayout(new GridBagLayout());
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        setupMenu();
        setupForm();

        pack();
        setLocationRelativeTo(null);    // center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupMenu() {
        menuItemSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SettingsDialog dialog = new SettingsDialog(SwingEmailSender.this, configUtility);
                dialog.setVisible(true);
            }
        });

        menuFile.add(menuItemSetting);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    private void setupForm() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(labelTo, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(fieldTo, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(labelSubject, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(fieldSubject, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        buttonSend.setFont(new Font("Arial", Font.BOLD, 16));
        add(buttonSend, constraints);

        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonSendActionPerformed(event);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
        filePicker.setMode(JFilePicker.MODE_OPEN);
        add(filePicker, constraints);

        constraints.gridy = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        add(new JScrollPane(textAreaMessage), constraints);
    }

    private void buttonSendActionPerformed(ActionEvent event) {
        if (!validateFields()) {
            return;
        }

        String toAddress = fieldTo.getText();
        String subject = fieldSubject.getText();
        String message = textAreaMessage.getText();

        File[] attachFiles = null;

        if (!filePicker.getSelectedFilePath().equals("")) {
            File selectedFile = new File(filePicker.getSelectedFilePath());
            attachFiles = new File[] {selectedFile};
        }

        try {
            emailUtility.sendEmail(toAddress, subject, message, attachFiles);

            JOptionPane.showMessageDialog(this,
                    "The e-mail has been sent successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error while sending the e-mail: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateFields() {
        if (fieldTo.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter To address!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldTo.requestFocus();
            return false;
        }

        if (fieldSubject.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter subject!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldSubject.requestFocus();
            return false;
        }

        if (textAreaMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter message!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            textAreaMessage.requestFocus();
            return false;
        }

        return true;
    }

    public void launch() {
        // set look and feel to system dependent
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SwingEmailSender(configUtility, emailUtility, encryptionUtility).setVisible(true);
            }
        });
    }
}

package com.netcracker.paladin.presentation.dialogs;

import com.netcracker.paladin.application.encryption.EncryptionUtility;
import com.netcracker.paladin.presentation.auxillary.JFilePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPublicKeyDialog extends JDialog {

    private EncryptionUtility encryptionUtility;

    private JLabel labelEmail = new JLabel("Email: ");
    private JTextField textEmail = new JTextField(20);

    private JFilePicker filePicker = new JFilePicker("Public key file", "Select");

    private JButton buttonAdd = new JButton("Add");

    public AddPublicKeyDialog(JFrame parent, EncryptionUtility encryptionUtility) {
        super(parent, "SMTP Settings", true);
        this.encryptionUtility = encryptionUtility;

        setupForm();

        pack();
        setLocationRelativeTo(null);
    }

    private void setupForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.anchor = GridBagConstraints.WEST;

        add(labelEmail, constraints);

        constraints.gridx = 1;
        add(textEmail, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
        filePicker.setMode(JFilePicker.MODE_OPEN);
        add(filePicker, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonAdd, constraints);

        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonAddActionPerformed(event);
            }
        });
    }

    private void buttonAddActionPerformed(ActionEvent event) {
        try {
            if (!validateFields()) {
                return;
            }
            
            JOptionPane.showMessageDialog(AddPublicKeyDialog.this,
                    "New public key was added successfully!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error while adding new public key: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateFields() {
        if (textEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter To address!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            textEmail.requestFocus();
            return false;
        }

        return true;
    }
}

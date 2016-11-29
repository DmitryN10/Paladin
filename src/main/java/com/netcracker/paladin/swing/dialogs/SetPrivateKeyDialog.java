package com.netcracker.paladin.swing.dialogs;

import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SetPrivateKeyDialog extends JDialog {

    private EncryptionService encryptionService;

    private JLabel labelCommand = new JLabel("Please, select action:");

    private JButton buttonLoad = new JButton("Load key");
    private JButton buttonGenerate = new JButton("Generate key");

    private JFileChooser fileChooser = new JFileChooser();
    private static final int MODE_OPEN = 1;
    private static final int MODE_SAVE = 2;
    private int mode = MODE_OPEN;

    private File privateKeyFile;

    public SetPrivateKeyDialog(JFrame parent, EncryptionService encryptionService) {
        super(parent, "Private key selection", true);
        this.encryptionService = encryptionService;

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

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(labelCommand, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonLoad, constraints);
        buttonLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonLoadActionPerformed(event);
            }
        });

        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonGenerate, constraints);
        buttonGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonGenerateActionPerformed(event);
            }
        });
    }

    private void buttonLoadActionPerformed(ActionEvent event) {
        try {
            if (mode == MODE_OPEN) {
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    privateKeyFile = fileChooser.getSelectedFile();
                }
            } else if (mode == MODE_SAVE) {
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    privateKeyFile = fileChooser.getSelectedFile();
                }
            }

            byte[] privateKey = FileUtils.readFileToByteArray(privateKeyFile);
            encryptionService.setPrivateKey(privateKey);

            JOptionPane.showMessageDialog(SetPrivateKeyDialog.this, "Key was loaded successfully!");
            dispose();
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving properties file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buttonGenerateActionPerformed(ActionEvent event) {
        try {
//            FileUtils.writeByteArrayToFile(new File("privateKey"), encryptionService.generatePrivateKey());

            encryptionService.generatePrivateKey();

            JOptionPane.showMessageDialog(SetPrivateKeyDialog.this,
                    "Key was generated successfully!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving properties file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package com.netcracker.paladin.swing.tabs;

import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.infrastructure.services.email.EmailService;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ivan on 29.11.16.
 */
public class TabRead extends JPanel {
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    private JTextPane textPaneFrom = new JTextPane();
    private JTextPane textPaneSubject = new JTextPane();
    private JButton buttonRefresh = new JButton("REFRESH");
    private JTextPane textPaneMessage = new JTextPane();
    private JLabel labelFrom = new JLabel("From: ");
    private JLabel labelSubject = new JLabel("Subject: ");
    private GridBagConstraints constraints = new GridBagConstraints();

    public TabRead(EmailService emailService, EncryptionService encryptionService) {
        this.emailService = emailService;
        this.encryptionService = encryptionService;

        setLayout(new GridBagLayout());
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(labelFrom, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(textPaneFrom, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(labelSubject, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(textPaneSubject, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        buttonRefresh.setFont(new Font("Arial", Font.BOLD, 16));
        add(buttonRefresh, constraints);

        constraints.gridy = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(new JScrollPane(textPaneMessage), constraints);
    }
}

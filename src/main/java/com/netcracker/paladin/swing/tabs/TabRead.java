package com.netcracker.paladin.swing.tabs;

import com.netcracker.paladin.domain.MessageEntry;
import com.netcracker.paladin.infrastructure.services.email.EmailService;
import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.infrastructure.services.encryption.exceptions.NoPrivateKeyException;
import com.netcracker.paladin.swing.exceptions.NoMessagesException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by ivan on 29.11.16.
 */
public class TabRead extends JPanel {
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    private final JLabel labelValueFrom = new JLabel("No email");
    private final JLabel labelValueSubject = new JLabel("No subject");
    private final JLabel labelValueDate = new JLabel("No date");
    private final JButton buttonRefresh = new JButton("REFRESH");
    private final JTextArea textAreaMessage = new JTextArea(10, 30);
    private final JLabel labelFrom = new JLabel("From: ");
    private final JLabel labelSubject = new JLabel("Subject: ");
    private final JLabel labelDate = new JLabel("Date: ");
    private final GridBagConstraints constraints = new GridBagConstraints();

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
        add(labelValueFrom, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(labelSubject, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(labelValueSubject, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(labelDate, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(labelValueDate, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.fill = GridBagConstraints.BOTH;
        buttonRefresh.setFont(new Font("Arial", Font.BOLD, 16));
        add(buttonRefresh, constraints);
        buttonRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonRefreshActionPerformed(event);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        textAreaMessage.setEditable(false);
        textAreaMessage.setText("No message");
        add(new JScrollPane(textAreaMessage), constraints);
    }

    private void buttonRefreshActionPerformed(ActionEvent event) {
        try {
            System.out.println("Reading...");
            List<MessageEntry> allMessageEntries = emailService.readEmails();
            if(allMessageEntries.isEmpty()){
                throw new NoMessagesException();
            }

            MessageEntry entry = allMessageEntries.get(allMessageEntries.size()-1);

            String from = entry.getFrom();
            String subject = entry.getSubject();
            String date = entry.getSentDate().toString();

            String message = null;
            if(entry.getCipherBlob() != null){
                message = encryptionService.decryptEmail(entry.getCipherBlob());
            }else{
                message = entry.getMessage();
            }

            labelValueFrom.setText(from);
            labelValueSubject.setText(subject);
            labelValueDate.setText(date);
            textAreaMessage.setText(message);
        } catch (NoMessagesException nme){
            JOptionPane.showMessageDialog(this,
                    "Mailbox is empty :(",
                    "No mail", JOptionPane.ERROR_MESSAGE);
        } catch (NoPrivateKeyException npke){
            JOptionPane.showMessageDialog(this,
                    "Please, set your private key first",
                    "No private key", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error while reading e-mails: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package com.netcracker.paladin.presentation;

/**
 * Created by ivan on 26.11.16.
 */

import com.netcracker.paladin.application.encryption.EncryptionUtility;
import com.netcracker.paladin.application.encryption.NoPrivateKeyException;
import com.netcracker.paladin.domain.MessageEntry;
import com.netcracker.paladin.infrastructure.ConfigUtility;
import com.netcracker.paladin.infrastructure.mail.EmailUtility;
import com.netcracker.paladin.presentation.dialogs.AddPublicKeyDialog;
import com.netcracker.paladin.presentation.dialogs.SetPrivateKeyDialog;
import com.netcracker.paladin.presentation.dialogs.SettingsDialog;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class SwingPaladinEmail implements ItemListener {
    JPanel cards;
    private final static String SENDPANEL = "Send an email";
    private final static String READPANEL = "Read emails";

    private final ConfigUtility configUtility;
    private final EmailUtility emailUtility;
    private final EncryptionUtility encryptionUtility;

    /* Components of cardSend */
    private JTextField fieldTo = new JTextField(30);
    private JTextField fieldSubject = new JTextField(30);
    private JButton buttonSend = new JButton("SEND");
    private JTextArea textAreaMessage = new JTextArea(10, 30);

    /* Components of cardRead */
    private JTextPane textPaneFrom = new JTextPane();
    private JTextPane textPaneSubject = new JTextPane();
    private JButton buttonRefresh = new JButton("REFRESH");
    private JTextPane textPaneMessage = new JTextPane();

    private JFrame frame;
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    JMenuItem menuItemSetting = new JMenuItem("Settings..");
    JMenuItem menuItemSetPrivateKey = new JMenuItem("Set private key..");
    JMenuItem menuItemExportPublicKey = new JMenuItem("Export public key..");
    JMenuItem menuItemAddPublicKey = new JMenuItem("Add public key..");

    public SwingPaladinEmail(ConfigUtility configUtility, EmailUtility emailUtility, EncryptionUtility encryptionUtility) {
        this.configUtility = configUtility;
        this.emailUtility = emailUtility;
        this.encryptionUtility = encryptionUtility;
    }

    public void addComponentToPane(Container pane) {
//        JPanel comboBoxPane = new JPanel();
//        String comboBoxItems[] = {SENDPANEL, READPANEL};
//        JComboBox cb = new JComboBox(comboBoxItems);
//        cb.setEditable(false);
//        cb.addItemListener(this);
//        comboBoxPane.add(cb);
//
//        cards = new JPanel(new CardLayout());
//        cards.add(createCardSend(), SENDPANEL);
//        cards.add(createCardRead(), READPANEL);
//
//        pane.add(comboBoxPane, BorderLayout.PAGE_START);
//        pane.add(cards, BorderLayout.CENTER);
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Send", createCardSend());
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Read", createCardRead());
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        pane.add(tabbedPane);
    }

    private JPanel createCardSend(){
        JPanel cardSend = new JPanel();

        JLabel labelTo = new JLabel("To: ");
        JLabel labelSubject = new JLabel("Subject: ");
        GridBagConstraints constraints = new GridBagConstraints();

        cardSend.setLayout(new GridBagLayout());
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        cardSend.add(labelTo, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        cardSend.add(fieldTo, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        cardSend.add(labelSubject, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        cardSend.add(fieldSubject, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        buttonSend.setFont(new Font("Arial", Font.BOLD, 16));
        cardSend.add(buttonSend, constraints);
        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonSendActionPerformed(event);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        cardSend.add(new JScrollPane(textAreaMessage), constraints);

        return cardSend;
    }

    private JPanel createCardRead(){
        JPanel cardRead = new JPanel();

        JLabel labelFrom = new JLabel("From: ");
        JLabel labelSubject = new JLabel("Subject: ");
        GridBagConstraints constraints = new GridBagConstraints();

        cardRead.setLayout(new GridBagLayout());
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        cardRead.add(labelFrom, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        cardRead.add(textPaneFrom, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        cardRead.add(labelSubject, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        cardRead.add(textPaneSubject, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        buttonRefresh.setFont(new Font("Arial", Font.BOLD, 16));
        cardRead.add(buttonRefresh, constraints);

        constraints.gridy = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        cardRead.add(new JScrollPane(textPaneMessage), constraints);

        return cardRead;
    }

    private void setupMenu(){
        menuItemSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SettingsDialog dialog = new SettingsDialog(frame, configUtility);
                dialog.setVisible(true);
            }
        });
        menuFile.add(menuItemSetting);

        menuItemSetPrivateKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SetPrivateKeyDialog dialog = new SetPrivateKeyDialog(frame, encryptionUtility);
                dialog.setVisible(true);
            }
        });
        menuFile.add(menuItemSetPrivateKey);

        menuItemExportPublicKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String username = configUtility.loadProperties().getProperty("mail.user");
                    FileUtils.writeByteArrayToFile(new File(username+"_publicKey"), encryptionUtility.getPublicKey());

                    JOptionPane.showMessageDialog(frame, "Public key was exported successfully!");
                } catch (NoPrivateKeyException npke) {
                    JOptionPane.showMessageDialog(frame,
                            "Please, set your private key first",
                            "No private key", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame,
                            "Error saving properties file: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        menuFile.add(menuItemExportPublicKey);

        menuItemAddPublicKey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                AddPublicKeyDialog dialog = new AddPublicKeyDialog(frame, encryptionUtility);
                dialog.setVisible(true);
            }
        });
        menuFile.add(menuItemAddPublicKey);

        menuBar.add(menuFile);
        frame.setJMenuBar(menuBar);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Paladin Email");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupMenu();

        SwingPaladinEmail swingEmailSender = new SwingPaladinEmail(configUtility, emailUtility, encryptionUtility);
        swingEmailSender.addComponentToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }


    private void buttonSendActionPerformed(ActionEvent event) {
        if (!validateFields()) {
            return;
        }

        String toAddress = fieldTo.getText();
        String subject = fieldSubject.getText();
        String message = textAreaMessage.getText();

//        File[] attachFiles = null;
//        if (!filePicker.getSelectedFilePath().equals("")) {
//            File selectedFile = new File(filePicker.getSelectedFilePath());
//            attachFiles = new File[] {selectedFile};
//        }

        try {
            System.out.println("Sending...");
            emailUtility.sendEmail(toAddress,
                                    subject,
//                                    encryptionUtility.decryptEmail(encryptionUtility.encryptEmail(message, toAddress)),
                                    "Message is encrypted",
                                    encryptionUtility.encryptEmail(message, toAddress));

            System.out.println("Reading...");
            List<MessageEntry> allMessageEntries = emailUtility.readEmails();
            MessageEntry entry = allMessageEntries.get(allMessageEntries.size()-1);
            System.out.println("From: "+entry.getFrom());
            System.out.println("Subject: "+entry.getSubject());
            System.out.println("Text: "+entry.getText());
            if(entry.getCipherBlob() != null){
                System.out.println("Encrypted message: "+encryptionUtility.decryptEmail(entry.getCipherBlob()));
            }

            JOptionPane.showMessageDialog(frame,
                    "The e-mail has been sent successfully!");
        } catch (NoPrivateKeyException npke){
            JOptionPane.showMessageDialog(frame,
                    "Please, set your private key first",
                    "No private key", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Error while sending the e-mail: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateFields() {
        if (fieldTo.getText().equals("")) {
            JOptionPane.showMessageDialog(frame,
                    "Please enter To address!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldTo.requestFocus();
            return false;
        }

        if (fieldSubject.getText().equals("")) {
            JOptionPane.showMessageDialog(frame,
                    "Please enter subject!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldSubject.requestFocus();
            return false;
        }

        if (textAreaMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(frame,
                    "Please enter message!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            textAreaMessage.requestFocus();
            return false;
        }

        return true;
    }

    public void launch() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

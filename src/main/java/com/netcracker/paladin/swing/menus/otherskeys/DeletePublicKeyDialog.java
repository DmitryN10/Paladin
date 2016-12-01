package com.netcracker.paladin.swing.menus.otherskeys;

import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.swing.SwingPaladinEmail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeletePublicKeyDialog extends JDialog {
    private JFrame parent;

    private EncryptionService encryptionService;

    private DefaultComboBoxModel<String> comboBoxModelEmails;
    private DefaultButtonModel buttonModelSend;

    private JLabel labelEmail = new JLabel("Email: ");
    private JComboBox comboBoxDelete;

    private JButton buttonDelete = new JButton("Delete");

    public DeletePublicKeyDialog(JFrame parent, EncryptionService encryptionService) {
        super(parent, "SMTP Settings", true);
        this.parent = parent;
        this.encryptionService = encryptionService;

        this.comboBoxModelEmails = ((SwingPaladinEmail) parent).getComboBoxModelEmails();
        this.buttonModelSend = ((SwingPaladinEmail) this.parent).getButtonModelSend();

        setupForm();

        pack();
        setLocationRelativeTo(parent);
    }

    private void setupForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.anchor = GridBagConstraints.WEST;

        add(labelEmail, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        comboBoxDelete = new JComboBox(comboBoxModelEmails);
        add(comboBoxDelete);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonDelete, constraints);

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buttonAddActionPerformed(event);
            }
        });
    }

    private void buttonAddActionPerformed(ActionEvent event) {
        try {
            String email = (String) comboBoxDelete.getSelectedItem();
            encryptionService.deletePublicKey(email);

            comboBoxModelEmails.removeElement(email);
            if(comboBoxModelEmails.getSize() == 0){
                comboBoxModelEmails.addElement(((SwingPaladinEmail) parent).getPlaceholderEmail());
                buttonModelSend.setEnabled(false);
            }

            updateEmailsWithPublicKeyList();

            JOptionPane.showMessageDialog(DeletePublicKeyDialog.this,
                    "Public key was deleted successfully!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error while deleting new public key: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmailsWithPublicKeyList(){
        List<String> allEmailsWithPublicKeyList = encryptionService.getAllEmailsWithPublicKey();
        if(allEmailsWithPublicKeyList.isEmpty()){
            allEmailsWithPublicKeyList.add("No public keys");
            buttonDelete.setEnabled(false);
        }else{
            buttonDelete.setEnabled(true);
        }
    }
}

package com.netcracker.paladin.swing.menus;

import com.netcracker.paladin.infrastructure.services.config.ConfigService;
import com.netcracker.paladin.infrastructure.services.email.EmailService;
import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.swing.dialogs.SettingsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ivan on 29.11.16.
 */
public class FileMenu extends JMenu {
    private final ConfigService configService;
    private final EmailService emailService;
    private final EncryptionService encryptionService;

//    private final JFrame frame;
    private final JMenuItem menuItemSetting = new JMenuItem("Settings..");

    public FileMenu(final JFrame frame, final ConfigService configService, final EmailService emailService, final EncryptionService encryptionService) {
        super("File");
        this.configService = configService;
        this.emailService = emailService;
        this.encryptionService = encryptionService;
//        this.frame = frame;

        menuItemSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SettingsDialog dialog = new SettingsDialog(frame, configService);
                dialog.setVisible(true);
            }
        });
        add(menuItemSetting);
    }
}

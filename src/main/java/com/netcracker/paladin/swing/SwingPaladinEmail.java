package com.netcracker.paladin.swing;

/**
 * Created by ivan on 26.11.16.
 */

import com.netcracker.paladin.infrastructure.services.config.ConfigService;
import com.netcracker.paladin.infrastructure.services.email.EmailService;
import com.netcracker.paladin.infrastructure.services.encryption.EncryptionService;
import com.netcracker.paladin.swing.menus.FileMenu;
import com.netcracker.paladin.swing.tabs.TabRead;
import com.netcracker.paladin.swing.tabs.TabSend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SwingPaladinEmail {
    private final ConfigService configService;
    private final EmailService emailService;
    private final EncryptionService encryptionService;

    private final static String TABSEND = "Send an email";
    private final static String TABREAD = "Read emails";

    private JFrame frame;

    public SwingPaladinEmail(ConfigService configService, EmailService emailService, EncryptionService encryptionService) {
        this.configService = configService;
        this.emailService = emailService;
        this.encryptionService = encryptionService;
    }

    public void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab(TABSEND, new TabSend(emailService, encryptionService));
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab(TABREAD, new TabRead(emailService, encryptionService));
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        pane.add(tabbedPane);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Paladin Email");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingPaladinEmail swingEmailSender = new SwingPaladinEmail(configService, emailService, encryptionService);
        swingEmailSender.addComponentToPane(frame.getContentPane());

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(new FileMenu(frame, configService, emailService, encryptionService));
        frame.setJMenuBar(jMenuBar);

        frame.pack();
        frame.setVisible(true);
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

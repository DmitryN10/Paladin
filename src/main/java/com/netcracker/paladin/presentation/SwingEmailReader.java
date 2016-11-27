package com.netcracker.paladin.presentation;

import com.netcracker.paladin.domain.MessageEntry;
import com.netcracker.paladin.infrastructure.ConfigUtility;
import com.netcracker.paladin.infrastructure.mail.EmailUtility;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static com.netcracker.paladin.presentation.PlainEmailChecker.check;

public class SwingEmailReader extends JFrame {
    private final ConfigUtility configUtility;
    private final EmailUtility emailUtility;

    private static final String storeType = "pop3";
    private List<MessageEntry> messageContainerList;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuItemSetting = new JMenuItem("Settings..");

    private JLabel labelTo = new JLabel("To: ");
    private JLabel labelSubject = new JLabel("Subject: ");

    private JTextField fieldTo = new JTextField(30);
    private JTextField fieldSubject = new JTextField(30);

    private JButton buttonSend = new JButton("SEND");

    private JFilePicker filePicker = new JFilePicker("Attached", "Attach File...");

    private DefaultStyledDocument doc = new DefaultStyledDocument(new StyleContext());

//    private JTextArea textAreaMessage = new JTextArea(10, 30);

    private GridBagConstraints constraints = new GridBagConstraints();

    public SwingEmailReader(ConfigUtility configUtility, EmailUtility emailUtility){
        super("Swing E-mail Reader Program");

        this.configUtility = configUtility;
        this.emailUtility = emailUtility;

        this.messageContainerList = emailUtility.readEmails();

        // set up layout
        setLayout(new GridBagLayout());
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        setupMenu();
        setupForm();

        pack();
        setLocationRelativeTo(null);    // center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("Over");
    }

    public DefaultStyledDocument getDoc() {
        return doc;
    }

    private void setupMenu() {
//        menuItemSetting.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                SettingsDialog dialog = new SettingsDialog(SwingEmailSender.this, configUtility);
//                dialog.setVisible(true);
//            }
//        });

//        menuFile.add(menuItemSetting);
//        menuBar.add(menuFile);
//        setJMenuBar(menuBar);
    }

    private void setupForm() {
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        add(labelTo, constraints);
//
//        constraints.gridx = 1;
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        add(fieldTo, constraints);
//
//        constraints.gridx = 0;
//        constraints.gridy = 1;
//        add(labelSubject, constraints);
//
//        constraints.gridx = 1;
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        add(fieldSubject, constraints);

//        constraints.gridx = 2;
//        constraints.gridy = 0;
//        constraints.gridheight = 2;
//        constraints.fill = GridBagConstraints.BOTH;
//        buttonSend.setFont(new Font("Arial", Font.BOLD, 16));
//        add(buttonSend, constraints);

//        buttonSend.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                buttonSendActionPerformed(event);
//            }
//        });

//        constraints.gridx = 0;
//        constraints.gridy = 2;
//        constraints.gridheight = 1;
//        constraints.gridwidth = 3;
//        filePicker.setMode(JFilePicker.MODE_OPEN);
//        add(filePicker, constraints);

        constraints.gridy = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

//        StyleContext sc = new StyleContext();
//        final DefaultStyledDocument doc = new DefaultStyledDocument(sc);

        try {
//            Message[] messages = check();
//            doc.insertString(0, "Pasha Dolboeb", null);
            doc.insertString(0, "No mail :(", null);
        }catch (BadLocationException ble){
            throw new IllegalStateException();
        }

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JTextPane(doc), constraints);
    }

    private void buttonSendActionPerformed(ActionEvent event) {
//        if (!validateFields()) {
//            return;
//        }

//        String toAddress = fieldTo.getText();
//        String subject = fieldSubject.getText();
//        String message = textAreaMessage.getText();
//
//        File[] attachFiles = null;
//
//        if (!filePicker.getSelectedFilePath().equals("")) {
//            File selectedFile = new File(filePicker.getSelectedFilePath());
//            attachFiles = new File[] {selectedFile};
//        }
//
//        try {
//            Properties smtpProperties = configUtility.loadProperties();
//            EmailUtility.sendEmail(smtpProperties, toAddress, subject, message, attachFiles);
//
//            JOptionPane.showMessageDialog(this,
//                    "The e-mail has been sent successfully!");
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this,
//                    "Error while sending the e-mail: " + ex.getMessage(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }

    public  void launch() {

        List<MessageEntry> messageContainerList = emailUtility.readEmails();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    SwingEmailReader swingEmailReader = new SwingEmailReader(configUtility, emailUtility);
                    swingEmailReader.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new IllegalStateException();
                }
            }
        });

        check();
    }

//    public  void writePart(Part p) throws Exception {
//
//        int i = 0;
//
//        if (p instanceof Message)
//            //Call methos writeEnvelope
//            writeEnvelope((Message) p);
//
//        System.out.println("----------------------------");
//        System.out.println("CONTENT-TYPE: " + p.getContentType());
//
//        //check if the content is plain text
//        if (p.isMimeType("text/plain")) {
//            System.out.println("This is plain text");
//            System.out.println("---------------------------");
//            System.out.println((String) p.getContent());
//        }
//        //check if the content has attachment
//        else if (p.isMimeType("multipart/*")) {
//            System.out.println("This is a Multipart");
//            System.out.println("---------------------------");
//            Multipart mp = (Multipart) p.getContent();
//            int count = mp.getCount();
//            for (i = 0; i < count; i++)
//                writePart(mp.getBodyPart(i));
//        }
//        //check if the content is a nested message
//        else if (p.isMimeType("message/rfc822")) {
//            System.out.println("This is a Nested Message");
//            System.out.println("---------------------------");
//            writePart((Part) p.getContent());
//        }
//        //check if the content is an inline image
//        else if (p.isMimeType("image/jpeg")) {
//            System.out.println("--------> image/jpeg");
//            Object o = p.getContent();
//
//            InputStream x = (InputStream) o;
//            // Construct the required byte array
//            System.out.println("x.length = " + x.available());
//            byte[] bArray = new byte[x.available()];
//            while ((i = (int) ((InputStream) x).available()) > 0) {
//                int result = (int) (((InputStream) x).read(bArray));
//                if (result == -1)
//                    i = 0;
//                bArray = new byte[x.available()];
//
//                break;
//            }
//            FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
//            f2.write(bArray);
//        }
//        else if (p.getContentType().contains("image/")) {
//            System.out.println("content type" + p.getContentType());
//            File f = new File("image" + new Date().getTime() + ".jpg");
//            DataOutputStream output = new DataOutputStream(
//                    new BufferedOutputStream(new FileOutputStream(f)));
//            com.sun.mail.util.BASE64DecoderStream test =
//                    (com.sun.mail.util.BASE64DecoderStream) p
//                            .getContent();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = test.read(buffer)) != -1) {
//                output.write(buffer, 0, bytesRead);
//            }
//        }
//        else {
//            Object o = p.getContent();
//            if (o instanceof String) {
//                System.out.println("This is a string");
//                System.out.println("---------------------------");
//                System.out.println((String) o);
//            }
//            else if (o instanceof InputStream) {
//                System.out.println("This is just an input stream");
//                System.out.println("---------------------------");
//                InputStream is = (InputStream) o;
//                is = (InputStream) o;
//                int c;
//                while ((c = is.read()) != -1)
//                    System.out.write(c);
//            }
//            else {
//                System.out.println("This is an unknown type");
//                System.out.println("---------------------------");
//                System.out.println(o.toString());
//            }
//        }
//
//    }
//
//    public  void writeEnvelope(Message m) throws Exception {
//        System.out.println("This is the message envelope");
//        System.out.println("---------------------------");
//        Address[] a;
//
//        // FROM
//        if ((a = m.getFrom()) != null) {
//            for (int j = 0; j < a.length; j++)
//                System.out.println("FROM: " + a[j].toString());
//        }
//
//        // TO
//        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
//            for (int j = 0; j < a.length; j++)
//                System.out.println("TO: " + a[j].toString());
//        }
//
//        // SUBJECT
//        if (m.getSubject() != null)
//            System.out.println("SUBJECT: " + m.getSubject());
//
//    }
}

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dialog extends JDialog {

    private JFrame frame;
    private boolean confirmation;

    public Dialog() {
        this.frame = new JFrame();
    }

    public Dialog(JFrame frame) {
        this.frame = frame;
        setBackground(Theme.getBackground());
    }

    public JFrame getFrame() {
        return frame;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public boolean showDialog(String dialogType, String dialogTitle, String messageTitle, String messageContent, boolean closeOperation) {
        JDialog messageDialog = new JDialog(this.frame, dialogTitle, true); // true = modal dialog
        messageDialog.setLayout(new GridBagLayout());
        messageDialog.setSize(new Dimension(600, 450));
        messageDialog.setResizable(false);
        messageDialog.setBackground(Theme.getBackground());
        messageDialog.getContentPane().setBackground(Theme.getBackground());

        // if true make the dialog not closable unless a input is received
        if(closeOperation)
            messageDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        else
            messageDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel messageIconPanel = new JPanel(new GridBagLayout());
        messageIconPanel.setBackground(Theme.getBackground());
        switch (dialogType) {
            case "SUCCESS" -> messageIconPanel.add(new JLabel(IconLoader.getDialogSuccessIcon()));
            case "ERROR" -> messageIconPanel.add(new JLabel(IconLoader.getDialogErrorIcon()));
            case "HAZARD" -> messageIconPanel.add(new JLabel(IconLoader.getDialogHazardIcon()));
        }

        JPanel messageTextPanel = new JPanel(new GridBagLayout());
        messageTextPanel.setBackground(Theme.getBackground());

        JLabel messageTitleLabel = new JLabel(messageTitle);
        messageTitleLabel.setForeground(Theme.getForeground());
        messageTitleLabel.setFont(CustomFonts.ROBOTO_BLACK.deriveFont(30f));

        JLabel messageContentLabel = new JLabel(messageContent);
        messageContentLabel.setForeground(Theme.getForeground());
        messageContentLabel.setFont(CustomFonts.ROBOTO_REGULAR.deriveFont(20f));

        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 20, 0);
        messageTextPanel.add(messageTitleLabel, gbc);
        messageTextPanel.add(messageContentLabel, gbc);

        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.NONE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 20, 0, 20);
        messageDialog.add(messageIconPanel, gbc);
        messageDialog.add(messageTextPanel, gbc);

        JPanel messageButtonPanel = new JPanel(new GridBagLayout());
        messageButtonPanel.setBackground(Theme.getBackground());

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(0, 5, 0, 5);

        if(dialogType.equals("ERROR") || dialogType.equals("HAZARD")) {
            RoundedButton cancelButton = createDialogButton("Cancel");
            cancelButton.addActionListener(e -> {
                messageDialog.dispose();
                confirmation = false;
            });

            String buttonText;
            if(dialogType.equals("ERROR")) buttonText = "OK";
            else buttonText = "Continue";

            RoundedButton oKButton = createDialogButton(buttonText);
            oKButton.addActionListener(e -> {
                messageDialog.dispose();
                confirmation = true;
            });

            messageButtonPanel.add(cancelButton, gbcButton);
            messageButtonPanel.add(oKButton, gbcButton);
        }

        else if(dialogType.equals("SUCCESS")) {
            RoundedButton oKButton = createDialogButton("OK");
            oKButton.addActionListener(e -> {messageDialog.dispose();});

            messageButtonPanel.add(oKButton, gbcButton);
        }

        messageDialog.add(messageButtonPanel, gbc);

        messageDialog.setLocationRelativeTo(this.frame);
        messageDialog.setVisible(true);
        return confirmation;
    }

    private RoundedButton createDialogButton(String text) {
        RoundedButton button = new RoundedButton(5, Theme.getHoverBackground());
        button.setText(text);
        button.setPreferredSize(new Dimension(110, 40));
        button.setForeground(Theme.getForeground());
        button.setFont(CustomFonts.INSTRUMENT_SANS_REGULAR.deriveFont(17f));
        // button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusable(false);

        return button;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}

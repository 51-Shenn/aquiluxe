package gui;

import javax.swing.*;

public class Dialog extends JDialog {

    private final JFrame frame;

    public Dialog(JFrame frame) {
        this.frame = frame;
    }

    public void showSuccessDialog() {
        // JDialog messageDialog = new JDialog(this.frame, "Welcome", true); // true = modal dialog
        // messageDialog.setLayout(new BorderLayout());
        // messageDialog.setSize(300, 150);
        // messageDialog.setLocationRelativeTo(this.frame);

        // // add content to dialog
        // JLabel messageLabel = new JLabel("Login Successful! Welcome, " + "Brian Kam");
        // messageLabel.setIcon(new ImageIcon("images/icons/error.png"));
        // messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // messageDialog.add(messageLabel, BorderLayout.CENTER);

        // // Add a "Close" button
        // JButton closeButton = new JButton("Close");
        // closeButton.addActionListener(e -> messageDialog.dispose());
        // messageDialog.add(closeButton, BorderLayout.SOUTH);

        // messageDialog.setVisible(true);
    }
}

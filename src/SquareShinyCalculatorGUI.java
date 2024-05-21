import javax.swing.*;
import java.awt.*;

public class SquareShinyCalculatorGUI {

    private final JFrame frame;
    private final JTextField tIdField;
    private final JTextField pIdField;
    private final JTextArea resultArea;

    public SquareShinyCalculatorGUI() {
        frame = new JFrame("Shiny Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 280);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        pIdField = new JTextField(10);
        tIdField = new JTextField(10);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        panel.add(new JLabel("请输入Pid:"));
        panel.add(pIdField);
        panel.add(new JLabel("请输入Tid:"));
        panel.add(tIdField);
        panel.add(createButton("三代后 < 8为闪", 8, false));
        panel.add(createButton("六代后 < 16 为闪", 16, false));
        panel.add(createButton("八代 == 0 为方闪", 0, true));
        panel.add(createButton("八代大冒险 == 1 为闪", 1, true));
        panel.add(new JScrollPane(resultArea));

        frame.getContentPane().add(panel);
    }

    private JButton createButton(String text, int condition, boolean isEquals) {
        JButton button = new JButton(text);
        button.addActionListener(_ -> calculate(condition, isEquals));
        return button;
    }

    public void show() {
        frame.setVisible(true);
    }

    private void calculate(int condition, boolean isEquals) {
        try {
            int tId = Integer.parseInt(tIdField.getText());
            long pId = Long.parseLong(pIdField.getText().substring(2), 16);

            int p1 = (int)(pId >>> 16);
            int p2 = (int)(pId & 0xFFFF);
            int p1_xor_p2 = p1 ^ p2;

            resultArea.setText("SID范围：\n");
            for (int i = 0; i <= 65536; i++) {
                int xorResult = (tId ^ i) ^ p1_xor_p2;
                if (isEquals ? xorResult == condition : xorResult < condition) {
                    resultArea.append(i + "\n");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "输入的pId和tId必须是有效的数值", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SquareShinyCalculatorGUI().show());
    }
}

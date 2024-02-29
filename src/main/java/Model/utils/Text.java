package Model.utils;

import javax.swing.*;

public class Text {
    public static void Imprimir(String msg, String title){
        JTextArea textArea = new JTextArea(15, 30);
        textArea.setText(msg);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static boolean verificarSeNaoEstaVazioNull(String validar) {
        return validar != null && !validar.isEmpty();
    }
}

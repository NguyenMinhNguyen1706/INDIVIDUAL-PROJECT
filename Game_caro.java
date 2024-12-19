import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Game_caro implements ActionListener {

    // Biến ngẫu nhiên để xác định người chơi nào đi trước
    private Random random = new Random();

    // Các thành phần giao diện chính
    private JFrame frame = new JFrame(); // Khung chính của trò chơi
    private JPanel titlePanel = new JPanel(); // Panel chứa tiêu đề
    private JPanel buttonPanel = new JPanel(); // Panel chứa các nút chơi
    private JPanel resetPanel = new JPanel(); // Panel chứa nút reset
    private JLabel textfield = new JLabel(); // Nhãn hiển thị trạng thái trò chơi
    private JButton[] buttons = new JButton[9]; // Mảng chứa các nút ô cờ
    private JButton resetButton = new JButton("Reset"); // Nút reset trò chơi
    private boolean player1Turn; // Cờ kiểm tra lượt của người chơi

    public Game_caro() {
        // Cài đặt cửa sổ chính
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50)); // Màu nền
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Cài đặt nhãn hiển thị trạng thái trò chơi
        textfield.setBackground(new Color(25, 25, 25)); // Màu nền
        textfield.setForeground(new Color(25, 255, 0)); // Màu chữ
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75)); // Font chữ
        textfield.setHorizontalAlignment(JLabel.CENTER); // Canh giữa
        textfield.setText("Tic-Tac-Toe"); // Văn bản ban đầu
        textfield.setOpaque(true); // Hiển thị màu nền

        // Panel tiêu đề (chứa nhãn)
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        // Panel chứa các nút chơi (bàn cờ 3x3)
        buttonPanel.setLayout(new GridLayout(3, 3)); // Bố cục lưới 3x3
        buttonPanel.setBackground(new Color(150, 150, 150)); // Màu nền

        // Tạo các nút cho bàn cờ
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton(); // Tạo nút mới
            buttonPanel.add(buttons[i]); // Thêm nút vào panel
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120)); // Font chữ to, đậm
            buttons[i].setFocusable(false); // Bỏ khung chọn (focus)
            buttons[i].addActionListener(this); // Thêm sự kiện khi nhấn nút
        }

        // Nút Reset để chơi lại
        resetButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Font chữ
        resetButton.addActionListener(e -> resetGame()); // Thêm sự kiện reset
        resetPanel.add(resetButton); // Thêm nút reset vào panel

        // Thêm các thành phần vào khung chính
        titlePanel.add(textfield); // Thêm nhãn vào panel tiêu đề
        frame.add(titlePanel, BorderLayout.NORTH); // Thêm panel tiêu đề vào phía trên
        frame.add(buttonPanel, BorderLayout.CENTER); // Thêm panel bàn cờ vào giữa
        frame.add(resetPanel, BorderLayout.SOUTH); // Thêm panel reset vào phía dưới

        // Xác định lượt chơi đầu tiên
        firstTurn();
    }

    // Xử lý sự kiện khi nhấn các nút trên bàn cờ
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) { // Kiểm tra nút nào được nhấn
                if (player1Turn) { // Nếu là lượt của người chơi 1
                    if (buttons[i].getText().isEmpty()) { // Chỉ xử lý nếu ô trống
                        buttons[i].setForeground(new Color(255, 0, 0)); // Màu chữ đỏ cho X
                        buttons[i].setText("X"); // Đặt chữ X
                        player1Turn = false; // Chuyển lượt sang người chơi 2
                        textfield.setText("O turn"); // Cập nhật trạng thái
                        check(); // Kiểm tra điều kiện thắng
                    }
                } else { // Nếu là lượt của người chơi 2
                    if (buttons[i].getText().isEmpty()) { // Chỉ xử lý nếu ô trống
                        buttons[i].setForeground(new Color(0, 0, 255)); // Màu chữ xanh cho O
                        buttons[i].setText("O"); // Đặt chữ O
                        player1Turn = true; // Chuyển lượt sang người chơi 1
                        textfield.setText("X turn"); // Cập nhật trạng thái
                        check(); // Kiểm tra điều kiện thắng
                    }
                }
            }
        }
    }

    // Xác định người chơi nào đi trước
    private void firstTurn() {
        player1Turn = random.nextBoolean(); // Ngẫu nhiên chọn người chơi đầu tiên
        textfield.setText(player1Turn ? "X turn" : "O turn"); // Cập nhật trạng thái
    }

    // Kiểm tra các điều kiện thắng
    private void check() {
        // Các điều kiện thắng cho X
        if ((buttons[0].getText().equals("X") && buttons[1].getText().equals("X") && buttons[2].getText().equals("X"))) {
            xWins(0, 1, 2);
        }
        if ((buttons[3].getText().equals("X") && buttons[4].getText().equals("X") && buttons[5].getText().equals("X"))) {
            xWins(3, 4, 5);
        }
        // (Các điều kiện khác tương tự...)

        // Các điều kiện thắng cho O
        if ((buttons[0].getText().equals("O") && buttons[1].getText().equals("O") && buttons[2].getText().equals("O"))) {
            oWins(0, 1, 2);
        }
        if ((buttons[3].getText().equals("O") && buttons[4].getText().equals("O") && buttons[5].getText().equals("O"))) {
            oWins(3, 4, 5);
        }
        // (Các điều kiện khác tương tự...)
    }

    // Hiển thị khi X thắng
    private void xWins(int a, int b, int c) {
        highlightWin(a, b, c); // Tô màu các ô thắng
        textfield.setText("X wins"); // Hiển thị trạng thái
        disableButtons(); // Vô hiệu hóa các nút còn lại
    }

    // Hiển thị khi O thắng
    private void oWins(int a, int b, int c) {
        highlightWin(a, b, c); // Tô màu các ô thắng
        textfield.setText("O wins"); // Hiển thị trạng thái
        disableButtons(); // Vô hiệu hóa các nút còn lại
    }

    // Tô màu các ô thắng
    private void highlightWin(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN); // Tô màu xanh lá cho các ô thắng
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);
    }

    // Vô hiệu hóa các nút còn lại
    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    // Đặt lại trạng thái trò chơi
    private void resetGame() {
        for (JButton button : buttons) {
            button.setText(""); // Xóa nội dung các nút
            button.setBackground(null); // Đặt lại màu nền
            button.setEnabled(true); // Kích hoạt lại nút
        }
        textfield.setText("GAME CO CARO"); // Đặt lại trạng thái
        firstTurn(); // Xác định lượt đầu tiên
    }
}

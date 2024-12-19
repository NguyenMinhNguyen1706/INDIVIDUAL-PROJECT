
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class Game_caro implements ActionListener {

    Random random = new Random(); // Tạo đối tượng để xác định lượt đi đầu tiên ngẫu nhiên.
    Timer timer = new Timer(); // Tạo đối tượng để quản lý thời gian (ví dụ: delay trước khi bắt đầu trò chơi hoặc reset).
    JFrame frame = new JFrame(); // Tạo cửa sổ chính của trò chơi.
    JPanel titlePanel = new JPanel(); // Panel để hiển thị tiêu đề của trò chơi.
    JPanel buttonPanel = new JPanel(); // Panel chứa các nút tương ứng với bảng cờ.
    JLabel textfield = new JLabel(); // Nhãn để hiển thị thông báo trạng thái của trò chơi.
    JButton[] buttons = new JButton[9]; // Mảng chứa 9 nút tương ứng với 9 ô trên bảng cờ.
    boolean player1Turn; // Biến boolean để kiểm tra lượt chơi hiện tại (người chơi 1: true, người chơi 2: false).
    boolean tie = true; // Biến boolean để xác định nếu trò chơi hòa.
    int turns = 0; // Biến đếm số lượt đã chơi.

    Game_caro() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng chương trình khi người dùng thoát cửa sổ.
        frame.setSize(800, 800); // Đặt kích thước cửa sổ.
        frame.getContentPane().setBackground(new Color(50, 50, 50)); // Đặt màu nền của cửa sổ.
        frame.setLayout(new BorderLayout()); // Sử dụng bố cục BorderLayout.
        frame.setVisible(true); // Hiển thị cửa sổ.
        frame.setTitle("Tic Tac Toe"); // Đặt tiêu đề cửa sổ.

        textfield.setBackground(new Color(25, 25, 25)); // Đặt màu nền cho nhãn.
        textfield.setForeground(new Color(25, 255, 0)); // Đặt màu chữ cho nhãn.
        textfield.setFont(new Font("Ink free", Font.BOLD, 75)); // Đặt font chữ, kiểu và kích thước.
        textfield.setHorizontalAlignment(JLabel.CENTER); // Canh giữa nội dung trong nhãn.
        textfield.setText("Tic Tac Toe"); // Đặt nội dung hiển thị ban đầu.
        textfield.setOpaque(true); // Hiển thị màu nền.

        titlePanel.setLayout(new BorderLayout()); // Sử dụng bố cục BorderLayout cho panel tiêu đề.
        titlePanel.setBounds(0, 0, 800, 100); // Đặt kích thước cho panel.

        buttonPanel.setLayout(new GridLayout(3, 3)); // Bố cục lưới 3x3 cho panel chứa các nút.

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton(); // Tạo nút mới.
            buttonPanel.add(buttons[i]); // Thêm nút vào panel.
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120)); // Đặt font chữ lớn và đậm.
            buttons[i].setFocusable(false); // Tắt focus trên nút.
            buttons[i].setEnabled(false); // Tạm thời vô hiệu hóa nút.
            buttons[i].addActionListener(this); // Gắn sự kiện khi nhấn nút.
        }

        titlePanel.add(textfield); // Thêm nhãn vào panel tiêu đề.
        frame.add(titlePanel, BorderLayout.NORTH); // Thêm panel tiêu đề vào phía trên.
        frame.add(buttonPanel); // Thêm panel chứa các nút vào giữa.

        firstTurn(); // Xác định lượt chơi đầu tiên.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) { // Kiểm tra nút nào được nhấn.
                if (player1Turn) { // Nếu là lượt của người chơi 1.
                    if (buttons[i].getText() == "") { // Nếu ô đó chưa có ký tự.
                        buttons[i].setForeground(new Color(255, 0, 0)); // Đặt màu chữ đỏ cho "X".
                        buttons[i].setText("X"); // Gán chữ "X".
                        turns++; // Tăng số lượt.
                        player1Turn = false; // Chuyển lượt sang người chơi 2.
                        textfield.setText("O's turn"); // Cập nhật trạng thái.
                        check(); // Kiểm tra điều kiện thắng/thua.
                    }
                } else { // Nếu là lượt của người chơi 2.
                    if (buttons[i].getText() == "") {
                        buttons[i].setForeground(new Color(0, 0, 255)); // Đặt màu chữ xanh cho "O".
                        buttons[i].setText("O");
                        turns++;
                        player1Turn = true; // Chuyển lượt sang người chơi 1.
                        textfield.setText("X's turn");
                        check(); // Kiểm tra điều kiện thắng/thua.
                    }
                }
            }
        }
    }

    public void firstTurn() {
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            if (random.nextInt(2) == 0) { // Xác định ngẫu nhiên người chơi đầu tiên.
                player1Turn = true;
                textfield.setText("X's turn"); // Thông báo lượt của X.
            } else {
                player1Turn = false;
                textfield.setText("O's turn"); // Thông báo lượt của O.
            }
            for (int i = 0; i < 9; i++) {
                buttons[i].setEnabled(true); // Bật tất cả các nút để bắt đầu chơi.
            }
        }
    }, 2000); // Trì hoãn 3 giây trước khi bắt đầu.
}


    public void check() {
        // Kiểm tra điều kiện thắng của X.
        if ((buttons[0].getText() == "X") && (buttons[1].getText() == "X") && (buttons[2].getText() == "X")) {
            xWins(0, 1, 2);
            tie = false;
        }
        if ((buttons[3].getText() == "X") && (buttons[4].getText() == "X") && (buttons[5].getText() == "X")) {
            xWins(3, 4, 5);
            tie = false;
        }
        if ((buttons[6].getText() == "X") && (buttons[7].getText() == "X") && (buttons[8].getText() == "X")) {
            xWins(6, 7, 8);
            tie = false;
        }
        if ((buttons[0].getText() == "X") && (buttons[3].getText() == "X") && (buttons[6].getText() == "X")) {
            xWins(0, 3, 6);
            tie = false;
        }
        if ((buttons[1].getText() == "X") && (buttons[4].getText() == "X") && (buttons[7].getText() == "X")) {
            xWins(1, 4, 7);
            tie = false;
        }
        if ((buttons[2].getText() == "X") && (buttons[5].getText() == "X") && (buttons[8].getText() == "X")) {
            xWins(2, 5, 8);
            tie = false;
        }
        if ((buttons[0].getText() == "X") && (buttons[4].getText() == "X") && (buttons[8].getText() == "X")) {
            xWins(0, 4, 8);
            tie = false;
        }
        if ((buttons[2].getText() == "X") && (buttons[4].getText() == "X") && (buttons[6].getText() == "X")) {
            xWins(2, 4, 6);
            tie = false;
        }
        // Kiểm tra điều kiện thắng của O.
        if ((buttons[0].getText() == "O") && (buttons[1].getText() == "O") && (buttons[2].getText() == "O")) {
            oWins(0, 1, 2);
            tie = false;
        }
        if ((buttons[3].getText() == "O") && (buttons[4].getText() == "O") && (buttons[5].getText() == "O")) {
            oWins(3, 4, 5);
            tie = false;
        }
        if ((buttons[6].getText() == "O") && (buttons[7].getText() == "O") && (buttons[8].getText() == "O")) {
            oWins(6, 7, 8);
            tie = false;
        }
        if ((buttons[0].getText() == "O") && (buttons[3].getText() == "O") && (buttons[6].getText() == "O")) {
            oWins(0, 3, 6);
            tie = false;
        }
        if ((buttons[1].getText() == "O") && (buttons[4].getText() == "O") && (buttons[7].getText() == "O")) {
            oWins(1, 4, 7);
            tie = false;
        }
        if ((buttons[2].getText() == "O") && (buttons[5].getText() == "O") && (buttons[8].getText() == "O")) {
            oWins(2, 5, 8);
            tie = false;
        }
        if ((buttons[0].getText() == "O") && (buttons[4].getText() == "O") && (buttons[8].getText() == "O")) {
            oWins(0, 4, 8);
            tie = false;
        }
        if ((buttons[2].getText() == "O") && (buttons[4].getText() == "O") && (buttons[6].getText() == "O")) {
            oWins(2, 4, 6);
            tie = false;
        }
        // Nếu như điền tất cả 9 ô rồi mà vẫn không tìm ra được bên chiến thắng thì Hòa
        if (tie && turns == 9) {
            tie();
        }
    }

    public void xWins(int a, int b, int c) {

        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins!");
        resetting();
    }

    public void oWins(int a, int b, int c) {

        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins!");
        resetting();
    }

    public void tie() {

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("Tie!");
        resetting();

    }

    public void resetting() {

        firstTurn();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    buttons[i].setEnabled(true);
                    buttons[i].setText("");
                    buttons[i].setBackground(null);
                }
            }
        }, 2000);
    }
}

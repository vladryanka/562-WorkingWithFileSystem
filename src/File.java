import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


class FileChooserTest extends JFrame {
    private JButton btnSaveFile = null;
    private JButton btnOpenDir = null;
    private JButton btnFileEdit = null;
    private JButton btnFileRename = null;
    private JButton editButton = null;
    private JFileChooser fileChooser = null;
    static JPanel jPanel = new JPanel();
    static JPanel jPanel2 = new JPanel();
    static JFrame jFrame = getFrame();
    static JFrame jFrame2 = getFrame();


    static JFrame getFrame() {
        JFrame jFrame = new JFrame() {
        };
        jFrame.setVisible(true);
        jFrame.setBounds(750, 250, 500, 50);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        return jFrame;
    }

    public FileChooserTest() {
        super("Пример FileChooser");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Кнопка создания диалогового окна для выбора директории
        btnOpenDir = new JButton("Открыть файл");
        // Кнопка создания диалогового окна для сохранения файла
        btnSaveFile = new JButton("Сохранить файл");
        // Кнопка создания диалогового окна для сохранения файла
        btnFileEdit = new JButton("Изменить файл");
        btnFileRename = new JButton("Переименовать файл");

        // Создание экземпляра JFileChooser
        fileChooser = new JFileChooser();
        // Подключение слушателей к кнопкам
        addFileChooserListeners();

        // Размещение кнопок в интерфейсе
        JPanel contents = new JPanel();
        contents.add(btnOpenDir);
        contents.add(btnSaveFile);
        contents.add(btnFileRename);
        contents.add(btnFileEdit);

        setContentPane(contents);
        // Вывод окна на экран
        setSize(360, 110);
        setVisible(true);
    }

    private void addFileChooserListeners() {
        btnOpenDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Открыть файл");
                // Определение режима - только файл
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(FileChooserTest.this);
                // Если директория выбрана, покажем ее в сообщении
                if (result == JFileChooser.APPROVE_OPTION) {
                    fileChooser.showOpenDialog(fileChooser.getParent());
                    File file1 = fileChooser.getSelectedFile();
                    String text = "";

                    try (FileInputStream fin = new FileInputStream(file1)) {
                        int i;
                        while ((i = fin.read()) != -1) {
                            text += (char) i;
                        }
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    JOptionPane.showMessageDialog(FileChooserTest.this,
                            text);
                }

            }
        });
        btnSaveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Сохранение файла");
                // Определение режима - только файл
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showSaveDialog(FileChooserTest.this);
                // Если файл выбран, то представим его в сообщении
                if (result == JFileChooser.APPROVE_OPTION)
                    JOptionPane.showMessageDialog(FileChooserTest.this,
                            "Файл '" + fileChooser.getSelectedFile() +
                                    " ) сохранен");
            }
        });
        btnFileEdit.addActionListener(new ActionListener() {
            String text = "";

            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Изменить файл");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(FileChooserTest.this);
                if (result == JFileChooser.APPROVE_OPTION) {

                    File file1 = fileChooser.getSelectedFile();
                    //READING file
                    try (FileInputStream fin = new FileInputStream(file1)) {
                        int i;
                        while ((i = fin.read()) != -1) {
                            text += (char) i;
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                    jFrame.add(jPanel);
                    jFrame.setLayout(new BorderLayout());
                    JTextField jTextField = new JTextField(text, 20);
                    jPanel.add(jTextField);
                    jPanel.revalidate();


                    editButton = new JButton("Изменить");
                    jFrame.add(editButton, BorderLayout.SOUTH);
                    jFrame.setLayout(new GridBagLayout());
                    text += jTextField.getText();
                    // при нажатии на кнопку изменить
                    editButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //writing to file
                            try (FileWriter writer = new FileWriter(file1, false)) {
                                String str = jTextField.getText();
                                text = str;
                                writer.write(text);
                            } catch (IOException ex) {
                                System.out.println("Error");
                            }
                        }
                    });
                }
            }
        });
        btnFileRename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Переименовать файл");
                // Определение режима - только файл
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showSaveDialog(FileChooserTest.this);
                // Если файл выбран, то представим его в сообщении
                if (result == JFileChooser.APPROVE_OPTION) {
                    String text = fileChooser.getName();
                    System.out.println(text);
                    jFrame2.add(jPanel2);
                    jFrame2.setLayout(new BorderLayout());
                    JTextField jTextField = new JTextField(text, 20);
                    jPanel2.setLayout(new BorderLayout());
                    jPanel2.add(jTextField);
                    jPanel2.revalidate();
                    editButton = new JButton("Переименовать");
                    jFrame2.add(editButton, BorderLayout.SOUTH);
                    jFrame2.setLayout(new GridBagLayout());
                    // при нажатии на кнопку переименовать
                    editButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String newFileName = jTextField.getText();
                            File oldFile = fileChooser.getSelectedFile();
                            File newFileOrDirectoryName = new File(newFileName);
                            if (oldFile.renameTo(newFileOrDirectoryName)) {
                                System.out.println("Файл переименован");
                            } else {
                                System.out.println("Error");
                            }
                        }
                    });
                }
            }
        });
    }
}

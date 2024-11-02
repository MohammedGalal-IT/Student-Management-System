package assisgnmnetv2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem extends JFrame {

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Grade> grades = new ArrayList<>();

    private JTable table;
    private DefaultTableModel tableModel;
    private Double Average;
    JTextField avgField;
    JComboBox<String> stdBox = new JComboBox<>();

    private void tableSetup() {
        // Table to display data
        tableModel = new DefaultTableModel();
        //tableModel.addColumn("Student ID");
        tableModel.addColumn("Course");
        tableModel.addColumn("Teacher Name");
        tableModel.addColumn("Grade");
        //tableModel.addColumn("Average");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 250, 700, 200);
        mainPanel.add(scrollPane);

    }

    private void load(String studentName) {
        tableModel.setRowCount(0);
        double tempAverage = 0;

        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i).getStudent().getName().equals(studentName)) {
                    tableModel.addRow(new Object[]{
                        grades.get(i).getCourse().getName(),
                        grades.get(i).getCourse().getTeacher().getName(),
                        grades.get(i).getScore()
                    });
                    tempAverage += grades.get(i).getScore();
                    
            }

            Average = (tempAverage / students.get(i).getGrades().size());
            
        }
        JOptionPane.showMessageDialog(null, Average);

    }

    private void reloadStudentToComboBox() {
        stdBox.removeAllItems();
        for (Student student : students) {
            stdBox.addItem(student.getName());
        }
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Students:")) {
                    while ((line = reader.readLine()) != null && !line.equals("Teachers:")) {
                        String[] parts = line.split(",");
                        addStudent(new Student(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]), parts[3]));
                    }
                }
                if (line.startsWith("Teachers:")) {
                    while ((line = reader.readLine()) != null && !line.equals("Courses:")) {
                        String[] parts = line.split(",");
                        addTeacher(new Teacher(Integer.parseInt(parts[0]), parts[1], parts[2]));
                    }
                }
                if (line.startsWith("Courses:")) {
                    while ((line = reader.readLine()) != null && !line.equals("Grades:")) {
                        String[] parts = line.split(",");
                        // You may need to reference the teacher when creating courses
                        Teacher teacher = getTeacherByName(parts[2]); 
                        addCourse(new Course(Integer.parseInt(parts[0]), parts[1], teacher));
                    }
                }
                if (line.startsWith("Grades:")) {
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        Student student = getStudentByName(parts[0]); 
                        Course course = getCourseByName(parts[1]); 
                        addGrade(new Grade(student, course, Double.parseDouble(parts[2])));
                    }
                }
            }
            //JOptionPane.showMessageDialog(null, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }
    
    // Save students, teachers, courses, and grades to a text file
    public void saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Save students
            writer.write("Students:\n");
            for (Student student : getStudents()) {
                writer.write(student.getStudentId() + "," + student.getName() + "," + student.getAge() + "," + student.getGender() + "\n");
            }

            // Save teachers
            writer.write("Teachers:\n");
            for (Teacher teacher : getTeachers()) {
                writer.write(teacher.getTacherId() + "," + teacher.getName() + "," + teacher.getSpecialty() + "\n");
            }

            // Save courses
            writer.write("Courses:\n");
            for (Course course : getCourses()) {
                writer.write(course.getCourseId() + "," + course.getName() + "," + course.getTeacher().getName() + "\n");
            }

            // Save grades
            writer.write("Grades:\n");
            for (Grade grade : getGrades()) {
                writer.write(grade.getStudent().getName() + "," + grade.getCourse().getName() + "," + grade.getScore() + "\n");
            }

            JOptionPane.showMessageDialog(null, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }

    
    class HoverButton extends JButton {
    private Color normalColor;
    private Color hoverColor;

    public HoverButton(String text) {
        super(text);
        normalColor = Color.WHITE;
        hoverColor = Color.BLACK;
        setBackground(normalColor);
        setForeground(Color.BLACK);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        // Add mouse listener for hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                setForeground(Color.BLACK);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK); // Border color
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded corners
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 40);
    }
}
    
    JPanel mainPanel = new JPanel();
    
    
    public StudentManagementSystem() {
        //Background Color For The App
        mainPanel.setBackground(new Color(0x7673ab));
        mainPanel.setBounds(0, 0, 800,600);
        mainPanel.setLayout(null);
        add(mainPanel);
        
        
        setTitle("Student Management System");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
    
        
        loadFromFile("C:\\Users\\Mohammed Galal\\Desktop\\Java_project\\new.txt");
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        // Table to display data      
        tableSetup();
        
        // Add Title
        JLabel TitlePanel = new JLabel("School Managment System");
        TitlePanel.setBounds(225, 10,400, 30);
        TitlePanel.setFont(new Font("Arial", Font.PLAIN, 30)); // Change font size to 20
        TitlePanel.setForeground(Color.WHITE);
        mainPanel.add(TitlePanel);
        
        // Add student button
        HoverButton addStudentBtn = new HoverButton("Add Student");
        addStudentBtn.setBounds(30, 50, 150, 30);
        mainPanel.add(addStudentBtn);
        addStudentBtn.addActionListener(e -> addStudent());

        // Add teacher button
        HoverButton addTeacherBtn = new HoverButton("Add Teacher");
        addTeacherBtn.setBounds(210, 50, 150, 30);
        mainPanel.add(addTeacherBtn);
        addTeacherBtn.addActionListener(e -> addTeacher());

        // Add course button
        HoverButton addcourseBtn = new HoverButton("Add Course");
        addcourseBtn.setBounds(400, 50, 150, 30);
        mainPanel.add(addcourseBtn);
        addcourseBtn.addActionListener(e -> addCourse());

        // Add grade button
        HoverButton addgradeBtn = new HoverButton("Add Grade");
        addgradeBtn.setBounds(600, 50, 150, 30);
        mainPanel.add(addgradeBtn);
        addgradeBtn.addActionListener(e -> addGrade());

        //  Add Student ComboBox
        stdBox.setBounds(30, 100, 150, 30);
        stdBox.setBackground(Color.WHITE);
        mainPanel.add(stdBox);
        reloadStudentToComboBox();

        // Add ComboBox Button
        HoverButton coboBoxBtn = new HoverButton("Refreah");
        coboBoxBtn.setBounds(210, 100, 150, 30);
        mainPanel.add(coboBoxBtn);
        coboBoxBtn.addActionListener(e -> coboBox());

        // label for Average
        JLabel avergeLable = new JLabel("Average:");
        avergeLable.setBounds(400, 100, 150, 30);
        avergeLable.setForeground(Color.WHITE);
        mainPanel.add(avergeLable);

        // Filed to show Average
        avgField = new JTextField();
        avgField.setBounds(460, 100, 150, 30);
        mainPanel.add(avgField);
        setVisible(true);

    }

    private void addStudent() {
        
        class HoverButton extends JButton {
    private Color normalColor;
    private Color hoverColor;

    public HoverButton(String text) {
        super(text);
        normalColor = new Color(0x7673ab);
        hoverColor = Color.WHITE;
        setBackground(normalColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        // Add mouse listener for hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setForeground(new Color(0x7673ab));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                setForeground(Color.WHITE);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK); // Border color
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded corners
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 40);
    }
}
        
        
        JFrame studentFrame = new JFrame("Students");
        studentFrame.setSize(600, 400);
        studentFrame.setLayout(null);
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setResizable(false);
        

        DefaultTableModel studentModel = new DefaultTableModel();
        studentModel.addColumn("ID");
        studentModel.addColumn("Name");
        studentModel.addColumn("Age");
        studentModel.addColumn("Gender");
        
        // Add Title
        JLabel TitlePanel = new JLabel(" Add Student");
        TitlePanel.setBounds(200, 10,400, 30);
        TitlePanel.setFont(new Font("Arial", Font.PLAIN, 30)); // Change font size to 20
        TitlePanel.setForeground(new Color(0x7673ab));
        studentFrame.add(TitlePanel);

        JTable studentTable = new JTable(studentModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(45, 55, 500, 150);
        studentFrame.add(scrollPane);

        // Loading Previous Data
        if (!students.isEmpty()) {
            for (Student student : students) {
                studentModel.addRow(new Object[]{student.getStudentId(), student.getName(), student.getAge(), student.getGender()});
            }
        }
        // Add student button
        HoverButton addStudentBtn = new HoverButton("Add Student");
        addStudentBtn.setBounds(50, 220, 150, 30);
        studentFrame.add(addStudentBtn);
        addStudentBtn.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JRadioButton maleButton = new JRadioButton("Male");
            JRadioButton femaleButton = new JRadioButton("Female");
            ButtonGroup genderGroup = new ButtonGroup();
            genderGroup.add(maleButton);
            genderGroup.add(femaleButton);
            Object[] fields = {
                "Student Name:", nameField,
                "Student Age:", ageField,
                " Student Gender:", maleButton, femaleButton

            };
            int result = JOptionPane.showConfirmDialog(null, fields, "Add Student", JOptionPane.OK_CANCEL_OPTION);
            try {
                if (result == JOptionPane.OK_OPTION) {
                    int id = Student.counter;
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String gender = femaleButton.isSelected() ? "Female" : "Male";
                    if (id != 0 && !name.equals("") && age > 15) {
                        Student.counter++;
                        students.add(new Student(id, name, age, gender));
                        studentModel.addRow(new Object[]{id, name, age, gender});
                        JOptionPane.showMessageDialog(studentFrame, "Student added successfully!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(studentFrame, "Please enter valid data.");
            }
            reloadStudentToComboBox();
        });

        // Edit student button
        HoverButton editStudentBtn = new HoverButton("Edit Student");
        editStudentBtn.setBounds(220, 220, 150, 30);
        studentFrame.add(editStudentBtn);
        editStudentBtn.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                JTextField nameField = new JTextField(studentModel.getValueAt(selectedRow, 1).toString());
                JTextField ageField = new JTextField(studentModel.getValueAt(selectedRow, 2).toString());
                //JTextField genderField = new JTextField(studentModel.getValueAt(selectedRow, 3).toString());
                JRadioButton maleButton = new JRadioButton("Male");
                JRadioButton femaleButton = new JRadioButton("Female");
                ButtonGroup genderGroup = new ButtonGroup();
                genderGroup.add(maleButton);
                genderGroup.add(femaleButton);
                Object[] fields = {
                    "Student Name:", nameField,
                    "Student Age:", ageField,
                    " Student Gender:", maleButton, femaleButton
                };
                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
                try {
                    if (result == JOptionPane.OK_OPTION) {
                        String name = nameField.getText();
                        int age = Integer.parseInt(ageField.getText());
                        String gender = maleButton.isSelected() ? "Male" : "Female";
                        if (!name.equals("") && age > 15) {
                            studentModel.setValueAt(name, selectedRow, 1);
                            studentModel.setValueAt(age, selectedRow, 2);
                            studentModel.setValueAt(gender, selectedRow, 3);

                            for (int i = 0; i < students.size(); i++) {
                                if (students.get(i).getStudentId() == Integer.parseInt(studentModel.getValueAt(selectedRow, 0).toString())) {
                                    students.get(i).setName(name);
                                    students.get(i).setAge(age);
                                    students.get(i).setGender(gender);
                                    break;
                                }
                            }

                        }
                        JOptionPane.showMessageDialog(studentFrame, "Student updated successfully!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(studentFrame, "Please enter valid data.");
                }
            } else {
                JOptionPane.showMessageDialog(studentFrame, "Please select a student to edit.");
            }
            reloadStudentToComboBox();
        });

        // Delete student button
        HoverButton deleteStudentBtn = new HoverButton("Delete Student");
        deleteStudentBtn.setBounds(390, 220, 150, 30);
        studentFrame.add(deleteStudentBtn);
        deleteStudentBtn.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                /*for(int i=0 ; i<students.size();i++){
                        if(students.get(i).getStudentId()== Integer.parseInt(studentModel.getValueAt(selectedRow, 0).toString())){
                        students.remove(i);
                        break;
                        }
                }*/
                students.remove(selectedRow);
                studentModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(studentFrame, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(studentFrame, "Please select a student to delete.");
            }
            reloadStudentToComboBox();
        });

        studentFrame.setVisible(true);

    }

    private void addTeacher() {
        class HoverButton extends JButton {
    private Color normalColor;
    private Color hoverColor;

    public HoverButton(String text) {
        super(text);
        normalColor = new Color(0x7673ab);
        hoverColor = Color.WHITE;
        setBackground(normalColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        // Add mouse listener for hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setForeground(new Color(0x7673ab));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                setForeground(Color.WHITE);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK); // Border color
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded corners
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 40);
    }
}
        JFrame teacherFrame = new JFrame("Teachers");
        teacherFrame.setSize(600, 400);
        teacherFrame.setLayout(null);
        teacherFrame.setLocationRelativeTo(null);
        teacherFrame.setResizable(false);
        

        DefaultTableModel teacherModel = new DefaultTableModel();
        teacherModel.addColumn("ID");
        teacherModel.addColumn("Name");
        teacherModel.addColumn("specialty");
        
        // Add Title
        JLabel TitlePanel = new JLabel(" Add Teacher");
        TitlePanel.setBounds(200, 10,400, 30);
        TitlePanel.setFont(new Font("Arial", Font.PLAIN, 30)); // Change font size to 20
        TitlePanel.setForeground(new Color(0x7673ab));
        teacherFrame.add(TitlePanel);

        JTable teacherTable = new JTable(teacherModel);
        JScrollPane scrollPane = new JScrollPane(teacherTable);
        scrollPane.setBounds(45, 55, 500, 150);
        teacherFrame.add(scrollPane);

        // Loading Previous Data
        if (!teachers.isEmpty()) {
            for (Teacher teacher : teachers) {
                teacherModel.addRow(new Object[]{teacher.getTacherId(), teacher.getName(), teacher.getSpecialty()});

            }
        }

        // Add teacher button
        HoverButton addTeacherBtn = new HoverButton("Add Teacher");
        addTeacherBtn.setBounds(50, 220, 150, 30);
        teacherFrame.add(addTeacherBtn);
        addTeacherBtn.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField specialtyField = new JTextField();
            Object[] fields = {
                "Teacher Name:", nameField,
                "Teacher Specailty:", specialtyField
            };
            int result = JOptionPane.showConfirmDialog(null, fields, "Add Teacher", JOptionPane.OK_CANCEL_OPTION);
            try {
                if (result == JOptionPane.OK_OPTION) {
                    int id = Teacher.counter;
                    String name = nameField.getText();
                    String specialty = specialtyField.getText();

                    if (!name.equals("") && !specialty.equals("")) {
                        Teacher.counter++;
                        teachers.add(new Teacher(id, name, specialty));
                        teacherModel.addRow(new Object[]{id, name, specialty});
                        JOptionPane.showMessageDialog(teacherFrame, "Teacher added successfully!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid data.");
            }
        });

        // Edit teacher button
        HoverButton editTeacherBtn = new HoverButton("Edit Teacher");
        editTeacherBtn.setBounds(220, 220, 150, 30);
        teacherFrame.add(editTeacherBtn);
        editTeacherBtn.addActionListener(e -> {
            int selectedRow = teacherTable.getSelectedRow();
            if (selectedRow != -1) {
                JTextField nameField = new JTextField(teacherModel.getValueAt(selectedRow, 1).toString());
                JTextField specialtyField = new JTextField(teacherModel.getValueAt(selectedRow, 2).toString());
                Object[] fields = {
                    "Teacher Name:", nameField,
                    "Teacher specialty:", specialtyField
                };
                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Teacher", JOptionPane.OK_CANCEL_OPTION);
                try {
                    if (result == JOptionPane.OK_OPTION) {
                        String name = nameField.getText();
                        String specialty = specialtyField.getText();

                        if (!name.equals("") && !specialty.equals("")) {
                            teacherModel.setValueAt(name, selectedRow, 1);
                            teacherModel.setValueAt(specialty, selectedRow, 2);
                            JOptionPane.showMessageDialog(teacherFrame, "Teacher updated successfully!");

                            for (int i = 0; i < teachers.size(); i++) {
                                if (teachers.get(i).getTacherId() == Integer.parseInt(teacherModel.getValueAt(selectedRow, 0).toString())) {
                                    teachers.get(i).setName(name);
                                    teachers.get(i).setSpecialty(specialty);
                                    break;
                                }
                            }
                        }

                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid data.");
                }
            } else {
                JOptionPane.showMessageDialog(teacherFrame, "Please select a teacher to edit.");
            }
        });

        // Delete teacher button
        HoverButton deleteTeacherBtn = new HoverButton("Delete Teacher");
        deleteTeacherBtn.setBounds(390, 220, 150, 30);
        teacherFrame.add(deleteTeacherBtn);
        deleteTeacherBtn.addActionListener(e -> {
            int selectedRow = teacherTable.getSelectedRow();
            if (selectedRow != -1) {
                teacherModel.removeRow(selectedRow);
                teachers.remove(selectedRow);
                JOptionPane.showMessageDialog(teacherFrame, "Teacher deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(teacherFrame, "Please select a teacher to delete.");
            }
        });

        teacherFrame.setVisible(true);
    }

    private void addCourse() {
        class HoverButton extends JButton {
    private Color normalColor;
    private Color hoverColor;

    public HoverButton(String text) {
        super(text);
        normalColor = new Color(0x7673ab);
        hoverColor = Color.WHITE;
        setBackground(normalColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        // Add mouse listener for hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setForeground(new Color(0x7673ab));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                setForeground(Color.WHITE);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK); // Border color
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded corners
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 40);
    }
}
        JFrame courseFrame = new JFrame("courses");
        courseFrame.setSize(600, 400);
        courseFrame.setLayout(null);
        courseFrame.setLocationRelativeTo(null);
        courseFrame.setResizable(false);
        
        

        DefaultTableModel courseModel = new DefaultTableModel();
        courseModel.addColumn("ID");
        courseModel.addColumn("Name");
        courseModel.addColumn("teacher");
        
        // Add Title
        JLabel TitlePanel = new JLabel(" Add courses");
        TitlePanel.setBounds(200, 10,400, 30);
        TitlePanel.setFont(new Font("Arial", Font.PLAIN, 30)); // Change font size to 20
        TitlePanel.setForeground(new Color(0x7673ab));
        courseFrame.add(TitlePanel);

        JTable courseTable = new JTable(courseModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBounds(45, 55, 500, 150);
        courseFrame.add(scrollPane);

        // Loading Previous Data
        if (!courses.isEmpty()) {
            for (Course course : courses) {
                courseModel.addRow(new Object[]{course.getCourseId(), course.getName(), course.getTeacher().getName()});
            }
        }

        // Add course button
        HoverButton addCourseBtn = new HoverButton("Add Course");
        addCourseBtn.setBounds(50, 220, 150, 30);
        courseFrame.add(addCourseBtn);
        addCourseBtn.addActionListener(e -> {

            JTextField nameField = new JTextField();
            JComboBox<String> teacherBox = new JComboBox<>();
            for (Teacher teacher : teachers) {
                teacherBox.addItem(teacher.getName());
            }
            Object[] fields = {
                "Course Name:", nameField,
                "Course Teacher:", teacherBox
            };
            int result = JOptionPane.showConfirmDialog(null, fields, "Add Course", JOptionPane.OK_CANCEL_OPTION);
            try {
                if (result == JOptionPane.OK_OPTION) {
                    int id = Course.counter;
                    String name = nameField.getText();
                    String teachername = (String) teacherBox.getSelectedItem();

                    if (!name.equals("") && !teachername.equals("")) {
                        Course.counter++;
                        for (int i = 0; i < teachers.size(); i++) {
                            if (teachers.get(i).getName().equals(teachername)) {
                                Course course = new Course(id, name, teachers.get(i));
                                courses.add(course);
                                teachers.get(i).addCourse(course);
                                courseModel.addRow(new Object[]{id, name, teachers.get(i).getName()});
                                break;
                            }
                        }
                        JOptionPane.showMessageDialog(courseFrame, "Teacher added successfully!");
                    }

                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid data.");
            }
        });

        // Edit course button
        HoverButton editCourseBtn = new HoverButton("Edit Course");
        editCourseBtn.setBounds(220, 220, 150, 30);
        courseFrame.add(editCourseBtn);
        editCourseBtn.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {

                JTextField nameField = new JTextField(courseTable.getValueAt(selectedRow, 1).toString());

                JComboBox<String> teacherBox = new JComboBox<>();
                for (Teacher teacher : teachers) {
                    teacherBox.addItem(teacher.getName());
                }

                teacherBox.setSelectedItem(courseModel.getValueAt(selectedRow, 2));

                Object[] fields = {
                    "Course Name:", nameField,
                    "Course Teacher:", teacherBox
                };
                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String teachername = (String) teacherBox.getSelectedItem();

                    if (!name.equals("") && !teachername.equals("")) {
                        Teacher teacher = null;

                        for (int i = 0; i < teachers.size(); i++) {
                            if (teachers.get(i).getName().equals(teachername)) {
                                teacher = teachers.get(i);
                                break;
                            }
                        }
                        for (int i = 0; i < courses.size(); i++) {
                            if (courses.get(i).getCourseId() == Integer.parseInt(courseModel.getValueAt(selectedRow, 0).toString())) {
                                courses.get(i).setName(name);
                                courses.get(i).setTeacher(teacher);
                                //teacher.addCourse(courses.get(i));

                                break;
                            }
                        }

                        courseTable.setValueAt(name, selectedRow, 1);
                        courseTable.setValueAt(teachername, selectedRow, 2);
                        JOptionPane.showMessageDialog(courseFrame, "course updated successfully!");
                    }

                }
            } else {
                JOptionPane.showMessageDialog(courseFrame, "Please select a teacher to edit.");
            }
        });

        // Delete course button
        HoverButton deleteCourseBtn = new HoverButton("Delete Course");
        deleteCourseBtn.setBounds(390, 220, 150, 30);
        courseFrame.add(deleteCourseBtn);
        deleteCourseBtn.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                courseModel.removeRow(selectedRow);
                courses.remove(selectedRow);
                JOptionPane.showMessageDialog(courseFrame, "Course deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(courseFrame, "Please select a Course to delete.");
            }
        });
        courseFrame.setVisible(true);
    }

    private void addGrade() {
        class HoverButton extends JButton {
    private Color normalColor;
    private Color hoverColor;

    public HoverButton(String text) {
        super(text);
        normalColor = new Color(0x7673ab);
        hoverColor = Color.WHITE;
        setBackground(normalColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        // Add mouse listener for hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                setForeground(new Color(0x7673ab));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                setForeground(Color.WHITE);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK); // Border color
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded corners
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 40);
    }
}
        JFrame gradeFrame = new JFrame("Grades");
        gradeFrame.setSize(600, 400);
        gradeFrame.setLayout(null);
        gradeFrame.setLocationRelativeTo(null);
        gradeFrame.setResizable(false);;
        

        DefaultTableModel gradeModel = new DefaultTableModel();
        gradeModel.addColumn("Student");
        gradeModel.addColumn("Course");
        gradeModel.addColumn("Score");
        
        // Add Title
        JLabel TitlePanel = new JLabel(" Add Grade");
        TitlePanel.setBounds(200, 10,400, 30);
        TitlePanel.setFont(new Font("Arial", Font.PLAIN, 30)); // Change font size to 20
        TitlePanel.setForeground(new Color(0x7673ab));
        gradeFrame.add(TitlePanel);

        JTable gradeTable = new JTable(gradeModel);
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBounds(45, 55, 500, 150);
        gradeFrame.add(scrollPane);

        // Loading Previous Data
        if (!grades.isEmpty()) {
            for (Grade grade : grades) {
                gradeModel.addRow(new Object[]{grade.getStudent().getName(), grade.getCourse().getName(), grade.getScore()});
            }
        }

        // Add Grade button
        HoverButton addGradeBtn = new HoverButton("Add Grade");
        addGradeBtn.setBounds(50, 220, 150, 30);
        gradeFrame.add(addGradeBtn);
        addGradeBtn.addActionListener(e -> {

            JComboBox<String> studentBox = new JComboBox<>();
            for (Student student : students) {
                studentBox.addItem(student.getName());
            }

            JComboBox<String> courseBox = new JComboBox<>();
            for (Course course : courses) {
                courseBox.addItem(course.getName());
            }
            JTextField scoreField = new JTextField();
            Object[] fields = {
                "Student Name:", studentBox,
                "Course:", courseBox,
                "Score:", scoreField
            };
            int result = JOptionPane.showConfirmDialog(null, fields, "Add Grade", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String studentName = (String) studentBox.getSelectedItem();
                String courseName = (String) courseBox.getSelectedItem();
                Double score = Double.valueOf(scoreField.getText());

                Student student = new Student(0, courseName, ABORT, courseName);
                Course course = new Course(NORMAL, courseName, new Teacher(HEIGHT, courseName, studentName));
                int i;

                for (i = 0; i < students.size(); i++) {
                    if (students.get(i).getName().equals(studentName)) {
                        student = students.get(i);
                        break;
                    }
                }
                for (int j = 0; i < courses.size(); j++) {
                    if (courses.get(j).getName().equals(courseName)) {
                        course = courses.get(j);
                        break;
                    }
                }

                grades.add(new Grade(student, course, score));
                students.get(i).addGrade(new Grade(student, course, score));
                gradeModel.addRow(new Object[]{student.getName(), course.getName(), score});
                JOptionPane.showMessageDialog(gradeFrame, "Grade added successfully!");
            }
            reloadStudentToComboBox();
        });

        // Edit Grade button
        HoverButton editGradeBtn = new HoverButton("Edit Grade");
        editGradeBtn.setBounds(220, 220, 150, 30);
        gradeFrame.add(editGradeBtn);
        editGradeBtn.addActionListener(e -> {
            int selectedRow = gradeTable.getSelectedRow();
            if (selectedRow != -1) {

                JComboBox<String> studentBox = new JComboBox<>();
                for (Student student : students) {
                    studentBox.addItem(student.getName());
                }

                JComboBox<String> courseBox = new JComboBox<>();
                for (Course course : courses) {
                    courseBox.addItem(course.getName());
                }
                studentBox.setSelectedItem(gradeModel.getValueAt(selectedRow, 0).toString());
                courseBox.setSelectedItem(gradeModel.getValueAt(selectedRow, 1).toString());
                JTextField scoreField = new JTextField(gradeModel.getValueAt(selectedRow, 2).toString());
                Object[] fields = {
                    "Student Name:", studentBox,
                    "Course:", courseBox,
                    "Score:", scoreField
                };
                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Teacher", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String studentName = (String) studentBox.getSelectedItem();
                    String courseName = (String) courseBox.getSelectedItem();
                    double score = Double.parseDouble(scoreField.getText());

                    grades.get(selectedRow).getStudent().setName(studentName);
                    grades.get(selectedRow).getCourse().setName(courseName);
                    grades.get(selectedRow).setScore(score);

                    gradeModel.setValueAt(studentName, selectedRow, 0);
                    gradeModel.setValueAt(courseName, selectedRow, 1);
                    gradeModel.setValueAt(score, selectedRow, 2);
                    JOptionPane.showMessageDialog(gradeFrame, "Grade updated successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(gradeFrame, "Please select a Grade to edit.");
            }
            reloadStudentToComboBox();
        });

        // Delete Grade button
        HoverButton deleteGradeBtn = new HoverButton("Delete Course");
        deleteGradeBtn.setBounds(390, 220, 150, 30);
        gradeFrame.add(deleteGradeBtn);
        deleteGradeBtn.addActionListener(e -> {
            int selectedRow = gradeTable.getSelectedRow();
            if (selectedRow != -1) {
                gradeModel.removeRow(selectedRow);
                grades.remove(selectedRow);
                JOptionPane.showMessageDialog(gradeFrame, "Course deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(gradeFrame, "Please select a Course to delete.");
            }
            reloadStudentToComboBox();
        });

        gradeFrame.setVisible(true);

    }

    private void coboBox() {
        //reloadStudentToComboBox();
        String studentName =  stdBox.getSelectedItem().toString();
        JOptionPane.showMessageDialog(null, studentName);
        load(studentName);
        avgField.setText("" + Average);
        saveToFile("C:\\Users\\Mohammed Galal\\Desktop\\Java_project\\new.txt");
    }
   
    

    public static void main(String[] args) {
        new StudentManagementSystem();
    }


    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    private void addStudent(Student student) {
        students.add(student);
        reloadStudentToComboBox();
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }
    
    public Teacher getTeacherByName(String name) {
        for (Teacher teacher : teachers) {
            if (teacher.getName().equals(name)) {
                return teacher;
            }
        }
        return null; // Return null if not found
    }

    public Student getStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null; // Return null if not found
    }

    public Course getCourseByName(String name) {
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null; // Return null if not found
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.ExamModel;
import Object.Exam;
import Object.Exams;
import Object.Subjects;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author SinhVienBK
 */
public class InformationExams extends JDialog{
    
    private JButton btnOK;
    private DefaultTableModel dtm;
    private JTable tblExams;
    
    public InformationExams()
    {
        this.setTitle("Thống kê đề thi");
        this.setModal(true);
        addControls();
        addEvents();
        showWindow();
    }
    
    public void addControls()
    {
        btnOK = new JButton("OK");
        Container con = getContentPane();
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BorderLayout());
        
        dtm = new DefaultTableModel();
        dtm.addColumn("STT");
        dtm.addColumn("Môn học");
        dtm.addColumn("Số đề thi");
        
        HashMap<String, Integer> nameAndNumber = new HashMap<String, Integer>();
        Subjects subjects = FunctionViewing.subjects;
        for (int i = 0; i < subjects.getSubjects().size(); i++)
        {
            String str = subjects.getSubject(i).getNameSubject();
            nameAndNumber.put(str, 0);
        }
        Exams exams = ExamModel.readExam();
        for (Exam ex : exams.getExams())
        {
            String nameSubject = ex.getNameSubject();
            if (nameAndNumber.get(nameSubject) != null)
            {
                int num = nameAndNumber.get(nameSubject) + 1;
                nameAndNumber.replace(nameSubject, num);
            } 
        }
        int i = 1;
        for (Map.Entry<String, Integer> e : nameAndNumber.entrySet()) {
                dtm.addRow(new Object[]{i + "", e.getKey(), e.getValue()}); 
                i++;
        } 
        
        tblExams = new JTable(dtm);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblExams.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblExams.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblExams.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        ((DefaultTableCellRenderer)tblExams.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel colMdl = tblExams.getColumnModel();
        colMdl.getColumn(0).setPreferredWidth(50);
        colMdl.getColumn(1).setPreferredWidth(200);
        
        JScrollPane sc = new JScrollPane(
				tblExams,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnMain.add(sc, BorderLayout.CENTER);
        con.add(pnMain);
        
    }
    
    public void addEvents()
    {
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                processOut();
            }
        });
    }
    
    private void processOut()
    {
        this.dispose();
    }
    
    public void showWindow()
    {
        this.setSize(400, 400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

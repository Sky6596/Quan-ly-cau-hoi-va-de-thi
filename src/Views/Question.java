/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ImportController;
import Controllers.QuestionController;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

/**
 *
 * @author Duc Dung Dan
 */
public class Question extends javax.swing.JPanel {

    /**
     * Creates new form Question
     */
    public Question() {
        initComponents();
    }
    
    public JPanel getJPanelQuestion() {
        return background;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        edit = new javax.swing.JMenuItem();
        delete = new javax.swing.JMenuItem();
        background = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableQuestion = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        typeSearch = new javax.swing.JComboBox();
        inputSearch = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        scrollPane1 = new java.awt.ScrollPane();
        question = new javax.swing.JPanel();
        importButton = new javax.swing.JButton();

        edit.setText("Chỉnh sửa");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        jPopupMenu1.add(edit);

        delete.setText("Xóa");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPopupMenu1.add(delete);

        background.setBackground(new java.awt.Color(233, 235, 238));
        background.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                backgroundAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(233, 235, 238));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tableQuestion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nội dung", "Chương", "Môn", "Độ khó", "Hình thức"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableQuestion.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableQuestion.setGridColor(new java.awt.Color(233, 235, 238));
        tableQuestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableQuestionMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableQuestionMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableQuestion);
        if (tableQuestion.getColumnModel().getColumnCount() > 0) {
            tableQuestion.getColumnModel().getColumn(0).setResizable(false);
            tableQuestion.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableQuestion.getColumnModel().getColumn(1).setResizable(false);
            tableQuestion.getColumnModel().getColumn(1).setPreferredWidth(350);
            tableQuestion.getColumnModel().getColumn(2).setResizable(false);
            tableQuestion.getColumnModel().getColumn(2).setPreferredWidth(135);
            tableQuestion.getColumnModel().getColumn(3).setResizable(false);
            tableQuestion.getColumnModel().getColumn(3).setPreferredWidth(80);
            tableQuestion.getColumnModel().getColumn(4).setResizable(false);
            tableQuestion.getColumnModel().getColumn(4).setPreferredWidth(70);
            tableQuestion.getColumnModel().getColumn(5).setResizable(false);
            tableQuestion.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        jButton1.setText("New");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        typeSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nội dung", "Chương", "Môn", "Độ khó" }));

        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Search.png"))); // NOI18N
        search.setContentAreaFilled(false);
        search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        scrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        question.setBackground(new java.awt.Color(233, 235, 238));
        question.setPreferredSize(new java.awt.Dimension(450, 446));

        javax.swing.GroupLayout questionLayout = new javax.swing.GroupLayout(question);
        question.setLayout(questionLayout);
        questionLayout.setHorizontalGroup(
            questionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );
        questionLayout.setVerticalGroup(
            questionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 524, Short.MAX_VALUE)
        );

        scrollPane1.add(question);

        importButton.setText("Import");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(importButton)
                        .addGap(355, 355, 355)
                        .addComponent(typeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(typeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(importButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backgroundAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_backgroundAncestorAdded
    }//GEN-LAST:event_backgroundAncestorAdded

    private void tableQuestionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableQuestionMouseReleased
        DefaultTableModel model = (DefaultTableModel) tableQuestion.getModel();
        int selectRow = tableQuestion.getSelectedRow();
        if (selectRow != -1 && evt.isPopupTrigger()) {
            delete.setVisible(true);
            edit.setVisible(true);
            jPopupMenu1.show(tableQuestion, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tableQuestionMouseReleased

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        //QuestionController.searchTableQuestion(tableQuestion, pageQuestion, inputSearch.getText() == null?"":inputSearch.getText(), typeSearch.getSelectedItem().toString());
    }//GEN-LAST:event_searchActionPerformed

    private void tableQuestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableQuestionMouseClicked
        QuestionController.viewQuestion(tableQuestion, question);
    }//GEN-LAST:event_tableQuestionMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        QuestionController.deleteQuestion(tableQuestion);
    }//GEN-LAST:event_deleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        QuestionController.viewNewQuestion();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        QuestionController.viewEditQuestion(tableQuestion);
    }//GEN-LAST:event_editActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        ImportController.showImportPath();
    }//GEN-LAST:event_importButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel background;
    public javax.swing.JMenuItem delete;
    public javax.swing.JMenuItem edit;
    public javax.swing.JButton importButton;
    public javax.swing.JTextField inputSearch;
    public javax.swing.JButton jButton1;
    public javax.swing.JPopupMenu jPopupMenu1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JPanel question;
    public java.awt.ScrollPane scrollPane1;
    public javax.swing.JButton search;
    public javax.swing.JTable tableQuestion;
    public javax.swing.JComboBox typeSearch;
    // End of variables declaration//GEN-END:variables
}

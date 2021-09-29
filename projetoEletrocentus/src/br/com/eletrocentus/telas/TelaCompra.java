/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eletrocentus.telas;
import java.sql.*;
import br.com.eletrocentus.dal.ModuloConexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author usuario
 */
public class TelaCompra extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaCompra
     */
    public TelaCompra() {
        initComponents();
        conexao = ModuloConexao.conector();
        System.out.println(conexao);
    }
    
    // Cria/ adiciona uma compra
    private void criar() {
        String sql = "INSERT INTO tbCompra(Cpf_Cnpj_Cliente_Compra, IdAparelhoCompra, DataCompra, Observacao) values(?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCompraCpfCnpjCliente.getText());
            pst.setString(2, txtCompraIdAparelho.getText());
            pst.setString(3, txtCompraDataCompra.getText());
            pst.setString(4, txtCompraObservacao.getText());
            
            // Validação dos campos obrigatórios
            if ((txtCompraCpfCnpjCliente.getText().isEmpty()) || (txtCompraIdAparelho.getText().isEmpty()) || (txtCompraDataCompra.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {

                // abaixo, atualiza o banco de dados e confirma a inserção de dados no mesmo
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Compra registrada com sucesso!");
                    txtCompraCpfCnpjCliente.setText(null);
                    txtCompraIdAparelho.setText(null);
                    txtCompraDataCompra.setText(null);
                    txtCompraObservacao.setText(null);
                    txtCompraNomeAparelho.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
   // Pesquisa aparelho mediante digitação do usuário
    private void pesquisa_aparelho() {
        String sql = "SELECT IdAparelho, NomeAparelho, Marca FROM tbAparelho WHERE NomeAparelho like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCompraNomeAparelho.getText() + "%");
            rs = pst.executeQuery();

            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblCompraPesquisaAparelho.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //seta campo IdAparelho enquanto atrelado a escolha na tabela de aparelho
    private void seta_idaparelho() {
        int setar = tblCompraPesquisaAparelho.getSelectedRow();
        txtCompraIdAparelho.setText(tblCompraPesquisaAparelho.getModel().getValueAt(setar, 0).toString());      
    }
    
    
    private void pesquisa_todas_compras() {
        String sql = "SELECT IdCompra, NomeCliente, NomeAparelho, DataCompra FROM ((tbCompra INNER JOIN tbCliente ON Cpf_Cnpj_Cliente_Compra = Cpf_Cnpj_Cliente)INNER JOIN tbAparelho ON IdAparelhoCompra = IdAparelho) order by NomeCliente ASC";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblPesquisaCompra.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void seta_idcompra() {
        int setar = tblPesquisaCompra.getSelectedRow();
        txtCompraIdCompra.setText(tblPesquisaCompra.getModel().getValueAt(setar, 0).toString());      
    }
    
    private void deletar(){
        // Abaixo pergunta ao usuário se deseja remover
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a Compra?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "DELETE FROM tbCompra WHERE IdCompra = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCompraIdCompra.getText());
                int deletado = pst.executeUpdate();
                if (deletado> 0){
                    JOptionPane.showMessageDialog(null, "Compra apagada com sucesso!");
                    txtCompraIdCompra.setText(null);
                    
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        txtCompraCpfCnpjCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCompraNomeAparelho = new javax.swing.JTextField();
        txtCompraIdAparelho = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnCompraAdicionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCompraPesquisaAparelho = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCompraDataCompra = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPesquisaCompra = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtCompraIdCompra = new javax.swing.JTextField();
        txtCompraObservacao = new javax.swing.JTextField();

        jLabel4.setText("jLabel4");

        jLabel5.setText("jLabel5");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(631, 619));

        txtCompraCpfCnpjCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompraCpfCnpjClienteKeyReleased(evt);
            }
        });

        jLabel1.setText("* Cpf do Cliente:");

        jLabel2.setText("Cadastro de Compras");

        jLabel3.setText("* Nome do aparelho: ");

        txtCompraNomeAparelho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompraNomeAparelhoKeyReleased(evt);
            }
        });

        txtCompraIdAparelho.setEnabled(false);

        jLabel7.setText("Id Aparelho:");

        btnCompraAdicionar.setText("Adicionar");
        btnCompraAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompraAdicionarActionPerformed(evt);
            }
        });

        tblCompraPesquisaAparelho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCompraPesquisaAparelho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCompraPesquisaAparelhoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCompraPesquisaAparelho);

        jLabel8.setText("Filtrar compras por:");

        jLabel9.setText("* Data/ Compra: ");

        txtCompraDataCompra.setText("ano/mês/dia");

        jLabel10.setText("Observação");

        jRadioButton1.setText("Todas as compras");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        tblPesquisaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPesquisaCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesquisaCompraMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPesquisaCompra);

        jButton2.setText("Deletar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel12.setText("Id Compra: ");

        txtCompraIdCompra.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(27, 27, 27)
                                    .addComponent(txtCompraCpfCnpjCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCompraNomeAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(276, 276, 276))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnCompraAdicionar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtCompraIdAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCompraDataCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                                            .addComponent(txtCompraObservacao))))
                                .addGap(0, 43, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(59, 59, 59)
                                    .addComponent(jLabel2))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jRadioButton1)
                                    .addGap(174, 174, 174)
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCompraIdCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCompraCpfCnpjCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCompraNomeAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtCompraIdAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtCompraDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtCompraObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCompraAdicionar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCompraIdCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton2)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        setBounds(0, 0, 631, 619);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompraAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompraAdicionarActionPerformed
        // Chama o método criar
        criar();
    }//GEN-LAST:event_btnCompraAdicionarActionPerformed

    private void txtCompraNomeAparelhoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompraNomeAparelhoKeyReleased
        // Chama o método pesquisa_aparelho
        pesquisa_aparelho();
    }//GEN-LAST:event_txtCompraNomeAparelhoKeyReleased

    private void tblCompraPesquisaAparelhoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCompraPesquisaAparelhoMouseClicked
        seta_idaparelho();
    }//GEN-LAST:event_tblCompraPesquisaAparelhoMouseClicked

    private void txtCompraCpfCnpjClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompraCpfCnpjClienteKeyReleased
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtCompraCpfCnpjClienteKeyReleased

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        pesquisa_todas_compras();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void tblPesquisaCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesquisaCompraMouseClicked
        // TODO add your handling code here:
        seta_idcompra();
    }//GEN-LAST:event_tblPesquisaCompraMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompraAdicionar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblCompraPesquisaAparelho;
    private javax.swing.JTable tblPesquisaCompra;
    private javax.swing.JTextField txtCompraCpfCnpjCliente;
    private javax.swing.JTextField txtCompraDataCompra;
    private javax.swing.JTextField txtCompraIdAparelho;
    private javax.swing.JTextField txtCompraIdCompra;
    private javax.swing.JTextField txtCompraNomeAparelho;
    private javax.swing.JTextField txtCompraObservacao;
    // End of variables declaration//GEN-END:variables
}

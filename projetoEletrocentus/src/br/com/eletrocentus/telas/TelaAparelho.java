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
public class TelaAparelho extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaAparelho
     */
    public TelaAparelho() {
        initComponents();
        conexao = ModuloConexao.conector();
        System.out.println(conexao);
    }
    
    //Método para fazer pesquisa de aparelhos e setar a tabela
    private void pesquisar_aparelho() {
        String sql = "SELECT *FROM tbAparelho WHERE NomeAparelho like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAparelhoPesquisaAparelho.getText() + "%");
            rs = pst.executeQuery();

            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblAparelhoPesquisa.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //método para preencher os campos do formulário com o conteúdo da tabela
    public void setar_campos() {
        int setar = tblAparelhoPesquisa.getSelectedRow();
        txtAparelhoIdAparelho.setText(tblAparelhoPesquisa.getModel().getValueAt(setar, 0).toString());
        txtAparelhoNomeAparelho.setText(tblAparelhoPesquisa.getModel().getValueAt(setar, 1).toString());
        txtAparelhoNumeroSerie.setText(tblAparelhoPesquisa.getModel().getValueAt(setar, 2).toString());
        txtAparelhoMarca.setText(tblAparelhoPesquisa.getModel().getValueAt(setar, 3).toString());
        txtAparelhoModelo.setText(tblAparelhoPesquisa.getModel().getValueAt(setar, 4).toString());
        txtAparelhoPreco.setText(tblAparelhoPesquisa.getModel().getValueAt(setar, 5).toString());
        
        // A linha abaixo desabilita o botão de adicionar
           btnAparelhoAdicionar.setEnabled(false);
    }

    private void criar() {
        String sql = "INSERT INTO tbAparelho(NumeroSerie, NomeAparelho, Marca, Modelo, Preco) values(?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAparelhoNumeroSerie.getText());
            pst.setString(2, txtAparelhoNomeAparelho.getText());
            pst.setString(3, txtAparelhoMarca.getText());
            pst.setString(4, txtAparelhoModelo.getText());
            pst.setString(5, txtAparelhoPreco.getText());
            // Validação dos campos obrigatórios
            if ((txtAparelhoNumeroSerie.getText().isEmpty()) || (txtAparelhoNomeAparelho.getText().isEmpty()) || (txtAparelhoMarca.getText().isEmpty()) || (txtAparelhoModelo.getText().isEmpty()) || (txtAparelhoPreco.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {

                // abaixo, atualiza o banco de dados e confirma a inserção de dados no mesmo
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Aparelho adicionado com sucesso");
                    txtAparelhoNomeAparelho.setText(null);
                    txtAparelhoNumeroSerie.setText(null);
                    txtAparelhoMarca.setText(null);
                    txtAparelhoModelo.setText(null);
                    txtAparelhoPreco.setText(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // método para alterar a tabela de aparelhos
    private void atualizar() {
        String sql = "UPDATE tbAparelho SET NomeAparelho = ?, NumeroSerie = ?, Marca= ?, Modelo = ?, Preco = ? WHERE IdAparelho = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAparelhoNomeAparelho.getText());
            pst.setString(2, txtAparelhoNumeroSerie.getText());
            pst.setString(3, txtAparelhoMarca.getText());
            pst.setString(4, txtAparelhoModelo.getText());
            pst.setString(5, txtAparelhoPreco.getText());
            pst.setString(6, txtAparelhoIdAparelho.getText());
            // Validação dos campos obrigatórios
            if ((txtAparelhoNumeroSerie.getText().isEmpty()) || (txtAparelhoNomeAparelho.getText().isEmpty()) || (txtAparelhoMarca.getText().isEmpty()) || (txtAparelhoModelo.getText().isEmpty()) || (txtAparelhoPreco.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else {

                // abaixo, atualiza o banco de dados e confirma a inserção de dados no mesmo
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Aparelho atualizados com sucesso");
                    txtAparelhoNumeroSerie.setText(null);
                    txtAparelhoNomeAparelho.setText(null);
                    txtAparelhoMarca.setText(null);
                    txtAparelhoModelo.setText(null);
                    txtAparelhoPreco.setText(null);
                    txtAparelhoIdAparelho.setText(null);
                    btnAparelhoAdicionar.setEnabled(true);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void deletar() {
        // Abaixo pergunta ao usuário se deseja remover
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar o aparelho?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tbAparelho WHERE IdAparelho = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtAparelhoIdAparelho.getText());
                int deletado = pst.executeUpdate();
                if (deletado > 0) {
                    JOptionPane.showMessageDialog(null, "Aparelho deletado com sucesso!");
                    txtAparelhoNumeroSerie.setText(null);
                    txtAparelhoNomeAparelho.setText(null);
                    txtAparelhoMarca.setText(null);
                    txtAparelhoModelo.setText(null);
                    txtAparelhoPreco.setText(null);
                    txtAparelhoIdAparelho.setText(null);
                    btnAparelhoAdicionar.setEnabled(true);

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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAparelhoNumeroSerie = new javax.swing.JTextField();
        txtAparelhoNomeAparelho = new javax.swing.JTextField();
        txtAparelhoMarca = new javax.swing.JTextField();
        txtAparelhoModelo = new javax.swing.JTextField();
        txtAparelhoPreco = new javax.swing.JTextField();
        btnAparelhoAdicionar = new javax.swing.JButton();
        btnAparelhoAtualizar = new javax.swing.JButton();
        btnAparelhoDeletar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAparelhoPesquisa = new javax.swing.JTable();
        txtAparelhoPesquisaAparelho = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAparelhoIdAparelho = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Aparelhos");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(631, 619));

        jLabel1.setText("* Número de Série:");

        jLabel2.setText("* Nome do Aparelho:");

        jLabel3.setText("* Marca:");

        jLabel4.setText("* Modelo:");

        jLabel5.setText("* Preço: ");

        btnAparelhoAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/eletrocentus/icones/create.png"))); // NOI18N
        btnAparelhoAdicionar.setToolTipText("Adicionar");
        btnAparelhoAdicionar.setPreferredSize(new java.awt.Dimension(70, 70));
        btnAparelhoAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAparelhoAdicionarActionPerformed(evt);
            }
        });

        btnAparelhoAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/eletrocentus/icones/update.png"))); // NOI18N
        btnAparelhoAtualizar.setToolTipText("Atualizar");
        btnAparelhoAtualizar.setPreferredSize(new java.awt.Dimension(70, 70));
        btnAparelhoAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAparelhoAtualizarActionPerformed(evt);
            }
        });

        btnAparelhoDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/eletrocentus/icones/delete.png"))); // NOI18N
        btnAparelhoDeletar.setToolTipText("Deletar");
        btnAparelhoDeletar.setPreferredSize(new java.awt.Dimension(70, 70));
        btnAparelhoDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAparelhoDeletarActionPerformed(evt);
            }
        });

        jLabel7.setText("* Campos obrigatórios");

        tblAparelhoPesquisa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAparelhoPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAparelhoPesquisaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAparelhoPesquisa);

        txtAparelhoPesquisaAparelho.setToolTipText("");
        txtAparelhoPesquisaAparelho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAparelhoPesquisaAparelhoKeyReleased(evt);
            }
        });

        jLabel6.setText("Id: ");

        txtAparelhoIdAparelho.setEnabled(false);

        jLabel8.setText("Nome do Aparelho");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAparelhoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAparelhoNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtAparelhoPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtAparelhoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1))
                        .addGap(562, 562, 562))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtAparelhoPesquisaAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAparelhoNomeAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(321, 321, 321)
                                .addComponent(btnAparelhoAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAparelhoAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAparelhoDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtAparelhoIdAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAparelhoAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAparelhoAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAparelhoDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(txtAparelhoPesquisaAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAparelhoIdAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAparelhoNomeAparelho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAparelhoNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtAparelhoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAparelhoPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtAparelhoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        setBounds(0, 0, 631, 619);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAparelhoAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAparelhoAdicionarActionPerformed
        // Chama o método Criar
        criar();
    }//GEN-LAST:event_btnAparelhoAdicionarActionPerformed

    private void btnAparelhoAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAparelhoAtualizarActionPerformed
        // Chama o método Atualizar
        atualizar();
    }//GEN-LAST:event_btnAparelhoAtualizarActionPerformed

    private void btnAparelhoDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAparelhoDeletarActionPerformed
        // Chama o método deletar
        deletar();
    }//GEN-LAST:event_btnAparelhoDeletarActionPerformed

    private void txtAparelhoPesquisaAparelhoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAparelhoPesquisaAparelhoKeyReleased
        // Chama o método pesquisar cliente
        pesquisar_aparelho();
    }//GEN-LAST:event_txtAparelhoPesquisaAparelhoKeyReleased

    private void tblAparelhoPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAparelhoPesquisaMouseClicked
        // Chama o método setar campos
        setar_campos();
    }//GEN-LAST:event_tblAparelhoPesquisaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAparelhoAdicionar;
    private javax.swing.JButton btnAparelhoAtualizar;
    private javax.swing.JButton btnAparelhoDeletar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAparelhoPesquisa;
    private javax.swing.JTextField txtAparelhoIdAparelho;
    private javax.swing.JTextField txtAparelhoMarca;
    private javax.swing.JTextField txtAparelhoModelo;
    private javax.swing.JTextField txtAparelhoNomeAparelho;
    private javax.swing.JTextField txtAparelhoNumeroSerie;
    private javax.swing.JTextField txtAparelhoPesquisaAparelho;
    private javax.swing.JTextField txtAparelhoPreco;
    // End of variables declaration//GEN-END:variables
}

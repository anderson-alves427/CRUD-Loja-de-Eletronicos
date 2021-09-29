/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eletrocentus.dal;

import java.sql.*;
/**
 *
 * @author usuario
 */
public class ModuloConexao {
    // Método que faz a conexão com o banco de dados
    public static Connection conector(){
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dbeletrocentusx";
        String user = "root";
        String password = ""; // aqui é digitado a senha que foi escolhida pro db localhost
        
        //Realizando a conexão com o banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            return null;
        }
    }
}

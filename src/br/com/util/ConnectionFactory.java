package br.com.util;

import java.sql.DriverManager;

import javax.swing.JOptionPane;

import java.sql.Connection;

public class ConnectionFactory {
	public static Connection getConnection() throws Exception{
		try {
			//indica o db é mysql apontado para o drive
			Class.forName("com.mysql.jdbc.Driver");
			//conexão com DB
			String login = "root";
			String senha = "root";
			String url = "jdbc:mysql://localhost:3306/dbleitor";
			return DriverManager.getConnection(url, login, senha);
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws Exception {
		try {
			Connection conn = ConnectionFactory.getConnection();
			JOptionPane.showMessageDialog(null, "CONECTADO");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO AO CONECTAR");
			throw new Exception(e.getMessage());
		}

	}
	
}

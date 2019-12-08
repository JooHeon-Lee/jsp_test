package DBBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class LoginDBBean {
	private static LoginDBBean instance = new LoginDBBean();
	
	public static LoginDBBean getInstance(){
		return instance;
	}
	
	private LoginDBBean() {}
	
	private Connection getConnection() throws Exception {
	
		
		
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/test");
		
		return ds.getConnection();	
	}
	
	public int idcheck(String id, String pass) {
		int check = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			// 1�ܰ� ����̹� �ε�
			// 2�ܰ� db���� => Connection con ��ü ����
			con = getConnection();

			// 3�ܰ� sql id�� �ش��ϴ� pass ��������
			String sql = "select pwd, isman from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			// 4�ܰ� rs=������ ����
			rs = pstmt.executeQuery();

			// 5�ܰ� rsù���̵� ������������"���̵�����"
			// ��й�ȣ���� check=1
			// Ʋ���� check=0;
			// ������ ���̵���� check=-1
			if (rs.next()) {
				// ���̵�����
				if (pass.equals(rs.getString("pwd"))) {
					// �α��� ����
					if(rs.getString("isman").equals("o")) {
						return 2;
					}
					if(rs.getString("isman").equals("x")) {
						return 1;
					}	
				} else {
					// ��й�ȣ Ʋ��
					check = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ��ü�ݱ�
			// ��ü���� �ݱ�(�����Ҹ� ȸ���ϴ� �۾�)
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}

		}
		return check;
	}// ���̵��й�ȣüũ�޼��峡

}


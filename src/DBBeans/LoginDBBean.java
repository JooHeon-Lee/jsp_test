package DBBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


import Beans.memberBean;

public class LoginDBBean {
	private static LoginDBBean instance = new LoginDBBean();
	
	public static LoginDBBean getInstance(){
		return instance;
	}
	
	private LoginDBBean() {}
	
	private Connection getConnection() throws Exception {
	
		
		
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/jsptest");
		
		return ds.getConnection();	
	}
	public void insertMember(memberBean member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String pass = member.getPasswd();
			
			pstmt = conn.prepareStatement("insert into member values()");
		}catch(Exception e){
			e.printStackTrace();
			
		}
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
			String sql = "select pass from member where id=?";
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
				if (pass.equals(rs.getString("pass"))) {
					// �α��� ����
					check = 1;
					System.out.println("daf");
				} else {
					// ��й�ȣ Ʋ��
					System.out.println("11");
					check = 0;
				}
			} else {
				System.out.println("f");
				// ���̵����.
				// check=-1; //�ʱ� ������ �����Ƿ� ��������.
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
	
	
	//���̵� �ش��ϴ� ȸ������ ����
	public memberBean getMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		memberBean member = null;
		
		try {
			conn = getConnection();
			
			pstmt=conn.prepareStatement("select * from member where id= ?");
			pstmt.setString(1, id);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				member = new memberBean();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
				member.setHp(rs.getInt("hp"));
				member.setIsman(rs.getString("isman"));
				member.setScial_num(rs.getInt("scial_num"));

			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			  if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return member;//������ ����� ��ü member ����
	}
	
	//ȸ������ ���� ó��
	
	public int updateMember(memberBean member) {
		 Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs= null;
	        int x=-1;
	        try {
	        	conn = getConnection();
	        	
	        	String pass = member.getPasswd();
	        	
	        	pstmt = conn.prepareStatement("select pwd from member where id = ?");
	        	pstmt.setString(1, member.getId());
	        	rs=pstmt.executeQuery();
	        	
	        	if(rs.next()) {
	        		String dbpasswd = rs.getString("pwd");
	        		if(pass.equals(dbpasswd)) {
	        			pstmt = conn.prepareStatement("update member set pwd = ?,address=?,hp=? where id=?");
	        			 pstmt.setString(1, member.getPasswd());
	                     pstmt.setString(2, member.getAddress());
	                     pstmt.setInt(3, member.getHp());
	                     pstmt.setString(4, member.getId());
	                     pstmt.executeUpdate();
	                     x= 1;//ȸ������ ���� ó�� ����
	        		}else
						x= 0;//ȸ������ ���� ó�� ����
	        	}
	        	}catch(Exception ex) {
	                ex.printStackTrace();
	            } finally {
	            	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	                if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	                if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	            }
	            return x;
	        
	}
	
	
	
	
	
	
}


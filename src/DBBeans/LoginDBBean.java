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

import Beans.LoginBean;

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
				} else {
					// ��й�ȣ Ʋ��
					check = 0;
				}
			} else {
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

	
	// ---------------------------------------
	public MemberBean getMember(String a) {
		MemberBean mb = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1�ܰ� ����̹� �ε�
			// 2�ܰ� db���� => Connection con ��ü ����
			con = getConnection();

			// 3�ܰ� sql id�� �ش��ϴ� pass ��������
			String sql = "select * from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, a);

			// 4�ܰ� rs=������ ����
			rs = pstmt.executeQuery();

			if (rs.next()) {
				mb = new LoginBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				mb.setAge(rs.getInt("age"));
				mb.setGender(rs.getString("gender"));
				mb.setEmail(rs.getString("email"));

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
		return mb;
	}// getmember

	// --------------------------------------------

	public int updateMember(MemberBean mb) {
		// check==1�̸� ��������. main.jsp�� �̵�.
		// check==0�̸� ��й�ȣ Ʋ��. �ڷ� �̵�.
		// check==-1�̸� ���̵����. �ڷ� �̵�.
		int check = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			// 1�ܰ� ����̹� �ε�
			// 2�ܰ� db���� => Connection con ��ü ����
			con = getConnection();

			// 3�ܰ� sql id�� �ش��ϴ� pass ��������

			// 4�ܰ� rs=������ ����

			// 5�ܰ� rs ù������ �̵� ������ ������ "���̵�����"
			// ��й�ȣ �� ������ CHECK=1
			// //3 sql ���� id �ش��ϴ� name, age, gender, email ����
			// 4 ����

			String sql = "select pass from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getId());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (!mb.getPass().equals("")) {
					// ��й�ȣ null check �ڵ尡 �������.
					// ����Ʈ���� null���� �������ִ� ���� ����.
					// System.out.println("1" + mb.getPass());
					// System.out.println("2" +
					// rs.getString("pass"));//rs.next()�� ������ ���� �ȳ���.
					if (mb.getPass().equals(rs.getString("pass"))) {
						sql = "update member set name=?,age=?,gender=?,email=? where id=?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, mb.getName());
						pstmt.setInt(2, mb.getAge());
						pstmt.setString(3, mb.getGender());
						pstmt.setString(4, mb.getEmail());
						pstmt.setString(5, mb.getId());

						pstmt.executeUpdate();

						check = 1;
					}
				} else {
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
	}// updatemember

	public int deleteMember(String id, String pass) {
		int check = 9;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LoginBean mb = new LoginBean();

		try {

			con = getConnection();
			String sql = "select pass from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (pass.equals(rs.getString("pass"))) {
					sql = "delete from member where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					check = 1;
				} else {
					// ��й�ȣ ����
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
	}// deletemember

	public List getMemberList() {
		// �迭(�÷���) ��ü ���� - �������� ������ ���+�������߰��ؼ� ���
		List memberList = new ArrayList();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			String sql = "select * from member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// �迭��ü ����

			while (rs.next()) {
				LoginBean mb = new LoginBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setReg_date(rs.getTimestamp("reg_date"));
				mb.setAge(rs.getInt("age"));
				mb.setGender(rs.getString("gender"));
				mb.setEmail(rs.getString("email"));
				memberList.add(mb);

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
		return memberList;
	}// getmemberlist

}


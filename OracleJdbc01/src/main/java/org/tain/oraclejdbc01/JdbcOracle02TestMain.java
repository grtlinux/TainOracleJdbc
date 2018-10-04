package org.tain.oraclejdbc01;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

public class JdbcOracle02TestMain {

	private static boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	private static String getClassInfo() {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];

		StringBuffer sb = new StringBuffer();

		if (flag) sb.append(e.getClassName()).append('.').append(e.getMethodName()).append("()");
		if (flag) sb.append(" - ");
		if (flag) sb.append(e.getFileName()).append('(').append(e.getLineNumber()).append(')');

		return sb.toString();
	}

	///////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		if (flag) select01(args);    // select with no param
		if (flag) select02(args);    // select with params
		if (flag) procedure01(args); // package procedure with in/out params
		if (flag) procedure02(args); // package procedure with in/out params
		if (flag) call01(args);      // use call execution
		if (flag) beginEnd01(args);  // use begin-end command, and Meta data
		if (flag) insert01(args);    // insert
	}

	private static void select01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
		conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
		conn.setAutoCommit(false);

		try {
			String sql = "select USER_ID, USER_NAME, PASSWORD from TB_ORG_USER where ACTIVE_FLAG = 1";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String userId = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				String password = rs.getString("PASSWORD");
				System.out.printf("[%-20.20s] [%s] [%s]%n", userId, userName, password);
			}
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void select02(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
		conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
		conn.setAutoCommit(false);

		try {
			String sql = "select * from TB_ORG_USER where USER_ID like '%' || ? || '%'";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "admin");
			rs = pstmt.executeQuery();
			while(rs.next()){
				String userId = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				String password = rs.getString("PASSWORD");
				System.out.printf("[%-20.20s] [%s] [%s]%n", userId, userName, password);
			}
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	//////////////////////////////////////////////////////////////////////
	/*
	 *  // SQL syntax
	 *  CallableStatement cs1 = conn.prepareCall("{ call proc(?, ?) }");  // stored procedure
	 *  CallableStatement cs1 = conn.prepareCall("{ ? = call func(?, ?) }");  // stored function
	 *  CallableStatement cs1 = conn.prepareCall("{ begin proc(?, ?); end; }");  // stored procedure
	 *  CallableStatement cs1 = conn.prepareCall("{ begin ? := func(?, ?); end; }");  // stored function
	 */
	private static void procedure01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		CallableStatement cstmt = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
		conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
		conn.setAutoCommit(false);

		try {
			String userid = "admin";
			String sql = "{ ? = call kang_action_pkg.cnt_org_user(?) }";

			cstmt = conn.prepareCall(sql);

			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, userid);
			cstmt.execute();

			int count = cstmt.getInt(1);

			System.out.printf("[userid:%s] [count:%d]%n", userid, count);
		} finally {
			if (cstmt != null) try { cstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void procedure02(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
		conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
		conn.setAutoCommit(false);

		try {
			String sql = "{? = call kang_var_pkg.get_user_info(?) }";

			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, "admin");
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			while(rs.next()){
				String userId = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				String password = rs.getString("PASSWORD");
				System.out.printf("[%-20.20s] [%s] [%s]%n", userId, userName, password);
			}
		} finally {
			if (rs != null) try { rs.close(); } catch (Exception e) {}
			if (cstmt != null) try { cstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void call01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		CallableStatement cstmt = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
		conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
		conn.setAutoCommit(false);

		try {
			String sql = "{ call test_kang_sp_03(?, ?, ?) }";

			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, "admin");
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);

			cstmt.execute();

			String userId = cstmt.getString(1);
			String rtnmsg = cstmt.getString(2);
			String rtncd = cstmt.getString(3);

			System.out.printf("[%s] [%s] [%s]%n", userId, rtnmsg, rtncd);
		} finally {
			if (cstmt != null) try { cstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void beginEnd01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		// connect to a server HWLIDBT database as user NEO_EP
		OracleDataSource oracleDataSource = null;
		Connection connection = null;
		CallableStatement cstmt = null;

		try {
			oracleDataSource = new OracleDataSource();
			oracleDataSource.setURL("jdbc:oracle:thin:NEO_EP/NEO_EP_DBA@192.168.2.66:1521/HWLIDBT");
			connection = oracleDataSource.getConnection();

			// call the PL/SQL procedures with the threee parameters
			// the first two string parameters (1 and 2) are passed to the procedure
			// as command-line arguments
			// the REF CURSOR parameter (3) is returned from the procedure
			cstmt = connection.prepareCall("begin kang_var_pkg.search_user_info(?, ?, ?); end;");
			cstmt.setString(1, "admin");
			cstmt.setString(2, "dummy");
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);

			cstmt.execute();

			// return the result set
			ResultSet resultSet = (ResultSet) cstmt.getObject(3);

			// determine the number of columns in each row of the result set
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int count = resultSetMetaData.getColumnCount();

			// print the results, all the columns in each row
			while (resultSet.next()) {
				String rsetRow = "";
				for (int i=1; i <= count; i++) {
					rsetRow = rsetRow + " " + String.format("[%s:%s]", resultSetMetaData.getColumnName(i), resultSet.getString(i));
				}
				System.out.println(rsetRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cstmt != null) try { cstmt.close(); } catch (Exception e) {}
			if (connection != null) try { connection.close(); } catch (Exception e) {}
			if (oracleDataSource != null) try { oracleDataSource.close(); } catch (Exception e) {}
		}
	}

	private static void insert01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		// insert
		Connection conn = null;
		PreparedStatement pstmt = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
		conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
		conn.setAutoCommit(false);

		try {
			conn.setAutoCommit(false);

			String sql = "insert into TB_MAIL_SPOOL "
					+ "("
					+ "  SPOOL_ID"
					+ ", EAGLE_SENDER_ID"
					+ ", USER_ID"
					+ ", SENDER_EMAIL"
					+ ", SUBJECT"
					+ ", CONTENT_FILE"
					+ ", RESERVE_DATE"
					+ ") values ("
					+ "  ?"    // SPOOL_ID - longTime
					+ ", ?"    // EAGLE_SENDER_ID
					+ ", ?"    // USER_ID - userid
					+ ", ?"    // SENDER_EMAIL - sender
					+ ", ?"    // SUBJECT - title
					+ ", ?"    // CONTENT_FILE - contents ? file
					+ ", ?"    // RESERVE_DATE - saveToSent  'yyyyMMddHHmm'
					+ ")";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, new Date().getTime());
			pstmt.setString(2, "eagle_sender_id");
			pstmt.setString(3, "userid");
			pstmt.setString(4, "sender@gmail.com");
			pstmt.setString(5, "This is a title.");
			pstmt.setString(6, "content_file");
			{
				String strDate = "201806111530";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
				ParsePosition pos = new ParsePosition(0);
				Date reserveDate = sdf.parse(strDate, pos);

				long longTime = new java.util.Date().getTime();
				java.sql.Date sqlDate = new java.sql.Date(longTime);
				java.sql.Time sqlTime = new java.sql.Time(longTime);
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(longTime);
				pstmt.setDate(7, sqlDate);

				if (flag) System.out.printf(">>>>> INSERT [%s] [%s] [%s] [%s]%n"
						, reserveDate
						, sqlDate
						, sqlTime
						, sqlTimestamp);
			}
			pstmt.executeUpdate();

			conn.commit();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}
}
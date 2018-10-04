package org.tain.oraclejdbc01;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class JdbcOracle01TestMain {

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

		if (flag) insert01(args);
		if (flag) update01(args);
		if (flag) delete01(args);
		if (flag) select01(args);
		if (flag) select02(args);
		if (flag) dropTable01(args);
		if (flag) createTable01(args);
		if (flag) dropProcedure01(args);
		if (flag) createProcedure01(args);
		if (flag) execProcedure01(args);
		if (flag) dropFunction01(args);
		if (flag) createFunction01(args);
		if (flag) execFunction01(args);
	}

	private static void insert01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

		    String sql = "insert into TB_MAIL_SPOOL"
					+ " ("
					+ "  SPOOL_ID"
					+ ", EAGLE_SENDER_ID"
					+ ", USER_ID"
					+ ", SENDER_EMAIL"
					+ ", SUBJECT"
					+ ", CONTENT_FILE"
					+ ", RESERVE_DATE"
					+ " ) values ("
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

			int rowUpdate = pstmt.executeUpdate();
			if (flag) System.out.printf(">>>>> rowUpdate = %d%n", rowUpdate);

			if (!flag)
				conn.commit();
			else
				conn.rollback();
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void update01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "update TB_MAIL_SPOOL"
					+ " set"
					+ "    SENDER_NAME = ?"
					+ "    , FIRST_TO_RECEIVER = ?"
					+ " where 1=1"
					+ "    and EAGLE_SENDER_ID = ?"
					+ "";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "Hello");
			pstmt.setString(2, "World");
			pstmt.setString(3, "eagle_sender_id");
			int rowUpdate = pstmt.executeUpdate();

			if (!flag)
				conn.commit();
			else
				conn.rollback();
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void delete01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "delete TB_MAIL_SPOOL"
					+ " where 1=1"
					+ "    and EAGLE_SENDER_ID = ?"
					+ "";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "eagle_sender_id");
			int rowUpdate = pstmt.executeUpdate();

			if (!flag)
				conn.commit();
			else
				conn.rollback();
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void select01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		List<Map<String, Object>> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			list = new ArrayList<Map<String, Object>>();

			String sql = ""
					+ "select"
					+ "    SPOOL_ID"
					+ "    , EAGLE_SENDER_ID"
					+ "    , USER_ID"
					+ "    , SENDER_NAME"
					+ "    , SENDER_EMAIL"
					+ "    , SUBJECT"
					+ "    , MAIL_SIZE"
					+ "    , FIRST_TO_RECEIVER"
					+ "    , CONTENT_FILE"
					+ " from"
					+ "    TB_MAIL_SPOOL"
					+ " where 1=1"
					+ "    and EAGLE_SENDER_ID = ?"
					+ "";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "eagle_sender_id");

			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("spoolId", resultSet.getLong("SPOOL_ID"));
				map.put("eagleSenderId", resultSet.getString("EAGLE_SENDER_ID"));
				map.put("userId", resultSet.getString("USER_ID"));
				map.put("senderName", resultSet.getString("SENDER_NAME"));
				map.put("senderEmail", resultSet.getString("SENDER_EMAIL"));
				map.put("subject", resultSet.getString("SUBJECT"));
				map.put("mailSize", resultSet.getInt("MAIL_SIZE"));
				map.put("firstToReceiver", resultSet.getString("FIRST_TO_RECEIVER"));
				map.put("contentFile", resultSet.getString("CONTENT_FILE"));
				list.add(map);
			}

			if (!flag)
				conn.commit();
			else
				conn.rollback();
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void select02(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		List<Map<String, Object>> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			list = new ArrayList<Map<String, Object>>();

			String sql = "select * from TB_ORG_USER where USER_ID like '%' || ? || '%'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "admin");
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", resultSet.getString("USER_ID"));
				map.put("userName", resultSet.getString("USER_NAME"));
				map.put("password", resultSet.getString("PASSWORD"));
				list.add(map);
			}

			if (!flag)
				conn.commit();
			else
				conn.rollback();
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (Exception e) {}
			if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void dropTable01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "drop table ZZ_TEST01";

			stmt = conn.createStatement();
			boolean isDropped = stmt.execute(sql);

			if (flag)
				conn.commit();
			else
				conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void createTable01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "create table ZZ_TEST01"
					+ " ("
					+ "    USER_ID    NUMBER(5)     NOT NULL, "
					+ "    USER_NAME  VARCHAR2(50)  NOT NULL,"
					+ "    CONTENT    VARCHAR2(128) NOT NULL,"
					+ "    REG_DATE   DATE          NOT NULL"
					+ " )";
					// + "    PRIMARY KEY (USER_ID) "

			stmt = conn.createStatement();
			boolean isCreated = stmt.execute(sql);

			if (flag) conn.commit();
			else conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void dropProcedure01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "drop procedure ZZ_PROC_INSERT01";

			stmt = conn.createStatement();
			boolean isDropped = stmt.execute(sql);

			if (flag) conn.commit();
			else conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void createProcedure01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = " create or replace procedure ZZ_PROC_INSERT01 ( \n"
				+ "    p_user_id    IN      ZZ_TB_TEST01.USER_ID%TYPE, \n"
				+ "    p_user_name  IN      ZZ_TB_TEST01.USER_NAME%TYPE, \n"
				+ "    p_content    IN      ZZ_TB_TEST01.CONTENT%TYPE, \n"
				+ "    p_reg_date   IN      ZZ_TB_TEST01.REG_DATE%TYPE, \n"
				+ "    p_ret_val    OUT     varchar2, \n"
				+ "    p_inout_val  IN OUT  varchar2 \n"
				+ " ) is \n"
				+ "    inner_val    varchar(10); \n"
				+ " begin \n"
				+ "    dbms_output.put_line('inout_val : ' || p_inout_val); \n"
				+ "     \n"
				+ "    insert into ZZ_TB_TEST01 ( \n"
				+ "        USER_ID, USER_NAME, CONTENT, REG_DATE \n"
				+ "    ) values ( \n"
				+ "        p_user_id, p_user_name, p_content, p_reg_date \n"
				+ "    ); \n"
				+ "     \n"
				+ "    p_ret_val := 'INSERT OK!!!!'; \n"
				+ "    p_inout_val := 'IN OUT is OK!!!'; \n"
				+ "    commit; \n"
				+ " end; \n";

			stmt = conn.createStatement();
			boolean isCreated = stmt.execute(sql);

			if (flag) conn.commit();
			else conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void execProcedure01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		CallableStatement cstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "{ call ZZ_PROC_INSERT01(?, ?, ?, ?, ?, ?) }";   // OK!!
			// String sql = "{ begin ZZ_PROC_INSERT01(?, ?, ?, ?, ?, ?); end; }";   // ERROR

			cstmt = conn.prepareCall(sql);

			cstmt.setInt(1, 1234);
			cstmt.setString(2, "userName");
			cstmt.setString(3, "content");
			cstmt.setDate(4, new java.sql.Date(new Date().getTime()));
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.setString(6, "Hello, world!!");

			boolean isExecuted = cstmt.execute();

			String ret_val = cstmt.getString(5);
			String inout_val = cstmt.getString(6);

			System.out.printf("[ret_val:%s] [inout_val:%s]%n", ret_val, inout_val);

			if (flag) conn.commit();
			else conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (cstmt != null) try { cstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void dropFunction01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "drop function ZZ_FUNC_COUNT01";

			stmt = conn.createStatement();
			boolean isDropped = stmt.execute(sql);

			if (flag) conn.commit();
			else conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void createFunction01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = " create or replace function ZZ_FUNC_COUNT01 ( \n"
				+ "    p_in_val     IN      varchar2 \n"
				//+ "    p_out_val    OUT     varchar2, \n"
				//+ "    p_inout_val  IN OUT  varchar2 \n"
				+ " )  \n"
				+ " return NUMBER \n"
				+ " is  \n"
				+ "    ret_val    number(5); \n"
				+ " begin \n"
				+ "    dbms_output.put_line('in_val = ' || p_in_val); \n"
				//+ "    dbms_output.put_line('inout_val = ' || p_inout_val); \n"
				+ "    \n"
				+ "    select \n"
				+ "        count(*) \n"
				+ "    into \n"
				+ "        ret_val \n"
				+ "    from \n"
				+ "        ZZ_TB_TEST01 \n"
				+ "    where 1=1 \n"
				+ "    ; \n"
				+ "    \n"
				//+ "    p_out_val := 'OUT VALUE'; \n"
				//+ "    p_inout_val := 'INOUT VALUE'; \n"
				+ "    \n"
				+ "    return ret_val; \n"
				+ " end; \n";

			stmt = conn.createStatement();
			boolean isCreated = stmt.execute(sql);

			if (flag) conn.commit();
			else conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	private static void execFunction01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + getClassInfo());

		Connection conn = null;
		CallableStatement cstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@192.168.2.66:1521:HWLIDBT";
			conn = DriverManager.getConnection(url, "NEO_EP", "NEO_EP_DBA");
			conn.setAutoCommit(false);

			String sql = "{ ? = call ZZ_FUNC_COUNT01(?) }";    // OK!!
			// String sql = "{ begin ? := ZZ_FUNC_COUNT01(?); end; }";   // ERROR

			cstmt = conn.prepareCall(sql);

			cstmt.setString(2, "IN_VALUE");
			cstmt.registerOutParameter(1, Types.NUMERIC);

			boolean isExecuted = cstmt.execute();   // true: return ResultSet,  false: no ResultSet

			int ret_val = cstmt.getInt(1);

			System.out.printf("[ret_val:%d]%n", ret_val);

			if (flag) conn.commit();
			else conn.rollback();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			if (cstmt != null) try { cstmt.close(); } catch (Exception e) {}
			if (conn != null) try { conn.close(); } catch (Exception e) {}
		}
	}

	/////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	/*
	 *  // SQL syntax
	 *  CallableStatement cs1 = conn.prepareCall("{ call proc(?, ?) }");  // stored procedure
	 *  CallableStatement cs1 = conn.prepareCall("{ ? = call func(?, ?) }");  // stored function
	 *  CallableStatement cs1 = conn.prepareCall("{ begin proc(?, ?); end; }");  // stored procedure
	 *  CallableStatement cs1 = conn.prepareCall("{ begin ? := func(?, ?); end; }");  // stored function
	 */

	/*
	public void procedure01() {

	}

	public void function01() {

	}

	public void pkgProcedure01() {

	}

	public void pkgFunction01() {

	}
	*/
}

package com.wf.seeker.mytest_cases.testIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
public class GeneratorSql {

//@formatter:off
//	INSERT INTO sys_user ( id, username, realname, password, salt, status, del_flag, work_no, create_time, activiti_sync,email ) VALUES ( '1217000000000000013', 'DNAF00987', '伍顿', '1f5c003d824eef5769b285dad242a0ad', 'CBRl88lP', 1, '0','DNAF00987' , sysdate(), '1','DNAF00987@df-nissanfc.com' ) ;
//	INSERT INTO sys_user_role ( id, user_id, role_id ) VALUES ( REPLACE(uuid(),'-',''), '1217000000000000013', '1208928788694880258' ) ;
//	INSERT INTO sys_user_depart ( id, user_id, dep_id ) VALUES ( REPLACE(uuid(),'-',''), '1217000000000000013', 'b9e5ed9153bc4aa4950ac9359df13e41' ) ;

	private static int num = 14;
	public static void main(String[] args) {

		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\BBB.txt"), "UTF-8"));

			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\AAA.txt"), "UTF-8"));
			String str = new String();
			while ((str = in.readLine()) != null) {
				out.write("INSERT INTO sys_user ( id, username, realname, password, salt, status, del_flag, work_no, create_time, activiti_sync,email ) VALUES ( '12170000000000000"+String.valueOf(num)+"', '"+str+"', '"+str+"', '1f5c003d824eef5769b285dad242a0ad', 'CBRl88lP', 1, '0','"+str+"' , sysdate(), '1','"+str+"@df-nissanfc.com' ) ;");
				out.newLine();

				out.write("INSERT INTO sys_user_role ( id, user_id, role_id ) VALUES ( REPLACE(uuid(),'-',''), '12170000000000000"+String.valueOf(num)+"', '1236959895222439938' ) ;");
				out.newLine();

				out.write("INSERT INTO sys_user_depart ( id, user_id, dep_id ) VALUES ( REPLACE(uuid(),'-',''),'12170000000000000"+String.valueOf(num)+"', 'b9e5ed9153bc4aa4950ac9359df13e41' ) ;");
				// out.append(str);
				out.newLine();
				out.newLine();
				num++;
			}
			in.close();

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

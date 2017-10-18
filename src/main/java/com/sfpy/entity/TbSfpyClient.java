package com.sfpy.entity;

import com.sfpy.field.AbstractTable;
import com.sfpy.field.Field;
import com.sfpy.type.DateType;
import com.sfpy.type.IntType;
import com.sfpy.type.NumberStringType;
import com.sfpy.type.StringType;

/**
 * �û���Ϣ��
 * @author SFPY
 *
 */
public class TbSfpyClient extends AbstractTable {
	public static String TABLE = "TB_SFPY_CLIENT";

	public static Field CLIENT_ID = new Field("�û�ID", "CLIENT_ID", new IntType(), true, true);
	public static Field CLIENT_NAME = new Field("����", "CLIENT_NAME", new StringType());
	public static Field CLIENT_PSWD = new Field("����", "CLIENT_PSWD", new StringType());
	public static Field CLIENT_IDENTITY = new Field("���֤��", "CLIENT_IDENTITY", new StringType(30));
	public static Field CLIENT_BALANCE = new Field("���", "CLIENT_BALANCE", new NumberStringType(15, 4));
	public static Field CLIENT_BANK_ID = new Field("��������", "CLIENT_BANK_ID", new IntType());
	public static Field CLIENT_ACCOUNT_CODE = new Field("���п���", "CLIENT_ACCOUNT_CODE", new StringType());
	public static Field CLIENT_STATUS = new Field("״̬", "CLIENT_STATUS", new IntType());
	public static Field CLIENT_ADDRESS = new Field("��ַ", "CLIENT_ADDRESS", new StringType(256));
	public static Field CLIENT_TIME = new Field("����ʱ��", "CLIENT_TIME", new DateType());
	
	
	
	public static final Field[] ALL_FIELDS = new TbSfpyClient().getArrayTableFields();
}

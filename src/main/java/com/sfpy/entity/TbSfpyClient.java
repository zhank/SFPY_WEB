package com.sfpy.entity;

import com.sfpy.field.AbstractTable;
import com.sfpy.field.Field;
import com.sfpy.type.DateType;
import com.sfpy.type.IntType;
import com.sfpy.type.NumberStringType;
import com.sfpy.type.StringType;

/**
 * 用户信息表
 * @author SFPY
 *
 */
public class TbSfpyClient extends AbstractTable {
	public static String TABLE = "TB_SFPY_CLIENT";

	public static Field CLIENT_ID = new Field("用户ID", "CLIENT_ID", new IntType(), true, true);
	public static Field CLIENT_NAME = new Field("姓名", "CLIENT_NAME", new StringType());
	public static Field CLIENT_PSWD = new Field("密码", "CLIENT_PSWD", new StringType());
	public static Field CLIENT_IDENTITY = new Field("身份证号", "CLIENT_IDENTITY", new StringType(30));
	public static Field CLIENT_BALANCE = new Field("余额", "CLIENT_BALANCE", new NumberStringType(15, 4));
	public static Field CLIENT_BANK_ID = new Field("开户银行", "CLIENT_BANK_ID", new IntType());
	public static Field CLIENT_ACCOUNT_CODE = new Field("银行卡号", "CLIENT_ACCOUNT_CODE", new StringType());
	public static Field CLIENT_STATUS = new Field("状态", "CLIENT_STATUS", new IntType());
	public static Field CLIENT_ADDRESS = new Field("地址", "CLIENT_ADDRESS", new StringType(256));
	public static Field CLIENT_TIME = new Field("开户时间", "CLIENT_TIME", new DateType());
	
	
	
	public static final Field[] ALL_FIELDS = new TbSfpyClient().getArrayTableFields();
}

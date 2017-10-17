package com.sfpy.entity;

import com.sfpy.field.AbstractTable;
import com.sfpy.field.Field;
import com.sfpy.type.DateType;
import com.sfpy.type.IntType;
import com.sfpy.type.StringType;

/**
 * 交易信息
 * @author SFPY
 */
public class TbSfpyTransfer extends AbstractTable {
	public static String TABLE = "TB_SFPY_TRANSFER";

	public static Field OBMS_ID = new Field("ID", "OBMS_ID", new IntType(), true, true);
	public static Field CLIENT_ID = new Field("客户ID", "OBMS_ID", new IntType(), true);
	public static Field TRANSFER_TIME = new Field("交易时间", "TRANSFER_TIME", new DateType(), true);
	public static Field TRANSFER_MONEY = new Field("交易金额", "TRANSFER_MONEY", new StringType(), true);
	public static Field CLIENT_BALANCE = new Field("余额", "TRANSFER_MONEY", new StringType(), true);

	public static final Field[] ALL_FIELDS = new TbSfpyTransfer().getArrayTableFields();
}

package com.sfpy.db;

import java.util.List;
import java.util.Map;

public interface IDbCall {
	/**
	 * 锟斤拷锟斤拷锟铰�
	 * @param table 锟斤拷锟斤拷
	 * @param dataMap 锟斤拷锟斤拷锟斤拷锟捷碉拷Map锟斤拷锟斤拷锟叫碉拷key锟斤拷锟斤拷锟街讹拷锟斤拷锟斤拷value锟角讹拷应锟斤拷锟街段碉拷锟斤拷锟斤拷
	 * @throws Exception
	 */
	public int insert(String table, Map<String,Object> dataMap) throws Exception;

	/**
	 * 锟斤拷锟铰憋拷锟铰硷拷亩锟斤拷锟街讹拷
	 * @param table 锟斤拷锟斤拷
	 * @param dataMap 锟斤拷锟斤拷锟斤拷锟斤拷锟捷碉拷Map锟斤拷锟斤拷锟叫碉拷key锟斤拷锟斤拷锟街讹拷锟斤拷锟斤拷value锟角讹拷应锟斤拷锟街段碉拷锟斤拷锟斤拷
	 * @param condition 锟斤拷锟斤拷锟斤拷锟�
	 * @param condValues 锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷
	 * @throws Exception
	 */
	public void update(String table, Map<String,Object> dataMap, String condition, List<Object> condValues) throws Exception;

	/**
	 * 锟斤拷锟铰憋拷锟铰硷拷锟揭伙拷锟斤拷侄锟�
	 * @param table 锟斤拷锟斤拷
	 * @param name 锟斤拷锟斤拷锟斤拷锟街讹拷锟斤拷
	 * @param value 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param condition 锟睫诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * @throws Exception
	 */
	public void update(String table, String name, Object value, String condition) throws Exception;

	/**
	 * 删锟斤拷锟斤拷录
	 * @param table 锟斤拷锟斤拷
	 * @param condition 锟斤拷锟斤拷锟斤拷锟�
	 * @param condValues 锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷
	 * @throws Exception
	 */
	public void delete(String table, String preparedCond, List<Object> condValues) throws Exception;

	/**
	 * 锟斤拷取一锟斤拷锟斤拷录锟斤拷锟斤拷map锟斤拷锟斤拷
	 * @param table 锟斤拷锟斤拷
	 * @param cols 锟斤拷要锟斤拷锟截碉拷锟街讹拷锟斤拷锟叫憋拷
	 * @param condition 锟斤拷锟斤拷锟斤拷锟�
	 * @param condValues 锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷
	 * @param mustUnique 锟角凤拷锟斤拷锟轿ㄒ伙拷锟斤拷锟轿猅rue锟斤拷锟斤拷实锟斤拷锟斤拷锟叫讹拷锟斤拷锟斤拷录锟斤拷锟斤拷锟阶筹拷锟届常
	 * @return 锟斤拷锟斤拷map
	 * @throws Exception
	 */
	public Map<String,Object> getOneRowAsMap(String table, List<String> cols, String condition, List<Object> condValues, boolean mustUnique)
			throws Exception;

	/**
	 * 锟斤拷取一锟斤拷锟斤拷录锟斤拷锟斤拷锟斤拷锟介方式锟斤拷锟截★拷
	 * @param table 锟斤拷锟斤拷
	 * @param cols 锟斤拷要锟斤拷锟截碉拷锟街讹拷锟斤拷锟叫憋拷
	 * @param condition 锟斤拷锟斤拷锟斤拷锟�
	 * @param condValues 锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷
	 * @param mustUnique 锟角凤拷锟斤拷锟轿ㄒ伙拷锟斤拷锟轿猅rue锟斤拷锟斤拷实锟斤拷锟斤拷锟叫讹拷锟斤拷锟斤拷录锟斤拷锟斤拷锟阶筹拷锟届常
	 * @return
	 * @throws Exception
	 */
	public Object[] getOneRowAsArray(String table, List<String> cols, String condition, List<Object> condValues, boolean mustUnique)
			throws Exception;

	//锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷菘锟絃abel为key锟侥凤拷锟斤拷,锟斤拷锟斤拷锟睫改伙拷影锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷写锟斤拷一锟斤拷锟斤拷锟斤拷,希锟斤拷锟斤拷锟斤拷目锟秸达拷锟节合诧拷锟斤拷锟斤拷  by maj 2016-8-8
	public List<Map<String,Object>> searchAsMapListInLabel(String table, List<String> cols, String condition, List<Object> condValues, int start, int max,
			String orderBy, String groupBy, boolean bAsc) throws Exception;
	/**
	 * 通锟矫诧拷询锟斤拷锟斤拷List[array1, array2...]锟斤拷式锟斤拷锟斤拷
	 * @param table 锟斤拷锟斤拷
	 * @param cols 锟斤拷要锟斤拷锟截碉拷锟街讹拷锟斤拷锟叫憋拷
	 * @param condition 锟斤拷锟斤拷锟斤拷锟�
	 * @param condValues 锟斤拷锟斤拷锟斤拷锟斤拷锟叫憋拷
	 * @param start 锟斤拷录锟侥匡拷始位锟斤拷
	 * @param max 锟斤拷锟斤拷锟斤拷锟斤拷录锟斤拷锟斤拷
	 * @param orderBy 锟斤拷锟斤拷锟街讹拷锟斤拷
	 * @param groupBy 锟斤拷锟斤拷锟街讹拷锟斤拷
	 * @param bAsc 锟角凤拷锟斤拷锟斤拷
	 * @return 锟斤拷List[array1, array2...]锟斤拷式锟斤拷锟截ｏ拷cols指锟斤拷锟斤拷锟街讹拷锟斤拷锟斤拷
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchAsMapList(String table, List<String> cols, String condition, List<Object> condValues, int start, int max,
			String orderBy, String groupBy, boolean bAsc) throws Exception;

	/**
	 * 锟矫碉拷锟斤拷录锟斤拷锟斤拷
	 * @param table 锟斤拷锟斤拷
	 * @param condition 锟斤拷锟斤拷锟斤拷锟�
	 * @param condValues 锟斤拷锟斤拷锟斤拷锟斤拷斜锟�
	 * @param distinct 锟斤拷锟捷匡拷distinct
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷录锟斤拷锟斤拷
	 * @throws Exception
	 */
	public int getCount(String table, String condition, List<Object> condValues, String distinct) throws Exception;
}

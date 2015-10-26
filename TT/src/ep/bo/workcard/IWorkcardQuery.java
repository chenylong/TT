package ep.bo.workcard;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IWorkcardQuery {

    /***
     * @throws SQLException 
     */
	public List queryWorkcard(HashMap map) throws SQLException;

	public List<?> queryNumCountById(HashMap hm) throws SQLException;

	public List<?> queryDomeSql(HashMap hm)throws SQLException;
 
	/**
	 * 
	 * @author chen1023 E-mail:chen1023@foxmail.com
	 * @version 创建时间：2015年10月24日 下午12:00:27
	 * 类说明
	 * @param hm
	 * @return
	 * @throws SQLException
	 */
	public List<?> viewPicbyId(HashMap hm);
	
	
}

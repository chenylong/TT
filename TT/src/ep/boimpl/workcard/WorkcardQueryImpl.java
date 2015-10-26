package ep.boimpl.workcard;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import ep.bo.workcard.IWorkcardQuery;

public class WorkcardQueryImpl extends SqlMapClientDaoSupport  implements IWorkcardQuery {

 
	public List queryWorkcard(HashMap map) throws SQLException {
		List workcard = new ArrayList();		
		workcard = this.getSqlMapClientTemplate().queryForList("selectWorkcard", map);
        return workcard;
	}

	@Override
	public List<?> queryNumCountById(HashMap hm) throws SQLException {
		 List list = this.getSqlMapClientTemplate().queryForList("queryNumCountById",hm);
		 return list;
	}

	@Override
	public List<?> queryDomeSql(HashMap hm) throws SQLException {
		 List list = this.getSqlMapClientTemplate().queryForList("queryDomeSql",hm);
		 return list;
	}

	@Override
	public List<?> viewPicbyId(HashMap hm) {
		 List list = this.getSqlMapClientTemplate().queryForList("queryPictureByID",hm);
		 return list;
	}
	
    
}

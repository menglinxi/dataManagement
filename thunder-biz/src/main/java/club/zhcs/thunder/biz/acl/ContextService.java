package club.zhcs.thunder.biz.acl;

import club.zhcs.thunder.bean.acl.Context;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author kerbores@gmail.com
 *
 */
@Service
public class ContextService extends BaseService<Context> {


    public List<Context> searchObj(String s) {
        Sql sql = Sqls.create(s);
        return searchObj(sql);
    }
}

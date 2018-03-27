package club.zhcs.thunder.biz.acl;

import club.zhcs.thunder.bean.acl.Game;
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
public class GameService extends BaseService<Game> {


    public List<Game> searchObj(String s) {
        Sql sql = Sqls.create(s);
        return searchObj(sql);
    }
}

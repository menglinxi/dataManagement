package club.zhcs.thunder.biz.acl;

import club.zhcs.thunder.bean.acl.ChannelGame;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
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
public class ChannelGameService extends BaseService<ChannelGame> {


    public List<Record> search(String s) {
        Sql sql = Sqls.create(s);
        return search(sql);
    }
}

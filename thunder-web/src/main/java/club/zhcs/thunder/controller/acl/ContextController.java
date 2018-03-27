package club.zhcs.thunder.controller.acl;

import club.zhcs.thunder.bean.acl.*;
import club.zhcs.thunder.biz.acl.*;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.ext.shiro.anno.SINORequiresPermissions;
import club.zhcs.thunder.ext.shiro.matcher.SINOCredentialsMatcher;
import club.zhcs.thunder.utils.CSVUtils;
import club.zhcs.thunder.utils.ServiceUtils;
import club.zhcs.thunder.vo.InstallPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author kerbores@gmail.com
 */
@RestController
@RequestMapping("context")
@Api(value = "Context", tags = {"数据模块"})
public class ContextController extends BaseController {


    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;
    @Autowired
    ChannelService channelService;
    @Autowired
    ContextService contextService;
    @Autowired
    ChannelGameService channelGameService;


    @GetMapping("canUp")
    @SINORequiresPermissions(InstallPermission.CONTEXT_CANUP)
    @ApiOperation(value = "检验当前登录者是否可以上传游戏数据文件")
    public Object canUp(){
        User user = getUser();
        Map map = new HashMap();
        if(user==null){
            return ServiceUtils.notLogin();
        }
        if(user.isManager()){
            map.put("enable",true);
            return ServiceUtils.success("可查看",map);
        }
        map.put("enable",false);
        return ServiceUtils.success("暂无授权",map);
    }

    @GetMapping("gList")
    @ApiOperation(value = "登录者被开放的游戏")
    public Object gList() {
        User user = getUser();
        if (user == null) {
            return ServiceUtils.failed("用户未登录");
        }
        List<NutMap> needBack = new ArrayList<>();
        if (user.isManager()) {
            List<Game> gameList = gameService.queryAll();
            for (Game game : gameList) {
                NutMap nutMap = new NutMap();
                nutMap.setv("game_id", game.getId());
                nutMap.setv("gamename", game.getGameName());
                needBack.add(nutMap);
            }
        } else {
            //List<Record> recordList = channelGameService.search(" select cg.*,g.gamename ,c.channel_name from t_channel_game cg left join t_game g on cg.game_id = g.id  left join t_channel c on cg.channel_id = c.id where cg.user_id = " + "42");
            List<Record> recordList = channelGameService.search(" select cg.*,g.gamename  from t_channel_game cg left join t_game g on cg.game_id = g.id   where cg.user_id = " + getUid()+" group by cg.game_id ");
            for (Record re : recordList) {
                NutMap nutMap = new NutMap();
                nutMap.setv("game_id", re.get("game_id"));
                nutMap.setv("gamename", re.get("gamename"));
                needBack.add(nutMap);
            }
        }
        return ServiceUtils.success("成功获取登陆者游戏列表", needBack);
    }

    @GetMapping("cList")
    @ApiOperation(value = "登陆者被开放的渠道")
    public Object cList(@RequestParam(value = "gameId") @ApiParam("游戏ID") Long gameId) {
        User user = getUser();
        if (user == null) {
            return ServiceUtils.failed("用户未登录");
        }
        List<NutMap> needBack = new ArrayList<>();
        if (user.isManager()) {
            List<Channel> channelList = channelService.queryAll();
            for (Channel channel : channelList) {
                NutMap nutMap = new NutMap();
                nutMap.setv("channelId", channel.getId());
                nutMap.setv("channelName", channel.getChannelName());
                needBack.add(nutMap);
            }
        } else {
            //List<Record> recordList = channelGameService.search(" select cg.*,g.gamename ,c.channel_name from t_channel_game cg left join t_game g on cg.game_id = g.id  left join t_channel c on cg.channel_id = c.id where cg.user_id = " + "42");
            List<Record> recordList = channelGameService.search(" select cg.*,c.channel_name  from t_channel_game cg left join t_channel c on cg.channel_id = c.id   where cg.user_id = " + getUid() + " and  cg.game_id=" + gameId + " ");
            for (Record re : recordList) {
                NutMap nutMap = new NutMap();
                nutMap.setv("channelId", re.get("channel_id"));
                nutMap.setv("channelName", re.get("channel_name"));
                needBack.add(nutMap);
            }
        }
        return ServiceUtils.success("成功获取登陆者渠道列表", needBack);
    }



    /**
     * 获取用户下的游戏列表
     * @param user
     * @return
     */
    public List<Game> getGameList4User(User user) {
        if(user.isManager()){
            return gameService.queryAll();
        }else{
            return gameService.searchObj("select g.* from t_game  g left join t_channel_game cg  on cg.game_id = g.id where cg.user_id = " + user.getId()+" group by g.id ");
        }
    }

    /**
     *通过用户和游戏获取到开放的渠道
     * @param user
     * @param game
     * @return
     */
    public List<Channel> getChannelList4UserAndGame(User user, Game game){
        if(user.isManager()){
            return  channelService.queryAll();
        }else {
            return channelService.searchObj("select c.* from t_channel  c left join t_channel_game cg  on cg.channel_id = c.id where cg.user_id ="+user.getId()+"  and cg.game_id="+game.getId()+" ");
        }


    }



    /**
     * 游戏数据列表
     */
    @GetMapping("list")
    @ApiOperation(value = "获取游戏流水数据")
    public Object list() throws ParseException {
        User user = getUser();
        if (user == null) {
            return ServiceUtils.failed("用户未登录");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //6个参数  开始时间（包含）
        String beginTimeStr = request.getParameter("beginTime");
        //结束时间（包含）
        String endTimeStr = request.getParameter("endTime");
        //游戏ID
        String gameIdStr = request.getParameter("gameId");
        //渠道ID
        String channelIdStr = request.getParameter("channelId");
        //页码
        String pageIndexStr = request.getParameter("pageIndex");
        //页面大小
        String pageSizeStr = request.getParameter("pageSize");
        Date date = new Date();
        String format = sdf.format(date);
        String beginTime = format + " 00:00:00";
        String endTime = format + " 23:59:59";
        if (Strings.isNotBlank(beginTimeStr)) {
            beginTime = beginTimeStr + " 00:00:00";
        }
        if (Strings.isNotBlank(endTimeStr)) {
            endTime = endTimeStr + " 23:59:59";
        }
        int pageIndex = 1;
        int pageSize = 10;
        if (Strings.isNotBlank(pageIndexStr)) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        if (Strings.isNotBlank(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        //登陆者的开放的游戏列表
        List<Game> gameList = getGameList4User(user);
        if(gameList==null){
            return ServiceUtils.failed("你还没有被授权游戏");
        }
        /**
         * 管理者的话直接查询所有的游戏下的对应的时间内的天数+
         * 渠道管理员的话查询被分配的game
         */
        //创建查询的sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("select * from t_context where 1=1 ");
        ChannelGame oneChannelAndGame = null;
        //超管
        if (user.isManager()) {
            if (Strings.isNotBlank(gameIdStr) && Strings.isNotBlank(channelIdStr)) {
                sql.append(" and game_id=" + gameIdStr + " ");
                Channel channel = channelService.fetch(Long.valueOf(channelIdStr));
                sql.append(" and  channel_name = '" + channel.getChannelName() + "'");
            }
            if (Strings.isNotBlank(beginTimeStr)) {
                //超管查询的开始时间不为空
                sql.append(" and import_time >= '" + beginTime + "' ");
            }

        } else {
            //非超管
            if (Strings.isNotBlank(gameIdStr) && Strings.isNotBlank(channelIdStr)) {
                sql.append(" and game_id=" + gameIdStr + " ");
                Channel channel = channelService.fetch(Long.valueOf(channelIdStr));
                sql.append(" and  channel_name = '" + channel.getChannelName() + "'");
                oneChannelAndGame = channelGameService.fetch(Cnd.where("game_id","=",gameIdStr).and("channel_id","=",channelIdStr).and("user_id","=",user.getId()));
            } else {
                //游戏为空，使用默认查到的第一个游戏和渠道
                Game game = gameList.get(0);
                if (game == null) {
                    return ServiceUtils.failed("你还没有被授权游戏");
                }
                String gameName = game.getGameName();
                //获取用户/游戏下的渠道数组
                List<Channel> gameChannels = getChannelList4UserAndGame(user,game);
                if(gameChannels == null || gameChannels.size()==0){
                    return  ServiceUtils.failed("你还没有获取到渠道数据");
                }
                Channel channel = gameChannels.get(0);
                sql.append(" and game_id=" + game.getId() + "");
                String channelName2 = channel.getChannelName();
                sql.append(" and  channel_name = '" + channelName2 + "'");
                oneChannelAndGame = channelGameService.fetch(Cnd.where("game_id","=",game.getId()).and("channel_id","=",channel.getId()).and("user_id","=",user.getId()));
            }
            Date createDate = oneChannelAndGame.getCreateDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createdDateStr = dateFormat.format(createDate);
            if (Strings.isBlank(beginTimeStr)) {
                //没有开始时间直接使用
                sql.append(" and import_time >= '" + createdDateStr + "' ");
            } else {

                Date date2 = dateFormat.parse(beginTime);
                if (date2.after(createDate)) {
                    //开始时间在创建时间之后
                    sql.append(" and import_time >= '" + beginTime + "' ");
                } else {
                    sql.append(" and import_time >= '" + createdDateStr + "' ");
                }
            }
        }
        if (Strings.isNotBlank(endTime)) {
            sql.append(" and import_time <= '" + endTime + "' ");
        }
        sql.append("  group by id ORDER BY looktime DESC ");
        log.info(sql.toString());
    /* */
        List<Context> contextList = contextService.searchObj(sql.toString());
        if(contextList == null || contextList.size()==0){
            NutMap nutMap = new NutMap();
            nutMap.setv("list",null).
                    setv("totalRow", 0)
                    .setv("pageIndex", pageIndex)
                    .setv("pageSize", pageSize);
            return ServiceUtils.success("暂无数据",nutMap);
//            return ServiceUtils.failed("");
        }
//自写分页
        Integer st = pageIndex*pageSize -pageSize;
        Integer en = pageIndex*pageSize;
        Integer size = contextList.size();
        List<Context> eedBack = new ArrayList<>();
       if(en>=size){
           for (int i = st; i <size ; i++) {
               eedBack.add(contextList.get(i));
           }
       }else{
           for (int i = st; i <en; i++) {
               eedBack.add(contextList.get(i));
           }

       }

       NutMap nutMap = new NutMap();
        nutMap.setv("list",eedBack).
                setv("totalRow", size)
                .setv("pageIndex", pageIndex)
                .setv("pageSize", pageSize);
        return ServiceUtils.success("获取数据成功",nutMap);
    }



    //本系统下使用的密码加密方式
    public void ttt(){
        System.out.println(SINOCredentialsMatcher.password("WW6666", "111222"));
    }

//    @Test
//    public void tt()  {
//
//
//
//            Properties prop = System.getProperties();
//
//            String os = prop.getProperty("os.name");
//            if (os != null && os.toLowerCase().indexOf("linux") > -1) {
////                return true;
//                System.out.println(true);
//            } else {
////                return false;
//                System.out.println(false);
//            }
//
//
//
//        URL resource = ClassUtils.getDefaultClassLoader().getResource("");
//
//        String path = resource.getPath();
//        System.out.println(path);
//        System.out.println(SINOCredentialsMatcher.password("zhangliang", "12345678"));
//        File file = new File(path+"56.txt");
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(file.getPath());
//        File file2 = new File("e:/56/56.txt");
//        try {
//            boolean newFile = file2.createNewFile();
//        } catch (IOException e) {
//            System.out.println("shibai");
//        }
//        try{}catch (Exception e){}finally {
////            file.delete();
//        }
//    }


    /**
     * 判断文件的编码格式
     * @param fileName :file
     * @return 文件编码格式
     * @throws Exception
     */
    public static String codeString(String fileName) throws Exception{
        BufferedInputStream bin = new BufferedInputStream(
                new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII" ;
                break ;
            default:
                code = "GBK";
        }

        return code;
    }




    /**
     * 上传游戏流水数据文件
     *
     * @param file
     * @return
     * @throws IOException
     * @throws ParseException
     */
//    @PostMapping(value = "fileUpload")
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST  )
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile file) throws Exception {
        User user = getUser();
        if(user==null){
            return ServiceUtils.notLogin();
        }
        //当前登陆者不可上传并更新游戏数据
        if(!user.isManager()){
            return ServiceUtils.failed("没有更新数据权限");
        }
//        Resource resource = new ClassPathResource("phone.json");
        if (!file.isEmpty()) {


            long startTime = System.currentTimeMillis();
            //TODO 注意windows和linux下缓存文件的路径不一致，考虑主动获取目录去缓存文件
            String path =  "E:/"+startTime + ".csv";
//            String path =  "/data/at_admanager/temp/"+startTime + ".csv";
            File newFile = new File(path);
            //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
            file.transferTo(newFile);
            try {
                String newFilePath = newFile.getPath();
                log.info("newFilePath="+newFilePath);
                dialogUpdateExcel(newFilePath);
                return ServiceUtils.success("更新成功");
            } catch (ParseException e) {
                e.printStackTrace();
                return ServiceUtils.failed("数据更新失败");
            }finally {
                //最后删除缓存文件，避免在服务器上造成大量垃圾文件
                newFile.delete();
            }
        } else {
            return ServiceUtils.failed("上传失败，因为文件是空的.");
        }
    }



    /**
     * 更新游戏流水数据
     *
     * @param path
     * @throws ParseException
     */
    public void dialogUpdateExcel(String path) throws Exception {
        File file = new File(path);
        String fileName = file.getName();
        String s3 = codeString(path);
        List<String> dataList = CSVUtils.importCsv(file,s3);
        if (dataList != null && !dataList.isEmpty()) {
            List<Game> gameList = gameService.queryAll();
            Map<String, Game> integerGameMap = new HashMap<>();
            for (Game g : gameList) {
                integerGameMap.put(g.getGameName(), g);
            }
            Map<String, String> needCoverDay = new HashMap<>();
            //剔除文件第一行数据
            for (int i = 1; i < dataList.size(); i++) {
                String s = dataList.get(i);
                if (!",,,,,,,,,,".equals(s)) {
                    String[] split = s.split(",");
                    Context excelContext = new Context();
                    String s1 = split[0];
                    s1 = s1.replaceAll("/", "-");
                    Date parse = null;
                    //读取到的每条数据的时间默认创建为对应日期+23:59:59，来保证用户可以看到分配渠道/游戏当天的数据
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    String s2 = (s1 + " 23:59:59");
                    if (s1.lastIndexOf("-") > 0) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        parse = sdf.parse(s2);
                    } else {
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        parse = sdf1.parse(s2);
                    }
                    s1 = sdf2.format(parse);
                    String aBoolean = needCoverDay.get(s1);
                    if (Strings.isBlank(aBoolean)) {
                        needCoverDay.put(s1, s1);
                        List<Context> byLookTime = contextService.query(Cnd.where("looktime", "=", s1));
                        for (Context e : byLookTime) {
                            try {
                                /**
                                 * 添加数据之前先删除根据文件内的天数进行一次删除，保证数据库中数据的唯一性，所以要求同一天的数据要连续而且完整
                                 * 例如要给1号数据中添加一条流水，必须将整个1号的流水作为一个文件上传，只上传包含一条流水的文件会导致1号的数据消失，只剩下你最新上传的那一条
                                 */
                                contextService.delete(e);
                                log.debug("delete|id=" + e.getId());
                            } catch (Exception ex) {
                                log.error("deleteFailed|id=" + e.getId() + "|Exception=" + ex);
                            }
                        }
                    }
                    excelContext.setImportTime(parse);
                    excelContext.setLooktime(s1);
                    excelContext.setChannelName(split[1]);
                    String gameName = split[2];
                    Game game = integerGameMap.get(gameName);
                    //记录新游戏，最后可以再数据库中查看没来检验是否出现相同/相似的游戏名
                    if (game != null) {
                        excelContext.setGameName(gameName);
                        excelContext.setGameId(game.getId());
                    } else {
                        Game game1 = new Game();
                        game1.setGameName(gameName);
                        Game save = gameService.save(game1);
                        excelContext.setGameName(save.getGameName());
                        excelContext.setGameId(save.getId());
                    }
                    excelContext.setAddNumber(split[3]);
                    excelContext.setAddPercent(split[4]);
                    excelContext.setKeep2(split[5]);
                    excelContext.setKeep7(split[6]);
                    excelContext.setPayMoney(split[7]);
                    excelContext.setPayPercent(split[8]);
                    excelContext.setClientPercent(split[9]);
                    excelContext.setClientarppu(split[10]);
                    excelContext.setExcelVersion(fileName);
                    excelContext.setCreateDate(new Date());
                    excelContext.setUpdateDate(new Date());
                    excelContext.setDeleteFlag("0");
                    excelContext.setAuditFlag(null);
                    contextService.save(excelContext);
                }
            }
        }
    }


}

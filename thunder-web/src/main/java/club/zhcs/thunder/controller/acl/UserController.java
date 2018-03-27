package club.zhcs.thunder.controller.acl;

import club.zhcs.codec.DES;
import club.zhcs.common.Result;
import club.zhcs.thunder.BootNutzVueApplication;
import club.zhcs.thunder.bean.acl.Channel;
import club.zhcs.thunder.bean.acl.ChannelGame;
import club.zhcs.thunder.bean.acl.Game;
import club.zhcs.thunder.bean.acl.User;
import club.zhcs.thunder.biz.acl.*;
import club.zhcs.thunder.controller.base.BaseController;
import club.zhcs.thunder.dto.GrantDTO;
import club.zhcs.thunder.dto.UserLoginDto;
import club.zhcs.thunder.ext.shiro.anno.SINORequiresPermissions;
import club.zhcs.thunder.ext.shiro.matcher.SINOCredentialsMatcher;
import club.zhcs.thunder.utils.Md5Util;
import club.zhcs.thunder.utils.ServiceUtils;
import club.zhcs.thunder.vo.InstallPermission;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.nutz.dao.Cnd;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author kerbores@gmail.com
 */
@RestController
@RequestMapping("user")
@Api(value = "User", tags = {"用户模块"}, consumes = "信息是二位")
public class UserController extends BaseController {
    @Autowired
    ShiroUserService shiroUserService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;
    @Autowired
    ChannelService channelService;
    @Autowired
    ChannelGameService channelGameService;

    /**
     * 用户列表
     *
     * @param page 页码
     * @return
     */
    @GetMapping("list")
    @SINORequiresPermissions(InstallPermission.USER_LIST)
    @ApiOperation(value = "用户分页列表")
    public Result list(@RequestParam(value = "page", defaultValue = "1") @ApiParam("页码") int page) {
        User loginUser = getUser();
        if(loginUser==null){
            return Result.fail("用户未登录");
        }
        return Result.success().addData("pager", userService.searchByPage(_fixPage(page), Cnd.NEW().desc("id")));
    }

    /**
     * @param key  检索条件
     * @param page 页码
     * @return
     */
    @GetMapping("search")
    @SINORequiresPermissions(InstallPermission.USER_LIST)
    @ApiOperation(value = "用户分页检索")
    public Result search(@RequestParam("key") @ApiParam("搜索关键字") String key, @RequestParam(value = "page", defaultValue = "1") @ApiParam("页码") int page) {
        User loginUser = getUser();
        if(loginUser==null){
            return Result.fail("用户未登录");
        }
        return Result.success()
                .addData("pager", userService.searchByKeyAndPage(
                        _fixSearchKey(key),
                        _fixPage(page),
                        // Cnd.NEW().desc("id"),
                        "name", "nickName", "realName")
                        .addParam("key", key));
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping(value = "add")
    @SINORequiresPermissions(InstallPermission.USER_ADD)
    @ApiOperation(value = "新增用户")
    public Result save(@RequestBody User user) {
        User loginUser = getUser();
        if(loginUser==null){
            return Result.fail("用户未登录");
        }

        user.setPassword(SINOCredentialsMatcher.password(user.getName(), user.getPassword()));
        return userService.save(user) == null ? Result.fail("保存用户失败!") : Result.success().addData("user", user);
    }



//    @GetMapping("myRefactor")
//    //@SINORequiresPermissions(InstallPermission.USER_myRefactor)
//    @ApiOperation(value = "重构数据ID")
//    public Result myRefactor() {
////        System.out.println("5454543435");
//        List<User> userList = userService.query(Cnd.where("1", "=", 1));
//        for (User user : userList) {
//            if (Strings.isNotBlank(user.getOpenid())) {
//                List<ChannelGame> channelGames = channelGameService.query(Cnd.where("user_id", "=", user.getOpenid()));
//                for (ChannelGame c : channelGames) {
//                    c.setUserId(user.getId() + "");
//                    channelGameService.update(c);
//                }
//            }
//        }
////
//        log.info("数值类型userId更换成功");
////
//        List<Game> gameList = gameService.queryAll();
//        for (Game g : gameList) {
//            List<ChannelGame> channelGames = channelGameService.query(Cnd.where("game_id", "=", g.getIdss()));
//            for (ChannelGame c : channelGames) {
//                c.setGameId(g.getId() + "");
//                channelGameService.update(c);
//            }
//        }
////
//        log.info("数值类型gameId更换成功");
//        return Result.fail("888");
//
//    }


    /**
     * 编辑用户
     *
     * @param user 待更新用户
     * @return
     */
    @PostMapping(value = "edit")
    @SINORequiresPermissions(InstallPermission.USER_EDIT)
    @ApiOperation(value = "修改用户基本信息", notes = "仅修改姓名,电话,邮箱和状态信息")
    public Result edit(@RequestBody User user) {
        User loginUser = getUser();
        if(loginUser==null){
            return Result.fail("用户未登录");
        }
        return userService.update(user, "realName", "phone", "email", "status") ? Result.success() : Result.fail("更新用户失败!");
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

    @PostMapping(value="dosave")
//    @SINORequiresPermissions(InstallPermission.USER_dosave)
    @ApiOperation(value = "保存设置的用户的游戏-渠道权限信息")
    /**
     * post请求  参数 info:json数据
     *                userId:被设置的用户ID
     */
    public Object dosave( @RequestParam("info") @ApiParam("设置信息") String info,@RequestParam("userId") @ApiParam("用户ID") Integer userId) throws JSONException {
        User loginUser = getUser();
        if(loginUser==null){
            return ServiceUtils.notLogin();
        }
        if(!loginUser.isManager()){
            return ServiceUtils.failed("暂无授权");
        }
        List<Info> infos = JSONArray.parseArray(info, Info.class);
        Map<String,List<String>> gameIdMap = new HashMap<>();
        //建立游戏ID数组
        for (Info s :infos) {
            ArrayList<String> realGames = new ArrayList<>();
            String[] strings = s.getRealGames();
            for (String ic :strings) {
                if(Strings.isNotBlank(ic)){
                    realGames.add(ic);
                }
            }
            gameIdMap.put(s.getGameId()+"",realGames);
        }

        User user = userService.fetch(Long.valueOf(userId));
        if(user==null){
            return ServiceUtils.failed("用户不存在");
        }
        //获取到当前用户的游戏列表
        List<Game> gameList = getGameList4User(user);
        Map<String,Game>  hasGameAndChannel = new HashMap<>();
        List<JSONObject> needDel = new ArrayList<>();
        //遍历用户当前的列表统计需要去删除的游戏和渠道，
        for (Game g : gameList) {
            String gId = g.getId()+"";
            hasGameAndChannel.put(gId,g);
            List<String> list = gameIdMap.get(gId);
            //新建数据没有这个游戏的数据 ， 添加到需要删除的数组
            if(list==null || list.size()==0){
                List<Channel> gGameChannels = getChannelList4UserAndGame(user,g);
                for (Channel c: gGameChannels) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("uid",user.getId());
                    jsonObject.put("gid",gId);
                    jsonObject.put("cid",c.getId());
                    needDel.add(jsonObject);
                }
            }else{
                List<Channel> hasGameChannels = getChannelList4UserAndGame(user,g);
                for (Channel cc : hasGameChannels) {
                    String ccChannelId = cc.getId()+"";
                    boolean b = false;
                    //新旧相同 不需要更改
                    for (String s  : list) {
                        if(s.equals(ccChannelId)){
                            b=true;
                        }
                    }
                    if(b==false){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("uid",user.getId());
                        jsonObject.put("gid",gId);
                        jsonObject.put("cid",cc.getId());
                        needDel.add(jsonObject);
                    }
                }
            }
        }

        //需要新建的
        List<JSONObject> needCreate = new ArrayList<>();
        for (Map.Entry<String ,List<String>> entry:gameIdMap.entrySet()) {
            String addGameId = entry.getKey();
            Game game = hasGameAndChannel.get(addGameId);
            List<String> value = entry.getValue();
            if(game == null){
                for (String ss :value) {
                    JSONObject jsonObject = new JSONObject() ;
                    jsonObject.put("uid",user.getId());
                    jsonObject.put("gid",addGameId);
                    jsonObject.put("cid",ss);
                    needCreate.add(jsonObject);
                }
            }else{
                for (String ss :value) {
                    List<Channel> gameChannels = getChannelList4UserAndGame(user,game);
                    boolean b = false;
                    for (Channel s  : gameChannels) {
                        if(ss.equals(s.getId()+"")){
                            b=true;
                        }
                    }
                    if(b==false){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("uid",user.getId());
                        jsonObject.put("gid",addGameId);
                        jsonObject.put("cid",ss);
                        needCreate.add(jsonObject);
                    }
                }
            }
        }

        for (JSONObject j: needDel) {
            ChannelGame channelGame = channelGameService.fetch(Cnd.where("game_id", "=", j.get("gid")).and("channel_id", "=", j.get("cid")).and("user_id", "=", user.getId()));
            channelGameService.delete(channelGame);
        }
        for (JSONObject j :needCreate) {
            //把对应的name找到对应的id
            Channel channel = this.channelService.fetch(Long.valueOf(j.getString("cid")));
            if (channel!=null) {
                ChannelGame channelAndGame = new ChannelGame();
                channelAndGame.setGameId(j.get("gid")+"");
                channelAndGame.setChannelId(channel.getId());
                channelAndGame.setUserId(user.getId()+"");
                channelAndGame.setDeleteFlag(0+"");
                channelAndGame.setCreateDate(new Date());
                channelAndGame.setUpdateDate(new Date());
                channelGameService.save(channelAndGame);
            }
        }
        return ServiceUtils.success("保存用户游戏-渠道信息成功");
    }



    @GetMapping("set")
    @ApiOperation(value = "获取用户当前的游戏权限信息")
    /**
     * get请求  参数：userId : 选中的用户ID
     */
    public Object set(@RequestParam("userId") @ApiParam("用户ID") Integer userId) {
        User loginUser = getUser();
        if(loginUser==null){
            return ServiceUtils.notLogin();
        }
        if(!loginUser.isManager()){
            return ServiceUtils.failed("暂无授权");
        }
        if (userId == null) {
            return Result.fail("请选择要设置的用户");
        }
        User user = userService.fetch(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        List<Game> games = gameService.queryAll();
        List<Channel> channels = channelService.queryAll();
        //创建游戏/渠道默认false数组 list0
        //获取用户游戏/渠道数据， 将匹配上的list0对象变为true
        List<NutMap> listFinal = new ArrayList<>();
        for (Game game : games) {
            NutMap nutMap = new NutMap();
            nutMap.put("gameId", game.getId());
            nutMap.put("gameName", game.getGameName());
            nutMap.put("gameStatus", false);
            List<NutMap> cMap = new ArrayList<>();
            for (Channel channel : channels) {
                NutMap cNutmp = new NutMap();
                cNutmp.setv("channelId", channel.getId());
                cNutmp.setv("channelName", channel.getChannelName());
                cNutmp.setv("channelStatus", false);
                cMap.add(cNutmp);
            }
            nutMap.setv("channels", cMap);
            listFinal.add(nutMap);
        }

        List<ChannelGame> channelGameList = channelGameService.query(Cnd.where("user_id", "=", user.getId()));
        for (NutMap map : listFinal) {
            for (ChannelGame channelGame : channelGameList) {
                String channelGameGameId = channelGame.getGameId();
                String gameId = map.get("gameId") + "";
                //如果
                if (channelGameGameId.equals(gameId)) {
                    map.put("gameStatus", true);
                    List<NutMap> allChannels = map.getAsList("channels", NutMap.class);
                    if (allChannels != null || allChannels.size() > 0) {
                        for (NutMap chMap : allChannels) {
                            long channelId = channelGame.getChannelId();
                            long allchannelId = chMap.getLong("channelId", 0);
                            //如果渠道ID与用户拥有的渠道ID相同
                            if (channelId == allchannelId) {
                                chMap.setv("channelStatus", true);
                                chMap.setv("channelAndGameId", channelGame.getId());
                            }

                        }

                    }


                }

            }


        }

        return ServiceUtils.success("成功获取用户游戏/渠道数据", listFinal);
    }


    @GetMapping("getUserChannel")
    @ApiOperation(value = "用户的游戏下的渠道权限信息")
    /**
     * get请求  参数 userId ：用户ID
     *              gameIds:被选中的游戏ID集合  采用id-id-id
     */
    public Object getUserChannel(@RequestParam("userId") @ApiParam("用户ID") Integer userId, @RequestParam("gameIds") @ApiParam("游戏ID") String gameIds) {
        if (userId == null || Strings.isBlank(gameIds)) {
            return Result.fail("请选择要设置的用户");
        }
        User user = userService.fetch(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        String[] gameIdsList = gameIds.split("-");
        List<Game> games = gameService.queryAll();
        List<Channel> channels = channelService.queryAll();
        //创建游戏/渠道默认false数组 list0
        //获取用户游戏/渠道数据， 将匹配上的list0对象变为true
        List<NutMap> listFinal = new ArrayList<>();
        List<ChannelGame> channelGameList = channelGameService.query(Cnd.where("user_id", "=", user.getId()));
        for (Game game : games) {

            for (String s : gameIdsList) {
                if (s != null && s.equals(game.getId() + "")) {
                    NutMap nutMap = new NutMap();
                    nutMap.put("gameId", game.getId());
                    nutMap.put("gameName", game.getGameName());
                    nutMap.put("gameStatus", false);
                    nutMap.put("realGames", new ArrayList<Long>());
                    List<NutMap> cMap = new ArrayList<>();
                    for (Channel channel : channels) {
                        NutMap cNutmp = new NutMap();
                        cNutmp.setv("channelId", channel.getId());
                        cNutmp.setv("channelName", channel.getChannelName());
                        cNutmp.setv("channelStatus", false);
                        cMap.add(cNutmp);
                    }
                    nutMap.setv("channels", cMap);
                    listFinal.add(nutMap);
                }

            }
        }

//        List<ChannelGame> channelGameList = channelGameService.query(Cnd.where("user_id", "=", user.getId()));
        for (NutMap map : listFinal) {
            for (ChannelGame channelGame : channelGameList) {
                String channelGameGameId = channelGame.getGameId();
                String gameId = map.get("gameId") + "";
                //如果
                if (channelGameGameId.equals(gameId)) {
                    map.put("gameStatus", true);
                    List<NutMap> allChannels = map.getAsList("channels", NutMap.class);
                    if (allChannels != null || allChannels.size() > 0) {
                        for (NutMap chMap : allChannels) {
                            long channelId = channelGame.getChannelId();
                            long allchannelId = chMap.getLong("channelId", 0);
                            //如果渠道ID与用户拥有的渠道ID相同
                            if (channelId == allchannelId) {
                                List<Long> realGames = map.getAsList("realGames", Long.class);
                                realGames.add(chMap.getLong("channelId"));
                                chMap.setv("channelStatus", true);
                                chMap.setv("channelAndGameId", channelGame.getId());
                            }
                        }
                    }
                }
            }
        }

        return ServiceUtils.success("成功获取用户游戏/渠道数据", listFinal);
    }


    /**
     * 重置密码
     *
     * @param user 用户信息
     * @return
     */
    @PostMapping(value = "resetPassword")
    @SINORequiresPermissions(InstallPermission.USER_EDIT)
    @ApiOperation(value = "重置用户密码")
    public Result resetPassword(@RequestBody User user) {
        user.setPassword(SINOCredentialsMatcher.password(user.getName(), user.getPassword()));// 密码密文转换
        return userService.updateFields(user, "password") != 1 ? Result.fail("保存用户失败!") : Result.success().addData("user", user);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("delete/{id}")
    @SINORequiresPermissions(InstallPermission.USER_DELETE)
    @ApiOperation(value = "删除用户")
    public Result delete(@PathVariable("id") @ApiParam("待删除用户id") long id) {
        return userService.delete(id) == 1 ? Result.success() : Result.fail("删除用户失败!");
    }

    /**
     * 用户详情
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("{id}")
    @SINORequiresPermissions(InstallPermission.USER_DETAIL)
    @ApiOperation(value = "用户详情")
    public Result detail(@PathVariable("id") @ApiParam("用户id") long id) {
        return Result.success().addData("user", userService.fetch(id));
    }

    /**
     * 获取用户的角色信息
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("role/{id}")
    @SINORequiresPermissions(InstallPermission.USER_ROLE)
    @ApiOperation(value = "用户角色授权信息")
    public Result roleInfo(@PathVariable("id") @ApiParam("用户id") int id) {
        return Result.success().addData("infos", userService.findRolesWithUserPowerdInfoByUserId(id));
    }

    /**
     * 获取用户的权限信息
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("permission/{id}")
    @SINORequiresPermissions(InstallPermission.USER_GRANT)
    @ApiOperation(value = "用户权限信息")
    public Result permissionInfo(@PathVariable("id") @ApiParam("用户id") int id) {
        return Result.success().addData("infos", userService.findPermissionsWithUserPowerdInfoByUserId(id));
    }

    /**
     * 为用户设置角色
     *
     * @param dto
     * @return
     */
    @PostMapping("/grant/role")
    @SINORequiresPermissions(InstallPermission.USER_ROLE)
    @ApiOperation("为用户设置角色")
    public Result grantRole(@RequestBody GrantDTO dto) {
        return userService.setRole(dto.getGrantIds(), dto.getId());
    }

    /**
     * 为用户设置权限
     *
     * @param dto
     * @return
     */
    @PostMapping("/grant/permission")
    @SINORequiresPermissions(InstallPermission.USER_GRANT)
    @ApiOperation("为用户设置权限")
    public Result grantPermission(@RequestBody GrantDTO dto) {
        return userService.setPermission(dto.getGrantIds(), dto.getId());
    }

    /**
     * 登录
     *
     * @param
     * @param session 会话
     * @return 登录结果
     */

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public Result login(@RequestBody UserLoginDto userLoginDto, @ApiIgnore HttpSession session, HttpServletResponse resp) {
        if (Strings.equalsIgnoreCase(userLoginDto.getCaptcha(), Strings.safeToString(session.getAttribute(BootNutzVueApplication.CAPTCHA_KEY), ""))) {

            System.out.println("+++++++++++" + userLoginDto.getPassword());
            System.out.println(Md5Util.generatePassword(userLoginDto.getPassword() + "    ***************12345678"));
            Result result = shiroUserService.login(userLoginDto.getUserName(), userLoginDto.getPassword(), Lang.getIP(request()));
//            Result result = shiroUserService.login(userLoginDto.getUserName(), Md5Util.generatePassword(userLoginDto.getPassword()), Lang.getIP(request()));
            if (result.isSuccess()) {
                // 登录成功处理
                _putSession(BootNutzVueApplication.USER_KEY, result.getData().get("loginUser"));
                if (userLoginDto.isRememberMe()) {
                    NutMap data = NutMap.NEW();
                    data.put("user", userLoginDto.getUserName());
                    data.put("password", userLoginDto.getPassword());
                    data.put("rememberMe", userLoginDto.getPassword());
                    _addCookie("kerbores", DES.encrypt(Json.toJson(data)), 24 * 60 * 60 * 365);
                }
                return result
                        .addData("roles", shiroUserService.roleInfos(userLoginDto.getUserName()))
                        .addData("permissions", shiroUserService.permissionInfos(userLoginDto.getUserName()));
            }
            return result;
        } else {
            return Result.fail("验证码输入错误");
        }
    }

    @GetMapping("logout")
    @ApiOperation(value = "退出登录")
    public Result logout(HttpServletResponse response) {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }


}

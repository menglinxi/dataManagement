package club.zhcs.thunder.utils;

import org.nutz.lang.util.NutMap;

/**
 * @author zhangliang
 * @Date 2017/12/28
 */
public class ServiceUtils {
    /**
     * 成功调用
     *
     * @param msg
     * @return
     */
    public static NutMap success(String msg) {
        return new NutMap().setv("operationState", "SUCCESS").setv("msg", msg);
    }

    /**
     * 成功调用,存放成功信息提示和数据
     * 无分页
     * @param msg
     * @return
     */
    public static NutMap success(String msg, Object data) {
        return new NutMap().setv("operationState", "SUCCESS").setv("msg", msg).setv("data", data);
    }

    /**
     * 成功调用,存放成功信息提示和数据
     * 分页
     * @param msg
     * @return
     */
    public static NutMap successPage(String msg, Object data,int totalRow,int pageIndex ,int pageSize ) {
        return new NutMap().setv("operationState","SUCCESS").setv("msg", msg).setv("data", data) .setv("totalRow", totalRow)
                .setv("pageIndex", pageIndex)
                .setv("pageSize", pageSize);
    }




    /**
     * 失败调用
     *
     * @param msg
     * @return
     */
    public static NutMap failed(String msg) {
        return new NutMap().setv("operationState", "FAIL").setv("msg", msg);
    }


    /**
     * 固定返货用户未登录。
     * 懒得每次都写一遍用户未登录
     * @return
     */
    public static NutMap notLogin() {
        return new NutMap().setv("operationState", "FAIL").setv("msg", "用户未登录");
    }
}


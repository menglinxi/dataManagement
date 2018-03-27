import http from '@/http'

export default {
    /**是否显示上传文件按钮
    * @param {Function} success 回调
    */
    canUp(success) {
        http.get('context/canUp',success);
    },
    /**
     * 游戏列表
     * @param {Function} success 回调
     */
    gameList(success) {
        http.get('context/gList',success);
    },
     /**
     * 渠道列表
     * @param {number} gameId 游戏ID
     * @param {Function} success 回调
     */
    channelList(gameId,success) {
   		http.get('context/cList?gameId='+gameId,success);	
   	},
   	/**
     * 数据列表
     * @param {number} pageIndex  
     * @param {number} pageSize 
     * @param {string} beginTime 开始时间
     * @param {string} endTime 结束时间
     * @param {number} gameId 游戏ID
     * @param {number} channelId 渠道ID（必传）
     * @param {Function} success 回调
     */
   	list(pageIndex,pageSize,beginTime,endTime,gameId,channelId,success) {
   		http.get('context/list',{
            pageIndex: pageIndex,
            pageSize: pageSize,
            beginTime: beginTime,
            endTime: endTime,
            gameId: gameId,
            channelId: channelId,
        },success);
   	}
} 
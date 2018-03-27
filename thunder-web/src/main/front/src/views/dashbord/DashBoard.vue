<template>
	<div>
		<br>
		<el-upload class="uploadcsv" ref="upload" :action="uploadFileUrl" :show-file-list="false" :auto-upload="false" :on-success="handleCsvSuccess" :before-upload="beforeCsvUpload" :on-change="handleFileChange" v-show="enableUpLoad">
			<el-button slot="trigger" size="small" type="primary">选取文件</el-button>
			<span>{{fileName}}</span>
			<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到并更新</el-button>
		</el-upload>
		<br>
		<el-row>
			<div class="block">
				<el-col :span="9">
					<el-date-picker v-model="beginTime" type="date" placeholder="输入开始时间" value-format="yyyy-MM-dd">
					</el-date-picker>
					<el-date-picker v-model="endTime" type="date" placeholder="输入结束时间" value-format="yyyy-MM-dd">
					</el-date-picker>
				</el-col>
				<el-col :span="5">
					<el-select v-model="gameId" @change="handleSelectGame">
						<el-option v-for="game in gameList" :key="game.game_id" :label="game.gamename" :value="game.game_id">
						</el-option>
					</el-select>
				</el-col>
				<el-col :span="4" :offset="1" @change="handleSelectChannel">
					<el-select v-model="channelId">
						<el-option v-for="channel in channelList" :key="channel.channelId" :label="channel.channelName" :value="channel.channelId">
						</el-option>
					</el-select>
				</el-col>
				<el-col :span="2" :offset="1">
						<el-button type="primary" icon="el-icon-search" @click="loadList">查询</el-button>
				</el-col>
				 
			</div>
		</el-row>
		<br>
		<el-table :data="tableData" style="width: 100%">
			<el-table-column prop="looktime" label="时间" align="center">
			</el-table-column>
			<el-table-column prop="gameName" label="游戏名称" align="center">
			</el-table-column>
			<el-table-column prop="channelName" label="渠道名称" align="center">
			</el-table-column>
			<el-table-column prop="addNumber" label="用户增加数" align="center">
			</el-table-column>
			<el-table-column prop="addPercent" label="增加环比" align="center">
			</el-table-column>
			<el-table-column prop="keep2" label="次日留存率" align="center">
			</el-table-column>
			<el-table-column prop="keep7" label="七日留存率" align="center">
			</el-table-column>
			<el-table-column prop="payMoney" label="充值金额" align="center">
			</el-table-column>
			<el-table-column prop="payPercent" label="充值环比" align="center">
			</el-table-column>
			<el-table-column prop="clientPercent" label="客户端充值率" align="center">
			</el-table-column>
			<el-table-column prop="clientarppu" label="客户端ARPPU" align="center">
			</el-table-column>
		</el-table>
		<br>
		<div align="center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageIndex" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="totalRow">
			</el-pagination>
		</div>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				uploadFileUrl: '',
				fileName: '',
				totalRow: 0,
				pageIndex: 1,
				pageSize: 10,
				beginTime: '',
				endTime: '',
				gameId: '',
				channelId: '',
				gameList: '',
				channelList: [],
				tableData:[],
				enableUpLoad: false,	//是否可以上传文件更新
			}
		},
		methods: {
			handleSizeChange(val) {
				this.pageSize = val;
				this.pageIndex = 1;
				this.loadList();
			},
			handleCurrentChange(val) {
				this.pageIndex = val;
				this.loadList();
			},
			handleCsvSuccess(res, file) {
				if(res.errors[0]) {
					this.$message.error(res.errors[0]);
				}
			},
			handleFileChange(file, fileList) {
				this.fileName = file.name;
				if(fileList.length > 1) {
					fileList.splice(0, 1);
				}
			},
			beforeCsvUpload(file) {
			},
			submitUpload() {
				this.$refs.upload.submit();
			},
			loadGameList() {
				this.$api.Dashboard.gameList(result => {
					this.gameList = result;
					let obj = {
						"game_id": "",
						"gamename": "所有游戏"
					};
					this.gameList.splice(0, 0, obj);
				});
			},
			handleSelectGame(value) {
				this.channelList = [];
				this.channelId = '';
				if(value != '') {
					this.$api.Dashboard.channelList(value, result => {
						this.channelList = result;
						this.channelId = result[0].channelId;
						this.handleSelectChannel(this.channelId);
					});
				}else{
					this.loadList();
				}
			},
			handleSelectChannel(value) {
				this.channelId = value;
				this.loadList();
			},
			loadList() {
				if (this.channelId == '' && this.gameId != '') {
					this.$message.error('请选择相关渠道');
					return;
				}
				this.$api.Dashboard.list(this.pageIndex,this.pageSize,this.beginTime,this.endTime,this.gameId,this.channelId,result=> {
					this.tableData = result.list;
					this.pageIndex = result.pageIndex;
					this.pageSize = result.pageSize;
					this.totalRow = result.totalRow;
				});
			},
			canUp() {
				this.$api.Dashboard.canUp(result=>{
					this.enableUpLoad = result.enable;
				});
			}
		},	
		created: function() {
			this.uploadFileUrl = baseURL + '/api/context/fileUpload';
			this.loadGameList();
			this.loadList();
			this.canUp();
		}
	}
</script>
<style>

</style>
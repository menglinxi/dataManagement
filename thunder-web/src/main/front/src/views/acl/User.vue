<template>
    <section>
        <el-row>
            <el-col :span="6">
                <el-input placeholder="请输入内容" v-model="searchKey" prefix-icon="el-icon-fa-search">
                    <div slot="append">
                        <el-button type="primary" icon="el-icon-fa-search"
                                   @click=" pager.pager.pageNumber = 1 ;doSearch()"></el-button>
                    </div>
                </el-input>
            </el-col>
            <el-col :span="6" :offset="12">
                <el-button type="primary" icon="el-icon-fa-plus" @click="addUser" size="small">添加用户</el-button>
            </el-col>
        </el-row>
        <el-table :data="pager.dataList" border stripe style="width: 100%">
            <el-table-column prop="id" label="ID" header-align="center" align="center" width="55">
            </el-table-column>
            <el-table-column prop="name" label="登录名" show-overflow-tooltip header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="realName" label="姓名" show-overflow-tooltip header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" :formatter="formatter" show-overflow-tooltip
                             header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="status" label="状态" show-overflow-tooltip header-align="center" align="center">
                <template slot-scope="scope">
                    <el-tag size="small" :type="scope.row.status === 'ACTIVED' ? 'success' : 'danger'" close-transition>
                        {{scope.row.status
                        == 'ACTIVED' ? 'ACTIVED' : 'DISABLED'}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" show-overflow-tooltip header-align="center" align="center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button title="编辑用户" size="mini" type="primary" icon="el-icon-fa-edit"
                                   @click="handleEdit(scope.$index,scope.row)"></el-button>
                        <el-button title="重置密码" size="mini" type="primary" icon="el-icon-fa-lock"
                                   @click="handleReset(scope.$index,scope.row)"></el-button>
                        <el-button title="删除用户" size="mini" type="primary" icon="el-icon-fa-trash"
                                   @click="handleDelete(scope.$index,scope.row)"></el-button>
                        <el-button title="用户角色" size="mini" type="primary" icon="el-icon-fa-fire"
                                   @click="handleGrant(scope.$index,scope.row,'role')"></el-button>
                        <el-button title="用户授权" size="mini" type="primary" icon="el-icon-fa-bolt"
                                   @click="handleGrant(scope.$index,scope.row,'permission')"></el-button>
                        <el-button title="渠道设置" size="mini" type="primary" icon="el-icon-setting"
                                   @click="handleChannel(scope.$index,scope.row)" style="height: 30px"></el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
        <el-row>
            <el-col :span="6" :offset="18">
                <el-pagination background small style="float:right" layout="prev, pager, next"
                               :total="pager.pager.recordCount" :page-size="pager.pager.pageSize"
                               :current-page.sync="pager.pager.pageNumber" v-show="pager.pager.pageCount != 0"
                               @current-change="changePage">
                </el-pagination>
            </el-col>
        </el-row>
        <!-- 弹框区域-->
        <el-dialog :title="user.id == 0 ? '添加用户' : '编辑用户' " :visible.sync="addEditShow" width="30%">
            <el-form :model="user" :rules="$rules" ref="userForm">
                <el-form-item label="登录名" :label-width="formLabelWidth" prop="name">
                    <el-input v-model="user.name" auto-complete="off" suffix-icon="el-icon-fa-user"
                              placeholder="请填写登录名"></el-input>
                </el-form-item>
                <el-form-item label="真实姓名" :label-width="formLabelWidth" prop="realName">
                    <el-input v-model="user.realName" auto-complete="off" suffix-icon="el-icon-fa-vcard"
                              placeholder="请填写用户真实姓名"></el-input>
                </el-form-item>
                <el-form-item label="密码" :label-width="formLabelWidth" prop="password"
                              v-show="user.password != '00000000'">
                    <el-input type="password" v-model="user.password" auto-complete="off"
                              suffix-icon="el-icon-fa-lock" placeholder="请填写用户密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" :label-width="formLabelWidth" prop="rePassword"
                              v-show="user.rePassword != '00000000'">
                    <el-input type="password" v-model="user.rePassword" auto-complete="off"
                              suffix-icon="el-icon-fa-lock" placeholder="请再次填写用户密码"></el-input>
                </el-form-item>
                <el-form-item label="电话" :label-width="formLabelWidth" prop="phone">
                    <el-input v-model="user.phone" auto-complete="off" suffix-icon="el-icon-fa-phone"
                              placeholder="请填写用户电话号码"></el-input>
                </el-form-item>
                <!-- <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
                    <el-input v-model="user.email" auto-complete="off" suffix-icon="el-icon-fa-envelope"
                              placeholder="请填写用户Email地址"></el-input>
                </el-form-item> -->
                <el-form-item label="用户状态" :label-width="formLabelWidth">
                    <el-switch v-model="user.status" active-value="ACTIVED" inactive-value="DISABLED">
                    </el-switch>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addEditShow = false ; user = {status:'ACTIVED'}">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateUser('userForm')">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="重置密码" :visible.sync="resetShow" width="30%">
            <el-form :model="user" :rules="$rules" ref="resetForm">
                <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
                    <el-input type="password" v-model="user.password" auto-complete="off"
                              suffix-icon="el-icon-fa-lock"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetShow = false">取 消</el-button>
                <el-button type="primary" @click="resetPassword('resetForm')">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog :title="type=='role' ? '设置角色' : '设置权限'" :visible.sync="grantShow" :width="dialogWidth">
            <template>
                <el-transfer v-model="selected" :data="options" :titles="['待选项', '已选项']" filterable></el-transfer>
            </template>
            <div slot="footer" class="dialog-footer">
                <el-button @click="grantShow = false">取 消</el-button>
                <el-button type="primary" @click="grant">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="渠道设置" :visible.sync="channelShow" :width="dialogWidth">
            <template>
                <div >
                    <span style="line-height: 22px;font-size: 16px;color: #303133;">{{user.name}}</span>
                    <br><br>
                    <el-row>
                        <el-col :span="11">
                            <div style="max-height: 400px;overflow: scroll">      
                                <el-checkbox-group v-model="checkList" @change="handleCheckdChannelDetail"> 
                                    <el-checkbox v-for="channel in channelInfoList" :label="channel.gameId" :key="channel.gameId" style="display: block;margin-left: 0px;">{{channel.gameName}}</el-checkbox>
                                </el-checkbox-group>
                            </div>
                        </el-col>
                        <el-col :span="11" :offset="2">
                            <div style="max-height: 400px;overflow: scroll">
                            <div v-for="channel in channelDetailList">
                                <span style="line-height: 22px;font-size: 16px;color: #303133;">{{channel.gameName}}渠道:</span>
                                <!-- @remove-tag = "handleRemoveTag(channel.channels,$event.value)" -->
                                <!-- @change = "handleSelectChange(channel.channels,channel.realGames,$event.value)" -->
                                <el-select v-model="channel.realGames" 
                                    multiple
                                    placeholder="请选择相关渠道" 
                                    style="margin: 5px 0px">
                                    <el-option
                                        v-for="item in channel.channels"
                                        :key="item.channelId"   
                                        :label="item.channelName"
                                        :value="item.channelId">
                                    </el-option>
                            </el-select>
                        </div>
                        </div>  
                    </el-col>
                </el-row>
            </div>
        </template>
        <div slot="footer" class="dialog-footer">
            <el-button @click="channelShow = false">取 消</el-button>
            <el-button type="primary" @click="saveChannelInfo">确 定</el-button>
        </div>
    </el-dialog>    

    </section>
</template>
<script>
    import moment from "moment";

    export default {
        data() {
            return {
                searchKey: "",
                pager: {
                    pager: {
                        pageCount: 0,
                        pageNumber: 1,
                        pageSize: 15,
                        recordCount: 0
                    }
                },
                selected: [],
                options: [],
                addEditShow: false,
                resetShow: false,
                grantShow: false,
                channelShow:false,
                type: "role",
                user: {
                    id: 0,
                    name: "",
                    realName: "",
                    status: "ACTIVED",
                    password: "",
                    rePassword: "",
                    phone: "",
                    email: ""
                },
                formLabelWidth: "100px",
                checkList:[],
                channelInfoList:[],
                channelDetailList:[],
                value:[],
            };
        },
        computed: {
            dialogWidth() {
                return 590 * 100 / this.$utils.windowWidth() + "%";
            }
        },
        watch: {
            options: function () {
                this.selected = [];
                this.options.forEach(item => {
                    if (item.selected) {
                        this.selected.push(item.key);
                    }
                });
            }
        },
        methods: {
            grant() {
                let url = "/user/grant/" + this.type;
                let data = {
                    userId: this.user.id,
                    grantIds: this.selected
                };
                this.$api.User.grant(this.user.id, this.type, this.selected, result => {
                    this.$message({
                        type: "success",
                        message: "授权成功!"
                    });
                    window.setTimeout(() => {
                        this.grantShow = false;
                    }, 2000);
                });
            },
            resetPassword(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid) {
                        this.$api.User.resetPassword(
                            this.user.id,
                            this.user.name,
                            this.user.password,
                            result => {
                                this.$message({
                                    type: "success",
                                    message: "重置成功!"
                                });
                                this.resetShow = false;
                            }
                        );
                    } else {
                        return false;
                    }
                });
            },
            handleReset(index, row) {
                this.user.id = row.id;
                this.user.name = row.name;
                this.resetShow = true;
            },
            changePage() {
                if (this.searchKey) {
                    this.doSearch();
                } else {
                    this.loadData();
                }
            },
            doSearch() {
                this.$api.User.search(
                    this.pager.pager.pageNumber,
                    this.searchKey,
                    result => {
                        this.pager = result.pager;
                    }
                );
            },
            checkSame() {
                return this.user.password === this.user.rePassword;
            },
            addUser() {
                this.addEditShow = true;
                this.user = {
                    id: 0,
                    name: "",
                    realName: "",
                    status: "ACTIVED",
                    password: "",
                    rePassword: "",
                    phone: "",
                    email: ""
                };
            },
            saveOrUpdateUser(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid && this.checkSame()) {
                        var callback = result => {
                            this.changePage();
                            this.addEditShow = false;
                        };
                        this.user.id
                            ? this.$api.User.update(this.user, callback)
                            : this.$api.User.save(this.user, callback);
                    } else {
                        return false;
                    }
                });
            },
            saveChannelInfo(){

                this.$api.User.userSaveChannel(
                    this.user.id,
                    this.channelDetailList,
                    result => {
                        this.channelShow = false;
                    }
                );
            },
            formatter(row, column) {
                return moment(row.createTime, "YYYY-MM-DD hh:mm:ss").format(
                    "YYYY年MM月DD日"
                );
            },
            handleEdit(index, row) {
                let id = this.pager.dataList[index].id;
                this.user = row;
                this.user.password = "00000000";
                this.user.rePassword = "00000000";
                this.addEditShow = true;
            },
            handleGrant(index, row, type) {
                this.user.id = row.id;
                this.type = type;
                this.$api.User.userGrantInfo(type, row.id, result => {
                    this.options = [];
                    result.infos.forEach((item, index) => {
                        this.options.push({
                            key: item.id,
                            label: item.description,
                            selected: item.selected
                        });
                    });
                    this.grantShow = true;
                });
            },
            handleChannel(index, row){
                this.checkList = [];
                this.checkIdList = [];
                this.user = row;
                this.channelShow = true;
                this.$api.User.userChannelInfo(row.id,result => {
                    this.channelInfoList = result;
                    for (var i = result.length - 1; i >= 0; i--) {
                        if(result[i].gameStatus == true){
                            this.checkList.push(result[i].gameId);
                        }
                    }
                    if(this.checkList.length>0){
                        this.handleChannelDetail(row.id,this.checkList,'total');
                    }
                });
            },
            handleChannelDetail(id,gameIds,type){

                this.$api.User.userChannelDetail(id,gameIds,result => {
                    if (type == 'total') {
                        this.channelDetailList = result;    
                    }else if (type == 'single'){
                        this.channelDetailList.push(result[0]);
                    }
                });
            },
            handleCheckdChannelDetail(value){
                let checkCount = value.length;
                if (checkCount > this.channelDetailList.length) {
                    this.handleChannelDetail(this.user.id,[value[checkCount-1]],'single');
                }else if(checkCount < this.channelDetailList.length) {
                    for (var i = this.channelDetailList.length - 1; i >= 0; i--) {
                        let gameid = this.channelDetailList[i].gameId;
                        if (value.indexOf(gameid) == -1) {
                            this.channelDetailList.splice(i,1); 
                            break;
                        }
                    }
                }
            },
            handleSelectChange(list,change,value){
                // if (change.length>0) {
                //     let channelId = change[change.length-1];
                //     for (var i = list.length - 1; i >= 0; i--) {
                //         let tmpChannelId = list[i].channelId;
                //         if (tmpChannelId == channelId) {
                //             list[i].channelStatus = true;
                //             break;
                //         }
                //     }
                // }
            },
            handleRemoveTag(list,value){
                // for (var i = list.length - 1; i >= 0; i--) {
                //     let tmpChannelId = list[i].channelId;
                //     if (tmpChannelId == value) {
                //         list[i].channelStatus = false;
                //         break;
                //     }
                // }
            },
            handleDelete(index, row) {
                this.$confirm("确认删除用户?", "删除确认", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    this.$api.User.delete(row.id, result => {
                        this.$message({
                            type: "success",
                            message: "删除成功!"
                        });
                        window.setTimeout(() => {
                            if (this.searchKey) {
                                this.doSearch();
                            } else {
                                this.loadData();
                            }
                        }, 2000);
                    });
                });
            },
            loadData() {
                this.$api.User.list(this.pager.pager.pageNumber, result => {
                    this.pager = result.pager;
                });
            }
        },
        mounted: function () {
            this.loadData();
        }
    };
</script>

<style scoped>

</style>
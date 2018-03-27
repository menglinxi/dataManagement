var validateMobile = (rule, value, callback) => {
    if (!value) {
        callback(new Error('请输入手机号'));
    } else if (!/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57]|17[678])[0-9]{8}$/.test(value)) {
        callback(new Error('请输入正确的手机号码'));
    } else {
        callback();
    }
};
var validateEmail = (rule, value, callback) => {
    if (/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(value)) {
        callback();
    } else {
        callback(new Error("请输入正确的邮箱地址"));
    }
};
export default {
    mobile: [{
        validator: validateMobile,
        trigger: "blur"
    }],
    name: [{
        required: true,
        message: "请输入名称",
        trigger: "blur"
    }],
    realName: [{
        required: true,
        message: "请输入真实姓名",
        trigger: "blur"
    }],
    password: [{
        required: true,
        message: "请输入密码",
        trigger: "blur"
    },
        {
            min: 3,
            max: 15,
            message: "请输入3到15位密码",
            trigger: "blur"
        }
    ],
    rePassword: [{
        required: true,
        message: "请输入密码",
        trigger: "blur"
    },
        {
            min: 3,
            max: 15,
            message: "请输入3到15位密码",
            trigger: "blur"
        },
    ],
    phone: [{
        required: true,
        message: "请输入手机号",
        trigger: "blur"
    },
        {
            validator: validateMobile,
            trigger: "blur"
        }
    ],
    email: [{
        required: true,
        message: "请输入电子邮箱",
        trigger: "blur"
    },
        {
            validator: validateEmail,
            trigger: "blur"
        }
    ],
    description: [{
        required: true,
        message: "请输入描述信息",
        trigger: "blur"
    }],
    groupId: [{
        type: "number",
        required: true,
        message: "请选择数据分组",
        trigger: "blur"
    }],
    value: [{
        required: true,
        message: "请输入码本数据值",
        trigger: "blur"
    }]
}
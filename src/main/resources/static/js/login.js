/**
 * @className: login
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/18 22:50
 * @version: 1.0
 **/
// 页面加载
$(function () {
    // 一加载获取焦点
    $("#username").textbox('textbox').focus();
});

// 登录处理
let login = () => {
    if ($("#username").val() === ""){
        $.messager.alert("温馨提示","请输入账号");
    } else if ($("#password").val() === ""){
        $.messager.alert("温馨提示","请输入密码");
    } else {
        // ajax异步提交
        $.ajax({
            url: "/login.do",
            type: "POST",
            data: $("#loginForm").serializeArray()
        }).then(data => {
            if (data.flag === 'ok'){
                location.href = '/index.html';
            }else {
                $.messager.alert("温馨提示","用户名或密码错误");
            }

        },(err) => {
            location.href = '/error/500';
            console.log(err);
        });
    }
};

// 绑定enter回车键
document.onkeydown = ev => {
  let event = ev || window.event;
  let code = event.keyCode || event.which || event.charCode;
  if (code === 13){
      login();
  }
};
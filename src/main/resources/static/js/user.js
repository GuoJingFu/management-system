/**
 * @className: user
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/20 22:08
 * @version: 1.0
 **/
let url = null;

// 页面加载
$(function () {

    $("#searchcondition").combobox({
        width: 180,
        editable: false,
        onShowPanel: function () {  // 面板展开时触发
            $(this).combobox('panel').height("auto");
        }
    });


    $("#roleid").combobox({
        width: 180,
        editable: false,
        onShowPanel: function () {  // 面板展开时触发
            $(this).combobox('panel').height("auto");
        }
    });


    // 加载数据
    $("#dg").datagrid({
        url: '/user/list',
        queryParams: {},  // 发送的额外参数
        title: "用户列表",
        rownumbers: true, // 显示带有行号的列
        fit: true,  // 是否填充
        toolbar: "#tb",
        singleSelect: true, // 只允许选中一行
        pagination: true, // 是否显示分页工具条
        pageSize: 20,
        pageList: [10,20],
        columns: [
            [
                {field: 'userid', title: '人员编号', width: 50, align: 'center', halign: 'center', hidden: 'true'},
                {field: 'username', title: '账号', width: '25%', align: 'center', halign: 'center'},
                {field: 'password', title: '密码', width: '25%', align: 'center', halign: 'center'},
                {
                    field: 'role',
                    title: '角色名称',
                    width: '25%',
                    align: 'center',
                    halign: 'center',
                    formatter: (value,row,index) => {
                        if (value.rolename){
                            return value.rolename;
                        }else {
                            return '';
                        }
                    }
                },
                {
                    field: 'operation',
                    title: '操作',
                    align: 'center',
                    halign: 'center',
                    width: '25%',
                    formatter: (value,row,index) => {
                        let temp = '';
                        if ($("#hdupdate").css("display")){
                            temp += '<a href="#" class="linkbutton-update" ' +
                                'onclick="openModifyDialog(' + index + ')"></a>' + '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        }

                        if ($("#hddelete").css("display")) {
                            temp += '<a href="#" class="linkbutton-delete" ' +
                                'onclick="deleteHandler(' + index + ')"></a>';
                        }
                        return temp;
                    }
                }
            ]
        ],
        onLoadSuccess: () => { // 当数据加载成功时触发
            $(".linkbutton-update").linkbutton({
                plain: true,
                iconCls: 'icon icon-edit'
            });

            $(".linkbutton-delete").linkbutton({
                plain: true,
                iconCls: 'icon icon-delete'
            });
        }
    });

    // 编辑对话框的角色下拉框动态数据展示
    $.ajax({
        url: '/role/findAll.do',
        type: 'GET',
        data: {},
        dataType: 'json'
    }).then(data => {
       let dataList = [];
       // 先清空
       $("#roleid").empty();
       $.each(data.list,(i,e) => {
          dataList.push({
              "value": data.list[i].roleid,
              "text": data.list[i].rolename,
          });
       });
       $("#roleid").combobox({"data":dataList});
    });
});

/**
 * 加载全部
 */
let loadAll = () => {

    // 查询条件还原为默认状态
    $("#searchcondition").combobox('setValue','username');
    $("#searchcontent").val("");

    // 表格数据刷新
    let param = {};
    $("#dg").datagrid('load',param);
};

/**
 * 点击搜索按钮
 */
let searchHandler = () => {
    // 设置参数
    let param = {
        searchcondition: $('#searchcondition').combobox('getValue'),
        searchcontent: $('#searchcontent').val()
    };
    // 根据参数查询
    $("#dg").datagrid('load',param);
};

// 绑定enter回车键
document.onkeydown = ev => {
    let event = ev || window.event;
    let code = event.keyCode || event.which || event.charCode;
    if (code === 13){
        searchHandler();
    }
};


/**
 * 打开编辑对话框
 * @param index
 */
let openModifyDialog = index => {

    // 根据索引获取表格中的行
    $("#dg").datagrid('selectRow',index);
    let row = $("#dg").datagrid("getSelected");

    // 打开编辑对话框
    $("#dlg").dialog("open").dialog("setTitle","编辑用户信息");

    // 在对话框的表单中展示数据
    $("#fm").form("load",row);

    // 设置复选框值
    $("#roleid").combobox("setValue",row.role.roleid);

    // 把 url 传过去
    url = "save?userid=" + row.userid;

};

/**
 * 重置表单内容
 */
let resetForm = () => {
    $("#fm")[0].reset();
};

/**
 * 关闭编辑对话框
 */
let closeDialog = () => {
  $("#dlg").dialog('close');
  resetForm();
};

/**
 * 打开添加对话框
 */
let openAddDialog = () => {

    // 重置表单内容
    resetForm();

    $("#dlg").dialog({
        modal: true,
        title: '添加用户信息'
    });

    // 打开对话框
    $("#dlg").dialog('open');

    url = "/user/save"
};

/**
 * 新增按钮
 */
let saveHandler = () => {
  $("#fm").form('submit',{
      url: url,
      onSubmit: function() {
          return $(this).form("validate");
      },
      success: data => {
          let result = eval('(' + data + ')');
          if (result.flag === 'ok'){
              $.messager.alert("系统提示", "保存成功！");
              // 重置表单内容
              resetForm();
              $("#dlg").dialog("close");
              // 数据重新刷新
              $("#dg").datagrid("reload");
          }else {
              $.messager.alert("系统提示", "保存失败！");
              return;
          }
      }
  });
};


/**
 * 执行删除操作
 * @param index
 */
let deleteHandler = index => {

    // 根据索引获取表格中的行
    $("#dg").datagrid('selectRow',index);
    let row = $("#dg").datagrid("getSelected");

    $.messager.confirm("系统提示", "您确定要删除这条数据吗？", r => {
        if (r){
            $.ajax({
               url: '/user/delete',
               type: 'DELETE',
               data: {userid: row.userid},
               dataType: 'json'
            }).then(data => {
                if (data.success) {
                    $.messager.alert("系统提示", "数据已成功删除！");
                    $("#dg").datagrid("reload");
                } else {
                    $.messager.alert("系统提示", "数据删除失败，请联系工作人员！");
                }
            });
        }
    });

};


/**
 * @className: role
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/21 11:31
 * @version: 1.0
 **/

let url = null;
let rowIndex = null;

// 页面加载
$(function () {

    // 加载右侧加载树
    $("#resourceTree").tree({
        url: '/resource/tree',
        animate: true,
        lines: true,
        checkbox: true
    });

    // 加载左侧表格数据
    $("#dg").datagrid({
        url: '/role/list',
        queryParams: {},
        title: '角色列表',
        rownumbers: true,
        fit: true,
        toolbar: '#tb',
        singleSelect: true,
        pagination: true,
        pageSize: 10,
        pageList: [5,10],
        columns: [
            [
                {field: 'roleid', title: '角色编号', width: 50, align: 'center', halign: 'center', hidden: 'true'},
                {field: 'rolename', title: '角色名称', width: '50%', align: 'center', halign: 'center'},
                {
                    field: 'operation', title: '操作', align: 'center', halign: 'center', width: '50%',
                    formatter: function (value,row,index) {
                        let temp = '';
                        if ($("#hdupdate").css("display")) {
                            temp += '<a href="#" class="linkbutton-update" ' +
                                'onclick="openModifyDialog(' + index + ')"></a>' + '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        }

                        if ($("#hddelete").css("display")) {
                            temp += '<a href="#" class="linkbutton-delete" ' +
                                'onclick="deleteHandler(' + index + ')"></a>' + '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        }

                        if ($("#hdconfig").css("display")) {
                            temp += '<a href="#" class="linkbutton-config" ' +
                                'onclick="relResources(' + index + ')"></a>';
                        }

                        return temp;
                    }

                }
            ]
        ],
        onClickRow: function (index,row) { // 点击一行时触发
            setResources(row);
        },
        onLoadSuccess: function () { // 加载成功后触发
            $(".linkbutton-update").linkbutton({plain: true, iconCls: 'icon icon-edit'});
            $(".linkbutton-delete").linkbutton({plain: true, iconCls: 'icon icon-delete'});
            $(".linkbutton-config").linkbutton({plain: true, iconCls: 'icon icon-config'});

            $("#dg").datagrid('selectRow',0);
            //获取选中的数据
            let row = $("#dg").datagrid("getSelected");
            setResources(row);
        }
    });

});


/**
 * 设置资源树
 * @param row
 */
let setResources = function (row) {
  $.ajax({
      url: '/role/resource/match',
      type: 'POST',
      data: {roleid: row.roleid},
      dataType: 'json'
  }).then(function (data) {
      if (data instanceof Array){
          // 回显数据全部清除
          let rootNodes = $("#resourceTree").tree('getRoots'); // 获取根节点
          for (let i = 0; i < rootNodes.length; i++){
              // 找到指定的节点并返回该节点对象
              let node = $("#resourceTree").tree('find',rootNodes[i].id);
              // 把指定节点设置为未勾选
              $("#resourceTree").tree('uncheck',node.target);
          }

          // 重新勾选树
          let len = data.length;
          for (let i = 0;i < len; i++){
              // 找到指定的节点并返回该节点对象
              let node = $("#resourceTree").tree('find',data[i]);
              if ($("#resourceTree").tree('isLeaf',node.target)){
                  // 把指定节点设置为未勾选
                  $("#resourceTree").tree('check',node.target);
              }
          }
      }else {
          $.messager.alert("系统提示","模板加载失败,请联系工作人员!");
      }
  });
};


/**
 * 关联资源树
 * @param row
 */
let relResources = function (index) {

    // 根据索引选择一行
    $("#dg").datagrid('selectRow',index);

    //根据索引获取记录
    let row = $("#dg").datagrid('getSelected');

    let selectArr = getTreeSelected($("#resourceTree"));

    let resourceids = [];
    for (let i = 0; i < selectArr.length; i++){
        resourceids[i] = selectArr[i].id;
    }

    parent.$.messager.confirm({
        title: '关联资源',
        msg: '确定要进行数据关联吗？',
        draggable: false,
        fn: function (bool) {
            if (bool){
                $.ajax({
                    url: '/role/relResources',
                    type: 'POST',
                    data: {roleid:row.roleid,resourceids: resourceids.toString()},
                    dataType: 'json'
                }).then(function (data) {
                    if (data){
                        $.messager.alert("系统提示","关联成功！");
                        // 回显数据 先清除
                        let rootNodes = $("#resourceTree").tree('getRoots');
                        for (let i = 0; i < rootNodes.length; i++) {
                            let node = $("#resourceTree").tree('find',rootNodes[i].id);
                            $("#resourceTree").tree('uncheck',node.target);
                        }

                        // 回显数据 后重新勾选
                        let len = resourceids.length;
                        for (let i = 0; i < len;i++){
                            let node = $('#resourceTree').tree('find', resourceids[i]);
                            if ($('#resourceTree').tree('isLeaf', node.target)) {
                                // 只绑定叶子节点，父节点都根据叶子节点的状态同步变化
                                $('#resourceTree').tree('check', node.target);
                            }
                        }
                    }else {
                        $.messager.alert("系统提示", "关联失败！");
                    }
                });
            }
        }
    });
};

/**
 * 获取树所选节点，以数据数组
 * @param targetObj
 */
let getTreeSelected = function (targetObj) {
    let nodes = targetObj.tree('getChecked');
    let arr = [];
    for (let i = 0;i < nodes.length;i++){
        arr.push(nodes[i]);
        fn(nodes[i]);
    }
    return arr;

    // 内部递归函数
    function fn(e) {
        let parent = targetObj.tree('getParent',e.target);
        if (parent === null){
            return;
        }
        if (isExistItem(parent)){
            return;
        }
        arr.push(parent);
        fn(parent);
    }

    // 验证节点是否已存在数组中
    function isExistItem(node) {
        let flag = false;
        for (let i = 0; i < arr.length; i++){
            if (arr[i] === node){
                flag = true;
                break;
            }
        }
        return flag;
    }
};

/**
 * 加载全部
 */
let loadAll = () => {
    // 查询条件还原为默认状态
    $("#searchcontent").val("");

    //表格重新加载
    let param = {};
    $("#dg").datagrid("load",param);
};

/**
 * 搜索按钮
 */
let searchHandler = () => {
    let param = { searchcontent: $('#searchcontent').val() };
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
 * 打开角色新增对话框
 */
let openAddDialog = () => {
    rowIndex = null;
    resetForm();

    $("#dlg").dialog({
        modal: true,
        title: '添加角色信息'
    });
    $("#dlg").dialog("open");

    url = "/role/save";
};

/**
 * 打开角色修改对话框
 */
let openModifyDialog = () => {
    let selectedRows = $("#dg").datagrid("getSelections");

    if (selectedRows.length === 0){
        $.messager.alert("系统提示","请选择要修改的数据");
        return;
    }

    let row = selectedRows[0];
    rowIndex = $("#dg").datagrid("getRowIndex",selectedRows[0]);
    $("#dlg").dialog({
       modal: true,
       title: '编辑角色信息'
    });

    $("#dlg").dialog("open");
    $("#fm").form('load',row);

    url = "/role/save?roleid=" + row.roleid;
};

/**
 * 关闭对话框
 */
let closeDialog = () => {
    $("#dlg").dialog("close");
    resetForm();
};


/**
 * 重置新增表单
 */
let resetForm = () => {
    $("#fm").form('clear');
};

/**
 * 新增
 */
let saveHandler = () => {
    $("#fm").form("submit",{
       url: url,
       onSubmit: function () {
           return $(this).form("validate");
       },
        success: function (data) {
            let result = eval('(' + data + ')');
            if (result.flag === 'ok'){
                $.messager.alert("系统提示", "保存成功！");
                resetForm();
                $("#dlg").dialog("close");
                $("#dg").datagrid("reload");
            }else {
                $.messager.alert("系统提示", "保存失败！");
                return;
            }
        }
    });
};

/**
 * 删除
 */
let deleteHandler = () => {
    // 获取所有的行
    let selectedRows = $("#dg").datagrid("getSelections");
    if (selectedRows.length === 0){
        $.messager.alert("系统提示", "请选择要删除的数据");
        return;
    }

    $.messager.confirm("系统提示", "您确定要删除这<font color=red>" + selectedRows.length + "</font>条数据吗？", function (r) {
        if (r){
            $.ajax({
                url: '/role/delete',
                type: 'POST',
                data: { roleid: selectedRows[0].roleid },
                dataType: 'json'
            }).then(function (result) {
                if (result.success === "true") {
                    $.messager.alert("系统提示", "数据已成功删除！");
                    $("#dg").datagrid("reload");
                } else if (result.success === "false") {
                    $.messager.alert("系统提示", "数据删除失败，请联系工作人员！");
                } else {
                    $.messager.alert("系统提示", result.success);
                }
            });
        }
    });
};


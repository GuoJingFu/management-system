/**
 * @className: index
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/19 0019 21:45
 * @version: 1.0
 **/
// 页面加载
$(function () {

    // 初始化 accordion
    $("#nav").accordion({
        fillSpace: true,
        fit: true,
        border: false,
        animate: false
    });

    // 加载左侧Accordion菜单
    $.ajax({
        url: '/loadResource',
        type: 'GET',
        data: {resourcepid: 0},
        dataType: 'json'
    }).then(data => {
        // 1.先删除左侧Accordion菜单的内容
        let panels = $("#nav").accordion('panels');
        let titles = '';
        if (panels){
            $.each(panels,(i,e) => {
                console.log(i);
                let title = panels[i].panel("options").title;
                titles += title + ',';
            });
        }
        let arr_title = titles.split(',');
        for (let i = 0;i < arr_title.length; i++){
            if (arr_title[i] !== ""){
                $("#nav").accordion("remove",arr_title[i]);
            }
        }

        // 2.重新添加左侧Accordion菜单的内容
        $.each(data,(i,e) => {
            let content = '';
            content += '<div style="padding:10px;"><ul name="'+e.resourcename+'">';
            $.each(e.children, (index, m) => {
                content += '<li><a href="#" onclick="addTab(\'' + m.resourcename + '\', \'' + m.resourceurl + '\', \'icon ' + m.resourceicon + '\')">'
                    + '<span class="icon ' + m.resourceicon + '">&nbsp;</span>&nbsp;' + m.resourcename + '</a></li>';
            });
            content += '</ul></div>';

            $("#nav").accordion('add',{
               title: e.resourcename,
               select: false,
               content: content
            });

        });

        // 3.默认选中第一个
        $("#nav").accordion('select',0);

    });


    // 监听右键事件，创建右键菜单
    $("#tabs").tabs({
        onContextMenu: (e,title,index) => {
            e.preventDefault();
            if (index > 0){
                $("#menu").menu('show',{
                   left: e.pageX,
                   top: e.pageY
                });
            }
        }
    });

    // 右键点击菜单
    $("#menu").menu({
       onClick: e => {
           closeTab(this,e.name);
       }
    });

});


// 关闭Tab选项卡
let closeTab = (menu,type) => {
    // 获取全部选项卡
    let allTabs = $("#tabs").tabs('tabs');
    // 定义选项卡标题数组
    let allTabtitle = [];

    $.each(allTabs,(i,e) => {
        let opt = $(e).panel('options');
        if (opt.closable){ // 如果关闭这个标签页面板
            allTabtitle.push(opt.title);
        }
    });

    let curTabTitle = $(menu).data("tabTitle");
    let curTabIndex = $("#tabs").tabs("getTabIndex",$("#tabs").tabs("getTab",curTabTitle));
    switch (type) {
        case 1:  // 刷新当前标签页
            let panel = $("#tabs").tabs("getTab",curTabTitle).panel("refresh");
            break;
        case 2:  // 关闭当前标签页
            $("#tabs").tabs("close",curTabIndex);
            return false;
            break;
        case 3:   // 关闭全部标签页
            for (let i = 0;i < allTabtitle.length; i++){
                $("#tabs").tabs("close",allTabtitle[i]);
            }
            break;
        case 4:  // 关闭其他标签页
            for (let i = 0;i < allTabtitle.length; i++){
                if (curTabTitle != allTabtitle[i]){
                    $("#tabs").tabs("close",allTabtitle[i]);
                }
            }
            $("#tabs").tabs("select",curTabTitle);
            break;
        case 5:  // 关闭右侧标签页
            for (let i = curTabIndex;i < allTabtitle.length; i++){
                if (curTabTitle != allTabtitle[i]){
                    $("#tabs").tabs("close",allTabtitle[i]);
                }
            }
            $("#tabs").tabs("select",curTabTitle);
            break;
        case 6:  // 关闭右侧标签页
            for (let i = 0;i < curTabIndex - 1; i++){
                $("#tabs").tabs("close",allTabtitle[i]);
            }
            $("#tabs").tabs("select",curTabTitle);
            break;
        default:
            break;
    }
};

// 退出
let logout = () => {
  $.messager.confirm("系统提示", "您确定要退出系统吗？",result => {
      if (result){
          window.location.href = '/logout';
      }
  })
};



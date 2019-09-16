/**
 * @className: common
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/20 21:45
 * @version: 1.0
 **/
$(function () {

    // 绑定tabs的右键菜单
    $("#tabs").tabs({
        onContextMenu: (e,title) => {
            e.preventDefault();
            $("#tabsMenu").menu('show',{
                left: e.pageX,
                top: e.pageY
            }).data("tabTitle",title);
        }
    });

    // 实例化menu的onClick事件
    $("#tabsMenu").menu({
       onClick: e => {
           CloseTab(this,e.name);
       }
    });


    // 关闭事件的实现
    function CloseTab(menu,type){
        let curTabTitle = $(menu).data("tabTitle");
        let tabs = $("#tabs");

        if (type === "close" && curTabTitle !== "首页"){
            tabs.tabs("close",curTabTitle);
            return;
        }

        let allTabs = tabs.tabs("tabs");
        let closeTabTitle = [];

        $.each(allTabs,() => {
            let opt = $(this).panel("options");
            if (opt.closable && opt.title !== curTabTitle && type === "Other"){
                closeTabTitle.push(opt.title);
            }else if (opt.closable && type === "All") {
                closeTabTitle.push(opt.title);
            }
        });

        for (let i = 0; i< closeTabTitle.length; i++){
            tabs.tabs("close",closeTabTitle[i]);
        }
    }
});

// 打开tab选项卡
let addTab = (title,url,icon) => {
    if ($("#tabs").tabs("exists",title)){
        $("#tabs").tabs("select",title);
    }else {
        let content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
        $("#tabs").tabs('add',{
            title: title,
            content: content,
            closable: true,
            icon: icon
        });
    }
};
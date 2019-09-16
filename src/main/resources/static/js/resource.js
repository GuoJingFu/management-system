/**
 * @className: resource
 * @author: QiuShangLin
 * @description:
 * @date: 2019/7/21 18:17
 * @version: 1.0
 **/
$(function () {

    $("#dg").treegrid({
        url: '/resource/list',
        title: '资源列表',
        method: 'get',
        idField: 'resourceid',
        treeField: 'resourcename',
        rownumbers: true,
        animate: true,
        fitColumns: true,
        resizable: true,
        columns: [
            [
                {title: '资源名称', field: 'resourcename', width: '30%', align: 'left', halign: 'center'},
                {title: '资源图标', field: 'resourceicon', width: '30%', align: 'center', halign: 'center'},
                {title: '资源URL', field: 'resourceurl', width: '40%', align: 'left', halign: 'center'}
            ]
        ]
    })

});
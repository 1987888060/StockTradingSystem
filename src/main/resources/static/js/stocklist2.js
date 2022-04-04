layui.use(['form', 'table', 'miniPage', 'element', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage
        , $ = layui.jquery;


    table.render({
        code:689009,
        elem: '#currentTableId2',
        url: 'http://hq.sinajs.cn/list=sh'+'{code}',
        toolbar: '#toolbarDemo2',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "numbers", width: 50, title: "序号"},
            {field: 'id', width: 140, title: 'ID'},
            {field: 'daima', width: 140, title: '代码号'},
            {field: 'mingcheng', width: 150, title: '名称'},
            {
                field: 'zuoshou', width: 140, title: '昨收', templet: function (data) {
                    return "<span style='color: green'>" + data.zuoshou + "</span>"
                }
            },
            {field: 'shiyinglv', title: '市盈率', minWidth: 120},
            {
                field: 'price', width: 80, title: '价格', templet: function (data) {
                    return "<span style='color: red'>" + data.price + "</span>"
                }
            },
            {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
    table.on('tool(currentTableFilter2)', function (obj) {
        console.log(obj)
        var data = obj.data; // data是数据
        console.log(JSON.stringify(data))
        if (obj.event === 'edit') {
            // 处理Ajax请求，发送到后台进行购买
            layer.confirm('是否要购买', {
                icon: 3,
                title: '提示'
            }, function (index) {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/buy_stock",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function (resu) {
                        console.log(resu)
                    },
                    error: function (err) {
                        console.log(err)
                    }
                })
                layer.close(index)
            })
        }
        if (obj.event === 'sh'){
            // 股票实时分析图
            window.location.href=`http://image.sinajs.cn/newchart/daily/n/sh${data.daima}.gif`
        }
    });
});

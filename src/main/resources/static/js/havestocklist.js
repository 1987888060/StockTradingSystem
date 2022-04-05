layui.use(['form', 'table', 'miniPage', 'element', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage
        , $ = layui.jquery;

    table.render({
        elem: '#currentTableId',
        url: 'http://localhost:8080/have_stock_list',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "numbers", width: 50, title: "序号"},
            {field: 'id', width: 50, title: 'ID'},
            {field: 'daima', width: 120, title: '股票代码'},
            {field: 'mingcheng', width: 150, title: '名称'},
            {field: 'zuoshou', width: 120, title: '昨收'},
            {field: 'shiyinglv', title: '市盈率', width: 120},
            {field: 'price', width: 120, title: '价格'},
            {field: 'head', width: 120, title: '数量',edit: 'text'},
            {title: '操作', minWidth: 200, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
    table.on('tool(currentTableFilter)', function (obj) {
        console.log(ojb)
        var data = obj.data; // data是数据
        console.log(JSON.stringify(data))

        if (obj.event === 'delete') {
            // 处理Ajax请求，发送到后台进行卖出
            if (obj.event === 'buy') {
                //跳转到购买页面
                sessionStorage.setItem("stockcode", data.daima)
                sessionStorage.setItem("stockname", data.mingcheng)
                window.parent.location.href = "/page/index#//page/buy_stock"
            }
        }

        if (obj.event === 'delete1') {
            // 处理Ajax请求，发送到后台进行卖出
            layer.confirm('是否抛出全部',{
                icon:3,
                title:'提示'
            },function (index){
                $.ajax({
                    type: "POST",
                    // url: "http://localhost:8080/sell_stock",
                    url: "http://localhost:8080/stock/upd",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (resu) {
                        // 数据回显成功之后进行表格重载
                        table.reload('currentTableId',{
                            url:"http://localhost:8080/have_stock_list"
                        })
                    }, error: function (err) {
                        console.log(err)
                    }
                })
                layer.close(index)
            })
        }
    });
    table.on('tool(currentTableFilter)', function (obj) {
        var data = obj.data; // data是数据
        console.log(JSON.stringify(data))
        if (obj.event === 'add') {
            // 处理Ajax请求，发送到后台进行卖出
            layer.confirm('是否购买',{
                icon:3,
                title:'提示'
            }
            ,function (index){
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8080/stock/upd",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (resu) {
                        // 数据回显成功之后进行表格重载
                        table.reload('currentTableId',{
                            url:"http://localhost:8080/have_stock_list"
                        })
                    }, error: function (err) {
                        console.log(err)
                    }
                })
                layer.close(index)
            }
            )
        }
    });
    table.on('tool(currentTableFilter)', function (obj) {
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
        if (obj.event === 'sh') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/daily/n/sh${data.daima}.gif`
        }else if(obj.event === 'sh2') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/weekly/n/sh${data.daima}.gif`
        }else if(obj.event === 'sh3') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/monthly/n/sh${data.daima}.gif`
        }else if(obj.event === 'sh4') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/min/n/sh${data.daima}.gif`
        }
    });
});

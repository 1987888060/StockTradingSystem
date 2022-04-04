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
            {field: 'id', width: 120, title: 'ID'},
            {field: 'daima', width: 120, title: '代码号'},
            {field: 'mingcheng', width: 150, title: '名称'},
            {field: 'zuoshou', width: 120, title: '昨收'},
            {field: 'shiyinglv', title: '市盈率', minWidth: 150},
            {field: 'price', width: 120, title: '价格'},
            {field: 'head', width: 150, title: '数量(文本输入)',edit: 'text'},
            {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
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

        if (obj.event === 'dile') {
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

});

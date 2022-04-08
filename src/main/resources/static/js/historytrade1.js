layui.use(['form', 'table', 'miniPage', 'element', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage
        , $ = layui.jquery;

    table.render({
        elem: '#currentTableId',
        url: 'http://localhost:8080/historytrade',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "numbers", width: 100, title: "序号"},
            {field: 'id', width: 130, title: 'ID'},
            {field: 'userid', width: 130, title: '用户ID'},
            {field: 'stockcode', width: 130, title: '股票代码'},
            {field: 'stockname', width: 130, title: '股票名'},
            {field: 'num', width: 100, title: '交易量'},
            {field: 'volume', width: 100, title: '交易额'},
            {field: 'price', width: 100, title: '单价'},
            {field: 'type', width: 100, title: '交易类型(0:卖出,1:买入)'},
            {field: 'time', width: 200, title: '时间'},
            {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    table.on('tool(currentTableFilter)', function (obj) {
        console.log(obj)
        var data = obj.data; // data是数据
        console.log(JSON.stringify(data))

        if (obj.event === 'delete') {
            console.log(data);
            $.post("http://localhost:8080/delhistorytrade", {
                id:data.id,
                //Lu 4.12 up END
                success: function (resu) {
                    console.log(resu)
                },
                error: function (err) {
                    console.log(err)
                }


            }, resu => {
                layer.open({
                    type:1,
                    content:resu.msg,
                    area: ['200px', '100px']
                })
                // 函数回调
                console.log(resu)
                window.parent.location.href = "/page/adminindex#//page/historytrade1"
            })
            return false;
        }
    });
});

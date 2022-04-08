layui.use(['form', 'table', 'miniPage', 'element', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage

    table.render({
        id: "tableone",
        elem: '#currentTableId',
        url: 'http://localhost:8080/stock_list',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "numbers", width: 50, title: "序号"},
            {field: 'id', width: 140, title: 'ID'},
            {field: 'code', width: 140, title: '代码号'},
            {field: 'name', width: 150, title: '名称'},
            // {
            //     field: 'zuoshou', width: 140, title: '昨收', templet: function (data) {
            //         return "<span style='color: green'>" + data.zuoshou + "</span>"
            //     }
            // },
            // {field: 'shiyinglv', title: '市盈率', minWidth: 120},
            // {
            //     field: 'price', width: 80, title: '价格', templet: function (data) {
            //         return "<span style='color: red'>" + data.price + "</span>"
            //     }
            // },
            {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });


    $('#sear').on('click', function () {
        var send_name = $('#daima').val();
        // 搜索条件
        console.log("result：" + send_name)
        if (send_name == '') {
            table.reload('tableone', {
                method: 'post',
                url: 'http://localhost:8080/stock_list',
                page: {
                    curr: '1'
                }
            });
        } else {
            table.reload('tableone', {
                method: 'post',
                url: 'http://localhost:8080/stock_list',
                where: {
                    'daima': send_name
                },
                page: {
                    curr: '1'
                }
            });
        }

    });


    //按钮操作
    table.on('tool(currentTableFilter)', function (obj) {
        console.log(obj)
        var data = obj.data; // data是数据
        console.log(JSON.stringify(data))

        if (obj.event === 'info'){
            //跳转
            sessionStorage.setItem("stockcode", data.code)
            sessionStorage.setItem("stockname", data.name)
            window.parent.location.href = "/page/adminindex#//page/stockinfo"
        }
        if (obj.event === 'sh') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/daily/n/${data.code}.gif`
        }else if(obj.event === 'sh2') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/weekly/n/${data.code}.gif`
        }else if(obj.event === 'sh3') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/monthly/n/${data.code}.gif`
        }else if(obj.event === 'sh4') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/min/n/${data.code}.gif`
        }
    });
});

layui.use(['form', 'table', 'miniPage', 'element', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage
        , $ = layui.jquery;

    table.render({
        elem: '#currentTableId',
        url: 'http://localhost:8080/pikedstock',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "numbers", width: 100, title: "序号"},
            {field: 'id', width: 140, title: 'ID'},
            {field: 'code', width: 140, title: '股票代码'},
            {field: 'stockname', width: 150, title: '股票名'},
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

        if (obj.event === 'buy') {
            //跳转到购买页面
            sessionStorage.setItem("stockcode", data.code)
            sessionStorage.setItem("stockname", data.stockname)
            window.parent.location.href = "/page/index#//page/buy_stock"
        }
        if (obj.event === 'dispick') {
            console.log(data);
            $.post("http://localhost:8080/dispick", {
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
                window.parent.location.href = "/page/index#//page/picked"
            })
            return false;
        }
        if (obj.event === 'info'){
            //跳转
            sessionStorage.setItem("stockcode", data.code)
            sessionStorage.setItem("stockname", data.name)
            window.parent.location.href = "/page/index#//page/stockinfo"
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

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
            {type: "numbers", width: 100, title: "序号"},
            {field: 'stockcode', width: 140, title: '代码号'},
            {field: 'stockname', width: 150, title: '名称'},
            {field: 'num', width: 150, title: '数量'},
            {field: 'price', width: 150, title: '均价'},
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
            sessionStorage.setItem("stockcode", data.stockcode)
            sessionStorage.setItem("stockname", data.stockname)
            window.parent.location.href = "/page/index#//page/buy_stock"
        }

        if (obj.event === 'sell') {
            //跳转到购买页面
            sessionStorage.setItem("stockcode", data.stockcode)
            sessionStorage.setItem("stockname", data.stockname)
            sessionStorage.setItem("num", data.num)
            window.parent.location.href = "/page/index#//page/sell_stock"
        }
        if (obj.event === 'info'){
            //跳转
            sessionStorage.setItem("stockcode", data.stockcode)
            sessionStorage.setItem("stockname", data.stockname)
            window.parent.location.href = "/page/index#//page/stockinfo"
        }
        if (obj.event === 'sh') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/daily/n/${data.stockcode}.gif`
        }else if(obj.event === 'sh2') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/weekly/n/${data.stockcode}.gif`
        }else if(obj.event === 'sh3') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/monthly/n/${data.stockcode}.gif`
        }else if(obj.event === 'sh4') {
            // 股票实时分析图
            window.location.href = `http://image.sinajs.cn/newchart/min/n/${data.stockcode}.gif`
        }
    });
});

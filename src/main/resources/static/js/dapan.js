// 大盘表格数据
layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#demo'
        , url: 'https://api.doctorxiong.club/v1/stock/board'
        , parseData: function (res) {
            return {
                "code": 0
                , 'msg': "操作成功"
                , "count": 1000
                , "data": res.data
            }
        }
        , cols: [[
            {field: 'code', title: '股票代码'}
            , {field: 'name', title: '股票名称'}
            , {field: 'open', title: '今日开盘价', sort: true}
            , {field: 'close', title: '昨日开盘价', sort: true}
            , {field: 'changePercent', title: '价格变化', sort: true}
            , {field: 'type', title: 'GP(股票),ZS(指数)'}
            , {field: 'price', title: '实时价格', sort: true}
            , {field: 'date', title: '时间'}
        ]]
    })
});

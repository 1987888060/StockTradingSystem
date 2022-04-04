layui.use(['form', 'jquery', 'miniPage'], function () {
    var miniPage = layui.miniPage
        , layer = layui.layer
        , $ = layui.jquery;

    $.get("http://localhost:8080/newsinfo", resu => {
        let data = resu.data;
        for (let i = 0; i < data.length; i++) {
            $("#app").append(`<div class="col-md-4">
                            <img src="${data[i]['img_addr']}" alt="...">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 onclick="" data-id=${i} class="card-title center-title">${data[i]['title']}</h5>
                                <p class="card-text"><small class="text-muted">${data[i]['note']}</small></p>
                            </div>
                        </div>`)
        }
        /*事件委托对象*/
        $("#app").on("click", ".center-title", function () {
            var index = $(this).data('id')
            window.sessionStorage.setItem("json", JSON.stringify(data[index]));
            console.log(data[index])
            window.open('_blank').location = '/page/to_info'
        })
    })
});

//热门基金数据
layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#demo3'
        , url: 'https://api.doctorxiong.club/v1/fund/hot'
        , page: true
        , parseData: function (res2) {
            console.log(res2.data.rank)
            return {
                "code": 0
                , 'msg': "操作成功"
                , "count": 1000
                , "data": res2.data.rank
            }
        }
        , cols: [[
            {field: 'code', title: '基金代码'}
            , {field: 'name', title: '基金名称'}
            , {field: 'fundType', title: '基金类型'}
            , {field: 'netWorth', title: '当前基金单位净值', sort: true}
            , {field: 'dayGrowth', title: '日涨幅', sort: true}
            , {field: 'lastMonthGrowth', title: '月涨幅', sort: true}
            , {field: 'thisYearGrowth', title: '今年的涨幅', sort: true}
        ]]
    });
});

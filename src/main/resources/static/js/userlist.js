layui.use(['form', 'table', 'miniPage', 'element', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage

    table.render({
        id: "tableone",
        elem: '#currentTableId',
        url: 'http://localhost:8080/user/list',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [[
            {type: "numbers", width: 50, title: "序号"},
            {field: 'id', width: 140, title: 'ID'},
            {field: 'username', width: 140, title: '用户名'},
            {field: 'password', width: 140, title: '密码'},
            {field: 'create_time', width: 150, title: '创建时间'},
            {field: 'balance', width: 150, title: '余额'},
            {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });


    $('#sear').on('click', function () {
        var send_name = $('#username').val();
        $('#username').val('');
        // 搜索条件
        console.log("result：" + send_name)
        if (send_name === '') {
            table.reload('tableone', {
                method: 'post',
                url: 'http://localhost:8080/user/list',
                where: {
                    'username': ""
                },
                page: {
                    curr: '1'
                }
            });
        } else {
            table.reload('tableone', {
                method: 'post',
                url: 'http://localhost:8080/user/list',
                where: {
                    'username': send_name
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
        if (obj.event === 'trade'){
            //跳转
            sessionStorage.setItem("username", data.username)
            sessionStorage.setItem("id", data.id)
            window.parent.location.href = "/page/adminindex#//page/historytrade"
        }
        if (obj.event === 'del'){
            $.post("http://localhost:8080/user/deleteById", {
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
                window.parent.location.href = "/page/adminindex#//page/userlist"
            })
            return false;
        }

    });
});

layui.use(['form', 'table', 'miniPage', 'element', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        miniPage = layui.miniPage
    table.render({
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
            {field: 'id', width: 120, title: 'ID'},
            {field: 'username', width: 120, title: '用户名', edit: 'text'},
            {field: 'password', width: 150, title: '密码', edit: 'text'},
            {field: 'stock_nums', width: 150, title: '休市（0以外休市）', edit: 'text'},
            {field: 'create_time', title: '创建时间', minWidth: 150},
            {field: 'balance', width: 120, title: '余额', edit: 'text'},
            {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });
    //修改
    table.on('tool(currentTableFilter)', function (obj) {
        var data = obj.data; // data是数据
        console.log(JSON.stringify(data))
        if (obj.event === 'add') {
            // 处理Ajax请求，发送到后台进行卖出
            layer.confirm('确定修改吗', {
                    icon: 3,
                    title: '提示'
                }
                , function (index) {
                    $.ajax({
                        type: "POST",
                        url: "http://localhost:8080/user/upd",
                        data: JSON.stringify(data),
                        contentType: "application/json",
                        dataType: "json",
                        success: function (resu) {
                            // 数据回显成功之后进行表格重载
                            table.reload('currentTableId', {
                                url: "http://localhost:8080/user/list"
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
    //休市
    layui.jquery(document).on('click', '#xiu', function () {
        layer.confirm('确定休市吗', {
                icon: 3,
                title: '提示'
            }
            , function () {
                $.ajax({
                    url: "http://localhost:8080/user/xiu",
                    success: function () {
                        // 数据回显成功之后进行表格重载
                        table.reload('currentTableId', {
                            url: "http://localhost:8080/user/list"
                        })
                        layer.msg("已休市")
                    },
                })
            }
        )
    });
    //开市
    layui.jquery(document).on('click', '#kai', function () {
        layer.confirm('确定开市吗', {
                icon: 3,
                title: '提示'
            }
            , function () {
                $.ajax({
                    url: "http://localhost:8080/user/kai",
                    success: function () {
                        // 数据回显成功之后进行表格重载
                        table.reload('currentTableId', {
                            url: "http://localhost:8080/user/list"
                        })
                        layer.msg("已开市")
                    },
                })
            }
        )
    })
});

/**
 * 登陆
 */
$(function() {
    // 点击回车登录
    $(document).ready(function() {
        // 回车提交事件
        $("body").bind('keydown', function(event) {
            if (event.keyCode == 13) {
                $(".login-btn").click();
            }
        });
    });

    // 登录
    $(".login-btn").click(function() {
        var valid = $('#form').Validform();
        if (!valid) {
            return;
        }
        var data = $("#form").serializeObject();
        $.ajax({
            method : "post",
            url : "/login",
            data : data,
            async : false,
            datatype : "json",
            success : function(result) {
                if (result.code == 200) {
                    location.href = "/";
                } else {
                    $('.messagepass').html(result.msg);
                }
            },
            error : function(errordata) {
                alert("登录页面请求异常！");
            }
        });
    });

});


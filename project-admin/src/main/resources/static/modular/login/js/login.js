/**
 * 登陆
 */
$(function() {
    //点击登陆
    $('#btnSubmit').click(function() {
        login();
    });

});

function login() {

    //alert(ctx);
    $('.messagepass').html("");
    var account = $("input[name='account']").val();
    var passWord = $("input[name='passWord']").val();
    var data = {
        account: account,
        passWord: passWord,
        rememberMe:true
    }
    $.ajax({
        type: 'POST',
        url: "/login",
        dataType: "json",
        async:true,
        data: data,
        success: function(result){

            if(result.code == 200){
               var cur= window.document.location.href;
               var path =window.document.location.pathname;
                var s=cur.indexOf(path);
                window.location =cur.substring(0,s)+"/";
            }else{
                $('.messagepass').html(result.msg);

            }
        }
    });
}

layui.use(['layer', 'form'], function() {
    var layer = layui.layer,
    $ = layui.jquery,
    form = layui.form;

    $('#verifitionCodeImg').on('click', function() {
    //document.getElementById('verifitionCodeImg').scr=;
    $("#verifitionCodeImg").attr("src","http://localhost:8088/index/verif.do?"+Math.random());
    });

    //清理左侧菜单缓存
    var index = layer.load(2, {
    shade: [0.3, '#333']
    });
    $(window).on('load', function() {
    layer.close(index);

    form.on('submit(login)', function(data) {
    var userName = data.field.userName,
    password = data.field.password,
    validCode = data.field.validCode;
    if(validCode == "" || validCode == "undefine") {
    layer.msg('验证码不能为空！',{time:2000, shift: 5, icon: 5});
    return false;
    }
    if(userName == "" || password == "") {
    layer.msg('用户名或密码不能为空！',{time:2000, shift: 5, icon: 5});
    return false;
    }
    $.ajax({
    url:"http://localhost:8088/user/login.do",
    type:"post",
    data: {"userName":userName,"password":password,"validCode":validCode},
    dataType:"json",
    success:function(result){
    if(result.status == 10) {
    addCookie("clientId", result.data);
    location.href='html/index.html';
    } else {
    layer.msg(result.msg,{time:2000, shift: 5, icon: 5},function(){});
    }
    },
    error:function(XMLHttpRequest, textstatus,errorThrown) {
    layer.msg(XMLHttpRequest.status);
    layer.msg(XMLHttpRequest.readystate);
    layer.msg(textstatus);
    return false;
    }
    });
    return false;
    });
    }());

    });
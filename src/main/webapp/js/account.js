  layui.use(['table', 'form'], function() {
			  var layer = layui.layer,
			  table = layui.table,  form = layui.form;
			  $ = layui.jquery;
			  var userCode = "32032219921217621X";
			  queryUser(table, userCode);
			  
			  //查询功能
			  //监听提交
			  form.on('submit(queryUser)', function(data){
			   queryUser(table, data.field.userCode);
			    return false;
			  });
	     
});

function queryUser(table, userCode) {
	$.ajax({
		url:"http://localhost:8088/account/accountQuery.do",
		type:"post",
		data: {"userCode":userCode},
		dataType:"json",
		success:function(result){
		var userData = eval("("+result.data+")");
			table.render({
				 elem: '#userAccountTab',
				 data:userData,
				 height: 150,
				 skin: 'row',
				 cols: [[ //标题栏
				          {field: 'CLIENT_NAME', title: '姓名', width:300},
				          {field: 'CLIENT_IDENTITY', title: '身份证号', width:300},
				           {field: 'CLIENT_BANK_ID', title: '开户银行', width:300},
				           {field: 'CLIENT_ACCOUNT_CODE', title: '银行账号' ,width:300},
				           {field: 'CLIENT_BALANCE', title: '余额', width:300}
				         ]] ,
				 //表格风格
				 even: true,
				 page: true, //是否显示分页
				 limits: [1],
				 limit: 1 //每页默认显示的数量
			});
		},
		error:function(XMLHttpRequest, textstatus,errorThrown) {
			 layer.msg(XMLHttpRequest.status);
			 layer.msg(XMLHttpRequest.readystate);
			 layer.msg(textstatus);
			return false;
		}
});
}
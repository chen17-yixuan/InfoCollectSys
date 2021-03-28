$(function(){
	$('#username').focus().blur(checkName);
	$('#password').blur(checkPassword);
	$('#loginin').click(loginin);
});

function checkName(){
	var name = $('#username').val();
	if(name == null || name == ""){
		//提示错误
		$('#count-msg').html("不能为空");
		return false;
	}
	var reg = /^\w{3,10}$/;
	if(!reg.test(name)){
		$('#count-msg').html("输入3-10个字母或数字或下划线");
		return false;
	}
	$('#count-msg').empty();
	return true;
}

function checkPassword(){
	var password = $('#password').val();
	if(password == null || password == ""){
		//提示错误
		$('#password-msg').html("不能为空");
		return false;
	}
	var reg = /^\w{3,10}$/;
	if(!reg.test(password)){
		$('#password-msg').html("输入3-10个字母或数字或下划线");
		return false;
	}
	$('#password-msg').empty();
	return true;
}

function loginin(){
	var usernamecheck = checkName();
	var passcheck = checkPassword();
	if(usernamecheck && passcheck){
		var username = $('#username').val();
		var userpass = $('#password').val();

		if(username == "yunwei" && userpass == "666666"){
			alert("登录完成")
			window.location = "/base.html" ;
		}else {
			alert("登陆失败，请检查密码")
		}
	}
}

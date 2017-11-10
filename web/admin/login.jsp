<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
    <meta >
    <script>
        var xmlHttp;
        window.onload = function () {
            if (getCookie("usernm") != null && getCookie("pssd") != null && getCookie("usernm") != undefined && getCookie("pssd") != undefined) {
                var nmInput = document.getElementsByName("username")[0].value = getCookie("usernm");
                var pssdInput = document.getElementsByName("password")[0].value = getCookie("pssd");
            }
        };

        //写cookies
        function setCookie(key, value, expired) {
            var exp = new Date();
            console.log(escape(value));
            if (expired != null && expired != 0) {
                exp.setTime(exp.getTime() + expired * 24 * 60 * 60 * 1000);
                document.cookie = key + "=" + escape(value) + ";expires=" + exp.toGMTString();
            } else {
                document.cookie = key + "=" + escape(value);
            }
        }
        //取出cookies
        function getCookie(key) {
            var arr, reg = new RegExp("(^| )" + key + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        }
        //删除cookes
        function delCookie(key) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = getCookie(key);
            if (cval != null)
                document.cookie = key + "=" + cval + ";expires=" + exp.toGMTString();
        }
        //创建XMLHttpRequest对象
        function createXMLHttpRequest() {
            if (window.XMLHttpRequest) { //Mozilla 浏览器
                xmlHttp = new XMLHttpRequest();
            } else if (window.ActiveXObject) { //IE浏览器
                try {
                    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e) {
                    }
                }
            }
            if (xmlHttp == null) {
                alert("不能创建XMLHttpRequest对象");
                return false;
            }
        }
        //发送异步请求
        function sendAsynchronRequest(url, parameter, callback) {
            createXMLHttpRequest();
            if (parameter == null) {
                //设置事件处理器，当XMLHttp状态发生变化，会触发该事件处理器，由他调用callback
                xmlHttp.onreadystatechange = callback;
                //设置对拂去其调用的参数（提交的方式，请求url，请求类型（是否异步请求））
                xmlHttp.open("GET", url, false);//true异步,false同步。
                xmlHttp.send(null);
            } else {
                xmlHttp.onreadystatechange = callback;
                xmlHttp.open("POST", url, false);
                xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
                xmlHttp.send(parameter);
            }
        }

        // 回调方法
        function loadCallBack() {
            try {
                if (xmlHttp.readyState == 4) {
                    if (xmlHttp.status == 200) {
                        if (xmlHttp.responseText) {
                            var json = JSON.parse(xmlHttp.responseText);
                            var tip = document.getElementById("tip");
                            if (json.code == 1) {//登陆成功
                                var pssdCheck = document.getElementsByName("checkbox")[0].value;
                                var usernm = document.getElementsByName("username")[0].value;
                                var pssd = document.getElementsByName("password")[0].value;
                                if (pssdCheck) {//记住账号密码
                                    setCookie("usernm", usernm, 0);
                                    setCookie("pssd", pssd, 0);
                                } else {
                                    delCookie("usernm");
                                    delCookie("pssd");
                                }
                                tip.style.display = "none";
                                window.location.href = "<%=request.getContextPath()%>/admin/";
                            } else {
                                tip.style.display = "block";
                            }
                        }
                    }
                }
            }
            catch (e) {
            }
        }

        //调用
        function doLogin() {
            var url = "<%=request.getContextPath()%>/user/login.php";
            var usernm = document.getElementsByName("username")[0].value;
            var pssd = document.getElementsByName("password")[0].value;
            var params = "loginname=" + usernm + "&password=" + pssd;
            sendAsynchronRequest(url, params, loadCallBack);
        }

    </script>
</head>
<body>
<div class="login">
    <div class="content">
        <div class="content_unit">
            <div class="login_right_content_title">请登录</div>
            <div class="login_right_unit">
                <input type="text" class="username" name="username" placeholder="请输入用户名" />
            </div>
            <div class="login_right_unit">
                <input type="password" class="password" name="password" placeholder="请输入密码" />
            </div>
            <div class="login_right_remmber">
                <input type="checkbox"  name="checkbox" checked/>
                <div class="login_content_remmber_text">记住密码</div>
            </div>
            <div style="color:red;display: none;width: 120px;height: 15px;margin:0 auto; font-size: 14px;" id="tip">
                账号或者密码错误
            </div>
            <div class="login_btn" onclick="doLogin()"> 登录</div>
        </div>
    </div>
</div>
</div>
</body>
</html>

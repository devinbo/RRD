<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
        .error {
            width: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        .error_content {
            width: 600PX;
            height: 500px;
            margin: 0 auto;
            padding: 0;
            background: url(<%=request.getContextPath()%>/img/404-ico.jpg) no-repeat;
            background-size: 600px 400px;
            overflow: hidden;
        }
        .btn {
            width: 200px;
            margin: 300px 0 0 70px;
            padding: 10px 0;
            font-size: 12px;
        }
        .btn_back {
            width: 100px;
            height: 24px;
            line-height: 24px;
            float: left;
            margin-right: 20px;
            border: #e7792b solid 1px;
            color: #e7792b;
            border-radius: 3px;
            text-align: center;
            cursor: pointer;
        }
        .btn_index {
            width: 60px;
            height: 24px;
            line-height: 24px;
            float: left;
            border: #e7792b solid 1px;
            color: #e7792b;
            border-radius: 3px;
            text-align: center;
            cursor: pointer;
        }
    </style>
    <script>
        function goHome() {
            window.location.href = '<%=request.getContextPath()%>/';
        }
        function goBack() {

        }
    </script>
</head>
<body>
<div class="error">
    <div class="error_content">
        <div class="btn">
            <div class="btn_back"><a href="javascript:history.go(-1)">返回上一页</a></div>
            <div class="btn_index" onclick="goHome()">首页</div>
        </div>
    </div>
</div>
</body>
</html>

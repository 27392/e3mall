<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>学生信息</h1>
    <p>学号：${student.id}</p>
    <p>姓名：${student.name}</p>
    <p>年龄：${student.age}</p>
    <p>地址：${student.address}</p>
    <table border="1">
        <tr>
            <th>编号</th>
            <th>学号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>地址</th>
        </tr>
        <#list studentList as s>
            <#if s_index % 2 == 0 >
                <tr style="background-color: #00a9e0">
            <#else>
                <tr>
            </#if>
                    <td>${s_index}</td>
                    <td>${s.id}</td>
                    <td>${s.name}</td>
                    <td>${s.age}</td>
                    <td>${s.address}</td>
                </tr>
        </#list>
    </table>
    <p>${date?date}</p>
    <p>${date?time}</p>
    <p>${date?datetime}</p>
    <p>${date?string('yyyy/MM/dd HH:mm:ss')}</p>
    <p>${null!"显示null的默认值"}</p>
    <#if null??>有内容<#else>没有内容</#if>
    <#include "hello.ftl">
</body>
</html>
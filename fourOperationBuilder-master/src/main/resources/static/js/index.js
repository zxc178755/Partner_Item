const generateBtn = document.getElementsByClassName("generateBtn")[0];
const checkBtn = document.getElementsByClassName("checkBtn")[0];
const quesNum = document.getElementsByClassName("quesNum")[0];
const fraScale = document.getElementsByClassName("scale")[0];

// 封装AJAX
function obj2str(data) {
    let res = [];
    for(let key in data) {
        res.push(encodeURIComponent(key)+"="+encodeURIComponent(data[key]));
    }
    return res.join("&");
}
function ajax(option)
{
    let str = obj2str(option.data);
    let xmlhttp, timer;
    // 创建异步对象
    if(window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else
    {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(option.type.toLowerCase() === "get") {
        xmlhttp.open(option.type, option.url+"?"+str, false);// 设置请求方式和请求地址
        xmlhttp.send();// 发送请求
    } else {
        xmlhttp.open(option.type, option.url, false);
        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlhttp.send(str);
    }
    // 监听状态变化，判断服务器请求是否完成且判断请求是否成功
    if(xmlhttp.readyState === 4) {
        clearInterval(timer);
        // 处理返回的结果
        if(xmlhttp.status >= 200 && xmlhttp.status < 300 || xmlhttp.status === 304) {
            option.success(xmlhttp);// 执行success回调函数
        } else {
            option.error(xmlhttp);// 执行error回调函数
        }
    }
    if(option.timeout) {
        timer = setInterval(function() {
            xmlhttp.abort();
            clearInterval(timer);
        }, option.timeout);
    }
}

// 生成题目和答案
generateBtn.addEventListener('click', function() {
    const num = quesNum.value;
    const scale = fraScale.value;
    ajax ({
        type: "POST",
        url: "/generate",
        data: {
            "questionNum": num,
            "maxLimit": scale
        },
        async: false, //保证ajax执行后才往下执行
        success: function(xmlhttp) {
            const str = xmlhttp.responseText;
            const obj = JSON.parse(str);
            console.log(obj);
            status = obj.status;
        },
        error: function(xmlhttp) {
            console.log(xmlhttp.status);
        }
    });
})

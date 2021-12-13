//时间格式化函数
export function timeFmt(value) {
    var date = new Date(value);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    month = month > 9 ? month : ('0' + month);
    var day = date.getDate();
    day = day > 9 ? day : ('0' + day);
    var hh = date.getHours();
    hh = hh > 9 ? hh : ('0' + hh);
    var mm = date.getMinutes();
    mm = mm > 9 ? mm : ('0' + mm);
    var ss = date.getSeconds();
    ss = ss > 9 ? ss : ('0' + ss);
    var time = year + '-' + month + '-' + day + ' ' + hh + ':' + mm + ':' + ss;
    return time;
};

// export function countDown(dateStr){
//     var end = +new Date(dateStr),
//         start = +new Date(),
//         during = Math.floor((end - start)/ 1000);
 
//         var day = Math.floor(during/(24*60*60)),
//             t1 = during - (day*24*60*60),
//             hour = Math.floor(t1/(60*60)),
//             t2 = t1- hour*60*60,
//             min = Math.floor(t2/60),
//             sec = t2%60;
 
//         return day +'天' + hour + '小时' + min + '分' + sec + '秒';
// }







# ZLog

ZLog,更方便的打印日志,Toast
参考[KLog](https://github.com/liompei/KLog)做了一些优化

- 支持显示行号
- 支持显示Log所在函数名称
- 支持无Tag快捷打印
- 支持在Android Studio开发IDE中，点击函数名称，跳转至Log所在位置
- 支持Json,XML字符串打印
- 支持无限制字数打印
- 支持全局Tag
- 支持Toast

## How to Use
Zlog可打印普通日志和Json,xml字符串,支持无msg、无tag、有tag模式
如
```
Z.d();
Z.d("This is debug");
Z.d("tag","This is debug")

```
输出结果
![](https://github.com/liompei/ZLog/blob/master/img/simple1.png)

##主要方法有:
###Log

```
Z.v();  //verbose
Z.d();  //debug
Z.i();  //information
Z.w();  //warning
Z.e();  //error
Z.wtf();  //What a Terrible Failure
Z.json();  //parse json
Z.xml();  //parse xml
```
若要设置全局Tag
```
Z.initLog("TAG",true);  //tag,isShowlog
```

###Toast
注意:使用Toast必须初始化传入Context,否则将不显示
```
Z.initToast(getApplicationContext(),true);  //context,isShowToast
```

```
Z.Show("This is Toast");
```


